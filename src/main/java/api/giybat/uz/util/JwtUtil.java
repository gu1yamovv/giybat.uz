package api.giybat.uz.util;

import api.giybat.uz.dto.JwtDTO;
import api.giybat.uz.enums.ProfileRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    public static String encode(Integer id) {


        return Jwts
                .builder()
                .subject(String.valueOf(id))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .signWith(getSignInKey())
                .compact();
    }

    public static String encode(String username,Integer id, List<ProfileRole> roleList) {
        String strRoles = roleList.stream().map(Enum::name).collect(Collectors.joining(","));

        Map<String, String> claims = new HashMap<>();
        claims.put("roles", strRoles);
        claims.put("id",String.valueOf(id));

        return Jwts
                .builder()
                .subject(username)
                .setClaims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String username = claims.getSubject();
        Integer id = Integer.valueOf((String) claims.get("id"));
        String strRole = (String) claims.get("roles");
       List<ProfileRole> roleList = Arrays.stream(strRole.split(","))
               .map(ProfileRole::valueOf)
               .toList();

        return new JwtDTO(username,id,roleList);
        }




    public static Integer decodeRegVerToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Integer.valueOf(claims.getSubject());
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
