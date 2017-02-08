package com.ccb.athena.converter.filter;

import com.alibaba.fastjson.JSONObject;

/**
 * 過濾器接口 类名：FilterAction 创建人：li_dk 修改人：li_dk 创建时间：2017年1月4日 下午5:43:26
 * 修改时间：2017年1月4日 下午5:43:26 文件版本：@version 1.0.0
 *
 */
public interface Filter {
	
    void init();
    
    void destroy();
	
	boolean doFilter(JSONObject jsonObject) throws Exception;
}
