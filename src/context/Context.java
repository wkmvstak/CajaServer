package context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Context {
	public static HashMap<Integer, List<Session>> sessions = new HashMap<Integer,List<Session>>();
	public static void addSession(Session session) {
		int hash = calculateHash(session.getCookieId());
		List<Session> list = sessions.get(hash);
		if(list == null) {
			list = new LinkedList<Session>();
			sessions.put(hash, list);
		}
		list.add(session);
	}
	private static int calculateHash(String s) {
		String cookie = s;
		int hash = 0;
		for(int i = 0; i < cookie.length(); i++) hash +=cookie.charAt(i);
		System.out.println(hash);
		return hash;
	}
	public static Session getSession(String cookieVal) {
		List<Session> sessionsList = sessions.get(calculateHash(cookieVal));
		if(sessionsList == null) return null;
		Session session = null;
		for (Session s : sessionsList) {
			if(s.getCookieId().equals(cookieVal)) {
				return s;
			}
		}
		return null;
	}
	
}
