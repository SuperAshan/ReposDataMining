package Datas;

/** 
 标签信息
 
*/
public class TagData
{
	/** 
	 标签ID
	 
	*/
	private String privateID;
	public final String getID()
	{
		return privateID;
	}
	public final void setID(String value)
	{
		privateID = value;
	}
	/** 
	 名称
	 
	*/
	private String privateName;
	public final String getName()
	{
		return privateName;
	}
	public final void setName(String value)
	{
		privateName = value;
	}
	/** 
	 权重
	 
	*/
	private String privateWeight;
	public final String getWeight()
	{
		return privateWeight;
	}
	public final void setWeight(String value)
	{
		privateWeight = value;
	}
}