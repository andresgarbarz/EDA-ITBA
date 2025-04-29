package itba.andy.Parcial1.Y2024_Q2.ej3.Lucene;

import itba.andy.TP2.Lucene.Utils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class TheSearcher {

	private static IndexReader getIndexReader() throws IOException {

		// target index directory
		Directory indexDir = FSDirectory.open(Paths.get(Utils.getPrefixDir() + "/index/"));

		return DirectoryReader.open(indexDir);

	}

	public static void main(String[] args) {

		try {
			IndexReader index = getIndexReader();
			IndexSearcher searcher = new IndexSearcher(index);
			searcher.setSimilarity(new ClassicSimilarity());

			// field of interest
			String fieldName = "content";
			String queryStr1 = "fly";
			String queryStr2 = "moon";

			Term t1 = new Term(fieldName, queryStr1);
			Term t2 = new Term(fieldName, queryStr2);
			Query query = new BooleanQuery.Builder()
					.add(new TermQuery(t1), BooleanClause.Occur.SHOULD)
					.add(new TermQuery(t2), BooleanClause.Occur.SHOULD)
					.build();
			// run the query
			long startTime = System.currentTimeMillis();
			TopDocs topDocs = searcher.search(query, 20);
			long endTime = System.currentTimeMillis();

			// show the resultset
			System.out.println(String.format("Query=> %s\n", query));
			System.out.println(String.format("%s topDocs documents found in %d ms.\n", topDocs.totalHits,
					(endTime - startTime)));

			ScoreDoc[] orderedDocs = topDocs.scoreDocs;

			int position = 1;
			System.out.println("Resultset=> \n");

			for (ScoreDoc aD : orderedDocs) {

				// print info about finding
				int docID = aD.doc;
				double score = aD.score;
				System.out.println(String.format("position=%-10d  score= %10.7f", position, score));

				// print docID, score
				System.out.println(aD);

				// obtain ALL the stored fields
				Document aDoc = searcher.doc(docID);
				System.out.println("Stored fields: " + aDoc);
				System.out.println(aDoc.get("path"));
				System.out.println(aDoc.get("content"));
				Explanation rta = searcher.explain(query, docID);
				System.out.println(rta);

				position++;
				System.out.println();
			}

			index.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}