package Config;

import java.io.File;

public class Conf {
	public static String GetDataDir() {
	       String p=Conf.class.getResource("").getFile();
	       File f=new File(p);
	       String path=f.getParentFile().getParentFile().getParent();
	       path+="/data";
	       return path;
		  
		}
	
	public static String GetGraphDir() {
	       String p=Conf.class.getResource("").getFile();
	       File f=new File(p);
	       String path=f.getParentFile().getParentFile().getParent();
	       path+="/Graph";
	       return path;
		  
		}
	
	public static String GetRootDir()
	{
		String p=Conf.class.getResource("").getFile();
	    File f=new File(p);
	    String path=f.getParentFile().getParentFile().getParent();
	    return path;

		
	}

}