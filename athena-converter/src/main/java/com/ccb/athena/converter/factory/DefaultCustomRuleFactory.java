package com.ccb.athena.converter.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ccb.athena.converter.components.CustomRule;
import com.ccb.athena.converter.components.MappingParam;
import com.ccb.athena.converter.config.entities.ParamConfig;
import com.ccb.athena.converter.config.entities.RuleConfig;

/**
 * 
 * 类名：DefaultCustomRuleFactory 创建人：li_dk 修改人：li_dk 创建时间：2017年1月23日 下午12:40:08
 * 修改时间：2017年1月23日 下午12:40:08 文件版本：@version 1.0.0
 *
 */

public class DefaultCustomRuleFactory implements CustomRuleFactory {

	@Override
	public CustomRule buildCustomRule(RuleConfig customRuleConfig) throws Exception {
		CustomRule customRule = new CustomRule();
		String id = customRuleConfig.getName();
		String clazzStr = customRuleConfig.getClassName();
		String methodStr = customRuleConfig.getMethodName();
		List<ParamConfig> params = customRuleConfig.getParams();
		List<MappingParam> mParams = new ArrayList<>();
		for (ParamConfig paramConfig : params) {
			MappingParam param = new MappingParam();
			String value = paramConfig.getDefaultValue();
			if (value.startsWith("$")) {
				value = StringUtils.substringAfter(value, "$");
				param.setArray(true);
			} else {
				param.setArray(false);
			}
			mParams.add(param);
		}
		Class<?> clazz = getClass(clazzStr);
		Method method = getMethod(clazz, methodStr, params);
		customRule.setId(id);
		customRule.setClazz(clazz);
		customRule.setMethod(method);
		customRule.setParams(mParams);
		return customRule;
	}

	/**
	 * 
	 * getClass(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param className
	 * @return 创建人：li_dk 修改人：li_dk 创建时间：2017年1月9日-上午11:09:33
	 *         修改时间：2017年1月9日-上午11:09:33 type:Class<?>
	 * @exception:
	 * @since 1.0.0
	 */
	public Class<?> getClass(String className) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return clazz;
	}

	/**
	 * 
	 * getMethod(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @return 创建人：li_dk 修改人：li_dk 创建时间：2017年1月9日-上午11:11:38
	 *         修改时间：2017年1月9日-上午11:11:38 type:Method
	 * @exception:
	 * @since 1.0.0
	 */
	public Method getMethod(Class<?> clazz, String methodStr, List<ParamConfig> params) {
		Method method = null;
		try {
			Class[] cla = new Class[params.size()];
			for (int i = 0; i < params.size(); i++) {
				cla[i] = String.class;
			}
			method = clazz.getMethod(methodStr, cla);
		} catch (Exception e) {
		}
		return method;
	}
}
