package edu.berkeley.cs.nlp.ocular.data.textreader;

import static org.junit.Assert.assertEquals;
import static edu.berkeley.cs.nlp.ocular.data.textreader.Charset.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.berkeley.cs.nlp.ocular.util.CollectionHelper;

/**
 * @author Dan Garrette (dhgarrette@gmail.com)
 */
public class BasicTextReaderTests {

	private String s1 = "ing th\\~q || | follies of thõsè, who éither ``sæek'' out th\\\"os\\`e wæys \"and\" means, which either are sq̃uccess lessons";

	@Test
	public void test_readCharacters_qtilde() {
		TextReader tr = new BasicTextReader();
		assertEquals(Arrays.asList("\\~q"), tr.readCharacters("q̃"));
		assertEquals(Arrays.asList("t", "h", "\\~q", "r"), tr.readCharacters("thq̃r"));
		assertEquals(Arrays.asList("t", "h", "\\~q", "r"), tr.readCharacters("th\\~qr"));
	}

	@Test
	public void test_readCharacters_stackedDiacritics() {
		TextReader tr = new BasicTextReader();
		assertEquals(Arrays.asList("\\`\\'\\\"\\-\\~n"), tr.readCharacters("\\`\\'ñ" + MACRON_COMBINING + DIAERESIS_COMBINING));
	}

	@Test
	public void test_readCharacters_dia() {
		TextReader tr = new BasicTextReader();
		List<String> r = Arrays.asList("i", "n", "g", " ", "t", "h", "\\~q", " ", "|", "|", " ", "|", " ", "f", "o", "l", "l", "i", "e", "s", " ", "o", "f", " ", "t", "h", "\\~o", "s", "\\`e", ",", " ", "w", "h", "o", " ", "\\'e", "i", "t", "h", "e", "r", " ", "\"", "s", "æ", "e", "k", "\"", " ", "o", "u", "t", " ", "t", "h", "\\\"o", "s", "\\`e", " ", "w", "æ", "y", "s", " ", "\"", "a", "n", "d", "\"", " ", "m", "e", "a", "n", "s", ",", " ", "w", "h", "i", "c", "h", " ", "e", "i", "t", "h", "e", "r", " ", "a", "r", "e", " ", "s", "\\~q", "u", "c", "c", "e", "s", "s", " ", "l", "e", "s", "s", "o", "n", "s");
		assertEquals(r, tr.readCharacters(s1));
	}

	@Test
	public void test_readCharacters_backslash() {
		TextReader tr = new BasicTextReader();
		List<String> r = Arrays.asList("t", "h", "i", "s", "\\\\", "t", "h", "a", "t", "\\\\", "t", "h", "e", "\\\\");
		assertEquals(r, tr.readCharacters("this\\\\that\\\\the\\\\"));
	}

	@Test
	public void test_readCharacters_banned() {
		String s = "thi&s\\\\tha$t\\\\t$he\\\\";
		
		TextReader tr = new BasicTextReader();
		List<String> r = Arrays.asList("t", "h", "i", "&", "s", "\\\\", "t", "h", "a", "$", "t", "\\\\", "t", "$", "h", "e", "\\\\");
		assertEquals(r, tr.readCharacters(s));

		TextReader tr2 = new BasicTextReader(CollectionHelper.makeSet("$","&"));
		List<String> r2 = Arrays.asList("t", "h", "i", "s", "\\\\", "t", "h", "a", "t", "\\\\", "t", "h", "e", "\\\\");
		assertEquals(r2, tr2.readCharacters(s));
	}

}
