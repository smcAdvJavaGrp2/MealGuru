package gui.smartnode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import data.mealguru.DailyIntakeDA;
import edible.DailyIntake;
import gui.PrimaryWindow;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utility.ResourceManager;

public class DailyIntakeGraph extends FlowPane {

	ArrayList<LineChart<String, Number>> charts; // holds each of the charts
	ArrayList<XYChart.Series<String, Number>> nutrients; // holds each of the
															// series
	ArrayList<DailyIntake> dailyIntakes; // holds the users intake by each day
	VBox box;
	Button button;
	double[] avgValues;
	final DecimalFormat df = new DecimalFormat("#.00");
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

	// XYChart.Series<String, Number> nutrients[] = new XYChart.Series<String,
	// Number>[14];

	public DailyIntakeGraph() {

		// initialize all charts and series
		this.charts = new ArrayList<>();
		this.nutrients = new ArrayList<>();
		this.initializeChartsAndArray();
		this.setSeriesNames();

		// initializes button and has method for its action
		this.buttonRelated();

		// settings for FlowPane
		this.setVgap(4);
		this.setHgap(4);
		this.setOrientation(Orientation.HORIZONTAL); // Horizontal FlowPane
		this.setAlignment(Pos.TOP_CENTER); // centers each Node of the FlowPane
		this.setPrefWrapLength(800);
		this.setPadding(new Insets(5, 1, 5, 1));

		this.drawGraph();
		this.createVBox();
		// adds graphs to FlowPane
		for (int i = 0; i < this.charts.size(); i++)
			this.getChildren().add(this.charts.get(i));
		this.getChildren().add(this.box);
	}

	// initial each series and add to ArrayList nutrients
	public void initializeChartsAndArray() {
		this.calories = new XYChart.Series<>();
		this.nutrients.add(this.calories);
		this.totalFat = new XYChart.Series<>();
		this.nutrients.add(this.totalFat);
		this.saturatedFat = new XYChart.Series<>();
		this.nutrients.add(this.saturatedFat);
		this.transFat = new XYChart.Series<>();
		this.nutrients.add(this.transFat);
		this.cholesterol = new XYChart.Series<>();
		this.nutrients.add(this.cholesterol);
		this.sodium = new XYChart.Series<>();
		this.nutrients.add(this.sodium);
		this.carbohydrates = new XYChart.Series<>();
		this.nutrients.add(this.carbohydrates);
		this.dietaryFiber = new XYChart.Series<>();
		this.nutrients.add(this.dietaryFiber);
		this.sugar = new XYChart.Series<>();
		this.nutrients.add(this.sugar);
		this.protein = new XYChart.Series<>();
		this.nutrients.add(this.protein);
		this.vitaminA = new XYChart.Series<>();
		this.nutrients.add(this.vitaminA);
		this.vitaminC = new XYChart.Series<>();
		this.nutrients.add(this.vitaminC);
		this.calcium = new XYChart.Series<>();
		this.nutrients.add(this.calcium);
		this.iron = new XYChart.Series<>();
		this.nutrients.add(this.iron);
	}

	public void buttonRelated() {
		// makes the button
		this.button = new Button("Go Back");
		this.addShadowEffect();

		// actions for the button
		this.button.setOnAction(e -> PrimaryWindow.displayMainGUI());	
	}

