package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dictionary {

	Set<String> dizionario;

	public Dictionary() {
		dizionario = new HashSet<String>();
	}

	public void loadDictionary(String language) {
		try {
			
			if (language.compareTo("Italian")==0)
			{
				language="C:\\Users\\Alberto\\git\\Lab03\\Lab03_SpellChecker\\src\\main\\resources\\Italian.txt";
			}
			else if (language.compareTo("English")==0)
			{
				language="C:\\Users\\Alberto\\git\\Lab03\\Lab03_SpellChecker\\src\\main\\resources\\English.txt";
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

	public List<RichWord> spellCheckTest(List<String> inputTextList) {
		List<RichWord> output= new LinkedList<RichWord>();
		for (String s: inputTextList)
		{
			RichWord r= new RichWord(s);
			if (dizionario.contains(s))
			{
				r.setCorrect();
			}
			output.add(r);
		}
			
		return output;
		
	}
	
	public String printError (List<RichWord> ls) {
		String s="";
		for (RichWord r: ls)
		{
			if (!r.isCorrect())
			{
				s+=r.getInput()+"\n";
			}
		}
		return s;
	}
	
	public int countError (List<RichWord> ls) {
		int i=0;
		for (RichWord r: ls)
		{
			if (!r.isCorrect())
			{
				i++;
			}
		}
		return i;
	}

}
