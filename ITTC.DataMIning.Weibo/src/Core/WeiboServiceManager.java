package Core;

 
/// <summary>
/// 对微博接口实现统一调用，避免重复继承问题
/// </summary>
public class WeiboServiceManager {

	private static WeiboServiceManager instance;
	SinaService sinaService;

	private WeiboServiceManager() {
		sinaService = new SinaService();
	}

	public static WeiboServiceManager Instance() {

		if (instance == null)
			instance = new WeiboServiceManager();
		return instance;

	}

	public IWeiboAPIService Get(WeiboType weiboType) {
		switch (weiboType) {
		case Sina:
			return sinaService;

		default:
			break;
		}
		return null;
	}

}