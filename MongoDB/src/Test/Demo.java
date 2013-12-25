package Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class Demo
{

	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	private static final String host = "10.108.201.165";
	private static final String dbName = "datamining";
	private static final String username = "ittcdb";
	private static final String password = "ittc706706";
	private static final String collectionName = "UserData";

	private final String indexDir = "/home/hadoop/Index/MongoUserIndex";
	private File path = new File(indexDir);

	private Logger logger = Logger.getLogger(Demo.class.getName());

	public static void main(String[] args) throws IOException, ParseException
	{
		// TODO Auto-generated method stub
		DBCollection userData = getMongoColl(host, dbName, username, password,
				collectionName);
		// userData.createIndex(new BasicDBObject("ID", 1));
		// String keyword = "2001566275";
		Demo demo = new Demo();
		// demo.getFollowsOfID(keyword);

		// demo.MongoIndex(userData);
		demo.getusersdegree(userData, "Fol", "AllUserINdegree.txt",
				"AllIndegreeOfPerUser.txt");
		// demo.getusersdegree(userData, "Friends", "degreevalueOUT.txt");
		// demo.getUsersClusterC(userData, "clusterC80_a.txt");
		demo.logger.info("程序全部运行完毕。");

	}

	/**
	 * @param userData
	 * @param follOrFri
	 * @param txtName1
	 * @param txtName2
	 * @throws IOException
	 */
	public void getusersdegree(DBCollection userData, String follOrFri,
			String txtName1, String txtName2) throws IOException
	{
		// DBCollection userData = getMongoColl("UserData");
		// DBCursor cursor = statusData.find();
		DBCursor cursor = userData.find().batchSize(100);
		long start = new Date().getTime();
		int count = 0, x = 0, y = 0, z = 0;
		List<Integer> degreeList = new ArrayList<Integer>();
		List<Long> valueList = new ArrayList<Long>();
		List<Integer> degreeOfPerUser = new ArrayList<Integer>();// 这个list里记录每个用户的度。
		FileWriter fileWriter=new FileWriter(txtName2); 
		try
		{
			while (cursor.hasNext())
			{
				BasicDBObject cur = (BasicDBObject) cursor.next();
				// System.out.println(cur.get("Follows") +
				// cur1.get("ID").toString());
				long ID = 0;
				int IDdegree = 0;
				// 下边这段是通过读取用户的朋友列表，再确定其大小从而得到朋友数，即
				// 度。实际上是脑残绕弯了，完全可以直接从数据库读取“Fol”或“Fri”
				// List<Object> follows = (List<Object>) cur.get(follOrFri);
				// if (follows == null)
				// {
				// x++;
				// } else if (follows.size() == 0)
				// {
				// y++;
				// } else
				// {
				// z++;
				// ID = (long) cur.get("ID");
				// IDdegree = follows.size();
				// if (degreeList.contains((long) IDdegree))
				// {
				// int index = degreeList.indexOf((long) IDdegree);
				// valueList.set(index, valueList.get(index) + 1);
				// } else
				// {
				// degreeList.add(degreeList.size(), (long) IDdegree);
				// valueList.add(valueList.size(), 1l);
				// }
				// }
				if (cur.get(follOrFri) == null)
				{

				}
				else
				{
					IDdegree = cur.getInt(follOrFri);
					//degreeOfPerUser.add(IDdegree);
					fileWriter.append(IDdegree+"\n");
					if (degreeList.contains(IDdegree))
					{
						int index = degreeList.indexOf(IDdegree);
						valueList.set(index, valueList.get(index) + 1);
					}
					else
					{
						degreeList.add(IDdegree);
						valueList.add(1l);
					}
					count++;
				}

				if(count%10000==0){
					logger.info(z + " / " + count);
					}
			}
			
		}
		finally
		{
			fileWriter.flush();
			fileWriter.close();
			cursor.close();
		}
		// 把两个list写入txt文件！！
		WriteListToTxt(degreeList, valueList, null, txtName1);
		//WriteListToTxt(degreeOfPerUser, null, null, txtName2);

		long end = new Date().getTime();
		System.out.println("为null的数量：" + x + " 为0的数量：" + y + " 有值的数量:" + z
				+ "一共有：" + count + "个   耗时(s)：" + (end - start) / 1000);
	}

	public void getUsersClusterC(DBCollection userData, String txtName)
			throws IOException, ParseException
	{
		DBCursor cursor = userData.find().skip(10000)
				.addOption(Bytes.QUERYOPTION_NOTIMEOUT).batchSize(100);
		long start = new Date().getTime();
		@SuppressWarnings("unused")
		int count = 0, x = 0, y = 0, z = 0;
		List<Long> IdList = new ArrayList<Long>();
		@SuppressWarnings("rawtypes")
		List clusterCList = new ArrayList();
		@SuppressWarnings("rawtypes")
		List degreeList = new ArrayList<Object>();
		// FileWriter fWriter = new FileWriter("clusterCTemp");
		while (cursor.hasNext() && z < 100000)
		{
			BasicDBObject cur = (BasicDBObject) cursor.next();
			// System.out.println(cur.get("Follows") +
			// cur1.get("ID").toString());
			long ID = 0;
			int IDdegree = 0;
			int num = 0;// 计算簇系数时的分子！！
			double clusterC = 0;// 簇系数！！
			@SuppressWarnings("rawtypes")
			List follows = (List) cur.get("Follows");
			if (follows == null)
			{
				x++;
			}
			else if (follows.size() == 0)
			{
				y++;
			}
			else
			{
				z++;
				ID = (Long) cur.get("ID");
				IDdegree = follows.size();
				String id = Long.toString(ID);
				@SuppressWarnings("rawtypes")
				List list1 = getFollowsOfID(id);
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				System.out.println(z + "|总数为：" + (x + y + z) + "  |"
						+ df.format(new Date()));
				if (list1 == null)
				{
					continue;
				}
				else
				{
					for (int i = 0; i < list1.size(); i++)
					{
						System.out.println(z + "内");
						@SuppressWarnings("rawtypes")
						List list2 = getFollowsOfID((String) list1.get(i));
						if (list2 == null)
						{
							continue;
						}
						else
						{
							for (int j = 0; j < list1.size(); j++)
							{
								if (list2.contains(list1.get(j)))
								{
									num++;
								}
							}
						}

					}
				}

				// 计算簇系数
				if (IDdegree >= 2)
				{
					int Cn2 = getCnk(IDdegree, 2);
					clusterC = num / Cn2;
				}
				else
				{
					clusterC = 0;
				}

				// 将ID与其簇系数对应存到相应的list中
				if (IdList.contains(ID))
				{
					clusterCList.set(IdList.indexOf(ID), clusterC);
					degreeList.set(IdList.indexOf(ID), IDdegree);

				}
				else
				{
					IdList.add(IdList.size(), ID);
					clusterCList.add(clusterCList.size(), clusterC);
					degreeList.add(degreeList.size(), IDdegree);
				}

			}
		}
		// 计算平均簇系数
		double sum = 0;
		double ave = 0;
		for (int i = 0; i < clusterCList.size(); i++)
		{
			sum += (Double) clusterCList.get(i);
		}
		ave = sum / clusterCList.size();

		// 把list写入txt文件
		WriteListToTxt(IdList, clusterCList, degreeList, txtName);
		long end = new Date().getTime();
		System.out.println("为null的数量：" + x + "  |为0的数量：" + y + "  |有值的数量:" + z
				+ "   |耗时(s)：" + (end - start) / 1000 + "   |平均簇系数： " + ave);

	}

	@SuppressWarnings("deprecation")
	public void MongoIndex(DBCollection userData) throws IOException
	{
		// DBCollection userData = getMongoColl("UserData");
		long start = new Date().getTime();
		DBCursor cursor = userData.find().batchSize(100);
		// 下面开始建立索引
		Directory directory = FSDirectory.open(path);
		Analyzer analyzer = new IKAnalyzer();
		boolean create = true;
		IndexWriter indexWriter = new IndexWriter(directory, analyzer, create,
				MaxFieldLength.LIMITED);
		int count = 0;
		while (cursor.hasNext())// && count < 800000)
		{
			BasicDBObject curs = (BasicDBObject) cursor.next();
			Document doc = new Document();

			List follows = (List) curs.get("Follows");
			if (follows == null)
			{
				// x++;
			}
			else if (follows.size() == 0)
			{
				// y++;
			}
			else
			{
				String strFollows = getStrFollows(follows);
				System.out.print(count + " ");
				System.out.println(curs);
				Field idField = new Field("ID", curs.get("ID").toString(),
						Store.YES, Index.ANALYZED);
				Field followsField = new Field("Follows", strFollows,
						Store.YES, Index.ANALYZED);
				doc.add(idField);
				doc.add(followsField);
				indexWriter.addDocument(doc);
				count++;
				// System.out.println(curs);
			}
		}

		indexWriter.optimize();
		indexWriter.close();
		long end = new Date().getTime();
		System.out.println(cursor.count());
		System.out.println("The Indexing Time(ms): " + (end - start));
	}

	// 通过搜索得到某一ID的Follows
	public List getFollowsOfID(String keyword) throws IOException,
			ParseException
	{
		List follosList = new ArrayList<Object>();
		// 读取索引阶段
		Directory dir = FSDirectory.open(path);
		IndexReader iReader = IndexReader.open(dir, true);
		IndexSearcher iSearcher = new IndexSearcher(iReader);
		// @SuppressWarnings("deprecation")
		// IndexSearcher inSearcher = new IndexSearcher(dir, true);
		// 创建query阶段
		Analyzer analyzer = new IKAnalyzer();
		QueryParser qp = new QueryParser(Version.LUCENE_35, "ID", analyzer);
		long start = new Date().getTime();
		Query query = qp.parse(keyword);
		// 获取结果阶段
		TopDocs docs = iSearcher.search(query, 5);
		ScoreDoc[] sd = docs.scoreDocs;
		long end = new Date().getTime();

		if (docs.totalHits == 0)
		{
			return null;
		}
		else
		{
			for (int i = 0; i < sd.length; i++)
			{
				Document doc = iSearcher.doc(sd[i].doc);
				System.out.println(sd[i].toString());
				System.out.println(doc.get("ID"));
				System.out.println(doc.get("Follows"));
				String[] follows = doc.get("Follows").split(" ");
				// String[] follows = doc.get("Follows").split(",");
				for (int j = 0; j < follows.length; j++)
				{
					follosList.add(follosList.size(), follows[j]);
					// System.out.println(follows[j]);
				}
				System.out
						.println("----------------------------------------------------");
			}
		}

		System.out.println("搜索：" + query + "  命中： " + docs.totalHits
				+ "条。  Searching time(ms): " + (end - start));

		iReader.close();
		iSearcher.close();
		return follosList;
	}

	public static String getStrFollows(List follows)
	{
		String strfollows = "";
		for (int i = 0; i < follows.size(); i++)
		{
			strfollows += follows.get(i) + " ";

		}
		return strfollows;

	}

	public static DBCollection getMongoColl(String coll)
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

	public static DBCollection getMongoColl(String host, String DBName,
			String username, String password, String collectionName)
			throws UnknownHostException
	{
		// Mongo mg = new Mongo(host, 27017);

		// for (String name : mg.getDatabaseNames())
		// {
		// System.out.println("dbName: " + name);
		// }
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.autoConnectRetry(true);
		builder.socketKeepAlive(true);
		builder.connectTimeout(15000);
		builder.socketTimeout(0);
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
		List<DBObject> indexes = collection.getIndexInfo();
		for (DBObject i : indexes)
		{
			System.out.println("Indexes info of the collection:");
			System.out.println(i);
		}
		return collection;

	}

	public static int getCnk(int n, int k)
	{
		int fenzi = 1;
		int fenmu = 1;
		for (int i = n - k + 1; i <= n; i++)
		{
			String s = Integer.toString(i);
			BigInteger stobig = new BigInteger(s);
			fenzi *= i;
		}
		for (int j = 1; j <= k; j++)
		{
			String ss = Integer.toString(j);
			BigInteger stobig2 = new BigInteger(ss);
			fenmu *= j;
		}
		int result = fenzi / fenmu;
		return result;
	}

	public static void WriteListToTxt(List list1, List list2, List list3,
			String txtName) throws IOException
	{
		FileWriter fw = new FileWriter(txtName);
		int LiSize = list1.size();
		if (list2 == null && list3 == null)
		{

			for (int i = 0; i < LiSize; i++)
			{
				String s1 = list1.get(i).toString();

				fw.write(s1, 0, s1.length());
				fw.write("\n");
			}
		}
		else if (list3 == null)
		{
			for (int i = 0; i < LiSize; i++)
			{
				String s1 = list1.get(i).toString() + " ";
				String s2 = list2.get(i).toString();

				fw.write(s1, 0, s1.length());
				fw.write(s2, 0, s2.length());
				fw.write("\n");
			}
		}
		else
		{
			for (int i = 0; i < LiSize; i++)
			{
				String s1 = list1.get(i).toString() + " ";
				String s2 = list2.get(i).toString() + " ";
				String s3 = list3.get(i).toString();

				fw.write(s1, 0, s1.length());
				fw.write(s2, 0, s2.length());
				fw.write(s3, 0, s3.length());
				fw.write("\n");
			}
		}

		fw.flush();
		fw.close();
	}
}
