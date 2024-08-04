package com.pms.common.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT Util Class
 * @author Sn0w_15
 * @since 27 Jul 2024
 */
@Service
public class JwtUtil {

    // TOKEN Valid Time
    public static final Long JWT_TTL = 60 * 60 * 1000L; // Token Valid Time One Hour

    // Configure JWT KEY
    public static final String JWT_KEY = "Sn0w15";

    // Generate UUID Tag
    public static String getUUID() {
        String token = UUID.randomUUID().toString().replaceAll("-","");
        return token;
    }

    /**
     * Generate JWT
     * @param subject Data needed to be store (JSON Format)
     * @param ttlMillis Token Valid Time
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * Generate JWT
     * @param id
     * @param subject Data needed to be store (JSON Format)
     * @param ttlMillis Token Valid Time
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }


    /**
     * Logic and ServiceImpl for JWT Token Generate
     * @param subject
     * @param ttlMillis
     * @param uuid
     * @return
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis(); // Get Current Time
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid) // Unique ID
                .setSubject(subject) // Data (JSON Format)
                .setIssuer("PMS") // Issuer
                .setIssuedAt(now) // Issue Date
                .signWith(signatureAlgorithm, secretKey) // Encrypt Algorithm Methods N SecretKey
                .setExpiration(expDate); // Expired Date
    }

    /**
     * Generate Encrypted SecretKey
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * Decode
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims getClaimsFromToken(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * Refresh Token
     * @param token
     * @return New Token
     */
    public static String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(Claims.ISSUED_AT, new Date());
            String userJson = claims.getSubject();
            refreshedToken = createJWT(userJson, JwtUtil.JWT_TTL);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }


}
