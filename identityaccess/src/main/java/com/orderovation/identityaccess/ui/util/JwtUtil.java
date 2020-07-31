package com.orderovation.identityaccess.ui.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.orderovation.identityaccess.ui.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 韦盛友
 */
public class JwtUtil {

    public static final long EXPIRE_TIME = 30 * 60 * 1000;
    private static final String TOKEN_SECRET = "orderovation";

    /**
     * 生成签名，30分钟过期
     * @param userDTO userBO
     * @return String
     */
    public static String sign(UserDTO userDTO) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            // 返回token字符串
            return JWT.create()
                    .withHeader(header)
                    .withClaim("user_id", userDTO.getUserId().toString())
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     * @param token token
     * @return boolean
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取user信息
     * @param token token
     * @return Integer user_id
     */
    public static Integer getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return Integer.parseInt(jwt.getClaim("user_id").asString());
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRequestToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
