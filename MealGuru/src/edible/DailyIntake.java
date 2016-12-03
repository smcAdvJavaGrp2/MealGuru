package edible;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import utility.Amount;
import utility.DataFormat;

public class DailyIntake extends Edible {

	// VARIABLES

	ArrayList<Meal> meals;

	int dailyIntakeID;

	Date date;
	Date lastEditDate;

	// CONSTRUCTORS

	public DailyIntake(Date date) {

		this.date = date;
		this.meals = new ArrayList<>();

	}

	// GETTERS

	@Override
	public String getName() {

		return DataFormat.transformDateToString(this.date);

	}

	public Date getDate() {

		return this.date;

	}

	public ArrayList<Meal> getMeals() {

		return this.meals;

	}

	@Override
	public Date getLastEdit() {
		return this.lastEditDate;
	}

	@Override
	public String getPictureExtension() {

		SimpleDateFormat format = new SimpleDateFormat("EEEE");

		return format.format(this.date) + ".PNG";

	}

	@Override
	public double getCalories() {

		double calories = 0;

		for (Meal m : this.getMeals())
			calories += m.getCalories();

		return calories;

	}

	@Override
	public Amount getTotalFat() {

		Amount totalFat = new Amount("0 g");

		for (Meal m : this.getMeals())
			totalFat.add(m.getTotalFat());

		return totalFat;

	}

	@Override
	public Amount getSaturatedFat() {

		Amount saturatedFat = new Amount("0 g");

		for (Meal m : this.getMeals())
			saturatedFat.add(m.getSaturatedFat());

		return saturatedFat;

	}

	@Override
	public Amount getTransFat() {

		Amount transFat = new Amount("0 g");

		for (Meal m : this.getMeals())
			transFat.add(m.getTransFat());

		return transFat;
	}

	@Override
	public Amount getCholesterol() {

		Amount cholesterol = new Amount("0 g");

		for (Meal m : this.getMeals())
			cholesterol.add(m.getCholesterol());

		return cholesterol;
	}

	@Override
	public Amount getSodium() {

		Amount sodium = new Amount("0 g");

		for (Meal m : this.getMeals())
			sodium.add(m.getSodium());

		return sodium;

	}

	@Override
	public Amount getCarbohydrates() {

		Amount carbohydrates = new Amount("0 g");

		for (Meal m : this.getMeals())
			carbohydrates.add(m.getCarbohydrates());

		return carbohydrates;
	}

	@Override
	public Amount getDietaryFiber() {

		Amount dietaryFiber = new Amount("0 g");

		for (Meal m : this.getMeals())
			dietaryFiber.add(m.getDietaryFiber());

		return dietaryFiber;
	}

	@Override
	public Amount getSugar() {

		Amount sugar = new Amount("0 g");

		for (Meal m : this.getMeals())
			sugar.add(m.getSugar());

		return sugar;
	}

	@Override
	public Amount getProtein() {

		Amount protein = new Amount("0 g");

		for (Meal m : this.getMeals())
			protein.add(m.getProtein());

		return protein;

	}

	@Override
	public double getVitaminA() {

		double vitaminA = 0;

		for (Meal m : this.getMeals())
			vitaminA += m.getVitaminA();

		return vitaminA;

	}

	@Override
	public double getVitaminC() {

		double vitaminC = 0;

		for (Meal m : this.getMeals())
			vitaminC += m.getVitaminC();

		return vitaminC;
	}

	@Override
	public double getCalcium() {

		double calcium = 0;

		for (Meal m : this.getMeals())
			calcium += m.getCalcium();

		return calcium;
	}

	@Override
	public double getIron() {

		double iron = 0;

		for (Meal m : this.getMeals())
			iron += m.getIron();

		return iron;
	}

	// SETTERS

	public void setID(int dailyIntakeID) {

		this.dailyIntakeID = dailyIntakeID;

	}

	// METHODS

	public void addMeal(Meal meal) {

		if (this.meals == null)
			this.meals = new ArrayList<>();

		this.meals.add(meal);

	}

	public void addMeals(ArrayList<Meal> arrayListOfMeals) {

		if (arrayListOfMeals == null)
			return;

		for (Meal meal : arrayListOfMeals)
			this.addMeal(meal);

	}

	public void removeMeal(Meal meal) {

		if (this.meals == null)
			return;

		this.meals.remove(meal);

	}

	public void removeAllMeals() {

		this.meals = new ArrayList<>();

	}

	@Override
	public String toString() {

		String toReturn;

		toReturn = "(MEAL) \"" + this.getName() + "\n[";

		toReturn = toReturn + "]\n" + "\nCalories: " + this.getCalories() + "\nTotal Fat: " + this.getTotalFat()
				+ "\n\tSaturated Fat: " + this.getSaturatedFat() + "\n\tTrans Fat: " + this.getTransFat()
				+ "\nCholesterol: " + this.getCholesterol() + "\nSodium: " + this.getSodium() + "\nCarbohydrates: "
				+ this.getCarbohydrates() + "\n\tDietary Fiber: " + this.getDietaryFiber() + "\n\tSugar: "
				+ this.getSugar() + "\nProtein: " + this.getProtein() + "\nVitamin A: " + this.getVitaminA()
				+ "\nVitamin C: " + this.getVitaminC() + "\nCalcium: " + this.getCalcium() + "\nIron: "
				+ this.getIron();

		return toReturn;

	}

}
