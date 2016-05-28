package gist;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author ianate
 *
 */
public class PwdDictionary {

	private static char[] pwdElements = ("abcdefghijklmnopqrstuvwxyz"
									+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
									+ "1234567890.`~!@#$%^&*()-_=+[]{}\\|'\";:,<>/?").toCharArray();//94 chars
	
	private static final String encode(String pwd){
		try{
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] encoded = md.digest(pwd.getBytes());
			StringBuilder builder = new StringBuilder();
			for(byte oneByte : encoded) builder.append(Integer.toHexString(oneByte & 0xff));
			return builder.toString();
		}catch(NoSuchAlgorithmException e){
			throw new RuntimeException(e);
		}
	}
	
	private static final String lastOfCurrentLength(int pwdLength){
		char lastChar = pwdElements[pwdElements.length - 1];
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < pwdLength; i ++){
			builder.append(lastChar);
		}
		return builder.toString();
	}
	
	private static int indexOf(char c){
		for(int i = 0; i < pwdElements.length; i ++){
			if(pwdElements[i] == c){
				return i;
			}
		}
		throw new IllegalArgumentException("illegal char");
	}
	
	/**
	 * @param source must starts with lastChar
	 * @return
	 */
	private static int posToCarry(String source, char lastChar){
		for(int i = 0 ; i < source.length(); i ++){
			if(source.charAt(i) != lastChar){
				return i;
			}
		}
		throw new RuntimeException("it should have ended");
	}
	
	public static void main(String[] args){
		Map<String, String> map = new HashMap<String, String>();
		
		char lastChar = pwdElements[pwdElements.length - 1];
		//left side first
		for(int pwdLength = 1; pwdLength < 7; pwdLength ++){
			String lastOfThisLength = lastOfCurrentLength(pwdLength);
			
			StringBuilder pwdBuilder = new StringBuilder(pwdLength);
			for(int i = 0; i < pwdLength; i ++){
				pwdBuilder.append(pwdElements[0]);//init
			}
			
			while(true){
				String source = pwdBuilder.toString();
				String encoded = encode(source);
				map.put(encoded, source);
				if(source.equals(lastOfThisLength)){
					break;
				}else if(lastChar == source.charAt(0)){
					pwdBuilder = new StringBuilder(pwdLength);
					int posToCarry = posToCarry(source, lastChar);
					for(int i = 0; i < pwdLength ; i ++){
						if(i == posToCarry){
							char formerChar = source.charAt(i);
							pwdBuilder.append(pwdElements[indexOf(formerChar) + 1]);
						}else if(i < posToCarry){
							pwdBuilder.append(pwdElements[0]);
						}else{
							pwdBuilder.append(source.charAt(i));
						}
					}
				}else{
					pwdBuilder = new StringBuilder(pwdLength);
					pwdBuilder.append(pwdElements[indexOf(source.charAt(0)) + 1]);
					for(int i = 1; i < pwdLength; i ++){
						pwdBuilder.append(source.charAt(i));
					}
				}
			}
		}
	}
}
