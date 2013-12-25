package  Patent;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import Data.IDictionarySerializable;
import Data.IRelationComputeable;

 
public class PatentInfo implements IDictionarySerializable, IRelationComputeable
{
 
	/** 
	 发明人
	 
	*/
	private java.util.ArrayList<String> privateInventor;
	public final java.util.ArrayList<String> getInventor()
	{
		return privateInventor;
	}
	public final void setInventor(java.util.ArrayList<String> value)
	{
		privateInventor = value;
	}

	public final String getKey()
	{
		return this.getName();
	}
	public final void setKey(String value)
	{
		this.setName(value);
	}

	private String privatePatentID;
	public final String getPatentID()
	{
		return privatePatentID;
	}
	public final void setPatentID(String value)
	{
		privatePatentID = value;
	}

	public final Object getRealBindingData()
	{
		return this;
	}

	private String privateAbstractInfo;
	public final String getAbstractInfo()
	{
		return privateAbstractInfo;
	}
	public final void setAbstractInfo(String value)
	{
		privateAbstractInfo = value;
	}

	private String privateApplicant;
	public final String getApplicant()
	{
		return privateApplicant;
	}
	public final void setApplicant(String value)
	{
		privateApplicant = value;
	}

	private String privateClassID;
	public final String getClassID()
	{
		return privateClassID;
	}
	public final void setClassID(String value)
	{
		privateClassID = value;
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

	private java.util.Date privatePublicTime = new java.util.Date(0);
	public final java.util.Date getPublicTime()
	{
		return privatePublicTime;
	}
	public final void setPublicTime(java.util.Date value)
	{
		privatePublicTime = value;
	}
 
	public final int CompareTo(Object obj)
	{
 
		PatentInfo rc = (PatentInfo)((obj instanceof PatentInfo) ? obj : null);
		if (rc == null)
		{
			return -1;
		}
		return this.getPatentID().compareTo(rc.getPatentID());
	}
 

	public final Boolean Contains(String data)
	{
		return this.getName().contains(data) || this.getPatentID().contains(data) || this.getClassID().contains(data) || this.getInventor().contains(data) || this.getAbstractInfo().contains(data);
	}

	 

	 
 
	public final void DictDeserialize(java.util.HashMap<String, Object> dicts)
	{
		this.setPatentID((String)dicts.get("PatentID"));
		this.setName((String)dicts.get("PatentID"));
		this.setPublicTime((Time)(dicts.get("PublicTime")));
		this.setClassID((String)dicts.get("ClassID"));
		this.setApplicant((String)dicts.get("Applicant"));
		this.setInventor((java.util.ArrayList<String>)dicts.get("Inventors"));
		this.setAbstractInfo((String)dicts.get("AbstractInfo"));
	}

 
	public final  HashMap<String, Object> DictSerialize()
	{
		this.setInventor(new java.util.ArrayList<String>());
		java.util.HashMap<String, Object> dict = new java.util.HashMap<String, Object>();
		dict.put("PatentID", this.getPatentID());
		dict.put("Name", this.getName());
		dict.put("PublicTime", this.getPublicTime());
		dict.put("ClassID", this.getClassID());
		dict.put("Applicant", this.getApplicant());

		dict.put("Inventors", this.getInventor());

		dict.put("AbstractInfo", this.getAbstractInfo());
		return dict;
	}

 
	public final double GetRelation(IRelationComputeable r1, IRelationComputeable r2)
	{
		double temp = 0.0;
		int sum = 0;
 
		PatentInfo rc1 = (PatentInfo)((r1 instanceof PatentInfo) ? r1 : null);
 
		PatentInfo rc2 = (PatentInfo)((r2 instanceof PatentInfo) ? r2 : null);
		 
		return temp / sum;
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