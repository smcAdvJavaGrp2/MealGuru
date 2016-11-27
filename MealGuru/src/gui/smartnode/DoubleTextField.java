package gui.smartnode;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;

public class DoubleTextField extends TextField {

	public DoubleTextField() {

		this(0);

	}

	public DoubleTextField(double startValue) {

		this.setPrefWidth(50);

		this.setText(startValue + "");

		this.setOnMouseClicked(e -> {

			this.selectAll();

		});

		this.textProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> {

			String toReplace = "";

			int decimalCount = 0;
			for (char c : newValue.toCharArray())
				if (Character.isDigit(c) || (c == '.'))
					if (c == '.') {
						if (decimalCount < 1) {
							toReplace += c;
							decimalCount++;
						}
					} else
						toReplace += c;

			this.cancelEdit();
			this.setText(toReplace);

		});

	}

	public double getValue() {

		if (this.getText().equalsIgnoreCase(""))
			return 0;

		return Math.abs(Double.parseDouble(this.getText()));

	}

	public boolean isNumeric(String string) {

		for (char c : string.toCharArray())
			if (!Character.isDigit(c) && (c != '.'))
				return false;

		return !(string.contains(".") && this.getText().contains("."));
	}

}
