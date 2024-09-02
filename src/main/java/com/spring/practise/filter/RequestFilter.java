package com.spring.practise.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Filter1 logs before (chain.doFilter()) starts");
        log.info("HOSTNAME :: {}",request.getLocalName());
        log.info("PORT :: {}",request.getLocalPort());
        log.info("REQUEST_ID :: {}",request.getRequestId());

        HttpServletRequest req = (HttpServletRequest) request;
        req.setAttribute("input_attr","456ABC");
        log.info("Filter1 logs before (chain.doFilter()) ends");
        log.info("Logging Request  {} : {}", req.getMethod(),req.getRequestURI());

        chain.doFilter(request, response);

        HttpServletResponse res = (HttpServletResponse) response;
        log.info("Filter1 logs after (chain.doFilter()) starts");
        log.info("Logging Response :{}",res.getContentType());
        log.info("HEADERS: {}",res.getHeaderNames());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
