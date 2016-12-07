package gui;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import utility.ResourceManager;

public class Guru extends AnchorPane {
	// main timeline
	private Timeline timeline;
	private TranslateTransition tt;
	FadeTransition fade;
	private final ImageView imageView;
	private Button button;
	private Label bubble;
	private StringProperty message;
	private boolean isSpeaking;
	private double posX, posY;
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
	 * After creating the guru you can call enableTips(), startAnimation(),
	 * setMessage() and move() Also you can return x, y position and a boolean
	 * if the guru has been clicked
	 * 
	 * @param posX
	 * @param posY
	 */
	public Guru(double posX, double posY) {
		super();
		this.setLayoutX(posX);
		this.setLayoutY(posY);
		this.message = new SimpleStringProperty();
		this.bubble = new Label();
		this.bubble.getStyleClass().add("bubble");
		this.bubble.textProperty().bind(message);
		this.bubble.setVisible(false);
		this.imageView = new ImageView(ResourceManager.getResourceImage("guru1.png"));
		this.imageView.setPreserveRatio(true);
		this.imageView.setFitHeight(100);
		this.imageView.setPreserveRatio(true);
		this.button = new Button();
		this.button.setStyle("-fx-background-color: transparent;");
		this.button.setGraphic(imageView);
		this.button.getStyleClass().add("button");

		// ADD OUR components to the anchor pane
		this.getChildren().addAll(button, bubble);

		// Positing stuff
		Guru.setTopAnchor(this.button, 5.0);
		Guru.setBottomAnchor(this.bubble, 5.0);
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
		this.timeline.setCycleCount(Timeline.INDEFINITE);
		this.timeline.setAutoReverse(true);

		// create a keyValue with factory: scaling the circle 2times
		KeyValue keyValueX = new KeyValue(this.scaleXProperty(), .75);
		KeyValue keyValueY = new KeyValue(this.scaleYProperty(), .75);

		// create a keyFrame, the keyValue is reached at time 2s
		// one can add a specific action when the keyframe is reached
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				if (!isSpeaking()) {
					setThoughtMessage(wiseSayings[new Random().nextInt(wiseSayings.length)]);
				}
				// events like swaping images can go here
				// I can also add a timer to count animation frames for a
				// conditional check
			}
		};

		KeyFrame keyFrame = new KeyFrame(Duration.millis(10000), onFinished, keyValueX, keyValueY);

		// add the keyframe to the timeline
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}

	/**
	 * Get X Position of node in parent
	 * 
	 * @return
	 */
	public double getPosX() {
		return this.posX;
	}

	/**
	 * Get Y Position of node in parent
	 * 
	 * @return
	 */
	public double getPosY() {
		return this.posY;
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
		this.tt.setToX(x);
		this.tt.setToY(y);
		this.tt.play();
	}


	/**
	 * Set message for text bubble, hard limit of 100 words, soft limit 50 chars
	 * per line
	 * 
	 * @param message
	 */
	public void setThoughtMessage(String message) {
		this.setBubble("thought_cloud");
		this.message(message);

	}

	/**
	 * 
	 * 
	 * @param message
	 */
	public void setSpeechMessage(String message) {
		this.setBubble("bubble");
		this.message(message);
	}

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
	private void setImage(String image) {
		this.imageView.setImage(ResourceManager.getResourceImage(image));
	}

	/**
	 * Private String to set stylesheet
	 * 
	 * @param style
	 */
	private void setBubble(String style) {
		this.bubble.getStyleClass().clear();
		this.bubble.getStyleClass().add(style);

	}

	public void flip(double speed) {
        RotateTransition rt = new RotateTransition(Duration.millis(speed), this.button);
        	rt.byAngleProperty();
	        rt.setByAngle(360);
	        rt.setAutoReverse(true);
	        rt.play();
	}

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