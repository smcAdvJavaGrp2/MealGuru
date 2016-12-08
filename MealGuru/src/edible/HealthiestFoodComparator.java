package edible;

import java.util.Comparator;

public class HealthiestFoodComparator implements Comparator<Food>{
	/**
	 * Orders Food Objects from most healthy to most unhealthy Compares food
	 * according to simple heuristic, The food with the lower result is
	 * healthier by this arbitrary metric.
	 * 
	 */
	@Override
	public int compare(Food food1, Food food2) {
		double ratio;
		int t = 0;
		int o = 0;

		if (food1.getCalories() < food2.getCalories())
			ratio = food1.getCalories() / food2.getCalories();
		else
			ratio = 1 - food1.getCalories() / food2.getCalories();
		if (food1.getTotalFat().getMeasure() * ratio < food2.getTotalFat().getMeasure() * ratio)
			o = o + 4;
		else
			t = t + 4;

		if (food1.getSaturatedFat().getMeasure() * ratio < food2.getSaturatedFat().getMeasure() * ratio)
			o = o + 5;
		else
			t = t + 5;

		if (food1.getTransFat().getMeasure() * ratio < food2.getTransFat().getMeasure() * ratio)

			o = o + 5;
		else
			t = t + 5;

		if (food1.getCholesterol().getMeasure() * ratio < food2.getCholesterol().getMeasure() * ratio)

			o = o + 5;
		else
			t = t + 5;

		if (food1.getSodium().getMeasure() * ratio < food2.getSodium().getMeasure() * ratio)

			o = o + 4;
		else
			t = t + 4;

		if (food1.getCarbohydrates().getMeasure() * ratio < food2.getCarbohydrates().getMeasure() * ratio)

			o = o + 2;
		else
			t = t + 2;

		if (food1.getDietaryFiber().getMeasure() * ratio < food2.getDietaryFiber().getMeasure() * ratio)

			o = o - 4;
		else
			t = t - 4;

		if (food1.getSugar().getMeasure() * ratio < food2.getSugar().getMeasure() * ratio)

			o = o + 4;
		else
			t = t + 4;

		if (food1.getProtein().getMeasure() * ratio < food2.getProtein().getMeasure() * ratio)

			o = o - 4;
		else
			t = t - 4;

		if (food1.getVitaminA() * ratio < food2.getVitaminA() * ratio)

			o = o - 1;
		else
			t = t - 1;

		if (food1.getVitaminC() * ratio < food2.getVitaminC() * ratio)

			o = o - 1;
		else
			t = t - 1;

		if (food1.getCalcium() * ratio < food2.getCalcium() * ratio)

			o = o - 1;
		else
			t = t - 1;

		if (food1.getIron() * ratio < food2.getIron() * ratio)

			o = o - 1;
		else
			t = t - 1;

		return t > o ? +1 : t < o ? -1 : 0;
	}

}


