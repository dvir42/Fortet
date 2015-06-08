package user.golf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import user.Prince;

/**
 * Contains functions to count the bytes used to write the {@link Prince}
 * program
 * 
 * @author dvir42
 *
 */
public class ByteCounter {

	public static int bytes() {
		try {
			byte[] bytes = Files.readAllBytes(new File("src/user/Prince.java")
					.toPath());
			int counter = 0;
			for (int i = 0; i < bytes.length; i++) {
				if (bytes[i] == '/' && bytes[i + 1] == '/') {
					int j;
					for (j = i; j < bytes.length; j++) {
						if (bytes[j] == '\n')
							break;
					}
					i = j;
					continue;
				}
				if (bytes[i] == '/' && bytes[i + 1] == '*') {
					int j;
					for (j = i; j < bytes.length; j++) {
						if (bytes[j] == '*' && bytes[j + 1] == '/')
							break;
					}
					i = j + 1;
					continue;
				}
				if (bytes[i] > 32 && bytes[i] < 127)
					counter++;
			}
			return counter;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static void main(String[] args) {
		System.out.println(bytes());
	}

}
