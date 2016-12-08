package gui;

import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
//import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import utility.ResourceManager;
import edible.Food;
import edible.HealthiestFoodComparator;

public class Guru extends AnchorPane {
	// main timeline
	private Timeline timeline;
	private TranslateTransition tt;
	private FadeTransition fade;
	private final ImageView imageView;
	private Button button;
	private Label bubble;
	private StringProperty message;
	private boolean isSpeaking;
	private double offSetX, offSetY;
	// Binary Search Tree is not better here (more memory efficient),
	// Set is more flexible for insertion/deletion/search/access
	private SortedSet<Food> foods = new TreeSet<Food>(new HealthiestFoodComparator());

	private String[] script;
	private String[] wiseSayings = { "60% of the time, it works every time.",
			"It takes considerable knowledge just to realize the extent of your own ignorance.",
			"If you live to be one hundred, you've got it made. Very few people die past that age.",
			"Parents are the last people on earth who ought to have children.",
			"An idea isn't responsible for the people who believe in it.",
			"Children are smarter than any of us. Know how I know that? I don't know one child with a full time job and children.",
			"Yesterday's the past, tomorrow's the future, but today is a gift. That's why it's called the present.",
			"Lost time is never found again.",
			"I always wanted to be somebody, but now I realize I should have been more specific.",
			"There are lots of people who mistake their imagination for their memory.",
			"Well, if I called the wrong number, why did you answer the phone?",
			"California is a fine place to live - if you happen to be an orange.",
			"Life is hard. After all, it kills you.", "I have never been hurt by what I have not said.",
			"A word to the wise is infuriating.", "Food is an important part of a balanced diet.",
			"Common sense is the collection of prejudices acquired by age eighteen.",
			"A child of five would understand this. Send someone to fetch a child of five.",
			"You can always tell when a man's well informed. His views are pretty much like your own.",
			"My one regret in life is that I am not someone else.",
			"If at first you don't succeed, blame your parents.", "What's another word for Thesaurus?",
			"It's amazing that the amount of news that happens in the world every day always just exactly fits the newspaper.",
			"I wanna make a jigsaw puzzle that's 40,000 pieces. And when you finish it, it says go outside.",
			"Progress is man's ability to complicate simplicity.",
			"Boy, those French: they have a different word for everything!",
			"I have tried to know absolutely nothing about a great many things, and I have succeeded fairly well.",
			"Conversation would be vastly improved by the constant use of four simple words: I do not know." };

	/**
	 * Guru object is an anchor pane with a top(image button) and bottom(text
	 * bubble) anchor. Position x, y are the starting position in the parent
	 * layout manager.
	 * 
	 * After creating the guru you can call 
	 * setScript(String[] script) : pass array of thing it says when clicked 
	 * startAnimation(double time in milliseconds) : begin a simple animation of transform image with onfinished thought bubble message
	 * setSpeechMessage(String) : set message in speech bubble
	 * setThoughtMessage(String) : set message in thought bubble
	 * setPowMessage(String) : set message in comic book style action speech Bubble
	 * move(double x, double y) : move to double x, y
	 * flip
	 * twirl
	 * various methods to interact with food
	 * 
	 * 
	 * isClicked() : boolean if the guru has been clicked
	 * 
	 * @param x
	 * @param y
	 */
	public Guru(double x, double y) {
		super();
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.offSetX = x;
		this.offSetY = y;
		this.message = new SimpleStringProperty();
		this.bubble = new Label();
		this.bubble.getStyleClass().add("bubble");
		this.bubble.textProperty().bind(message);
		this.bubble.setVisible(false);
		this.bubble.setMouseTransparent(true);
		this.imageView = new ImageView(ResourceManager.getResourceImage("guru.png"));
		this.imageView.setPreserveRatio(true);
		this.imageView.setFitHeight(100);
		this.imageView.setPreserveRatio(true);
		this.button = new Button();
		this.button.setStyle("-fx-background-color: transparent;");
		this.button.setGraphic(imageView);
		this.button.getStyleClass().add("button");

		// add components to the anchor pane
		this.getChildren().addAll(button, bubble);

		// Positing
		AnchorPane.setTopAnchor(this.button, 5.0);
		AnchorPane.setBottomAnchor(this.bubble, 5.0);
	}