	public void createVBox() {
		Text t = this.setVBoxTitle();
		this.box = new VBox(5);
		this.box.setPadding(new Insets(5));
		this.box.getChildren().add(t);
		this.box.setAlignment(Pos.CENTER);
		this.box.setPrefWidth(250);

		Text[] avgs = new Text[14];
		HBox[] hbs = new HBox[7];
		avgs[0] = new Text("Calories: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[0])))) + "g");
		avgs[1] = new Text("Total Fat: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[1])))) + "g");
		avgs[2] = new Text(
				"Saturated Fat: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[2])))) + "g");
		avgs[3] = new Text("Trans Fat: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[3])))) + "g");
		avgs[4] = new Text(
				"Cholesterol: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[4])))) + "mg");
		avgs[5] = new Text("Sodium: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[5])))) + "mg");
		avgs[6] = new Text(
				"Carbohydrates: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[6])))) + "g");
		avgs[7] = new Text(
				"Dietary Fiber: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[7])))) + "g");
		avgs[8] = new Text("Sugar: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[8])))) + "g");
		avgs[9] = new Text("Protein: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[9])))) + "g");
		avgs[10] = new Text(
				"Vitamin A: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[10])))) + "%");
		avgs[11] = new Text(
				"Vitamin C: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[11])))) + "%");
		avgs[12] = new Text("Calcium: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[12])))) + "%");
		avgs[13] = new Text("Iron: " + Double.toString((Double.valueOf(this.df.format(this.avgValues[13])))) + "%");

		for (int i = 0; i < avgs.length; i = i + 2) {
			hbs[i / 2] = new HBox();
			hbs[i / 2].getChildren().add(avgs[i]);
			hbs[i / 2].getChildren().add(avgs[i + 1]);
			hbs[i / 2].setAlignment(Pos.CENTER);
			hbs[i / 2].setMargin(avgs[i + 1], new Insets(5));
			this.box.getChildren().add(hbs[i / 2]);
		}
		this.box.getChildren().add(this.button);
	}

	// creates Title for the VBox and adds style
	public Text setVBoxTitle() {
		Text t = new Text("Weekly Averages");
		t.setId("fancytext");
		return t;
	}

	public void averageValues() {
		// Initializing the average values here and using calculating them
		// within this method
		// because dailyIntakes is
		double avgCalories = 0.0;
		double avgTotalFat = 0.0;
		double avgSaturatedFat = 0.0;
		double avgTransFat = 0.0;
		double avgCholesterol = 0.0;
		double avgSodium = 0.0;
		double avgCarbohydrates = 0.0;
		double avgDietaryFiber = 0.0;
		double avgSugar = 0.0;
		double avgProtein = 0.0;
		double avgVitaminA = 0.0;
		double avgVitaminC = 0.0;
		double avgCalcium = 0.0;
		double avgIron = 0.0;
		this.avgValues = new double[14];

		for (int i = 0; i < this.dailyIntakes.size(); i++) {
			avgCalories += this.dailyIntakes.get(i).getCalories();
			avgTotalFat += this.dailyIntakes.get(i).getTotalFat().getMeasure();
			avgSaturatedFat += this.dailyIntakes.get(i).getSaturatedFat().getMeasure();
			avgTransFat += this.dailyIntakes.get(i).getTransFat().getMeasure();
			avgCholesterol += this.dailyIntakes.get(i).getCholesterol().getMeasure();
			avgSodium += this.dailyIntakes.get(i).getSodium().getMeasure();
			avgCarbohydrates += this.dailyIntakes.get(i).getCarbohydrates().getMeasure();
			avgDietaryFiber += this.dailyIntakes.get(i).getDietaryFiber().getMeasure();
			avgSugar += this.dailyIntakes.get(i).getSugar().getMeasure();
			avgProtein += this.dailyIntakes.get(i).getSugar().getMeasure();
			avgVitaminA += this.dailyIntakes.get(i).getVitaminA();
			avgVitaminC += this.dailyIntakes.get(i).getVitaminC();
			avgCalcium += this.dailyIntakes.get(i).getCalcium();
			avgIron += this.dailyIntakes.get(i).getIron();
		}

		this.avgValues[0] = avgCalories / 7;
		this.avgValues[1] = avgTotalFat / 7;
		this.avgValues[2] = avgSaturatedFat / 7;
		this.avgValues[3] = avgTransFat / 7;
		this.avgValues[4] = avgCholesterol / 7;
		this.avgValues[5] = avgSodium / 7;
		this.avgValues[6] = avgCarbohydrates / 7;
		this.avgValues[7] = avgDietaryFiber / 7;
		this.avgValues[8] = avgSugar / 7;
		this.avgValues[9] = avgProtein / 7;
		this.avgValues[10] = avgVitaminA / 7;
		this.avgValues[11] = avgVitaminC / 7;
		this.avgValues[12] = avgCalcium / 7;
		this.avgValues[13] = avgIron / 7;

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

		// adds data to appropriate series
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

		// creates each of the charts
		for (int i = 0; i < this.nutrients.size(); i++) {
			CategoryAxis xAxis = new CategoryAxis();
			NumberAxis yAxis = new NumberAxis();
			yAxis.setLabel(this.yAxisLabel(this.nutrients.get(i)));
			LineChart<String, Number> tempChart = new LineChart<>(xAxis, yAxis);
			tempChart.getStylesheets()
					.add(ResourceManager.getCSS("chartStyle" + this.styleNumber(this.nutrients.get(i)) + ".css"));
			tempChart.setTitle(this.nutrients.get(i).getName());
			tempChart.getData().add(this.nutrients.get(i));
			tempChart.setPrefSize(250, 220);
			tempChart.setLegendVisible(false);
			this.charts.add(tempChart);
		}
		this.averageValues(); // stores weekly averages into the array avgValues
	}

	// returns the appropriate yAxisLabel for the parameter that is passed
	public String styleNumber(XYChart.Series<String, Number> chart) {
		if (chart.getName().equals("Calories"))
			return Integer.toString(1);
		else if (chart.getName().equals("Total Fat") || chart.getName().equals("Saturated Fat")
				|| chart.getName().equals("Trans Fat") || chart.getName().equals("Carbohydrates")
				|| chart.getName().equals("Dietary Fiber") || chart.getName().equals("Sugar")
				|| chart.getName().equals("Protein"))
			return Integer.toString(2);
		else if (chart.getName().equals("Cholesterol") || chart.getName().equals("Sodium"))
			return Integer.toString(3);
		else
			return Integer.toString(4);
	}

	public String yAxisLabel(XYChart.Series<String, Number> chart) {
		if (chart.getName().equals("Calories"))
			return "Total Calories";
		else if (chart.getName().equals("Total Fat") || chart.getName().equals("Saturated Fat")
				|| chart.getName().equals("Trans Fat") || chart.getName().equals("Carbohydrates")
				|| chart.getName().equals("Dietary Fiber") || chart.getName().equals("Sugar")
				|| chart.getName().equals("Protein"))
			return "Grams";
		else if (chart.getName().equals("Cholesterol") || chart.getName().equals("Sodium"))
			return "Milligrams";
		else
			return "DV Percentage";
	}

	public void addShadowEffect() {
		DropShadow shadow = new DropShadow();
		// Adding the shadow when the mouse cursor is on
		this.button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> DailyIntakeGraph.this.button.setEffect(shadow));
		// Removing the shadow when the mouse cursor is off
		this.button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> DailyIntakeGraph.this.button.setEffect(null));
	}

	// gives all the Series their appropriate names
	public void setSeriesNames() {
		this.calories.setName("Calories");
		this.totalFat.setName("Total Fat");
		this.saturatedFat.setName("Saturated Fat");
		this.transFat.setName("Trans Fat");
		this.cholesterol.setName("Cholesterol");
		this.sodium.setName("Sodium");
		this.carbohydrates.setName("Carbohydrates");
		this.dietaryFiber.setName("Dietary Fiber");
		this.sugar.setName("Sugar");
		this.protein.setName("Protein");
		this.vitaminA.setName("Vitamin A");
		this.vitaminC.setName("Vitamin C");
		this.calcium.setName("Calcium");
		this.iron.setName("Iron");
	}
}
