package importantUsers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ImportantUsers
{
	private String host = "10.108.193.226";
	private String dbName = "datamining";
	private String username = "ittcdb";
	private String password = "ittc706706";
	private String collectionName = "UserData";
	private DBCollection userData;
	Logger log = Logger.getLogger(ImportantUsers.class.getName());

	@SuppressWarnings("unchecked")
	public void getImportantUsers(String pathname) throws IOException
	{
		int fol = 0;
		Long id = 0l;
		String ss = "";
		int count = 0, num = 0, invalid = 0;
		FileWriter FW = new FileWriter(new File(pathname));

		userData = getMongoDBCollection.getMongoDBColl(host, dbName, username,
				password, collectionName);
		DBCursor cursor = userData.find();//.batchSize(80);
		while (cursor.hasNext())
		{
			DBObject cur = cursor.next();
			List<Long> follows = new ArrayList<Long>();
			try
			{
				fol = (Integer) cur.get("Fol");
				id = (Long) cur.get("ID");

				follows = (List<Long>) cur.get("Follows");
			} catch (Exception e)
			{
				// TODO: handle exception
				invalid++;
				continue;
			}

			ss = fol + " " + id + "\n";
			if (follows == null)
			{
				num++;
			} else if (fol != follows.size())
			{
				num++;
			}

			FW.append(ss);

			count++;
			if (count % 10000 == 0)
			{
				log.info(invalid + "-/-" + num + "-/-" + count);

			}
			if (count % 4091439 == 0)
			{
				FW.flush();
				FW.close();
				FW = new FileWriter(new File(count + pathname));
			}
			// log.info(count);
		}
		FW.flush();
		FW.close();

		cursor.close();

	}
}
