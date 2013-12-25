package Config;

import java.util.LinkedList;
import java.util.Vector;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class DataMiningConfig extends XMLSerializer
{
	private static DataMiningConfig _Instance;

	public static DataMiningConfig Instance()
	{
		if (_Instance == null)
			_Instance = new DataMiningConfig();
		return _Instance;
	}

	private String _name;
	private String _sex;
	private LinkedList list = new LinkedList();
	private Vector vec = new Vector();
	int age;

	public void setAge(int num)
	{
		age = num;
	}

	public int getAge()
	{
		return age;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public void setSex(String sex)
	{
		_sex = sex;
	}

	public String getName()
	{
		return _name;
	}

	public String getSex()
	{
		return _sex;
	}

}
