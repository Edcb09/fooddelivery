package com.myx.fooddelivery.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY="FoodDeliverySecretKey2026";
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成 Token
     * @param userId 用户ID
     * @param role 用户角色
     * @return JWT 字符串
     */
    public static String createToken(Long userId, Integer role) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_TIME);

        return JWT.create()
                .withClaim("userId", userId) // 把用户 ID 存进 Token 里
                .withClaim("role", role)     // 把角色存进去，方便后面做权限校验
                .withIssuedAt(now)           // 签发时间
                .withExpiresAt(expireDate)   // 过期时间
                .sign(Algorithm.HMAC256(SECRET_KEY)); // 签名算法
    }

    /**
     * 解析并验证 Token
     * @param token 前端传来的 Token
     * @return 解析后的 JWT 对象，如果解析失败或过期会抛异常
     */
    public static DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build()
                .verify(token);
    }
}
