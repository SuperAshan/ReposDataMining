package DataBases;

import java.util.List;
import java.util.Map;

import com.mongodb.*;

import Data.IDictionarySerializable;
 
public class MongoService
{

	protected com.mongodb.Mongo Mongo;

	protected DB db;

	public String IPAddress = "localhost";

	public int Port = 27017;

	// 数据库名
	public String DBName = "DataMining";

	public boolean IsUseable = true;

	// / <summary>
	// / 连接到数据库，只需执行一次
	// / </summary>
	public void ConnectDB()
	{
		try
		{
			Mongo = new Mongo(IPAddress, Port);

			db = Mongo.getDB(DBName);

		} catch (Exception e)
		{
			IsUseable = false;
			// TODO: handle exception
		}

	}

	// / <summary>
	// / 获取数据库中的所有实体
	// / </summary>
	// / <returns></returns>
	public List<IDictionarySerializable> GetAllEntitys(String tableName)
	{

		if (this.IsUseable == false)
		{
			return null;
		}

		DBCollection users = db.getCollection(tableName);
		DBCursor cur = users.find();

		while (cur.hasNext())
		{
			String string = cur.next().toString();
			System.out.println(string);

		}
		return null;

	}

	// / <summary>
	// / 直接插入一个实体
	// / </summary>
	// / <param name="user"></param>
	// / <param name="tableName"></param>
	public boolean InsertEntity(IDictionarySerializable user, String tableName,
			String key)
	{
		if (this.IsUseable == false)
		{
			return false;
		}
		DBCollection users = db.getCollection(tableName);
		DBObject object = new BasicDBObject();
		for (Map.Entry<String, Object> keyString : user.DictSerialize().entrySet())
		{
			object.put(keyString.getKey(), keyString.getValue());
		}

		users.insert(object);

		return true;
	}

	// / <summary>
	// / 更新或增加一个新文档
	// / </summary>
	// / <param name="user"></param>
	// / <param name="tableName">表名 </param>
	// / <param name="keyName"> </param>
	// / <param name="keyvalue"> </param>
	public void SaveOrUpdateEntity(IDictionarySerializable user,
			String tableName, String keyName, Object keyvalue)
	{
		if (this.IsUseable == false)
		{
			return;
		}

		DBCollection users = db.getCollection(tableName);
		DBObject object = users.findOne(new BasicDBObject(keyName, keyvalue));

		for (Map.Entry<String, Object> keyString : user.DictSerialize().entrySet())
		{
			object.put(keyString.getKey(), keyString.getValue());
		}
		users.save(object);

		users.insert(object);
	}

}
