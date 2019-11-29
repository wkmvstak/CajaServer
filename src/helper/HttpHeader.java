package helper;

import java.io.InputStream;

public class HttpHeader {
	public String method;
	public String path;
	public String httpVersion;
	public String host;
	public String userAgent;
	public String[] accept; //Accept, encoding, language
	boolean keepAlive;
	String other_stuff;
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
				System.out.println(now);
				if((now > '9' || now < '0' )&& now != '.') throw new IllegalArgumentException();
				sb.append(now);
			}else {
				if(str.charAt(i) == '/') fetchingVersion = true;
				else if(str.charAt(i)!=http[HTTPlen]) throw new IllegalArgumentException("Wrong HTTP Version");
			}
			
			if(HTTPlen>20) throw new IllegalArgumentException();
		}
		httpVersion = sb.toString();
		i+=2; //Jump from \r to \n and then from \n to the nex character, Host
		while(str.charAt(i) != ' ') i++;
		i++;
		sb = new StringBuilder(6);
		for (;str.charAt(i) != '\r';i++) sb.append(str.charAt(i));
		validateHost(sb.toString());
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
		if(firstChar != 'G' && firstChar != 'P'&&firstChar != 'D' && firstChar != 'O') throw new IllegalArgumentException("Wrong method");
		switch (method.length()){
			case 3: //GET, PUT
				if(method.equals("GET") || method.equals("PUT")) {
					this.method = method;
					return;
				}
				break;
			case 4://POST
				if(method.equals("POST")){
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
}
