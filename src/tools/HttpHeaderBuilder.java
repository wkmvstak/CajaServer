package tools;
import static settings.Settings.END;
public class HttpHeaderBuilder {
	StringBuilder header;
	boolean lastEol =false;
	public HttpHeaderBuilder() {
		header = new StringBuilder();
	}
	public HttpHeaderBuilder(int size) {
		header = new StringBuilder(size);
	}
	public void addLine(String line) {
		header.append(line);
		header.append(END);
	}
	public void addField(String field, boolean eol) {
		header.append(field);
		if(eol) header.append(END);
		else header.append(' ');
		lastEol = eol;
	}
	public void addField(int field, boolean eol) {
		header.append(field);
		if(eol) header.append(END);
		else header.append(' ');
	}
	public void addBody(String body) {
		header.append(END);
		header.append(body);
	}
	public String getWholeHeader() {
		header.append(END);
		return header.toString();
	}
	public void addSimpleCookie(String name, String value) {
		header.append("Set-Cookie: ");
		header.append(name);
		header.append("=");
		header.append(value);
		header.append(END);
	}
}
