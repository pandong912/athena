package com.ccb.athena.converter.components;

import com.alibaba.fastjson.JSONObject;

/**
 * 
  * 类名：Converter
  * 创建人：li_dk
  * 修改人：li_dk
  * 创建时间：2017年1月6日 下午3:45:46
  * 修改时间：2017年1月6日 下午3:45:46
  * 文件版本：@version 1.0.0
  *
 */
public interface IConverter {
    String getConverterResult(JSONObject jsonObject,int index);
}
