

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.hydromatic.linq4j.Linq4j;
import Clusters.LeaderCluster;
import Config.Conf;
import Core.IWeiboAPIService;
import Core.RelationType;
import Core.WeiboServiceManager;
import Core.WeiboType;
import Data.IPositionComputeable;
import Data.IRelationComputeable;
import Data.LinkedPath;
import Data.Node;
import Datas.UserData;
//import Logs.XLogSys;
import PositionProcess.NetworkProcessView;
import PositionProcess.PahtfilterFactory;
import PositionProcess.PathFilterMethod;
import Process.UserRelationCalcultor;
import Tool.TextOperation;
import Data.RelationMatrix;

 

/**
 * Hello world!
 * 
 */
public class App
{
    public static void main(String[] args) throws Exception
    {
	//XLogSys.Instance().Info("haha");
	String username="大漠飞刀客";
    
    	
    	
  
 //   String username="BYR_米米";
	
	IWeiboAPIService service = WeiboServiceManager.Instance().Get(
		WeiboType.Sina);
	List<UserData> userDatas = service.GetRelations(RelationType.Friend,
		username, 2000); // 央视新闻
	System.out.print("总点数："+userDatas.size());
	//userDatas.get(1).get
	
	
	UserData SelfData=service.GetUserData(username);
	
	userDatas.add(SelfData);
	
	List<IRelationComputeable> userdata=Linq4j.ofType(userDatas,
			IRelationComputeable.class).toList();
	
//	List<IRelationComputeable> userdata=Linq4j.ofType(userDatas,
//			IRelationComputeable.class).toList();
	
//	Node selfNode=Linq4j.ofType(SelfData,
//			IRelationComputeable.class);
//	
//	IRelationComputeable node=Linq4j.ofType(SelfData, IRelationComputeable.class);
	
	IRelationComputeable node=userdata.get(userdata.size()-1);
	
	UserRelationCalcultor calcultor = new UserRelationCalcultor();
	calcultor.WeiboAPIService = service;
	calcultor.InitProcess();
	calcultor.setDataSource(userdata.subList(0, userdata.size()-1));
	calcultor.DataProcess();
	
	
	RelationMatrix resultMatrix=calcultor.getRelationMatrix();
	
	

	
	
	int nodenumber=resultMatrix.getSize();
	int[][] Input=new int[nodenumber][nodenumber];
//	for()
//	int 1number=0;
	
	for(int i=0;i<nodenumber;i++)
	{
		for(int j=i;j<nodenumber;j++)
		{
			Input[i][j]=resultMatrix.IsLinked(i, j)?1:0;
			if(resultMatrix.IsLinked(i, j))
			{
				System.out.print("("+i+","+j+")");
			}
				
		}
	}
	
//	print(username+".txt",Input);
//	writeFile(username+"name"+".txt",userDatas);
	
	
	
	
	
	LeaderCluster cluster = new LeaderCluster();
	cluster.InitProcess();
	cluster.setRelation(calcultor);
	cluster.DataProcess();
	
	
	Map<Integer,List<Integer>> clusterresult=cluster.getClusterResult();
	List<IPositionComputeable> Nodesresult=Linq4j.ofType(cluster.getNodes(),
			IPositionComputeable.class).toList();
	RelationMatrix Matrixresult=calcultor.getRelationMatrix();
	
	PahtfilterFactory pathfilterFactory=new PahtfilterFactory(PathFilterMethod.ClusterCenter);
	
	Matrixresult=pathfilterFactory.selectPathMethod(clusterresult, Nodesresult, Matrixresult);
	
	
	
	
	NetworkProcessView view = new NetworkProcessView();
	view.InitProcess();
	view.nodes=Nodesresult;
	view.matrix=Matrixresult;
	
//	view.nodes = Linq4j.ofType(cluster.getNodes(),
//		IPositionComputeable.class).toList();
//	view.matrix = calcultor.getRelationMatrix();
	
	view.matrix.Export("relationText.txt");
	view.DataProcess();
	
	GephiFactory gephi=new GephiFactory();
	List<Node> nodes = Linq4j.asEnumerable(view.nodes)
			.ofType(Node.class).toList();
	gephi.GephiOutput(nodes, view.matrix,username);
	
	
	
//	ObjectMapper mapper = new ObjectMapper();
//	List<Node> nodes = Linq4j.asEnumerable(cluster.getNodes())
//		.ofType(Node.class).toList();
//	List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();
//	List<Map<String, Object>> pathList = new ArrayList<Map<String, Object>>();
//	for (Node userData : nodes)
//	{
//	    nodeList.add(userData.DictSerialize());
//	}
//
//	for (LinkedPath path : view.getAllPath())
//	{
//	    pathList.add(path.DictSerialize());
//	}
//	
//	
//	
//	Map<String, Object> map=new HashMap<String, Object>();
//	map.put("Nodes", nodeList);
//	map.put("Paths", pathList);
//	
//	try
//	{
//	    mapper.writeValue(new File("hallo.txt"), map);
//	} 
//	catch (JsonGenerationException e)
//	{
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
//	catch (JsonMappingException e)
//	{
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
//	catch (IOException e)
//	{
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}

