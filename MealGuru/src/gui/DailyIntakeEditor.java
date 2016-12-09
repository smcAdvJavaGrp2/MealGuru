package gui;

import java.util.ArrayList;
import java.util.Random;

import data.mealguru.DailyIntakeDA;
import edible.DailyIntake;
import edible.Meal;
import gui.smartnode.CancelButton;
import gui.smartnode.DailyIntakeLabel;
import gui.smartnode.EdibleLabel;
import gui.smartnode.MealSearchBar;
import gui.smartnode.SubmitButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DailyIntakeEditor extends BorderPane {

	HBox centerHBox;

	VBox leftVBox;
	VBox rightVBox;

	// DAILY INTAKE
	DailyIntakeLabel dailyIntakeLabel;

	// SEARCH BAR OBJECTS
	MealSearchBar mealSearchBar;

	// SEARCH RESULTS BUTTONS
	ScrollPane resultsScrollPane;
	VBox results;

	// FINISHED
	CancelButton cancel;
	SubmitButton submit;

	Guru guru;
	
	String[] tips = { "To create a new meal, press the fruit bowl icon ",
			"To find a meal you've entered/recorded before, type its name and press the magnifying glass.", 
			"Click the photo of meal item.", 
			"Press Submit and it will be recorded in your daily intake for that day."};
	
	public DailyIntakeEditor(DailyIntake dailyIntake, Guru guru) {
		this.guru = guru;
		this.guru.setScript(tips);
		this.dailyIntakeLabel = new DailyIntakeLabel(dailyIntake, guru);
		this.dailyIntakeLabel.setAddMealButtonVisible(false);
		
		// SEARCH FOR AN EXISTING MEAL
		this.mealSearchBar = new MealSearchBar();

		this.mealSearchBar.getSearchButton().setOnAction(e -> {
			this.guru.setSpeechMessage( tips[new Random().nextInt(tips.length)]);				

			this.populateResults(this.mealSearchBar.getValues());

		});

		this.mealSearchBar.getAutoCompleteTextField().setOnKeyReleased(e -> {

			if (e.getCode() == KeyCode.ENTER)
				this.populateResults(this.mealSearchBar.getValues());

		});

		// RESULTS OF A SEARCH

		this.results = new VBox(10);
		this.results.setPadding(new Insets(10));

		this.resultsScrollPane = new ScrollPane(this.results);
		this.resultsScrollPane.getStyleClass().add("scroll-pane-inner-shadow");
		this.resultsScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.resultsScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		this.resultsScrollPane.setMaxWidth(380);

		// FINISHED EDITs
		this.submit = new SubmitButton();
		this.submit.setOnAction(e -> {

			DailyIntakeDA dailyIntakeDA = new DailyIntakeDA();

			dailyIntakeDA.deleteAllFrom(dailyIntake.getDate());

			dailyIntake.setID(dailyIntakeDA.saveDailyIntake(dailyIntake));

			EdibleLableController.renderEdibleLabels(this.dailyIntakeLabel.getDailyIntake());

			SecondaryStage.closeDailyIntakeEditor();

		});

		// CANCEL EDITs
		this.cancel = new CancelButton();
		this.cancel.setOnAction(e -> {

			SecondaryStage.closeDailyIntakeEditor();

		});

		// BUIDING THE GUI

		this.leftVBox = new VBox(5, this.mealSearchBar, this.resultsScrollPane);
		Region spring = new Region();
		VBox.setVgrow(spring, Priority.ALWAYS);

		Region spring2 = new Region();
		VBox.setVgrow(spring2, Priority.ALWAYS);

		Region spring3 = new Region();
		VBox.setVgrow(spring3, Priority.ALWAYS);

		Region spring4 = new Region();
		VBox.setVgrow(spring4, Priority.ALWAYS);

		Region spring5 = new Region();
		VBox.setVgrow(spring5, Priority.ALWAYS);

		Region spring6 = new Region();
		VBox.setVgrow(spring6, Priority.ALWAYS);

		Text searchMealText = new Text("Search for a meal");

		this.leftVBox = new VBox(10, new Separator(), searchMealText, this.mealSearchBar, new Separator(),
				this.resultsScrollPane, spring3, new Separator());
		this.leftVBox.getStyleClass().add("box");
		this.leftVBox.setAlignment(Pos.TOP_CENTER);
		this.leftVBox.setAlignment(Pos.CENTER);
		this.leftVBox.setPadding(new Insets(5));

		HBox buttonHBox = new HBox(5, this.submit, this.cancel);
		buttonHBox.setAlignment(Pos.BASELINE_RIGHT);

		Text dailyIntakeText = new Text("Daily Intake");

		this.rightVBox = new VBox(15, new Separator(), dailyIntakeText, new Separator(), spring2, this.dailyIntakeLabel,
				spring, new Separator(), buttonHBox);
		this.rightVBox.getStyleClass().add("box");
		this.rightVBox.setPadding(new Insets(15));
		this.rightVBox.setAlignment(Pos.CENTER);

		this.centerHBox = new HBox(20, this.leftVBox, this.rightVBox);
		this.centerHBox.setAlignment(Pos.CENTER);
		this.centerHBox.setPadding(new Insets(15));

		this.setCenter(this.centerHBox);

	}

	public void populateResults(ArrayList<Meal> mealsToPopulateSearch) {

		this.results.getChildren().clear();

		for (int i = 0; i < mealsToPopulateSearch.size(); i++) {

			Meal meal = mealsToPopulateSearch.get(i);
			EdibleLabel temp = new EdibleLabel(meal, 140);

			temp.setOnMouseClicked(e -> {
			
				this.addMeal(meal);

			});

			this.results.getChildren().add(temp);

		}

	}

	public void addMeal(Meal meal) {

		this.dailyIntakeLabel.addMeal(meal);

	}

}
