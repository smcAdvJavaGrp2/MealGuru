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

		this.setStyle("-fx-background-color: #4CBB17; " + "-fx-background-radius: 5em; " + "-fx-min-width: 100px; "
				+ "-fx-min-height: 100px; " + "-fx-max-width: 100px; " + "-fx-max-height: 100px;");

		this.setOnMouseEntered(e -> {

			imageView.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,0.5), 20, .8, 0, 0);");

		});

		this.setOnMouseExited(e -> {

			imageView.setStyle("");

		});

		Tooltip tooltip = new Tooltip("Submit");

		Tooltip.install(this, tooltip);

	}

}
