package com.ccb.athena.converter.factory;

import com.ccb.athena.converter.components.Mapping;
import com.ccb.athena.converter.config.entities.MappingConfig;

/**
   * 
   * 类名：MappingFactory
   * 创建人：li_dk
   * 修改人：li_dk
   * 创建时间：2017年1月8日 下午5:40:11
   * 修改时间：2017年1月8日 下午5:40:11
   * 文件版本：@version 1.0.0
   *
  */
public interface MappingFactory {
    Mapping buildMapping(MappingConfig mappingConfig);
}
