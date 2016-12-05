package gui;

import java.util.ArrayList;

import data.mealguru.FoodDA;
import data.mealguru.MealComponentDA;
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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

		this.searchBar.getAutoCompleteTextField().setOnKeyReleased(e -> {

			if (e.getCode() == KeyCode.ENTER)
				this.populateResults(this.searchBar.getValues());

		});

		this.results = new VBox(10);
		this.results.setPadding(new Insets(10));

		this.resultsScrollPane = new ScrollPane(this.results);
		this.resultsScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.resultsScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		this.resultsScrollPane.setMaxWidth(380);
		this.resultsScrollPane.getStyleClass().add("scroll-pane-inner-shadow");

		VBox leftVBox = new VBox(this.searchBar, this.resultsScrollPane);
		leftVBox.getStyleClass().add("box");
		leftVBox.setAlignment(Pos.TOP_CENTER);
		leftVBox.setPadding(new Insets(5));

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

		this.selectedFoodLabel = new EdibleLabel(new Food(), 300);

		this.nutritionLabel = new NutritionLabel(mealComponent);
		this.nutritionLabel.setPreserveRatio(true);
		this.nutritionLabel.setFitWidth(320);

		this.submit = new SubmitButton();
		this.submit.setOnAction(event -> {

			if ((this.mealComponent == null) || (this.mealComponent.getFood() == null)
					|| (this.mealComponent.getAmount() == null)) {

				AlertBox invalidInput = new AlertBox("Invalid MealComponent",
						"A value you've entered is missing or invalid. Please check your information.",
						"A meal component requires a decimal amount of an ingredient and a unit classification (e.g. grams, cup, milligram, unit).");

				invalidInput.showAndWait();

			} else {

				if (mealComponent.getFood().getID() == 0)
					mealComponent.getFood().setFoodID(new FoodDA().saveFood(mealComponent.getFood()));

				if (editingExistingMealComponent)
					new MealComponentDA().updateMealComponent(this.getMealComponent());
				else
					new MealComponentDA().updateMealComponent(this.getMealComponent());

				EdibleLableController.renderEdibleLabels(this.getMealComponent());
				SecondaryStage.closeMealComponentEditor();

			}

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
		this.rightVBox.setPadding(new Insets(20));
		this.rightVBox.setAlignment(Pos.CENTER);

		HBox displayHBox = new HBox(10, leftVBox, this.rightVBox);
		displayHBox.setAlignment(Pos.CENTER);
		displayHBox.setPadding(new Insets(15));

		this.setCenter(displayHBox);

		if (mealComponent.getFood() != null)
			this.selectFood(mealComponent.getFood());

		if (mealComponent.getAmount() != null)
			this.unitsOfMeasure.setValue(mealComponent.getAmount().getUnits());

	}

	public void populateResults(ArrayList<Food> foodsToPopulateSearch) {

		this.results.getChildren().clear();

		for (Food food : foodsToPopulateSearch) {

			EdibleLabel temp = new EdibleLabel(food, 345);

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
		this.selectedFoodLabel = new EdibleLabel(this.mealComponent.getFood(), 300);

		this.rightVBox.getChildren().add(0, this.selectedFoodLabel);

		this.unitsOfMeasure.clearAll();

		if (this.mealComponent.getFood().getServingSize() != null)
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
