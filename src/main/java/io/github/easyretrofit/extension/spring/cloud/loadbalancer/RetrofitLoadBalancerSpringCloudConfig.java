package io.github.easyretrofit.extension.spring.cloud.loadbalancer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetrofitLoadBalancerSpringCloudConfig {

    @Bean
    @ConditionalOnMissingBean
    public RetrofitLoadBalancerInterceptor retrofitLoadBalancerInterceptor() {
        return new RetrofitLoadBalancerInterceptor();
    }
}
