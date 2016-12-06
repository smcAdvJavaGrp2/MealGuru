package gui;

import java.awt.image.BufferedImage;
import java.util.Optional;

import data.mealguru.UserDA;
import gui.smartnode.IntegerTextField;
import gui.smartnode.PhoneTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import user.User;
import utility.ResourceManager;

public class NewUser extends StackPane {

	private User user;

	private CreateUsernamePasswordPane createUsernamePasswordPane;
	TextField username;
	PasswordField password;
	Button submit;

	private SetPersonalInformation setPersonalInformation;
	TextField email;
	PhoneTextField phoneNumber;

	private SetUserGender setUserGender;
	Button male;
	Button female;

	private SetUserBirthday setUserBirthday;
	ComboBox<String> month;
	ComboBox<String> day;
	ComboBox<String> year;

	private SetUserBasicDetails setUserBasicDetails;
	IntegerTextField heightTextField;
	IntegerTextField weightTextField;

	private SetUserAdvancedDetails setUserAdvancedDetails;

	public Text message;

	public NewUser() {

		this.createUsernamePasswordPane = new CreateUsernamePasswordPane();
		this.createUsernamePasswordPane.setVisible(true);

		this.setUserGender = new SetUserGender();
		this.setUserGender.setVisible(false);

		this.setUserBirthday = new SetUserBirthday();
		this.setUserBirthday.setVisible(false);

		this.setUserBasicDetails = new SetUserBasicDetails();
		this.setUserBasicDetails.setVisible(false);

		this.setPersonalInformation = new SetPersonalInformation();
		this.setPersonalInformation.setVisible(false);

		this.setUserAdvancedDetails = new SetUserAdvancedDetails();
		this.setUserAdvancedDetails.setVisible(false);

		this.user = new User();

		this.getChildren().addAll(this.createUsernamePasswordPane, this.setPersonalInformation, this.setUserGender,
				this.setUserBirthday, this.setUserBasicDetails, this.setUserAdvancedDetails);

	}

	class CreateUsernamePasswordPane extends BorderPane {

		// VARIABLES

		Button existingUser;

		public CreateUsernamePasswordPane() {

			// NODES

			ImageView genie = new ImageView(ResourceManager.getResourceImage("logo.png"));
			genie.setPreserveRatio(true);
			genie.setFitHeight(200);

			NewUser.this.message = new Text();
			NewUser.this.message.setFill(Color.RED);
			NewUser.this.message.maxWidth(150);

			NewUser.this.username = new TextField();
			NewUser.this.username.setPromptText("New Username");
			NewUser.this.username.setMaxWidth(150);

			NewUser.this.password = new PasswordField();
			NewUser.this.password.setPromptText("Password");
			NewUser.this.password.setMaxWidth(150);

			NewUser.this.submit = new Button("Submit");
			NewUser.this.submit.setMaxWidth(150);

			this.existingUser = new Button("Back");
			this.existingUser.setMaxWidth(150);

			// ACTION

			NewUser.this.username.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					NewUser.this.password.requestFocus();
			});

