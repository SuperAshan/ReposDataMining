package Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ArticalSearch
{

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException
	{
		// TODO Auto-generated method stub
		search("/home/hadoop/Index/MongoArticalIndex", "高富帅");
	}

	public static void search(String indexDir, String q) throws IOException,
			ParseException
	{
		Directory dir = FSDirectory.open(new File(indexDir));
		@SuppressWarnings("deprecation")
		IndexSearcher iS = new IndexSearcher(dir);
		TermQuery Titquery = new TermQuery(new Term("Title", q));
		TermQuery conQuery = new TermQuery(new Term("ArticalContent", q));
		QueryParser parser = new QueryParser(Version.LUCENE_35,
				"ArticalContent", new IKAnalyzer());
		Query queryq = parser.parse(q);
		BooleanQuery query = new BooleanQuery();
		query.add(Titquery, Occur.SHOULD);
		query.add(conQuery, Occur.SHOULD);
		long start = new Date().getTime();
		TopDocs tDocs = iS.search(queryq, 5);
		long end = new Date().getTime();
		for (ScoreDoc sdDoc : tDocs.scoreDocs)
		{
			Document document = iS.doc(sdDoc.doc);
			System.out.println(document.get("Title"));
			System.out.println(document.get("ArticalContent"));
			System.out.println("--------------------------------------------");

		}
		System.out.println("Searching time(ms)" + (end - start));

	}

}
