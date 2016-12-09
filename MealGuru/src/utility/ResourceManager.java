package utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
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

		while ((outputfile = new File("usercontent/" + (int) (Math.random() * 100000) + ".css")).exists())
			continue;

		try {

			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputfile));

			String cssString = "* {\n" + "\t\n" + "\tbackgroundcolor: " + primaryColor + ";\n" + "\tboxcolor: "
					+ secondaryColor + ";\n" + "\tbuttoncolor: " + buttonColorString + ";\n" + "\t\n"
					+ "\thighopacityblack: rgba(0,0,0,0.8);\n" + "\t\n" + "}\n" + "\n" + ".root {\n" + "\t\n"
					+ "\t-fx-background-color: backgroundcolor;\t\n" + "\t\n" + "    -fx-base: buttoncolor; \n"
					+ "    -fx-accent: boxcolor ;\n" + "    -fx-default-button: buttoncolor;\n" + "        \n"
					+ "    -fx-text-color : ladder(\n" + "            -fx-selection-bar,\n"
					+ "            -fx-light-text-color 0%,\n" + "            -fx-dark-text-color 25%,\n"
					+ "            -fx-dark-text-color 60%,\n" + "            -fx-mid-text-color 75%\n" + "        );\n"
					+ "\n" + "    -fx-font-family: sans-serif;\n" + "    \n" + "}\n" + "\n" + ".bubble {\n"
					+ "\t-fx-shape:\"m67.974792,29.993683c-22.159973,0 -40,17.839996 -40,40l0,239.583862c0,22.160004 17.840027,40 40,40l126.835815,0l-17.12085,103.648834l98.044495,-103.648834l299.583862,0c22.159973,0 40,-17.839996 40,-40l0,-239.583862c0,-22.160004 -17.840027,-40 -40,-40l-507.343323,0z\";\t-fx-background-color: black, lightblue;\n"
					+ "\t-fx-background-color: rgba(173, 216, 230, 0.5);\n" + "\t-fx-padding: 25;\n"
					+ "\t-fx-font-size: 18;\n" + "}\n" + "\n" + ".pow {\n"
					+ "    -fx-shape: \"m1577.249146,1104.525513l40.766724,-91.398499l-166.989746,11.516541l123.802734,-86.374084l-172.747925,-66.220093l215.934937,-20.153931l-71.978271,-143.956665l129.561035,95.011414l48.945313,-126.681885l48.94519,95.011414l83.494873,-126.681885l63.340942,103.648804l100.769775,-71.978333l-2.879272,89.253113l106.527954,-92.132263l-48.945313,149.714966l149.715088,40.307861l-166.989746,48.945313l149.714844,106.527893l-178.506226,5.758362l46.06604,126.681885l-106.527832,-89.253174l-60.461914,106.527954l-60.461792,-83.494873l-77.736572,103.648804l-17.27478,-100.769775l-66.220093,69.099365l-11.516479,-95.011597l-98.349487,42.453369z\";\n"
					+ "    -fx-background-color: black, yellow;\n" + "    -fx-text-fill: red;\n"
					+ "    -fx-padding: 100;\n" + "    -fx-font-size: 20;\n" + "    \n" + "}\n" + "\n"
					+ ".thought_cloud {\n"
					+ "   \t-fx-shape: \"m1532.405762,249.30925c-23.032959,135.31929 178.506348,146.835831 190.022949,28.791336c37.428711,126.681915 210.176758,118.044495 233.209717,-43.187012c43.187012,86.374023 181.385498,20.153946 115.165527,-54.703537c69.099121,51.824417 221.693115,-37.428757 86.374023,-135.319321c89.253174,5.75827 164.110596,-282.155121 -63.341064,-187.143707c109.407227,-132.440186 -112.286133,-236.088989 -184.264648,-100.769714c8.637451,-71.978333 -77.736572,-60.461792 -86.374023,-11.516541c57.582764,-126.681885 -233.209717,-267.75946 -238.968018,5.758301c-60.461914,-138.198425 -236.088867,-57.582703 -166.989746,51.824402c-43.187012,-48.945251 -74.857422,14.395691 -48.945313,31.670471c-112.286377,-123.802734 -253.36377,126.681915 -97.890625,161.231537c-169.868896,25.912201 -51.824463,184.264572 34.549561,123.802734c-54.703613,40.307892 -5.758301,164.110657 115.165527,95.011444c-51.824463,71.978363 92.132324,123.802765 112.286133,34.549606z\";\n"
					+ "\t-fx-background-color: rgba(173, 216, 230, 0.5);\n" + "\t-fx-font-size: 18;\t\n"
					+ "    -fx-padding: 100;\n" + "}\n" + "\n" + ".button {\n" + "\t\n"
					+ "\t-fx-background-color: buttoncolor;\n" + "\t\n" + "}\n" + "\n" + ".button:hover {\n" + "\t\n"
					+ "\t-fx-opacity: .7;\t\n" + "\t\n" + "}\n" + "\n" + ".box {\n" + "\t\t\n"
					+ "\t-fx-effect: dropshadow(three-pass-box, highopacityblack, 15, 0, 0, 0);\n" + "\t\n"
					+ "    -fx-background-color: boxcolor;\n" + "\t-fx-background-radius: 15;\n" + "\t\n"
					+ "\t-fx-padding: 10px;\n" + "\n" + "}\n" + "\n" + ".box-no-shadow {\n" + "\t\t\t\n"
					+ "    -fx-background-color: boxcolor;\n" + "\t-fx-background-radius: 15;\n"
					+ "\t-fx-padding: 10px;\n" + "\n" + "}\n" + "\n" + "\n" + ".blankTextField  {\n" + "\t\n"
					+ "\t-fx-background-image: url(\"smallwarning.png\");\n" + "\t-fx-background-repeat: no-repeat;\n"
					+ "\t-fx-background-position: right center;\n" + "\t\n" + "}\n" + "\n" + "\n" + ".searchBar {\n"
					+ "\n" + "\t-fx-effect: dropshadow(three-pass-box, highopacityblack, 10, 0, 0, 0);\n"
					+ "\t-fx-background-color: white;\n" + "\t-fx-background-radius: 0;\n" + "\t\n" + "}\n" + "\n"
					+ ".check-box:selected>.box>.mark{\n" + "  -fx-background-color: green;\n" + "}\n" + "\n"
					+ ".root.popup {\n" + "\t\n"
					+ "    -fx-background-color: highopacityblack; -fx-background-radius: 10;\n" + "    \n" + "}\n"
					+ "\n" + ".user-label  {\n" + "\t\n"
					+ "\t-fx-effect: dropshadow(three-pass-box, highopacityblack, 10, 0, 0, 0);\n"
					+ "\t-fx-background-color: transparent;\n" + "\t\n" + "}\n" + "\n" + ".context-menu {\n" + "\n"
					+ "  -fx-background-color: transparent;\n" + "  -fx-text-fill: boxcolor;\n" + "  \n" + "}\n" + "\n"
					+ ".menu-item .label {\n" + "\t\n" + "  -fx-text-fill: boxcolor;\n" + "  \n" + "}\n" + "\n"
					+ ".menu-item:focused .label {\n" + "\t\n" + "  -fx-text-fill: white;\n" + "  \n" + "}\n" + "\n"
					+ ".scroll-pane > .viewport {\n" + "\n" + "\t-fx-background-color: transparent;\n" + "\n" + "}\n"
					+ "\n" + ".scroll-pane {\n" + "\n" + "\t-fx-base: backgroundcolor;\n"
					+ "    -fx-background-color: transparent;\n" + "\n" + "}\n" + "\n" + ".scroll-pane-inner-shadow {\n"
					+ "\n" + "\t-fx-effect: innershadow(gaussian , highopacityblack, 15, 0, 0, 0);\n" + "\n"
					+ "    -fx-background-color: backgroundcolor;\n" + "\n" + "}\n" + "\n" + "#fancytext {\n"
					+ "    -fx-font: 20px Tahoma;\n"
					+ "    -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%);\n"
					+ "    -fx-stroke: black;\n" + "    -fx-stroke-width: 1;\n" + "}";

			bufferedWriter.write(cssString);
			bufferedWriter.newLine();

			bufferedWriter.close();

			outputfile.createNewFile();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return outputfile.getName();

	}

}
