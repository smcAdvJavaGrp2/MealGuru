package gui.smartnode;

import java.util.ArrayList;
import java.util.Date;

import data.mealguru.DailyIntakeDA;
import edible.DailyIntake;
import gui.MainGUI;
import gui.PrimaryWindow;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import utility.Amount;

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

	    lineChart = new LineChart<String, Number>(
	        xAxis, yAxis);

	    lineChart.setTitle("Line Chart");

	    drawGraph();
	    
        this.setCenter(lineChart);
		
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
		
		calories = new XYChart.Series<String, Number>();
		calories.setName("Calories");
		lineChart.getData().add(calories);
		
		totalFat = new XYChart.Series<String, Number>();
		totalFat.setName("Total Fat");
		lineChart.getData().add(totalFat);
		
		saturatedFat = new XYChart.Series<String, Number>();
		saturatedFat.setName("Saturated Fat");
		lineChart.getData().add(saturatedFat);
		
		transFat = new XYChart.Series<String, Number>();
		transFat.setName("Trans Fat");
		lineChart.getData().add(transFat);
		
		cholesterol = new XYChart.Series<String, Number>();
		cholesterol.setName("Cholesterol");
		lineChart.getData().add(cholesterol);
		
		sodium = new XYChart.Series<String, Number>();
		sodium.setName("Sodium");
		lineChart.getData().add(sodium);
		
		carbohydrates = new XYChart.Series<String, Number>();
		carbohydrates.setName("Carbohydrates");
		lineChart.getData().add(carbohydrates);
		
		dietaryFiber = new XYChart.Series<String, Number>();
		dietaryFiber.setName("Dietary Fiber");
		lineChart.getData().add(dietaryFiber);
		
		sugar = new XYChart.Series<String, Number>();
		sugar.setName("Sugar");
		lineChart.getData().add(sugar);
		
		protein = new XYChart.Series<String, Number>();
		protein.setName("Protein");
		lineChart.getData().add(protein);
		
		vitaminA = new XYChart.Series<String, Number>();
		vitaminA.setName("Vitamin A");
		lineChart.getData().add(vitaminA);
		
		vitaminC = new XYChart.Series<String, Number>();
		vitaminC.setName("Vitamin C");
		lineChart.getData().add(vitaminC);
		
		calcium = new XYChart.Series<String, Number>();
		calcium.setName("Calcium");
		lineChart.getData().add(calcium);
		
		iron = new XYChart.Series<String, Number>();
		iron.setName("Iron");
		lineChart.getData().add(iron);
		
		for(int i = 0; i < dailyIntakes.size(); i++) {
			
			calories.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getCalories()));
			totalFat.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getTotalFat().getMeasure()));
			saturatedFat.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getSaturatedFat().getMeasure()));
			transFat.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getTransFat().getMeasure()));
			cholesterol.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getCholesterol().getMeasure()));
			sodium.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getSodium().getMeasure()));
			carbohydrates.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getCarbohydrates().getMeasure()));
			dietaryFiber.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getDietaryFiber().getMeasure()));
			sugar.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getSugar().getMeasure()));
			protein.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getSugar().getMeasure()));
			vitaminA.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getVitaminA()));
			vitaminC.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getVitaminC()));
			calcium.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getCalcium()));
			iron.getData().add(new XYChart.Data<String, Number>(dailyIntakes.get(i).getName(), dailyIntakes.get(i).getIron()));
			
		}
		
	}
	
}
