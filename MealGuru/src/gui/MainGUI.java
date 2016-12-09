package gui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import data.mealguru.DailyIntakeDA;
import edible.DailyIntake;
import edible.Meal;
import gui.smartnode.DailyIntakeLabel;
import gui.smartnode.EdibleLabel;
import gui.smartnode.UserLabel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import utility.ResourceManager;

public class MainGUI extends StackPane {

	private UserLabel userLabel;

	static AnchorPane anchorPane;
	BorderPane borderPane;

	HBox dailyIntakeHBox;

	ArrayList<DailyIntake> dailyIntakes;

	Button future;
	Button past;

	Guru guru;
	String[] tips = { "Record your meals here", "Start at today's date", "Press Add Meal",
		"Press Remove All Meals to start fresh."};
	public MainGUI() {

		this(new Date());

	}

	public MainGUI(Date date) {

		Screen screen = Screen.getPrimary();
		Rectangle2D sbounds = screen.getBounds();

		double x = (sbounds.getMaxX() / 12);
		double y = (sbounds.getMaxY() / 5);
		
		// Create Guru and set its x, y position
		this.guru = new Guru(x, y);

		// started with simple animation, I'm not sure about over head yet
		// To do: switching the image or more complicated animations
		this.guru.startAnimation();
		// Return a random String from tip array
		this.guru.setScript(tips);
		this.guru.enablePath();
		if (PrimaryWindow.getCenterDate() == null)
			PrimaryWindow.setCenterDate(new Date());

		// LOAD USER STYLESHEET

		String styleSheetsExtension;
		if ((styleSheetsExtension = ResourceManager
				.getUserCSS(PrimaryWindow.getActiveUser().getCustomCSSExtension())) != null) {
			PrimaryWindow.getVisibleScene().getStylesheets().clear();
			PrimaryWindow.getVisibleScene().getStylesheets().add(styleSheetsExtension);
		} else
			PrimaryWindow.getVisibleScene().getStylesheets().add(ResourceManager.getCSS("style.css"));

		// ANCHORPANE

		anchorPane = new AnchorPane();
		anchorPane.setMouseTransparent(true);

		this.borderPane = new BorderPane();

		this.userLabel = new UserLabel();
		HBox topHBox = new HBox(this.userLabel);
		topHBox.setAlignment(Pos.TOP_RIGHT);
		topHBox.setPadding(new Insets(0, 0, 0, 0));
		this.borderPane.setTop(topHBox);

		ImageView leftImage = new ImageView(ResourceManager.getResourceImage("left.png"));
		leftImage.setPreserveRatio(true);
		leftImage.setFitHeight(50);
		VBox left = new VBox(leftImage);
		left.setAlignment(Pos.CENTER);
		left.setOnMouseEntered(e -> {
			left.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
		});
		left.setOnMouseExited(e -> {
			left.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0);");
		});
		left.setOnMouseClicked(e -> {
			this.past();
		});
		this.borderPane.setLeft(left);

		// CENTER

		this.dailyIntakeHBox = new HBox(10);

		this.dailyIntakeHBox.setAlignment(Pos.CENTER);

		this.borderPane.setCenter(this.dailyIntakeHBox);

		this.initializeDailyIntakeLabels();

		// RIGHT

		ImageView rightImage = new ImageView(ResourceManager.getResourceImage("right.png"));
		rightImage.setPreserveRatio(true);
		rightImage.setFitHeight(50);
		rightImage.setStyle("-fx-background-color:transparent;");

		VBox right = new VBox(rightImage);
		right.setAlignment(Pos.CENTER);
		right.setOnMouseEntered(e -> {
			right.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
		});
		right.setOnMouseExited(e -> {
			right.setStyle("-fx-background-color: rgba(0, 0, 0, 0.0);");
		});
		right.setOnMouseClicked(e -> {
			this.future();
		});
		
		
		this.borderPane.setRight(right);

		// BOTTOM

		this.getChildren().addAll(this.borderPane, anchorPane);

		this.initializeDailyIntakeLabels();

