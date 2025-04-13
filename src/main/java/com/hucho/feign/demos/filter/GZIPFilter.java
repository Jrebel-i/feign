package com.hucho.feign.demos.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: GZIPFilter
 * @Author zhangjin
 * @Date 2022/3/26 0:36
 * @Description:
 */
@Slf4j
@Component
public class GZIPFilter implements Filter {

    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String GZIP = "gzip";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String contentEncoding = httpServletRequest.getHeader(CONTENT_ENCODING);
        String acceptEncoding = httpServletRequest.getHeader(ACCEPT_ENCODING);

        if (GZIP.equals(contentEncoding)) {
            log.info("需要解压[{}]请求body数据 ", httpServletRequest.getRequestURI());

            httpServletRequest = new UnZIPRequestWrapper(httpServletRequest);
        }

        if (GZIP.equals(acceptEncoding)) {
            log.info("需要压缩[{}]响应body数据 ", httpServletRequest.getRequestURI());
            GZIPResponseWrapper gzipResponseWrapper = new GZIPResponseWrapper(httpServletResponse);
            filterChain.doFilter(httpServletRequest, gzipResponseWrapper);
            gzipResponseWrapper.resetResponse();
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

        log.info("耗时：{}ms", System.currentTimeMillis() - start);
    }
}
