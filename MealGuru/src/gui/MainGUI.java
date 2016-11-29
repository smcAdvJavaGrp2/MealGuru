package gui;

import java.util.ArrayList;
import java.util.Date;

import data.mealguru.DailyIntakeDA;
import edible.DailyIntake;
import edible.Meal;
import gui.smartnode.DailyIntakeLabel;
import gui.smartnode.EdibleLabel;
import gui.smartnode.UserLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utility.ResourceManager;

public class MainGUI extends StackPane {

	private UserLabel userLabel;

	static AnchorPane anchorPane;
	BorderPane borderPane;

	Date centerDay;

	HBox dailyIntakeHBox;

	ArrayList<DailyIntake> dailyIntakes;
	ArrayList<DailyIntakeLabel> dailyIntakeLabels;

	Button future;
	Button past;

	public MainGUI() {

		this.dailyIntakes = new ArrayList<>();
		this.dailyIntakeLabels = new ArrayList<>();

		anchorPane = new AnchorPane();
		anchorPane.setMouseTransparent(true);

		this.borderPane = new BorderPane();

		this.userLabel = new UserLabel();
		HBox topHBox = new HBox(this.userLabel);
		topHBox.setAlignment(Pos.TOP_RIGHT);
		topHBox.setPadding(new Insets(10, -40, 0, 0));
		this.borderPane.setTop(topHBox);

		this.centerDay = new Date();

		this.past = new Button();
		ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
		leftImage.setPreserveRatio(true);
		leftImage.setFitHeight(50);
		this.past.setGraphic(leftImage);
		this.past.setStyle("-fx-background-color:transparent;");
		this.past.setOnAction(e -> {

			this.centerDay.setTime(this.centerDay.getTime() - (1 * 24 * 60 * 60 * 1000));

			this.refreshDailyIntakeLabels();

		});
		VBox left = new VBox(this.past);
		left.setAlignment(Pos.CENTER);
		this.borderPane.setLeft(left);

		this.dailyIntakeHBox = new HBox(10);

		this.refreshDailyIntakeLabels();

		this.dailyIntakeHBox.setAlignment(Pos.CENTER);

		this.borderPane.setCenter(this.dailyIntakeHBox);

		// RIGHT

		this.future = new Button();
		ImageView rightImage = new ImageView(ResourceManager.getResourceImage("right.png"));
		rightImage.setPreserveRatio(true);
		rightImage.setFitHeight(50);
		this.future.setGraphic(rightImage);
		this.future.setStyle("-fx-background-color:transparent;");
		this.future.setOnAction(e -> {

			this.centerDay.setTime(this.centerDay.getTime() + (1 * 24 * 60 * 60 * 1000));

			this.refreshDailyIntakeLabels();

		});

		VBox right = new VBox(this.future);
		right.setAlignment(Pos.CENTER);
		this.borderPane.setRight(right);

		// BOTTOM

		this.getChildren().addAll(this.borderPane, anchorPane);

	}

	public void dragFinished(double x, double y, EdibleLabel edibleLabel) {

		anchorPane.setMouseTransparent(true);

		for (Node node : this.dailyIntakeHBox.getChildren())

			if (node.getBoundsInParent().contains(x, y)) {

				DailyIntakeDA dailyIntakeDA = new DailyIntakeDA();

				dailyIntakeDA.saveMealToDay((Meal) edibleLabel.getEdibleObject(),
						((DailyIntakeLabel) node).getDailyIntake().getDate());

				break;

			}

		this.refreshDailyIntakeLabels();

	}

	public void refreshDailyIntakeLabels() {

		DailyIntakeDA dailyIntakeDA = new DailyIntakeDA();

		Date dateToDisplay = new Date();
		dateToDisplay.setTime(this.centerDay.getTime());

		dateToDisplay.setTime(dateToDisplay.getTime() - (3 * 24 * 60 * 60 * 1000));

		this.dailyIntakes = new ArrayList<>();

		for (int i = 0; i < 7; i++) {

			DailyIntake dailyIntake = dailyIntakeDA.getDailyIntakeByDay(dateToDisplay);

			if (dailyIntake != null)
				this.dailyIntakes.add(dailyIntake);
			else
				this.dailyIntakes.add(new DailyIntake(dateToDisplay));

			dateToDisplay.setTime(dateToDisplay.getTime() + (1 * 24 * 60 * 60 * 1000));

		}

		this.dailyIntakeHBox.getChildren().clear();

		for (int i = 0; i < this.dailyIntakes.size(); i++)

			this.dailyIntakeHBox.getChildren().add(new DailyIntakeLabel(this.dailyIntakes.get(i)));

		for (Node node : this.dailyIntakeHBox.getChildren())
			if (node.getClass() == DailyIntakeLabel.class) {

				DailyIntakeLabel dailyIntakeLabel = (DailyIntakeLabel) node;

				for (EdibleLabel label : dailyIntakeLabel.getEdibleLabels()) {

					label.setOnMouseEntered(e -> {

						label.setCursor(Cursor.HAND);

					});

					label.setOnMousePressed(e -> {

						dailyIntakeLabel.getChildren().remove(label);

						label.setLayoutX(e.getSceneX() - (label.getWidth() / 2));
						label.setLayoutY(e.getSceneY() - (label.getHeight() / 2));

						anchorPane.getChildren().add(label);

						label.setCursor(Cursor.CLOSED_HAND);

					});

					label.setOnMouseDragged(e -> {

						label.setLayoutX(e.getSceneX() - (label.getWidth() / 2));
						label.setLayoutY(e.getSceneY() - (label.getHeight() / 2));

					});

					label.setOnMouseReleased(e -> {

						dailyIntakeDA.deleteMealFromDay((Meal) label.getEdibleObject(),
								dailyIntakeLabel.getDailyIntake().getDate());

						label.setCursor(Cursor.HAND);

						anchorPane.getChildren().remove(label);
						this.dragFinished(e.getSceneX() - (label.getWidth() / 2),
								e.getSceneY() - (label.getHeight() / 2), label);

					});

				}

			}

	}

}