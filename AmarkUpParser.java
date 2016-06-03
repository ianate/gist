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

	private static final Pattern oneShortTag = Pattern.compile("(<(\\w+)([^(/>)]*)/>)(.*)");//$1 whole tag;$2 name;$3 options;$4 rest;
	private static final Pattern tag = Pattern.compile("<(\\w+)([^>]*)>(.*)(</\\1>)(.*)");
	private static final Pattern exception = Pattern.compile("</\\s*br>");
	
	private boolean isNullOrEmpty(String str) {return str == null || "".equals(str.trim());}
	
	public Element parseOneTag(String text){
		if(isNullOrEmpty(text)) return null;
		Element element = null;
		Matcher shortMatcher = oneShortTag.matcher(text);
		Matcher tagMatcher =  tag.matcher(text);
		if(shortMatcher.matches()){//short tag; no property; no subElements
			String name = shortMatcher.group(2);
			if(!isNullOrEmpty(name)){//illegal if no name
				element = new Element();
				element.setName(name);
				String proString = shortMatcher.group(3).trim();
				if(!isNullOrEmpty(proString)){
					String[] properties = proString.split("\\s+");
					Map<String, String> map = new HashMap<String, String>();
					for(String property : properties){
						String[] pair = property.split("=");
						String key = pair[0];
						String val = pair[1];
						map.put(key, val);
					}
					element.setProperty(map);
				}
			}
		}else if(tagMatcher.matches()){
			if(){//multi
				
			}else if(){//nest
				
			}
		}
		
		return element;
	}
	
	public static void main(String[] args){
		/*System.out.println("<h1></h1><h1 qwe />".matches("<\\w+([^(/>)]*)/>"));
		System.out.println("<h1></h1><h1 qwe />".matches(tag));
		System.out.println("<h1 qwe /><h1></h1><h1 qwe />".matches(oneShortTag));
		Pattern p = Pattern.compile(tag);
		Matcher m = p.matcher("<h1></h1><h1 qwe />");
		System.out.println("--");
		if(m.matches()){
			System.out.println(m.group(4));
		}
		m = p.matcher("<h1></h1><h1></h1>");
		System.out.println("--");
		if(m.matches()){
			System.out.println(m.group(4));
		}*/
		String a = " a  b c";
		String[] array = a.split("\\s+");
		System.out.println(array.length);
	}
	
	
}
