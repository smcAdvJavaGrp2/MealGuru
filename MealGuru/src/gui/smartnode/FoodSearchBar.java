package gui.smartnode;

import java.util.ArrayList;

import data.mealguru.FoodDA;
import data.usda.USDADA;
import edible.Food;
import gui.SecondaryStage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import utility.ResourceManager;

public class FoodSearchBar extends GridPane {
	
	FoodDA foodDA;
	USDADA usdaDA;
	VBox vbox;
	HBox hbox1,hbox2;
	AutoCompleteTextField autoCompleteTextField;
	Button selectFood;

	ComboBox<String> searchOptions;
	ComboBox<String> foodGroups;
	Button search;

	Button createNewFood;

	public FoodSearchBar() {
		
		hbox1 = new HBox();
		hbox2 = new HBox();
		vbox = new VBox();
		this.foodDA = new FoodDA();
		this.usdaDA = new USDADA();

		this.getStylesheets().add(ResourceManager.getCSS("style.css"));
		this.getStyleClass().add("searchBarPane");
		this.setHgap(10);
		//this.setPadding(new Insets(10, 5, 10, 5));
		this.setAlignment(Pos.CENTER);
		
		this.foodGroups = new ComboBox<String>();
		// List of food groups - null option to search all
	    this.foodGroups.getItems().addAll(
				null,
				"Dairy and Egg Products", 
				"Spices and Herbs", 
				"Baby Foods", 
				"Fats and Oils", 
				"Poultry Products",
				"Soups, Sauces, and Gravies", 
				"Sausages and Luncheon Meats", 
				"Breakfast Cereals", 
				"Fruits and Fruit Juices",
				"Pork Products",
				"Vegetables and Vegetable Products", 
				"Nut and Seed Products", 
				"Beef Products",
				"Beverages", 
				"Finfish and Shellfish Products", 
				"Legumes and Legume Products", 
				"Lamb, Veal, and Game Products",
				"Baked Products",
				"Sweets",
				"Cereal Grains and Pasta",
				"Fast Foods",
				"Meals, Entrees, and Side Dishes",
				"Snacks",
				"American Indian/Alaska Native Foods",
				"Restaurant Foods");
	    Tooltip.install(this.foodGroups, new Tooltip("Search user made foods or the USDA database"));

		// Search Bar
		this.autoCompleteTextField = new AutoCompleteTextField();
		this.autoCompleteTextField.setPromptText("search");
		this.autoCompleteTextField.getStyleClass().add("searchBar");
		this.autoCompleteTextField.setPrefHeight(15);
		this.autoCompleteTextField.setOnMouseClicked(e -> {
			this.autoCompleteTextField.selectAll();
		});

		this.searchOptions = new ComboBox<>();
		this.searchOptions.getItems().addAll("LOCAL", "USDA");
		this.searchOptions.getSelectionModel().clearSelection();
		this.searchOptions.setPrefHeight(15);
		this.searchOptions.getStyleClass().add("searchBar");
		Tooltip.install(this.searchOptions, new Tooltip("Search user made foods or the USDA database"));
		this.searchOptions.getSelectionModel().selectedIndexProperty().addListener(e -> {
			if (this.searchOptions.getValue().equalsIgnoreCase("USDA"))
				this.autoCompleteTextField.getEntries()
						.addAll(this.usdaDA.searchFood(this.autoCompleteTextField.getText(), this.foodGroups.getValue()));
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
		
		// Give the autoCompleteTextField max width
		HBox.setHgrow(this.autoCompleteTextField, Priority.ALWAYS);
		this.autoCompleteTextField.setMaxWidth(Double.MAX_VALUE);

		// Is there a way to accomplish this with less layouts?
		hbox1.getChildren().addAll(this.autoCompleteTextField, this.search);
		hbox1.setSpacing(10);
		
		hbox2.getChildren().addAll(this.foodGroups, this.searchOptions);
		hbox2.setSpacing(10);
		
		vbox.getChildren().addAll(hbox1, hbox2);
		vbox.setSpacing(10);
		
		this.add(vbox, 1, 1);
		this.add(this.createNewFood,2,1);
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
			toReturn = this.usdaDA.getFoods(this.autoCompleteTextField.getText());
		else
			toReturn = this.foodDA.findFood(this.autoCompleteTextField.getText());

		return toReturn;

	}

}
