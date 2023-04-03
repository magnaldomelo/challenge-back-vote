package com.southsystem.challengebackvote.infrastructure.api.pool;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.Client;
import feign.Request;
import feign.httpclient.ApacheHttpClient;

public class UsersPoolConfig {

    @Value("${pool.usersservice.maxConn}")
    private Integer connPerRoute;

    @Value("${pool.usersservice.maxRoutes}")
    private Integer maxConnTotal;

    @Value("${pool.usersservice.connTimeout}")
    private Integer connTimeout;

    @Value("${pool.usersservice.readTimeout}")
    private Integer readTimeout;

    @Bean
    public Request.Options options() {
        return new Request.Options(connTimeout, readTimeout);
    }

    @Bean
    public Client poolConfig() {
        return new ApacheHttpClient(
                HttpClientBuilder.create()
                        .setMaxConnPerRoute(connPerRoute)
                        .setMaxConnTotal(maxConnTotal)
                        .build()
        );
    }
}
