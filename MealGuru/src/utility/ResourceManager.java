package utility;

import java.awt.image.BufferedImage;
import java.io.File;
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

		File outputfile = null;

		int number = (int) (Math.random() * 100000);

		while ((outputfile = new File("usercontent/" + number + ".png")).exists())
			number++;

		try {

			ImageIO.write(image, "png", outputfile);
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

}
