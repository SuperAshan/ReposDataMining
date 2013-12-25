import java.util.List;

//import sun.launcher.resources.launcher;
import sun.misc.Sort;
//import Logs.XLogSys;
import SearchEngine.SolrEngine;
import WordSegment.MMSegMethod;

public class main
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		/*
		 * MongoService service = new MongoService(); //
		 * mongodb://localhost:27017 service.IPAddress = "192.168.2.109";
		 * service.DBName = "RehabilitationSystem"; try { service.ConnectDB();
		 * service.GetAllEntitys("UserTable");
		 * 
		 * } catch (Exception e) { // TODO: handle exception }
		 */

		/*
		 * RPCServer server = new RPCServer(); server.ServiceName = "Test"; Test
		 * test = new Test(); try { server.Begin(test); } catch (Exception e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 */

//		RPCClient client = new RPCClient();
//		client.IPAddress = "10.108.201.165";
//		client.Port = 1989;
//		IDataMIningService service2 = (IDataMIningService) client
//				.Connect(IDataMIningService.class); //
//
//		List list = new ArrayList();
//		Random random = new Random();
//		for (int i = 0; i < 500; i++)
//		{
//			Map m = new HashMap();
//			m.put("X", random.nextDouble() * 100);
//			m.put("Y", random.nextDouble() * 100);
//			m.put("Z", random.nextDouble() * 100);
//			m.put("Group", 1);
//			m.put("Key", String.valueOf(i));
//			list.add(m);
//			if (i % 100000 == 0)
//				System.out.println(i);
//		}
//		Map mainMap = new HashMap();
//		mainMap.put("Nodes", list);
//		mainMap.put("Paths", new ArrayList());
//
//		Long start = System.currentTimeMillis();
//		service2.addDataPoint(mainMap);
//		System.out.println(System.currentTimeMillis() - start);
//
//		DataMiningConfig.Instance().setAge(20);

		// int age = DataMiningConfig.Instance().getAge();
	    
	    
	    
	    
//		 XLogSys.Instance().Info("HEELO ITTCDataMining");
//		 System.out.println("getBookInfoByISBN");
//		 MMSegMethod mms=new MMSegMethod();
//		 mms.InitProcess();
//		   List<String> worStrings=null;
//                for (int i=0;i<10000;i++ )
//		{
//                    worStrings=  mms.GetSegment("今天公司中高层领导开会开了一天，结果给我们这些开发人员带来了一个晴天霹雳的消息。手上以及以后所有的项目全部都用java。可怜我们这些。net的程序员啊！.net还没学的牛逼又要开始转战java了。以后的日子该何去何从，是继续研究.net并且以短平快的方式学习java?还是转战开始深入研究Java？.net跟java真的有那么大的区别吗？不就是一种语言，一种编程工具罢了。");
//		}
//		
//
//		int a= worStrings.size();
	    
	    
	    
	    
	    
	    SolrEngine solr=new  SolrEngine();
	    solr.SearchResult("haha");
	  
	}
}
