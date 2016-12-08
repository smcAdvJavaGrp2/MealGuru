package edible;

import java.util.ArrayList;
import java.util.Date;

import utility.Amount;
import utility.UnitClassification;
import utility.Units;

public class Food extends Edible implements Comparable {

	// VARIABLES

	int foodID;

	String name;

	private ArrayList<String> categories;

	String pictureExtension;

	Date lastEdit;

	// SERVINGS PER CONTAINER

	private Amount servingsPerThis;
	private Amount unitsPerServingSize;
	private Amount weightPerServingSize;
	private Amount liquidVolumePerServingSize;

	double calories;

	Amount totalFat;
	Amount saturatedFat;
	Amount transFat;

	Amount cholesterol;

	Amount sodium;

	Amount carbohydrates;
	Amount dietaryFiber;
	Amount sugar;

	Amount protein;

	double vitaminA;
	double vitaminC;

	double calcium;
	double iron;

	// CONSTRUCTORS

	public Food() {

		this.setName("untitled");
		this.setPictureExtension("defaultfood.png");
		this.setCategories(null);
		this.setCalories(0);
		this.setTotalFat(new Amount(0, Units.GRAM));
		this.setSaturatedFat(new Amount(0, Units.GRAM));
		this.setTransFat(new Amount(0, Units.GRAM));
		this.setCholesterol(new Amount(0, Units.MILLIGRAM));
		this.setSodium(new Amount(0, Units.MILLIGRAM));
		this.setCarbohydrates(new Amount(0, Units.GRAM));
		this.setDietaryFiber(new Amount(0, Units.GRAM));
		this.setSugar(new Amount(0, Units.GRAM));
		this.setProtein(new Amount(0, Units.GRAM));
		this.setVitaminA(0);
		this.setVitaminC(0);
		this.setCalcium(0);
		this.setIron(0);

	}

