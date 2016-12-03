package gui.smartnode;

import edible.Edible;
import edible.Food;
import edible.Meal;
import edible.MealComponent;
import gui.EdibleLableController;
import gui.SecondaryStage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import utility.ResourceManager;

public class EdibleLabel extends Button {

	Edible edibleObject;
	ImageView edibleImage;

	int maxWidth;

	Tooltip tooltip;

	NutritionLabel nutritionLabel;

	public EdibleLabel(Food food, int maxWidth) {

		EdibleLableController.addEdibleLabel(this);
		this.maxWidth = maxWidth;

		this.edibleObject = food;

		this.edibleImage = new ImageView();
		this.edibleImage = new ImageView(ResourceManager.getImage(food.getPictureExtension()));

		this.setGraphic(this.edibleImage);
		this.setText(food.getName());

		this.tooltip = new Tooltip();
		this.nutritionLabel = new NutritionLabel(food);
		this.nutritionLabel.setPreserveRatio(true);
		this.nutritionLabel.setFitWidth(300);
		this.tooltip.setGraphic(this.nutritionLabel);
		this.tooltip.setAutoHide(false);
		this.setTooltip(this.tooltip);

		ContextMenu contextMenu = new ContextMenu();
		MenuItem editItem = new MenuItem("Edit Nutritional Information");
		editItem.setOnAction(e -> {
			SecondaryStage.showFoodEditor(food, true);
		});
		MenuItem close = new MenuItem("Close");
		contextMenu.getItems().addAll(editItem, close);
		this.setContextMenu(contextMenu);

		this.setStyle();

	}

	public EdibleLabel(MealComponent mealComponent, int maxWidth) {

		EdibleLableController.addEdibleLabel(this);
		this.maxWidth = maxWidth;

		this.edibleObject = mealComponent;

		this.edibleImage = new ImageView();
		this.edibleImage = new ImageView(ResourceManager.getImage(mealComponent.getPictureExtension()));

		this.setGraphic(this.edibleImage);
		this.setText(mealComponent.getName() + ", " + mealComponent.getAmount());

		this.tooltip = new Tooltip();
		this.nutritionLabel = new NutritionLabel(mealComponent);
		this.nutritionLabel.setPreserveRatio(true);
		this.nutritionLabel.setFitWidth(300);
		this.tooltip.setGraphic(this.nutritionLabel);
		this.tooltip.setAutoHide(false);
		this.setTooltip(this.tooltip);

		ContextMenu contextMenu = new ContextMenu();
		MenuItem editItem = new MenuItem("Edit Meal Component");
		editItem.setOnAction(e -> {
			SecondaryStage.showMealComponentEditor(mealComponent, true);
		});
		MenuItem close = new MenuItem("Close");
		contextMenu.getItems().addAll(editItem, close);
		this.setContextMenu(contextMenu);

		this.setStyle();

		// DRAG/DROP FUNCTIONALITY

	}

	public EdibleLabel(Meal meal, int maxWidth) {

		EdibleLableController.addEdibleLabel(this);
		this.maxWidth = maxWidth;

		this.edibleObject = meal;

		this.edibleImage = new ImageView(ResourceManager.getImage(meal.getPictureExtension()));

		this.setGraphic(this.edibleImage);
		this.setText(meal.getName());

		this.tooltip = new Tooltip();
		this.nutritionLabel = new NutritionLabel(meal);
		this.nutritionLabel.setPreserveRatio(true);
		this.nutritionLabel.setFitWidth(300);
		this.tooltip.setGraphic(this.nutritionLabel);
		this.tooltip.setAutoHide(false);
		this.setTooltip(this.tooltip);

		ContextMenu contextMenu = new ContextMenu();
		MenuItem editItem = new MenuItem("Edit Meal");
		editItem.setOnAction(e -> {
			SecondaryStage.showMealEditor(meal, true);
		});
		MenuItem close = new MenuItem("Close");
		contextMenu.getItems().addAll(editItem, close);
		this.setContextMenu(contextMenu);

		this.setStyle();

		// DRAG/DROP FUNCTIONALITY

	}

	// METHODS

	public Edible getEdibleObject() {
		return this.edibleObject;
	}

	public void setStyle() {

		this.edibleImage.setPreserveRatio(true);
		this.edibleImage.setFitHeight(50);

		this.setMaxWidth(this.maxWidth);

		this.setAlignment(Pos.CENTER);

		this.edibleImage.setStyle(
				"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" + "-fx-background-radius: 5;");
		this.setStyle(
				"-fx-border-style: none;" + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
						+ "-fx-background-radius: 5;" + "-fx-background-color: white;" + "-fx-font-size: 10;"
						+ "-fx-font-family: sans-serif;-fx-text-fill: black;");

		this.setOnMouseEntered(e -> {

			this.edibleImage.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,255,0.8), 10, 0, 0, 0);"
					+ "-fx-background-radius: 5;");
			this.setStyle("-fx-border-style: none;"
					+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,255,0.8), 10, 0, 0, 0);"
					+ "-fx-background-radius: 5;" + "-fx-background-color: white;" + "-fx-font-size: 10;"
					+ "-fx-font-family: sans-serif;-fx-text-fill: black;");

		});

		this.setOnMouseExited(e -> {

			this.edibleImage.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
					+ "-fx-background-radius: 5;");
			this.setStyle(
					"-fx-border-style: none;" + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
							+ "-fx-background-radius: 5;" + "-fx-background-color: white;" + "-fx-font-size: 10;"
							+ "-fx-font-family: sans-serif;-fx-text-fill: black;");
		});

	}

	public void render() {

		this.edibleImage.setImage(ResourceManager.getImage(this.edibleObject.getPictureExtension()));

		this.setText(this.edibleObject.getName());

		this.nutritionLabel.redrawLabel(this.edibleObject);

	}

	// DRAG/DROP FUNCTIONALITY

}
