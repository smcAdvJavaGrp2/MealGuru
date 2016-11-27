package gui.smartnode;

import java.util.ArrayList;

import data.FoodDA;
import edible.Food;
import gui.SecondaryStage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import utility.ResourceManager;

public class FoodSearchBar extends GridPane {

	FoodDA foodDA;

	TextField searchField;
	ListView<Label> suggestions;

	ComboBox<String> searchOptions;

	Button search;

	Button createNewFood;

	public FoodSearchBar() {

		this.foodDA = new FoodDA();

		this.getStylesheets().add(ResourceManager.getCSS("style.css"));
		this.getStyleClass().add("searchBarPane");
		this.setAlignment(Pos.CENTER);

		this.searchField = new TextField();
		this.searchField.setPromptText("search");
		this.searchField.getStyleClass().add("searchBar");
		this.searchField.setPrefHeight(15);
		this.searchField.setOnMouseClicked(e -> {
			this.searchField.selectAll();
		});
		Tooltip.install(this.searchField,
				new Tooltip("Try typing tags or food names:\n'vegetarian'\n'celery'\n'fish'"));

		this.suggestions = new ListView<>();
		this.suggestions.getItems().addAll(new Label("Hello"));

		this.searchOptions = new ComboBox<>();
		this.searchOptions.getItems().addAll("LOCAL", "USDA");
		this.searchOptions.setPrefHeight(15);
		this.searchOptions.getStyleClass().add("searchBar");
		Tooltip.install(this.searchOptions, new Tooltip("Search user made foods or the USDA database"));

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
		this.createNewFood = new Button();
		this.createNewFood.setGraphic(imageViewForNewFood);
		this.createNewFood.setPrefHeight(15);
		this.createNewFood.getStyleClass().add("searchBar");
		this.createNewFood.setStyle("-fx-background-color: transparent;" + "-fx-border-color: transparent;");
		Tooltip.install(this.createNewFood, new Tooltip("Create a new food to add to your meal"));
		this.createNewFood.setOnAction(e -> {
			SecondaryStage.showFoodEditor();
		});

		GridPane gridPane = new GridPane();
		gridPane.addRow(0, this.searchField, this.searchOptions, this.search, this.createNewFood);

		this.getChildren().add(gridPane);

	}

	public Button getSearchButton() {
		return this.search;
	}

	public TextField getSearchField() {
		return this.searchField;
	}

	public ArrayList<Food> getValues() {

		FoodDA foodDA = new FoodDA();

		return foodDA.findFood(this.searchField.getText());
	}

}
