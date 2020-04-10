package com.ljryh.client.utils.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Service
@Slf4j
public class JWTUtils {

    /* redis使用方法更改 需要确认  warning  */
//    @Resource
//    private RedisUtil redisUtil;
//
//    @Resource
//    private UserServiceImpl userServiceImpl;

    /**
     * 检验token是否正确
     *
     * @param **token**
     * @return
     */
    public static boolean verify(String token, String sk) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(sk);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 利用jwt生成token信息.
     *
     * @param claims 数据声明（Claim）其实就是一个Map，比如我们想放入用户名，
     *               可以简单的创建一个Map然后put进去
     * @param secret 用于进行签名的秘钥
     * @return
     * @throws Exception
     */
    public static String generateToken(Map<String, Object> claims, String secret) throws Exception {
        DESCoder desCoder = new DESCoder();
        Key key = desCoder.toKey(secret);
        //设置过期时间为10分钟
        Date ecpiration = new Date(System.currentTimeMillis() + 600000L);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(ecpiration)
                .signWith(SignatureAlgorithm.HS512, key) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
    }

    /**
     * 利用jwt解析token信息.
     *
     * @param token  要解析的token信息
     * @param secret 用于进行签名的秘钥
     * @return
     * @throws Exception
     */
    public static Optional<Claims> getClaimsFromToken(String token, String secret) throws Exception {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * 验证token是否过期
     *
     * @param tooken 要解析的token信息
     * @param secret 用于进行签名的秘钥
     * @return true 表示过期，false表示不过期，如果没有设置过期时间，则也不认为过期
     * @throws Exception
     */
    public static boolean isExpired(String tooken, String secret) throws Exception {
        Optional<Claims> claims = getClaimsFromToken(tooken, secret);
        if (claims.isPresent()) {
            Date expiration = claims.get().getExpiration();
            return expiration.before(new Date());
        }
        return false;
    }

    /**
     * 获取tooken中的参数值
     *
     * @param token  要解析的token信息
     * @param secret 用于进行签名的秘钥
     * @return
     * @throws Exception
     */
    public static Map<String, Object> extractInfo(String token, String secret) throws Exception {
        Optional<Claims> claims = getClaimsFromToken(token, secret);
        if (claims.isPresent()) {
            Map<String, Object> info = new HashMap<String, Object>();
            Set<String> keySet = claims.get().keySet();
            //通过迭代，提取token中的参数信息
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = claims.get().get(key);
                info.put(key, value);

            }
            return info;
        }
        return null;
    }


    /**
     * 由字符串生成加密key
     *
     * @return
     */
    private static SecretKey generalKey(String key) {
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(key);

        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey SecretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");

        return SecretKey;
    }

    /**
     * 解密+验证token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public Object parseJWT(String token,boolean judge) throws Exception {
        // 解析请求的token（获取：公钥与加密ID）
        DecodedJWT DecodedJWT = null;
        try {
            DecodedJWT = JWT.decode(token);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return false;
        }
        String publicKey = DecodedJWT.getIssuer();
        String id = DecodedJWT.getId();
        // 本地调试
//        String publicKey = "a98f22ff358544ba94945b7f541fbd80";
        // 判断公钥是否存在
        if (publicKey == null) {
            log.error("[JWTUtils]token ,userName=" + publicKey);
            return false;
        }
        // redis验证token是否重复使用
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        Object redisJudge = redisUtil.get(id);
//        if (redisJudge != null) {
//            log.error("[JWTUtils]token redisJudge ,userName=" + publicKey + "id," + id);
//            return false;
//        }
        // 数据库验证公钥是否存在 TODO
        JWTBean jwt = null;
        try {
//            jwt = userServiceImpl.getPrivateKey(publicKey);
        } catch (Exception e) {
            log.error("[JWTUtils]token jwt error,userName=" + publicKey, e);
        }
        if (jwt == null) {
            log.error("[JWTUtils]token jwt null,userName=" + publicKey);
            return false;
        }
        try {
            // 验证token是否在有效期内(不在有效期内进入异常，然后返回失败)
            SecretKey key = generalKey(jwt.getPassword());  //签名秘钥，和生成的签名的秘钥一模一样
            Claims c = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)                 //设置签名的秘钥
                    .parseClaimsJws(token).getBody();     //设置需要解析的jwt
            // 获取有效时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(jwt.getCreateTime());
            calendar.add(Calendar.DATE, jwt.getEffectiveTime());
            Date effectiveTime = calendar.getTime();
            // 获取签发时间
            Date issuedAt = c.getIssuedAt();
            // 判断是否在有效时间内
            if (effectiveTime.getTime() < issuedAt.getTime()) {
                log.error("[JWTUtils]token illegal,userName=" + publicKey);
                return false;
            }
            // 将已使用过得token进行redis存储
//            if(judge){
//                redisUtil.set(id, token, 3600L);
//            }
            return publicKey;
        } catch (Exception e) {
            log.error("[JWTUtils]token check error,userName=" + publicKey, e);
            return false;
        }
    }

    public Object parseJWTNoRedis(String token) throws Exception {
        // 解析请求的token（获取：公钥与加密ID）
        DecodedJWT DecodedJWT = JWT.decode(token);
        String publicKey = DecodedJWT.getIssuer();
        String id = DecodedJWT.getId();
        // 本地调试
//        String publicKey = "a98f22ff358544ba94945b7f541fbd80";
        // 判断公钥是否存在
        if (publicKey == null) {
            log.error("[JWTUtils]token ,userName=" + publicKey);
            return "[JWTUtils]token ,userName=" + publicKey;
        }
        // 数据库验证公钥是否存在 TODO
        JWTBean jwt = null;
        try {
//            jwt = userServiceImpl.getPrivateKey(publicKey);
        } catch (Exception e) {
            log.error("[JWTUtils]token jwt error,userName=" + publicKey, e);
        }
        if (jwt == null) {
            log.error("[JWTUtils]token jwt null,userName=" + publicKey);
            return "[JWTUtils]token jwt null,userName=" + publicKey;
        }
        try {
            // 验证token是否在有效期内(不在有效期内进入异常，然后返回失败)
            SecretKey key = generalKey(jwt.getPassword());  //签名秘钥，和生成的签名的秘钥一模一样
            Claims c = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)                 //设置签名的秘钥
                    .parseClaimsJws(token).getBody();     //设置需要解析的jwt
            // 获取有效时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(jwt.getCreateTime());
            calendar.add(Calendar.DATE, jwt.getEffectiveTime());
            Date effectiveTime = calendar.getTime();
            // 获取签发时间
            Date issuedAt = c.getIssuedAt();
            // 判断是否在有效时间内
            if (effectiveTime.getTime() < issuedAt.getTime()) {
                log.error("[JWTUtils]token illegal,userName=" + publicKey);
                return "[JWTUtils]token illegal,userName=" + publicKey;
            }
            return true;
        } catch (Exception e) {
            log.error("[JWTUtils]token check error,userName=" + publicKey, e);
            return "[JWTUtils]token check error Exception,userName=" + publicKey;
        }
    }

}
