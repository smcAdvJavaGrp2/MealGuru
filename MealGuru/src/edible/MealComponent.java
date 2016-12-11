package edible;

import java.util.Date;

import utility.Amount;
import utility.UnitClassification;
import utility.Units;

public class MealComponent extends Edible implements Comparable<MealComponent>{

	// VARIABLES

	private int mealComponentID;

	private Food food;
	private Amount foodAmount;

	// CONSTRUCTORS

	public MealComponent() {
	}

	public MealComponent(Food food, Amount amount) {

		this(food);
		this.setAmount(amount);

	}

	public MealComponent(Food food, double measure, Units units) {

		this(food);
		this.foodAmount = new Amount(measure, units);

	}

	public MealComponent(Food food, String foodAmount) {

		this(food);
		this.foodAmount = new Amount(foodAmount);

	}

	private MealComponent(Food food) {

		this.food = food;

	}

	// GETTERS

	public int getID() {
		return this.mealComponentID;
	}

	public Food getFood() {
		return this.food;
	}

	@Override
	public String getName() {
		return this.food.name;
	}

	public Amount getAmount() {
		return this.foodAmount;
	}

	@Override
	public double getCalories() {
		return (this.food.getCalories() * this.getSizeRatio());
	}

	@Override
	public Amount getTotalFat() {
		return new Amount(this.food.getTotalFat().getMeasure() * this.getSizeRatio(), Units.GRAM);
	}

	@Override
	public Amount getSaturatedFat() {
		return new Amount(this.food.getSaturatedFat().getMeasure() * this.getSizeRatio(), Units.GRAM);
	}

	@Override
	public Amount getTransFat() {
		return new Amount(this.food.getTransFat().getMeasure() * this.getSizeRatio(), Units.GRAM);
	}

	@Override
	public Amount getCholesterol() {
		return new Amount(this.food.getCholesterol().getMeasure() * this.getSizeRatio(), Units.MILLIGRAM);
	}

	@Override
	public Amount getSodium() {
		return new Amount(this.food.getSodium().getMeasure() * this.getSizeRatio(), Units.MILLIGRAM);
	}

	@Override
	public Amount getCarbohydrates() {
		return new Amount(this.food.getCarbohydrates().getMeasure() * this.getSizeRatio(), Units.GRAM);
	}

	@Override
	public Amount getDietaryFiber() {
		return new Amount(this.food.getDietaryFiber().getMeasure() * this.getSizeRatio(), Units.GRAM);
	}

	@Override
	public Amount getSugar() {
		return new Amount(this.food.getSugar().getMeasure() * this.getSizeRatio(), Units.GRAM);
	}

	@Override
	public Amount getProtein() {
		return new Amount(this.food.getProtein().getMeasure() * this.getSizeRatio(), Units.GRAM);
	}

	@Override
	public double getVitaminA() {
		return this.food.getVitaminA() * this.getSizeRatio();
	}

	@Override
	public double getVitaminC() {
		return this.food.getVitaminC() * this.getSizeRatio();
	}

	@Override
	public double getCalcium() {
		return this.food.getCalcium() * this.getSizeRatio();
	}

	@Override
	public double getIron() {
		return this.food.getIron() * this.getSizeRatio();
	}

	@Override
	public Date getLastEdit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPictureExtension() {
		return this.food.pictureExtension;
	}

	// SETTERS

	public void setID(int mealComponentID) {
		this.mealComponentID = mealComponentID;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public void setAmount(Amount amount) {
		this.foodAmount = amount;
	}

	// METHODS

	private double getSizeRatio() {

		// Returns the relationship between the serving size and the measure of
		// the ingredient to
		// further calculate the nutrition information of a certain ingredient
		// measure

		double ratio = 0;

		if (this.foodAmount.getUnits() == Units.SERVING)
			ratio = this.foodAmount.getMeasure();
		else if ((this.foodAmount.getUnits() == Units.UNIT) && (this.food.getUnitsPerServingSize() != null))
			ratio = this.foodAmount.getMeasure() * this.food.getUnitsPerServingSize().getMeasure();
		else if ((this.foodAmount.getUnits().getClassification() == UnitClassification.WEIGHTED)
				&& (this.food.getWeightPerServingSize() != null)) {

			this.foodAmount.convert(this.food.getWeightPerServingSize().getUnits());
			ratio = this.foodAmount.getMeasure() / this.food.getWeightPerServingSize().getMeasure();

		} else if ((this.foodAmount.getUnits().getClassification() == UnitClassification.LIQUID_VOLUME)
				&& (this.food.getLiquidVolumePerServingSize() != null)) {

			this.foodAmount.convert(this.food.getLiquidVolumePerServingSize().getUnits());
			ratio = this.foodAmount.getMeasure() / this.food.getLiquidVolumePerServingSize().getMeasure();

		}

		return ratio;

	}

	@Override
	public String toString() {

		String toReturn;

		toReturn = "(MealComponent) \"" + this.getFood().getName() + "\" - " + this.getAmount() + "\n"
				+ this.food.pictureExtension + "\n" + this.food.getCategories() + "\n" + "Serving Size:"
				+ "\nCalories: " + this.getCalories() + "\nTotal Fat: " + this.getTotalFat() + "\n\tSaturated Fat: "
				+ this.getSaturatedFat() + "\n\tTrans Fat: " + this.getTransFat() + "\nCholesterol: "
				+ this.getCholesterol() + "\nSodium: " + this.getSodium() + "\nCarbohydrates: "
				+ this.getCarbohydrates() + "\n\tDietary Fiber: " + this.getDietaryFiber() + "\n\tSugar: "
				+ this.getSugar() + "\nProtein: " + this.getProtein() + "\nVitamin A: " + this.getVitaminA()
				+ "\nVitamin C: " + this.getVitaminC() + "\nCalcium: " + this.getCalcium() + "\nIron: "
				+ this.getIron();

		return toReturn;

	}

	@Override
	public int compareTo(MealComponent mealComponent) {
		 int c;
	    c = this.getName().compareToIgnoreCase(mealComponent.getName());		
	    if (c == 0)
	    	c = this.getCalories() > mealComponent.getCalories() ? +1 : this.getCalories() < mealComponent.getCalories() ? -1 : 0;
	    return c;
	}
	
}