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

		this.setStyle("-fx-background-color: red; " + "-fx-background-radius: 5em; " + "-fx-min-width: 75px; "
				+ "-fx-min-height: 75px; " + "-fx-max-width: 75px; " + "-fx-max-height: 75px;");

		this.setOnMouseEntered(e -> {

			imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.5), 20, .8, 0, 0);");

		});

		this.setOnMouseExited(e -> {

			imageView.setStyle("");

		});

		Tooltip tooltip = new Tooltip("Cancel");

		Tooltip.install(this, tooltip);

	}

}
