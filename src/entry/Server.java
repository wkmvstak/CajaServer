package entry;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import helper.HttpHeader;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8080);
			Socket sck = server.accept();
			InputStream is = sck.getInputStream();
			byte[] reading = is.readAllBytes();
			String str = new String(reading);
			HttpHeader header = new HttpHeader(str);
			System.out.println(header.method);
			System.out.println(header.httpVersion);
			System.out.println(header.host);
			System.out.println(header.path);
			System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
