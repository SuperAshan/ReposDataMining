package Datas;

import Data.IComputable;
 
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[, XmlRoot("userTrend")]
public class TrendData implements IComputable
{
 
	 
	private long privateHits;
	public final long getHits()
	{
		return privateHits;
	}
	public final void setHits(long value)
	{
		privateHits = value;
	}

	 
	private String privateHotWord;
	public final String getHotWord()
	{
		return privateHotWord;
	}
	public final void setHotWord(String value)
	{
		privateHotWord = value;
	}

	 
	private long privateID;
	public final long getID()
	{
		return privateID;
	}
	public final void setID(long value)
	{
		privateID = value;
	}

	public final String getKey()
	{
		return (new Long(this.getID())).toString();
	}
	public final void setKey(String value)
	{
		this.setID(Long.parseLong(value));
	}

	public final Object getRealBindingData()
	{
		return this;
	}
 

	public final Boolean Contains(String data)
	{
		return this.getHotWord().contains(data) || this.getKey().contains(data);
	}

	public final Object[] GetData()
	{
		return null;
	}

	public final void SetData(Object[] value)
	{
	}
	@Override
	public int compareTo(Object o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
 
}