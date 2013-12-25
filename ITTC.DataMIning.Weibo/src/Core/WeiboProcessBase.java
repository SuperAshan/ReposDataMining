package Core;

import AlgorithmManagement.IDataPorcess;

 
public  abstract class WeiboProcessBase implements IDataPorcess
{
    private static IWeiboAPIService apiService=new SinaService();
    public WeiboProcessBase()
    {
	WeiboAPIService=apiService;
	 
    }
    public WeiboType WeiboType;
    
    public IWeiboAPIService WeiboAPIService;
   
    
}
