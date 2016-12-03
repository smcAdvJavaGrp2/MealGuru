package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSSWriter {

	File writeFile;

	public CSSWriter(String primaryHex, String secondaryHex) {

		String extension = ResourceManager.getCSS("style.css").replaceAll("file:", "");

		File readFile = new File(extension);
		try {
			this.writeFile = File.createTempFile("temp", ".css");
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {

			BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.writeFile));

			String s;

			while ((s = bufferedReader.readLine()) != null)
				if (s.contains("backgroundcolor: darkgray;"))
					s = s.replaceAll("darkgray", primaryHex);
				else if (s.contains("boxcolor: lightgray;")) {
					s = s.replaceAll("lightgray", secondaryHex);

					bufferedWriter.write(s + "\n");

				}

			bufferedReader.close();
			bufferedWriter.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public File getCSSFile() {

		return this.writeFile;

	}

}
