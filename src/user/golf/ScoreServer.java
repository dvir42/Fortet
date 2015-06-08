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

	public final int PORT = 2212;

	private ServerSocket serverSocket;
	private Database db;

	public ScoreServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			db = new Database();
			while (true) {
				Socket socket = serverSocket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String line = in.readLine();
				in.close();
				String name = line.split("|")[0];
				String score = line.split("|")[1];
				db.insertUpdateDelete("insert into scores values('" + name
						+ "'," + score + ")");
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream()));
				out.write(sort(db.select("select * from scores")));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String sort(String[][] scores) {
		String sorted = "";
		Arrays.sort(scores, new Comparator<String[]>() {
			@Override
			public int compare(String[] a, String[] b) {
				return Integer.compare(Integer.parseInt(a[1]),
						Integer.parseInt(b[1]));
			}
		});
		for (String[] entry : scores) {
			for (String value : entry)
				sorted += value + " ";
			sorted += '\n';
		}
		return sorted;
	}

	public static void main(String[] args) {
		String[][] s = { { "asd", "4" }, { "qwe", "2" }, { "qwr", "1" },
				{ "ter", "3" } };
		System.out.println(ScoreServer.sort(s));
	}

}
