package gui;

import java.awt.image.BufferedImage;

import data.mealguru.FoodDA;
import edible.Food;
import gui.smartnode.CancelButton;
import gui.smartnode.DoubleTextField;
import gui.smartnode.NutritionLabel;
import gui.smartnode.SmartChoiceBox;
import gui.smartnode.SubmitButton;
import gui.smartnode.TagCreator;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utility.Amount;
import utility.ResourceManager;
import utility.UnitClassification;
import utility.Units;

public class FoodEditor extends StackPane {

	boolean editingExistingFood;

	// Nutritional Label
	NutritionLabel nutritionLabel;

	// The food we're editing or returning
	Food food;

	// Text-fields to gather information
	VBox left;

	Button getFoodPicture;
	ImageView imageView;

	TextField foodName;

	GridPane servingSizeGridPane;
	DoubleTextField servingTextField1;
	SmartChoiceBox servingChoiceBox1;
	DoubleTextField servingTextField2;
	SmartChoiceBox servingChoiceBox2;
	DoubleTextField servingTextField3;
	SmartChoiceBox servingChoiceBox3;

	GridPane nutritionalInformationGridPane;
	DoubleTextField caloriesTextField;
	DoubleTextField totalFatTextField;
	DoubleTextField saturatedFatTextField;
	DoubleTextField transFatTextField;
	DoubleTextField cholesterolTextField;
	DoubleTextField sodiumTextField;
	DoubleTextField carbohydrateTextField;
	DoubleTextField dietaryFiberTextField;
	DoubleTextField sugarTextField;
	DoubleTextField proteinTextField;
	DoubleTextField vitaminATextField;
	DoubleTextField vitaminCTextField;
	DoubleTextField calciumTextField;
	DoubleTextField ironTextField;

	TagCreator tagCreator;

	SubmitButton saveFood;
	CancelButton cancel;

	// Guru Object
	Guru guru;

	String[] tips = { "Hi welcome to MealGuru, I am the MealGuru! I'm here to assist you!",
			"MealGuru lets you create meals and track your nutrition.", "You can eat healthy, I'm here to help you!" };

