package gui.smartnode;

import edible.Edible;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import utility.ResourceManager;

public class NutritionLabel extends ImageView {

	WritableImage foodLabel;

	public NutritionLabel(Edible edible) {

		this.redrawLabel(edible);

	}

	public void redrawLabel(Edible edible) {

		Image template = ResourceManager.getResourceImage("NutritionFacts.jpg");

		this.foodLabel = new WritableImage((int) template.getWidth(), (int) template.getHeight());

		this.setOnMouseClicked(e -> {

			System.out.println(e.getX() + " " + e.getY());

		});

		PixelReader reader = template.getPixelReader();
		PixelWriter writer = this.foodLabel.getPixelWriter();

		for (int y = 0; y < this.foodLabel.getHeight(); y++)
			for (int x = 0; x < this.foodLabel.getWidth(); x++)
				writer.setColor(x, y, reader.getColor(x, y));

		try {

			this.drawTextToImage(edible.getCalories() + "", 161, 240);

			this.drawTextToImage(edible.getTotalFat() + "", 172, 342);

			this.drawTextToImage(edible.getSaturatedFat() + "", 278, 388);

			this.drawTextToImage(edible.getTransFat() + "", 215, 445);

			this.drawTextToImage(edible.getCholesterol() + "", 220, 495);

			this.drawTextToImage(edible.getSodium() + "", 161, 547);

			this.drawTextToImage(edible.getCarbohydrates() + "", 336, 598);

			this.drawTextToImage(edible.getDietaryFiber() + "", 261, 648);

			this.drawTextToImage(edible.getSugar() + "", 178, 700);

			this.drawTextToImage(edible.getProtein() + "", 145, 750);

			this.drawTextToImage(edible.getVitaminA() + "%", 176, 824);

			this.drawTextToImage(edible.getVitaminC() + "%", 494, 824);

			this.drawTextToImage(edible.getCalcium() + "%", 151, 878);

			this.drawTextToImage(edible.getIron() + "%", 403, 878);

		} catch (Exception e) {

		}

		this.setImage(this.foodLabel);

	}

	public void drawTextToImage(String s, int xX, int yY) {

		TextOverlay text = null;

		try {

			text = new TextOverlay(s);

		} catch (Exception e) {
		}

		PixelReader reader = text.textImage.getPixelReader();
		PixelWriter writer = this.foodLabel.getPixelWriter();

		for (int y = 0; y < text.getHeight(); y++)
			for (int x = 0; x < text.getWidth(); x++)
				if (((x + xX) < this.foodLabel.getWidth()) && ((x + xX) > 0) && ((y + yY) > 0)
						&& ((y + yY) < this.foodLabel.getHeight()))
					writer.setColor(x + xX, y + yY, reader.getColor(x, y));

	}

}
