package gui;

import data.mealguru.UserDA;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import user.User;
import utility.ResourceManager;

class SplashPageGUI extends BorderPane {
	Text message;
	TextField username;
	TextField password;
	Button submit;
	Button newUser;
	Guru guru;
	String[] tips = {
			"Hi welcome to MealGuru, I am the MealGuru! I'm here to assist you!",
			"MealGuru lets you create meals and track your nutrition.",
			"You can eat healthy, I'm here to help you!",
			"If this is your first time here, click on 'New User' to make a new account.",
			"If you forgot your password, you're shit out of luck"};

	public SplashPageGUI() {

		// Create Guru and start animation
		this.guru = new Guru(200,200);
		this.guru.startAnimation();
		// Supposed to return a random String from tip array
		this.guru.enableTips(tips);

		// Attempt to move guru to mouse click
		this.setOnMouseClicked(e -> {
			this.guru.move(e.getSceneX(),e.getSceneY());
	        });        
		
		// GRAPHICS
		
		ImageView genie = new ImageView(ResourceManager.getResourceImage("logo.png"));
		genie.setPreserveRatio(true);
		genie.setFitHeight(200);
		

		// ERROR MESSAGE
		
		this.message = new Text();
		this.message.setFill(Color.RED);
		this.message.maxWidth(150);

		// ACCOUNT INFORMATION

		this.username = new TextField();
		this.username.setPromptText("Username");
		this.username.setOnMouseClicked(e -> {
			this.username.selectAll();
		});
		this.username.setPadding(new Insets(5));
		this.username.setMaxWidth(150);

		this.password = new PasswordField();
		this.password.setPromptText("Password");
		this.password.setOnMouseClicked(e -> {
			this.password.selectAll();
		});
		this.password.setPadding(new Insets(5));
		this.password.setMaxWidth(150);

		this.submit = new Button("Submit");
		this.submit.setMaxWidth(150);
		
		// SUBMIT INFORMATION

		this.username.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				this.password.requestFocus();
		});
		this.password.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER)
				this.submit();
		});
		this.submit.setOnAction(e -> {
			this.submit();
		});
				
		// CREATE A NEW USER

		this.newUser = new Button("New User");
		this.newUser.setMaxWidth(150);
		this.newUser.setOnAction(e -> {

			PrimaryWindow.displayNewUserGUI();

		});

		VBox center = new VBox(6, genie, this.message, this.username, this.password, this.submit, this.newUser);
		center.setAlignment(Pos.CENTER);
		this.setCenter(center);
		
		// Add guru to this borderPane and set its x, y position
		this.getChildren().add(this.guru);


	}

	public void submit() {

		this.username.getStyleClass().remove("blankTextField");
		this.password.getStyleClass().remove("blankTextField");

		if (this.username.getText().equalsIgnoreCase("") && this.password.getText().equalsIgnoreCase("")) {
			this.username.getStyleClass().add("blankTextField");
			this.password.getStyleClass().add("blankTextField");
		} 
		else if (this.username.getText().equalsIgnoreCase(""))
			this.username.getStyleClass().add("blankTextField");
		
		else if (this.password.getText().equalsIgnoreCase(""))
			this.password.getStyleClass().add("blankTextField");
		
		else {
			UserDA userDA = new UserDA();
			User account = userDA.getUserByUsername(this.username.getText());

			if ((account != null) && account.isPasswordCorrect(this.password.getText())) {
				PrimaryWindow.setActiveUser(account);

				PrimaryWindow.displayMainGUI();
			}

			else this.message.setText("Invalid username or password!");
		}
	}
}