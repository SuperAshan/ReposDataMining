package Datas;

import java.util.HashMap;
 
 
public class UserData implements Data.IRelationComputeable
{
    

    public UserData()
    {
	this.setFriendIDs(new java.util.ArrayList<Long>());
	this.setFollowIDs(new java.util.ArrayList<Long>());
    }

    // C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
  
    private java.util.ArrayList<Long> privateFollowIDs;

    public final java.util.ArrayList<Long> getFollowIDs()
    {
	return privateFollowIDs;
    }

    public final void setFollowIDs(java.util.ArrayList<Long> value)
    {
	privateFollowIDs = value;
    }

 
    private java.util.ArrayList<Long> privateFriendIDs;

    public final java.util.ArrayList<Long> getFriendIDs()
    {
	return privateFriendIDs;
    }

    public final void setFriendIDs(java.util.ArrayList<Long> value)
    {
	privateFriendIDs = value;
    }

    
    public final String getKey()
    {
	return this.getName();
    }

    public final void setKey(String value)
    {
	this.setName(value);
    }

    
    public final Object getRealBindingData()
    {
	return this;
    }

    public   double getWeight()
    {
	double value=  Math.log10(this.getFollowersCount()) ;
	if(value<1)
	    return 1;
	return value;
    }
 

    public final int CompareTo(Object obj)
    {
	 
	UserData r = (UserData) ((obj instanceof UserData) ? obj : null);
	if (r == null)
	{
	    return -1;
	} else
	{
	    return (new Long(this.getID())).compareTo(r.getID());
	}
    }

    private Object privateTag;

    public final Object getTag()
    {
	return privateTag;
    }

