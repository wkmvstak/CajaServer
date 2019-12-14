package entry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(8080);
		} catch (IOException e1) {
			return;
		}
		while(true)
			try {
				Socket sck = server.accept();
				Thread th = new Thread(new RequestHandler(sck));
				th.start();
				System.out.println("Started");
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		server.close();
	}

}
