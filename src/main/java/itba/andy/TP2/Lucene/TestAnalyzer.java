package itba.andy.TP2.Lucene;

import java.io.*;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.*;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class TestAnalyzer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		// SimpleAnalyzer
		// StandardAnalyzer
		// WhitespaceAnalyzer
		// StopAnalyzer
		// SpanishAnalyzer

		Analyzer a;

		a = new SimpleAnalyzer();

		// a= new StandardAnalyzer();

		// a= new WhitespaceAnalyzer();

		// CharArraySet sw = StopFilter.makeStopSet("de", "y");
		// a= new StopAnalyzer(sw);

		// a= new SpanishAnalyzer();

		String fieldValue = "Estructura de datos. Y algoritmos; 2020-Q1  en eda.ita.edu";

		TokenStream tokenStream = a.tokenStream("fieldName", fieldValue);
		CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
		tokenStream.reset();
		while (tokenStream.incrementToken()) {
			System.out.println(attr.toString());
		}
	}

}
