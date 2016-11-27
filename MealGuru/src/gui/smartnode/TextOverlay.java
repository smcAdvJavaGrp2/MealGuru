package gui.smartnode;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class TextOverlay {

	WritableImage textImage;

	// CONSTRUCTORS

	public TextOverlay(String s) throws IOException {
		this.setText(s);
	}

	// METHODS

	public void setText(String string) throws IOException {

		Font font = new Font("sans-serif", Font.ROMAN_BASELINE, 30);

		BufferedImage bufferedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bufferedImage.getGraphics();

		graphics.setFont(font);
		FontMetrics fm = graphics.getFontMetrics();

		bufferedImage = new BufferedImage(fm.stringWidth(string), fm.getHeight(), BufferedImage.TYPE_INT_ARGB);
		graphics = bufferedImage.getGraphics();

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

		graphics.setColor(Color.BLACK);
		graphics.setFont(font);

		graphics.drawString(string, 0, fm.getHeight() - fm.getDescent());
		graphics.dispose();

		this.textImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());

		PixelWriter pw = this.textImage.getPixelWriter();

		for (int y = 0; y < bufferedImage.getHeight(); y++)
			for (int x = 0; x < bufferedImage.getWidth(); x++)
				pw.setArgb(x, y, bufferedImage.getRGB(x, y));

	}

	public int getWidth() {
		return (int) this.textImage.getWidth();
	}

	public int getHeight() {
		return (int) this.textImage.getHeight();
	}

}
