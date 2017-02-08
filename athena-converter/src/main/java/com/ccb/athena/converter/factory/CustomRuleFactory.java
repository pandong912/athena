package com.ccb.athena.converter.factory;

import com.ccb.athena.converter.components.CustomRule;
import com.ccb.athena.converter.config.entities.RuleConfig;

/**
 * 
 * 类名：CustomRuleFactory 创建人：li_dk 修改人：li_dk 创建时间：2017年1月9日 上午10:55:56
 * 修改时间：2017年1月9日 上午10:55:56 文件版本：@version 1.0.0
 *
 */
public interface CustomRuleFactory {
	CustomRule buildCustomRule(RuleConfig customRuleConfig) throws Exception;
}
