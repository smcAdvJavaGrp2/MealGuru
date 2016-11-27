package gui.smartnode;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import utility.ResourceManager;

public class SubmitButton extends Button {

	public SubmitButton() {

		ImageView imageView = new ImageView(ResourceManager.getResourceImage("check.png"));
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);

		this.setGraphic(imageView);

		this.setStyle("-fx-background-color: green; " + "-fx-background-radius: 5em; " + "-fx-min-width: 100px; "
				+ "-fx-min-height: 100px; " + "-fx-max-width: 100px; " + "-fx-max-height: 100px;");

		Tooltip tooltip = new Tooltip("Submit");

		Tooltip.install(this, tooltip);

	}

}
