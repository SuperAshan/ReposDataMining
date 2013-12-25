package Project;

import java.util.HashMap;

import Data.IDictionarySerializable;
import Data.IRelationComputeable;
import Data.ISegwordable;

public class ProjectInfo implements IRelationComputeable, ISegwordable,
	IDictionarySerializable
{
    private static final String Person = null;

    private java.util.Date privateEndTime = new java.util.Date(0);

    public final java.util.Date getEndTime()
    {
	return privateEndTime;
    }

    public final void setEndTime(java.util.Date value)
    {
	privateEndTime = value;
    }

    public final String getKey()
    {
	return this.getName();
    }

    public final void setKey(String value)
    {
	this.setName(value);
    }

    private float privateMoney;

    public final float getMoney()
    {
	return privateMoney;
    }

    public final void setMoney(float value)
    {
	privateMoney = value;
    }

    private String privateName;

    public final String getName()
    {
	return privateName;
    }

    public final void setName(String value)
    {
	privateName = value;
    }

    private String privateNumber;

    public final String getNumber()
    {
	return privateNumber;
    }

    public final void setNumber(String value)
    {
	privateNumber = value;
    }

    private String privatePerson;

    public final String getPerson()
    {
	return privatePerson;
    }

    public final void setPerson(String value)
    {
	privatePerson = value;
    }

    public final Object getRealBindingData()
    {
	return this;
    }

    /**
     * 保存的分词位置
     */
    private java.util.ArrayList<String> privateSegName;

    public final java.util.ArrayList<String> getSegName()
    {
	return privateSegName;
    }

    public final void setSegName(java.util.ArrayList<String> value)
    {
	privateSegName = value;
    }

    private java.util.Date privateStartTime = new java.util.Date(0);

    public final java.util.Date getStartTime()
    {
	return privateStartTime;
    }

    public final void setStartTime(java.util.Date value)
    {
	privateStartTime = value;
    }

    private String privateUnit;

    public final String getUnit()
    {
	return privateUnit;
    }

    public final void setUnit(String value)
    {
	privateUnit = value;
    }

    public final String getPosition()
    {
	return this.getUnit();
    }

    public final int CompareTo(Object obj)
    {
	// C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit
	// typing in Java:
	ProjectInfo rc = (ProjectInfo) ((obj instanceof ProjectInfo) ? obj
		: null);
	if (rc == null)
	{
	    return -1;
	}
	return this.getNumber().compareTo(rc.getNumber());
    }

    public final Boolean Contains(String data)
    {
	return this.getName().contains(data) || this.getNumber().contains(data)
		|| this.getPerson().contains(data)
		|| this.getUnit().contains(data);
    }

    public final void DictDeserialize(java.util.HashMap<String, Object> dicts)
    {
	this.setNumber((String) dicts.get("Number"));
	this.setName((String) dicts.get("Name"));
	this.setPerson((String) dicts.get("Person"));
	this.setUnit((String) dicts.get("Unit"));
	this.setNumber((String) dicts.get("Number"));
	this.setStartTime((java.util.Date) dicts.get("StartYear"));
	this.setEndTime((java.util.Date) dicts.get("EndYear"));

	this.setMoney(Float.parseFloat((String) dicts.get("Money")));
    }

    public final java.util.HashMap<String, Object> DictSerialize()
    {
	java.util.HashMap<String, Object> dict = new java.util.HashMap<String, Object>();

	dict.put("Number", this.getNumber());
	dict.put("Name", this.getName());
	dict.put("Person", this.getPerson());
	dict.put("Unit", this.getUnit());
	dict.put("StartYear", this.getStartTime());
	dict.put("EndYear", this.getEndTime());
	dict.put("Money", this.getMoney());
	return dict;
    }

    public final double GetRelation(IRelationComputeable r1,
	    IRelationComputeable r2)
    {

	ProjectInfo rc1 = (ProjectInfo) ((r1 instanceof ProjectInfo) ? r1
		: null);

	ProjectInfo rc2 = (ProjectInfo) ((r2 instanceof ProjectInfo) ? r2
		: null);
	if (rc1 == null)
	{
	    return 0;
	}
	double r = 0.0;
	// 编号
	String[] num1 = rc1.getNumber().split("[/]", -1);
	char[] num1char = num1[1].toCharArray();
	String[] num2 = rc2.getNumber().split("[/]", -1);
	char[] num2char = num2[1].toCharArray();
	int num;
	if ((num1char.length) > (num2char.length))
	{
	    num = num2char.length;
	} else
	{
	    num = num1char.length;
	}
	for (int i = 0; i < num; i++)
	{
	    if (num1char[i] == num2char[i])
	    {
		r += 20.0;
	    } else
	    {
		break;
	    }
	}
	// 单位
	if (rc1.getUnit() == rc2.getUnit())
	{
	    r += 20.0;
	}
	// 负责人
	if (rc1.getPerson() == rc2.getPerson())
	{
	    r += 50.0;
	}

	r /= 100.0;
	// r = num / sum;
	return r;
    }

    public final String GetOriginString()
    {
	return this.getName();
    }

    @Override
    public int compareTo(Object o)
    {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public double getWeight()
    {
	// TODO Auto-generated method stub
	return 0;
    }

}
