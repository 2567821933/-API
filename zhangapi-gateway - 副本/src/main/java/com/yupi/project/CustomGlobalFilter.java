package com.yupi.project;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SignUtil;
import com.example.myapicommon.exception.BusinessException;
import com.example.myapicommon.exception.ErrorCode;
import com.example.myapicommon.model.entity.InterfaceInfo;
import com.example.myapicommon.model.entity.User;
import com.example.myapicommon.service.InnerInterfaceInfoService;
import com.example.myapicommon.service.InnerUserService;
import com.example.zhangclientsdk.client.ZhApiClient;
import com.example.zhangclientsdk.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static com.example.zhangclientsdk.utils.SignUtils.getSign;


/**
 * 全局过滤
 */
@Slf4j
@Configuration
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    //
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");



    /**
     * 五分钟过期时间
     */
    private static final long FIVE_MINUTES = 5L * 60;



    @DubboReference
    InnerUserService innerUserService;

    @DubboReference
    InnerInterfaceInfoService interfaceInfoService;





    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 参数1 exchange 路由交换机,获取请求信息
        // 参数2 chain 责任链模式





        //2. 请求日志
        // 获取请求对象
        ServerHttpRequest request1 = exchange.getRequest();
        System.out.println(request1.getId());
        System.out.println(request1.getPath().value());
        System.out.println(request1.getMethod());
        System.out.println(request1.getQueryParams());
        System.out.println(request1.getRemoteAddress());
        // 获取响应对象
        ServerHttpResponse response = exchange.getResponse();
        //3. （黑白名单）
        String hostString = request1.getLocalAddress().getHostString();
        if(!IP_WHITE_LIST.contains(hostString)) {
            // 拒绝
            System.out.println("返回响应");
            response.setStatusCode(HttpStatus.FORBIDDEN); // 设置响应状态码
            return handleNoAuth(response); // 直接返回
        }

        // 1. 流量染色,获取构造器,添加染色头。注意request是不能修改的，可以重新构建一个
        ServerHttpRequest request = request1.mutate().header("X-Traffic-Color", "blue").build();


        //4. API对用户鉴权（判断ak，sk是否合法）
        // todo 通过请求获取信息，完成鉴权
        // 4.1 获取ak，sk（通过SDK保存在请求头中
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String body = headers.getFirst("body");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        // 获取数据判断
        if(!StrUtil.isAllNotEmpty(accessKey,body,timestamp,sign)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "请求参数为空");
        }

        // 防重发XHR（XmlHttpRequest）超时校验
        long currentTime = System.currentTimeMillis() / 1000;
        assert timestamp != null;
        if (currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "会话已过期,请重试！");
        }


        // 4.2 通过ak，获取用户信息，判断是否存在。
        User user = innerUserService.getUserByAccessKey(accessKey);
        // 校验用户
        if(user == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "情正确配置接口凭证");
        }
        // 校验accessKey
        if (!user.getAccessKey().equals(accessKey)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "请先获取请求密钥");
        }
        // 校验签名
        if (!getSign(body, user.getSecretKey()).equals(sign)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "非法请求");
        }

        //5. 判断请求的接口是否存在
        //todo 从数据库中查询模拟接口是否存在，以及请求方法是否匹配，还可以校验请求参数。
        String url = "http://localhost:8090" + request.getPath().value();
        System.out.println("输出查找的url" + url);
        InterfaceInfo interfaceInfoByUrl = interfaceInfoService.getInterfaceInfoByUrl(url);
        if(interfaceInfoByUrl == null || !interfaceInfoByUrl.getMethod().equals(request.getMethodValue())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口不存在");
        }





        // 这里可以用到锁
        //6. 判断接口是否有剩余调用次数,在外面做了



        // 这里是请求之后的事情，需要搜索装饰类来增强响应后的操作能力。

        //7. 请求转发，调用模拟接口
        //8. 响应日志, 可以尝试搜索
        //9. todo 调用成功，接口次数+1 ， 在外面做了
        //10. 调用失败，返回一个规范的错误码







        System.out.println("进入了全局过滤器");
        return chain.filter(exchange.mutate().request(request).build()); // 修改成新的请求。
    }

    @Override
    public int getOrder() { // 执行顺序
        return 0;
    }


    // 处理未经授权的请求，并返回一个403
    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

}
