package gui;

import javax.management.timer.Timer;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import utility.ResourceManager;

public class Guru extends AnchorPane {
	Button button;
	Label chatMessage;
	String text;
	
	public Guru(){
		this.button = button;
		this.chatMessage = chatMessage;
		this.text = text;
	}
	public void getPath() {
		 chatMessage = new Label();
	     chatMessage.getStyleClass().add("chat-bubble");
		final Path path = new Path();
		//THE START OF OUR PATH IS 30, 30
			path.getElements().add(new MoveTo(30, 30));
			
			//ARC PATH
			ArcTo arcTo = new ArcTo();
			arcTo.setX(80);
			arcTo.setY(75);
			arcTo.setRadiusX(30);
			arcTo.setRadiusY(75);
			
			//ADD ARC PATH TO THE PATH
			path.getElements().add(arcTo);
			path.setOpacity(0); //SET THIS TO ZERO TO MAKE THE PATH INVISIBLE
						
			//WHAT WE WANT TO MOVE AROUND THE SCREEN
			final ImageView imageView = new ImageView(ResourceManager.getResourceImage("guru.png"));
			imageView.setPreserveRatio(true);
			imageView.setFitHeight(100);
			
			button = new Button();
			button.setStyle("-fx-background-color: transparent;");
			button.setGraphic(imageView);
			button.getStyleClass().add("button");

			//ADD OUR components to the anchor pane
			this.getChildren().addAll(button, chatMessage);
			
			// Positing stuff
			Guru.setRightAnchor(button,5.0);
			Guru.setLeftAnchor(chatMessage,5.0);
			chatMessage.setLayoutY(-100);
			//PATH TRANSITION - SET DURATIONS
			final PathTransition pathTransition = new PathTransition();
			
			// add path to the AnchorPane
			pathTransition.setDuration(Duration.seconds(5)); //ANIMATION TIME LENGTH
			pathTransition.setDelay(Duration.seconds(0)); //TIME BEFORE ANIMATION STARTS
			pathTransition.setPath(path);
			pathTransition.setNode(this);
			pathTransition.setOrientation(OrientationType.NONE); //KEEP THIS UPRIGHT
			pathTransition.setCycleCount(Timeline.INDEFINITE); //GO FOREVER
			pathTransition.setAutoReverse(true); //IT REVERSES RATHER THAN STARTS OVER

			final ParallelTransition parallelTransition = new ParallelTransition(pathTransition);
			
			parallelTransition.play();
	
			
	}

	public void setText(String text) {
		this.button.setOnAction(e -> {
			this.chatMessage.setVisible(true);
			this.setText(text);
			this.chatMessage.setVisible(false);

			
		});
	}
}
