package gui.smartnode;

import java.util.ArrayList;

import data.mealguru.FoodDA;
import data.usda.FoodDAOImpl;
import edible.Food;
import gui.SecondaryStage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import utility.ResourceManager;

public class FoodSearchBar extends GridPane {

	FoodDA foodDA;
	FoodDAOImpl foodDAOImpl;

	AutoCompleteTextField autoCompleteTextField;
	Button selectFood;

	ComboBox<String> searchOptions;

	Button search;

	Button createNewFood;

	public FoodSearchBar() {

		this.foodDA = new FoodDA();
		this.foodDAOImpl = new FoodDAOImpl();

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

		this.searchOptions = new ComboBox<>();
		this.searchOptions.getItems().addAll("LOCAL", "USDA");
		this.searchOptions.setPrefHeight(15);
		this.searchOptions.getStyleClass().add("searchBar");
		Tooltip.install(this.searchOptions, new Tooltip("Search user made foods or the USDA database"));
		this.searchOptions.getSelectionModel().selectedIndexProperty().addListener(e -> {

			this.autoCompleteTextField.refresh();

			if (this.searchOptions.getValue().equalsIgnoreCase("USDA"))
				this.autoCompleteTextField.getEntries()
						.addAll(this.foodDAOImpl.searchFood(this.autoCompleteTextField.getText()));
			else if (this.searchOptions.getValue().equalsIgnoreCase("LOCAL"))
				this.autoCompleteTextField.getEntries()
						.addAll(this.foodDA.findFoodNames(this.autoCompleteTextField.getText()));

		});

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
		gridPane.addRow(0, this.autoCompleteTextField, this.searchOptions, this.search, this.createNewFood);

		this.getChildren().add(gridPane);

	}

	public AutoCompleteTextField getAutoCompleteTextField() {

		return this.autoCompleteTextField;

	}

	public Button getSearchButton() {

		return this.search;

	}

	public ArrayList<Food> getValues() {

		ArrayList<Food> toReturn;

		if (this.searchOptions.getValue() == null)
			this.searchOptions.setValue("LOCAL");

		if (this.searchOptions.getValue().equalsIgnoreCase("USDA"))
			toReturn = this.foodDAOImpl.getFoods(this.autoCompleteTextField.getText());
		else
			toReturn = this.foodDA.findFood(this.autoCompleteTextField.getText());

		return toReturn;

	}

}
