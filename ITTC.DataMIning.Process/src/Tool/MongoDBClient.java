package Tool;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongoDBClient
{
	public static DBCollection getMongoDBColl(String coll)
			throws UnknownHostException
	{
		Mongo mg = new Mongo("10.108.201.165");

		for (String name : mg.getDatabaseNames())
		{
			System.out.println("dbName: " + name);
		}
		MongoOptions opt = mg.getMongoOptions();
		opt.setSocketTimeout(2000);
		DB db = mg.getDB("datamining");
		Set<String> collNames = db.getCollectionNames();
		for (String name : collNames)
		{
			System.out.println("collectionName: " + name);
		}
		// DBCollection statusData = db.getCollection("StatusData");
		DBCollection collection = db.getCollection(coll);
		return collection;

	}

	public static DBCollection getMongoDBColl(String host, String DBName,
			String username, String password, String collectionName)
			throws UnknownHostException
	{
		//Mongo mg = new Mongo(host, 27017);

		// for (String name : mg.getDatabaseNames())
		// {
		// System.out.println("dbName: " + name);
		// }
		MongoOptions opt = new MongoOptions();
		opt.connectTimeout = 120000;
		opt.autoConnectRetry = true;
		opt.socketKeepAlive = true;
		opt.socketTimeout = 200000;
		Mongo mg = new Mongo(new ServerAddress(host, 27017),opt);
		DB db = mg.getDB(DBName);
		db.authenticate(username, password.toCharArray());
		Set<String> collNames = db.getCollectionNames();
		for (String name : collNames)
		{
			System.out.println("collectionName: " + name);
		}
		// DBCollection statusData = db.getCollection("StatusData");
		DBCollection collection = db.getCollection(collectionName);
		System.out.println("The size of the collection you choose is "
				+ collection.count());
		List<DBObject> indexes = collection.getIndexInfo();
		for (DBObject i : indexes)
		{
			System.out.println("Indexes info of the collection:");
			System.out.println(i);
		}
		return collection;

	}

}
