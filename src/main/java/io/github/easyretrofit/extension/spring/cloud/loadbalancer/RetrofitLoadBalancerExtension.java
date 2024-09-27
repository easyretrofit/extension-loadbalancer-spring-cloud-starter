package io.github.easyretrofit.extension.spring.cloud.loadbalancer;


import io.github.easyretrofit.core.RetrofitInterceptorExtension;
import io.github.easyretrofit.core.delegate.BaseExceptionDelegate;
import io.github.easyretrofit.core.exception.RetrofitExtensionException;
import io.github.easyretrofit.core.extension.BaseInterceptor;

import java.lang.annotation.Annotation;

public class RetrofitLoadBalancerExtension implements RetrofitInterceptorExtension {
    @Override
    public Class<? extends Annotation> createAnnotation() {
        return RetrofitLoadBalancer.class;
    }

    @Override
    public Class<? extends BaseInterceptor> createInterceptor() {
        return RetrofitLoadBalancerInterceptor.class;
    }

    @Override
    public Class<? extends BaseExceptionDelegate<? extends RetrofitExtensionException>> createExceptionDelegate() {
        return null;
    }
}
