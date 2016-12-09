package gui;

import data.mealguru.DietDA;
import gui.smartnode.DoubleTextField;
import gui.smartnode.IntegerTextField;
import gui.smartnode.TagCreator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import user.Diet;
import utility.Amount;
import utility.Units;

public class DietEditor extends GridPane {
	
	IntegerTextField caloriesLimitTextField;
	DoubleTextField totalFatLimitTextField, saturatedFatLimitTextField, transFatLimitTextField;
	DoubleTextField cholesterolLimitTextField; 
	DoubleTextField sodiumLimitTextField;
	DoubleTextField carbohydratesLimitTextField, dietaryFiberLimitTextField, sugarLimitTextField;
	DoubleTextField proteinLimitTextField;
	DoubleTextField vitaminALimitTextField, vitaminCLimitTextField;
	DoubleTextField calciumLimitTextField, ironLimitTextField;
	TagCreator categoryPreferenceCreator;
	
	public DietEditor() {
		
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		this.setMaxSize(getPrefWidth(), getPrefHeight());
		
		
		Separator separator = new Separator();
		
		
		if (PrimaryWindow.getActiveUser().getDiet() == null){
			PrimaryWindow.getActiveUser().setDiet(new Diet());
			System.out.println("new diet");
		}
		// Labels
		
		Label caloriesLimitLabel = new Label("Daily Calories Limit: ");
		this.add(caloriesLimitLabel, 0, 0);
		
		this.addRow(1, separator);
		
		Label totalFatLimitLabel = new Label("Daily Total Fat Limit(in grams): ");
		this.add(totalFatLimitLabel, 0, 2);
		Label saturatedFatLimitLabel = new Label("Daily Saturated Fat Limit(in grams): ");
		this.add(saturatedFatLimitLabel, 0, 3);
		Label transFatLimitLabel = new Label("Daily Trans Fat Limit(in grams): ");
		this.add(transFatLimitLabel, 0, 4);
		
		Label cholesterolLimitLabel = new Label("Daily Cholesterol Limit(in milligrams): ");
		this.add(cholesterolLimitLabel, 0, 6);
		
		Label sodiumLimitLabel = new Label("Daily Sodium Limit(in milligrams): ");
		this.add(sodiumLimitLabel, 0, 8);
		
		Label carbohydratesLimitLabel = new Label("Daily Carbohydrates Limit(in grams): ");
		this.add(carbohydratesLimitLabel, 0, 10);
		Label dietaryFiberLimitLabel = new Label("Daily Dietary Fiber Limit(in grams): ");
		this.add(dietaryFiberLimitLabel, 0, 11);
		Label sugarLimitLabel = new Label("Daily Sugar Limit(in grams): ");
		this.add(sugarLimitLabel, 0, 12);
		
		Label proteinLimitLabel = new Label("Daily Protein Limit(in grams): ");
		this.add(proteinLimitLabel, 0, 14);
		
		Label vitaminALimitLabel = new Label("Daily Vitamin A Limit(in milligrams): ");
		this.add(vitaminALimitLabel, 0, 16);
		Label vitaminCLimitLabel = new Label("Daily Vitamin C Limit(in milligrams): ");
		this.add(vitaminCLimitLabel, 0, 17);
		
		Label calciumLimitLabel = new Label("Daily Calcium Limit(in mlligrams): ");
		this.add(calciumLimitLabel, 0, 19);
		Label ironLimitLabel = new Label("Daily Iron Limit(in milligrams): ");
		this.add(ironLimitLabel, 0, 20);
		
		Label categoryPreferenceCreateorLabel = new Label("Choose your category preferences here:");
		this.add(categoryPreferenceCreateorLabel, 0, 24);
		
		
		// TextFields
		
		caloriesLimitTextField = new IntegerTextField();
		
		caloriesLimitTextField.setText(Integer.toString(PrimaryWindow.getActiveUser().getDiet().getCaloriesLimit()));
		caloriesLimitTextField.setMinWidth(100);
		caloriesLimitTextField.setMaxWidth(600);
		this.add(caloriesLimitTextField, 1, 0);
		
		totalFatLimitTextField = new DoubleTextField();
		totalFatLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getTotalFatLimit().getMeasure()));
		totalFatLimitTextField.setMaxWidth(600);
		this.add(totalFatLimitTextField, 1, 2);
		saturatedFatLimitTextField = new DoubleTextField();
		saturatedFatLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getSaturatedFatLimit().getMeasure()));
		saturatedFatLimitTextField.setMaxWidth(600);
		this.add(saturatedFatLimitTextField, 1, 3);
		transFatLimitTextField = new DoubleTextField();
		transFatLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getTransFatLimit().getMeasure()));
		transFatLimitTextField.setMaxWidth(600);
		this.add(transFatLimitTextField, 1, 4);
		
		cholesterolLimitTextField = new DoubleTextField();
		cholesterolLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getCholesterolLimit().getMeasure()));
		cholesterolLimitTextField.setMaxWidth(600);
		this.add(cholesterolLimitTextField, 1, 6);
		
		sodiumLimitTextField = new DoubleTextField();
		sodiumLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getSodiumLimit().getMeasure()));	
		sodiumLimitTextField.setMaxWidth(600);
		this.add(sodiumLimitTextField, 1, 8);
		
		carbohydratesLimitTextField = new DoubleTextField();
		carbohydratesLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getCarbohydratesLimit().getMeasure()));
		carbohydratesLimitTextField.setMaxWidth(600);
		this.add(carbohydratesLimitTextField, 1, 10);
		dietaryFiberLimitTextField = new DoubleTextField();
		dietaryFiberLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getDietaryFiberLimit().getMeasure()));
		dietaryFiberLimitTextField.setMaxWidth(600);
		this.add(dietaryFiberLimitTextField, 1, 11);
		sugarLimitTextField = new DoubleTextField();
		sugarLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getSugarLimit().getMeasure()));
		sugarLimitTextField.setMaxWidth(600);
		this.add(sugarLimitTextField, 1, 12);
		
		proteinLimitTextField = new DoubleTextField();
		proteinLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getProteinLimit().getMeasure()));
		proteinLimitTextField.setMaxWidth(600);
		this.add(proteinLimitTextField, 1, 14);
		
		vitaminALimitTextField = new DoubleTextField();
		vitaminALimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getVitaminALimit()));
		vitaminALimitTextField.setMaxWidth(600);
		this.add(vitaminALimitTextField, 1, 16);
		vitaminCLimitTextField = new DoubleTextField();
		vitaminCLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getVitaminCLimit()));
		vitaminCLimitTextField.setMaxWidth(600);
		this.add(vitaminCLimitTextField, 1, 17);
		
		calciumLimitTextField = new DoubleTextField();
		calciumLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getCalciumLimit()));
		calciumLimitTextField.setMaxWidth(600);
		this.add(calciumLimitTextField, 1, 19);
		ironLimitTextField = new DoubleTextField();
		ironLimitTextField.setText(Double.toString(PrimaryWindow.getActiveUser().getDiet().getIronLimit()));
		ironLimitTextField.setMaxWidth(600);
		this.add(ironLimitTextField, 1, 20);
		
		
		// Category Preference Creator
		
		categoryPreferenceCreator = new TagCreator();
		if (PrimaryWindow.getActiveUser().getDiet().getCategoryPreference() != null)
			categoryPreferenceCreator.setCategories(PrimaryWindow.getActiveUser().getDiet().getCategoryPreference());
		categoryPreferenceCreator.setMaxWidth(300);
		this.addRow(25, categoryPreferenceCreator);
	}
	
	private void update() {
		
		if ((this.caloriesLimitTextField.getText() != null) && !this.caloriesLimitTextField.getText().equals("")) 
			PrimaryWindow.getActiveUser().getDiet().setCaloriesLimit(this.caloriesLimitTextField.getValue());
		
		if (this.totalFatLimitTextField.getText() != null && !this.totalFatLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setTotalFatLimit(new Amount(this.totalFatLimitTextField.getValue(), Units.GRAM));
		if (this.saturatedFatLimitTextField.getText() != null && !this.saturatedFatLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setSaturatedFatLimit(new Amount(this.saturatedFatLimitTextField.getValue(), Units.GRAM));
		if (this.transFatLimitTextField.getText() != null && !this.transFatLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setTransFatLimit(new Amount(this.transFatLimitTextField.getValue(), Units.GRAM));
		
		if (this.cholesterolLimitTextField.getText() != null && !this.cholesterolLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setCholesterolLimit(new Amount(this.cholesterolLimitTextField.getValue(), Units.MILLIGRAM));
		
		if (this.sodiumLimitTextField.getText() != null && !this.sodiumLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setSodiumLimit(new Amount(this.sodiumLimitTextField.getValue(), Units.MILLIGRAM));
		
		if (this.carbohydratesLimitTextField.getText() != null && !this.carbohydratesLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setCarbohydratesLimit(new Amount(this.carbohydratesLimitTextField.getValue(), Units.GRAM));
		if (this.dietaryFiberLimitTextField.getText() != null && !this.dietaryFiberLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setDietaryFiberLimit(new Amount(this.dietaryFiberLimitTextField.getValue(), Units.GRAM));
		if (this.sugarLimitTextField.getText() != null && !this.sugarLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setSugarLimit(new Amount(this.sugarLimitTextField.getValue(), Units.GRAM));
		
		if (this.proteinLimitTextField.getText() != null && !this.proteinLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setProteinLimit(new Amount(this.proteinLimitTextField.getValue(), Units.GRAM));
		
		if (this.vitaminALimitTextField.getText() != null && !this.vitaminALimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setVitaminALimit(this.vitaminALimitTextField.getValue());
		if (this.vitaminCLimitTextField.getText() != null && !this.vitaminCLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setVitaminCLimit(this.vitaminCLimitTextField.getValue());
		
		if (this.calciumLimitTextField.getText() != null && !this.calciumLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setCalciumLimit(this.calciumLimitTextField.getValue());
		if (this.ironLimitTextField.getText() != null && !this.ironLimitTextField.getText().equals(""))
			PrimaryWindow.getActiveUser().getDiet().setIronLimit(this.ironLimitTextField.getValue());
		
		if (this.categoryPreferenceCreator.getCategories() != null)
			PrimaryWindow.getActiveUser().getDiet().setCategoryPreference(this.categoryPreferenceCreator.getCategories());
		
	}
	
	public void updateDiet() {
		this.update();
		if (new DietDA().getDiet()!=null)
			new DietDA().updateDiet(PrimaryWindow.getActiveUser().getDiet());
		else
			this.saveDiet();
	}
	
	public void saveDiet() {
		this.update();
		new DietDA().saveDiet(PrimaryWindow.getActiveUser().getDiet());
	}
	
}
