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
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utility.ResourceManager;

public class MealEditor extends BorderPane {

	boolean editingExistingMeal;

	Meal meal;

	TreeView<String> treeView;

	TextField name;

	private TextArea directions;

	VBox componentsOfMeal;

	CancelButton cancel;
	SubmitButton submit;

	Button getNewMealComponent;

	// Guru Object
	Guru guru;
	String[] tips = { "Hi welcome to MealGuru, I am the MealGuru! I'm here to assist you!",
			"MealGuru lets you create meals and track your nutrition.", "You can eat healthy, I'm here to help you!" };

	public MealEditor(Meal meal, boolean editingExistingMeal) {

		// Create Guru and set its x, y position
		this.guru = new Guru(40, 80);
		// started with simple animation, I'm not sure about over head yet
		// To do: switching the image or more complicated animations
		this.guru.startAnimation();
		// Return a random String from tip array
		this.guru.setScript(tips);

		// You can also set the string to a specific message at any time
		// this.guru.setMessage("specific message");

		this.editingExistingMeal = editingExistingMeal;

		this.meal = meal;

		// TOP

		VBox left = new VBox(10);
		left.getStyleClass().add("box");
		left.setAlignment(Pos.CENTER);

		VBox right = new VBox(10);
		right.getStyleClass().add("box");
		right.setAlignment(Pos.CENTER);
		right.setPadding(new Insets(5));

		this.name = new TextField(this.meal.getName());

		TagCreator tagCreator = new TagCreator();
		if (meal.getCategories() != null)
			tagCreator.setCategories(meal.getCategories());

		Button getMealPicture = new Button();
		getMealPicture.setStyle("-fx-background-color: transparent;");
		Image image;
		ImageView imageView;
		if (meal.getPictureExtension() != null)
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
		this.directions.setPrefHeight(200);
		this.directions.setPrefWidth(80);

		Text mealInformation = new Text("Meal Information");
		Text nameMealText = new Text("What is your meal called?");
		Text categories = new Text("Tag your meal");
		Text upload = new Text("Upload a picture for your meal");
		Text notes = new Text("Notes");

		Region spring3 = new Region();
		VBox.setVgrow(spring3, Priority.ALWAYS);

		Region spring4 = new Region();
		VBox.setVgrow(spring4, Priority.ALWAYS);

		left.getChildren().addAll(new Separator(), mealInformation, new Separator(), spring3, new Separator(),
				nameMealText, this.name, new Separator(), categories, tagCreator, new Separator(), upload,
				getMealPicture, new Separator(), notes, this.directions, new Separator(), spring4, new Separator());

		// CENTER

		ImageView imageViewForNewFood = new ImageView(ResourceManager.getResourceImage("new-food.png"));
		imageViewForNewFood.setPreserveRatio(true);
		imageViewForNewFood.setFitHeight(50);
		this.getNewMealComponent = new Button("Add a new ingredient");
		this.getNewMealComponent.setGraphic(imageViewForNewFood);
		this.getNewMealComponent.setPrefHeight(15);
		Tooltip.install(this.getNewMealComponent, new Tooltip("Add an ingredient to this food!"));
		this.getNewMealComponent.setOnAction(e -> {

			SecondaryStage.showMealComponentEditor(new MealComponent(), false);

		});

		this.componentsOfMeal = new VBox();
		this.componentsOfMeal = new VBox(10);
		this.componentsOfMeal.setPadding(new Insets(10));

		this.populateIngredients();

		ScrollPane ingredientsScrollPane = new ScrollPane(this.componentsOfMeal);
		ingredientsScrollPane = new ScrollPane(this.componentsOfMeal);
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

		Region spring = new Region();
		VBox.setVgrow(spring, Priority.ALWAYS);

		Region spring2 = new Region();
		VBox.setVgrow(spring2, Priority.ALWAYS);

		HBox buttonHBox = new HBox(10, this.submit, this.cancel);
		buttonHBox.setAlignment(Pos.BASELINE_RIGHT);

		Text dailyIntakeText = new Text("Ingredients");

		right.getChildren().addAll(new Separator(), dailyIntakeText, new Separator(), spring2, new Separator(),
				ingredientsScrollPane, this.getNewMealComponent, new Separator(), spring, new Separator(), buttonHBox);

		HBox displayHBox = new HBox(10, left, right);
		displayHBox.setAlignment(Pos.CENTER);
		displayHBox.setPadding(new Insets(15));

		this.setCenter(displayHBox);

		// Guru Object to BorderPane
		this.getChildren().add(this.guru);

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
