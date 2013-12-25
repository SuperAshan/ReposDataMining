import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import Test.Demo;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class UesrInfo
{
	private final static String host = "10.108.201.165";
	private final static String dbName = "datamining";
	private final static String username = "ittcdb";
	private final static String password = "ittc706706";
	private final static String collectionName = "UserData";

	public static void main(String[] args) throws IOException
	{

		DBCollection coll = Demo.getMongoColl(host, dbName, username, password,
				collectionName);
		getUserInfo(coll, "四川 雅安", "yaUsers.txt");
	}

	public static void getUserInfo(DBCollection collection, String loc,
			String fileName) throws IOException
	{
		DBCursor cursor = collection.find().batchSize(60);
		int count = 0, tcou = 0;
		FileWriter fW = new FileWriter(fileName);
		FileWriter fW2 = new FileWriter("otherU.txt");
		long start = new Date().getTime();
		while (cursor.hasNext())// && count < 10)
		{
			DBObject cur = cursor.next();
			String userLoc = (String) cur.get("Loc");
			if (userLoc == null)
			{
				continue;

			} else if (userLoc.equals(loc))
			{
				String s1 = cur.get("ID") + " " + (String) cur.get("Name")
						+ " " + (String) cur.get("Gen") + " " + userLoc;
				fW.append(s1);
				fW.append("\n");
				count++;
			} else if (userLoc.contains("四川"))
			{
				String s1 = cur.get("ID") + " " + (String) cur.get("Name")
						+ " " + (String) cur.get("Gen") + " " + userLoc;
				fW2.append(s1);
				fW2.append("\n");

			}
			tcou++;
			if ((tcou % 100000) == 0)
			{
				System.out.println(count + " / " + tcou);

			}

		}
		fW.write(count);
		fW.flush();
		fW.close();

		fW2.flush();
		fW2.close();
		long end = new Date().getTime();
		System.out.println("时间（ms） " + (end - start));

	}

}
