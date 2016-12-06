package gui;

import java.util.Date;

import gui.smartnode.DailyIntakeGraph;
import javafx.scene.layout.BorderPane;

public class DailyIntakeWeekSummary extends BorderPane {

	public DailyIntakeWeekSummary() {

		if (PrimaryWindow.getCenterDate() == null)
			PrimaryWindow.setCenterDate(new Date());

		DailyIntakeGraph dailyIntakeGraph = new DailyIntakeGraph();

		this.setCenter(dailyIntakeGraph);

	}

	public void redrawGraph() {

	}

}
