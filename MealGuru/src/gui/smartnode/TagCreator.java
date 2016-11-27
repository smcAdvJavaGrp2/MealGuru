package gui.smartnode;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class TagCreator extends GridPane {

	private ArrayList<String> tags;

	public TagComboBox tagComboBox;
	public Button action;
	public Label tagLabel;

	public TagCreator() {

		this.tags = new ArrayList<>();

		this.tagComboBox = new TagComboBox();
		this.getStyleClass().add("box");
		
		this.action = new Button("add tag");
		this.action.setVisible(false);

		this.tagLabel = new Label();
		this.tagLabel.setWrapText(true);
		this.tagLabel.setPrefWidth(120);
		this.tagLabel.setTextAlignment(TextAlignment.LEFT);

		this.action.setOnAction(e -> {

			this.tags.add(this.tagComboBox.getValue().toLowerCase());

			this.tagComboBox.setValue("");
			this.action.setVisible(false);
			this.tagLabel.setText(this.tags.toString());

		});

		this.addRow(0, this.tagComboBox, this.action);
		this.addRow(1, this.tagLabel);

	}

	public ArrayList<String> getCategories() {
		return this.tags;
	}

	class TagComboBox extends ComboBox<String> {

		public TagComboBox() {

			this.setEditable(true);

			this.getItems().addAll("Vegetarian", "Paleo", "Vegan");

			this.setOnKeyTyped(e -> {

				if ((this.getValue() != null) && !this.getValue().equals("")) {
					TagCreator.this.action.setVisible(true);
					TagCreator.this.action.setText("add tag");

					if (TagCreator.this.tags.contains(this.getValue()))
						TagCreator.this.action.setText("remove tag");
					else
						TagCreator.this.action.setText("add tag");
				}

			});

			this.valueProperty().addListener(e -> {

				if ((this.getValue() != null) && !this.getValue().equals("")) {
					TagCreator.this.action.setVisible(true);
					TagCreator.this.action.setText("add tag");

					if (TagCreator.this.tags.contains(this.getValue()))
						TagCreator.this.action.setText("remove tag");
					else
						TagCreator.this.action.setText("add tag");
				}

			});

		}

	}

}
