package gui.smartnode;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

public class IntegerTextField extends TextField {

	public IntegerTextField() {

		this(0);

	}

	public IntegerTextField(int startValue) {

		this.setPrefWidth(50);

		this.setText(startValue + "");

		this.setOnMouseClicked(e -> {

			this.selectAll();

		});

		this.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*"))
				IntegerTextField.this.setText(newValue.replaceAll("[^\\d]", ""));
		});

	}

	public int getValue() {

		return Math.abs(Integer.parseInt(this.getText()));

	}

	public boolean isNumeric(String string) {

		for (char c : string.toCharArray())
			if (!Character.isDigit(c))
				return false;

		return true;
	}

}
