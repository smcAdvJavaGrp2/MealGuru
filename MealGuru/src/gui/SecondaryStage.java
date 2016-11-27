package gui;

import edible.DailyIntake;
import edible.Food;
import edible.Meal;
import edible.MealComponent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utility.ResourceManager;

public class SecondaryStage extends Stage {

	// CLASS MEMBERS

	private static SecondaryStage secondaryStage;

	private static Scene visibleScene;

	private static MealEditor mealEditor;
	private static DailyIntakeEditor dailyIntakeEditor;
	private static MealComponentEditor mealComponentEditor;
	private static FoodEditor foodEditor;

	private SecondaryStage() {

		this.setScene(SecondaryStage.visibleScene);

		this.setWidth(960);
		this.setMinWidth(960);

		this.setHeight(800);
		this.setMinHeight(800);

		this.initModality(Modality.APPLICATION_MODAL);

		SecondaryStage.visibleScene.getStylesheets().add(ResourceManager.getCSS("style.css"));

		this.setOnCloseRequest(e -> {

			if (SecondaryStage.mealEditor != null)
				SecondaryStage.mealEditor = null;

			if (SecondaryStage.dailyIntakeEditor != null)
				SecondaryStage.dailyIntakeEditor = null;

			if (SecondaryStage.mealComponentEditor != null)
				SecondaryStage.mealComponentEditor = null;

			if (SecondaryStage.foodEditor != null)
				SecondaryStage.foodEditor = null;

		});

	}

	// GETTERS/SETTERS

	// METHODS

	public static void closeFoodEditorPane() {

		if ((SecondaryStage.foodEditor != null) && SecondaryStage.foodEditor.isVisible()) {

			if (SecondaryStage.mealComponentEditor != null) {

				if (SecondaryStage.foodEditor.getFood() != null)
					SecondaryStage.mealComponentEditor.selectFood(SecondaryStage.foodEditor.getFood());

				SecondaryStage.foodEditor = null;

				SecondaryStage.mealComponentEditor.setVisible(true);
				SecondaryStage.visibleScene.setRoot(SecondaryStage.mealComponentEditor);
				return;

			}

			SecondaryStage.foodEditor = null;

			SecondaryStage.secondaryStage.close();
			return;

		}
	}

	public static void closeMealComponentEditor() {

		if ((SecondaryStage.mealComponentEditor != null) && SecondaryStage.mealComponentEditor.isVisible()) {

			if (SecondaryStage.mealEditor != null) {

				if ((SecondaryStage.mealComponentEditor.getMealComponent() != null)
						&& !SecondaryStage.mealComponentEditor.isEditingExistingMealComponent())
					SecondaryStage.mealEditor
							.addMealComponentToMeal(SecondaryStage.mealComponentEditor.getMealComponent());

				SecondaryStage.mealComponentEditor = null;

				SecondaryStage.mealEditor.setVisible(true);
				SecondaryStage.visibleScene.setRoot(SecondaryStage.mealEditor);
				return;

			}

			SecondaryStage.mealComponentEditor = null;

			SecondaryStage.secondaryStage.close();
			return;

		}

	}

	public static void closeMealEditor() {

		if ((SecondaryStage.mealEditor != null) && SecondaryStage.mealEditor.isVisible()) {

			if (SecondaryStage.dailyIntakeEditor != null) {

				if (SecondaryStage.mealEditor.getMeal() != null)
					SecondaryStage.dailyIntakeEditor.addMeal(SecondaryStage.mealEditor.getMeal());

				SecondaryStage.mealEditor = null;

				SecondaryStage.dailyIntakeEditor.setVisible(true);
				SecondaryStage.visibleScene.setRoot(SecondaryStage.dailyIntakeEditor);
				return;

			}

			SecondaryStage.mealEditor = null;

			SecondaryStage.secondaryStage.close();
			return;

		}

	}

	public static void closeDailyIntakeEditor() {

		if ((SecondaryStage.dailyIntakeEditor != null) && SecondaryStage.dailyIntakeEditor.isVisible()) {

			SecondaryStage.dailyIntakeEditor = null;
			SecondaryStage.secondaryStage.close();

			return;

		}

	}

