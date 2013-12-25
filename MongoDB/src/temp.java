import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class temp
{

	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException
	{
		// TODO Auto-generated method stub

		Mongo mg = new Mongo("10.108.193.226");
		DB db = mg.getDB("datamining");
		Set<String> collectionName = db.getCollectionNames();
		for (String name : collectionName)
		{
			System.out.print(name);
			DBCollection collection = db.getCollection(name);
			DBCursor cursor = collection.find();
			System.out.println(cursor.size());
		}

	}

}
