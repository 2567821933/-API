package com.example.zhangclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtils {


    public static String getSign(String body, String secretKey) {
        // 通过hutool 签名认证加密
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content = body + secretKey;
        return digester.digestHex(content); // 加密
    }
}
