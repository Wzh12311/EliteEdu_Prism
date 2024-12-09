package com.example.eliteedu_prism.EliteEdu_Prism.utils;

import com.example.eliteedu_prism.EliteEdu_Prism.pojo.Result;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public class GetTokenUserId {

    public static String getUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
//        String token = request.getHeader("token");
        // 解析 JWT Token，获取用户 ID
        String jwt = token.substring(7); // 去掉 'Bearer ' 前缀
        Claims claims = JwtUtils.parseJwt(jwt); // 从 JWT 中获取用户 ID
//        Claims claims = JwtUtils.parseJwt(token); // 从 JWT 中获取用户 ID
        return claims.get("id").toString();
    }
}
