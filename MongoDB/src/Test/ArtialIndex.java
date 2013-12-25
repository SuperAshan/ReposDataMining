package Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class ArtialIndex
{

	/**
	 * @param args
	 */
	private final static String indexDir = "/home/hadoop/Index/MongoArticalIndex";
	private final static File path = new File(indexDir);

	public static void main(String[] args) throws IOException
	{
		// TODO Auto-generated method stub

		DBCollection dbcollection = Demo.getMongoColl("ArticalInfo");
		// getMongoIndex(dbcollection);
	}

	public static void getMongoIndex(DBCollection dbcollection)
			throws IOException
	{
		long start = new Date().getTime();
		DBCursor cursor = dbcollection.find().batchSize(100);
		Directory directory = FSDirectory.open(path);
		@SuppressWarnings("deprecation")
		IndexWriter inWriter = new IndexWriter(directory, new IKAnalyzer(),
				true, IndexWriter.MaxFieldLength.UNLIMITED);
		int x = 1;
		while (cursor.hasNext())
		{
			BasicDBObject curs = (BasicDBObject) cursor.next();
			Document doc = new Document();
			doc.add(new Field("Title", cursgetToStri(curs, "Title"), Store.YES,
					Index.ANALYZED, TermVector.YES));
			doc.add(new Field("PublishTime",
					cursgetToStri(curs, "PublishTime"), Store.YES, Index.NO,
					TermVector.NO));
			doc.add(new Field("Author", cursgetToStri(curs, "Author"),
					Store.YES, Index.ANALYZED, TermVector.NO));
			doc.add(new Field("ArticalContent", cursgetToStri(curs,
					"ArticalContent"), Store.YES, Index.ANALYZED,
					TermVector.WITH_POSITIONS_OFFSETS));
			inWriter.addDocument(doc);
			System.out.println(x + " " + curs.toString());
			x++;

		}
		System.out.println(cursor.count() + " " + inWriter.numDocs());
		inWriter.close();
		long end = new Date().getTime();
		System.out.println("The Indexing Time(ms): " + (end - start));

	}

	public static String cursgetToStri(BasicDBObject curs, String fieldNa)
	{
		if (curs.get(fieldNa) == null)
		{
			return "";
		} else
		{
			return curs.get(fieldNa).toString();
		}

	}

}
