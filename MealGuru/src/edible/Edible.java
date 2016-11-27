package edible;

import java.util.Date;

import utility.Amount;

public abstract class Edible {

	/****************************
	 * get______();
	 *
	 * This method is meant to return information about Edible Objects.
	 *
	 ****************************/
	public abstract String getName();

	public abstract Date getLastEdit();

	public abstract String getPictureExtension();

	/****************************
	 * get______();
	 *
	 * This method is meant to return information about Edible Objects. These
	 * methods are handled differently for different types of foodobjects.
	 *
	 * Food.java - the method returns the value for one serving size.
	 *
	 * MealComponent.java - the method returns the value of the Food given the
	 * amount of the MealComponent.
	 *
	 * Meal.java - the method returns the total aggregate value for all the
	 * MealComponents this meal has.
	 *
	 ****************************/
	public abstract double getCalories();

	public abstract Amount getTotalFat();

	public abstract Amount getSaturatedFat();

	public abstract Amount getTransFat();

	public abstract Amount getCholesterol();

	public abstract Amount getSodium();

	public abstract Amount getCarbohydrates();

	public abstract Amount getDietaryFiber();

	public abstract Amount getSugar();

	public abstract Amount getProtein();

	public abstract double getVitaminA();

	public abstract double getVitaminC();

	public abstract double getCalcium();

	public abstract double getIron();

	/****************************
	 * is();
	 *
	 * This methods purpose is to return true if a particular edible object
	 * matches the String 'check'.
	 *
	 * If a the method argument is "celery" for example and the Edible Object
	 * name is "Celery", the method will return true.
	 *
	 * If the method argument is "Vegetarian" and the Edible Object has
	 * "vegetarian" in its ArrayList<String> then the method will return true.
	 *
	 ****************************/
	public abstract boolean is(String check);

}
