package Artical;

import java.util.HashMap;

import Data.IDictionarySerializable;
import Data.IRelationComputeable;
import Data.ISegwordable;

 
public class ArticalInfo implements ISegwordable, IRelationComputeable,
		IDictionarySerializable
{
 

	public Boolean Contains(String data)
	{
		return this.getArticalContent().contains(data)
				|| this.getTitle().contains(data);
	}

	 
	public Object getRealBindingData()
	{
		return this;
	}

	public String getKey()
	{
		return this.getTitle();
	}

	public void setKey(String value)
	{
		this.setTitle(value);
	}

	public String GetOriginString()
	{
		return this.getArticalContent();
		 
	}

	private String privateArticalContent;

	public final String getArticalContent()
	{
		return privateArticalContent;
	}

	public final void setArticalContent(String value)
	{
		privateArticalContent = value;
	}

	public final int CompareTo(Object obj)
	{

		ArticalInfo artical = (ArticalInfo) ((obj instanceof ArticalInfo) ? obj
				: null);
		if (artical == null)
		{
			return -1;
		}
		return this.getTitle().compareTo(artical.getTitle());
	}

	private String privateTitle;

	public final String getTitle()
	{
		return privateTitle;
	}

	public final void setTitle(String value)
	{
		privateTitle = value;
	}

	public final double GetRelation(IRelationComputeable r1,
			IRelationComputeable r2)
	{
		return 0;
	}

	private java.util.Date privatePublishTime = new java.util.Date(0);

	public final java.util.Date getPublishTime()
	{
		return privatePublishTime;
	}

	public final void setPublishTime(java.util.Date value)
	{
		privatePublishTime = value;
	}

	//
	public final void DictDeserialize(java.util.HashMap<String, Object> dicts)
	{
		this.setTitle((String) dicts.get("Title"));
		this.setPublishTime((java.util.Date) dicts.get("PublishTime"));
		this.setAuthor((String) dicts.get("Author"));
		this.setArticalContent((String) dicts.get("ArticalContent"));
		this.setPaperID(((Integer) dicts.get("PaperID")).intValue());
		this.setClassID((String) dicts.get("ClassID"));
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

	private int privatePaperID;

	public final int getPaperID()
	{
		return privatePaperID;
	}

	public final void setPaperID(int value)
	{
		privatePaperID = value;
	}

	private String privateAuthor;

	public final String getAuthor()
	{
		return privateAuthor;
	}

	public final void setAuthor(String value)
	{
		privateAuthor = value;
	}

	@Override
	public int compareTo(Object o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HashMap<String, Object> DictSerialize()
	{
		java.util.HashMap<String, Object> dict = new java.util.HashMap<String, Object>();
		dict.put("Title", this.getTitle());
		dict.put("PublishTime", this.getPublishTime());
		dict.put("Author", this.getAuthor());
		dict.put("ArticalContent", this.getArticalContent());
		dict.put("PaperID", this.getPaperID());
		dict.put("ClassID", this.getClassID());
		return dict;
	 
	}

	 

	@Override
	public double getWeight()
	{
	    // TODO Auto-generated method stub
	    return 0;
	}


 
	 
}