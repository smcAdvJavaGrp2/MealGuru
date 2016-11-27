package gui;

import java.util.ArrayList;

import edible.DailyIntake;
import edible.Edible;
import gui.smartnode.DailyIntakeLabel;
import gui.smartnode.EdibleLabel;

public class EdibleLableController {

	static ArrayList<EdibleLabel> edibleLabels;

	public static void addEdibleLabel(EdibleLabel edibleLabel) {

		if (EdibleLableController.edibleLabels == null)
			EdibleLableController.edibleLabels = new ArrayList<>();

		EdibleLableController.edibleLabels.add(edibleLabel);

	}

	public static void renderEdibleLabels(Edible edible) {

		for (EdibleLabel edibleLabel : EdibleLableController.edibleLabels)
			if (edible == edibleLabel.getEdibleObject())
				edibleLabel.render();

	}

	
	static ArrayList<DailyIntakeLabel> dailyIntakeLabels;

	public static void addDailyIntakeLabel(DailyIntakeLabel dailyIntakeLabel) {

		if (EdibleLableController.dailyIntakeLabels == null)
			EdibleLableController.dailyIntakeLabels = new ArrayList<>();

		EdibleLableController.dailyIntakeLabels.add(dailyIntakeLabel);

	}

	public static void renderEdibleLabels(DailyIntake dailyIntake) {

		for (DailyIntakeLabel dailyIntakeLabel : EdibleLableController.dailyIntakeLabels)
			if (dailyIntake == dailyIntakeLabel.getDailyIntake())
				dailyIntakeLabel.render();

	}
	
	
}
