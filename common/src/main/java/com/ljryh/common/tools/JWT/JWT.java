package com.ljryh.common.tools.JWT;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class JWT {
    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey(String jwtSecret) {
        // 本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(jwtSecret);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建jwt
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public String createJWT(String id, String issuer, String subject, long ttlMillis,String privateKey) throws Exception {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put("username", "admin");
        claims.put("password", "admin");

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey(privateKey);

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id)                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           // iat: jwt的签发时间
                .setIssuer(issuer)          // issuer：jwt签发人
                .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        log.info("<------------------用户：{}，签发token------------------->",issuer);
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public Map<String, Object> parseJWT(String jwt,String privateKey) throws Exception {
        Map<String, Object> map = new ConcurrentHashMap<>();
        try {
            SecretKey key = generalKey(privateKey);  //签名秘钥，和生成的签名的秘钥一模一样
            Claims claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)                 //设置签名的秘钥
                    .parseClaimsJws(jwt).getBody();     //设置需要解析的jwt
            map.put("result",true);
            map.put("data",claims);
            return map;
        } catch (Exception e) {
            map.put("result", false);
            return map;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {


//        Map<String,Object> map = new ConcurrentHashMap<>();
//
//        map.put("q","在微信智言与微信智聆两大技术的支持下，微信AI团队推出了“微信对话开放平台”和“腾讯小微”智能硬件两大核心产品。微信支付团队最新发布的“微信青蛙Pro”在现场设置了体验区，让大家感受AI认脸的本事。");
//
//        String s = Jwts.builder()
//                .claim("userid", UUID.randomUUID().toString())
//                .signWith(SignatureAlgorithm.HS256, "FG1vMoYBCjtc3s5osSN2SBDUy2iTrECCBKs3ln6N6wy".getBytes("UTF-8"))
//                .compact();
//
//        System.out.println(s);
//
//        Claims claims = Jwts.parser()
//                .setSigningKey("FG1vMoYBCjtc3s5osSN2SBDUy2iTrECCBKs3ln6N6wy".getBytes("UTF-8"))
//                .parseClaimsJws(s).getBody();
//        System.out.println(claims);

//        Map<String,Object> map = new ConcurrentHashMap<>();
//
//        map.put("q","在微信智言与微信智聆两大技术的支持下，微信AI团队推出了“微信对话开放平台”和“腾讯小微”智能硬件两大核心产品。微信支付团队最新发布的“微信青蛙Pro”在现场设置了体验区，让大家感受AI认脸的本事。");
//
//        String s = Jwts.builder()
//                .claim("data", map)
//                .claim("uid", UUID.randomUUID().toString())
//                .signWith(SignatureAlgorithm.HS256, "Cu4PggtnhQfFq6m7GxKwDS3E8TwGqwkXXbgFH7k54k3".getBytes("UTF-8"))
//                .compact();
//
//        System.out.println(s);
//
//        Claims claims = Jwts.parser()
//                .setSigningKey("Cu4PggtnhQfFq6m7GxKwDS3E8TwGqwkXXbgFH7k54k3".getBytes("UTF-8"))
//                .parseClaimsJws(s).getBody();
//        System.out.println(claims);




//        String s = Jwts.builder()
//                .setSubject("1234567890")
//                .setId("74dd18d3-40f2-419d-b54e-26c49335959e")
//                .setIssuedAt(Date.from(Instant.ofEpochSecond(1589265811)))
//                .setExpiration(Date.from(Instant.ofEpochSecond(1589269411)))
//                .claim("name", "John Doe")
//                .claim("admin", true)
//                .signWith(SignatureAlgorithm.HS256, "Cu4PggtnhQfFq6m7GxKwDS3E8TwGqwkXXbgFH7k54k3".getBytes("UTF-8"))
//                .compact();
//
//        System.out.println(s);


        Map<String,String> map = new ConcurrentHashMap<>();
        map.put("name","knight");
        map.put("id","001");
        String subject = new Gson().toJson(map);

        String jwtId = Constant.JWT_ID;

        try {
            JWT util = new JWT();
            String jwt = util.createJWT(jwtId, "Anson", subject, Constant.JWT_TTL,"Anson");
            System.out.println("JWT：" + jwt);

            System.out.println("\n解密\n");
//            String jwt= "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJzaW5vd2VsIiwiZXhwIjoxNTcyOTQyMjk2LCJpYXQiOjE1NzI5Mzg2OTYsImp0aSI6IjViYTQ3MjE3LTQxMDMtNDQ3MS1iNmMwLWU3ODhlMTRhYzIxYyIsInVzZXJuYW1lIjoic2lub3dlbCJ9.qxIJw8s7whGqQW2soyRfmELeISOOiGFQa9uMxPDCHKA";

            JWT JWT = new JWT();

//            Map<String, Object> jwtMap = JWT.parseJWT(jwt);



            DecodedJWT DecodedJWT = com.auth0.jwt.JWT.decode(jwt);
            String str = DecodedJWT.getClaim("username").asString();
            String publicKey = DecodedJWT.getIssuer();
            System.out.println(str);

            System.out.println(publicKey);

            Map<String, Object> result = util.parseJWT(jwt,"Anson");
            if(Boolean.valueOf(result.get("result").toString())){
                Claims c = (Claims) result.get("data");
                System.out.println(c.getId());
                System.out.println(c.getIssuedAt());
                System.out.println(c.getSubject());
                System.out.println(c.getIssuer());
                System.out.println(c.get("username", String.class));
                System.out.println(c.get("password", String.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
