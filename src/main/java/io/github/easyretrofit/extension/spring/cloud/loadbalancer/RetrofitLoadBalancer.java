package io.github.easyretrofit.extension.spring.cloud.loadbalancer;


import io.github.easyretrofit.core.annotation.RetrofitInterceptor;
import io.github.easyretrofit.core.annotation.RetrofitInterceptorParam;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@RetrofitInterceptor(handler = RetrofitLoadBalancerInterceptor.class)
public @interface RetrofitLoadBalancer {
    String name() default "";

    RetrofitInterceptorParam extensions() default @RetrofitInterceptorParam();
}