    public final void setTag(Object value)
    {
	privateTag = value;
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

    public final Boolean Contains(String data)
    {
	return this.getName().contains(data)
		|| (new Long(this.getID())).toString().contains(data);
    }

    public final Object[] GetData()
    {
	return new Object[] { this.getID(), this.getScreenName(),
		this.getName(), this.getDefineAs(), this.getProvince(),
		this.getCity(), this.getLocation(), this.getDescription(),
		this.getUrl(), this.getProfileImageUrl(), this.getDomain(),
		this.getGender(), this.getFollowersCount(),
		this.getFriendsCount(), this.getStatusesCount(),
		this.getFavouritesCount(), this.getCreatedAt(),
		this.getGeoEnabled(), this.getAllowAllActMsg(),
		this.getFollowing(), this.getBiFollowing(), this.getVerified() };
    }

    private java.util.Date privateCreatedAt = new java.util.Date(0);

    public final java.util.Date getCreatedAt()
    {
	return privateCreatedAt;
    }

    public final void setCreatedAt(java.util.Date value)
    {
	privateCreatedAt = value;
    }

    private String privateCity;

    public final String getCity()
    {
	return privateCity;
    }

    public final void setCity(String value)
    {
	privateCity = value;
    }

    private String privateDefineAs;

    public final String getDefineAs()
    {
	return privateDefineAs;
    }

    public final void setDefineAs(String value)
    {
	privateDefineAs = value;
    }

    private String privateProfileImageUrl;

    public final String getProfileImageUrl()
    {
	return privateProfileImageUrl;
    }

    public final void setProfileImageUrl(String value)
    {
	privateProfileImageUrl = value;
    }

    private String privateDomain;

    public final String getDomain()
    {
	return privateDomain;
    }

    public final void setDomain(String value)
    {
	privateDomain = value;
    }

    private String privateProvince;

    public final String getProvince()
    {
	return privateProvince;
    }

    public final void setProvince(String value)
    {
	privateProvince = value;
    }

    private String privateGender;

    public final String getGender()
    {
	return privateGender;
    }

    public final void setGender(String value)
    {
	privateGender = value;
    }

    private String privateLocation;

    public final String getLocation()
    {
	return privateLocation;
    }

    public final void setLocation(String value)
    {
	privateLocation = value;
    }

    private String privateScreenName;

    public final String getScreenName()
    {
	return privateScreenName;
    }

    public final void setScreenName(String value)
    {
	privateScreenName = value;
    }

    private String privateDescription;

    public final String getDescription()
    {
	return privateDescription;
    }

    public final void setDescription(String value)
    {
	privateDescription = value;
    }

    private String privateUrl;

    public final String getUrl()
    {
	return privateUrl;
    }

    public final void setUrl(String value)
    {
	privateUrl = value;
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

    private int privateFriendsCount;

    public final int getFriendsCount()
    {
	return privateFriendsCount;
    }

    public final void setFriendsCount(int value)
    {
	privateFriendsCount = value;
    }

    private int privateStatusesCount;

    public final int getStatusesCount()
    {
	return privateStatusesCount;
    }

    public final void setStatusesCount(int value)
    {
	privateStatusesCount = value;
    }

    private int privateFollowersCount;

    public final int getFollowersCount()
    {
	return privateFollowersCount;
    }

    public final void setFollowersCount(int value)
    {
	privateFollowersCount = value;
    }

    private int privateFavouritesCount;

    public final int getFavouritesCount()
    {
	return privateFavouritesCount;
    }

    public final void setFavouritesCount(int value)
    {
	privateFavouritesCount = value;
    }

    private boolean privateAllowAllActMsg;

    public final boolean getAllowAllActMsg()
    {
	return privateAllowAllActMsg;
    }

    public final void setAllowAllActMsg(boolean value)
    {
	privateAllowAllActMsg = value;
    }

    private boolean privateFollowing;

    public final boolean getFollowing()
    {
	return privateFollowing;
    }

    public final void setFollowing(boolean value)
    {
	privateFollowing = value;
    }

    private boolean privateBiFollowing;

    public final boolean getBiFollowing()
    {
	return privateBiFollowing;
    }

    public final void setBiFollowing(boolean value)
    {
	privateBiFollowing = value;
    }

    private boolean privateVerified;

    public final boolean getVerified()
    {
	return privateVerified;
    }

    public final void setVerified(boolean value)
    {
	privateVerified = value;
    }

    private boolean privateGeoEnabled;

    public final boolean getGeoEnabled()
    {
	return privateGeoEnabled;
    }

    public final void setGeoEnabled(boolean value)
    {
	privateGeoEnabled = value;
    }

    public final void SetData(Object[] value)
    {
	this.setID(((Long) value[0]).longValue());
	this.setScreenName((String) value[1]);
	this.setName((String) value[2]);
	this.setDefineAs((String) value[3]);
	this.setProvince((String) value[4]);
	this.setCity((String) value[5]);
	this.setLocation((String) value[6]);
	this.setDescription((String) value[7]);
	this.setUrl((String) value[8]);
	this.setProfileImageUrl((String) value[9]);
	this.setDomain((String) value[10]);
	this.setGender((String) value[11]);
	this.setFollowersCount(((Integer) value[12]).intValue());
	this.setFriendsCount(((Integer) value[13]).intValue());
	this.setStatusesCount(((Integer) value[14]).intValue());
	this.setFavouritesCount(((Integer) value[15]).intValue());
	this.setCreatedAt((java.util.Date) value[16]);
	this.setGeoEnabled(((Boolean) value[17]).booleanValue());
	this.setAllowAllActMsg(((Boolean) value[18]).booleanValue());
	this.setFollowing(((Boolean) value[19]).booleanValue());
	this.setBiFollowing(((Boolean) value[20]).booleanValue());
	this.setVerified(((Boolean) value[21]).booleanValue());
    }

    public final java.util.Map<String, Object> DictSerialize()
    {
	java.util.HashMap<String, Object> dict = new java.util.HashMap<String, Object>();
	dict.put("ID", this.getID());

	dict.put("City", this.getCity());
	dict.put("Cre", this.getCreatedAt());
	dict.put("DAs", this.getDefineAs());
	dict.put("Des", this.getDescription());

	dict.put("Dom", this.getDomain());
	dict.put("Fol", this.getFollowersCount());
	dict.put("Fri", this.getFriendsCount());
	dict.put("Gen", this.getGender());
	dict.put("Geo", this.getGeoEnabled());
	dict.put("ID", this.getID());
	dict.put("Ima", this.getProfileImageUrl());
	dict.put("Name", this.getName());
	dict.put("Loc", this.getLocation());
	dict.put("Pro", this.getProvince());
	dict.put("Sta", this.getStatusesCount());
	dict.put("Url", this.getUrl());
	dict.put("Ver", this.getVerified());
	dict.put("Friends", getFriendIDs());
	dict.put("Follows", getFollowIDs());
	return dict;
    }

    // C# TO JAVA CONVERTER TODO TASK: C# optional parameters are not converted
    // to Java:
    // ORIGINAL LINE: public void DictDeserialize(IDictionary<string, object>
    // dicts, Scenario scenario = Scenario.Database)
    public final void DictDeserialize(java.util.Map<String, Object> dicts)
    {

	setID(((Long) dicts.get("ID")).longValue());
	setName((String) dicts.get("Name"));
	setCity((String) dicts.get("City"));
	setCreatedAt((java.util.Date) dicts.get("Cre"));
	setDescription((String) dicts.get("DAs"));
	setDomain((String) dicts.get("Dom"));
	setFollowersCount(((Integer) dicts.get("Fol")).intValue());
	setFriendsCount(((Integer) dicts.get("Fri")).intValue());
	setGender((String) dicts.get("Gen"));
	setGeoEnabled(((Boolean) dicts.get("Geo")).booleanValue());
	setID(((Long) dicts.get("ID")).longValue());
	setProfileImageUrl((String) dicts.get("Ima"));
	setName((String) dicts.get("Name"));
	setLocation((String) dicts.get("Loc"));
	setProvince((String) dicts.get("Pro"));
	setStatusesCount(((Integer) dicts.get("Sta")).intValue());
	setUrl((String) dicts.get("Url"));
	setVerified(((Boolean) dicts.get("Ver")).booleanValue());
	// C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit
	// typing in Java:
	// var fri = (java.util.ArrayList<Long>)((dicts.get("Friends")
	// instanceof java.util.ArrayList<Long>) ? dicts.get("Friends") : null);
	// C# TO JAVA CONVERTER TODO TASK: There is no equivalent to implicit
	// typing in Java:
	// var fol= (java.util.ArrayList<Long>)((dicts.get("Follows") instanceof
	// java.util.ArrayList<Long>) ? dicts.get("Follows") : null);
	// if(fri!=null)
	// {
	// setFriendIDs(fri);
	// }
	//
	// if (fol != null)
	// {
	// setFollowIDs(fol);
	// }

    }

    private String privateWeiboContent;

    public final String getWeiboContent()
    {
	return privateWeiboContent;
    }

    public final void setWeiboContent(String value)
    {
	privateWeiboContent = value;
    }

    // C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
    // /#endregion

    private java.util.ArrayList<String> privateSegName;

    public final java.util.ArrayList<String> getSegName()
    {
	return privateSegName;
    }

    public final void setSegName(java.util.ArrayList<String> value)
    {
	privateSegName = value;
    }

    public final String GetOriginString()
    {
	return this.getWeiboContent();
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

    public double GetRelation(Data.IRelationComputeable r1, Data.IRelationComputeable r2)
    {
	// TODO Auto-generated method stub
	return 0;
    }

    

    public void setWeight(double value)
    {
	// TODO Auto-generated method stub
	
    }

    

  

}