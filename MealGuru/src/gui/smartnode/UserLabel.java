package gui.smartnode;

import gui.PrimaryWindow;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import utility.ResourceManager;

public class UserLabel extends Button {

	ImageView imageView;

	public UserLabel() {

		super(PrimaryWindow.getActiveUser().getUsername());

		this.imageView = new ImageView(ResourceManager.getImage(PrimaryWindow.getActiveUser().getPictureExtension()));

		this.imageView.setPreserveRatio(true);
		this.imageView.setFitHeight(50);
		this.imageView.setFitWidth(50);

		this.setGraphic(this.imageView);

	}

}
