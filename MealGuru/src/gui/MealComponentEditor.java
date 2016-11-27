package gui;

import java.util.ArrayList;

import data.MealComponentDA;
import edible.Food;
import edible.MealComponent;
import gui.smartnode.CancelButton;
import gui.smartnode.DoubleTextField;
import gui.smartnode.EdibleLabel;
import gui.smartnode.FoodSearchBar;
import gui.smartnode.NutritionLabel;
import gui.smartnode.SmartChoiceBox;
import gui.smartnode.SubmitButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utility.Amount;
import utility.UnitClassification;

public class MealComponentEditor extends BorderPane {

	boolean editingExistingMealComponent;

	MealComponent mealComponent;

	NutritionLabel nutritionLabel;

	Button createNewFood;

	FoodSearchBar searchBar;

	ScrollPane resultsScrollPane;
	VBox results;

	EdibleLabel selectedFoodLabel;

	DoubleTextField amountOfFood;
	SmartChoiceBox unitsOfMeasure;
	HBox amountOfFoodHBox;

	CancelButton cancel;
	SubmitButton submit;

	VBox rightVBox;

	// CONSTRUCTORS

	public MealComponentEditor(MealComponent mealComponent, boolean editingExistingMealComponent) {

		this.editingExistingMealComponent = editingExistingMealComponent;

		this.mealComponent = mealComponent;

		this.searchBar = new FoodSearchBar();
		this.searchBar.getSearchButton().setOnAction(e -> {

			this.populateResults(this.searchBar.getValues());

		});

		this.results = new VBox();

		this.resultsScrollPane = new ScrollPane(this.results);
		this.resultsScrollPane.setMaxHeight(550);

		VBox leftVBox = new VBox(this.searchBar, this.resultsScrollPane);
		leftVBox.getStyleClass().add("box");
		leftVBox.setAlignment(Pos.TOP_CENTER);
		leftVBox.setPadding(new Insets(5));

		ListView<String> listView = new ListView<>();
		listView.getItems().addAll("Hey", "how are you");
		listView.setMaxHeight(100);

		StackPane leftStackPane = new StackPane();
		leftStackPane.getChildren().addAll(leftVBox /* , listView */);

		if (mealComponent.getAmount() != null)
			this.amountOfFood = new DoubleTextField(mealComponent.getAmount().getMeasure());
		else
			this.amountOfFood = new DoubleTextField();

		this.amountOfFood.setOnKeyReleased(e -> {

			this.setAmount();
			this.redrawLabel();

		});

		this.unitsOfMeasure = new SmartChoiceBox();
		this.unitsOfMeasure.valueProperty().addListener(e -> {

			this.setAmount();
			this.redrawLabel();

		});

		this.amountOfFoodHBox = new HBox(5, this.amountOfFood, this.unitsOfMeasure);
		this.amountOfFoodHBox.setAlignment(Pos.CENTER);

		this.selectedFoodLabel = new EdibleLabel(new Food());

		this.nutritionLabel = new NutritionLabel(mealComponent);
		this.nutritionLabel.setPreserveRatio(true);
		this.nutritionLabel.setFitWidth(320);

		this.submit = new SubmitButton();
		this.submit.setOnAction(event -> {
			
			if(this.mealComponent == null || this.mealComponent.getFood() == null || this.mealComponent.getAmount() == null) {
				AlertBox invalidInput = new AlertBox("Invalid MealComponent", "A value you've entered is missing or invalid. Please check your information.",
						"A meal component requires a decimal amount of an ingredient and a unit classification (e.g. grams, cup, milligram, unit).");
				return;
			}
			
			if (editingExistingMealComponent) {
				
				EdibleLableController.renderEdibleLabels(this.getMealComponent());

				new MealComponentDA().updateMealComponent(this.getMealComponent());

			}

			SecondaryStage.closeMealComponentEditor();

		});

		this.cancel = new CancelButton();
		this.cancel.setOnAction(event -> {

			this.mealComponent = null;

			SecondaryStage.closeMealComponentEditor();

		});

		HBox submitCancelHBox = new HBox(5, this.submit, this.cancel);
		submitCancelHBox.setAlignment(Pos.CENTER);

		this.rightVBox = new VBox(5, this.selectedFoodLabel, this.amountOfFoodHBox, this.nutritionLabel,
				submitCancelHBox);
		this.rightVBox.getStyleClass().add("box");
		this.rightVBox.setPadding(new Insets(5));
		this.rightVBox.setAlignment(Pos.CENTER);

		HBox displayHBox = new HBox(10, leftStackPane, this.rightVBox);
		displayHBox.setAlignment(Pos.CENTER);
		displayHBox.setPadding(new Insets(15));

		this.setCenter(displayHBox);

		// IF YOU'RE EDITING A MEAL COMPONENT - THIS WILL SET THE VALUES IN THE
		// GUI TO MATCH THAT MEAL COMPONENT

		System.out.println((mealComponent == null) + " " + (mealComponent.getFood() == null));

		if (mealComponent.getFood() != null)
			this.selectFood(mealComponent.getFood());

		if (mealComponent.getAmount() != null)
			this.unitsOfMeasure.setValue(mealComponent.getAmount().getUnits());

	}

	public void populateResults(ArrayList<Food> foodsToPopulateSearch) {

		this.results.getChildren().clear();

		for (Food food : foodsToPopulateSearch) {

			EdibleLabel temp = new EdibleLabel(food);

			temp.setOnMouseClicked(e -> {
				this.selectFood(food);
				this.redrawLabel();
			});

			this.results.getChildren().add(temp);

		}

	}

	public void redrawLabel() {

		this.nutritionLabel.redrawLabel(this.getMealComponent());

	}

	public void selectFood(Food food) {

		this.mealComponent.setFood(food);

		this.rightVBox.getChildren().remove(this.selectedFoodLabel);
		this.selectedFoodLabel = new EdibleLabel(this.mealComponent.getFood());

		this.rightVBox.getChildren().add(0, this.selectedFoodLabel);

		this.unitsOfMeasure.clearAll();

		if (this.mealComponent.getFood() != null)
			this.unitsOfMeasure.addAvailableMeasurements(UnitClassification.SERVING);

		if (this.mealComponent.getFood().getUnitsPerServingSize() != null)
			this.unitsOfMeasure.addAvailableMeasurements(UnitClassification.UNIT);

		if (this.mealComponent.getFood().getWeightPerServingSize() != null)
			this.unitsOfMeasure.addAvailableMeasurements(UnitClassification.WEIGHTED);

		if (this.mealComponent.getFood().getLiquidVolumePerServingSize() != null)
			this.unitsOfMeasure.addAvailableMeasurements(UnitClassification.LIQUID_VOLUME);

	}

	public void setAmount() {

		if (this.unitsOfMeasure.getValue() != null)
			this.mealComponent.setAmount(new Amount(this.amountOfFood.getValue(), this.unitsOfMeasure.getValue()));

	}

	public MealComponent getMealComponent() {

		return this.mealComponent;

	}

	public boolean isEditingExistingMealComponent() {

		return this.editingExistingMealComponent;

	}

}
