package io.todak.study.javateststudy.junit;

import io.todak.study.javateststudy.annotation.SlowTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class FindSlowTestExtention implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger log = LoggerFactory.getLogger(FindSlowTestExtention.class);
    private static final long THRESHOLD = 1000L;

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        ExtensionContext.Store store = extractStore(extensionContext);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {

        ExtensionContext.Store store = extractStore(extensionContext);
        long startTime = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - startTime;

        if (duration > THRESHOLD && !isSlowTest(extensionContext)) {
            log.warn("Please considor mark method [{}.{}] with @SlowTest.",
                    extensionContext.getRequiredTestClass().getName(),
                    extensionContext.getRequiredTestMethod().getName());
        }
    }

    private boolean isSlowTest(ExtensionContext context) {
        Method method = context.getRequiredTestMethod();
        SlowTest annotation = method.getAnnotation(SlowTest.class);
        return annotation != null;
    }

    private ExtensionContext.Store extractStore(ExtensionContext context) {
        String testClassName = context.getRequiredTestClass().getName();
        String testMethodName = context.getRequiredTestMethod().getName();
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }

}
