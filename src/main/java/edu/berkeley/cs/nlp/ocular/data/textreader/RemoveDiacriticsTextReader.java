package edu.berkeley.cs.nlp.ocular.data.textreader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dan Garrette (dhgarrette@gmail.com)
 */
public class RemoveDiacriticsTextReader implements TextReader {

	private TextReader delegate;

	public RemoveDiacriticsTextReader(TextReader delegate) {
		this.delegate = delegate;
	}

	public List<String> readCharacters(String line) {
		List<String> chars = new ArrayList<String>();
		for (String c : delegate.readCharacters(line)) {
			chars.add(Charset.removeAnyDiacriticFromChar(c)); // just the letter, which is the last char of the escaped string
		}
		return chars;
	}

	public String toString() {
		return "RemoveDiacriticsTextReader(" + delegate + ")";
	}

}
