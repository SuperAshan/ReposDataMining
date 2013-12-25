/**
 * TODO
 */
package EmailClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

/**
 * 2013年10月24日 by @author weitao
 * TODO
 */
public class EmailDemo
{

	/**
	 * main
	 * TODO   测试发送邮件
	 * @param args
	 * @throws MessagingException 
	 */
	public static void main(String[] args) throws MessagingException
	{
		// TODO Auto-generated method stub
//		String filedir="E:\\dataminingworkplace\\ITTCDataMingJava\\data\\NetWorkMeasurement";
//		File file=new File(filedir);
//		File[] filelist=file.listFiles();
//		List<String> attachmentList=new ArrayList<>();
//		for(int i=0;i<filelist.length;i++)
//		{
//			attachmentList.add(filelist[i].getAbsolutePath());
//		}
		EMailInstance instance=new EMailInstance();
		instance.setHead("测试");
		instance.setContentFromText("bug调通了");
	//	instance.setAttachment(attachmentList);
		String[] receive_addresslistStrings={"ashan_2012@126.com","827357393@qq.com","zhcheng1021@qq.com"};
		EMailProcess eProcess=new EMailProcess(instance);
		
		eProcess.SendEmail(receive_addresslistStrings);
		System.out.println("发送完毕！");

	}

}
