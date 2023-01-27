package s.m.learning.footballapp.instrumentation;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class Instrumentor {

    @SneakyThrows
    @Around("@annotation(s.m.learning.footballapp.instrumentation.Instrumented)")
    public Object measureExecutionTime(final ProceedingJoinPoint joinPoint){
        final StopWatch stopWatch = new StopWatch();
        final String methodName = joinPoint.getSignature().getName();
        try {
            stopWatch.start(methodName);
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            log.info("{}() took {} ms to complete",
                    methodName, stopWatch.getLastTaskTimeMillis());
        }
    }
}