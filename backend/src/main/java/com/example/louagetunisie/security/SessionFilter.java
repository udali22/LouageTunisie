package com.example.louagetunisie.security;

import com.example.louagetunisie.model.Session;
import com.example.louagetunisie.model.Utilisateur;
import com.example.louagetunisie.repository.SessionRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class SessionFilter extends OncePerRequestFilter {

    private final SessionRepository sessionRepository;

    public SessionFilter(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank()) {
            String token = authHeader.trim();

            Optional<Session> sessionOpt = sessionRepository.findByToken(token);

            if (sessionOpt.isPresent()) {
                Session session = sessionOpt.get();
                if (session.getExpiration().isAfter(java.time.LocalDateTime.now())) {
                    Utilisateur user = session.getUtilisateur();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())) // 👈 très important
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    System.out.println("Utilisateur authentifié : " + user.getEmail());
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
