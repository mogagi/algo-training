package io.mogagi.quant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * @author mogagi
 * @since 2021/10/22 22:52
 */
@Configuration
public class ClientConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder().version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofMillis(5000))
                .executor(Executors.newFixedThreadPool(5)).build();
    }
}