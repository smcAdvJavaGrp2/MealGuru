package gui.smartnode;

import data.DailyIntakeDA;
import edible.DailyIntake;
import edible.Meal;
import gui.EdibleLableController;
import gui.SecondaryStage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import utility.ResourceManager;

public class DailyIntakeLabel extends VBox {

	ImageView dayImage;
	
	Label dayLabel;

	DailyIntake dailyIntake;

	VBox mealsVBox;
	ScrollPane scrollPane;

	Button removeAll;

	boolean showAddMealButton = true;
	Button addMeal;
	
	public DailyIntakeLabel(DailyIntake dailyIntake) {

		EdibleLableController.addDailyIntakeLabel(this);
		
		this.setDailyIntake(dailyIntake);
							
		this.render();
		
		this.setStyle();

	}

	
	public void setDailyIntake(DailyIntake dailyIntake) {
		
		this.dailyIntake = dailyIntake;
		
		if(this.dayImage == null)
			this.dayImage = new ImageView(ResourceManager.getResourceImage(dailyIntake.getPictureExtension()));
		else
			this.dayImage.setImage(ResourceManager.getResourceImage(dailyIntake.getPictureExtension()));
		
		this.dayImage.setPreserveRatio(true);
		this.dayImage.setFitWidth(100);
		this.dayImage.setOnMouseClicked(e -> {

			System.out.print(this.dailyIntake.getName() + " - ");

			for (int i = 0; i < this.dailyIntake.getMeals().size(); i++)
				System.out.print(this.dailyIntake.getMeals().get(i).getName() + " + ");

			System.out.print("...\n");

		});
		
		if(this.dayLabel == null)
			this.dayLabel = new Label(dailyIntake.getName());
		else
			this.dayLabel.setText(dailyIntake.getName());

		this.getChildren().removeAll(this.dayImage, this.dayLabel);
		this.getChildren().addAll(this.dayImage, this.dayLabel);
		
		this.refreshMealList();
		
	}

	private void refreshMealList() {

		this.getChildren().removeAll(this.scrollPane, this.removeAll, this.addMeal);

		this.mealsVBox = new VBox();
		this.scrollPane = new ScrollPane(this.mealsVBox);
		this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		this.scrollPane.getStyleClass().add("scroll-pane");

		if ((this.dailyIntake.getMeals() != null) && (this.dailyIntake.getMeals().size() > 0))
			for (int i = 0; i < this.dailyIntake.getMeals().size(); i++)
				this.addMealLabel(this.dailyIntake.getMeals().get(i));

		this.getChildren().add(this.scrollPane);

		this.removeAll = new Button("Remove All Meals");
		this.removeAll.setOnAction(e -> {

			this.dailyIntake.removeAllMeals();
			this.refreshMealList();

		});
		this.getChildren().add(this.removeAll);

		this.addMeal = new Button("Add Meals");
		this.addMeal.setOnAction(e -> {

			SecondaryStage.showDailyIntakeEditor(this.dailyIntake);

		});

		if (this.showAddMealButton)
			this.getChildren().add(this.addMeal);

		this.setStyle();

	}

	public void addMeal(Meal meal) {

		this.addMealLabel(meal);

		this.dailyIntake.addMeal(meal);

	}

	public void addMealLabel(Meal meal) {

		EdibleLabel edibleLabel = new EdibleLabel(meal);

		MenuItem remove = new MenuItem("Remove Meal");
		remove.setOnAction(e -> {
			
			edibleLabel.getContextMenu().hide();
			
			this.dailyIntake.removeMeal(meal);
			EdibleLableController.renderEdibleLabels(this.dailyIntake);
			
			new DailyIntakeDA().deleteMealFromDay(meal, dailyIntake.getDate());
			
		});
		
		edibleLabel.getContextMenu().getItems().add(remove);
		
		this.mealsVBox.getChildren().add(edibleLabel);

	}

	public void setStyle() {

		this.setAlignment(Pos.CENTER);

		this.scrollPane.setMinWidth(160);

		this.mealsVBox.setAlignment(Pos.CENTER);

		this.mealsVBox.setMinWidth(160);
		this.mealsVBox.setPadding(new Insets(5));
		this.mealsVBox.setSpacing(5);

		this.dayImage.setStyle(
				"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" + "-fx-background-radius: 5;");

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

		if(this.dayImage == null)
			this.dayImage = new ImageView(ResourceManager.getResourceImage(dailyIntake.getPictureExtension()));
		else
			this.dayImage.setImage(ResourceManager.getResourceImage(dailyIntake.getPictureExtension()));
		
		this.dayImage.setPreserveRatio(true);
		this.dayImage.setFitWidth(100);
		this.dayImage.setOnMouseClicked(e -> {

			System.out.print(this.dailyIntake.getName() + " - ");

			for (int i = 0; i < this.dailyIntake.getMeals().size(); i++)
				System.out.print(this.dailyIntake.getMeals().get(i).getName() + " + ");

			System.out.print("...\n");

		});
		
		if(this.dayLabel == null)
			this.dayLabel = new Label(dailyIntake.getName());
		else
			this.dayLabel.setText(dailyIntake.getName());

		this.getChildren().removeAll(this.dayImage, this.dayLabel);
		this.getChildren().addAll(this.dayImage, this.dayLabel);
		
		this.refreshMealList();

	}
	
}
