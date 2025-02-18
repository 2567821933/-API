package com.example.zhangclientsdk.client;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.example.zhangclientsdk.medol.User;
import com.example.zhangclientsdk.utils.SignUtils;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方接口的客户端
 *
 * @author  zhang
 */
public class ZhApiClient {

    private String accessKey;
    private String secretKey;
    public ZhApiClient() {

    }
    public ZhApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "zhang");

        String result3= HttpUtil.get("http://localhost:8090/api/name/", paramMap);
        return result3;
    }

    public String getNameByPost(String name) {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "zhang");

        String result3= HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        return result3;
    }


    // 测试代码
    public String getUsernameByPost(User user, String requestAddress) {

        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post(requestAddress)
                .charset(StandardCharsets.UTF_8)
                .body(json).addHeaders(getHeaderMap(json))
                .execute();
        System.out.println(httpResponse.getStatus());
        return httpResponse.body();
    }


    // get 请求
    public String getRequestBody(User user, String requestAddress, Map<String, Object> paramMap) {
        String json = JSONUtil.toJsonStr(user);
        // 发送GET请求并添加请求头
        String body = HttpUtil.createGet(requestAddress)
                .addHeaders(getHeaderMap(json))
                .form(paramMap)
                .execute()
                .body();
        return body;
    }

    // post 请求
    public String getRequestBodyByPost(User user, String requestAddress, Map<String,Object> paramMap) {
        String json = JSONUtil.toJsonStr(paramMap);
        String body = HttpRequest.post(requestAddress)
                .charset(StandardCharsets.UTF_8)
                .body(json).addHeaders(getHeaderMap(json))
                .execute().body();
        return body;
    }





    // 在调用之前通过这个获取签名
    public Map<String,String> getHeaderMap(String body) {
        HashMap<String, String> map = new HashMap<>();
        map.put("accessKey",accessKey);

        // 注意这个一定不能发送给后端
//        map.put("secretKey",secretKey);
        map.put("body", body);
        map.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        map.put("sign", SignUtils.getSign(body,secretKey)); // 获取签名

        return map;
    }

    /**
     * 获取签名算法
     */

}
