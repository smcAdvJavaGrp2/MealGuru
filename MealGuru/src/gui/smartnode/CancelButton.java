package gui.smartnode;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class CancelButton extends Button {

	public CancelButton() {

		super("Cancel");

		this.setStyle("-fx-font-size: 16px; -fx-background-color: #FFFFFF; "
				+ "-fx-background-radius: 3em; -fx-max-width: 100px;"
				+ "-fx-border-color: red; -fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 3em;");

		Tooltip tooltip = new Tooltip("Cancel");

		Tooltip.install(this, tooltip);

	}

}