	// GETTERS
	public int getID() {
		return this.foodID;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getPictureExtension() {
		return this.pictureExtension;
	}

	@Override
	public Date getLastEdit() {
		return this.lastEdit;
	}

	public Amount getWeightPerServingSize() {
		return this.weightPerServingSize;
	}

	public Amount getLiquidVolumePerServingSize() {
		return this.liquidVolumePerServingSize;
	}

	public Amount getUnitsPerServingSize() {
		return this.unitsPerServingSize;
	}

	@Override
	public double getCalories() {
		return this.calories;
	}

	@Override
	public Amount getTotalFat() {
		return this.totalFat;
	}

	@Override
	public Amount getSaturatedFat() {
		return this.saturatedFat;
	}

	@Override
	public Amount getTransFat() {
		return this.transFat;
	}

	@Override
	public Amount getCholesterol() {
		return this.cholesterol;
	}

	@Override
	public Amount getSodium() {
		return this.sodium;
	}

	@Override
	public Amount getCarbohydrates() {
		return this.carbohydrates;
	}

	@Override
	public Amount getDietaryFiber() {
		return this.dietaryFiber;
	}

	@Override
	public Amount getSugar() {
		return this.sugar;
	}

	@Override
	public Amount getProtein() {
		return this.protein;
	}

	@Override
	public double getVitaminA() {
		return this.vitaminA;
	}

	@Override
	public double getVitaminC() {
		return this.vitaminC;
	}

	@Override
	public double getCalcium() {
		return this.calcium;
	}

	@Override
	public double getIron() {
		return this.iron;
	}

	public ArrayList<String> getCategories() {
		return this.categories;
	}

	// SETTERS
	public void setFoodID(int foodID) {

		this.foodID = foodID;

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPictureExtension(String pictureExtension) {
		this.pictureExtension = pictureExtension;
	}

	public void setLastEdit(Date date) {
		this.lastEdit = date;
	}

	public void setLastEdit() {
		this.lastEdit = new Date();
	}

	public void setServingSize(Amount amount) {

		if ((amount.getMeasure() == 0) || (amount.getUnits() == null))
			return;

		if (amount.getUnits().getClassification() == UnitClassification.SERVING)
			this.servingsPerThis = amount;
		else if ((amount.getUnits().getClassification() == UnitClassification.UNIT)
				|| (amount.getUnits().getClassification() == UnitClassification.CONTAINER)) {

			if (amount.getUnits() == Units.CONTAINER)
				this.unitsPerServingSize = new Amount(1 / amount.getMeasure(), Units.UNIT);
			else
				this.unitsPerServingSize = new Amount(amount.getMeasure(), Units.UNIT);

		} else if (amount.getUnits().getClassification() == UnitClassification.WEIGHTED)
			this.weightPerServingSize = amount;
		else if (amount.getUnits().getClassification() == UnitClassification.LIQUID_VOLUME)
			this.liquidVolumePerServingSize = amount;

	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public void setTotalFat(Amount totalFat) {
		this.totalFat = totalFat;
	}

	public void setSaturatedFat(Amount saturatedFat) {
		this.saturatedFat = saturatedFat;
	}

	public void setTransFat(Amount transFat) {
		this.transFat = transFat;
	}

	public void setCholesterol(Amount cholesterol) {
		this.cholesterol = cholesterol;
	}

	public void setSodium(Amount sodium) {
		this.sodium = sodium;
	}

	public void setCarbohydrates(Amount carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public void setDietaryFiber(Amount dietaryFiber) {
		this.dietaryFiber = dietaryFiber;
	}

	public void setSugar(Amount sugar) {
		this.sugar = sugar;
	}

	public void setProtein(Amount protein) {
		this.protein = protein;
	}

	public void setVitaminA(double vitaminA) {
		this.vitaminA = vitaminA;
	}

	public void setVitaminC(double vitaminC) {
		this.vitaminC = vitaminC;
	}

	public void setCalcium(double calcium) {
		this.calcium = calcium;
	}

	public void setIron(double iron) {
		this.iron = iron;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	// METHODS

	public void addTag(String tag) {

		if (this.getCategories() == null)
			this.setCategories(new ArrayList<>());

		this.getCategories().add(tag.toLowerCase());

	}

	public void removeTag(String tag) {

		this.getCategories().remove(tag.toLowerCase());

	}

	/**
	 * Orders Food Objects from most healthy to most unhealthy
	 * Compares food according to simple heuristic, The food with the lower result is healthier
	 * by this arbitrary metric. 
	 * 
	 */
	@Override
	public int compareTo(Object other) {
		double ratio;
		int t = 0;
		int o = 0;
		if (other instanceof Food) {
			Food food = (Food) other;
			if (this.calories < food.calories)
				ratio = this.calories / food.calories;
			else
				ratio = 1 - this.calories / food.calories;
			if (this.totalFat.getMeasure() * ratio < food.totalFat.getMeasure())
				o = o + 4;
			else
				t = t + 4;

			if (this.saturatedFat.getMeasure() * ratio < food.saturatedFat.getMeasure() * ratio)
				o = o + 5;
			else
				t = t + 5;

			if (this.transFat.getMeasure() * ratio < food.transFat.getMeasure() * ratio)

				o = o + 5;
			else
				t = t + 5;

			if (this.cholesterol.getMeasure() * ratio < food.cholesterol.getMeasure() * ratio)

				o = o + 5;
			else
				t = t + 5;

			if (this.sodium.getMeasure() * ratio < food.sodium.getMeasure() * ratio)

				o = o + 4;
			else
				t = t + 4;

			if (this.carbohydrates.getMeasure() * ratio < food.carbohydrates.getMeasure() * ratio)

				o = o + 2;
			else
				t = t + 2;

			if (this.dietaryFiber.getMeasure() * ratio < food.dietaryFiber.getMeasure() * ratio)

				o = o - 4;
			else
				t = t - 4;

			if (this.sugar.getMeasure() * ratio < food.sugar.getMeasure() * ratio)

				o = o + 4;
			else
				t = t + 4;

			if (this.protein.getMeasure() * ratio < food.protein.getMeasure() * ratio)

				o = o - 4;
			else
				t = t - 4;

			if (this.vitaminA * ratio < food.vitaminA * ratio)

				o = o - 1;
			else
				t = t - 1;

			if (this.vitaminC * ratio < food.vitaminC * ratio)

				o = o - 1;
			else
				t = t - 1;

			if (this.calcium * ratio < food.calcium * ratio)

				o = o - 1;
			else
				t = t - 1;

			if (this.iron * ratio < food.iron * ratio)

				o = o - 1;
			else
				t = t - 1;
		}
		return t > o ? +1 : t < o ? -1 : 0;
	}

	@Override
	public String toString() {

		String toReturn;

		toReturn = "(Food) \"" + this.name + "\" - last edit: " + this.lastEdit + "\n" + this.pictureExtension + "\n"
				+ this.getCategories() + "\n" + "Serving Size:" + "\n\t" + this.getUnitsPerServingSize()
				+ " per serving" + "\n\t" + this.getWeightPerServingSize() + " per serving" + "\n\t"
				+ this.getLiquidVolumePerServingSize() + " per serving" + "\nCalories: " + this.getCalories()
				+ "\nTotal Fat: " + this.getTotalFat() + "\n\tSaturated Fat: " + this.getSaturatedFat()
				+ "\n\tTrans Fat: " + this.getTransFat() + "\nCholesterol: " + this.getCholesterol() + "\nSodium: "
				+ this.getSodium() + "\nCarbohydrates: " + this.getCarbohydrates() + "\n\tDietary Fiber: "
				+ this.getDietaryFiber() + "\n\tSugar: " + this.getSugar() + "\nProtein: " + this.getProtein()
				+ "\nVitamin A: " + this.getVitaminA() + "\nVitamin C: " + this.getVitaminC() + "\nCalcium: "
				+ this.getCalcium() + "\nIron: " + this.getIron();

		return toReturn;

	}

	public Amount getServingSize() {
		return this.servingsPerThis;
	}

}