	public FoodEditor(Food food, boolean editingExistingFood) {

		// Create Guru and set its x, y position
		this.guru = new Guru(50, 80);
		// started with simple animation, I'm not sure about over head yet
		// To do: switching the image or more complicated animations
		this.guru.startAnimation();
		// Return a random String from tip array
		this.guru.setScript(tips);

		// You can also set the string to a specific message at any time
		// this.guru.setMessage("specific message");

		this.editingExistingFood = editingExistingFood;

		this.food = food;

		BorderPane borderPane = new BorderPane();

		if (!editingExistingFood || (food.getServingSize() != null))
			this.food.setServingSize(new Amount(1, Units.SERVING));

		this.setOnMouseClicked(e -> this.nutritionLabel.redrawLabel(this.getFood()));

		this.left = new VBox(5);
		this.left.getStyleClass().add("box");

		this.getFoodPicture = new Button();
		this.getFoodPicture.setStyle("-fx-background-color: transparent;");
		this.imageView = new ImageView(ResourceManager.getResourceImage("camera-icon.png"));
		this.imageView.setPreserveRatio(true);
		this.imageView.setFitHeight(100);
		this.imageView.setFitWidth(100);
		this.getFoodPicture.setGraphic(this.imageView);
		this.getFoodPicture.setOnAction(e -> {

			BufferedImage bufferedImage = PrimaryWindow.grabImage();

			if (bufferedImage != null) {
				food.setPictureExtension(ResourceManager.saveImage(bufferedImage));
				this.imageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
			}

		});

		this.foodName = new TextField();
		HBox foodNamingHBox = new HBox(5, this.foodName);
		foodNamingHBox.setAlignment(Pos.CENTER);

		this.servingSizeGridPane = new GridPane();
		this.servingSizeGridPane.setAlignment(Pos.CENTER);

		this.servingTextField1 = new DoubleTextField();
		this.servingTextField1.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.servingChoiceBox1 = new SmartChoiceBox(UnitClassification.UNIT, UnitClassification.WEIGHTED,
				UnitClassification.LIQUID_VOLUME);
		this.servingSizeGridPane.addRow(0, this.servingTextField1, this.servingChoiceBox1);

		this.servingTextField2 = new DoubleTextField();
		this.servingTextField2.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.servingChoiceBox2 = new SmartChoiceBox(UnitClassification.UNIT, UnitClassification.WEIGHTED,
				UnitClassification.LIQUID_VOLUME);
		this.servingSizeGridPane.addRow(1, this.servingTextField2, this.servingChoiceBox2);

		this.servingTextField3 = new DoubleTextField();
		this.servingTextField3.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.servingChoiceBox3 = new SmartChoiceBox(UnitClassification.UNIT, UnitClassification.WEIGHTED,
				UnitClassification.LIQUID_VOLUME);
		this.servingSizeGridPane.addRow(2, this.servingTextField3, this.servingChoiceBox3);

		this.nutritionalInformationGridPane = new GridPane();
		this.nutritionalInformationGridPane.setAlignment(Pos.CENTER);

		this.caloriesTextField = new DoubleTextField(food.getCalories());
		this.caloriesTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(0, new Label("Calories"), this.caloriesTextField, new Label("cal"));

		this.totalFatTextField = new DoubleTextField(food.getTotalFat().getMeasure());
		this.totalFatTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(1, new Label("Total Fat"), this.totalFatTextField, new Label("g"));

		this.saturatedFatTextField = new DoubleTextField(food.getSaturatedFat().getMeasure());
		this.saturatedFatTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(2, new Label("Saturated Fat"), this.saturatedFatTextField,
				new Label("g"));

		this.transFatTextField = new DoubleTextField(food.getTransFat().getMeasure());
		this.transFatTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(3, new Label("Trans Fat"), this.transFatTextField, new Label("g"));

		this.cholesterolTextField = new DoubleTextField(food.getCholesterol().getMeasure());
		this.cholesterolTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(4, new Label("Cholesterol"), this.cholesterolTextField,
				new Label("mg"));

		this.sodiumTextField = new DoubleTextField(food.getSodium().getMeasure());
		this.sodiumTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(5, new Label("Sodium"), this.sodiumTextField, new Label("mg"));

		this.carbohydrateTextField = new DoubleTextField(food.getCarbohydrates().getMeasure());
		this.carbohydrateTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(6, new Label("Carbohydrate"), this.carbohydrateTextField,
				new Label("g"));

		this.dietaryFiberTextField = new DoubleTextField(food.getDietaryFiber().getMeasure());
		this.dietaryFiberTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(7, new Label("Dietary Fiber"), this.dietaryFiberTextField,
				new Label("g"));

		this.sugarTextField = new DoubleTextField(food.getSugar().getMeasure());
		this.sugarTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(8, new Label("Sugar"), this.sugarTextField, new Label("g"));

		this.proteinTextField = new DoubleTextField(food.getProtein().getMeasure());
		this.proteinTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(9, new Label("Protein"), this.proteinTextField, new Label("g"));

		this.vitaminATextField = new DoubleTextField(food.getVitaminA());
		this.vitaminATextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(10, new Label("Vitamin A"), this.vitaminATextField, new Label("%"));

		this.vitaminCTextField = new DoubleTextField(food.getVitaminC());
		this.vitaminCTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(11, new Label("Vitamin C"), this.vitaminCTextField, new Label("%"));

		this.calciumTextField = new DoubleTextField(food.getCalcium());
		this.calciumTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(12, new Label("Calcium"), this.calciumTextField, new Label("%"));

		this.ironTextField = new DoubleTextField(food.getIron());
		this.ironTextField.setOnKeyReleased(e -> this.nutritionLabel.redrawLabel(this.getFood()));
		this.nutritionalInformationGridPane.addRow(13, new Label("Iron"), this.ironTextField, new Label("%"));

		this.tagCreator = new TagCreator();
		if ((food != null) && (food.getCategories() != null))
			this.tagCreator.setCategories(food.getCategories());
		this.tagCreator.setMaxWidth(200);

		Text foodInformation = new Text("Food Information");
		Text nameYourFood = new Text("Name your food");
		Text uploadPhotoText = new Text("Upload a photo");
		Text foodServingSize = new Text("Food Serving Size");
		Text createTags = new Text("Add Tags");

		Region spring4 = new Region();
		VBox.setVgrow(spring4, Priority.ALWAYS);
		Region spring3 = new Region();
		VBox.setVgrow(spring3, Priority.ALWAYS);

		this.left.getChildren().addAll(new Separator(), foodInformation, new Separator(), spring4, new Separator(),
				nameYourFood, this.foodName, new Separator(), uploadPhotoText, this.getFoodPicture, new Separator(),
				foodServingSize, this.servingSizeGridPane, new Separator(), this.nutritionalInformationGridPane,
				new Separator(), createTags, this.tagCreator, spring3);

		// RIGHT

		this.saveFood = new SubmitButton();
		this.saveFood.setOnAction(event -> {

			FoodDA foodDA = new FoodDA();

			if (editingExistingFood) {
				EdibleLableController.renderEdibleLabels(this.food);
				foodDA.updateFood(this.getFood());
			} else
				food.setFoodID(foodDA.saveFood(this.getFood()));

			SecondaryStage.closeFoodEditorPane();

		});

		this.cancel = new CancelButton();
		this.cancel.setOnAction(event -> {

			this.food = null;
			SecondaryStage.closeFoodEditorPane();

		});

		HBox submitCancelButton = new HBox(5, this.saveFood, this.cancel);
		submitCancelButton.setAlignment(Pos.BASELINE_RIGHT);
		submitCancelButton.setPadding(new Insets(5));

		this.nutritionLabel = new NutritionLabel(food);
		this.nutritionLabel.setPreserveRatio(true);
		this.nutritionLabel.setFitWidth(300);

		Region spring1 = new Region();
		VBox.setVgrow(spring1, Priority.ALWAYS);

		Region spring2 = new Region();
		VBox.setVgrow(spring2, Priority.ALWAYS);

		Text nutritionalText = new Text("One Serving");

		VBox right = new VBox(5, new Separator(), nutritionalText, new Separator(), spring2, new Separator(),
				this.nutritionLabel, new Separator(), spring1, new Separator(), submitCancelButton);

		right.setPadding(new Insets(10));
		right.getStyleClass().add("box");

		HBox leftAndRight = new HBox(20, left, right);
		leftAndRight.setPadding(new Insets(10, 0, 10, 0));
		leftAndRight.setAlignment(Pos.CENTER);

		borderPane.setCenter(leftAndRight);

		this.getChildren().add(leftAndRight);

		this.setAlignment(Pos.CENTER);

		this.fillInFields(food);

		// Guru Object to BorderPane
		borderPane.getChildren().add(this.guru);

	}

