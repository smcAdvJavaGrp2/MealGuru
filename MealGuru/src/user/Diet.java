package user;

import java.util.ArrayList;

import utility.Amount;
import utility.Units;

public class Diet {

	private ArrayList<String>categoryPreference;

	int caloriesLimit;
	Amount totalFatLimit, saturatedFatLimit, transFatLimit;
	Amount cholesterolLimit;
	Amount sodiumLimit;
	Amount carbohydratesLimit, dietaryFiberLimit, sugarLimit;
	Amount proteinLimit;
	double vitaminALimit, vitaminCLimit;
	double calciumLimit, ironLimit;

	public Diet() {
		this.setCaloriesLimit(0);
		this.setTotalFatLimit(new Amount(0, Units.GRAM));
		this.setSaturatedFatLimit(new Amount(0, Units.GRAM));
		this.setTransFatLimit(new Amount(0, Units.GRAM));
		this.setCholesterolLimit(new Amount(0, Units.MILLIGRAM));
		this.setSodiumLimit(new Amount(0, Units.MILLIGRAM));
		this.setCarbohydratesLimit(new Amount(0, Units.GRAM));
		this.setDietaryFiberLimit(new Amount(0, Units.GRAM));
		this.setSugarLimit(new Amount(0, Units.GRAM));
		this.setProteinLimit(new Amount(0, Units.GRAM));
		this.setVitaminALimit(0);
		this.setVitaminCLimit(0);
		this.setCalciumLimit(0);
		this.setIronLimit(0);
	}

	public Diet(int caloriesLimit, Amount totalFatLimit, Amount saturatedFatLimit, Amount transFatLimit,
			Amount cholesterolLimit, Amount sodiumLimit, Amount carbohydratesLimit, Amount dietaryFiberLimit,
			Amount sugarLimit, Amount proteinLimit, double vitaminALimit, double vitaminCLimit, double calciumLimit,
			double ironLimit, String...CategoryPreference) {

		this.setCaloriesLimit(caloriesLimit);
		this.setTotalFatLimit(totalFatLimit);
		this.setSaturatedFatLimit(saturatedFatLimit);
		this.setTransFatLimit(transFatLimit);
		this.setCholesterolLimit(cholesterolLimit);
		this.setSodiumLimit(sodiumLimit);
		this.setCarbohydratesLimit(carbohydratesLimit);
		this.setDietaryFiberLimit(dietaryFiberLimit);
		this.setSugarLimit(sugarLimit);
		this.setProteinLimit(proteinLimit);
		this.setVitaminALimit(vitaminALimit);
		this.setVitaminCLimit(vitaminCLimit);
		this.setCalciumLimit(calciumLimit);
		this.setIronLimit(ironLimit);

		for (String categoryPreference : CategoryPreference)
			this.addCategoryPreference(categoryPreference);
	}

	public ArrayList<String> getCategoryPreference() {
		return this.categoryPreference;
	}

	public void setCategoryPreference(ArrayList<String> categoryPreference) {
		this.categoryPreference = categoryPreference;
	}
	
	public void setCategoryPreference(String...CategoryPreference){
		for (String categoryPreference : CategoryPreference)
			this.addCategoryPreference(categoryPreference);
	}

	public int getCaloriesLimit() {
		return this.caloriesLimit;
	}

	public void setCaloriesLimit(int caloriesLimit) {
		this.caloriesLimit = caloriesLimit;
	}

	public Amount getTotalFatLimit() {
		return this.totalFatLimit;
	}

	public void setTotalFatLimit(Amount totalFatLimit) {
		this.totalFatLimit = totalFatLimit;
	}

	public Amount getSaturatedFatLimit() {
		return this.saturatedFatLimit;
	}

	public void setSaturatedFatLimit(Amount saturatedFatLimit) {
		this.saturatedFatLimit = saturatedFatLimit;
	}

	public Amount getTransFatLimit() {
		return this.transFatLimit;
	}

	public void setTransFatLimit(Amount transFatLimit) {
		this.transFatLimit = transFatLimit;
	}

	public Amount getCholesterolLimit() {
		return this.cholesterolLimit;
	}

	public void setCholesterolLimit(Amount cholesterolLimit) {
		this.cholesterolLimit = cholesterolLimit;
	}

	public Amount getSodiumLimit() {
		return this.sodiumLimit;
	}

	public void setSodiumLimit(Amount sodiumLimit) {
		this.sodiumLimit = sodiumLimit;
	}

	public Amount getCarbohydratesLimit() {
		return this.carbohydratesLimit;
	}

	public void setCarbohydratesLimit(Amount carbohydratesLimit) {
		this.carbohydratesLimit = carbohydratesLimit;
	}

	public Amount getDietaryFiberLimit() {
		return this.dietaryFiberLimit;
	}

	public void setDietaryFiberLimit(Amount dietaryFiberLimit) {
		this.dietaryFiberLimit = dietaryFiberLimit;
	}

	public Amount getSugarLimit() {
		return this.sugarLimit;
	}

