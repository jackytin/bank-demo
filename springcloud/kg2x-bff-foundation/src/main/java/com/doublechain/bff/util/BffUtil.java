package com.doublechain.bff.util;

import com.doublechaintech.iamservice.Keys;
import com.doublechaintech.util.Util;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

public class BffUtil {

  public static Jwt parseJwtToken(String header) {
    try {
      if (header == null) {
        return null;
      }
      if (header.startsWith("Bearer ")) {
        header = header.substring("Bearer ".length());
      }
      Jwt rst =
          Jwts.parser()
              .setSigningKey(Keys.DEFAULT_SIGN_KEY.getBytes(StandardCharsets.UTF_8))
              .requireIssuer(Keys.DEFAULT_ISSUER)
              .parse(header);
      return rst;
    } catch (Throwable t) {
      //            t.printStackTrace(System.err);
      return null;
    }
  }

  public static String getRemoteIp(HttpServletRequest request) {
    Util.ding(request.getClass().getName());
    return "123";
  }
}
