package utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class ResourceManager {

	public static Image getResourceImage(String pictureExtension) {

		URL url = ResourceManager.class.getResource("/images/" + pictureExtension);

		if (url == null)
			return ResourceManager.getResourceImage("no-image-found.jpg");
		else
			return new Image(url.toExternalForm());

	}

	public static Image getImage(String extension) {

		if (extension.equalsIgnoreCase("defaultmeal.png") || extension.equalsIgnoreCase("defaultfood.png")
				|| extension.equalsIgnoreCase("defaultuser.png"))
			return getResourceImage(extension);

		new File("usercontent").mkdir();

		if (new File("usercontent/" + extension).exists())
			return new Image("file:usercontent/" + extension, true);
		else
			return ResourceManager.getResourceImage("no-image-found.jpg");

	}

	public static String saveImage(BufferedImage image) {

		new File("usercontent").mkdir();

		double imageHeight = image.getHeight();
		double imageWidth = image.getWidth();

		int scaledHeight = 200;
		int scaledWidth = 200;

		if ((imageHeight / 200) > (imageWidth / 200))
			scaledWidth = (int) ((200 * imageWidth) / imageHeight);
		else
			scaledHeight = (int) ((200 * imageHeight) / imageWidth);

		// creates output image
		BufferedImage newImage = new BufferedImage(scaledWidth, scaledHeight, image.getType());
		// scales the input image to the output image
		Graphics2D g2d = newImage.createGraphics();
		g2d.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		File outputfile = null;

		int number = (int) (Math.random() * 100000);

		while ((outputfile = new File("usercontent/" + number + ".png")).exists())
			number++;

		try {

			ImageIO.write(newImage, "png", outputfile);
			return outputfile.getName();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return null;

	}

	public static String getCSS(String extension) {

		URL url = ResourceManager.class.getResource("/css/" + extension);

		return url.toExternalForm();

	}

	public static String getUserCSS(String extension) {

		new File("usercontent").mkdir();

		if (new File("usercontent/" + extension).exists())
			return ("file:usercontent/" + extension);

		return null;

	}

	public static String saveCSS(String primaryColor, String secondaryColor, String buttonColorString) {

		new File("usercontent").mkdir();

		File outputfile = null;

		int number = (int) (Math.random() * 100000);

		while ((outputfile = new File("usercontent/" + number + ".css")).exists())
			number++;

		String extension = ResourceManager.getCSS("style.css").replaceAll("file:", "");
		File readFile = new File(extension);

		try {

			BufferedReader bufferedReader = new BufferedReader(new FileReader(readFile));
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputfile));

			String s;

			while ((s = bufferedReader.readLine()) != null) {
				if (s.contains("backgroundcolor: darkgray;") && (primaryColor != null))
					s = s.replaceAll("darkgray", primaryColor);
				else if (s.contains("boxcolor: lightgray;") && (secondaryColor != null))
					s = s.replaceAll("lightgray", secondaryColor);
				else if (s.contains("buttoncolor: #cccccc;") && (buttonColorString != null))
					s = s.replaceAll("#cccccc", buttonColorString);

				bufferedWriter.write(s);
				bufferedWriter.newLine();

			}

			bufferedReader.close();
			bufferedWriter.close();

			outputfile.renameTo(new File("file:" + outputfile.getName()));
			outputfile.createNewFile();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return outputfile.getName();

	}

}