	public void setSugarLimit(Amount sugarLimit) {
		this.sugarLimit = sugarLimit;
	}

	public Amount getProteinLimit() {
		return this.proteinLimit;
	}

	public void setProteinLimit(Amount proteinLimit) {
		this.proteinLimit = proteinLimit;
	}

	public double getVitaminALimit() {
		return this.vitaminALimit;
	}

	public void setVitaminALimit(double vitaminALimit) {
		this.vitaminALimit = vitaminALimit;
	}

	public double getVitaminCLimit() {
		return this.vitaminCLimit;
	}

	public void setVitaminCLimit(double vitaminCLimit) {
		this.vitaminCLimit = vitaminCLimit;
	}

	public double getCalciumLimit() {
		return this.calciumLimit;
	}

	public void setCalciumLimit(double calciumLimit) {
		this.calciumLimit = calciumLimit;
	}

	public double getIronLimit() {
		return this.ironLimit;
	}

	public void setIronLimit(double ironLimit) {
		this.ironLimit = ironLimit;
	}

	// Methods
	
	public void addCategoryPreference(String tag) {
		if (this.getCategoryPreference() == null)
			this.setCategoryPreference(new ArrayList<>());

		this.getCategoryPreference().add(tag.toLowerCase());
	}

	public void removeCategoryLimits(String tag) {
		this.getCategoryPreference().remove(tag.toLowerCase());
	}
	
	//

	public boolean isWithinCaloriesLimit(int caloriesLimit) {
		return this.caloriesLimit == caloriesLimit;
	}

	public boolean isWithinTotalFatLimit(Amount totalFatLimit) {
		return this.totalFatLimit == totalFatLimit;
	}

	public boolean isWithinSaturatedFatLimit(Amount saturatedFatLimit) {
		return this.saturatedFatLimit == saturatedFatLimit;
	}

	public boolean isWithinTransFatLimit(Amount transFatLimit) {
		return this.transFatLimit == transFatLimit;
	}

	public boolean isWithinCholesterolLimit(Amount cholesterolLimit) {
		return this.cholesterolLimit == cholesterolLimit;
	}

	public boolean isWithinSodiumLimit(Amount sodiumLimit) {
		return this.sodiumLimit == sodiumLimit;
	}

	public boolean isWithinCarbohydratesLimit(Amount carbohydratesLimit) {
		return this.carbohydratesLimit == carbohydratesLimit;
	}

	public boolean isWithinDietaryFiberLimit(Amount dietaryFiberLimit) {
		return this.dietaryFiberLimit == dietaryFiberLimit;
	}

	public boolean isWithinSugarLimit(Amount sugarLimit) {
		return this.sugarLimit == sugarLimit;
	}

	public boolean isWithinProteinLimit(Amount proteinLimit) {
		return this.proteinLimit == proteinLimit;
	}

	public boolean isWithinVitaminALimit(double vitaminALimit) {
		return this.vitaminALimit == vitaminALimit;
	}

	public boolean isWithinCalciumLimit(double calciumLimit) {
		return this.calciumLimit == calciumLimit;
	}

	public boolean isWithinIronLimit(double ironLimit) {
		return this.ironLimit == ironLimit;
	}

	public boolean isWithinCategoryLimits(String tag) {
		return this.categoryPreference.contains(tag);
	}

	public boolean isWithinCategoryLimits(ArrayList<String> tags) {

		for (String tag : tags)
			if (this.isWithinCategoryLimits(tag))
				return true;

		return false;
	}
	
	

	public ArrayList<String> sameInCategoryLimits(ArrayList<String> tags) {
		if (this.isWithinCategoryLimits(tags)) {
			ArrayList<String> r = new ArrayList<>();
			for (String tag : tags)
				if (this.categoryPreference.contains(tag))
					r.add(tag);
			return r;
		} else
			return null;
	}

	@Override
	public String toString() {
		String toReturn = "Category Limits: " + this.getCategoryPreference() + "\n" + "\nCalories Limit: "
				+ this.getCaloriesLimit() + "\nTotal Fat Limit: " + this.getTotalFatLimit()
				+ "\n\tSaturated Fat Limit: " + this.getSaturatedFatLimit() + "\n\tTrans Fat Limit: "
				+ this.getTransFatLimit() + "\nCholesterol Limit: " + this.getCholesterolLimit() + "\nSodium Limit: "
				+ this.getSodiumLimit() + "\nCarbohydrates Limit: " + this.getCarbohydratesLimit()
				+ "\n\tDietary Fiber Limit: " + this.getDietaryFiberLimit() + "\n\tSugar Limit: " + this.getSugarLimit()
				+ "\nProtein Limit: " + this.getProteinLimit() + "\nVitamin A Limit: " + this.getVitaminALimit()
				+ "\nVitamin C Limit: " + this.getVitaminCLimit() + "\nCalcium Limit: " + this.getCalciumLimit()
				+ "\nIron Limit: " + this.getIronLimit();

		return toReturn;
	}
}
