package com.example.boardApi.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/** FORM Controller 필터
 *  인가할때 호출됌
 * */
@Slf4j
public class ReqeustVaildationBeforeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("ReqeustVaildationBeforeFilter[doFilter]");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
