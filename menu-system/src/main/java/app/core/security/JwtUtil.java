package app.core.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import app.core.enums.Level;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${jwt.token.secret}")
	private String SECRET_KEY;
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractPassword(String token) {
		final Claims claims = extractAllClaims(token);
		return (String) claims.get("Password");
	}

	public int extractId(String token) {
		final Claims claims = extractAllClaims(token);
		return (int) claims.get("UserId");
	}
	
	public String extractUserType(String token) {
		final Claims claims = extractAllClaims(token);
		return (String) claims.get("Level");
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String userEmail, String password, Level level, int id) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userEmail,password,level, id);
	}
	
	private String createToken(Map<String, Object> claims, String subject, String password, Level level, int id) {
		return  Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *15))
				.claim("Password", password)
				.claim("Level", level.toString())
				.claim("UserId", id)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
	
	public Boolean validateToken(String token, String userEmail) {
		final String username = extractUsername(token);
		return (username.equals(userEmail) && !isTokenExpired(token));
	}
	
}
