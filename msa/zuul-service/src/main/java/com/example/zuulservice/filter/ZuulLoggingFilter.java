package com.example.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class ZuulLoggingFilter extends ZuulFilter {

    /**
     * 실제 어떤 동작을 하는지 지정.
     * 사용자의 요청이 들어올때마다 동작.
     */
    @Override
    public Object run() throws ZuulException {

        log.info("************ printing logs: ");

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        log.info("************ printing logs: {}", request.getRequestURI());
        return null;
    }

    /**
     * 사전필터인지, 사후필터인지 결정.
     * 사전필터이므로 pre
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 여러개의 필터가 있을 경우 순서 결정.
     * 한개의 필터만 있으니 1.
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 필터로 쓰거나 쓰지않거나 옵션을 지정.
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }
}
