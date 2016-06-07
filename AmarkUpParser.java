package gist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Element{
	private String name;
	private Map<String, String> property;
	private List<Element> subElement;
	private String content;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getProperty() {
		return property;
	}
	public void setProperty(Map<String, String> property) {
		this.property = property;
	}
	public List<Element> getSubElement() {
		return subElement;
	}
	public void setSubElement(List<Element> subElement) {
		this.subElement = subElement;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}

/**
 * @author ianate
 * a regular experssion exercise
 * no robustness
 */
public class AmarkUpParser {

	private static final Pattern SHORT_TAG = Pattern.compile("\\G(<(\\w+)([^(/>)]*)/>)");//$1 whole tag;$2 name;$3 options;$4 rest;
	/*private static final Pattern tag = Pattern.compile("\\G<((\\w+)([^>]*)>(.*)</\\1>)");
	private static final Pattern multiTag = Pattern.compile("\\G<(\\w+)([^>]*)>[^(</\\1>|/>)]*(</\\1>)(.*)</\\1>");
	private static final Pattern nestTag = Pattern.compile("\\G<(\\w+)([^>]*)>[^(</\\1>)]*<(\\w+)([^>]*)>.*</\\1>");*/
	private static final Pattern TAG = Pattern.compile("<(\\w+)>(.*?)</\\s*\\1>");
	@Deprecated
	private static final Pattern exception = Pattern.compile("</\\s*br>");//mistake.a <br /> matches short tag
	
	private boolean isNullOrEmpty(String str) {return str == null || "".equals(str.trim());}
	
	public Element parseOneTag(String text){
		if(isNullOrEmpty(text)) return null;
		Element element = null;
		Matcher shortMatcher = SHORT_TAG.matcher(text);
		Matcher tagMatcher =  TAG.matcher(text);
		if(shortMatcher.matches()){//short tag; no property; no subElements
			String name = shortMatcher.group(2);
			if(!isNullOrEmpty(name)){//illegal if no name
				element = new Element();
				element.setName(name);
				String property = shortMatcher.group(3).trim();
				if(!isNullOrEmpty(property)){
					Map<String, String> map = parseProperty(property);
					element.setProperty(map);
				}
			}
		}else if(tagMatcher.matches()){
			String tagName = tagMatcher.group(1);
			String content = tagMatcher.group(2);//.*?		not-null
			int openTags =  countAppearance(content, "<" + tagName + ">");
			int shortCloseTags;//TODO
			if(openTags == 0){//one
				
			}else if(openTags - shortCloseTags == 0){//nest but all closed
				
			}else{//nest and ask more to be a tag. (open and close mismatch)
				
			}
		}
		
		return element;
	}
	
	private Map<String, String> parseProperty(String str){
		String[] properties = str.split("\\s+");
		Map<String, String> map = new HashMap<String, String>();
		for(String property : properties){
			String[] pair = property.split("=");
			String key = pair[0];
			String val = pair[1];
			map.put(key, val);
		}
		return map;
	}
	
	private int countAppearance(String source, String toCount){
		int count = 0;
		//TODO:count appearance
		return count;
	}
	
}
