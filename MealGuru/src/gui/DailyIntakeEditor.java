package gui;

import java.util.ArrayList;

import data.mealguru.DailyIntakeDA;
import edible.DailyIntake;
import edible.Meal;
import gui.smartnode.DailyIntakeLabel;
import gui.smartnode.EdibleLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DailyIntakeEditor extends BorderPane {

	// DAILY INTAKE BUTTON
	DailyIntakeLabel dailyIntakeLabel;

	// SEARCH BAR OBJECTS
	HBox searchBar;
	TextField searchExistingMeals;
	Button search;

	// SEARCH RESULTS BUTTONS
	VBox results;
	ScrollPane resultsScrollPane;

	// CREATE A NEW MEAL FOR dailyIntake
	Button addNewMeal;

	// FINISHED
	Button finishedAndSave;

	// CANCEL
	Button cancel;

	public DailyIntakeEditor(DailyIntake dailyIntake) {

		this.dailyIntakeLabel = new DailyIntakeLabel(dailyIntake);
		this.dailyIntakeLabel.setAddMealButtonVisible(false);

		// SEARCH FOR AN EXISTING MEAL
		this.searchExistingMeals = new TextField();
		this.search = new Button("Search");

		// RESULTS OF A SEARCH
		this.results = new VBox();

		// CREATE A NEW MEAL
		this.addNewMeal = new Button("New Meal");
		this.addNewMeal.setOnAction(event -> {

			SecondaryStage.showMealEditor(new Meal(), false);

		});

		// FINISHED EDITs
		this.finishedAndSave = new Button("Finish and Save");
		this.finishedAndSave.setOnAction(e -> {

			DailyIntakeDA dailyIntakeDA = new DailyIntakeDA();

			dailyIntakeDA.deleteAllFrom(dailyIntake.getDate());

			dailyIntake.setID(dailyIntakeDA.saveDailyIntake(dailyIntake));

			EdibleLableController.renderEdibleLabels(this.dailyIntakeLabel.getDailyIntake());

			SecondaryStage.closeDailyIntakeEditor();

		});

		// CANCEL EDITs
		this.cancel = new Button("Cancel");
		this.cancel.setOnAction(e -> {

			SecondaryStage.closeDailyIntakeEditor();

		});

		// BUIDING THE GUI

		this.searchBar = new HBox(5, new Label("Search for Existing Meals"), this.searchExistingMeals, this.search);
		this.searchBar.setPadding(new Insets(10));
		this.searchBar.setAlignment(Pos.CENTER);
		this.setTop(this.searchBar);

		this.results.setPadding(new Insets(5));
		this.results.setSpacing(5);
		this.results.setAlignment(Pos.CENTER);

		this.resultsScrollPane = new ScrollPane(this.results);
		this.setCenter(this.resultsScrollPane);
		this.setRight(this.dailyIntakeLabel);
		this.setBottom(new HBox(this.addNewMeal, this.finishedAndSave, this.cancel));

	}

	public void populateResults(ArrayList<Meal> mealsToPopulateSearch) {

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
