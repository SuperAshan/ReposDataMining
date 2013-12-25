package Core;

import java.util.Dictionary;
import java.util.List;

 
import Datas.CommentData;
import Datas.StatusData;
import Datas.TagData;
import Datas.UserData;

public interface IWeiboAPIService
{

    StatusData GetStatusData(String statusID);

   	// / <summary>
   	// / 获取当前账号的信息，剩余API次数等
   	// / </summary>
   	int GetCurrentCountInfo();

   	// / <summary>
   	// / 通过关键词查找用户好友ID
   	// / </summary>
   	// / <param name="searchinfo"></param>
   	// / <param name="mount">要获取的数量</param>
   	// / <returns></returns>
   	List<Long> GetRelationIDs(RelationType relationType, String searchinfo,
   			int mount);

   	// / <summary>
   	// / 获取用户关系接口
   	// / </summary>
   	// / <param name="relationType"></param>
   	// / <param name="id"></param>
   	// / <param name="mount"></param>
   	// / <returns></returns>
   	List<UserData> GetRelations(RelationType relationType, String id, int mount);

   	// / <summary>
   	// / 获取某条微博的转发微博
   	// / </summary>
   	// / <param name="statusId"></param>
   	// / <param name="mount"></param>
   	// / <returns></returns>
   	List<StatusData> GetRepostStatus(String statusId, int mount);

   	// / <summary>
   	// / 获取某条微博ID的评论
   	// / </summary>
   	// / <param name="statusID"></param>
   	// / <param name="mount"></param>
   	// / <returns></returns>
   	List<CommentData> GetStatusComments(String statusID, int mount);

   	// / <summary>
   	// / 获取用户信息
   	// / </summary>
   	// / <param name="userName"> </param>
   	// / <returns></returns>
   	UserData GetUserData(String userName);
   	
	// / <summary>
   	// / 获取用户信息
   	// / </summary>
   	// / <param name="userName"> </param>
   	// / <returns></returns>
   	UserData GetUserDatabyId(String Id);

   	// / <summary>
   	// /获取某人的微博时间线
   	// / </summary>
   	// / <param name="people"></param>
   	// / <param name="mount"></param>
   	// / <returns></returns>
   	List<StatusData> GetUserStatus(String people, int mount);
   	
   	
	// / <summary>
   	// /获取某人的微博时间线
   	// / </summary>
   	// / <param name="people"></param>
   	// / <param name="mount"></param>
   	// / <returns></returns>
   	List<StatusData> GetUserStatusById(String Id, int mount);

   	// / <summary>
   	// / 获取用户标签
   	// / </summary>
   	// / <param name="userIDs"></param>
   	// / <returns></returns>
   	Dictionary<String, TagData[]> GetUserTags(List<String> userIDs);

   	// / <summary>
   	// / 通过关键词查询微博
   	// / </summary>
   	// / <param name="searchinfo"></param>
   	// / <returns></returns>
   	List<StatusData> SearchStatusDataByKeyword(String searchinfo, int mount);

   	List<UserData> SearchUserName(String name);
}
