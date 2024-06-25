package com.trace.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import javax.servlet.http.HttpServletRequest;

public class SleuthCommonRequestFilter extends CommonsRequestLoggingFilter {
    @Autowired
    private Tracer tracer;
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        super.beforeRequest(request, message);
        logger.info(message);
    }
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        super.afterRequest(request, message);
        tracer.currentSpan().tag("request info", message);
        logger.info(message);
    }
    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }
}
