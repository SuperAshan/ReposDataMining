package EmailClient;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 2013年10月23日 by @author weitao TODO 用来管理发送邮件和接受邮件的类
 */
public class EMailProcess
{
	
	private EMailInstance eMailInstance;
	
	public EMailProcess(EMailInstance EMailExample)
	{
		eMailInstance=new EMailInstance();
		eMailInstance=EMailExample;
		
	}

	public void SendEmail(String[] Receive_Address)
	{
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", EmailConfig.SmtpAddress);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);
		{
			try
			{
				InternetAddress from = new InternetAddress(   
						EmailConfig.send_Address);
				message.setFrom(from);
				
				String toList = getMailList(Receive_Address);
				 
				InternetAddress[] iaToList = new InternetAddress().parse(toList);
				 
				// 收件人
				message.setRecipients(Message.RecipientType.TO,iaToList);

				message.setSubject(eMailInstance.getHead());
				String content = eMailInstance.getContent();
				

				message.setContent(content, "text/html;charset=GBK");
//				message.setContent(eMailInstance.getAttachment());
				message.saveChanges();
				Transport transport = session.getTransport("smtp");
		
				 transport.connect(EmailConfig.SmtpAddress, EmailConfig.send_Username, EmailConfig.send_Password);
			

				 transport.sendMessage(message, message.getAllRecipients());
				 transport.close();
				 System.out.println("Successful!");
			} catch (MessagingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private String getMailList(String[] mailArray){
		 
	    StringBuffer toList = new StringBuffer();
	 
	    int length = mailArray.length;
	    if(mailArray!=null && length <2) {
	        toList.append(mailArray[0]);
	    } else {
	            for(int i=0;i<length;i++){
	                toList.append(mailArray[i]);
	                if(i!=(length-1)){
	                toList.append(",");
	            }
	        }
	     }
	 
	     return toList.toString();
	}
	

}
