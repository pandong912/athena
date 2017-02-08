package com.ccb.athena.executor.scheduler.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
	
	public static Map<String, String> xml2Map(String message){
		Map<String, String> data = new HashMap<String, String>();
		Document doc = null;
		try {
			 doc = DocumentHelper.parseText(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		 
		Element root = doc.getRootElement();
		element2Map(root,data);
		return data;
	}
	private static Map<String, Integer> index = new HashMap<String, Integer>();
	private static void element2Map(Element ele,Map<String, String> data){
		if(ele == null){
			return;
		}
		String name = ele.getName();
		if(index.containsKey(name)){
			index.put(name, index.get(name)+1);
		}
		else{
			index.put(name, 0);
		}
		List<Element> elements = ele.elements();
		if(elements!=null&&elements.size()>0){
			for (Element element : elements) {
				element2Map(element,data);
			}
		}
		else{
			String path = ele.getPath();
			String value = ele.getText();
			String newPath = replacePath(path);
			data.put(newPath, value);
			
		}
		
	}
	
	private static String replacePath(String path){
		String[] pathArray = path.split("/");
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		for (int i = 0; i < pathArray.length; i++) {
			String s= pathArray[i];
			if(s==null||s.equals("")){
				continue;
			}
			if(index.containsKey(s)){
				int nodeIndex = index.get(s);
				if(nodeIndex>0){
					s= s+"["+(nodeIndex+1)+"]";
				}
				
			}
			
			sb.append(s);
			if(i<pathArray.length-1){
				sb.append("/");
			}
		}
		return sb.toString();
	}
	
	
	
	
	private static Map<String, String> put(Map<String, String> data,String key,String value){
		Map<String, String> newMap = data;
		Set<String> keySet = newMap.keySet();
		int newIndex = -1;
		String newKey = null;
		for (String string : keySet) {
			if(string.startsWith(key)){
				String indexStr = StringUtils.substringBetween(string, "[", "]");
				if(StringUtils.isEmpty(indexStr)){
					newIndex = 1;
					break;
				}
				else{
					int index = Integer.valueOf(indexStr);
					if(index>newIndex){
						newIndex = index;
					}
				}
			}
		}
		if(newIndex==1){
			newMap.put(key+"["+newIndex+"]", newMap.remove(key));
			newIndex++;
			newKey = key+"["+newIndex+"]";
		}
		else if(newIndex>1){
			newIndex++;
			newKey = key+"["+newIndex+"]";;
		}
		else{
			newKey = key;
		}
		newMap.put(newKey, value);
		return new HashMap<String, String>(newMap);
	}
	
/*	public static void main(String[] args) {
		File file = new File("C:/Users/zhouxuke/Desktop/test.xml");
		try {
			Document doc = DocumentHelper.createDocument();
			String message = FileUtils.readFileToString(file,"utf-8");
			Map<String, String> map = xml2Map(message);
			System.out.println(map.size());
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				System.out.println(key+"======"+map.get(key));
				//String xpath = StringUtils.substringBefore(key, "[");
				//System.out.println(xpath);    
				////Element ele = DocumentHelper.makeElement(doc, key);
				//ele.setText("             ");
			}
			//System.out.println(doc.asXML().replaceAll("[d]",""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}
