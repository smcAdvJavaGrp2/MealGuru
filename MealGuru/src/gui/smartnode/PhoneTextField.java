package gui.smartnode;

import javafx.scene.control.TextField;

public class PhoneTextField extends TextField {

	public PhoneTextField() {

		this.textProperty().addListener(e -> {

			if ((this.getText() != null) && !this.getText().equalsIgnoreCase(this.formatPhoneNumber(this.getText())))
				this.setText(this.formatPhoneNumber(this.getText()));

		});

	}

	private String formatPhoneNumber(String phoneNumber) {

		String toReturn = "";

		char[] temp = phoneNumber.toCharArray();

		for (char c : temp)
			if (Character.isDigit(c))
				toReturn += c;

		if (toReturn.length() < 4)
			toReturn = "(" + toReturn;
		else if (toReturn.length() < 7)
			toReturn = "(" + toReturn.substring(0, 3) + ") " + toReturn.substring(3);
		else if (toReturn.length() >= 7)
			toReturn = "(" + toReturn.substring(0, 3) + ") " + toReturn.substring(3, 6) + "-" + toReturn.substring(6);

		if (toReturn.length() == 1)
			return null;
		else
			return toReturn;
	}

}