	public void redrawNutritionLabel() {

		this.nutritionLabel.redrawLabel(this.food);

	}

	public Food getFood() {

		if (this.food == null)
			return null;

		this.food.setName(this.foodName.getText());

		try {
			this.food.setServingSize(new Amount(Double.parseDouble(this.servingTextField1.getText()),
					this.servingChoiceBox1.getValue()));
		} catch (Exception ex) {

		}
		try {
			this.food.setServingSize(new Amount(Double.parseDouble(this.servingTextField2.getText()),
					this.servingChoiceBox2.getValue()));
		} catch (Exception ex) {

		}
		try {
			this.food.setServingSize(new Amount(Double.parseDouble(this.servingTextField3.getText()),
					this.servingChoiceBox3.getValue()));
		} catch (Exception ex) {

		}

		this.food.setCalories(this.caloriesTextField.getValue());
		this.food.setTotalFat(new Amount(this.totalFatTextField.getValue(), Units.GRAM));
		this.food.setSaturatedFat(new Amount(this.saturatedFatTextField.getValue(), Units.GRAM));
		this.food.setTransFat(new Amount(this.transFatTextField.getValue(), Units.GRAM));
		this.food.setCholesterol(new Amount(this.cholesterolTextField.getValue(), Units.MILLIGRAM));
		this.food.setSodium(new Amount(this.sodiumTextField.getValue(), Units.MILLIGRAM));
		this.food.setCarbohydrates(new Amount(this.carbohydrateTextField.getValue(), Units.GRAM));
		this.food.setDietaryFiber(new Amount(this.dietaryFiberTextField.getValue(), Units.GRAM));
		this.food.setSugar(new Amount(this.sugarTextField.getValue(), Units.GRAM));
		this.food.setProtein(new Amount(this.proteinTextField.getValue(), Units.GRAM));
		this.food.setVitaminA(this.vitaminATextField.getValue());
		this.food.setVitaminC(this.vitaminCTextField.getValue());
		this.food.setCalcium(this.calciumTextField.getValue());
		this.food.setIron(this.ironTextField.getValue());

		this.food.setCategories(this.tagCreator.getCategories());

		this.food.setLastEdit();

		return this.food;

	}

	public void fillInFields(Food food) {

		Image image;
		if ((image = ResourceManager.getImage(food.getPictureExtension())) != null)
			this.imageView.setImage(image);

		this.foodName.setText(food.getName());

		if (food.getUnitsPerServingSize() != null) {
			this.servingTextField1.setText(food.getUnitsPerServingSize().getMeasure() + "");
			this.servingChoiceBox1.setValue(food.getUnitsPerServingSize().getUnits());
		}

		if (food.getWeightPerServingSize() != null) {
			this.servingTextField2.setText(food.getWeightPerServingSize().getMeasure() + "");
			this.servingChoiceBox2.setValue(food.getWeightPerServingSize().getUnits());
		}

		if (food.getLiquidVolumePerServingSize() != null) {
			this.servingTextField3.setText(food.getLiquidVolumePerServingSize().getMeasure() + "");
			this.servingChoiceBox3.setValue(food.getLiquidVolumePerServingSize().getUnits());
		}

		this.caloriesTextField.setText(food.getCalories() + "");

		this.caloriesTextField.setText(food.getCalories() + "");
		this.totalFatTextField.setText(food.getTotalFat().getMeasure() + "");
		this.saturatedFatTextField.setText(food.getSaturatedFat().getMeasure() + "");
		this.transFatTextField.setText(food.getTransFat().getMeasure() + "");
		this.cholesterolTextField.setText(food.getCholesterol().getMeasure() + "");
		this.sodiumTextField.setText(food.getSodium().getMeasure() + "");
		this.carbohydrateTextField.setText(food.getCarbohydrates().getMeasure() + "");
		this.dietaryFiberTextField.setText(food.getDietaryFiber().getMeasure() + "");
		this.sugarTextField.setText(food.getSugar().getMeasure() + "");
		this.proteinTextField.setText(food.getProtein().getMeasure() + "");
		this.vitaminATextField.setText(food.getVitaminA() + "");
		this.vitaminCTextField.setText(food.getVitaminC() + "");
		this.calciumTextField.setText(food.getCalcium() + "");
		this.ironTextField.setText(food.getIron() + "");

	}

	public boolean isEditingExistingFood() {
		return this.editingExistingFood;
	}

}
