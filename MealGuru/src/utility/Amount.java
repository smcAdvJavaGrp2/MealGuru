package utility;

public class Amount {

	private double measure;
	private Units unitMeasurement;

	/**
	 * Constructor used for creating a measurement given a double and a
	 * {@link Units} enum (e.g. Units.GRAM).
	 *
	 * @param measure
	 *            the parameter measure
	 * @param unitMeasurement
	 *            the parameter for unit clarification
	 */
	public Amount(double measure, Units unitMeasurement) {

		this.setUnits(unitMeasurement);
		this.measure = measure;

	}

	/**
	 * Constructor used for database and also simple creation of an Amount. This
	 * constructor is useful because all you need to enter into it is a
	 * {@link String} (e.g. "33 fl.oz", "44 g", "4 unit").
	 * <p>
	 * It handles separating the measurement and the {@link Units} by splitting
	 * the {@link String} then setting the class members: double measure, and
	 * {@link Units} unitMeasurement.
	 *
	 * @param amount
	 *            this is a {@link String} argument of what you want to convert
	 *            to an amount
	 */
	public Amount(String amount) {

		if ((amount == null) || !amount.contains(" "))
			return;

		String[] broken = { amount.substring(0, amount.indexOf(' ')), amount.substring(amount.indexOf(' ') + 1) };

		this.setMeasure(Double.parseDouble(broken[0]));

		if (broken[1].equalsIgnoreCase("serving"))
			this.setUnits(Units.SERVING);
		else if (broken[1].equalsIgnoreCase("unit"))
			this.setUnits(Units.UNIT);
		else if (broken[1].equalsIgnoreCase("container"))
			this.setUnits(Units.CONTAINER);
		else if (broken[1].equalsIgnoreCase("mg"))
			this.setUnits(Units.MILLIGRAM);
		else if (broken[1].equalsIgnoreCase("g"))
			this.setUnits(Units.GRAM);
		else if (broken[1].equalsIgnoreCase("oz"))
			this.setUnits(Units.OUNCE);
		else if (broken[1].equalsIgnoreCase("lbs"))
			this.setUnits(Units.POUND);
		else if (broken[1].equalsIgnoreCase("ml"))
			this.setUnits(Units.MILLIGRAM);
		else if (broken[1].equalsIgnoreCase("l"))
			this.setUnits(Units.LITER);
		else if (broken[1].equalsIgnoreCase("tsp"))
			this.setUnits(Units.TEA_SPOON);
		else if (broken[1].equalsIgnoreCase("tbsp"))
			this.setUnits(Units.TABLE_SPOON);
		else if (broken[1].equalsIgnoreCase("cup"))
			this.setUnits(Units.CUP);
		else if (broken[1].equalsIgnoreCase("fl. oz"))
			this.setUnits(Units.FLUID_OUNCE);
		else if (broken[1].equalsIgnoreCase("pt"))
			this.setUnits(Units.PINT);
		else if (broken[1].equalsIgnoreCase("qt"))
			this.setUnits(Units.QUART);
		else if (broken[1].equalsIgnoreCase("gal"))
			this.setUnits(Units.GALLON);
		else if (broken[1].equalsIgnoreCase("container"))
			this.setUnits(Units.CONTAINER);

	}

	/**
	 * Returns {@link Units} of this given class
	 *
	 * @return a {@link Units} enum.
	 */
	public Units getUnits() {
		return this.unitMeasurement;
	}

