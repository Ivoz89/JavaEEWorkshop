/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataaccess.logging;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.inject.Qualifier;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author izielinski
 */
@Retention(RUNTIME)
@Target({METHOD, TYPE})
@InterceptorBinding
@Qualifier
public @interface Log {

}
