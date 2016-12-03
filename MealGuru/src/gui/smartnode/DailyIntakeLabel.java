package gui.smartnode;

import java.util.ArrayList;

import data.mealguru.DailyIntakeDA;
import edible.DailyIntake;
import edible.Meal;
import gui.EdibleLableController;
import gui.PrimaryWindow;
import gui.SecondaryStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import utility.ResourceManager;

public class DailyIntakeLabel extends VBox {

	VBox primaryVBox;

	ImageView dayImage;

	Button dayLabel;

	DailyIntake dailyIntake;

	VBox mealsVBox;
	ScrollPane scrollPane;

	Button removeAll;

	boolean showAddMealButton = true;
	Button addMeal;

	public DailyIntakeLabel(DailyIntake dailyIntake) {

		this.primaryVBox = new VBox(5);
		this.primaryVBox.setAlignment(Pos.CENTER);

		EdibleLableController.addDailyIntakeLabel(this);

		this.setDailyIntake(dailyIntake);

		this.render();

		this.setStyle();

		Region spring1 = new Region();
		Region spring2 = new Region();

		VBox.setVgrow(spring1, Priority.ALWAYS);
		VBox.setVgrow(spring2, Priority.ALWAYS);

		this.getChildren().addAll(spring1, this.primaryVBox, spring2);

		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(20, 0, 20, 0));
		this.primaryVBox.getStyleClass().add("box");

	}

	public void setDailyIntake(DailyIntake dailyIntake) {

		this.dailyIntake = dailyIntake;

		if (this.dayImage == null)
			this.dayImage = new ImageView(ResourceManager.getResourceImage(dailyIntake.getPictureExtension()));
		else
			this.dayImage.setImage(ResourceManager.getResourceImage(dailyIntake.getPictureExtension()));

		this.dayImage.setPreserveRatio(true);
		this.dayImage.setFitWidth(100);

		if (this.dayLabel == null)
			this.dayLabel = new Button(dailyIntake.getName());
		else
			this.dayLabel.setText(dailyIntake.getName());

		this.getChildren().removeAll(this.dayImage, this.dayLabel);
		this.getChildren().addAll(this.dayImage, this.dayLabel);

		this.refreshMealList();

	}

	private void refreshMealList() {

		this.primaryVBox.getChildren().removeAll(this.scrollPane, this.removeAll, this.addMeal);

		this.mealsVBox = new VBox();

		this.scrollPane = new ScrollPane(this.mealsVBox);
		this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		if ((this.dailyIntake.getMeals() != null) && (this.dailyIntake.getMeals().size() > 0))
			for (int i = 0; i < this.dailyIntake.getMeals().size(); i++)
				if (this.dailyIntake.getMeals().get(i) != null)
					this.addMealLabel(this.dailyIntake.getMeals().get(i));

		this.primaryVBox.getChildren().add(this.scrollPane);

		this.removeAll = new Button("Remove All Meals");
		this.removeAll.setOnAction(e -> {

			this.dailyIntake.removeAllMeals();
			new DailyIntakeDA().deleteAllFrom(this.dailyIntake.getDate());
			;
			this.refreshMealList();

		});
		this.primaryVBox.getChildren().add(this.removeAll);

		this.addMeal = new Button("Add Meals");
		this.addMeal.setOnAction(e -> {

			SecondaryStage.showDailyIntakeEditor(this.dailyIntake);

		});

		if (this.showAddMealButton)
			this.primaryVBox.getChildren().add(this.addMeal);

		this.setStyle();

	}

	public void removeMeal(Meal meal) {

		this.dailyIntake.removeMeal(meal);
		this.removeMealLabel(meal);

		this.render();

	}

	public void removeMealLabel(Meal meal) {

		for (Node node : this.mealsVBox.getChildren())
			if (meal == ((Meal) ((EdibleLabel) node).getEdibleObject())) {
				this.mealsVBox.getChildren().remove(node);
				break;
			}

	}

	public void addMeal(Meal meal) {

		this.addMealLabel(meal);

		this.dailyIntake.addMeal(meal);

	}

	public void addMealLabel(Meal meal) {

		EdibleLabel edibleLabel = new EdibleLabel(meal, 115);

		MenuItem remove = new MenuItem("Remove Meal");
		remove.setOnAction(e -> {

			this.dailyIntake.removeMeal(meal);
			this.mealsVBox.getChildren().remove(edibleLabel);

			new DailyIntakeDA().deleteMealFromDay(meal, this.dailyIntake.getDate());

		});

		edibleLabel.getContextMenu().getItems().add(1, remove);

		this.mealsVBox.getChildren().add(edibleLabel);

	}

	public void setStyle() {

		this.primaryVBox.setAlignment(Pos.CENTER);

		this.scrollPane.setMinWidth(160);
		this.scrollPane.getStyleClass().add("scroll-pane-inner-shadow");
		this.scrollPane.setPadding(new Insets(5));
		this.scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		this.mealsVBox.setAlignment(Pos.CENTER);

		this.mealsVBox.setMinWidth(135);
		this.mealsVBox.setSpacing(10);
		this.mealsVBox.setPadding(new Insets(5));
		this.mealsVBox.getStyleClass().add("box-no-shadow");

		this.dayImage.setStyle(
				"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" + "-fx-background-radius: 5;");

		this.primaryVBox.setSpacing(10);

	}

	public ArrayList<EdibleLabel> getEdibleLabels() {

		ArrayList<EdibleLabel> edibleLabels = new ArrayList<>();

		for (Node node : this.mealsVBox.getChildren())
			if (node.getClass() == EdibleLabel.class)
				edibleLabels.add((EdibleLabel) node);

		return edibleLabels;

	}

	public void setAddMealButtonVisible(boolean addMealButtonVisible) {

		this.showAddMealButton = false;

		if (this.addMeal.isVisible())
			this.addMeal.setVisible(false);

	}

	public DailyIntake getDailyIntake() {

		return this.dailyIntake;

	}

	public void render() {

		if (this.dayImage == null)
			this.dayImage = new ImageView(ResourceManager.getResourceImage(this.dailyIntake.getPictureExtension()));
		else
			this.dayImage.setImage(ResourceManager.getResourceImage(this.dailyIntake.getPictureExtension()));

		this.dayImage.setPreserveRatio(true);
		this.dayImage.setFitWidth(100);

		if (this.dayLabel == null)
			this.dayLabel = new Button(this.dailyIntake.getName());
		else
			this.dayLabel.setText(this.dailyIntake.getName());

		this.dayLabel.setOnAction(e -> {

			PrimaryWindow.displayDailyIntakeWeekSummary();

		});

		this.primaryVBox.getChildren().removeAll(this.dayImage, this.dayLabel);
		this.primaryVBox.getChildren().addAll(this.dayImage, this.dayLabel);

		this.refreshMealList();

	}

}
