package com.chilleric.franchise_sys.jwt;

import static java.util.Map.entry;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.exception.UnauthorizedException;
import com.chilleric.franchise_sys.log.AppLogger;
import com.chilleric.franchise_sys.log.LoggerFactory;
import com.chilleric.franchise_sys.log.LoggerType;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtValidation {
  @Value("${spring.key.jwt}")
  protected String JWT_SECRET;

  protected AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

  public String generateToken(String userId) {
    Date now = new Date();
    long JWT_EXPIRATION = 7 * 24 * 60 * 60 * 1000L; // expired in 7 days since login
    Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
    try {
      Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
      String token = JWT
        .create()
        .withPayload(Map.ofEntries(entry("userId", userId)))
        .withExpiresAt(expiryDate)
        .sign(algorithm);
      return token;
    } catch (JWTCreationException exception) {
      return "";
      // Invalid Signing configuration / Couldn't convert Claims.
    }
  }

  public String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    // Kiểm tra xem header Authorization có chứa thông tin jwt không
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public String getUserIdFromJwt(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT decodedJwt = verifier.verify(token);
      Map<String, Claim> claims = decodedJwt.getClaims();
      if (claims == null) {
        throw new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED);
      } else {
        if (!claims.containsKey("userId")) {
          throw new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED);
        }
        String userId = claims.get("userId").toString();
        return userId.substring(1, userId.length() - 1);
      }
    } catch (JWTVerificationException exception) {
      APP_LOGGER.error(exception.getMessage());
      throw new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED);
    }
  }

  public boolean isValid(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET);
      JWTVerifier verifier = JWT.require(algorithm).build();
      verifier.verify(token);
      return true;
    } catch (JWTVerificationException exception) {
      APP_LOGGER.error(exception.getMessage());
      return false;
    }
  }
}
