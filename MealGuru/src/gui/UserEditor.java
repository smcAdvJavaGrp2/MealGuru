package gui;

import data.mealguru.UserDA;
import gui.smartnode.IntegerTextField;
import gui.smartnode.PhoneTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.ResourceManager;

class Mine extends BorderPane {

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

	public Mine() {

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

public class UserEditor extends BorderPane {

	// Create exampleButton; make it use FileChooser to grab an Image
	// and save that image
	Button exampleButton;

	TextField userName;
	TextField encryptedPassword;
	TextField pictureExtension;
	TextField email;
	TextField phoneNumber;
	TextField gender;
	TextField dateOfBirth;
	TextField height;
	TextField weight;

	// Create the submitButton; its ActionHandler will:
	// (1) take all the data from the editUser Pane and:
	// App.getUser().setUserData(userName) and all the TextField objects;
	// (2) SQL: Update App.getUser() in our database. . .
	// new userDA().saveUser(App.getUser()));
	Button submit;

	// Create the parametless constructor
	public UserEditor() {

		// Image Object
		Image image = ResourceManager.getImage(PrimaryWindow.getActiveUser().getPictureExtension());

		// ImageView to view Image in GUI
		ImageView imageView = new ImageView(image);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(200);
		imageView.setFitWidth(200);

		// Data Text Fields
		this.userName = new TextField();
		this.encryptedPassword = new TextField();
		this.pictureExtension = new TextField();
		this.email = new TextField();
		this.phoneNumber = new TextField();
		this.gender = new TextField();
		this.dateOfBirth = new TextField();
		this.height = new TextField();
		this.weight = new TextField();

		// Use VBox Layout on BorderPane
		VBox rightVBox = new VBox(5); // 5 spaces between each node
		rightVBox.getChildren().add(this.userName);
		rightVBox.getChildren().add(this.encryptedPassword);
		rightVBox.getChildren().add(this.pictureExtension);
		rightVBox.getChildren().add(this.email);
		rightVBox.getChildren().add(this.phoneNumber);
		rightVBox.getChildren().add(this.gender);
		rightVBox.getChildren().add(this.dateOfBirth);
		rightVBox.getChildren().add(this.height);
		rightVBox.getChildren().add(this.weight);

		this.setRight(rightVBox);

		// Create exampleButton with a graphic
		this.exampleButton = new Button();
		this.exampleButton.setGraphic(imageView);
		this.exampleButton.setOnAction(arg0 -> System.out.println("Test"));

		// Create submit Button
		this.submit = new Button("Submit");
		// This HANDLES the submit button click event
		this.submit.setOnAction(arg0 -> {
			PrimaryWindow.getActiveUser().setUsername(UserEditor.this.userName.getText());
			PrimaryWindow.getActiveUser().setPassword(UserEditor.this.encryptedPassword.getText());
			PrimaryWindow.getActiveUser().setPictureExtension(UserEditor.this.pictureExtension.getText());
			PrimaryWindow.getActiveUser().setEmail(UserEditor.this.email.getText());
			PrimaryWindow.getActiveUser().setPhoneNumber(UserEditor.this.phoneNumber.getText());
			PrimaryWindow.getActiveUser().setGender(UserEditor.this.gender.getText());
			PrimaryWindow.getActiveUser().setDateOfBirth(UserEditor.this.dateOfBirth.getText());
			PrimaryWindow.getActiveUser().setHeight(Integer.parseInt(UserEditor.this.height.getText()));
			PrimaryWindow.getActiveUser().setWeight(Integer.parseInt(UserEditor.this.weight.getText()));
			// App.getActiveUser().setSJFDKal()(textField.getText());
			UserDA userDA = new UserDA();
			userDA.saveUser(PrimaryWindow.getActiveUser());
		});

		// Place submit button on the bottom of BorderPane/GUI
		this.setBottom(this.submit);
		// Place example button on the left of BorderPane/GUI
		this.setLeft(this.exampleButton);
	}

}
