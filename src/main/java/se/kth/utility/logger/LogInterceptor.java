/*
 */
package se.kth.utility.logger;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * 
 */
@Log
@Interceptor
public class LogInterceptor implements Serializable{

    private static final Logger LOGGER = Logger.getLogger("se.kth");
    //FINE is a message level providing tracing information.
    private static final Level LEVEL = Level.FINE;

    /**
     * Logs entry to and exit from a method. Also logs parameter values, return
     * value and exceptions.
     *
     * @param ctx
     * @return The value returned by the intercepted method.
     * @throws Exception
     */
    @AroundInvoke
    public Object logInvocation(InvocationContext ctx) throws Exception {
        Method targetMethod = logEntry(ctx);
        Object returnValue = null;
        try {
            returnValue = ctx.proceed();
        } catch (Exception e) {
            logException(targetMethod, e);
        }
        logExit(targetMethod, returnValue);
        return returnValue;
    }

    private Method logEntry(InvocationContext ctx) {
        Method targetMethod = ctx.getMethod();
        Object[] params = ctx.getParameters();
        LOGGER.log(LEVEL, "Entering: {0}", targetMethod.toGenericString());
        LOGGER.log(LEVEL, "    Parameters: {0}", Arrays.toString(params));
        return targetMethod;
    }

    private void logException(Method targetMethod, Exception e) throws Exception {
        Object[] args = {targetMethod.getDeclaringClass().getCanonicalName(),
            targetMethod.getName(), e.getClass().getCanonicalName()};
        LOGGER.log(LEVEL, "{0}.{1} threw {2}", args);
        throw (e);
    }

    private void logExit(Method targetMethod, Object returnValue) {
        Object[] args = {targetMethod.getDeclaringClass().getCanonicalName(),
            targetMethod.getName()};
        LOGGER.log(LEVEL, "Call to {0}.{1} completed", args);
        if (!isVoid(targetMethod) && returnValue != null) {
            LOGGER.log(LEVEL, "    Return value: {0}", returnValue.toString());
        }
    }
    
    private boolean isVoid(Method targetMethod) {
        return targetMethod.getReturnType().isAssignableFrom(Void.TYPE);
    }

}
