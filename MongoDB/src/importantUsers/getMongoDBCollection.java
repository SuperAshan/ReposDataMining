package importantUsers;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class getMongoDBCollection
{
	public static DBCollection getMongoDBColl(String host, String DBName,
			String username, String password, String collectionName)
			throws UnknownHostException
	{
		// Mongo mg = new Mongo(host, 27017);

		// for (String name : mg.getDatabaseNames())
		// {
		// System.out.println("dbName: " + name);
		// }
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.socketTimeout(0);
		builder.connectTimeout(120000);
		builder.socketKeepAlive(true);
		builder.autoConnectRetry(true);
		MongoClientOptions opt = builder.build();
		MongoClient mg = new MongoClient(new ServerAddress(host, 27017), opt);
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
		return collection;

	}
}
