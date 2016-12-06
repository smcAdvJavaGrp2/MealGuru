package gui;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertBox extends Alert {

	Optional<ButtonType> result;

	public AlertBox(String confirmationDialogue, String header, String contextText) {

		super(Alert.AlertType.CONFIRMATION);
		this.setTitle(confirmationDialogue);
		this.setHeaderText(header);
		this.setContentText(contextText);

		this.setWidth(200);

		//this.getButtonTypes().remove(0);

	}

}
