package gui.smartnode;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class SubmitButton extends Button {

	public SubmitButton() {

		super("Submit");

		this.setStyle("-fx-font-size: 16px; -fx-background-color: #FFFFFF; "
				+ "-fx-background-radius: 3em; -fx-max-width: 100px;"
				+ "-fx-border-color: green; -fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 3em;");

		Tooltip tooltip = new Tooltip("Submit");

		Tooltip.install(this, tooltip);

	}

}
