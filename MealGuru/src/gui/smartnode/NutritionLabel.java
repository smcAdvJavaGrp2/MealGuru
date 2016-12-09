package gui.smartnode;

import java.text.DecimalFormat;

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

		PixelReader reader = template.getPixelReader();
		PixelWriter writer = this.foodLabel.getPixelWriter();

		for (int y = 0; y < this.foodLabel.getHeight(); y++)
			for (int x = 0; x < this.foodLabel.getWidth(); x++)
				writer.setColor(x, y, reader.getColor(x, y));

		try {
			
			DecimalFormat df = new DecimalFormat("####.##"); 
			
			this.drawTextToImage(df.format(edible.getCalories()), 161, 240);

			this.drawTextToImage(df.format(edible.getTotalFat().getMeasure()) + edible.getTotalFat().getUnits(), 172, 342);

			this.drawTextToImage(df.format(edible.getSaturatedFat().getMeasure()) + edible.getSaturatedFat().getUnits(), 278, 388);

			this.drawTextToImage(df.format(edible.getTransFat().getMeasure()) + edible.getTransFat().getUnits(), 215, 445);

			this.drawTextToImage(df.format(edible.getCholesterol().getMeasure()) + edible.getCholesterol().getUnits(), 220, 495);

			this.drawTextToImage(df.format(edible.getSodium().getMeasure()) + edible.getSodium().getUnits(), 161, 547);

			this.drawTextToImage(df.format(edible.getCarbohydrates().getMeasure()) + edible.getCarbohydrates().getUnits(), 336, 598);

			this.drawTextToImage(df.format(edible.getDietaryFiber().getMeasure()) + edible.getDietaryFiber().getUnits(), 261, 648);

			this.drawTextToImage(df.format(edible.getSugar().getMeasure()) + edible.getSugar().getUnits(), 178, 700);

			this.drawTextToImage(df.format(edible.getProtein().getMeasure()) + edible.getProtein().getUnits(), 145, 750);

			this.drawTextToImage(df.format(edible.getVitaminA()) + "%", 176, 824);

			this.drawTextToImage(df.format(edible.getVitaminC()) + "%", 494, 824);

			this.drawTextToImage(df.format(edible.getIron()) + "%", 151, 878);

			this.drawTextToImage(df.format(edible.getCalcium()) + "%", 403, 878);

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
