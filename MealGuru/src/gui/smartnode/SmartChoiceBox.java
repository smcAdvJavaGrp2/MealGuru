package gui.smartnode;

import javafx.scene.control.ChoiceBox;
import utility.UnitClassification;
import utility.Units;

public class SmartChoiceBox extends ChoiceBox<Units> {

	public SmartChoiceBox(UnitClassification unitClassifications) {

		this.setAvailableMeasurements(unitClassifications);

	}

	public SmartChoiceBox(UnitClassification... unitClassifications) {

		this.setAvailableMeasurements(unitClassifications);

	}

	public void setAvailableMeasurements(UnitClassification... unitClassifications) {

		for (UnitClassification unitClassification : unitClassifications)
			this.addAvailableMeasurements(unitClassification);

	}

	public void addAvailableMeasurements(UnitClassification unitClassification) {

		if (unitClassification == UnitClassification.SERVING)
			this.getItems().add(Units.SERVING);
		else if (unitClassification == UnitClassification.UNIT)
			this.getItems().add(Units.UNIT);
		else if (unitClassification == UnitClassification.CONTAINER)
			this.getItems().add(Units.CONTAINER);
		else if (unitClassification == UnitClassification.WEIGHTED)
			this.getItems().addAll(Units.MILLIGRAM, Units.GRAM, Units.OUNCE, Units.POUND);
		else if (unitClassification == UnitClassification.LIQUID_VOLUME)
			this.getItems().addAll(Units.MILLILITER, Units.LITER, Units.TEA_SPOON, Units.TABLE_SPOON, Units.CUP,
					Units.FLUID_OUNCE, Units.PINT, Units.QUART, Units.GALLON);

		this.setValue(Units.SERVING);

	}

	public void clear(UnitClassification... unitClassifications) {

		for (UnitClassification unitClassification : unitClassifications)
			if (unitClassification == UnitClassification.SERVING)
				this.getItems().remove(Units.SERVING);
			else if (unitClassification == UnitClassification.UNIT)
				this.getItems().remove(Units.UNIT);
			else if (unitClassification == UnitClassification.CONTAINER)
				this.getItems().removeAll(Units.CONTAINER);
			else if (unitClassification == UnitClassification.WEIGHTED)
				this.getItems().removeAll(Units.MILLIGRAM, Units.GRAM, Units.OUNCE, Units.POUND);
			else if (unitClassification == UnitClassification.LIQUID_VOLUME)
				this.getItems().removeAll(Units.MILLILITER, Units.LITER, Units.TEA_SPOON, Units.TABLE_SPOON, Units.CUP,
						Units.FLUID_OUNCE, Units.PINT, Units.QUART, Units.GALLON);

	}

	public void clearAll() {

		this.getItems().removeAll(

				Units.SERVING,

				Units.UNIT,

				Units.CONTAINER,

				Units.MILLIGRAM, Units.GRAM, Units.OUNCE, Units.POUND,

				Units.MILLILITER, Units.LITER, Units.TEA_SPOON, Units.TABLE_SPOON, Units.CUP, Units.FLUID_OUNCE,
				Units.PINT, Units.QUART, Units.GALLON

		);

	}

}