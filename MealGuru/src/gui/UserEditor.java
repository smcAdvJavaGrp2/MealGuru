package gui;

import java.awt.image.BufferedImage;
import java.io.File;

import data.mealguru.UserDA;
import gui.smartnode.CancelButton;
import gui.smartnode.IntegerTextField;
import gui.smartnode.PhoneTextField;
import gui.smartnode.SubmitButton;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utility.ResourceManager;

class UserEditor extends BorderPane {

	//

	ColorPicker primaryColorPicker;
	String primaryColorString;

	ColorPicker secondaryColorPicker;
	String secondaryColorString;

	ColorPicker complimentaryColorPicker;
	String complimentaryColorString;

	ColorPicker buttonColorPicker;
	String buttonColorString;

	CheckBox updateCSS;
	CheckBox doNotUpdateCSS;

	//

	Button getUserPicture;

	TextField username;
	PasswordField passwordField;

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

		this.getUserPicture = new Button();
		this.getUserPicture.setStyle("-fx-background-color: transparent;");
		ImageView imageView = new ImageView(
				ResourceManager.getImage(PrimaryWindow.getActiveUser().getPictureExtension()));
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		this.getUserPicture.setGraphic(imageView);
		this.getUserPicture.setOnAction(e -> {

			BufferedImage bufferedImage = PrimaryWindow.grabImage();

			if (bufferedImage != null) {
				PrimaryWindow.getActiveUser().setPictureExtension(ResourceManager.saveImage(bufferedImage));
				imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
			}

		});

		this.username = new TextField(PrimaryWindow.getActiveUser().getUsername());
		this.username.setEditable(false);

		this.email = new TextField(PrimaryWindow.getActiveUser().getEmail());
		this.phoneNumber = new PhoneTextField(PrimaryWindow.getActiveUser().getPhoneNumber());

		this.male = new Button();
		this.female = new Button();

		ImageView maleImageView = new ImageView(ResourceManager.getResourceImage("male.png"));
		maleImageView.setPreserveRatio(true);
		maleImageView.setFitHeight(50);
		this.male.setGraphic(maleImageView);
		this.male.setStyle("-fx-background-color: transparent;");
		this.male.setAlignment(Pos.CENTER);
		if (PrimaryWindow.getActiveUser().getGender().equalsIgnoreCase("MALE"))
			this.male.setStyle("-fx-background-color: blue;");
		this.male.setOnAction(e -> {

			this.male.setStyle("-fx-background-color: blue;");
			this.female.setStyle("-fx-background-color: transparent;");

			PrimaryWindow.getActiveUser().setGender("male");

		});

		ImageView femaleImageView = new ImageView(ResourceManager.getResourceImage("female.png"));
		femaleImageView.setPreserveRatio(true);
		femaleImageView.setFitHeight(50);
		this.female.setGraphic(femaleImageView);
		this.female.setStyle("-fx-background-color: transparent;");
		this.female.setAlignment(Pos.CENTER);
		if (PrimaryWindow.getActiveUser().getGender().equalsIgnoreCase("FEMALE"))
			this.female.setStyle("-fx-background-color: pink;");
		this.female.setOnAction(e -> {

			this.female.setStyle("-fx-background-color: pink;");
			this.male.setStyle("-fx-background-color: transparent;");

			PrimaryWindow.getActiveUser().setGender("female");

		});

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

		String birthday = PrimaryWindow.getActiveUser().getDateOfBirth();
		String[] birthdaySplit = birthday.split("-");
		System.out.println(birthday);

		if (Integer.parseInt(birthdaySplit[1]) == 0)
			this.month.setValue(monthOptions.get(11));
		else
			this.month.setValue(monthOptions.get(Integer.parseInt(birthdaySplit[1]) - 1));
		this.day.setValue(birthdaySplit[2]);
		this.year.setValue(birthdaySplit[0]);

		HBox comboBoxHBox = new HBox(5, this.month, this.day, this.year);

		comboBoxHBox.setAlignment(Pos.CENTER);

		this.height = new IntegerTextField(PrimaryWindow.getActiveUser().getHeight());
		this.weight = new IntegerTextField(PrimaryWindow.getActiveUser().getWeight());

