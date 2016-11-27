package gui;

import java.util.Date;

import data.DailyIntakeDA;
import edible.DailyIntake;
import gui.smartnode.DailyIntakeLabel;
import gui.smartnode.UserLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.ResourceManager;

class MainGUI extends BorderPane {

	private UserLabel userLabel;

	Date centerDay;
	
	HBox dailyIntakeHBox;

	DailyIntakeLabel threeDaysAgoLabel;
	DailyIntakeLabel twoDaysAgoLabel;
	DailyIntakeLabel oneDaysAgoLabel;
	DailyIntakeLabel centerDayLabel;
	DailyIntakeLabel oneDayFromNowLabel;
	DailyIntakeLabel twoDaysFromNowLabel;
	DailyIntakeLabel threeDaysFromNowLabel;

	Button future;
	Button past;

	public MainGUI() {

		this.userLabel = new UserLabel();
		this.userLabel.setOnAction(e -> {
			PrimaryWindow.setActiveUser(null);
			PrimaryWindow.displayWelcomeGUI();
		});
		this.setTop(this.userLabel);

		centerDay = new Date();
		
		this.past = new Button();
		ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
		leftImage.setPreserveRatio(true);
		leftImage.setFitHeight(50);
		this.past.setGraphic(leftImage);
		this.past.setStyle("-fx-background-color:transparent;");
		this.past.setOnAction(e -> {
			
			centerDay.setTime(centerDay.getTime() - 1 * 24 * 60 * 60 * 1000);
			
			refreshDailyIntakeLabels();
			
		});
		VBox left = new VBox(this.past);
		left.setAlignment(Pos.CENTER);
		this.setLeft(left);
		
		refreshDailyIntakeLabels();
		
		this.dailyIntakeHBox = new HBox(this.threeDaysAgoLabel, this.twoDaysAgoLabel, this.oneDaysAgoLabel,
				this.centerDayLabel, this.oneDayFromNowLabel, this.twoDaysFromNowLabel, this.threeDaysFromNowLabel);
		
		this.dailyIntakeHBox.setAlignment(Pos.CENTER);

		this.setCenter(this.dailyIntakeHBox);

		// RIGHT

		this.future = new Button();
		ImageView rightImage = new ImageView(ResourceManager.getResourceImage("right.png"));
		rightImage.setPreserveRatio(true);
		rightImage.setFitHeight(50);
		this.future.setGraphic(rightImage);
		this.future.setStyle("-fx-background-color:transparent;");
		this.future.setOnAction(e -> {
			
			centerDay.setTime(centerDay.getTime() + 1 * 24 * 60 * 60 * 1000);
			
			refreshDailyIntakeLabels();
			
		});
		
		VBox right = new VBox(this.future);
		right.setAlignment(Pos.CENTER);
		this.setRight(right);

		// BOTTOM

	}
	
	public void refreshDailyIntakeLabels() {
		
		DailyIntakeDA dailyIntakeDA = new DailyIntakeDA();
		
		Date dateToDisplay = new Date();
		dateToDisplay.setTime(centerDay.getTime());
		
		dateToDisplay.setTime(dateToDisplay.getTime() - 3 * 24 * 60 * 60 * 1000);
		
		if(this.threeDaysAgoLabel == null) 
			this.threeDaysAgoLabel = new DailyIntakeLabel(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		else 
			this.threeDaysAgoLabel.setDailyIntake(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));

		dateToDisplay.setTime(dateToDisplay.getTime() + 1 * 24 * 60 * 60 * 1000);
		
		if(this.twoDaysAgoLabel == null) 
			this.twoDaysAgoLabel = new DailyIntakeLabel(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		else
			this.twoDaysAgoLabel.setDailyIntake(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		
		dateToDisplay.setTime(dateToDisplay.getTime() + 1 * 24 * 60 * 60 * 1000);
		
		if(this.oneDaysAgoLabel == null) 
			this.oneDaysAgoLabel = new DailyIntakeLabel(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		else 
			this.oneDaysAgoLabel.setDailyIntake(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		
		dateToDisplay.setTime(dateToDisplay.getTime() + 1 * 24 * 60 * 60 * 1000);
		
		if(this.centerDayLabel == null) 
			this.centerDayLabel = new DailyIntakeLabel(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		else 
			this.centerDayLabel.setDailyIntake(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		
		dateToDisplay.setTime(dateToDisplay.getTime() + 1 * 24 * 60 * 60 * 1000);
		
		if(this.oneDayFromNowLabel == null) 
			this.oneDayFromNowLabel = new DailyIntakeLabel(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		else
			this.oneDayFromNowLabel.setDailyIntake(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		
		dateToDisplay.setTime(dateToDisplay.getTime() + 1 * 24 * 60 * 60 * 1000);
		
		if(this.twoDaysFromNowLabel == null) 
			this.twoDaysFromNowLabel = new DailyIntakeLabel(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		else
			this.twoDaysFromNowLabel.setDailyIntake(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		
		dateToDisplay.setTime(dateToDisplay.getTime() + 1 * 24 * 60 * 60 * 1000);
		
		if(this.threeDaysFromNowLabel == null) 
			this.threeDaysFromNowLabel = new DailyIntakeLabel(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		else
			this.threeDaysFromNowLabel.setDailyIntake(dailyIntakeDA.getDailyIntakeByDay(dateToDisplay));
		
	}

}