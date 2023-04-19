package com.bovine.taotao.common.security;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson2.JSON;
import com.bovine.taotao.common.security.entity.Principal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT管理器
 * @author eden
 * @date 2022年10月19日 下午9:21:44
 */
public abstract class JwtTokenManager {

	public static final String SUBJECT_HEADER = "subject";
	
	private static final String SECRET_KEY = "9i!w0l^OM7rlA8zz";
	
	/**
	 * 生成token
	 * @param p
	 * @return
	 */
	public static String generate(Principal p) {
		Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + 24 * 3600 * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(JSON.toJSONString(p))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
	}
	
	/**
	 * 解析token
	 * @param token
	 * @return
	 */
	public static Claims parse(String token) {
		Pattern pattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(token);
		if(matcher.matches()) token = matcher.group("token");
		return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
	}
}
