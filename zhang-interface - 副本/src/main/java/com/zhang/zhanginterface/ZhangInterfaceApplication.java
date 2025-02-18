package com.zhang.zhanginterface;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ZhangInterfaceApplication {



    public static void main(String[] args) {

        SpringApplication.run(ZhangInterfaceApplication.class, args);
    }

}
