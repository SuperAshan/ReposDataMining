/**
 * TODO
 */
package EmailClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;


/**
 * 2013年10月23日 by @author weitao TODO 一封邮件样例
 */
public class EMailInstance
{
	/**
	 * 判断邮件是否带附件
	 */
	public Boolean attachmentFlag = false;
	/**
	 * 邮件内容
	 */
	private String content = "";
	/**
	 * 邮件附件
	 */
	private Multipart Attachment=new Multipart()
	{
		
		@Override
		public void writeTo(OutputStream arg0) throws IOException,
				MessagingException
		{
			// TODO Auto-generated method stub
			
		}
	};
	
	/**
	 *邮件标题
	 */
	private String head="";

	/**
	 * setAttachment TODO 给邮件添加附件
	 * 
	 * @param filelist
	 *            以文件列表的形式给邮件添加附件
	 * @throws MessagingException
	 */
	public void setAttachment(List<String> filelist) throws MessagingException
	{
		for (int i = 0; i < filelist.size(); i++)
		{
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filelist.get(i));
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filelist.get(i));
			this.Attachment.addBodyPart(messageBodyPart);

		}
		this.attachmentFlag = true;
	}

	/**
	 * setAttachment TODO 给邮件添加附件
	 * 
	 * @param text
	 *            以文本的形式添加附件
	 * @throws MessagingException
	 */
	public void setAttachment(String text) throws MessagingException
	{
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(text);
		this.Attachment.addBodyPart(messageBodyPart);
		this.attachmentFlag = true;
	}
	
	/**
	 * getAttachment
	 * TODO 获得邮件的附件
	 * @return  邮件附件
	 */
	public Multipart getAttachment()
	{
		return this.Attachment;
	}

	/**
	 * setContentFromFile TODO 给邮件添加内容
	 * 
	 * @param filename
	 *            文件的路径
	 */
	public void setContentFromFile(String filename)
	{
		this.content = readFile(filename);
	}

	/**
	 * setContentFromText TODO 给邮件添加内容
	 * 
	 * @param contentString
	 *            以文本的形式给文件添加内容
	 */
	public void setContentFromText(String contentString)
	{
		this.content = contentString;
	}
	
	/**
	 * getContent
	 * TODO  返回邮件的内容
	 * @return  邮件内容
	 */
	public String getContent()
	{
		return this.content;
	}

	/**
	 * setHead TODO 给邮件添加主题
	 * 
	 * @param head
	 *            邮件主题
	 */
	public void setHead(String headString)
	{
		this.head=headString;
	}
	
	/**
	 * getHead
	 * TODO  返回邮件主题
	 * @return  邮件主题
	 */
	public String getHead()
	{
		return this.head;
	}
	
	/**  
     * 读取文件内容  
     *  
     * @param filePathAndName  
     *            String 如 c:\\1.txt 绝对路径  
     * @return boolean  
     */   
   public  String readFile(String filePathAndName) {   
       String fileContent = "";   
       try {    
           File f = new File(filePathAndName);   
           if(f.isFile()&&f.exists()){   
               InputStreamReader read = new InputStreamReader(new FileInputStream(f),"UTF-8");   
               BufferedReader reader=new BufferedReader(read);   
               String line;   
               while ((line = reader.readLine()) != null) {   
                   fileContent += line;   
               }     
               read.close();   
           }   
       } catch (Exception e) {   
           System.out.println("读取文件内容操作出错");   
           e.printStackTrace();   
       }   
       return fileContent;   
   }   
}
