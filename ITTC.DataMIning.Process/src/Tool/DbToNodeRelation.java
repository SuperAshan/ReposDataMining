package Tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.solr.update.processor.MinFieldValueUpdateProcessorFactory;
import org.omg.CORBA.PUBLIC_MEMBER;

import Config.Conf;
import Core.IWeiboAPIService;
import Core.RelationType;
import Core.WeiboServiceManager;
import Core.WeiboType;
import Datas.UserData;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class DbToNodeRelation {
	private final static String host = "10.108.201.165";
	private final static String dbName = "datamining";
	private final static String username = "ittcdb";
	private final static String password = "ittc706706";
	
//	private final static String haidianUsers = "HaidianUsersNew";
	private String dbname;
	private HashMap<Integer,TreeSet<Integer>> edgeMap;
	private int userNumber=Integer.MAX_VALUE;
	private String usernameString=null;
	private String collectionName;
	private DBCollection userDataCollection;
	private List<String> userIDList;

	
    
    /**
     * @param dbname 数据库名字
     */
    public DbToNodeRelation(String collectionName)
	{
		this.collectionName=collectionName;
	}
	
	/**
	 * @param dbname 数据库名字
	 * @param mount  从数据库取出的用户数
	 */
	public DbToNodeRelation(String collectionName,int mount)
	{
		this.collectionName=collectionName;
		this.userNumber=mount;
	}
	
	/**
	 * @param dbname  数据库名字
	 * @param username 从数据库中取出的用户个人网络
	 */
	public DbToNodeRelation(String collectionName,String username)
	{
		this.collectionName=collectionName;
		this.usernameString=username;
	}

	/**
	 *  连接Mongodb指定的Collection，读取数据的起始位置
	 */
	public void Initialize()
	{
		this.userIDList=new ArrayList<String>();
		this.edgeMap=new HashMap<Integer, TreeSet<Integer>>();
//		try
//		{
////			if(userNumber<0){
//			this.userDataCollection = MongoDBClient
//					.getMongoDBColl(host, dbName, username, password,
//							collectionName);
////			}
////			else {
////				this.userDataCollection = MongoDBClient
////						.getMongoDBColl(host, dbName, username, password,
////								collectionName);
////				this.userDataCollection=this.userDataCollection.getCollection(Integer.toString(userNumber));
////			}
//		} 
//	    catch (UnknownHostException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	/**
	 * @throws IOException
	 */
	/**
	 * @throws IOException
	 */
	/**
	 * @throws IOException
	 */
	public void DataProcess() throws IOException
	{
		DBCursor cursor=this.userDataCollection.find().batchSize(60);
		int userindex=1;
		
		String usernumberString=this.collectionName;
		if(this.userNumber==Integer.MAX_VALUE)
		{
			usernumberString+="whole";
		}
		else {
			usernumberString+=Integer.toString(this.userNumber);
		}
		String UserIdFilePath=Conf.GetDataDir()+"\\NetWorkMeasurement\\UserIDCollection"+usernumberString+".txt";
		File UserIDFile = new File(UserIdFilePath);   
        if (!UserIDFile.exists()) {   
        	UserIDFile.createNewFile();   
        }   
        OutputStreamWriter UserIDwrite = new OutputStreamWriter(new FileOutputStream(UserIDFile),"GB2312");   
        BufferedWriter UserIDwriter=new BufferedWriter(UserIDwrite);
        int curnumber=0;
        int usermount=0;
       
		while (cursor.hasNext() && usermount<this.userNumber)
		{
			DBObject cur = cursor.next();
		
			if((Long)cur.get("ID")==-1)
			{
				continue;
			}
			String ID = cur.get("ID").toString();
			userIDList.add(ID);
			UserIDwriter.write(userindex+" "+ID);
			UserIDwriter.append("\n");
			UserIDwriter.flush();
			userindex++;
			usermount++;
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            System.out.println(df.format(new Date(System.currentTimeMillis()))+"\t"+Integer.toString(userindex));// new Date()为获取当前系统时间
	
		}
		UserIDwriter.close();
		System.out.println("用户名列表生成完毕！开始生成邻接表文件");
		
		
		String AdjacementHaidianFilePath=Conf.GetDataDir()+"\\NetWorkMeasurement\\AdjacementHaidianFile"+usernumberString+".txt";
		File AdjacementHaidianFile = new File(AdjacementHaidianFilePath);   
        if (!AdjacementHaidianFile.exists()) {   
        	AdjacementHaidianFile.createNewFile();   
        }   
        OutputStreamWriter AdjacementHaidianwrite = new OutputStreamWriter(new FileOutputStream(AdjacementHaidianFile),"GB2312");   
        BufferedWriter AdjacementHaidianwriter=new BufferedWriter(AdjacementHaidianwrite);
		userindex=0;
		cursor=this.userDataCollection.find().batchSize(60);
		 usermount=0;
	       
		while (cursor.hasNext() && usermount<this.userNumber)
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.println(df.format(new Date(System.currentTimeMillis()))+"\t"+Integer.toString(userindex));// new Date()为获取当前系统时间
//			System.out.println(Integer.toString(userindex));
			DBObject cur = cursor.next();
			if((Long)cur.get("ID")==-1)
			{
				continue;
			}
			BasicDBList Follow = (BasicDBList)cur.get("Follows");
			TreeSet<Integer> IDCollectionIntegers=new TreeSet<Integer>();
			int count=Follow.size();
			for(int i=0;i<count;i++)
			{
				String Id=null;
	
			    Id=Follow.get(i).toString();
			    if(Id==null || Id.isEmpty())
			    {
			    	continue;
			    }
			    else {
			    	try {
						int index=userIDList.indexOf(Id);
						if(index<0)
							continue;
						else {
							IDCollectionIntegers.add(index);
						}
					} catch (Exception e2) {
						// TODO: handle exception
					}
					
				}
		
				
				
			}
			edgeMap.put(userindex, IDCollectionIntegers);
			String edgefinalindexString="";
//			int edgenumber=IDCollectionIntegers.size();
			Iterator iterator=IDCollectionIntegers.iterator();
			while(iterator.hasNext())
			{
				edgefinalindexString+=Integer.toString((Integer)iterator.next());
				edgefinalindexString+=" ";
			}
			AdjacementHaidianwriter.write(userindex+":"+edgefinalindexString);
			AdjacementHaidianwriter.append("\n");
			AdjacementHaidianwriter.flush();
			userindex++;
			usermount++;
		}
		System.out.println("邻接表生成完毕！");
		AdjacementHaidianwriter.close();
		
	}
	
	
	
	public void DataProcessConnected() throws IOException
	{
		DBCursor cursor=this.userDataCollection.find().batchSize(60);
		List<Integer> userIDindexIntegers=new ArrayList<Integer>();
		int userindex=0;
		
		String usernumberString=this.collectionName;
		if(this.userNumber==Integer.MAX_VALUE)
		{
			usernumberString+="whole";
		}
		else {
			usernumberString+=Integer.toString(this.userNumber);
		}
		String UserIdFilePath=Conf.GetDataDir()+"\\NetWorkMeasurement\\UserIDCollectionConnected"+usernumberString+".txt";
		File UserIDFile = new File(UserIdFilePath);   
        if (!UserIDFile.exists()) {   
        	UserIDFile.createNewFile();   
        }   
        OutputStreamWriter UserIDwrite = new OutputStreamWriter(new FileOutputStream(UserIDFile),"GB2312");   
        BufferedWriter UserIDwriter=new BufferedWriter(UserIDwrite);
        
        String AdjacementHaidianFilePath=Conf.GetDataDir()+"\\NetWorkMeasurement\\AdjacementHaidianFileConnected"+usernumberString+".txt";
		File AdjacementHaidianFile = new File(AdjacementHaidianFilePath);   
        if (!AdjacementHaidianFile.exists()) {   
        	AdjacementHaidianFile.createNewFile();   
        }   
        OutputStreamWriter AdjacementHaidianwrite = new OutputStreamWriter(new FileOutputStream(AdjacementHaidianFile),"GB2312");   
        BufferedWriter AdjacementHaidianwriter=new BufferedWriter(AdjacementHaidianwrite);
        int curnumber=0;
        int usermount=0;
        if(cursor.hasNext())
        {
        	DBObject cur = cursor.next();
			if((Long)cur.get("ID")==-1)
			{
				
			}
			else {
				String ID = cur.get("ID").toString();
				
				DBCursor curtem=this.userDataCollection.find(new BasicDBObject("ID",Long.parseLong(ID) )).batchSize(60);
				if(curtem.hasNext())
				{
					userIDList.add(ID);
					UserIDwriter.write(userindex+" "+ID);
					UserIDwriter.append("\n");
					UserIDwriter.flush();
					usermount++;
				}
				
				
			}
			
        }
        	
		while (usermount<this.userNumber)
		{
			String ID=userIDList.get(userindex);
			TreeSet<Integer> IDCollectionIntegers=new TreeSet<Integer>();
			DBCursor cur=this.userDataCollection.find(new BasicDBObject("ID",Long.parseLong(ID) )).batchSize(60);
			DBObject cursorDbObject=cur.next();  //***************
			
			
			BasicDBList Follow = (BasicDBList)cursorDbObject.get("Follows");
			int count=Math.min(Follow.size(), this.userNumber-usermount);
			for(int i=0;i<count;i++)
			{
				String Id=null;
				
			    Id=Follow.get(i).toString();
			    if(Id==null || Id.isEmpty())
			    {
			    	i=i-1;
			    	continue;
			    }
			    if(!userIDList.contains(Id))
			    {
			    	DBCursor curtem=this.userDataCollection.find(new BasicDBObject("ID",Long.parseLong(Id) )).batchSize(60);
					if(curtem.hasNext())
					{
						userIDList.add(Id);
					    UserIDwriter.write(userindex+" "+Id);
						UserIDwriter.append("\n");
						UserIDwriter.flush();
						IDCollectionIntegers.add(usermount);
						usermount++;
						System.out.println("usermount:"+Integer.toString(userindex));
					}
					else {
						 continue;
					}
			    	
//				   
			    }
			
			}
//			if(ID)
			edgeMap.put(userindex, IDCollectionIntegers);
			String edgefinalindexString="";
//			int edgenumber=IDCollectionIntegers.size();
			Iterator iterator=IDCollectionIntegers.iterator();
			while(iterator.hasNext())
			{
				edgefinalindexString+=Integer.toString((Integer)iterator.next());
				edgefinalindexString+=" ";
			}
			AdjacementHaidianwriter.write(userindex+":"+edgefinalindexString);
			AdjacementHaidianwriter.append("\n");
			AdjacementHaidianwriter.flush();
            System.out.println("userindex:"+Integer.toString(userindex));
		    userindex++;
		}
		UserIDwriter.close();
		for(int i=userindex;i<this.userNumber;i++)
		{
			String ID=userIDList.get(i);
			TreeSet<Integer> IDCollectionIntegers=new TreeSet<Integer>();
			DBCursor cur=this.userDataCollection.find(new BasicDBObject("ID",ID ));
			DBObject cursorDbObject=cur.next();
			BasicDBList Follow = (BasicDBList)cursorDbObject.get("Follows");
			int count=Follow.size();
			for(int j=0;j<count;j++)
			{
				String Id=null;
				
			    Id=Follow.get(i).toString();
			    int index=userIDList.indexOf(Id);
			    if(index<0)
			    {
			    }
			    else {
					IDCollectionIntegers.add(index);
				}
			}
			edgeMap.put(i, IDCollectionIntegers);
			String edgefinalindexString="";
//			int edgenumber=IDCollectionIntegers.size();
			Iterator iterator=IDCollectionIntegers.iterator();
			while(iterator.hasNext())
			{
				edgefinalindexString+=Integer.toString((Integer)iterator.next());
				edgefinalindexString+=" ";
			}
			AdjacementHaidianwriter.write(userindex+":"+edgefinalindexString);
			AdjacementHaidianwriter.append("\n");
			AdjacementHaidianwriter.flush();
			System.out.println("userindex:"+Integer.toString(userindex));
			userindex++;

		}
		AdjacementHaidianwriter.close();
		System.out.println("用户名列表生成完毕！开始生成邻接表文件");
		
	}
	
	
	
	public void DataProcessConnectedInternet() throws IOException
	{
		HashMap<Integer, TreeSet<Integer>> map=new HashMap<Integer, TreeSet<Integer>>();
		String usernameString="大漠飞刀客";
		IWeiboAPIService service = WeiboServiceManager.Instance().Get(
				WeiboType.Sina);
		UserData seedData=service.GetUserData(usernameString);
		List<Long> userIDindexIntegers=new ArrayList<Long>();
		int userindex=0;
		
		String usernumberString=this.collectionName;
		if(this.userNumber==Integer.MAX_VALUE)
		{
			usernumberString+="whole";
		}
		else {
			usernumberString+=Integer.toString(this.userNumber);
		}
		String UserIdFilePath=Conf.GetDataDir()+"\\NetWorkMeasurement\\UserIDCollectionConnected"+usernumberString+".txt";
		File UserIDFile = new File(UserIdFilePath);   
        if (!UserIDFile.exists()) {   
        	UserIDFile.createNewFile();   
        }   
        OutputStreamWriter UserIDwrite = new OutputStreamWriter(new FileOutputStream(UserIDFile),"GB2312");   
        BufferedWriter UserIDwriter=new BufferedWriter(UserIDwrite);
        
        String AdjacementHaidianFilePath=Conf.GetDataDir()+"\\NetWorkMeasurement\\AdjacementHaidianFileConnected"+usernumberString+".txt";
		File AdjacementHaidianFile = new File(AdjacementHaidianFilePath);   
        if (!AdjacementHaidianFile.exists()) {   
        	AdjacementHaidianFile.createNewFile();   
        }   
        OutputStreamWriter AdjacementHaidianwrite = new OutputStreamWriter(new FileOutputStream(AdjacementHaidianFile),"GB2312");   
        BufferedWriter AdjacementHaidianwriter=new BufferedWriter(AdjacementHaidianwrite);
        int curnumber=0;
        int usermount=0;
	
	    Long ID = seedData.getID();
			
	    userIDindexIntegers.add(ID);
		UserIDwriter.write(userindex+" "+Long.toString(ID));
		UserIDwriter.append("\n");
		UserIDwriter.flush();
		usermount++;
			
        	
		while (usermount<this.userNumber)
		{
			Long Id=userIDindexIntegers.get(userindex);
			List<Long> followDatas=service.GetRelationIDs(RelationType.Follows, Long.toString(Id), 1000);
//			List<Long> followDatas=seedData.getFollowIDs();
			TreeSet<Integer> IDCollectionIntegers=null;
			if(map.containsKey(userindex))
			{
				IDCollectionIntegers=map.get(userindex);
			}
			else {
				IDCollectionIntegers=new TreeSet<Integer>();
				map.put(userindex, IDCollectionIntegers);
			}
			
			int count=Math.min(followDatas.size(), this.userNumber-usermount);
			for(int i=0;i<count;i++)
			{
				Long id=null;
				
			    id=followDatas.get(i);
			  
			    if(!userIDindexIntegers.contains(id))
			    {
				        userIDindexIntegers.add(id);
					    UserIDwriter.write(usermount+" "+Long.toString(id));
						UserIDwriter.append("\n");
						UserIDwriter.flush();
						IDCollectionIntegers.add(usermount);
						TreeSet<Integer> set=new TreeSet();
						set.add(userindex);
						map.put(usermount, set);
						usermount++;
						System.out.println("usermount:"+Integer.toString(usermount));
			    }
					else 
					{
						 continue;
					}
			    	
//				   
			    }
			
			
//			if(ID)
			edgeMap.put(userindex, IDCollectionIntegers);
			String edgefinalindexString="";
//			int edgenumber=IDCollectionIntegers.size();
			Iterator iterator=IDCollectionIntegers.iterator();
			while(iterator.hasNext())
			{
				edgefinalindexString+=Integer.toString((Integer)iterator.next());
				edgefinalindexString+=" ";
			}
			AdjacementHaidianwriter.write(userindex+":"+edgefinalindexString);
			AdjacementHaidianwriter.append("\n");
			AdjacementHaidianwriter.flush();
            System.out.println("userindex:"+Integer.toString(userindex));
		    userindex++;
		    if(userindex>=usermount)
		    {
		    	System.out.println("关于大漠飞刀客的最大连通节点个数："+Integer.toString(usermount));
		    	break;
		    }
		}
		UserIDwriter.close();
		for(int i=userindex;i<usermount;i++)
		{
			long id=userIDindexIntegers.get(i);
			TreeSet<Integer> IDCollectionIntegers=null;
			if(map.containsKey(userindex))
			{
				IDCollectionIntegers=map.get(userindex);
			}
			else {
				IDCollectionIntegers=new TreeSet<Integer>();
				map.put(userindex, IDCollectionIntegers);
			}
			List<Long> followDatas=service.GetRelationIDs(RelationType.Follows, Long.toString(id), 1000);			
		    int count=followDatas.size();
			for(int j=0;j<count;j++)
			{
				
				
			    Long Id=followDatas.get(j);
			    int index=userIDindexIntegers.indexOf(Id);
			    if(index<0)
			    {
			    }
			    else {
			    	IDCollectionIntegers.add(index);
				}
			}
			edgeMap.put(i, IDCollectionIntegers);
			String edgefinalindexString="";
//			int edgenumber=IDCollectionIntegers.size();
			Iterator iterator=IDCollectionIntegers.iterator();
			while(iterator.hasNext())
			{
				edgefinalindexString+=Integer.toString((Integer)iterator.next());
				edgefinalindexString+=" ";
			}
			AdjacementHaidianwriter.write(userindex+":"+edgefinalindexString);
			AdjacementHaidianwriter.append("\n");
			AdjacementHaidianwriter.flush();
			System.out.println("userindex:"+Integer.toString(userindex));
			userindex++;

		}
		AdjacementHaidianwriter.close();
		System.out.println("用户名列表生成完毕！开始生成邻接表文件");
		
	}
	
	
	

}
