package context;


import java.nio.charset.Charset;

import helper.ContentType;
import helper.ResponseHeader;
import settings.Settings;
import tools.HttpHeaderBuilder;
import values.HttpVersion;

public class HttpResponse {
	ResponseHeader response = ResponseHeader.OK;
	byte[] body;
	HttpVersion httpVersion = HttpVersion.HTTP11;
	String connection = "close";
	public boolean conectionClose;
	public ContentType contentType = ContentType.HTML;
	public String charset = "utf-8";
	public HttpResponse(ResponseHeader response, byte[] body) {
		this.response = response;
		this.body = body;
	}
	public HttpResponse() {
	}
	public HttpResponse(boolean close, ContentType cType, HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
		if(!close) connection = "keep-alive";
		contentType = cType;
		
	}
	public void setBody(String body) {
		this.body = body.getBytes();
	}
	public void setBody(byte[] body) {
		this.body = body;
	}
	public String getRawHTTPResponse() {
		HttpHeaderBuilder header = new HttpHeaderBuilder(response.name.length());
		header.addField(httpVersion.getText(), false);
		header.addField(response.code, false);
		header.addField(response.name, true);
		header.addLine("Connection: " + connection);
		header.addLine("Server: "+Settings.server);
		header.addLine("Content-Length: "+body.length);
		header.addLine("Content-Type: "+contentType.name()+"; charset="+charset);
		header.addSimpleCookie("example","0123456");
		header.addSimpleCookie("example2","01234567");
		header.addBody(new String(body)); //In some moment it should not be String at all, in base64 cases
		return header.getWholeHeader();
		
	}
}