		// Add guru to this borderPane
		this.borderPane.getChildren().add(this.guru);
	}

	public void dragFinished(double x, double y, EdibleLabel edibleLabel) {

		anchorPane.setMouseTransparent(true);

		for (Node node : this.dailyIntakeHBox.getChildren())

			if (node.getBoundsInParent().contains(x, y)) {

				node.setStyle("");

				DailyIntakeDA dailyIntakeDA = new DailyIntakeDA();

				((DailyIntakeLabel) node).addMeal((Meal) edibleLabel.getEdibleObject());

				this.setDragFunctionality(((DailyIntakeLabel) node));

				dailyIntakeDA.saveMealToDay((Meal) edibleLabel.getEdibleObject(),
						((DailyIntakeLabel) node).getDailyIntake().getDate());

				break;

			}

	}

	public void future() {

		PrimaryWindow.getCenterDate().setTime(PrimaryWindow.getCenterDate().getTime() + (1 * 24 * 60 * 60 * 1000));

		Date dateToDisplay = new Date();
		dateToDisplay.setTime(PrimaryWindow.getCenterDate().getTime() + (3 * 24 * 60 * 60 * 1000));

		this.dailyIntakeHBox.getChildren().remove(0);
		this.dailyIntakeHBox.getChildren()
				.add(new DailyIntakeLabel(new DailyIntakeDA().getDailyIntakeByDay(dateToDisplay),this.guru));

		if (this.dailyIntakeHBox.getChildren().get(6).getClass() == DailyIntakeLabel.class)
			this.setDragFunctionality((DailyIntakeLabel) this.dailyIntakeHBox.getChildren().get(6));

	}

	public void past() {

		PrimaryWindow.getCenterDate().setTime(PrimaryWindow.getCenterDate().getTime() - (1 * 24 * 60 * 60 * 1000));

		Date dateToDisplay = new Date();
		dateToDisplay.setTime(PrimaryWindow.getCenterDate().getTime() - (3 * 24 * 60 * 60 * 1000));

		this.dailyIntakeHBox.getChildren().remove(6);
		this.dailyIntakeHBox.getChildren().add(0,
				new DailyIntakeLabel(new DailyIntakeDA().getDailyIntakeByDay(dateToDisplay), this.guru));

		if (this.dailyIntakeHBox.getChildren().get(0).getClass() == DailyIntakeLabel.class)
			this.setDragFunctionality((DailyIntakeLabel) this.dailyIntakeHBox.getChildren().get(0));

	}

	public void setDragFunctionality(DailyIntakeLabel dailyIntakeLabel) {

		for (EdibleLabel label : dailyIntakeLabel.getEdibleLabels()) {
			label.setOnMouseEntered(e -> {
				label.setCursor(Cursor.HAND);
				label.setStyle("-fx-border-style: none;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,255,0.8), 10, 0, 0, 0);"
						+ "-fx-background-radius: 5;" + "-fx-background-color: white;" + "-fx-font-size: 10;"
						+ "-fx-font-family: sans-serif;");

			});
			
			

			label.setOnMousePressed(e -> {
				this.guru.setWhisperMessage(tips[new Random().nextInt(tips.length)]);				
				if (e.getButton() == MouseButton.PRIMARY) {

					new DailyIntakeDA().deleteMealFromDay((Meal) label.getEdibleObject(),
							dailyIntakeLabel.getDailyIntake().getDate());

					dailyIntakeLabel.removeMeal((Meal) label.getEdibleObject());
					anchorPane.getChildren().add(label);

					label.setLayoutX(e.getSceneX() - (label.getWidth() / 2));
					label.setLayoutY(e.getSceneY() - (label.getHeight() / 2));

					label.setCursor(Cursor.CLOSED_HAND);

					this.setDragFunctionality(dailyIntakeLabel);

				}

			});

			label.setOnMouseDragged(e -> {

				if (e.getButton() == MouseButton.PRIMARY) {

					label.setLayoutX(e.getSceneX() - (label.getWidth() / 2));
					label.setLayoutY(e.getSceneY() - (label.getHeight() / 2));

					for (Node node : this.dailyIntakeHBox.getChildren()) {

						node.setStyle("");

						if (node.getBoundsInParent().contains(e.getSceneX() - (label.getWidth() / 2),
								e.getSceneY() - (label.getHeight() / 2)))
							node.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");

					}

				}

			});

			label.setOnMouseReleased(e -> {

				if (e.getButton() == MouseButton.PRIMARY) {

					label.setCursor(Cursor.HAND);

					anchorPane.getChildren().clear();

					this.dragFinished(e.getSceneX() - (label.getWidth() / 2), e.getSceneY() - (label.getHeight() / 2),
							label);

				}

			});

		}

	}

	public void initializeDailyIntakeLabels() {

		DailyIntakeDA dailyIntakeDA = new DailyIntakeDA();

		Date dateToDisplay = new Date();
		dateToDisplay.setTime(PrimaryWindow.getCenterDate().getTime() - (3 * 24 * 60 * 60 * 1000));

		MainGUI.this.dailyIntakes = new ArrayList<>();

		for (int i = 0; i < 7; i++) {

			DailyIntake dailyIntake = dailyIntakeDA.getDailyIntakeByDay(dateToDisplay);

			if (dailyIntake != null)
				MainGUI.this.dailyIntakes.add(dailyIntake);
			else
				MainGUI.this.dailyIntakes.add(new DailyIntake(dateToDisplay));

			dateToDisplay.setTime(dateToDisplay.getTime() + (1 * 24 * 60 * 60 * 1000));

		}

		if (MainGUI.this.dailyIntakeHBox.getChildren().size() > 0)
			MainGUI.this.dailyIntakeHBox.getChildren().clear();

		for (int i = 0; i < MainGUI.this.dailyIntakes.size(); i++)
			MainGUI.this.dailyIntakeHBox.getChildren().add(new DailyIntakeLabel(MainGUI.this.dailyIntakes.get(i), guru));

		for (int i = 0; i < MainGUI.this.dailyIntakeHBox.getChildren().size(); i++) {

			Node node = MainGUI.this.dailyIntakeHBox.getChildren().get(i);

			if (node.getClass() == DailyIntakeLabel.class)
				MainGUI.this.setDragFunctionality((DailyIntakeLabel) node);

		}

	}

}