		this.cancelChanges = new CancelButton();
		this.cancelChanges.setOnAction(e -> {
			PrimaryWindow.displayMainGUI();
		});

		this.submitChanges = new SubmitButton();
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

			if ((this.primaryColorString != null) || (this.secondaryColorString != null)
					|| (this.buttonColorString != null)) {

				File file = null;
				if ((ResourceManager.getUserCSS(PrimaryWindow.getActiveUser().getCustomCSSExtension()) != null)
						&& (file = new File(
								ResourceManager.getUserCSS(PrimaryWindow.getActiveUser().getCustomCSSExtension())
										.replaceAll("file:", ""))).exists())
					file.delete();

				PrimaryWindow.getActiveUser().setCustomCSSExtension(ResourceManager.saveCSS(this.primaryColorString,
						this.secondaryColorString, this.buttonColorString));

			}

			if (this.updateCSS.isSelected() == true)
				PrimaryWindow.getActiveUser().setUpdateCSS(true);

			new UserDA().updateUser(PrimaryWindow.getActiveUser());

			PrimaryWindow.displayMainGUI();

		});

		HBox maleFemaleButtons = new HBox(10, this.male, this.female);
		maleFemaleButtons.setAlignment(Pos.CENTER);

		HBox submitCancelButtons = new HBox(10, this.submitChanges, this.cancelChanges);
		submitCancelButtons.setAlignment(Pos.BASELINE_RIGHT);

		HBox heightWeightFields = new HBox(10, this.height, this.weight);
		heightWeightFields.setAlignment(Pos.CENTER);

		Text personalInformation = new Text("Personal Information");
		Text profilePicture = new Text("Profile Picture");
		Text userName = new Text("Username");
		Text emailPhoneNumber = new Text("Email/Phone number");
		Text gender = new Text("Gender");
		Text birthdayText = new Text("Birthdate?");
		Text heightWeight = new Text("Height/Weight");

		Region spring = new Region();
		VBox.setVgrow(spring, Priority.ALWAYS);

		Region spring1 = new Region();
		VBox.setVgrow(spring1, Priority.ALWAYS);

		VBox rightVBox = new VBox(5, new Separator(), personalInformation, new Separator(), spring1, new Separator(),
				profilePicture, this.getUserPicture, new Separator(), userName, this.username, new Separator(),
				emailPhoneNumber, this.email, this.phoneNumber, new Separator(), gender, maleFemaleButtons,
				new Separator(), birthdayText, comboBoxHBox, new Separator(), heightWeight, heightWeightFields,
				new Separator(), spring, new Separator(), submitCancelButtons);
		rightVBox.setAlignment(Pos.CENTER);
		rightVBox.setPadding(new Insets(10));
		rightVBox.getStyleClass().add("box");

		// Color Stuff

		// Example Pane

		BorderPane background = new BorderPane();

		background.setStyle("-fx-background-color: backgroundcolor;"
				+ "-fx-effect: innershadow(gaussian , highopacityblack, 15, 0, 0, 0);");
		background.setMinSize(300, 300);

		ImageView exampleImageView = new ImageView(ResourceManager.getResourceImage("Sunday.PNG"));
		exampleImageView.setPreserveRatio(true);
		exampleImageView.setFitWidth(100);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPadding(new Insets(5));
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setStyle("-fx-effect: innershadow(gaussian , highopacityblack, 15, 0, 0, 0);"
				+ "-fx-base: backgroundcolor;" + "-fx-background-color: backgroundcolor;");

		Button button = new Button("Example Button");
		button.setOnAction(e -> {
			this.defaultColorSchema();
		});
		button.setStyle("-fx-background-color: buttoncolor;");

		VBox vBox = new VBox(5, exampleImageView, scrollPane, button);
		vBox.setAlignment(Pos.CENTER);
		vBox.setMaxSize(200, 200);
		vBox.setPadding(new Insets(10));
		vBox.setStyle("-fx-effect: dropshadow(three-pass-box, highopacityblack, 15, 0, 0, 0);"
				+ "-fx-background-color: boxcolor;" + "-fx-background-radius: 15;");

		background.setCenter(vBox);

		this.primaryColorPicker = new ColorPicker();
		this.primaryColorPicker.valueProperty().addListener(e -> {

			Color color = this.primaryColorPicker.getValue();

			this.primaryColorString = this.getHexStringFromColor(color);

			background.setStyle("-fx-background-color: " + this.primaryColorString + ";"
					+ "-fx-effect: innershadow(gaussian , highopacityblack, 15, 0, 0, 0);");

			scrollPane.setStyle(
					"-fx-effect: innershadow(gaussian , highopacityblack, 15, 0, 0, 0);" + "-fx-background-color: "
							+ this.primaryColorString + ";" + "-fx-base: " + this.primaryColorString + ";");

		});

		this.secondaryColorPicker = new ColorPicker();
		this.secondaryColorPicker.valueProperty().addListener(e -> {

			Color color = this.secondaryColorPicker.getValue();

			this.secondaryColorString = this.getHexStringFromColor(color);

			vBox.setStyle("-fx-effect: dropshadow(three-pass-box, highopacityblack, 15, 0, 0, 0);"
					+ "-fx-background-color: " + this.secondaryColorString + ";" + "-fx-background-radius: 15;");

		});
		this.secondaryColorPicker.setValue(Color.LIGHTGRAY);

		this.buttonColorPicker = new ColorPicker();
		this.buttonColorPicker.valueProperty().addListener(e -> {

			Color color = this.buttonColorPicker.getValue();

			this.buttonColorString = this.getHexStringFromColor(color);

			button.setStyle("-fx-background-color: " + this.buttonColorString + ";");

		});
		this.buttonColorPicker.setValue(Color.web("#cccccc"));

		this.defaultColorSchema();

		this.updateCSS = new CheckBox("Update CSS");
		this.doNotUpdateCSS = new CheckBox("Do Not Update");

		this.updateCSS.selectedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue,
				newValue) -> UserEditor.this.doNotUpdateCSS.setSelected(!newValue));

		this.doNotUpdateCSS.selectedProperty().addListener((ChangeListener<Boolean>) (observable, oldValue,
				newValue) -> UserEditor.this.updateCSS.setSelected(!newValue));

		if (PrimaryWindow.getActiveUser().getUpdateCSS()) {
			this.doNotUpdateCSS.setSelected(false);
			this.updateCSS.setSelected(true);
		} else {
			this.updateCSS.setSelected(false);
			this.doNotUpdateCSS.setSelected(true);
		}

		VBox vBoxCheckBoxes = new VBox(5);
		vBoxCheckBoxes.getChildren().addAll(this.updateCSS, this.doNotUpdateCSS);
		vBoxCheckBoxes.setPadding(new Insets(10));
		vBoxCheckBoxes.getStyleClass().add("box");

		Text backGroundColor = new Text("Background Color");
		Text foreGroundColor = new Text("Foreground Color");
		Text buttonColor = new Text("Button Color");
		Text sample = new Text("Color Sample");
		Text updateCSS = new Text("Save your style?");

		Text colorVBoxText = new Text("Set a style");

		Region spring3 = new Region();
		VBox.setVgrow(spring3, Priority.ALWAYS);

		Region spring4 = new Region();
		VBox.setVgrow(spring4, Priority.ALWAYS);

		Region spring5 = new Region();
		VBox.setVgrow(spring5, Priority.ALWAYS);

		VBox colorVBox = new VBox(5, new Separator(), colorVBoxText, new Separator(), spring3, new Separator(),
				backGroundColor, this.primaryColorPicker, new Separator(), foreGroundColor, this.secondaryColorPicker,
				new Separator(), buttonColor, this.buttonColorPicker, new Separator(), sample, background,
				new Separator(), updateCSS, vBoxCheckBoxes, new Separator(), spring5, new Separator());
		colorVBox.setAlignment(Pos.CENTER);
		colorVBox.getStyleClass().add("box");

		// All Together

		HBox centerHBox = new HBox(20, colorVBox, rightVBox);
		centerHBox.setPadding(new Insets(10));
		centerHBox.setAlignment(Pos.CENTER);

		this.setCenter(centerHBox);

	}

	public String getHexStringFromColor(Color color) {

		return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));

	}

	public void defaultColorSchema() {

		this.primaryColorPicker.setValue(Color.DARKGRAY);
		this.buttonColorPicker.setValue(Color.web("#cccccc"));
		this.secondaryColorPicker.setValue(Color.LIGHTGRAY);

	}

}