package gui;

import java.util.Date;

import gui.smartnode.DailyIntakeGraph;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utility.ResourceManager;

public class DailyIntakeWeekSummary extends BorderPane {

	// Guru Object

	Guru guru;
	String[] tips = { "Hi welcome to MealGuru, I am the MealGuru! I'm here to assist you!",
			"MealGuru lets you create meals and track your nutrition.", "You can eat healthy, I'm here to help you!" };

	public DailyIntakeWeekSummary() {

		// Create Guru and set its x, y position
		this.guru = new Guru(400, 150);
		// started with simple animation, I'm not sure about over head yet
		// To do: switching the image or more complicated animations
		this.guru.startAnimation();
		// Return a random String from tip array
		this.guru.setScript(tips);

		// You can also set the string to a specific message at any time
		// this.guru.setMessage("specific message");

		// Move guru to mouse click
		this.setOnMouseClicked(e -> {
			this.guru.move(e.getSceneX(), e.getSceneY());
		});

		// SUMMATY GRAPH

		if (PrimaryWindow.getCenterDate() == null)
			PrimaryWindow.setCenterDate(new Date());

		DailyIntakeGraph dailyIntakeGraph = new DailyIntakeGraph();

		this.setCenter(dailyIntakeGraph);

		// BACK BUTTON

		ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
		leftImage.setPreserveRatio(true);
		leftImage.setFitHeight(50);
		VBox left = new VBox(leftImage);
		left.setAlignment(Pos.CENTER);
		left.setOnMouseEntered(e -> {
			left.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
		});
		left.setOnMouseExited(e -> {
			left.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0);");
		});
		left.setOnMouseClicked(e -> {
			PrimaryWindow.displayMainGUI();
		});

		this.setLeft(left);

		// Add guru to this borderPane
		this.getChildren().add(this.guru);
	}

	public void redrawGraph() {

	}

}