	/**
	 * Toggles the guru to display text bubble on click. Sets random String
	 * argument from passed array to display in Text bubble.
	 * 
	 * @param tips
	 */
	public void setScript(String[] script) {
		if (script != null) {
			this.script = script;
			this.isSpeaking = false;
		}
		this.button.setOnAction(e -> {
			this.isSpeaking = !this.isSpeaking;

			if (this.isSpeaking) {
				this.setSpeechMessage(this.script[new Random().nextInt(this.script.length)]);
			} else
				this.fadeOut(1);
		});

	}

	/**
	 * Start simple animation. I don't know what the overhead is yet, so I'm not
	 * going overboard
	 */
	public void startAnimation() {

		// A Timeline, defined by one or more KeyFrames,
		// processes individual KeyFrame sequentially,
		// in the order specified by KeyFrame.time.
		this.timeline = new Timeline();
		this.timeline.setCycleCount(Animation.INDEFINITE);
		this.timeline.setAutoReverse(true);

		// create a keyValue with factory: scaling
		KeyValue keyValueX = new KeyValue(this.scaleXProperty(), 0.75);
		KeyValue keyValueY = new KeyValue(this.scaleYProperty(), 0.75);

		// create a keyFrame, the keyValue is reached at time 2s
		// one can add a specific action when the keyframe is reached
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				if (!isSpeaking()) {
					setThoughtMessage(wiseSayings[new Random().nextInt(wiseSayings.length)]);
				}
				// events like swaping images can go here
				// I can also add a timer to count animation frames for a
				// conditional check
			}
		};

		KeyFrame keyFrame = new KeyFrame(Duration.millis(20000), onFinished, keyValueX, keyValueY);

		// add the keyframe to the timeline
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

	/**
	 * Twirls image around Y-axis, speed in milliseconds
	 * 
	 * @param speed
	 */
	public void twirl(double speed) {
		RotateTransition rotator = new RotateTransition(Duration.millis(speed), this.button);
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(0);
		rotator.setToAngle(360);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(1);
		rotator.play();

	}

	/**
	 * Enable float behavior by using path, but it doesn't work well with moveTo
	 * 
	 */
	public void enablePath() {
		// http://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Path.html

		// CREATE A NEW PATH
		final Path path = new Path();

		// THE START OF OUR PATH IS 30, 30
		path.getElements().add(new MoveTo(50, 75));

		// ARC PATH
		ArcTo arcTo = new ArcTo();
		arcTo.setX(80);
		arcTo.setY(75);
		arcTo.setRadiusX(30);
		arcTo.setRadiusY(75);

		// ADD ARC PATH TO THE PATH
		path.getElements().add(arcTo);
		path.setOpacity(0); // SET THIS TO ZERO TO MAKE THE PATH INVISIBLE

		// ADD THE PATH TO THE ANCHORPANE
		this.getChildren().add(path);

		// PATH TRANSITION - SET DURATIONS
		final PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.seconds(2)); // ANIMATION TIME
															// LENGTH
		pathTransition.setDelay(Duration.seconds(0)); // TIME BEFORE ANIMATION
														// STARTS
		pathTransition.setPath(path);
		pathTransition.setNode(button);
		pathTransition.setOrientation(OrientationType.NONE); // KEEP THIS
																// UPRIGHT
		pathTransition.setCycleCount(Timeline.INDEFINITE); // GO FOREVER
		pathTransition.setAutoReverse(true); // IT REVERSES RATHER THAN STARTS
												// OVER

		ParallelTransition parallelTransition = new ParallelTransition(pathTransition);

		parallelTransition.play();

	}

	/**
	 * Add Food object to Sorted Set
	 * 
	 * @param food
	 */
	public void addFood(Food food) {
		this.foods.add(food);

	}

	/**
	 * Add ArrayList of Food objects to sorted set
	 * 
	 * @param foods
	 */
	public void addFoods(ArrayList<Food> foods) {
		this.foods.addAll(foods);
	}
	

	/**
	 * Add Set of Food objects to sorted set
	 * 
	 * @param foods
	 */
	public void addFoods(SortedSet<Food> foods) {
		this.foods.addAll(foods);
	}

	/**
	 * Remove Food objects from sorted set
	 * 
	 * @param foods
	 */
	public void removeFood(Food food) {
		this.foods.remove(food);
	}

	/**
	 * Get healthiest food to display in speech bubble String first : set
	 * message before food name String second : set message after food name
	 */
	public void getBestFood(String first, String second) {
		if (!foods.isEmpty()) {
			this.setSpeechMessage(this.foods.first().getName());
		}
	}

	/**
	 * Gets most unhealthy food to display in speech bubble String first : set
	 * message before food name String second : set message after food name
	 * 
	 */
	public void getWorstFood() {
		if (!foods.isEmpty()) {
			this.setSpeechMessage(this.foods.last().getName());
		}
	}

	/**
	 * Toggles boolean if Guru is clicked
	 * 
	 * @return
	 */
	public boolean isSpeaking() {
		return this.isSpeaking;
	}

	/**
	 * It is supposed to move Guru to the coordinates but Translate Transition
	 * needs a translation
	 * 
	 * @param x
	 * @param y
	 */
	public void move(double x, double y) {
		this.tt = new TranslateTransition(Duration.millis(5000), this);
		if (x > this.getLayoutX())
			this.button.setScaleX(-1);
		else
			this.button.setScaleX(1);
		this.tt.setToX(x - offSetX);
		this.tt.setToY(y - offSetY);
		this.tt.play();
	}

	/**
	 * Set message for thought bubble, hard limit of 150 chars, line limit 50
	 * chars per line
	 * 
	 * @param message
	 */
	public void setThoughtMessage(String message) {
		this.setBubble("thought_cloud");
		this.message(message);

	}

	/**
	 * Set message for text bubble, hard limit of 150 chars, line limit 50 chars
	 * per line
	 * 
	 * @param message
	 */
	public void setPowMessage(String message) {
		this.setBubble("pow");
		this.message(message);
	}

	/**
	 * Set message for text bubble, hard limit of 150 chars, line limit 50 chars
	 * per line
	 * 
	 * @param message
	 */
	public void setSpeechMessage(String message) {
		this.setBubble("bubble");
		this.message(message);
	}

	/**
	 * private method to manage bubble
	 * 
	 * @param message
	 */
	private void message(String message) {
		this.fadeIn(2);
		if (message.length() < 150) {
			StringBuilder sb = new StringBuilder(message);
			int i = 0;
			while ((i = sb.indexOf(" ", i + 50)) != -1) {
				sb.replace(i, i + 1, "\n");
			}
			this.message.setValue(sb.toString());
		}
	}

	/**
	 * Private String to set image
	 * 
	 * @param image
	 */
	public void setImage(String image) {
		if (image != null)
			this.imageView.setImage(ResourceManager.getResourceImage(image));
	}

	/**
	 * Private String to set stylesheet
	 * 
	 * @param style
	 */
	private void setBubble(String style) {
		try {
			this.bubble.getStyleClass().clear();
			this.bubble.getStyleClass().add(style);
		} catch (IllegalArgumentException e) {
		}

	}

	/**
	 * Rotate Guru object 360 degrees Rotation speed in milliseconds
	 * 
	 * @param speed
	 */
	public void flip(double speed) {
		RotateTransition rt = new RotateTransition(Duration.millis(speed), this.button);
		rt.byAngleProperty();
		rt.setByAngle(360);
		rt.setAutoReverse(true);
		rt.play();
	}

	/**
	 * 
	 * Load a .ttf or .otf font
	 * 
	 * 
	 * @param font
	 * @param size
	 * @return
	 */
//	public void loadFont(String font, double size) {
//		// This behavior belongs in utility.ResourceManager, but it is not used
//		// at the moment
//		try {
//			Font myFontloadFontAirstream = Font.loadFont(getClass().getResourceAsStream(font), size);
//			this.bubble.setFont(myFontloadFontAirstream);
//		} catch (IllegalArgumentException e) {
//
//		}
//	}
	

	/**
	 * Toggles text bubble to visible and fades it in
	 * 
	 * @param node
	 */
	private void fadeIn(double time) {
		this.bubble.setVisible(true);
		fade = new FadeTransition(Duration.seconds(time), this.bubble);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();
	}

	/**
	 * Fades text bubble out and toggles it to invisible
	 * 
	 * @param node
	 */
	private void fadeOut(double time) {
		fade = new FadeTransition(Duration.seconds(time), this.bubble);
		fade.setFromValue(1);
		fade.setToValue(0);
		fade.play();

	}

}
