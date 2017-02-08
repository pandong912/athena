package com.ccb.athena.converter.filter;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractFilter implements Filter {

    /**
     * Does nothing
     */
    public void init() {
    }
    
    /**
     * Does nothing
     */
    public void destroy() {
    }


    /**
     * Override to handle interception
     */
    public abstract boolean doFilter(JSONObject jsonObject) throws Exception;
}