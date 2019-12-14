package context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Context {
	public static HashMap<Integer, List<Session>> sessions = new HashMap<Integer,List<Session>>();
	public static void addSession(Session session) {
		int hash = calculateHash(session);
		List<Session> list = sessions.get(hash);
		if(list == null) {
			list = new LinkedList<Session>();
		}
		list.add(session);
	}
	private static int calculateHash(Session s) {
		String cookie = s.cookieId;
		int hash = 0;
		for(int i = 0; i < cookie.length(); i++) hash +=cookie.charAt(i);
		return hash;
	}
	
}
