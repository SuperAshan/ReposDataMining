//package Logs;
//
//import java.util.logging.Level;
//
////import com.sun.istack.internal.logging.Logger;
//
//public class XLogSys implements ILogSys
//{
////	static Logger logger;
//
//	private static XLogSys _Instance;
//
//	public static XLogSys Instance()
//	{
//		if (_Instance == null)
//			_Instance = new XLogSys();
////		logger = Logger.getLogger("classname", XLogSys.class);
//		return _Instance;
//	}
//
//	@Override
//	public void Debug(String message)
//	{
//		// TODO Auto-generated method stub
//		logger.log(Level.FINEST, (String) message);
//
//	}
//
//	@Override
//	public void Debug(String message, Exception exception)
//	{
//		// TODO Auto-generated method stub
//		logger.log(Level.FINEST, (String) message);
//
//	}
//
//	@Override
//	public void Error(String message)
//	{
//		// TODO Auto-generated method stub
//		logger.log(Level.WARNING, (String) message);
//	}
//
//	@Override
//	public void Error(String message, Exception exception)
//	{
//		// TODO Auto-generated method stub
//		logger.log(Level.WARNING, (String) message);
//
//	}
//
//	@Override
//	public void Info(String message)
//	{
//		// TODO Auto-generated method stub
//		logger.log(Level.INFO, (String) message);
//
//	}
//
//	@Override
//	public void Info(String message, Exception exception)
//	{
//		// TODO Auto-generated method stub
//		logger.log(Level.FINEST, (String) message);
//
//	}
//
//}
