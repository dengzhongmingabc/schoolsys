package com.honorfly.schoolsys.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT类
 *
 * @author geYang
 * @date 2018-05-18
 */
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JWT {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 加密秘钥
     */
    private String secret;
    /**
     * 有效时间
     */
    private long expire;
    /**
     * 用户凭证
     */
    private String header;

    /**
     * 获取:加密秘钥
     */
    public String getSecret() {
        return secret;
    }

    /**
     * 设置:加密秘钥
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * 获取:有效期(s)
     * */
    public long getExpire() {
        return expire;
    }
    /**
     * 设置:有效期(s)
     * */
    public void setExpire(long expire) {
        this.expire = expire;
    }

    /**
     * 获取:凭证
     * */
    public String getHeader() {
        return header;
    }
    /**
     * 设置:凭证
     * */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * 生成Token签名
     * @param userId 用户ID
     * @return 签名字符串
     * @author geYang
     * @date 2018-05-18 16:35
     */
    public String generateToken(long userId) {
        System.out.println("header=" + getHeader() + ", expire=" + getExpire() + ", secret=" + getSecret());
        Date nowDate = new Date();
        // 过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(String.valueOf(userId))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, getSecret())
                .compact();
        // 注意: JDK版本高于1.8, 缺少 javax.xml.bind.DatatypeConverter jar包,编译出错
    }

    /**
     * 获取签名信息
     * @param token
     * @author geYang
     * @date 2018-05-18 16:47
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            //logger.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * 判断Token是否过期
     * @param expiration
     * @return true 过期
     * @author geYang
     * @date 2018-05-18 16:41
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public static void main(String[] args) {
        JWT jwt = new JWT();
        jwt.setExpire(10L);
        jwt.setHeader("dd");
        jwt.setSecret("_secret");
       /* String token = jwt.generateToken(123);
        System.out.println(token);*/

        Claims id = jwt.getClaimByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMiLCJpYXQiOjE1OTI3OTgzNDIsImV4cCI6MTU5Mjc5ODM1Mn0.FZLzGH48Gj-LLVBB20jo1DHEA84AbGdIhqGbiD33G8tHmnHgbEFIV1dCqUfv881QRpRgbZcC1iUS0ua4Lv6bmg");
        System.out.println(id);
    }
}
