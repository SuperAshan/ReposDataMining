package Paper;

import java.util.HashMap;

import Data.IComputable;
import Data.IDictionarySerializable;
import Data.ISegwordable;
import Data.ObjectToValueConverter;

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:

public class PaperInfo implements ISegwordable, IComputable,
	IDictionarySerializable
{
    // C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
    // /#region Constants and Fields

    // C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to
    // .NET attributes:
    // [NonSerialized]
    public String DownloadLink;

    /**
     * 关键词列表
     */
    public java.util.ArrayList<String> KeywordList;

    /**
     * 相同导师文献
     */
    public java.util.ArrayList<String> SameleaderPaper;

    public PaperInfo()
    {
	this.setAuthorName(new java.util.ArrayList<String>());
	this.setFund(new java.util.ArrayList<String>());
	this.KeywordList = new java.util.ArrayList<String>();
	this.SameleaderPaper = new java.util.ArrayList<String>();
	this.setAuthorUnit(new java.util.ArrayList<String>());
	this.setText("");
	this.setID("");
	this.setTitle("");
    }

    private String privateAbstractText;

    public final String getAbstractText()
    {
	return privateAbstractText;
    }

    public final void setAbstractText(String value)
    {
	privateAbstractText = value;
    }

    /**
     * 作者
     */
    private java.util.ArrayList<String> privateAuthorName;

    public final java.util.ArrayList<String> getAuthorName()
    {
	return privateAuthorName;
    }

    public final void setAuthorName(java.util.ArrayList<String> value)
    {
	privateAuthorName = value;
    }

    private java.util.ArrayList<String> privateAuthorUnit;

    public final java.util.ArrayList<String> getAuthorUnit()
    {
	return privateAuthorUnit;
    }

    public final void setAuthorUnit(java.util.ArrayList<String> value)
    {
	privateAuthorUnit = value;
    }

    /**
     * 论文英文名称
     */
    private String privateEnglishName;

    public final String getEnglishName()
    {
	return privateEnglishName;
    }

    public final void setEnglishName(String value)
    {
	privateEnglishName = value;
    }

    /**
     * 基金来源
     */
    private java.util.ArrayList<String> privateFund;

    public final java.util.ArrayList<String> getFund()
    {
	return privateFund;
    }

    public final void setFund(java.util.ArrayList<String> value)
    {
	privateFund = value;
    }

    public final String getKey()
    {
	return this.getID();
    }

    public final void setKey(String value)
    {
	this.setID(value);
    }

    /**
     * 导师名称
     */
    private String privateLeader;

    public final String getLeader()
    {
	return privateLeader;
    }

    public final void setLeader(String value)
    {
	privateLeader = value;
    }

    /**
     * 网络投稿发行人
     */
    private String privateNetworkPublicAuthor;

    public final String getNetworkPublicAuthor()
    {
	return privateNetworkPublicAuthor;
    }

    public final void setNetworkPublicAuthor(String value)
    {
	privateNetworkPublicAuthor = value;
    }

    /**
     * 网络投稿发行时间
     */
    private String privateNetworkPublicTime;

    public final String getNetworkPublicTime()
    {
	return privateNetworkPublicTime;
    }

    public final void setNetworkPublicTime(String value)
    {
	privateNetworkPublicTime = value;
    }

    /**
     * 论文级别
     */
    private String privatePaperGrade;

    public final String getPaperGrade()
    {
	return privatePaperGrade;
    }

    public final void setPaperGrade(String value)
    {
	privatePaperGrade = value;
    }

    public final Object getRealBindingData()
    {
	return this;
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

    /**
     * 学科专业名称
     */
    private String privateSubjectName;

    public final String getSubjectName()
    {
	return privateSubjectName;
    }

    public final void setSubjectName(String value)
    {
	privateSubjectName = value;
    }

    public final Boolean Contains(String data)
    {
	return true;
    }

    private String privateJounaryName;

    protected final String getJounaryName()
    {
	return privateJounaryName;
    }

    protected final void setJounaryName(String value)
    {
	privateJounaryName = value;
    }

    private String privateClassID;

    protected final String getClassID()
    {
	return privateClassID;
    }

    protected final void setClassID(String value)
    {
	privateClassID = value;
    }

    private String privateID;

    protected final String getID()
    {
	return privateID;
    }

    protected final void setID(String value)
    {
	privateID = value;
    }

    private String privateTitle;

    protected final String getTitle()
    {
	return privateTitle;
    }

    protected final void setTitle(String value)
    {
	privateTitle = value;
    }

    private String privatePubilcYear;

    protected final String getPubilcYear()
    {
	return privatePubilcYear;
    }

    protected final void setPubilcYear(String value)
    {
	privatePubilcYear = value;
    }

    public final void DictDeserialize(java.util.HashMap<String, Object> dicts)
    {

	String t = (String) dicts.get("Text");
	if (t != null)
	{
	    this.setText(t);
	}

	this.setID((String) dicts.get("ID"));
	this.setTitle((String) dicts.get("Title"));
	this.setJounaryName((String) dicts.get("JounaryName"));
	this.setPubilcYear((String) dicts.get("PubilcYear"));
	this.setClassID((String) dicts.get("ClassID"));

	this.setKeyID(((Integer) dicts.get("KeyID")).intValue());
    }

    public final java.util.HashMap<String, Object> DictSerialize()
    {
	java.util.HashMap<String, Object> dict = new java.util.HashMap<String, Object>();
	dict.put("Title", this.getTitle());
	dict.put("ID", this.getID());
	this.setAuthorName(new java.util.ArrayList<String>());

	dict.put("AuthorName", this.getAuthorName());
	dict.put("JounaryName", this.getJounaryName());
	dict.put("PubilcYear", this.getPubilcYear());
	dict.put("ClassID", this.getClassID());
	this.KeywordList = new java.util.ArrayList<String>();

	dict.put("Keywords", this.KeywordList);
	dict.put("Text", this.getText());
	dict.put("KeyID", this.getKeyID());
	return dict;
    }

    private int privateKeyID;

    public final int getKeyID()
    {
	return privateKeyID;
    }

    public final void setKeyID(int value)
    {
	privateKeyID = value;
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

    public final String GetOriginString()
    {
	return this.getAbstractText();
    }

    @Override
    public int compareTo(Object o)
    {
	PaperInfo info = (PaperInfo) o;
	if (info == null)
	    return 0;
	// return this.getKey().compareTo(info.getKeyID());
	return 0;
    }

}