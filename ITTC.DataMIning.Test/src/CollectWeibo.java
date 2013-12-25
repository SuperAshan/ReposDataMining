import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import Config.Conf;
import Core.IWeiboAPIService;
import Core.WeiboServiceManager;
import Core.WeiboType;
import Datas.StatusData;
import Datas.UserData;


public class CollectWeibo {
	
	
	public static void main(String[] args) throws Exception
	{
	IWeiboAPIService service = WeiboServiceManager.Instance().Get(
			WeiboType.Sina);
	String UserIdFilePath=Conf.GetDataDir()+"\\UserCollection\\users2.txt";
	String WeiboUserNamePath=Conf.GetDataDir()+"\\OutPut\\weiboNameCollection100000.txt";
	String WeiboContentPath=Conf.GetDataDir()+"\\OutPut\\weibocontentCollection1000000.txt";
	
	try {    
		
		File UserNameFile = new File(WeiboUserNamePath);   
        if (!UserNameFile.exists()) {   
        	UserNameFile.createNewFile();   
        }   
        OutputStreamWriter UserNamewrite = new OutputStreamWriter(new FileOutputStream(UserNameFile),"GB2312");   
        BufferedWriter UserNamewriter=new BufferedWriter(UserNamewrite);
        
        File WeiboContentFile = new File(WeiboContentPath);   
        if (!UserNameFile.exists()) {   
        	UserNameFile.createNewFile();   
        }   
        OutputStreamWriter WeiboContentwrite = new OutputStreamWriter(new FileOutputStream(WeiboContentFile),"GB2312");   
        BufferedWriter WeiboContentwriter=new BufferedWriter(WeiboContentwrite);
		
        File userIdfile = new File(UserIdFilePath);   
        int weibonumber=1;
        int usernumber=1;
        if(userIdfile.isFile()&&userIdfile.exists()){   
            InputStreamReader read = new InputStreamReader(new FileInputStream(userIdfile),"UTF-8");   
            BufferedReader reader=new BufferedReader(read);   
            Date startDate=new Date(System.currentTimeMillis());
        //    Date endDate=new Date(System.currentTimeMillis());
           
            String line=" "; 
            int usertotalnumber=0;
            while (usernumber<100001) { 
            	int Iplimitednumber=service.GetCurrentCountInfo();
         //   	Date nowTime = new Date(System.currentTimeMillis());
            
         //       System.out.println(nowTime);//方法二：Date方式，输出现在时间
            	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println(df.format(new Date(System.currentTimeMillis()))+"\t"+"剩余IP次数："+Iplimitednumber);// new Date()为获取当前系统时间
          //  	System.out.println(nowTime+"\t"+"剩余IP次数："+Iplimitednumber);
            	if(Iplimitednumber<100)
            		Thread.sleep(1000*1800);
            	
            	line=reader.readLine();
            	String Id="";
            	if(line!=null)
            		Id=line.split("\t")[1];
            	try {
            		UserData user=service.GetUserDatabyId(Id);
                	List<StatusData> statusDataCollection=service.GetUserStatusById(Id, 20);
                	if(statusDataCollection.size()>10)
                	{
                		System.out.println("已经读取用户数目："+usernumber);
                		for(int i=0;i<10;i++)
                		{
                			System.out.println("微博数目："+weibonumber);
                			WeiboContentwriter.write(weibonumber+" "+statusDataCollection.get(i).getText());
                			WeiboContentwriter.append("\n");
                			WeiboContentwriter.flush();
                			weibonumber++;
                		}
                		
                		UserNamewriter.write(usernumber+" "+user.getName());
                		UserNamewriter.append("\n");
                		UserNamewriter.flush();
                		usernumber++;
                	}
                	
					
				} catch (Exception e) {
					// TODO: handle exception
				}
            	
            }  
            WeiboContentwriter.close();
            UserNamewriter.close();
            
            read.close();   
        }
    //    userIdfile.
     }   
     catch (Exception e) {   
        System.out.println("读取文件内容操作出错");   
        e.printStackTrace();   
    }   
	
	
	}
	

}
