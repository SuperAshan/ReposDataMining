package Process;

public interface IProcess
{

	// / <summary>
	// / 名称
	// / </summary>
	String PublicName = "";

	// / <summary>
	// / 数据处理函数
	// / </summary>
	// / <returns></returns>
	boolean DataProcess();

	// / <summary>
	// / 数据初始化
	// / </summary>
	// / <returns></returns>
	boolean InitProcess();

	// / <summary>
	// / 是否开启
	// / </summary>
	boolean IsOpen = false;

	// / <summary>
	// / 关闭所需的处理函数
	// / </summary>
	// / <returns></returns>
	boolean CloseProcess();
}