	// List<StatusData> statusDatas= service.GetRepostStatus(statusId, 200);
	System.out.println("Hello World!");
    }
    
	public static  void print(String filePath,int MatrixRelation[][]) throws Exception{
	//	FileWriter output=new FileWriter(new File(filePath),"UTF-8");
		OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
	//	FileWriter output=new FileWriter(new File(output.toString()),false);
		
		for(int i=0;i<MatrixRelation.length;i++){
			for(int j=i;j<MatrixRelation.length;j++){
				output.write(Integer.toString(MatrixRelation[i][j]));
				output.append(" ");
			}
			//output.append("\n");
		}
		output.flush();
		output.close();
	}
	
	public static  void printUsername(String filePath,List<UserData> usercollection) throws Exception{
		//FileWriter output=new FileWriter(new File(filePath));
		OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");
	//	FileWriter output=new FileWriter(new File(output.toString()),false);
		System.out.println("微博名称：");
		for(int i=0;i<usercollection.size();i++){
			
				output.write(usercollection.get(i).getName());
				System.out.println(i+" "+usercollection.get(i).getName());
				output.append("\n");
			
			//output.append("\n");
		}
		output.flush();
		output.close();
	}
	
//    /**  
//     * 读取文件内容  
//     *  
//     * @param filePathAndName  
//     *            String 如 c:\\1.txt 绝对路径  
//     * @return boolean  
//     */   
//   public static String readFile(String filePathAndName) {   
//       String fileContent = "";   
//       try {    
//           File f = new File(filePathAndName);   
//           if(f.isFile()&&f.exists()){   
//               InputStreamReader read = new InputStreamReader(new FileInputStream(f),"UTF-8");   
//               BufferedReader reader=new BufferedReader(read);   
//               String line;   
//               while ((line = reader.readLine()) != null) {   
//                   fileContent += line;   
//               }     
//               read.close();   
//           }   
//       } catch (Exception e) {   
//           System.out.println("读取文件内容操作出错");   
//           e.printStackTrace();   
//       }   
//       return fileContent;   
//   }   
//   
//   
//   /**  
//    * 写入文件  
//    *  
//    * @param filePathAndName  
//    *            String 如 c:\\1.txt 绝对路径  
//    */   
//  public static void writeFile(String filePathAndName, List<UserData> usercollection) {   
//      try {   
//          File f = new File(filePathAndName);   
//          if (!f.exists()) {   
//              f.createNewFile();   
//          }   
//          OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f),"GB2312");   
//          BufferedWriter writer=new BufferedWriter(write);
//          
//          
//          for(int i=0;i<usercollection.size()-1;i++){
//  			
//        	  writer.write(i+" "+usercollection.get(i).getName());
//				System.out.println(i+" "+usercollection.get(i).getName());
//				writer.append("\n");
//			
//			//output.append("\n");
//		}
//          writer.flush();
//          writer.close();
//          
//          
////          writer.write(fileContent);
////          writer.close();
//      
//      } catch (Exception e) {   
//          System.out.println("写文件内容操作出错");   
//          e.printStackTrace();   
//      }   
//  }   
  
  
   
	
}