			NewUser.this.password.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					this.submit();
			});

			NewUser.this.submit.setOnAction(e -> {
				this.submit();
			});

			this.existingUser.setOnAction(e -> {

				PrimaryWindow.setActiveUser(null);
				PrimaryWindow.displayWelcomeGUI();

			});

			// ADD THE NODES

			VBox center = new VBox(6, genie, NewUser.this.message, NewUser.this.username, NewUser.this.password,
					NewUser.this.submit, this.existingUser);
			center.setAlignment(Pos.CENTER);
			this.setCenter(center);

		}

		// METHODS

		public void submit() {

			NewUser.this.username.getStyleClass().remove("blankTextField");
			NewUser.this.password.getStyleClass().remove("blankTextField");

			if (NewUser.this.username.getText().equalsIgnoreCase("")
					&& NewUser.this.password.getText().equalsIgnoreCase("")) {

				NewUser.this.username.getStyleClass().add("blankTextField");
				NewUser.this.password.getStyleClass().add("blankTextField");
			} else if (NewUser.this.username.getText().equalsIgnoreCase(""))
				NewUser.this.username.getStyleClass().add("blankTextField");
			else if (NewUser.this.password.getText().equalsIgnoreCase(""))
				NewUser.this.password.getStyleClass().add("blankTextField");
			else // Does this username already exist?
			if (new UserDA().getUserByUsername(NewUser.this.username.getText()) == null) {

				// We have a new active user! //WE NEED TO
				PrimaryWindow.setActiveUser(NewUser.this.user);

				// Our user is this person
				NewUser.this.user.setUsername(NewUser.this.username.getText());
				NewUser.this.user.setPassword(NewUser.this.password.getText());

				// Switch panes
				NewUser.this.createUsernamePasswordPane.setVisible(false);
				NewUser.this.setPersonalInformation.setVisible(true);
				NewUser.this.email.requestFocus();
			} else
				NewUser.this.message.setText("That username already exists!");
		}
	}

	class SetPersonalInformation extends BorderPane {

		// VARIABLES

		Button forward;
		Button back;

		public SetPersonalInformation() {

			// INITIALIZING VARIABLES

			Button getUserProfilePicture = new Button();
			getUserProfilePicture.setStyle("-fx-background-color: transparent;");
			ImageView imageView = new ImageView(ResourceManager.getResourceImage("camera-icon.png"));
			imageView.setPreserveRatio(true);
			imageView.setFitHeight(200);
			imageView.setFitWidth(200);
			getUserProfilePicture.setGraphic(imageView);
			getUserProfilePicture.setOnAction(e -> {

				BufferedImage bufferedImage = PrimaryWindow.grabImage();

				if (bufferedImage != null) {
					NewUser.this.user.setPictureExtension(ResourceManager.saveImage(bufferedImage));
					imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
				}
			});

			NewUser.this.email = new TextField();
			NewUser.this.email.setPromptText("Email");
			NewUser.this.email.setMaxWidth(160);

			NewUser.this.phoneNumber = new PhoneTextField();
			NewUser.this.phoneNumber.setPromptText("Phone Number");
			NewUser.this.phoneNumber.setMaxWidth(160);

			// FIELD ACTIONS

			NewUser.this.email.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER)
					NewUser.this.phoneNumber.requestFocus();
			});

			// FORWARD
			this.forward = new Button();
			this.forward.setOnAction(e -> {

				NewUser.this.updateUser();

				NewUser.this.setPersonalInformation.setVisible(false);
				NewUser.this.setUserGender.setVisible(true);

			});
			ImageView rightImage = new ImageView(ResourceManager.getResourceImage("right.png"));
			rightImage.setPreserveRatio(true);
			rightImage.setFitHeight(50);
			this.forward.setGraphic(rightImage);
			this.forward.setStyle("-fx-background-color:transparent;");
			VBox right = new VBox(this.forward);
			right.setAlignment(Pos.CENTER);
			this.setRight(right);
			
			// BACK
			this.back = new Button();
			back.setOnAction(e -> {
				AlertBox alertBox = new AlertBox("Discard change?", 
						"The new user you just created will not be saved.", 
						"Are you sure you want to go back?");
				Optional<ButtonType> result = alertBox.showAndWait();
				 if (result.isPresent() && result.get() == ButtonType.OK) {
					 NewUser.this.user = null;
					 PrimaryWindow.setActiveUser(null);
					 PrimaryWindow.displayWelcomeGUI();
				 }
			});

			ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
			leftImage.setPreserveRatio(true);
			leftImage.setFitHeight(50);
			this.back.setGraphic(leftImage);
			this.back.setStyle("-fx-background-color:transparent;");
			VBox left = new VBox(this.back);
			left.setAlignment(Pos.CENTER);
			this.setLeft(left);

			// ADDING NODES

			VBox centerVBox = new VBox(10, getUserProfilePicture, NewUser.this.email, NewUser.this.phoneNumber);
			centerVBox.setAlignment(Pos.CENTER);

			this.setCenter(centerVBox);
		}
	}

	class SetUserGender extends BorderPane {

		// VARIABLES

		Button forward;
		Button back;

		public SetUserGender() {

			// INITIALIZING VARIABLES

			NewUser.this.male = new Button();
			NewUser.this.male.setGraphic(new ImageView(ResourceManager.getResourceImage("male.png")));
			NewUser.this.male.setStyle("-fx-background-color: transparent;");
			NewUser.this.male.setAlignment(Pos.CENTER);
			NewUser.this.male.setOnAction(e -> {

				NewUser.this.male.setStyle("-fx-background-color: blue;");
				NewUser.this.female.setStyle("-fx-background-color: transparent;");

				NewUser.this.user.setGender("male");
			});

			NewUser.this.female = new Button();
			NewUser.this.female.setGraphic(new ImageView(ResourceManager.getResourceImage("female.png")));
			NewUser.this.female.setStyle("-fx-background-color: transparent;");
			NewUser.this.female.setAlignment(Pos.CENTER);
			NewUser.this.female.setOnAction(e -> {

				NewUser.this.female.setStyle("-fx-background-color: pink;");
				NewUser.this.male.setStyle("-fx-background-color: transparent;");

				NewUser.this.user.setGender("female");
			});

			HBox centerHBox = new HBox(60, NewUser.this.male, NewUser.this.female);
			centerHBox.setAlignment(Pos.CENTER);

			// FORWARD/BACK Button

			this.back = new Button();
			this.back.setOnAction(e -> {

				NewUser.this.setUserGender.setVisible(false);
				NewUser.this.setPersonalInformation.setVisible(true);

				NewUser.this.email.requestFocus();
			});

			ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
			leftImage.setPreserveRatio(true);
			leftImage.setFitHeight(50);
			this.back.setGraphic(leftImage);
			this.back.setStyle("-fx-background-color:transparent;");
			VBox left = new VBox(this.back);
			left.setAlignment(Pos.CENTER);
			this.setLeft(left);

			this.forward = new Button();
			this.forward.setOnAction(e -> {

				NewUser.this.setUserGender.setVisible(false);
				NewUser.this.setUserBirthday.setVisible(true);

				NewUser.this.month.requestFocus();
			});

			ImageView rightImage = new ImageView(ResourceManager.getResourceImage("right.png"));
			rightImage.setPreserveRatio(true);
			rightImage.setFitHeight(50);
			this.forward.setGraphic(rightImage);
			this.forward.setStyle("-fx-background-color:transparent;");
			VBox right = new VBox(this.forward);
			right.setAlignment(Pos.CENTER);
			this.setRight(right);

			// ADDING THE NODES

			this.setCenter(centerHBox);
		}
	}

	class SetUserBirthday extends BorderPane {

		Button forward;
		Button back;

		public SetUserBirthday() {

			Label questionLabel = new Label("When were you born?");

			ObservableList<String> monthOptions = FXCollections.observableArrayList("January", "February", "March",
					"April", "May", "June", "July", "August", "September", "October", "November", "December");

			ObservableList<String> dayOptions = FXCollections.observableArrayList();
			for (int i = 1; i < 32; i++)
				dayOptions.add(Integer.toString(i));

			ObservableList<String> yearOptions = FXCollections.observableArrayList();
			for (int i = 2016; i > 1916; i--)
				yearOptions.add(Integer.toString(i));

			NewUser.this.month = new ComboBox<>(monthOptions);
			NewUser.this.day = new ComboBox<>(dayOptions);
			NewUser.this.year = new ComboBox<>(yearOptions);

			HBox comboBoxHBox = new HBox(5, questionLabel, NewUser.this.month, NewUser.this.day, NewUser.this.year);

			comboBoxHBox.setAlignment(Pos.CENTER);

			this.setCenter(comboBoxHBox);

			this.back = new Button();
			this.back.setOnAction(e -> {

				NewUser.this.updateUser();

				NewUser.this.setUserBirthday.setVisible(false);
				NewUser.this.setUserGender.setVisible(true);

			});
			ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
			leftImage.setPreserveRatio(true);
			leftImage.setFitHeight(50);
			this.back.setGraphic(leftImage);
			this.back.setStyle("-fx-background-color:transparent;");
			VBox left = new VBox(this.back);
			left.setAlignment(Pos.CENTER);
			this.setLeft(left);

			this.forward = new Button();
			this.forward.setOnAction(e -> {

				NewUser.this.updateUser();

				NewUser.this.setUserBirthday.setVisible(false);
				NewUser.this.setUserBasicDetails.setVisible(true);

				NewUser.this.heightTextField.requestFocus();

			});
			ImageView rightImage = new ImageView(ResourceManager.getResourceImage("right.png"));
			rightImage.setPreserveRatio(true);
			rightImage.setFitHeight(50);
			this.forward.setGraphic(rightImage);
			this.forward.setStyle("-fx-background-color:transparent;");
			VBox right = new VBox(this.forward);
			right.setAlignment(Pos.CENTER);
			this.setRight(right);
		}
	}

	class SetUserBasicDetails extends BorderPane {

		Button forward;
		Button back;

		public SetUserBasicDetails() {

			Label heightLabel = new Label("Enter your height (height in centimeters)");
			Label weightLabel = new Label("Enter your weight (weight in pounds)");

			NewUser.this.heightTextField = new IntegerTextField();
			NewUser.this.weightTextField = new IntegerTextField();

			VBox heightVBox = new VBox(5, heightLabel, NewUser.this.heightTextField);
			VBox weightVBox = new VBox(5, weightLabel, NewUser.this.weightTextField);

			heightVBox.setAlignment(Pos.CENTER);
			weightVBox.setAlignment(Pos.CENTER);

			ImageView heightIcon = new ImageView(ResourceManager.getResourceImage("height.png"));
			ImageView weightIcon = new ImageView(ResourceManager.getResourceImage("weight.png"));
			weightIcon.setPreserveRatio(true);
			weightIcon.setFitHeight(200);

			HBox heightHBox = new HBox(10, heightVBox, heightIcon);
			HBox weightHBox = new HBox(10, weightVBox, weightIcon);

			heightHBox.setAlignment(Pos.CENTER);
			weightHBox.setAlignment(Pos.CENTER);

			HBox center = new HBox(70, heightHBox, weightHBox);

			center.setAlignment(Pos.CENTER);

			this.back = new Button();
			this.back.setOnAction(e -> {

				NewUser.this.updateUser();

				NewUser.this.setUserBasicDetails.setVisible(false);
				NewUser.this.setUserBirthday.setVisible(true);

				NewUser.this.month.requestFocus();

			});
			ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
			leftImage.setPreserveRatio(true);
			leftImage.setFitHeight(50);
			this.back.setGraphic(leftImage);
			this.back.setStyle("-fx-background-color:transparent;");
			VBox left = new VBox(this.back);
			left.setAlignment(Pos.CENTER);
			this.setLeft(left);

			this.forward = new Button();
			this.forward.setOnAction(e -> {

				NewUser.this.updateUser();

				NewUser.this.setUserBasicDetails.setVisible(false);
				NewUser.this.setUserAdvancedDetails.setVisible(true);

				NewUser.this.setUserAdvancedDetails.requestFocus();

			});
			ImageView rightImage = new ImageView(ResourceManager.getResourceImage("right.png"));
			rightImage.setPreserveRatio(true);
			rightImage.setFitHeight(50);
			this.forward.setGraphic(rightImage);
			this.forward.setStyle("-fx-background-color:transparent;");
			VBox right = new VBox(this.forward);
			right.setAlignment(Pos.CENTER);
			this.setRight(right);

			this.setCenter(center);
		}
	}

	class SetUserAdvancedDetails extends BorderPane {

		Button back;
		Button forward;

		public SetUserAdvancedDetails() {

			Text placeHolder = new Text("Set User Advanced Details Page");
			
			this.setCenter(placeHolder);
			
			this.back = new Button();
			this.back.setOnAction(e -> {

				NewUser.this.updateUser();

				NewUser.this.setUserAdvancedDetails.setVisible(false);
				NewUser.this.setUserBasicDetails.setVisible(true);

				NewUser.this.heightTextField.requestFocus();

			});
			ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
			leftImage.setPreserveRatio(true);
			leftImage.setFitHeight(50);
			this.back.setGraphic(leftImage);
			this.back.setStyle("-fx-background-color:transparent;");
			VBox left = new VBox(this.back);
			left.setAlignment(Pos.CENTER);
			this.setLeft(left);

			this.forward = new Button();
			this.forward.setOnAction(e -> {

				NewUser.this.updateUser();

				NewUser.this.saveUser();

				PrimaryWindow.editUserGUI();

			});
			ImageView rightImage = new ImageView(ResourceManager.getResourceImage("right.png"));
			rightImage.setPreserveRatio(true);
			rightImage.setFitHeight(50);
			this.forward.setGraphic(rightImage);
			this.forward.setStyle("-fx-background-color:transparent;");
			VBox right = new VBox(this.forward);
			right.setAlignment(Pos.CENTER);
			this.setRight(right);
		}
	}

	public void updateUser() {

		if ((this.email.getText() != null) && !this.email.getText().equals(""))
			this.user.setEmail(this.email.getText());
		if ((this.phoneNumber.getText() != null) && !this.phoneNumber.getText().equals(""))
			this.user.setPhoneNumber(this.phoneNumber.getText());

		if ((this.month.getValue() != null) && (this.day.getValue() != null) && (this.year.getValue() != null))
			this.user.setDateOfBirth(this.month.getValue(), Integer.parseInt(this.day.getValue()),
					Integer.parseInt(this.year.getValue()));

		if ((this.weightTextField.getText() != null) && !this.weightTextField.getText().equals(""))
			this.user.setWeight(Integer.parseInt(this.weightTextField.getText()));
		if ((this.heightTextField.getText() != null) && !this.heightTextField.getText().equals(""))
			this.user.setHeight(Integer.parseInt(this.heightTextField.getText()));
	}

	private void saveUser() {
		new UserDA().saveUser(this.user);
	}
}
