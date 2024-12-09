package com.example.eliteedu_prism.EliteEdu_Prism.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // 生成JWT// 生成JWT
    public static String generateJwt(Map<String,Object> claims) {

        String jwt = Jwts.builder().
                addClaims(claims)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, "Bjtu")
                .setExpiration(new Date(System.currentTimeMillis() + 43200000L))
                .compact();


        return jwt;


    }

    public static Claims parseJwt(String jwt) {

        return Jwts.parser()
                .setSigningKey("Bjtu")
                .parseClaimsJws(jwt)
                .getBody();
    }

}
