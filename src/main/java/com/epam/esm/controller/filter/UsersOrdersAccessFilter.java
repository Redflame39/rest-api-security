package com.epam.esm.controller.filter;

import com.epam.esm.model.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UsersOrdersAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        Long requestedUserId = getUserIdFromRequest(servletRequest);
        if (requestedUserId != -1) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = (User) userDetails;
            if (!user.getId().equals(requestedUserId)) {
                servletResponse.sendError(403);
            }
        }
        chain.doFilter(request, response);
    }

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        uri = uri.replace(request.getContextPath(), "");
        uri = uri.replaceAll("/users/?", "");
        uri = uri.replaceAll("/orders(/\\d+)?/?", "");
        uri = uri.replaceAll("/popular_tags/?", "");
        uri = uri.replaceAll("\\?.+", "");
        return !uri.isEmpty()
                ? Long.parseLong(uri)
                : -1;
    }

}
