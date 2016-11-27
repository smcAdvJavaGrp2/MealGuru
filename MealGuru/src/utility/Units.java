package utility;

public enum Units {

	// 1st CLASSIFICATION

	SERVING(UnitClassification.SERVING, "serving"),

	UNIT(UnitClassification.UNIT, "unit"),

	CONTAINER(UnitClassification.CONTAINER, "container"),

	MILLIGRAM(UnitClassification.WEIGHTED, "mg"), GRAM(UnitClassification.WEIGHTED,
			"g"), OUNCE(UnitClassification.WEIGHTED, "oz"), POUND(UnitClassification.WEIGHTED, "lbs"),

	MILLILITER(UnitClassification.LIQUID_VOLUME, "ml"), LITER(UnitClassification.LIQUID_VOLUME, "l"),

	TEA_SPOON(UnitClassification.LIQUID_VOLUME, "tsp"), TABLE_SPOON(UnitClassification.LIQUID_VOLUME, "tbsp"), CUP(
			UnitClassification.LIQUID_VOLUME, "cup"), FLUID_OUNCE(UnitClassification.LIQUID_VOLUME,
					"fl.oz"), PINT(UnitClassification.LIQUID_VOLUME, "pt"), QUART(UnitClassification.LIQUID_VOLUME,
							"qt"), GALLON(UnitClassification.LIQUID_VOLUME, "gal");

	// 2nd CLASSIFICATION

	private UnitClassification classification;
	private String abbreviation;

	// CONSTRUCTORS

	Units(UnitClassification classification, String abbreviation) {

		this.setUnitClassification(classification);

		this.setAbbreviation(abbreviation);

	}

	// METHODS

	public UnitClassification getUnitClassification() {
		return this.getClassification();
	}

	private void setUnitClassification(UnitClassification classification) {
		this.classification = classification;
	}

	public void setAbbreviation(String unitDefined) {

		this.abbreviation = unitDefined;

	}

	@Override
	public String toString() {

		return this.getAbbreviation();

	}

	public UnitClassification getClassification() {
		return this.classification;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

}