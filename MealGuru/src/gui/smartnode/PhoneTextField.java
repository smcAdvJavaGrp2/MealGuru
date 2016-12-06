package gui.smartnode;

import javafx.scene.control.TextField;

public class PhoneTextField extends TextField {

	String currentString;

	public PhoneTextField() {

		this.setOnKeyTyped(e -> {

			String numbers = "";
			int caret = this.getCaretPosition();

			if (this.getText().toCharArray() != null)
				for (char c : this.getText().toCharArray())
					if (Character.isDigit(c))
						numbers = numbers + c;

			if (e.getCharacter().equalsIgnoreCase("\b")) {

				if ((this.getCaretPosition() > 4) || (this.getCaretPosition() < 6))
					this.selectPositionCaret(3);
				else if ((this.getCaretPosition() > 10) || (this.getCaretPosition() < 11))
					this.selectPositionCaret(9);

				caret--;

			} else if (!e.getCharacter().matches("\\d+"))
				e.consume();

			if ((this.getText() != null) && (this.formatPhoneNumber(numbers) != null))
				while (!this.getText().equalsIgnoreCase(this.formatPhoneNumber(numbers)))
					this.setText(this.formatPhoneNumber(numbers));

			if ((caret == 5) && !e.getCharacter().equalsIgnoreCase("\b"))
				caret += 2;

			this.positionCaret(caret + 1);

		});

	}

	public PhoneTextField(String phoneNumber) {

		this();

		this.setText(phoneNumber);

	}

	private String formatPhoneNumber(String phoneNumber) {

		String toReturn = "";

		char[] temp = phoneNumber.toCharArray();

		for (char c : temp)
			if (Character.isDigit(c))
				toReturn += c;

		this.currentString = toReturn;

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

	public String backSpace() {

		while (Character.isDigit(this.getText().charAt(this.getCaretPosition()))) {
			if (this.getCaretPosition() == 0)
				return this.getText();
			this.selectPositionCaret(this.getCaretPosition() - 1);
		}

		return this.getText().substring(0, this.getCaretPosition()) + '\b'
				+ this.getText().substring(this.getCaretPosition());

	}

}
