package gui;

import java.awt.image.BufferedImage;

import data.mealguru.MealDA;
import edible.Meal;
import edible.MealComponent;
import gui.smartnode.CancelButton;
import gui.smartnode.EdibleLabel;
import gui.smartnode.SubmitButton;
import gui.smartnode.TagCreator;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.ResourceManager;

public class MealEditor extends BorderPane {

	boolean editingExistingMeal;

	Meal meal;

	TreeView<String> treeView;

	HBox nameHBox;
	Label nameLabel;
	TextField name;

	private TextArea directions;

	VBox componentsOfMeal;

	CancelButton cancel;
	SubmitButton submit;

	Button getNewMealComponent;

	public MealEditor(Meal meal, boolean editingExistingMeal) {
		
		this.editingExistingMeal = editingExistingMeal;

		this.meal = meal;

		// TOP

		VBox left = new VBox(10);
		left.getStyleClass().add("box");
		VBox right = new VBox(10);
		right.getStyleClass().add("box");
		right.setAlignment(Pos.TOP_CENTER);
		right.setPadding(new Insets(5));
		
		this.nameLabel = new Label("What is your Meal Called? ");
		this.name = new TextField(this.meal.getName());
		this.nameHBox = new HBox(5, this.nameLabel, this.name);

		TagCreator tagCreator = new TagCreator();
		if(meal.getCategories() != null)
			tagCreator.setCategories(meal.getCategories());
		
		Button getMealPicture = new Button();
		getMealPicture.setStyle("-fx-background-color: transparent;");
		Image image;
		ImageView imageView;
		if(meal.getPictureExtension() != null)
			imageView = new ImageView(ResourceManager.getImage(meal.getPictureExtension()));
		else
			imageView = new ImageView(ResourceManager.getResourceImage("camera-icon.png"));
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		getMealPicture.setGraphic(imageView);
		getMealPicture.setOnAction(e -> {

			BufferedImage bufferedImage = PrimaryWindow.grabImage();

			if (bufferedImage != null) {
				this.meal.setPictureExtension(ResourceManager.saveImage(bufferedImage));
				imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
			}
		});
		
		this.directions = new TextArea();
		
		left.getChildren().addAll(this.nameHBox, tagCreator, new Separator(), getMealPicture, new Separator(),  this.directions);

		// CENTER
		
		ImageView imageViewForNewFood = new ImageView(ResourceManager.getResourceImage("new-food.png"));
		imageViewForNewFood.setPreserveRatio(true);
		imageViewForNewFood.setFitHeight(60);
		this.getNewMealComponent = new Button("Add a new ingredient");
		this.getNewMealComponent.setGraphic(imageViewForNewFood);
		this.getNewMealComponent.setPrefHeight(15);
		this.getNewMealComponent.getStyleClass().add("searchBar");
		this.getNewMealComponent.setStyle("-fx-background-color: transparent;" + "-fx-border-color: transparent;");
		Tooltip.install(this.getNewMealComponent, new Tooltip("Add an ingredient to this food!"));
		this.getNewMealComponent.setOnAction(e -> {
			
			SecondaryStage.showMealComponentEditor(new MealComponent(), false);
			
		});
		
		this.componentsOfMeal = new VBox();
		this.componentsOfMeal = new VBox(10);
		this.componentsOfMeal.setPadding(new Insets(10));
		
		this.populateIngredients();
		
		ScrollPane ingredientsScrollPane = new ScrollPane(this.componentsOfMeal);
		ingredientsScrollPane = new ScrollPane(componentsOfMeal);
		ingredientsScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		ingredientsScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		ingredientsScrollPane.setMaxWidth(380);
		ingredientsScrollPane.getStyleClass().add("scroll-pane-inner-shadow");

		this.submit = new SubmitButton();
		this.submit.setOnAction(e -> {

			MealDA mealDA = new MealDA();

			if (this.editingExistingMeal) {

				this.meal.setName(this.name.getText());
				this.meal.setCategories(tagCreator.getCategories());
				this.meal.setDirections(this.directions.getText());
				
				EdibleLableController.renderEdibleLabels(this.getMeal());
				mealDA.updateMeal(this.getMeal());

			} else
				this.meal.setID(mealDA.saveMeal(this.getMeal()));

			EdibleLableController.renderEdibleLabels(this.getMeal());

			SecondaryStage.closeMealEditor();

		});

		this.cancel = new CancelButton();
		this.cancel.setOnAction(e -> {

			this.meal = null;

			SecondaryStage.closeMealEditor();

		});

		right.getChildren().addAll(getNewMealComponent, ingredientsScrollPane, new HBox(submit, cancel));
		
		HBox displayHBox = new HBox(10, left, right);
		displayHBox.setAlignment(Pos.CENTER);
		displayHBox.setPadding(new Insets(15));
		
		this.setCenter(displayHBox);

	}

	public void populateIngredients() {

		if ((this.meal.getMealComponents() == null) || (this.meal.getMealComponents().size() == 0))
			return;

		this.componentsOfMeal.getChildren().clear();

		for (int i = 0; i < this.meal.getMealComponents().size(); i++) {

			EdibleLabel temp = new EdibleLabel(this.meal.getMealComponents().get(i), 185);

			MenuItem menuItem = new MenuItem("Remove Component");
			menuItem.setOnAction(e -> {
				this.removeMealComponentFromMeal((MealComponent) temp.getEdibleObject());
			});

			temp.getContextMenu().getItems().add(1, menuItem);

			this.componentsOfMeal.getChildren().add(temp);

		}

	}

	public Meal getMeal() {

		return this.meal;

	}

	public void addMealComponentToMeal(MealComponent mealComponent) {

		this.meal.addMealComponent(mealComponent);

		this.populateIngredients();

	}

	public void removeMealComponentFromMeal(MealComponent edibleObject) {

		this.meal.removeMealComponent(edibleObject);

		this.populateIngredients();

	}

}