	/**
	 * Sets {@link Units} of this given class.
	 */
	public void setUnits(Units unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	/**
	 * Get the double measure of this Amount.
	 *
	 * @return double amount.
	 */
	public double getMeasure() {

		return this.measure;

	}

	/**
	 * Setter to set the double measure of this {@link Object}.
	 *
	 * @param toSet
	 *            the double amount to set this {@link Object} to.
	 */
	public void setMeasure(double toSet) {

		this.measure = toSet;

	}

	/**
	 * This method's purpose is to add an {@link Amount} to this Amount. It
	 * takes into consideration the {@link Units}.
	 * <p>
	 * This method cannot convert between liquid {@link UnitClassification}'s,
	 * solids, and utilities.
	 *
	 * @param amountToAdd
	 *            This method takes an {@link Amount} to add. The result is kept
	 *            in the {@link Units} that is being added to.
	 */
	public void add(Amount amountToAdd) {

		if (amountToAdd.getUnits() != this.getUnits()) {
			if (this.getUnits().getUnitClassification() == amountToAdd.getUnits().getUnitClassification()) {
				amountToAdd.convert(this.getUnits());
				this.measure += amountToAdd.measure;
			} else
				System.out.println("Cannot add Amounts: " + this.getUnits().getUnitClassification() + " -!-> "
						+ amountToAdd.getUnits().getUnitClassification());
		} else
			this.measure += amountToAdd.measure;

	}

	/**
	 * This method is responsible for all conversions of the {@link Amount}
	 * class. This method will take the measure and the {@link Units} and
	 * determine whether a conversion is possible.
	 * <p>
	 * Conversions are IMPOSSIBLE between Amounts with different
	 * {@link UnitClassification}'s. <i>Units.GRAM -!- Units.FLUID_OUNCE</i>
	 * <i>Units.UNIT -!- Units.CUP</i>
	 *
	 * @param toThisUnit
	 *            this is the {@link Units} to convert to.
	 */
	public void convert(Units toThisUnit) {

		// US
		final double TEASPOON_TO_CUP = 0.02083333333;
		final double TABLESPOON_TO_CUP = 0.0625;
		final double FLUID_OUNCE_TO_CUP = .125;
		final double PINT_TO_CUP = 2;
		final double QUART_TO_CUP = 4;
		final double GALLONS_TO_CUP = 16;

		// METRIC
		final double MILLILITER_TO_CUP = 0.0041666666666667;
		final double LITER_TO_CUP = 4.2;

		// WEIGHT
		final double MILLIGRAM_TO_GRAM = 0.001;
		final double OUNCE_TO_GRAM = 28.3495;
		final double POUND_TO_GRAM = 453.592;

		if (this.getUnits().getClassification() == toThisUnit.getClassification()) {

			if (this.getUnits() == toThisUnit)
				return;

			switch (this.getUnits()) {

			case UNIT:
				break;
			case MILLIGRAM:
				this.measure *= MILLIGRAM_TO_GRAM;
				switch (toThisUnit) {
				case GRAM:
					this.setUnits(Units.GRAM);
					break;
				case OUNCE:
					this.measure /= OUNCE_TO_GRAM;
					this.setUnits(Units.OUNCE);
					break;
				case POUND:
					this.measure /= POUND_TO_GRAM;
					this.setUnits(Units.POUND);
					break;
				case MILLIGRAM:
					this.measure /= MILLIGRAM_TO_GRAM;
					this.setUnits(Units.MILLIGRAM);
					break;
				}
				break;
			case GRAM:
				switch (toThisUnit) {
				case GRAM:
					this.setUnits(Units.GRAM);
					break;
				case OUNCE:
					this.measure /= OUNCE_TO_GRAM;
					this.setUnits(Units.OUNCE);
					break;
				case POUND:
					this.measure /= POUND_TO_GRAM;
					this.setUnits(Units.POUND);
					break;
				case MILLIGRAM:
					this.measure /= MILLIGRAM_TO_GRAM;
					this.setUnits(Units.MILLIGRAM);
					break;
				}
				break;
			case OUNCE:
				this.measure *= OUNCE_TO_GRAM;
				switch (toThisUnit) {
				case GRAM:
					this.setUnits(Units.GRAM);
					break;
				case OUNCE:
					this.measure /= OUNCE_TO_GRAM;
					this.setUnits(Units.OUNCE);
					break;
				case POUND:
					this.measure /= POUND_TO_GRAM;
					this.setUnits(Units.POUND);
					break;
				case MILLIGRAM:
					this.measure /= MILLIGRAM_TO_GRAM;
					this.setUnits(Units.MILLIGRAM);
					break;
				}
				break;
			case POUND:
				this.measure *= POUND_TO_GRAM;
				switch (toThisUnit) {
				case GRAM:
					this.setUnits(Units.GRAM);
					break;
				case OUNCE:
					this.measure /= OUNCE_TO_GRAM;
					this.setUnits(Units.OUNCE);
					break;
				case POUND:
					this.measure /= POUND_TO_GRAM;
					this.setUnits(Units.POUND);
					break;
				case MILLIGRAM:
					this.measure /= MILLIGRAM_TO_GRAM;
					this.setUnits(Units.MILLIGRAM);
					break;
				}
				break;

			case TEA_SPOON:
				this.measure *= TEASPOON_TO_CUP;
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;
			case TABLE_SPOON:
				this.measure *= TABLESPOON_TO_CUP;
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;
			case CUP:
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;
			case FLUID_OUNCE:
				this.measure *= FLUID_OUNCE_TO_CUP;
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;
			case PINT:
				this.measure *= PINT_TO_CUP;
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;
			case QUART:
				this.measure *= QUART_TO_CUP;
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;
			case GALLON:
				this.measure *= GALLONS_TO_CUP;
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;
			case MILLILITER:
				this.measure *= MILLILITER_TO_CUP;
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;
			case LITER:
				this.measure *= LITER_TO_CUP;
				switch (toThisUnit) {
				case TEA_SPOON:
					this.measure /= TEASPOON_TO_CUP;
					this.setUnits(Units.TEA_SPOON);
					break;
				case TABLE_SPOON:
					this.measure /= TABLESPOON_TO_CUP;
					this.setUnits(Units.TABLE_SPOON);
					break;
				case CUP:
					this.setUnits(Units.CUP);
					break;
				case FLUID_OUNCE:
					this.measure /= FLUID_OUNCE_TO_CUP;
					this.setUnits(Units.FLUID_OUNCE);
					break;
				case PINT:
					this.measure /= PINT_TO_CUP;
					this.setUnits(Units.PINT);
					break;
				case QUART:
					this.measure /= QUART_TO_CUP;
					this.setUnits(Units.QUART);
					break;
				case GALLON:
					this.measure /= GALLONS_TO_CUP;
					this.setUnits(Units.GALLON);
					break;
				case MILLILITER:
					this.measure /= MILLILITER_TO_CUP;
					this.setUnits(Units.MILLILITER);
					break;
				case LITER:
					this.measure /= LITER_TO_CUP;
					this.setUnits(Units.LITER);
					break;
				}
				break;

			}

		} else
			System.out.println("Cannot convert " + this.getUnits() + " to " + toThisUnit);

	}

	@Override
	public String toString() {

		if (this.getUnits() != null)
			return this.measure + " " + this.getUnits().getAbbreviation();

		return "NO AMOUNT";

	}

}
