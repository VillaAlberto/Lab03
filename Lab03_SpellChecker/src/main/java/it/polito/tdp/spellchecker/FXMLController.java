package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	Dictionary d = new Dictionary();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<String> choicheBox;

	@FXML
	private TextArea txtToSpell;

	@FXML
	private Button btnSpellCheck;

	@FXML
	private TextArea txtSpelled;

	@FXML
	private Label lblErrors;

	@FXML
	private Button btnClearText;

	@FXML
	private Label lblTime;

	@FXML
	void doClearText(ActionEvent event) {

		// Abilito il pulsante Spell
		btnSpellCheck.setDisable(false);
		choicheBox.setDisable(false);

		txtSpelled.clear();
		txtToSpell.clear();
		lblErrors.setText("");
		lblTime.setText("");

	}

	@FXML
	void doSpellCheck(ActionEvent event) {

		// Inibisco il pulsante Spell
		btnSpellCheck.setDisable(true);
		choicheBox.setDisable(true);
		btnClearText.setDisable(false);

		// Controllo che ci sia qualcosa da controllare
		String toSpell = txtToSpell.getText();
		if (toSpell.length() == 0 || toSpell == null) {
			txtSpelled.setText("Insert at least one word! Press Clear Text to restart");
			return;
		}

		// Pulisco il testo e guardo la lunghezza della lista
		toSpell = toSpell.replaceAll("\n", " ");
		toSpell = toSpell.toLowerCase().replaceAll("[.,\\/#!?$%\\^&\\*;:{}=\\-_`~()\\[\\]\"0-9]", "");
		// List<String> LToSpell = Arrays.asList(toSpell.split(" "));

		List<String> LToSpell = new LinkedList<String>();
		String[] parts = toSpell.split(" ");
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].matches("[a-zA-Z]+")) {
				LToSpell.add(parts[i].trim());
			}
		}

		if (LToSpell.size() == 0) {
			txtSpelled.setText("Insert at least one word! Press Clear Text to restart");
			return;
		}

		// Carico il dizioario e abilito clear text
		d.loadDictionary(choicheBox.getValue());

		// Faccio lo spelling e misuro il tempo impiegato

		double start = System.nanoTime();
		List<RichWord> ls = d.spellCheckTestDichotomic(LToSpell);
		double stop = System.nanoTime();

		txtSpelled.setText(d.printError(ls));
		lblTime.setText("Spell check completed in " + String.valueOf((stop - start) / 1000000000 + " seconds"));
		lblErrors.setText("The text contains " + d.countError(ls) + " errors");

	}

	@FXML
	void initialize() {
		assert choicheBox != null : "fx:id=\"choicheBox\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtToSpell != null : "fx:id=\"txtToSpell\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtSpelled != null : "fx:id=\"txtSpelled\" was not injected: check your FXML file 'Scene.fxml'.";
		assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'Scene.fxml'.";
		assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'Scene.fxml'.";

		choicheBox.setItems(FXCollections.observableArrayList("English", "Italian"));
		choicheBox.setValue("English");

	}
}
