package io.github.easyretrofit.extension.spring.cloud.loadbalancer;


import io.github.easyretrofit.core.RetrofitResourceContext;
import io.github.easyretrofit.core.extension.BaseInterceptor;
import io.github.easyretrofit.core.resource.RetrofitApiInterfaceBean;
import io.github.easyretrofit.core.util.RetrofitUrlUtils;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;


@Component
public class RetrofitLoadBalancerInterceptor extends BaseInterceptor {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RetrofitResourceContext context;

    @Override
    protected Response executeIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        final Method method = super.getRequestMethod(request);
        String clazzName = super.getClazzNameByMethod(method);
        final RetrofitApiInterfaceBean currentServiceBean = context.getRetrofitApiInterfaceBean(clazzName);
        RetrofitLoadBalancer annotation = null;
        annotation = currentServiceBean.getSelfClazz().getAnnotation(RetrofitLoadBalancer.class);
        if (annotation == null) {
            annotation = currentServiceBean.getParentClazz().getAnnotation(RetrofitLoadBalancer.class);
        }
        final String serviceName = RetrofitUrlUtils.convertDollarPattern(annotation.name(), context.getEnvManger()::resolveRequiredPlaceholders);
        if (StringUtils.isNotEmpty(serviceName)) {
            final URI uri = loadBalancerClient.choose(serviceName).getUri();
            final HttpUrl httpUrl = HttpUrl.get(uri);
            HttpUrl newUrl = request.url().newBuilder()
                    .scheme(httpUrl.scheme())
                    .host(httpUrl.host())
                    .port(httpUrl.port())
                    .build();
            request = request.newBuilder().url(newUrl).build();
        }
        return chain.proceed(request);
    }

    @Override
    protected RetrofitResourceContext getInjectedRetrofitResourceContext() {
        return context;
    }
}
