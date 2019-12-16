package context;

import java.net.InetAddress;

public class Session {
	private String cookieId;
	private String browser;
	private InetAddress ip;
	private String temp;
	public int visits;
	public Session() {
		cookieId = Long.toString(System.currentTimeMillis());
		visits = 0;
	}
	public String getCookieId() {
		return cookieId;
	}
	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public int getVisits() {
		return visits;
	}
	public void setVisits(int visits) {
		this.visits = visits;
	}

}
