//package com.tweets.configuration.security;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class EntryPointConfig implements AuthenticationEntryPoint {
//
//    @Override
//    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        if(subDomainMatchesUri(httpServletRequest.getRequestURI(), "/api/")){
//            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You're not authorized to see this");
//        } else {
//            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            httpServletResponse.sendRedirect("/");
//
//        }
//    }
//
//    private Boolean subDomainMatchesUri(String uri, String subDomain){
//        return uri.substring(0,5).equals(subDomain);
//    }
//}
