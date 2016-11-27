package gui;

import data.UserDA;
import gui.smartnode.IntegerTextField;
import gui.smartnode.PhoneTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class UserEditor extends BorderPane {

	TextField username;

	TextField email;
	PhoneTextField phoneNumber;

	Button male;
	Button female;

	ComboBox<String> month;
	ComboBox<String> day;
	ComboBox<String> year;

	IntegerTextField height;
	IntegerTextField weight;

	Button cancelChanges;
	Button submitChanges;

	public UserEditor() {

		this.username = new TextField(PrimaryWindow.getActiveUser().getUsername());

		this.email = new TextField(PrimaryWindow.getActiveUser().getEmail());
		this.phoneNumber = new PhoneTextField();

		this.male = new Button("Male");
		this.female = new Button("Female");

		ObservableList<String> monthOptions = FXCollections.observableArrayList("January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December");

		ObservableList<String> dayOptions = FXCollections.observableArrayList();
		for (int i = 1; i < 32; i++)
			dayOptions.add(Integer.toString(i));

		ObservableList<String> yearOptions = FXCollections.observableArrayList();
		for (int i = 2016; i > 1916; i--)
			yearOptions.add(Integer.toString(i));

		this.month = new ComboBox<>(monthOptions);
		this.day = new ComboBox<>(dayOptions);
		this.year = new ComboBox<>(yearOptions);

		HBox comboBoxHBox = new HBox(5, this.month, this.day, this.year);

		comboBoxHBox.setAlignment(Pos.CENTER);

		this.height = new IntegerTextField(PrimaryWindow.getActiveUser().getHeight());
		this.weight = new IntegerTextField(PrimaryWindow.getActiveUser().getWeight());

		this.setRight(new VBox(20, this.username, this.email, this.phoneNumber, new HBox(this.male, this.female),
				this.height, this.weight));

		this.cancelChanges = new Button("Cancel");

		this.submitChanges = new Button("Save and Finish");
		this.submitChanges.setOnAction(e -> {

			if ((this.email.getText() != null) && !this.email.getText().equals(""))
				PrimaryWindow.getActiveUser().setEmail(this.email.getText());
			if ((this.phoneNumber.getText() != null) && !this.phoneNumber.getText().equals(""))
				PrimaryWindow.getActiveUser().setPhoneNumber(this.phoneNumber.getText());

			if ((this.month.getValue() != null) && (this.day.getValue() != null) && (this.year.getValue() != null))
				PrimaryWindow.getActiveUser().setDateOfBirth(this.month.getValue(),
						Integer.parseInt(this.day.getValue()), Integer.parseInt(this.year.getValue()));

			if ((this.weight.getText() != null) && !this.weight.getText().equals(""))
				PrimaryWindow.getActiveUser().setWeight(Integer.parseInt(this.weight.getText()));
			if ((this.height.getText() != null) && !this.height.getText().equals(""))
				PrimaryWindow.getActiveUser().setHeight(Integer.parseInt(this.height.getText()));

			new UserDA().updateUser(PrimaryWindow.getActiveUser());

			PrimaryWindow.displayMainGUI();

		});

		this.setBottom(new HBox(5, this.cancelChanges, this.submitChanges));

	}

}
