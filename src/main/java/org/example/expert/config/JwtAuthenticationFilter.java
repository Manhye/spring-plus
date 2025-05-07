// package org.example.expert.config;
//
// import java.io.IOException;
// import java.util.List;
//
// import org.example.expert.domain.common.dto.AuthUser;
// import org.example.expert.domain.user.enums.UserRole;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import io.jsonwebtoken.Claims;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
// 	private final JwtUtil jwtUtil;
//
// 	@Override
// 	protected void doFilterInternal(
// 		HttpServletRequest request,
// 		HttpServletResponse response,
// 		FilterChain filterChain
// 	) throws ServletException, IOException {
//
// 		String bearerToken = request.getHeader("Authorization");
// 		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
// 			String token = jwtUtil.substringToken(bearerToken);
// 			try {
// 				Claims claims = jwtUtil.extractClaims(token);
// 				if (claims != null) {
// 					Long userId = Long.parseLong(claims.getSubject());
// 					String email = claims.get("email", String.class);
// 					String nickname = claims.get("nickname", String.class);
// 					String userRole = claims.get("userRole", String.class);
//
// 					List<GrantedAuthority> authorities = List.of(
// 						new SimpleGrantedAuthority("ROLE_" + userRole)
// 					);
//
// 					AuthUser authUser = new AuthUser(userId, email, UserRole.of(userRole), nickname);
//
// 					UsernamePasswordAuthenticationToken authentication =
// 						new UsernamePasswordAuthenticationToken(authUser, null, authorities);
//
// 					SecurityContextHolder.getContext().setAuthentication(authentication);
// 				}
// 			} catch (Exception e) {
// 				log.error("JWT authentication error", e);
// 				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 인증 실패");
// 				return;
// 			}
// 		}
//
// 		filterChain.doFilter(request, response);
// 	}
// }
