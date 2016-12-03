package gui;

import data.mealguru.MealDA;
import edible.Meal;
import edible.MealComponent;
import gui.smartnode.EdibleLabel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MealEditor extends BorderPane {

	boolean editingExistingMeal;

	Meal meal;

	TreeView<String> treeView;

	HBox nameHBox;
	Label nameLabel;
	TextField name;

	private TextArea directions;

	VBox componentsOfMeal;

	Button cancel;
	Button submit;

	Button getNewMealComponent;

	public MealEditor(Meal meal, boolean editingExistingMeal) {

		this.editingExistingMeal = editingExistingMeal;

		this.meal = meal;

		// TOP

		this.nameLabel = new Label("What is your Meal Called? ");
		this.name = new TextField(this.meal.getName());
		this.nameHBox = new HBox(5, this.nameLabel, this.name);
		this.setTop(this.nameHBox);

		// CENTER

		this.directions = new TextArea();

		this.componentsOfMeal = new VBox();
		this.populateIngredients();

		this.submit = new Button("submit");
		this.submit.setOnAction(e -> {

			MealDA mealDA = new MealDA();

			if (this.editingExistingMeal) {

				EdibleLableController.renderEdibleLabels(this.getMeal());
				mealDA.updateMeal(this.getMeal());

			} else
				this.meal.setID(mealDA.saveMeal(this.getMeal()));

			EdibleLableController.renderEdibleLabels(this.getMeal());

			SecondaryStage.closeMealEditor();

		});

		this.cancel = new Button("cancel");
		this.cancel.setOnAction(e -> {

			this.meal = null;

			SecondaryStage.closeMealEditor();

		});

		this.getNewMealComponent = new Button("Create new MealComponent");
		this.getNewMealComponent.setOnAction(e -> {

			SecondaryStage.showMealComponentEditor(new MealComponent(), false);

		});

		this.setCenter(new VBox(this.directions, this.componentsOfMeal));

		this.setBottom(new HBox(this.submit, this.cancel, this.getNewMealComponent));

	}

	public void populateIngredients() {

		if ((this.meal.getMealComponents() == null) || (this.meal.getMealComponents().size() == 0))
			return;

		this.componentsOfMeal.getChildren().clear();

		for (int i = 0; i < this.meal.getMealComponents().size(); i++) {

			EdibleLabel temp = new EdibleLabel(this.meal.getMealComponents().get(i), 200);

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
