package edible;

import java.util.ArrayList;
import java.util.Date;

import utility.Amount;
import utility.Units;

public class Meal extends Edible implements Comparable<Meal>{

	// VARIABLES

	private int mealID;

	private String name;

	private String directions;

	private String pictureExtension;

	private ArrayList<MealComponent> mealComponents;

	private ArrayList<String> categories;

	private Date lastEdit;

	// CONSTRUCTORS

	public Meal() {

		this.setName("Untitled");

		this.pictureExtension = "defaultmeal.png";

	}

	public Meal(String mealName, MealComponent... foods) {

		this();

		this.setName(mealName);

		for (MealComponent food : foods)
			this.addMealComponent(food);

	}

	public Meal(String mealName, String pictureExtension, MealComponent... foods) {

		this.setName(mealName);

		this.pictureExtension = pictureExtension;

		for (MealComponent food : foods)
			this.addMealComponent(food);

	}

	// GETTERS

	public int getID() {
		return this.mealID;
	}

	@Override
	public String getName() {

		return this.name;

	}

	@Override
	public Date getLastEdit() {
		return this.lastEdit;
	}

	public ArrayList<String> getCategories() {
		return this.categories;
	}

	@Override
	public String getPictureExtension() {

		return this.pictureExtension;

	}

	public ArrayList<MealComponent> getMealComponents() {
		return this.mealComponents;
	}

	@Override
	public double getCalories() {

		double toReturn = 0;

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn += mealComponent.getCalories();

		return toReturn;

	}

	@Override
	public Amount getTotalFat() {

		Amount toReturn = new Amount(0, Units.GRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getTotalFat());

		return toReturn;

	}

	@Override
	public Amount getSaturatedFat() {

		Amount toReturn = new Amount(0, Units.GRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getSaturatedFat());

		return toReturn;

	}

	@Override
	public Amount getTransFat() {

		Amount toReturn = new Amount(0, Units.GRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getTransFat());

		return toReturn;

	}

	@Override
	public Amount getCholesterol() {

		Amount toReturn = new Amount(0, Units.MILLIGRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getCholesterol());

		return toReturn;

	}

	@Override
	public Amount getSodium() {

		Amount toReturn = new Amount(0, Units.MILLIGRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getSodium());

		return toReturn;
	}

	@Override
	public Amount getCarbohydrates() {

		Amount toReturn = new Amount(0, Units.GRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getCarbohydrates());

		return toReturn;

	}

	@Override
	public Amount getDietaryFiber() {

		Amount toReturn = new Amount(0, Units.GRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getDietaryFiber());

		return toReturn;

	}

	@Override
	public Amount getSugar() {

		Amount toReturn = new Amount(0, Units.GRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getSugar());

		return toReturn;

	}

	@Override
	public Amount getProtein() {

		Amount toReturn = new Amount(0, Units.GRAM);

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn.add(mealComponent.getProtein());

		return toReturn;

	}

	@Override
	public double getVitaminA() {

		double toReturn = 0;

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn += mealComponent.getVitaminA();

		return toReturn;

	}

	@Override
	public double getVitaminC() {

		double toReturn = 0;

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn += mealComponent.getVitaminC();

		return toReturn;

	}

	@Override
	public double getCalcium() {

		double toReturn = 0;

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn += mealComponent.getCalcium();

		return toReturn;

	}

	@Override
	public double getIron() {

		double toReturn = 0;

		if (this.mealComponents == null)
			return toReturn;

		for (MealComponent mealComponent : this.mealComponents)
			toReturn += mealComponent.getIron();

		return toReturn;

	}

	public String getDirections() {

		return this.directions;

	}

	// SETTERS

	public void setDirections(String text) {

		this.directions = text;

	}

	public void setID(int mealID) {
		this.mealID = mealID;
	}

	public void setName(String mealName) {
		this.name = mealName;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
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

	// METHODS

	public void addTag(String tag) {

		if (this.getCategories() == null)
			this.setCategories(new ArrayList<>());

		this.getCategories().add(tag.toLowerCase());

	}

	public void removeTag(String tag) {

		this.getCategories().remove(tag.toLowerCase());

	}

	// METHODS

	public void addMealComponent(MealComponent toAdd) {

		if (this.mealComponents == null)
			this.mealComponents = new ArrayList<>();

		this.mealComponents.add(toAdd);

	}

	public void removeMealComponent(MealComponent edibleObject) {
		this.mealComponents.remove(edibleObject);
	}

	public void addMealComponents(ArrayList<MealComponent> mealComponentArray) {

		if (this.mealComponents == null)
			this.mealComponents = new ArrayList<>();

		for (MealComponent toAdd : mealComponentArray)
			this.addMealComponent(toAdd);

	}

	@Override
	public String toString() {

		String toReturn;

		toReturn = "(MEAL) \"" + this.name + "\" - last edit: " + this.lastEdit + "\n[";

		if (this.mealComponents != null)
			for (int i = 0; i < this.mealComponents.size(); i++)
				toReturn = toReturn + this.mealComponents.get(i).getAmount() + " "
						+ this.mealComponents.get(i).getName() + ", ";

		toReturn = toReturn + "]\n" + "\nCalories: " + this.getCalories() + "\nTotal Fat: " + this.getTotalFat()
				+ "\n\tSaturated Fat: " + this.getSaturatedFat() + "\n\tTrans Fat: " + this.getTransFat()
				+ "\nCholesterol: " + this.getCholesterol() + "\nSodium: " + this.getSodium() + "\nCarbohydrates: "
				+ this.getCarbohydrates() + "\n\tDietary Fiber: " + this.getDietaryFiber() + "\n\tSugar: "
				+ this.getSugar() + "\nProtein: " + this.getProtein() + "\nVitamin A: " + this.getVitaminA()
				+ "\nVitamin C: " + this.getVitaminC() + "\nCalcium: " + this.getCalcium() + "\nIron: "
				+ this.getIron();

		return toReturn;

	}

	@Override
	public int compareTo(Meal meal) {
		return this.getName().compareToIgnoreCase(meal.getName());
		
	}

}