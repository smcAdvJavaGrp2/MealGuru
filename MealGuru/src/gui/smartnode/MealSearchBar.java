package gui.smartnode;

import java.util.ArrayList;

import data.mealguru.MealDA;
import edible.Meal;
import gui.SecondaryStage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import utility.ResourceManager;

public class MealSearchBar extends HBox {

	MealDA mealDA;

	AutoCompleteTextField autoCompleteTextField;
	Button selectMeal;

	Button search;

	Button createNewMeal;

	public MealSearchBar() {

		this.mealDA = new MealDA();

		this.getStylesheets().add(ResourceManager.getCSS("style.css"));
		this.getStyleClass().add("searchBarPane");
		this.setAlignment(Pos.CENTER);

		this.autoCompleteTextField = new AutoCompleteTextField();
		this.autoCompleteTextField.setPromptText("search");
		this.autoCompleteTextField.getStyleClass().add("searchBar");
		this.autoCompleteTextField.setPrefHeight(15);
		this.autoCompleteTextField.setOnMouseClicked(e -> {
			this.autoCompleteTextField.selectAll();
		});
		this.autoCompleteTextField.getEntries().addAll(this.mealDA.findMealNames(this.autoCompleteTextField.getText()));

		ImageView imageView = new ImageView(ResourceManager.getResourceImage("magnifying-glass.png"));
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(15);
		this.search = new Button();
		this.search.setGraphic(imageView);
		this.search.setPrefHeight(15);
		this.search.getStyleClass().add("searchBar");
		Tooltip.install(this.search, new Tooltip("Search for an existing food"));

		ImageView imageViewForNewFood = new ImageView(ResourceManager.getResourceImage("new-food.png"));
		imageViewForNewFood.setPreserveRatio(true);
		imageViewForNewFood.setFitHeight(60);
		this.createNewMeal = new Button();
		this.createNewMeal.setGraphic(imageViewForNewFood);
		this.createNewMeal.setPrefHeight(15);
		this.createNewMeal.getStyleClass().add("searchBar");
		this.createNewMeal.setStyle("-fx-background-color: transparent;" + "-fx-border-color: transparent;");
		Tooltip.install(this.createNewMeal, new Tooltip("Create a new meal"));
		this.createNewMeal.setOnAction(e -> {
			SecondaryStage.showMealEditor();
		});

		this.setSpacing(10);
		this.getChildren().addAll(this.autoCompleteTextField, this.search, this.createNewMeal);

	}

	public AutoCompleteTextField getAutoCompleteTextField() {

		return this.autoCompleteTextField;

	}

	public Button getSearchButton() {

		return this.search;

	}

	public ArrayList<Meal> getValues() {

		ArrayList<Meal> toReturn;

		toReturn = this.mealDA.findMeals(this.autoCompleteTextField.getText());

		return toReturn;

	}

}
