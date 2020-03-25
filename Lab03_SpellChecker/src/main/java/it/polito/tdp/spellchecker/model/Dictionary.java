package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dictionary {

	List<String> dizionario;

	public Dictionary() {
		dizionario = new ArrayList<String>();
	}

	public void loadDictionary(String language) {
		try {

			if (language.compareTo("Italian") == 0) {
				language = "C:\\Users\\Alberto\\git\\Lab03\\Lab03_SpellChecker\\src\\main\\resources\\Italian.txt";
			} else if (language.compareTo("English") == 0) {
				language = "C:\\Users\\Alberto\\git\\Lab03\\Lab03_SpellChecker\\src\\main\\resources\\English.txt";
			}

			FileReader fr = new FileReader(language);
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
				dizionario.add(word);
			}
			br.close();

		}

		catch (Exception e) {
			System.out.println("Errore nella lettura del file");
		}
	}

	// RICERCA CONTAINS
	public List<RichWord> spellCheckTest(List<String> inputTextList) {
		List<RichWord> output = new LinkedList<RichWord>();
		for (String s : inputTextList) {
			RichWord r = new RichWord(s);
			if (dizionario.contains(s)) {
				r.setCorrect();
			}

			output.add(r);

		}

		return output;

	}

	// RICERCA LINEARE
	public List<RichWord> spellCheckTestLinear(List<String> inputTextList) {
		List<RichWord> output = new LinkedList<RichWord>();
		for (String s : inputTextList) {
			RichWord r = new RichWord(s);
			for (String parola : dizionario) {
				if (parola.compareTo(s) == 0) {
					r.setCorrect();
				}
			}
			output.add(r);
		}

		return output;

	}

	// RICERCA DICOTOMICA
	public List<RichWord> spellCheckTestDichotomic(List<String> inputTextList) {

		List<RichWord> parole = new ArrayList<RichWord>();

		for (String str : inputTextList) {

			RichWord richWord = new RichWord(str);
			if (binarySearch(str.toLowerCase()))
				richWord.setCorrect();
			parole.add(richWord);
		}

		return parole;
	}

	private boolean binarySearch(String stemp) {
		int inizio = 0;
		int fine = dizionario.size();

		while (inizio != fine) {
			int medio = inizio + (fine - inizio) / 2;
			if (stemp.compareToIgnoreCase(dizionario.get(medio)) == 0) {
				return true;
			} else if (stemp.compareToIgnoreCase(dizionario.get(medio)) < 0) {
				fine = medio;
			} else {
				inizio = medio + 1;
			}
		}

		return false;
	}

	public String printError(List<RichWord> ls) {
		String s = "";
		for (RichWord r : ls) {
			if (!r.isCorrect()) {
				s += r.getInput() + "\n";
			}
		}
		return s;
	}

	public int countError(List<RichWord> ls) {
		int i = 0;
		for (RichWord r : ls) {
			if (!r.isCorrect()) {
				i++;
			}
		}
		return i;
	}

}
