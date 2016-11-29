package gui.smartnode;

import gui.PrimaryWindow;
import javafx.scene.control.Button;
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

		ContextMenu contextMenu = new ContextMenu();

		MenuItem editItem = new MenuItem("Edit Personal Information");
		editItem.setOnAction(e -> {
			PrimaryWindow.editUserGUI();
		});
		MenuItem signOut = new MenuItem("Sign Out");
		signOut.setOnAction(e -> {
			PrimaryWindow.displayWelcomeGUI();
		});
		MenuItem close = new MenuItem("Cancel");
		contextMenu.getItems().addAll(editItem, signOut, close);
		this.setContextMenu(contextMenu);

	}

}
