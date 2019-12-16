package context;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class HttpHeader {
	public String method;
	public String path;
	public String httpVersion;
	public String host;
	public HashMap<String, String> header = new HashMap<String, String>();
	public HashMap<String, String> cookies = new HashMap<String, String>();
	public HttpHeader(InputStream is) {
		
	}
	public HttpHeader(String str) { //Whole header
		if(str.length()<3) throw new IllegalArgumentException();
		StringBuilder sb = new StringBuilder(7);
		int i = 0;
		for (;str.charAt(i) != ' ';i++) sb.append(str.charAt(i));
		validateMethod(sb.toString());
		i++;
		sb = new StringBuilder();
		for (;str.charAt(i) != ' ';i++) sb.append(str.charAt(i));
		validatePath(sb.toString());
		i++;
		int HTTPlen = 0;
		boolean fetchingVersion = false;
		sb = new StringBuilder(3);
		char[] http = {'H','T','T','P'};
		for (;str.charAt(i) != '\r';i++,HTTPlen++) {
			if(fetchingVersion) {
				char now = str.charAt(i);
				if((now > '9' || now < '0' )&& now != '.') throw new IllegalArgumentException();
				sb.append(now);
			}else {
				if(str.charAt(i) == '/') fetchingVersion = true;
				else if(str.charAt(i)!=http[HTTPlen]) throw new IllegalArgumentException("Wrong HTTP Version");
			}
			
			if(HTTPlen>20) throw new IllegalArgumentException();
		}
		httpVersion = sb.toString();
		i+=2; //\r to \n, and \n to next valid character
		sb = new StringBuilder();
		String headerName =  null;
		boolean doubleDot = false;
		boolean cookie = false;
		while(i<str.length()) {
			char now = str.charAt(i);
			if(now == ':' && !doubleDot) {
				i++;
				while(str.charAt(i) == ' ' && i < str.length()) i++;
				headerName = sb.toString();
				if(headerName.equals("Cookie")) cookie = true;
				sb = new StringBuilder();
				doubleDot = true;
				continue;
			}
			else if(now == '\r') {
				String value = sb.toString();
				if(cookie) {
					processCookie(value);
					cookie = false;
				}
				else header.put(headerName, value);
				sb = new StringBuilder();
				i+=2;
				doubleDot = false;
				if(str.charAt(i) == '\r') break;
				continue;
			}else {
				sb.append(now);
				i++;
			}
		}
		if(method == "GET") return;
		sb = new StringBuilder(str.length()-i);
		while(i<str.length()) {
			sb.append(str.charAt(i));
			i++;
		}
		header.put("body", sb.toString());
		
	}
	private void processCookie(String value) {
		int i = 0;
		while(value.charAt(i) == ' ' && i < value.length()) i++;
		String name = null;
		String val = null;
		StringBuilder sb = new StringBuilder();
		for(;i < value.length();i++) {
			char now = value.charAt(i);
			if(now == '=') {
				name = sb.toString();
				sb = new StringBuilder();
				continue;
			}
			if(now == ';') {
				cookies.put(name, sb.toString());
				i++;
				while(value.charAt(i) == ' ' && i < value.length()) i++;
				sb = new StringBuilder();
				now = value.charAt(i);
				
			}
			sb.append(now);
			if(i == value.length()-1) {
				cookies.put(name, sb.toString());
			}
		}
	}
	private void validateHost(String string) {
		// TODO Auto-generated method stub
		host = string;
		
	}
	private void validatePath(String string) {
		if(string.length() == 0 || string.charAt(0) == '.') throw new IllegalArgumentException("Wrong path");
		path = string;
	}
	private void validateMethod(String method) {
		//GET, POST, PUT, DELETE, PATCH, OPTIONS
		char firstChar = method.charAt(0);
		if(firstChar != 'G' && firstChar != 'P'&&firstChar != 'D' && firstChar != 'O'&& firstChar!='H') throw new IllegalArgumentException("Wrong method");
		switch (method.length()){
			case 3: //GET, PUT
				if(method.equals("GET") || method.equals("PUT")) {
					this.method = method;
					return;
				}
				break;
			case 4://POST
				if(method.equals("POST") || method.equals("HEAD")){
					this.method = method;
					return;
				}
				break;
			case 5://PATCH
				if(method.equals("PATCH")){
					this.method = method;
					return;
				}
				break;
			case 6://DELETE
				if(method.equals("DELETE")){
					this.method = method;
					return;
				}
				break;
			case 7://OPTIONS
				if(method.equals("OPTIONS")){
					this.method = method;
					return;
				}
				break;
	
			default:
				throw new IllegalArgumentException("Wrong method");
		}
		throw new IllegalArgumentException("Wrong method");
	}
	public boolean existsCookie() {
		return !cookies.isEmpty();
	}
	//public List<String> getCookies(){
		
//	}
	@Override
	public String toString() {
		String str = host + method + path + httpVersion;
		for (String s : header.keySet()) {
			str+=s +" "+header.get(s)+" ";
		}
		for (String s : cookies.keySet()) {
			str+=s + " " +cookies.get(s)+"\\";
		}
		return str;
	}
	public String getCookie(String cookie) {
		return cookies.get(cookie);
		
	}
}
