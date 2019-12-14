package values;

public enum HttpVersion {
	HTTP11("HTTP/1.1"), HTTP2("HTTP2");
	String text;
	private HttpVersion(String text) {
		this.text = text;
	}
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}
}
