package Data;

public interface IComputable extends Comparable<Object>
{
 // / <summary>
 	// / 是否包含某一字段
 	// / </summary>
 	// / <param name="data"></param>
 	// / <returns></returns>
 	Boolean Contains(String data);

 	String getKey();

 	 

 	// / <summary>
 	// / 真实的数据绑定数据源
 	// / <remarks>该项的引入主要解决添加装饰器后的数据绑定问题</remarks>
 	// / </summary>
 	Object RealBindingData = null;

}
