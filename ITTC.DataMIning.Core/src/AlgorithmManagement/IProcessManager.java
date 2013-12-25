package AlgorithmManagement;

import java.util.List;

public interface IProcessManager
{
	// / <summary>
	// / 当前已经加入的处理方法集合
	// / </summary>
	Iterable<IDataPorcess> CurrentProcessCollections = null;

	// / 通过名称获取处理方法实例
	// / </summary>
	// / <param name="name"></param>
	// / <returns></returns>
	IDataPorcess GetOneInstance(String name, Boolean isAddToList);

	// / <summary>
	// / 开始一项处理任务
	// / </summary>
	// / <param name="methodNames">任务名称列表</param>
	// / <param name="dataSourceName">处理的数据集名称</param>
	void LoadOneTask(List<String> methodNames, String dataSourceName,
			boolean shouldStart);

	// / <summary>
	// / 删除一个任务
	// / </summary>
	// / <param name="process"></param>
	void RemoveOneTask(IDataPorcess process);
}
