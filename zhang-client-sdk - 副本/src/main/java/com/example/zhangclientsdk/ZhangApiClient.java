package com.example.zhangclientsdk;


import com.example.zhangclientsdk.client.ZhApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("zhang.client")
@Data
@ComponentScan
public class ZhangApiClient {

    private String accessKey;
    private String secretKey;


    @Bean
    public ZhApiClient zhApiClient() {
        return new ZhApiClient(accessKey,secretKey);
    }
}
