package gui.smartnode;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is a TextField which implements an "autocomplete" functionality, based on a supplied list of entries.
 * 
 */
public class AutoCompleteTextField extends TextField {
	/** The existing autocomplete entries. */
	private SortedSet<String> entries;
	/** The popup used to select an entry. */
	private ContextMenu entriesPopup;

	/** Construct a new AutoCompleteTextField. */
	public AutoCompleteTextField() {

		super();

		this.entries = new TreeSet<>();
		this.entriesPopup = new ContextMenu();

		this.textProperty().addListener((ChangeListener<String>) (observableValue, s, s2) -> {
			if ((AutoCompleteTextField.this.getText() != null) && (AutoCompleteTextField.this.getText().length() == 0))
				AutoCompleteTextField.this.entriesPopup.hide();
			else {
				LinkedList<String> searchResult = new LinkedList<>();
				// Makes results pop out instead of drop down
				final List<String> filteredEntries = AutoCompleteTextField.this.entries.stream()
						.filter(e -> e.toLowerCase().contains(AutoCompleteTextField.this.getText().toLowerCase()))
						.collect((Collectors.toList()));
				searchResult.addAll(filteredEntries);
				if (AutoCompleteTextField.this.entries.size() > 0) {
					AutoCompleteTextField.this.populatePopup(searchResult);
					if (!AutoCompleteTextField.this.entriesPopup.isShowing())
						AutoCompleteTextField.this.entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
				} else
					AutoCompleteTextField.this.entriesPopup.hide();
			}
		});

		this.focusedProperty().addListener((ChangeListener<Boolean>) (observableValue, aBoolean,
				aBoolean2) -> AutoCompleteTextField.this.entriesPopup.hide());

	}

	/**
	 * Get the existing set of autocomplete entries.
	 * 
	 * @return The existing autocomplete entries.
	 */
	public SortedSet<String> getEntries() {
		return this.entries;
	}

	/**
	 * Populate the entry set with the given search results. Display is limited
	 * to 10 entries, for performance.
	 * 
	 * @param searchResult
	 *            The set of matching strings.
	 */
	private void populatePopup(List<String> searchResult) {
		List<CustomMenuItem> menuItems = new LinkedList<>();
		// If you'd like more entries, modify this line.
		int maxEntries = 10;
		int count = Math.min(searchResult.size(), maxEntries);
		for (int i = 0; i < count; i++) {
			final String result = searchResult.get(i);
			Label entryLabel = new Label(result);
			CustomMenuItem item = new CustomMenuItem(entryLabel, true);
			item.setOnAction(actionEvent -> {
				AutoCompleteTextField.this.setText(result);
				AutoCompleteTextField.this.entriesPopup.hide();
			});
			menuItems.add(item);
		}

		this.entriesPopup.getItems().clear();
		this.entriesPopup.getItems().addAll(menuItems);

	}

	public void refresh() {

		this.entries = new TreeSet<>();
		this.entriesPopup = new ContextMenu();

		this.textProperty().addListener((ChangeListener<String>) (observableValue, s, s2) -> {
			if ((AutoCompleteTextField.this.getText() != null) && (AutoCompleteTextField.this.getText().length() == 0))
				AutoCompleteTextField.this.entriesPopup.hide();
			else {
				LinkedList<String> searchResult = new LinkedList<>();
				final List<String> filteredEntries = AutoCompleteTextField.this.entries.stream()
						.filter(e -> e.toLowerCase().contains(AutoCompleteTextField.this.getText().toLowerCase()))
						.collect((Collectors.toList()));
				searchResult.addAll(filteredEntries);
				if (AutoCompleteTextField.this.entries.size() > 0) {
					AutoCompleteTextField.this.populatePopup(searchResult);
					if (!AutoCompleteTextField.this.entriesPopup.isShowing())
						AutoCompleteTextField.this.entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
				} else
					AutoCompleteTextField.this.entriesPopup.hide();
			}
		});

		this.focusedProperty().addListener((ChangeListener<Boolean>) (observableValue, aBoolean,
				aBoolean2) -> AutoCompleteTextField.this.entriesPopup.hide());

	}

}