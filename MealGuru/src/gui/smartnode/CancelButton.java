package gui.smartnode;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import utility.ResourceManager;

public class CancelButton extends Button {

	public CancelButton() {

		ImageView imageView = new ImageView(ResourceManager.getResourceImage("cancel.png"));
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(75);
		imageView.setFitWidth(75);

		this.setGraphic(imageView);

		this.setStyle("-fx-background-color: #ff7f7f; " + "-fx-background-radius: 5em; " + "-fx-min-width: 75px; "
				+ "-fx-min-height: 75px; " + "-fx-max-width: 75px; " + "-fx-max-height: 75px;");

		Tooltip tooltip = new Tooltip("Cancel");

		Tooltip.install(this, tooltip);

	}

}
