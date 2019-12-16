package entry;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import context.Context;
import context.HttpHeader;
import context.HttpResponse;
import context.Session;
import helper.ResponseHeader;

public class RequestHandler implements Runnable {
	Socket sck;
	HttpHeader header;
	public RequestHandler(Socket sck) {
		this.sck = sck;
	}
	@Override
	public void run() {
		byte[] reading = null;
		try {
			InputStream is = sck.getInputStream();
			if(is.available() == 0) return;
			reading = is.readNBytes(is.available());
			
		}catch(Throwable e) {
			e.printStackTrace();
		}
		String str = new String(reading);
		//System.out.println(str);
		header = new HttpHeader(str);
		Session session = processSession(header.getCookie("example"));
		processRequest(header);
		//System.out.println(header);
		String send = "<html><body>Welcome to Caja Server:"+session.visits+"</body></html>";
		String s  = new HttpResponse(ResponseHeader.OK, send.getBytes()).getRawHTTPResponse();
		//System.out.println(s);
		try {
			sck.getOutputStream().write(s.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private HttpResponse processRequest(HttpHeader header2) {
		// TODO Auto-generated method stub
		return null;
		
	}
	private Session processSession(String cookie) {
		Session session = null;
		if(cookie != null) {
			session = Context.getSession(cookie);
			//This should not happen except in cases where there was a cookie before server execution
			if(session == null) {
				session = new Session(); //There should be filled with session data, but not now
				session.setCookieId(cookie);
				Context.addSession(session);
				System.out.println("Session not found");
			}
		} else {
			//System.out.println("Cookie NOT found");
			session = new Session();
			session.setCookieId(Long.toString(System.currentTimeMillis()));
			Context.addSession(session);
			//Add to HTTPResponse a new cookie
		}
		session.visits++;
		return session;
	}

}
