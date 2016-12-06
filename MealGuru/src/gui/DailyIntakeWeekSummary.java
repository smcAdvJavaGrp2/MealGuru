package gui;

import java.util.Date;

import gui.smartnode.DailyIntakeGraph;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import utility.ResourceManager;

public class DailyIntakeWeekSummary extends BorderPane {

	public DailyIntakeWeekSummary() {
		
		//SUMMATY GRAPH
		
		if (PrimaryWindow.getCenterDate() == null)
			PrimaryWindow.setCenterDate(new Date());

		DailyIntakeGraph dailyIntakeGraph = new DailyIntakeGraph();

		this.setCenter(dailyIntakeGraph);
		
		
		//BACK BUTTON
		
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
	}

	public void redrawGraph() {

	}

}
