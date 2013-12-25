package Datas;

import java.util.Date;
import java.util.HashMap;

 
 
 
public class StatusData  
{
 
	public StatusData()
	{
		this.setCommentDatas(new java.util.ArrayList<CommentData>());
	}
 
	private java.util.ArrayList<CommentData> privateCommentDatas;
	public final java.util.ArrayList<CommentData> getCommentDatas()
	{
		return privateCommentDatas;
	}
	public final void setCommentDatas(java.util.ArrayList<CommentData> value)
	{
		privateCommentDatas = value;
	}

 
	public final String getKey()
	{
		return (new Long(this.getID())).toString();
	}
	public final void setKey(String value)
	{
		this.setID(Long.parseLong(value));
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[MongoIgnore]
	private String privateMiddlePic;
	public final String getMiddlePic()
	{
		return privateMiddlePic;
	}
	public final void setMiddlePic(String value)
	{
		privateMiddlePic = value;
	}
 
	public final Object getRealBindingData()
	{
		return this;
	}
 
	private String privateReplyTo;
	public final String getReplyTo()
	{
		return privateReplyTo;
	}
	public final void setReplyTo(String value)
	{
		privateReplyTo = value;
	}
 
	private String privateReplyToUserScreenName;
	public final String getReplyToUserScreenName()
	{
		return privateReplyToUserScreenName;
	}
	public final void setReplyToUserScreenName(String value)
	{
		privateReplyToUserScreenName = value;
	}


 
	private StatusData privateRetweetedStatus;
	public final StatusData getRetweetedStatus()
	{
		return privateRetweetedStatus;
	}
	public final void setRetweetedStatus(StatusData value)
	{
		privateRetweetedStatus = value;
	}

 
	private java.util.ArrayList<String> privateSegName;
	public final java.util.ArrayList<String> getSegName()
	{
		return privateSegName;
	}
	public final void setSegName(java.util.ArrayList<String> value)
	{
		privateSegName = value;
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

 
	private String privateThumbnailPic;
	public final String getThumbnailPic()
	{
		return privateThumbnailPic;
	}
	public final void setThumbnailPic(String value)
	{
		privateThumbnailPic = value;
	}
 
 
	public final String getPosition()
	{
		return null;
	}

	private java.util.Date privateCreatedAtTime = new java.util.Date(0);
	public final java.util.Date getCreatedAtTime()
	{
		return privateCreatedAtTime;
	}
	public final void setCreatedAtTime(java.util.Date value)
	{
		privateCreatedAtTime = value;
	}

 

	public final int CompareTo(Object obj)
	{
		StatusData data=(StatusData)obj;
		return (new Long(this.getID())).compareTo(data.getID());
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

 

	public final Boolean Contains(String data)
	{
		boolean result = this.getText().contains(data);
		return result;
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

	public final Object[] GetData()
	{
		 
		if (this.getRetweetedStatus() == null)
		{
			this.setRetweetedStatus(new StatusData());
			this.getRetweetedStatus().setUserData(new UserData());
		}
		return new Object[] { this.getCreatedAtTime(), (new Long(this.getID())).toString(), this.getText(), this.getRetweetedStatus().getID(), this.getRetweetedStatus().getUserData().getID(), this.getRetweetedStatus().getText(), this.getGeoX(), this.getGeoY(), this.getUserData().getID(), this.getUserData().getName(), this.getUserData().getFavouritesCount(), this.getUserData().getFollowersCount() };
	}

	private UserData privateUserData;
	public final UserData getUserData()
	{
		return privateUserData;
	}
	public final void setUserData(UserData value)
	{
		privateUserData = value;
	}

	public final void SetData(Object[] value)
	{
		this.setCreatedAtTime((Date) value[0]);

		this.setID(Long.parseLong((String)value[1]));
		this.setText(String.valueOf(value[2]));
		this.setRetweetedStatus(new StatusData());
			// Geo = new Point { X = Convert.ToDouble(value[6]), Y = Convert.ToDouble(value[7]) }
		this.getRetweetedStatus().setUserData(new UserData());

		this.getRetweetedStatus().setID(Long.parseLong((String) value[3]));
		this.getRetweetedStatus().getUserData().setID(Long.parseLong((String) value[4]));
		this.getRetweetedStatus().setText(String.valueOf(value[5]));

		UserData tempVar = new UserData();
		tempVar.setID(Long.parseLong((String) value[8]));
		tempVar.setName(String.valueOf(value[9]));
		tempVar.setFavouritesCount(Integer.parseInt((String) value[10]));
		tempVar.setFollowersCount(Integer.parseInt((String) value[11]));
		this.setUserData(tempVar);
	}

 
	public final void DictDeserialize(java.util.Map<String, Object> dicts)
	{
		setID(((Long)dicts.get("ID")).longValue());
		setCreatedAtTime((java.util.Date)dicts.get("Time"));
		if (dicts.get("Mid")!=null)
		{
			setMid(((Long)dicts.get("Mid")).longValue());
		}

		if(dicts.get("X")!=null)
		{
			setGeoX(((Double)dicts.get("X")).doubleValue());

		}
		if (dicts.get("Y") != null)
		{
			setGeoY(((Double)dicts.get("Y")).doubleValue());
		}


		setOriginalPic((String)dicts.get("Pic"));
		setText((String)dicts.get("Text"));
		setreposts_count(((Integer)dicts.get("RC")).intValue());
		setcomments_count(((Integer)dicts.get("CC")).intValue());
		if(dicts.get("UID")!=null)
		{
			setUserDataID(((Long)dicts.get("UID")).longValue());
		}
		if (dicts.get("RST") != null)
		{
			setRetweetedStatusText((String)dicts.get("RST"));
		}
		if (dicts.get("RSD") != null)

		{
			setRetweetedStatusID(Long.parseLong((String) dicts.get("RSD")));
		}

	}
 
	public final java.util.Map<String, Object> DictSerialize()
	{
		java.util.HashMap<String, Object> dict = new java.util.HashMap<String, Object>();
		dict.put("ID", this.getID());
		dict.put("Time", this.getCreatedAtTime());
		dict.put("Mid", this.getMid());
		dict.put("X", this.getGeoX());
		dict.put("Y", this.getGeoY());
		dict.put("Pic", this.getOriginalPic());
		dict.put("Text", this.getText());
		dict.put("RC", this.getreposts_count());
		dict.put("CC", this.getcomments_count());
		dict.put("UID", this.getUserDataID());
		dict.put("RST", this.getRetweetedStatusText());
		dict.put("RSD", this.getRetweetedStatusID());
		 
		return dict;
	}

	private long privateMid;
	public final long getMid()
	{
		return privateMid;
	}
	public final void setMid(long value)
	{
		privateMid = value;
	}

	private int privatecomments_count;
	public final int getcomments_count()
	{
		return privatecomments_count;
	}
	public final void setcomments_count(int value)
	{
		privatecomments_count = value;
	}

	private String privateOriginalPic;
	public final String getOriginalPic()
	{
		return privateOriginalPic;
	}
	public final void setOriginalPic(String value)
	{
		privateOriginalPic = value;
	}

	private double privateGeoX;
	public final double getGeoX()
	{
		return privateGeoX;
	}
	public final void setGeoX(double value)
	{
		privateGeoX = value;
	}

	private double privateGeoY;
	public final double getGeoY()
	{
		return privateGeoY;
	}
	public final void setGeoY(double value)
	{
		privateGeoY = value;
	}

	private String privateRetweetedStatusText;
	public final String getRetweetedStatusText()
	{
		return privateRetweetedStatusText;
	}
	public final void setRetweetedStatusText(String value)
	{
		privateRetweetedStatusText = value;
	}

	private long privateRetweetedStatusID;
	public final long getRetweetedStatusID()
	{
		return privateRetweetedStatusID;
	}
	public final void setRetweetedStatusID(long value)
	{
		privateRetweetedStatusID = value;
	}

	private long privateUserDataID;
	public final long getUserDataID()
	{
		return privateUserDataID;
	}
	public final void setUserDataID(long value)
	{
		privateUserDataID = value;
	}

	private int privatereposts_count;
	public final int getreposts_count()
	{
		return privatereposts_count;
	}
	public final void setreposts_count(int value)
	{
		privatereposts_count = value;
	}

	private boolean privateFavorited;
	public final boolean getFavorited()
	{
		return privateFavorited;
	}
	public final void setFavorited(boolean value)
	{
		privateFavorited = value;
	}

	private boolean privateTruncated;
	public final boolean getTruncated()
	{
		return privateTruncated;
	}
	public final void setTruncated(boolean value)
	{
		privateTruncated = value;
	}

	private String privateReplyToUserId;
	public final String getReplyToUserId()
	{
		return privateReplyToUserId;
	}
	public final void setReplyToUserId(String value)
	{
		privateReplyToUserId = value;
	}

	private String privateReplyUserName;
	public final String getReplyUserName()
	{
		return privateReplyUserName;
	}
	public final void setReplyUserName(String value)
	{
		privateReplyUserName = value;
	}

 
 

	public final String GetOriginString()
	{
		return this.getText();
	}
 
	public int compareTo(Object o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	 
	public HashMap<String, Object> Serialize()
	{
		// TODO Auto-generated method stub
		return null;
	}
	 
	public void Deserialize(HashMap<String, Object> dict)
	{
		// TODO Auto-generated method stub
		
	}
 
}