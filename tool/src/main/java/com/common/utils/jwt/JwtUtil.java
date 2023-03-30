package com.common.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * jwt
 */
public class JwtUtil {

    private static final String encodeSecretKey = "XX#$%()(#*!()!KL<><MQLMNQNQJQKsdfkjsdrow32234545fdf>?N<:{LWPW";

    /**
     * token过期时间
     */
//    private static final long EXPIRE_TIME = 1000 * 10 * 60 * 24 * 7;
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 365 * 3;

    /**
     * 生成token的字段
     */
    private static final String field = "userId";

    /**
     * 生成token
     * @return
     */
    public static String createToken(String userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(encodeSecretKey);
            return JWT.create()
                    .withExpiresAt(date)
                    .withClaim(field, userId)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验token是否失效
     * @param token
     * @return
     */
    public static boolean checkToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(encodeSecretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token信息
     * @param token
     * @return
     */
    public static String getTokenMsg(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(field).asString();
        } catch (Exception e) {
            return null;
        }
    }
}
