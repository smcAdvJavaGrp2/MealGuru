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
		return null;
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
		return null;
	}

	@Override
	public Amount getSaturatedFat() {
		return null;
	}

	@Override
	public Amount getTransFat() {
		return null;
	}

	@Override
	public Amount getCholesterol() {
		return null;
	}

	@Override
	public Amount getSodium() {
		return null;
	}

	@Override
	public Amount getCarbohydrates() {
		return null;
	}

	@Override
	public Amount getDietaryFiber() {
		return null;
	}

	@Override
	public Amount getSugar() {
		return null;
	}

	@Override
	public Amount getProtein() {
		return null;
	}

	@Override
	public double getVitaminA() {
		return 0;
	}

	@Override
	public double getVitaminC() {
		return 0;
	}

	@Override
	public double getCalcium() {
		return 0;
	}

	@Override
	public double getIron() {
		return 0;
	}

	@Override
	public boolean is(String check) {
		return false;
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
		
		if(arrayListOfMeals == null)
			return;
		
		for(Meal meal : arrayListOfMeals)
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
		return String.valueOf(this.meals);
	}

	public boolean isThisDate(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("EEEEEEEEEE, MMMMMMMMM dd, yyyy");

		return format.format(date).equalsIgnoreCase(format.format(this.date));

	}

	/**
	 * @param dailyIntake
	 * @return
	 */
	public boolean equalsTo(DailyIntake dailyIntake) {

		System.out.println(this.getName());
		System.out.println(dailyIntake.getName());

		System.out.println("IN CLASS: " + dailyIntake.getName().equalsIgnoreCase(this.getName()));

		return dailyIntake.getName().equalsIgnoreCase(this.getName());

	}


}
