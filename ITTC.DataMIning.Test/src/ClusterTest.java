import java.util.List;

import Core.IWeiboAPIService;
import Core.RelationType;
import Core.WeiboServiceManager;
import Core.WeiboType;
import Datas.UserData;


public class ClusterTest {
	
	 String username="大漠飞刀客";
		
		IWeiboAPIService service = WeiboServiceManager.Instance().Get(
			WeiboType.Sina);
		List<UserData> userDatas = service.GetRelations(RelationType.Friend,
			username, 600); // 央视新闻
	//	System.out.print("总点数："+userDatas.size());

}
