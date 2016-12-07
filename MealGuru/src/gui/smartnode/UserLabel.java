package gui.smartnode;

import java.util.Optional;

import data.mealguru.UserDA;
import gui.PrimaryWindow;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import utility.ResourceManager;

public class UserLabel extends Button {

	ImageView imageView;

	public UserLabel() {

		double pictureSize = 100;

		this.getStyleClass().add("user-label");

		Image userImage = ResourceManager.getImage(PrimaryWindow.getActiveUser().getPictureExtension());

		this.imageView = new ImageView(userImage);

		this.imageView.setPreserveRatio(true);

		if (userImage.getWidth() > userImage.getHeight())
			this.imageView.setFitWidth(pictureSize);
		else
			this.imageView.setFitHeight(pictureSize);

		final Circle clip = new Circle(pictureSize / 2, pictureSize / 2, pictureSize / 2);
		this.imageView.setClip(clip);

		this.setGraphic(this.imageView);

		this.setOnAction(e -> {
			PrimaryWindow.editUserGUI();
		});

		ContextMenu contextMenu = new ContextMenu();

		MenuItem editItem = new MenuItem("Edit Personal Information");
		editItem.setOnAction(e -> {
			PrimaryWindow.editUserGUI();
		});

		MenuItem signOut = new MenuItem("Sign Out");
		signOut.setOnAction(e -> {
			PrimaryWindow.displayWelcomeGUI();
		});

		MenuItem deleteUser = new MenuItem("Delete Account");
		deleteUser.setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Delete Account?");
			alert.setHeaderText("Everything in your account will be deleted (forever).");
			alert.setContentText("Are you sure you want to delete your account?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && (result.get() == ButtonType.OK)) {
				new UserDA().deleteUserByUsername(PrimaryWindow.getActiveUser().getUsername());
				PrimaryWindow.displayWelcomeGUI();
			}
		});

		MenuItem close = new MenuItem("Cancel");
		contextMenu.getItems().addAll(editItem, signOut, deleteUser, close);
		this.setContextMenu(contextMenu);

	}

}
