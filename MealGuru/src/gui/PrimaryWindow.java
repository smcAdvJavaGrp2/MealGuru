package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import data.mealguru.DietDA;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import user.User;
import utility.ResourceManager;

public class PrimaryWindow extends Application {

	// CLASS MEMBERS

	private static Scene primaryScene;

	private static User activeUser;

	private static Date centerDate;

	// MAIN METHOD
	public static void main(String[] args) {

		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Pane pane = new Pane();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 4);
		primaryStage.setMaximized(true);

		PrimaryWindow.primaryScene = new Scene(pane, 1450, 800);
		PrimaryWindow.primaryScene.getStylesheets().add(ResourceManager.getCSS("style.css"));

		primaryStage.setWidth(primScreenBounds.getWidth());
		//primaryStage.setMinWidth(1450);

		primaryStage.setHeight(primaryStage.getHeight());
		//primaryStage.setMinHeight(800);

		primaryStage.setScene(PrimaryWindow.primaryScene);
		primaryStage.setTitle("Meal Guru");

		PrimaryWindow.displayWelcomeGUI();

		primaryStage.show();

	}

	// GETTERS/SETTERS

	public static User getActiveUser() {
		return PrimaryWindow.activeUser;
	}

	public static void setActiveUser(User user) {

		PrimaryWindow.activeUser = user;

		String extension;
		if ((PrimaryWindow.getActiveUser() != null)
				&& ((extension = ResourceManager.getUserCSS(activeUser.getCustomCSSExtension())) != null)) {
			PrimaryWindow.primaryScene.getStylesheets().clear();
			PrimaryWindow.primaryScene.getStylesheets().add(extension);
		}

	}

	public static Date getCenterDate() {

		return centerDate;

	}

	public static void setCenterDate(Date center) {

		centerDate = center;

	}

	// METHODS

	public static void displayWelcomeGUI() {

		PrimaryWindow.setActiveUser(null);

		PrimaryWindow.setCenterDate(null);

		PrimaryWindow.getVisibleScene().getStylesheets().clear();
		PrimaryWindow.getVisibleScene().getStylesheets().add(ResourceManager.getCSS("style.css"));

		PrimaryWindow.primaryScene.setRoot(new SplashPageGUI());

	}

	public static void displayNewUserGUI() {

		PrimaryWindow.primaryScene.setRoot(new NewUser());

	}

	public static void editUserGUI() {

		PrimaryWindow.primaryScene.setRoot(new UserEditor());

	}

	public static void displayMainGUI() {

		PrimaryWindow.primaryScene.setRoot(new MainGUI());

	}

	public static void displayDailyIntakeWeekSummary() {

		PrimaryWindow.primaryScene.setRoot(new DailyIntakeWeekSummary());

	}

	// PHOTOGRABBER

	public static BufferedImage grabImage() {

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		FileChooser.ExtensionFilter extFilterJPEG = new FileChooser.ExtensionFilter("JPEG files (*.jpeg)", "*.JPEG");

		fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterJPEG);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(PrimaryWindow.primaryScene.getWindow());

		if ((file != null) && file.exists())
			try {
				return ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return null;

	}

	public static Scene getVisibleScene() {
		return primaryScene;
	}

}