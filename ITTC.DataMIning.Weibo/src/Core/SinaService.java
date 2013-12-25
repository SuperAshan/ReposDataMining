package Core;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

 








import Datas.CommentData;
import Datas.DataConverter;
import Datas.StatusData;
import Datas.TagData;
import Datas.UserData;
//import Logs.XLogSys;
import weibo4j.*;
import weibo4j.model.*;

public class SinaService implements IWeiboAPIService
{
    public SinaService()
    {
	tm.client.setToken(access_token);

	um.client.setToken(access_token);

	am.client.setToken(access_token);
	cm.client.setToken(access_token);
	fm.client.setToken(access_token);
	fm.client.setToken(access_token);
    }

//    public static String access_token = "2.00fGrYJCe3dgkC60196939db0xHe1q";
    public static String access_token = " 2.00jrobWBe3dgkCca2470d714YduPGC";
  //  2.00jrobWBe3dgkCca2470d714YduPGC

    Account am = new Account();
    Comments cm = new Comments();

    Timeline tm = new Timeline();
    Users um = new Users();

    Friendships fm = new Friendships();

    public Datas.StatusData GetStatusData(String statusID)
    {
	return null;
    }

    public int GetCurrentCountInfo()
    {
	try
	{
	    RateLimitStatus data = am.getAccountRateLimitStatus();
	    return data.getRemainingIpHits();
	} catch (WeiboException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return 0;
	// TODO Auto-generated method stub

    }

    private List<Long> Translate(String[] source)

    {
	List<Long> dataList = new ArrayList<Long>();
	for (String item : source)
	{
	    try
	    {
		dataList.add(Long.parseLong(item));
	    } catch (Exception e)
	    {

	    }

	}
	return dataList;
    }

    public List<Long> GetRelationIDs(RelationType relationType,
	    String searchinfo, int mount)
    {
	switch (relationType)
	{
	case Friend:
	    try
	    {
		return Translate(fm.getFriendsIdsByUid(searchinfo, mount, 0));
	    } catch (WeiboException e1)
	    {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		return new ArrayList<Long>();
	    }
	case FriendsOnBilateral:
	    try
	    {
		return Translate(fm
			.getFriendsBilateralIds(searchinfo, mount, 0));
	    } catch (WeiboException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return new ArrayList<Long>();
	    }
	case Follows:
	    try
	    {
		return Translate(fm.getFollowersIdsById(searchinfo, mount, 0));
	    } catch (WeiboException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    break;

	}
	return new ArrayList<Long>();

    }

    public List<UserData> GetRelations(RelationType relationType, String name,
	    int mount)
    {
	ArrayList<UserData> userDatas = new ArrayList<UserData>();
	UserWapper users = null;

	int totalMount = mount, cursor = 0;
	int rate = 200;
	if (totalMount < 200)
	    rate = totalMount;
	while (cursor < totalMount)
	{
	    try
	    {
		switch (relationType)
		{
		case Friend:
		    users = fm.getFriendsByScreenName(name, rate, cursor);

		    break;
		case Follows:
		    users = fm.getFollowersByName(name, rate, cursor);

		    break;
		case FriendsOnBilateral:
		    users = fm.getFriendsBilateral(name, rate, cursor);

		    break;
		default:
		    break;
		}
		totalMount = (int) (totalMount > users.getTotalNumber() ? users
			.getTotalNumber() : totalMount);
		cursor = (int) users.getNextCursor();
		
	    } catch (Exception e)
	    {
//		XLogSys.Instance().Error(e.toString());
	    }

	    for (User user : users.getUsers())
	    {
		userDatas.add(DataConverter.ConvertFrom(user));
	    }
	    if(cursor==0)
		    break;
	}

	return userDatas;

    }

    public List<StatusData> GetRepostStatus(String statusId, int mount)
    {
	List<Status> status;
	try
	{
	    status = tm.getRepostTimeline(statusId, mount, 1).getStatuses();
	    List<StatusData> statusDatas = new ArrayList<StatusData>()
	    {
	    };
	    for (Status status2 : status)
	    {
		statusDatas.add(DataConverter.ConvertFrom(status2));

	    }
	    return statusDatas;
	} catch (WeiboException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return new ArrayList<StatusData>();

    }

    public List<CommentData> GetStatusComments(String statusID, int mount)
    {
	try
	{
	    List<Comment> status = cm.getCommentById(statusID).getComments();
	    List<CommentData> statusDatas = new ArrayList<CommentData>()
	    {
	    };
	    for (Comment status2 : status)
	    {
		statusDatas.add(DataConverter.ConvertFrom(status2));

	    }
	    return statusDatas;
	} catch (Exception e)
	{
	    // TODO: handle exception
	    return new ArrayList<CommentData>();
	}

    }

    public UserData GetUserData(String userName)
    {
    	User userdata=null;
    	try {
			userdata=um.showUserByScreenName(userName);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return DataConverter.ConvertFrom(userdata);
	// TODO Auto-generated method stub
	//return null;
    }
    
    
    public UserData GetUserDatabyId(String Id)
    {
    	User userdata=null;
    	try {
    		userdata=um.showUserById(Id);
//			userdata=um.showUserByScreenName(userName);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return DataConverter.ConvertFrom(userdata);
	// TODO Auto-generated method stub
	//return null;
    }
    

    public List<StatusData> GetUserStatus(String people, int mount)
    {

	// TODO Auto-generated method stub
	try
	{
	    List<Status> statuss = tm
		    .getUserTimelineByName(people, mount, 1, 1).getStatuses();
	    List<StatusData> statusDatas = new ArrayList<StatusData>();
	    for (Status status : statuss)
	    {
		statusDatas.add(DataConverter.ConvertFrom(status));
	    }
	    return statusDatas;

	} catch (WeiboException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();

	}
	return new ArrayList<StatusData>();

    }
    
    public List<StatusData> GetUserStatusById(String Id, int mount)
    {

	// TODO Auto-generated method stub
	try
	{
		List<Status> statuss=tm.getUserTimelineByUid(Id, mount, 1, 1).getStatuses();
//	    List<Status> statuss = tm
//		    .getUserTimelineByName(people, mount, 1, 1).getStatuses();
	    List<StatusData> statusDatas = new ArrayList<StatusData>();
	    for (Status status : statuss)
	    {
		statusDatas.add(DataConverter.ConvertFrom(status));
	    }
	    return statusDatas;

	} catch (WeiboException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();

	}
	return new ArrayList<StatusData>();

    }
    

    public Dictionary<String, TagData[]> GetUserTags(List<String> userIDs)
    {
	// TODO Auto-generated method stub

	return null;
    }

    public List<StatusData> SearchStatusDataByKeyword(String searchinfo,
	    int mount)
    {
	// TODO Auto-generated method stub
	return null;
    }

    public List<UserData> SearchUserName(String name)
    {
	// TODO Auto-generated method stub
	return null;
    }

}
