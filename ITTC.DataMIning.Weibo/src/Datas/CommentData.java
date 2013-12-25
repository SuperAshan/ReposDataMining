package Datas;

import java.util.Date;
 
public class CommentData 
{
 

	public CommentData()
	{
		this.setSegName(new java.util.ArrayList<String>());
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Properties

	/**
	 * Gets or sets the source of the status.
	 */
	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [XmlElement("source"), MongoAlias("Content")]
	private String privateContent;

	public final String getContent()
	{
		return privateContent;
	}

	public final void setContent(String value)
	{
		privateContent = value;
	}

	/**
	 * Gets or sets the creation time of the status.
	 */

	private Date privateCreatedAt;

	public final Date getCreatedAt()
	{
		return privateCreatedAt;
	}

	public final void setCreatedAt(Date value)
	{
		privateCreatedAt = value;
	}

	/**
	 * Gets or sets the stutus id.
	 */
	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [XmlElement("id")]
	private long privateID;

	public final long getID()
	{
		return privateID;
	}

	public final void setID(long value)
	{
		privateID = value;
	}

	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [MongoIgnore]
	public final String getKey()
	{
		return (new Long(this.getID())).toString();
	}

	public final void setKey(String value)
	{
		this.setID(Long.parseLong(value));
	}

	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [MongoIgnore]
	public final Object getRealBindingData()
	{
		return this;
	}

	// C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
	// .NET attributes:
	// [MongoIgnore]
	private java.util.ArrayList<String> privateSegName;

	public final java.util.ArrayList<String> getSegName()
	{
		return privateSegName;
	}

	public final void setSegName(java.util.ArrayList<String> value)
	{
		privateSegName = value;
	}

	private StatusData privateStatus;

	public final StatusData getStatus()
	{
		return privateStatus;
	}

	public final void setStatus(StatusData value)
	{
		privateStatus = value;
	}

	private String privateText;

	public final String getText()
	{
		return privateText;
	}

	public final void setText(String value)
	{
		privateText = value;
	}

	private UserData privateUser;

	public final UserData getUser()
	{
		return privateUser;
	}

	public final void setUser(UserData value)
	{
		privateUser = value;
	}

	private String privateSource;

	public final String getSource()
	{
		return privateSource;
	}

	public final void setSource(String value)
	{
		privateSource = value;
	}

	public final Boolean Contains(String data)
	{
		return true;
	}

	public final Object[] GetData()
	{
		return null;
	}

	public final void SetData(Object[] value)
	{
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region ISegWordable

	public final String GetOriginString()
	{
		return this.getText();
	}

	public int compareTo(Object o)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}