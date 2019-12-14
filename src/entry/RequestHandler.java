package entry;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

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
		System.out.println(str);
		header = new HttpHeader(str);
		Session session = new Session();
		System.out.println(header);
		String send = "<html><body>Welcome to Caja Server</body></html>";
		String s  = new HttpResponse(ResponseHeader.OK, send.getBytes()).getRawHTTPResponse();
		System.out.println(s);
		try {
			sck.getOutputStream().write(s.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
