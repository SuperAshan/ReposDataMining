package WordSegment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.chenlb.mmseg4j.*;

import AlgorithmInteractive.IWordSegment;
import AlgorithmManagement.AbstractProcessMethod;

public class MMSegMethod extends AbstractProcessMethod implements IWordSegment
{

    private ComplexSeg seg;

    @Override
    public boolean InitProcess()
    {
	File file = new File("/home/hadoop/Develop/SVN/ITTC.DataMining.Process/SegwordDict");// 词典的目录
	Dictionary dic = Dictionary.getInstance(file);// 建立词典实例，与比较老的版本中不相同。不能直接new。
	 
	seg = new ComplexSeg(dic);
	return true;

    }

    public List<String> GetSegment(String source)
    {
	List<String> lists = new ArrayList<String>();
	StringReader reader = new StringReader(source);
	MMSeg mmSeg = new MMSeg(reader, seg);
	Seg seg = null;

	Word word = null;
	try
	{
	    while ((word = mmSeg.next()) != null)
	    { // 未做重复判断
		String temp = word.getString();
		lists.add(temp);
	    }
	} catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return lists;
    }

    public List<String> GetFileSegment(File path) throws IOException
    {
	List<String> list = new ArrayList<String>();
	File[] files = path.listFiles();
	for (File f : files)
	{
	    FileReader reader = new FileReader(f);
	    File file = new File("D:/Search/dic");// 词典的目录
	    Dictionary dic = Dictionary.getInstance(file);// 建立词典实例，与比较老的版本中不相同。不能直接new。
	    Seg seg = null;
	    seg = new ComplexSeg(dic);
	    MMSeg mmSeg = new MMSeg(reader, seg);
	    Word word = null;
	    while ((word = mmSeg.next()) != null)
	    { // 未做重复判断
		String temp = word.getString();
		list.add(temp);
	    }
	}
	return list;
    }

}
