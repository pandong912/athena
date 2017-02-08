package com.ccb.athena.converter.factory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;

import com.ccb.athena.converter.components.Request;
import com.ccb.athena.converter.components.Segment;
import com.ccb.athena.converter.config.entities.RequestConfig;
import com.ccb.athena.converter.config.entities.SegmentMapping;

/**
 * 
 * 类名：DefaultRequestFactory 创建人：li_dk 修改人：li_dk 创建时间：2017年1月6日 下午6:04:02
 * 修改时间：2017年1月6日 下午6:04:02 文件版本：@version 1.0.0
 *
 */
public class DefaultRequestFactory implements RequestFactory {
	Document reqDocument = DocumentHelper.createDocument();

	@Override
	public Request buildRequest(RequestConfig requestConfig) {
		Request request = new Request();
		boolean convert = requestConfig.isConvert();
		List<SegmentMapping> reqSegmentMapping = requestConfig.getSegments();

		request.setIsconvert(convert);
		LinkedList<Segment> segments = new LinkedList<Segment>();

		if (convert) {
			Map<String, Element> map = new HashMap<String, Element>();
			for (int i = 0; i < reqSegmentMapping.size(); i++) {

				Segment segment = reqSegmentMapping.get(i).getSegment();
				segments.add(segment);
				String rootPath = segment.getRoot();
				Element rootNode = segment.getRootElement();
				request.setSegments(segments);
				getDoc(map, rootPath, rootNode);
			}
			request.setReqDocument(reqDocument);
		}

		return request;
	}

	/**
	 * 该方法只需执行一次 getDoc(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param map1
	 * @param doc
	 * @param path
	 * @param nameNode
	 * @return 创建人：li_dk 修改人：li_dk 创建时间：2016年12月23日-下午4:01:51
	 *         修改时间：2016年12月23日-下午4:01:51 type:Element
	 * @exception:
	 * 
	 * @since 1.0.0
	 */
	private Element getDoc(Map<String, Element> map1, String path, Element nameNode) {
		if (map1.containsKey(path))
			return map1.get(path);
		int lastIndex = path.lastIndexOf(".");
		if (lastIndex == -1) {
			Element e = reqDocument.addElement(path);
			map1.put(path, e);
			return e;
		}
		String parentStr = path.substring(0, lastIndex);
		Element parentElement = getDoc(map1, parentStr, nameNode);

		Element e = new BaseElement(path.substring(lastIndex + 1, path.length()));
		if (e.getName().equals(nameNode.getName())) {
			parentElement.add(nameNode);
		} else {
			parentElement.add(e);
		}
		map1.put(path, e);
		return e;
	}

}
