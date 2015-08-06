/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.logging;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.apache.log4j.Logger;

/**
 *
 * @author izielinski
 */
@Log
@Interceptor
public class LoggingInterceptor {

    private static final Logger LOG = Logger.getLogger(LoggingInterceptor.class.getName());

    @AroundInvoke
    public Object logMethodEntry(InvocationContext ctx) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entering method ").append(ctx.getMethod().getName());
        stringBuilder.append(" with parameters:");
        for (Object o : ctx.getParameters()) {
            if (o != null) {
                stringBuilder.append("\n\t").append(o.getClass()).append("=").append(o.toString());
            } else {
                stringBuilder.append("\n\t NULL!!");
            }
        }
        LOG.info(stringBuilder.toString());
        return ctx.proceed();
    }
}
