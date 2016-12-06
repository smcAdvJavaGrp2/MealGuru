package gui.smartnode;

import java.util.ArrayList;
import java.util.Date;

import data.mealguru.DailyIntakeDA;
import edible.DailyIntake;
import gui.PrimaryWindow;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;

public class DailyIntakeGraph extends BorderPane {

	final LineChart<String, Number> lineChart;

	ArrayList<DailyIntake> dailyIntakes;

	XYChart.Series<String, Number> calories;
	XYChart.Series<String, Number> totalFat;
	XYChart.Series<String, Number> saturatedFat;
	XYChart.Series<String, Number> transFat;
	XYChart.Series<String, Number> cholesterol;
	XYChart.Series<String, Number> sodium;
	XYChart.Series<String, Number> carbohydrates;
	XYChart.Series<String, Number> dietaryFiber;
	XYChart.Series<String, Number> sugar;
	XYChart.Series<String, Number> protein;
	XYChart.Series<String, Number> vitaminA;
	XYChart.Series<String, Number> vitaminC;
	XYChart.Series<String, Number> calcium;
	XYChart.Series<String, Number> iron;

	public DailyIntakeGraph() {

		final CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Day");

		final NumberAxis yAxis = new NumberAxis();

		this.lineChart = new LineChart<>(xAxis, yAxis);

		this.lineChart.setTitle("Line Chart");

		this.drawGraph();

		this.setCenter(this.lineChart);

	}

	public void drawGraph() {

		Date dateToDisplay = new Date();
		dateToDisplay.setTime(PrimaryWindow.getCenterDate().getTime() - (3 * 24 * 60 * 60 * 1000));

		this.dailyIntakes = new ArrayList<>();

		DailyIntakeDA dailyIntakeDA = new DailyIntakeDA();
		for (int i = 0; i < 7; i++) {

			DailyIntake dailyIntake = dailyIntakeDA.getDailyIntakeByDay(dateToDisplay);

			if (dailyIntake != null)
				this.dailyIntakes.add(dailyIntake);
			else
				this.dailyIntakes.add(new DailyIntake(dateToDisplay));

			dateToDisplay.setTime(dateToDisplay.getTime() + (1 * 24 * 60 * 60 * 1000));

		}

		this.calories = new XYChart.Series<>();
		this.calories.setName("Calories");
		this.lineChart.getData().add(this.calories);

		this.totalFat = new XYChart.Series<>();
		this.totalFat.setName("Total Fat");
		this.lineChart.getData().add(this.totalFat);

		this.saturatedFat = new XYChart.Series<>();
		this.saturatedFat.setName("Saturated Fat");
		this.lineChart.getData().add(this.saturatedFat);

		this.transFat = new XYChart.Series<>();
		this.transFat.setName("Trans Fat");
		this.lineChart.getData().add(this.transFat);

		this.cholesterol = new XYChart.Series<>();
		this.cholesterol.setName("Cholesterol");
		this.lineChart.getData().add(this.cholesterol);

		this.sodium = new XYChart.Series<>();
		this.sodium.setName("Sodium");
		this.lineChart.getData().add(this.sodium);

		this.carbohydrates = new XYChart.Series<>();
		this.carbohydrates.setName("Carbohydrates");
		this.lineChart.getData().add(this.carbohydrates);

		this.dietaryFiber = new XYChart.Series<>();
		this.dietaryFiber.setName("Dietary Fiber");
		this.lineChart.getData().add(this.dietaryFiber);

		this.sugar = new XYChart.Series<>();
		this.sugar.setName("Sugar");
		this.lineChart.getData().add(this.sugar);

		this.protein = new XYChart.Series<>();
		this.protein.setName("Protein");
		this.lineChart.getData().add(this.protein);

		this.vitaminA = new XYChart.Series<>();
		this.vitaminA.setName("Vitamin A");
		this.lineChart.getData().add(this.vitaminA);

		this.vitaminC = new XYChart.Series<>();
		this.vitaminC.setName("Vitamin C");
		this.lineChart.getData().add(this.vitaminC);

		this.calcium = new XYChart.Series<>();
		this.calcium.setName("Calcium");
		this.lineChart.getData().add(this.calcium);

		this.iron = new XYChart.Series<>();
		this.iron.setName("Iron");
		this.lineChart.getData().add(this.iron);

		for (int i = 0; i < this.dailyIntakes.size(); i++) {

			this.calories.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getCalories()));
			this.totalFat.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getTotalFat().getMeasure()));
			this.saturatedFat.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getSaturatedFat().getMeasure()));
			this.transFat.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getTransFat().getMeasure()));
			this.cholesterol.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getCholesterol().getMeasure()));
			this.sodium.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getSodium().getMeasure()));
			this.carbohydrates.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getCarbohydrates().getMeasure()));
			this.dietaryFiber.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getDietaryFiber().getMeasure()));
			this.sugar.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getSugar().getMeasure()));
			this.protein.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getSugar().getMeasure()));
			this.vitaminA.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getVitaminA()));
			this.vitaminC.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getVitaminC()));
			this.calcium.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getCalcium()));
			this.iron.getData().add(new XYChart.Data<String, Number>(this.dailyIntakes.get(i).getName(),
					this.dailyIntakes.get(i).getIron()));

		}

	}

}
