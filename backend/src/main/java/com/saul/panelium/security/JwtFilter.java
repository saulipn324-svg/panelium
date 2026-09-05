package com.saul.panelium.security;

import jakarta.servlet.*; import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private final JwtService jwt; public JwtFilter(JwtService jwt){this.jwt=jwt;}
  @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
    String header=req.getHeader("Authorization");
    if(header!=null&&header.startsWith("Bearer "))try{var claims=jwt.parse(header.substring(7));String role=claims.get("role",String.class);var auth=new UsernamePasswordAuthenticationToken(claims.getSubject(),null,List.of(new SimpleGrantedAuthority("ROLE_"+role)));auth.setDetails(claims);SecurityContextHolder.getContext().setAuthentication(auth);}catch(Exception ignored){}
    chain.doFilter(req,res);
  }
}
