package user.golf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Comparator;

public class ScoreServer {

	private class ServerThread implements Runnable {

		private final Socket socket;
		private Database db;

		public ServerThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				db = new Database();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				String entry = "";
				while (!in.ready())
					;
				entry = in.readLine();
				String name = entry.split("#")[0];
				String score = entry.split("#")[1];
				String maze = entry.split("#")[2];
				db.insertUpdateDelete("insert into scores values('" + name
						+ "'," + score + ",'" + maze + "')");
				out.write(sort(db
						.select("select name,score from scores where maze='"
								+ maze + "'")) + '\n');
				out.flush();
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static final int PORT = 42424;
	public static final String HOST = "127.0.0.1";

	private ServerSocket serverSocket;

	public ScoreServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				new Thread(new ServerThread(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param scores
	 * @return a string representation of <b>scores</b> sorted
	 */
	private static String sort(String[][] scores) {
		String sorted = "";
		Arrays.sort(scores, new Comparator<String[]>() {
			@Override
			public int compare(String[] a, String[] b) {
				return Integer.max(Integer.parseInt(a[1]),
						Integer.parseInt(b[1]));
			}
		});
		for (String[] entry : scores) {
			for (String value : entry)
				sorted += value + '#';
			sorted += '~';
		}
		return sorted;
	}

	public static void main(String[] args) {
		new ScoreServer();
	}

}
