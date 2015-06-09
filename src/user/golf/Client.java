package user.golf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

	private Socket socket;

	private String serverString;

	public Client(String message) {
		try {
			socket = new Socket(ScoreServer.HOST, ScoreServer.PORT);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out.write(message + '\n');
			out.flush();
			while (!in.ready())
				;
			serverString = in.readLine();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public String getServerString() {
		return serverString;
	}

}