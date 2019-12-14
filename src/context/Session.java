package context;

import java.net.InetAddress;

public class Session {
	public String cookieId;
	public String browser;
	public InetAddress ip;
	public String temp;
	public int visits;
	public Session() {
		cookieId = Long.toString(System.currentTimeMillis());
		visits = 0;
	}

}
