package com.spring.practise.filter;

import com.spring.practise.exception.ExceptionResponse;
import com.spring.practise.exception.InvalidRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * HTTP Request Servlet Filter for handle exception in filter layer
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidRequestException e) {
            ExceptionResponse apiError = null;
            String errorCode = e.getCode();
            if (errorCode.equals("400")) {
                apiError = ExceptionResponse.builder()
                        .errorMessage("message1")
                        .errorCode("1000")
                        .timeStamp(LocalDateTime.now())
                        .build();
            } else if (errorCode.equals("404")) {
                apiError = ExceptionResponse.builder()
                        .errorMessage("message2")
                        .errorCode("1001")
                        .timeStamp(LocalDateTime.now())
                        .build();
            }

            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(convertObjectToJson(apiError));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}