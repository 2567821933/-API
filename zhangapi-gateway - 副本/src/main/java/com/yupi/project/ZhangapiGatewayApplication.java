package com.yupi.project;



import com.yupi.project.provider.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
public class ZhangapiGatewayApplication {

    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ZhangapiGatewayApplication.class, args);
        // 尝试获取远程调用
        ZhangapiGatewayApplication bean = run.getBean(ZhangapiGatewayApplication.class);
        System.out.println(bean.doSayHello());

    }

    public String doSayHello() {
        return demoService.getHello();
    }



}
