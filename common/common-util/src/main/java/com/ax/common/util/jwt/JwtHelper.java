package com.ax.common.util.jwt;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xing
 */
public class JwtHelper {

    public static String tokenSignKey = "user";
    public static long tokenExpires = 24 * 60 * 60 * 100;

    /**
     * 签发JWT
     */
    public static String createToken(String id, String userName) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
        };
        map.put("uid", id);
        map.put("userName", userName);
        map.put("expire_time", System.currentTimeMillis() + tokenExpires);
        final String token = JWTUtil.createToken(map, tokenSignKey.getBytes());
        return token;
    }

    /**
     * 验证JWT
     *
     * @param token
     * @return
     */
    public static boolean validateToken(String token) {
        return JWTUtil.verify(token, tokenSignKey.getBytes());
    }

    // 根据token字符串得到用户id
    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        final JWT jwt = JWTUtil.parseToken(token);
        String uid = (String) jwt.getPayload("uid");
        return Long.valueOf(uid);
    }

    public static String getUserName(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        final JWT jwt = JWTUtil.parseToken(token);
        String userName = (String) jwt.getPayload("userName");
        return userName;
    }
}
