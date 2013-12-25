package Files;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

 
public class XMLHelper
{
 
	public static void ObjectXmlEncoder(Object obj, String fileName)

	{
		// 鍒涘缓杈撳嚭鏂囦欢
		File fo = new File(fileName);
		// 鏂囦欢涓嶅瓨鍦�灏卞垱寤鸿鏂囦欢
		if (!fo.exists())
		{
			// 鍏堝垱寤烘枃浠剁殑鐩綍
			String path = fileName.substring(0, fileName.lastIndexOf('.'));
			File pFile = new File(path);
			pFile.mkdirs();
		}
		try
		{
			// 鍒涘缓鏂囦欢杈撳嚭娴�			
			FileOutputStream fos = new FileOutputStream(fo);
			// 鍒涘缓XML鏂囦欢瀵硅薄杈撳嚭绫诲疄渚�		
			XMLEncoder encoder = new XMLEncoder(fos);
			// 瀵硅薄搴忓垪鍖栬緭鍑哄埌XML鏂囦欢
			encoder.writeObject(obj);
			encoder.flush();
			// 鍏抽棴搴忓垪鍖栧伐鍏�			encoder.close();
			// 鍏抽棴杈撳嚭娴�			fos.close();
		} catch (Exception e)
		{
			// TODO: handle exception
		}

	}

 
	public static List ObjectXmlDecoder(String objSource)
			throws FileNotFoundException, IOException, Exception
	{
		List objList = new ArrayList();
		File fin = new File(objSource);
		FileInputStream fis = new FileInputStream(fin);
		XMLDecoder decoder = new XMLDecoder(fis);
		Object obj = null;
		try
		{
			while ((obj = decoder.readObject()) != null)
			{
				objList.add(obj);
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
		}
		fis.close();
		decoder.close();
		return objList;
	}
}