	public static void showDailyIntakeEditor(DailyIntake dailyIntake) {

		if (SecondaryStage.mealEditor != null)
			SecondaryStage.mealEditor.setVisible(false);
		if (SecondaryStage.mealComponentEditor != null)
			SecondaryStage.mealComponentEditor.setVisible(false);
		if (SecondaryStage.foodEditor != null)
			SecondaryStage.foodEditor.setVisible(false);

		SecondaryStage.dailyIntakeEditor = new DailyIntakeEditor(dailyIntake);

		if (SecondaryStage.visibleScene == null)
			SecondaryStage.visibleScene = new Scene(SecondaryStage.dailyIntakeEditor);
		else
			SecondaryStage.visibleScene.setRoot(SecondaryStage.dailyIntakeEditor);

		if (SecondaryStage.secondaryStage == null)
			SecondaryStage.secondaryStage = new SecondaryStage();

		SecondaryStage.secondaryStage.setTitle("Edit Daily Intake for " + dailyIntake.getName());

		if (!SecondaryStage.secondaryStage.isShowing())
			SecondaryStage.secondaryStage.showAndWait();

	}

	public static void showMealEditor() {

		SecondaryStage.showMealEditor(new Meal(), false);

	}

	public static void showMealEditor(Meal meal, boolean editingExistingMealComponent) {

		if (SecondaryStage.dailyIntakeEditor != null)
			SecondaryStage.dailyIntakeEditor.setVisible(false);
		if (SecondaryStage.mealComponentEditor != null)
			SecondaryStage.mealComponentEditor.setVisible(false);
		if (SecondaryStage.foodEditor != null)
			SecondaryStage.foodEditor.setVisible(false);

		SecondaryStage.mealEditor = new MealEditor(meal, editingExistingMealComponent);

		if (SecondaryStage.visibleScene == null)
			SecondaryStage.visibleScene = new Scene(SecondaryStage.mealEditor);
		else
			SecondaryStage.visibleScene.setRoot(SecondaryStage.mealEditor);

		if (SecondaryStage.secondaryStage == null)
			SecondaryStage.secondaryStage = new SecondaryStage();

		SecondaryStage.secondaryStage.setTitle("Edit Meal");

		if (!SecondaryStage.secondaryStage.isShowing())
			SecondaryStage.secondaryStage.showAndWait();

		System.out.println("returning: " + meal);

	}

	public static void showMealComponentEditor() {

		SecondaryStage.showMealComponentEditor(new MealComponent(), false);

	}

	public static void showMealComponentEditor(MealComponent mealComponent, boolean editingExistingMealComponent) {

		if (SecondaryStage.mealEditor != null)
			SecondaryStage.mealEditor.setVisible(false);
		if (SecondaryStage.dailyIntakeEditor != null)
			SecondaryStage.dailyIntakeEditor.setVisible(false);
		if (SecondaryStage.foodEditor != null)
			SecondaryStage.foodEditor.setVisible(false);

		SecondaryStage.mealComponentEditor = new MealComponentEditor(mealComponent, editingExistingMealComponent);

		if (SecondaryStage.visibleScene == null)
			SecondaryStage.visibleScene = new Scene(SecondaryStage.mealComponentEditor);
		else
			SecondaryStage.visibleScene.setRoot(SecondaryStage.mealComponentEditor);

		if (SecondaryStage.secondaryStage == null)
			SecondaryStage.secondaryStage = new SecondaryStage();

		SecondaryStage.secondaryStage.setTitle("Edit Meal Component");

		if (!SecondaryStage.secondaryStage.isShowing())
			SecondaryStage.secondaryStage.showAndWait();

	}

	public static void showFoodEditor() {

		SecondaryStage.showFoodEditor(new Food(), false);

	}

	public static void showFoodEditor(Food food, boolean editingExistingFood) {

		if (SecondaryStage.mealEditor != null)
			SecondaryStage.mealEditor.setVisible(false);
		if (SecondaryStage.dailyIntakeEditor != null)
			SecondaryStage.dailyIntakeEditor.setVisible(false);
		if (SecondaryStage.mealComponentEditor != null)
			SecondaryStage.mealComponentEditor.setVisible(false);

		SecondaryStage.foodEditor = new FoodEditor(food, editingExistingFood);

		if (SecondaryStage.visibleScene == null)
			SecondaryStage.visibleScene = new Scene(SecondaryStage.foodEditor);
		else
			SecondaryStage.visibleScene.setRoot(SecondaryStage.foodEditor);

		if (SecondaryStage.secondaryStage == null)
			SecondaryStage.secondaryStage = new SecondaryStage();

		SecondaryStage.secondaryStage.setTitle("Edit Food");

		if (!SecondaryStage.secondaryStage.isShowing())
			SecondaryStage.secondaryStage.showAndWait();

	}

}
