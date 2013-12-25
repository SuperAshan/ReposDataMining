package AlgorithmInteractive;

import java.util.HashMap;
import java.util.List;

import Data.ISegwordable;
import Data.WordData;

public interface IStringNumberConverter
{

	// / <summary>
	// / 转换结果
	// / </summary>
	HashMap<ISegwordable, List<WordData>> ConvertResult = null;

	// / <summary>
	// / 对单个字符串实现向量转换
	// / </summary>
	// / <param name="value"></param>
	// / <returns></returns>
	List<WordData> Transform(String value);

	// / <summary>
	// / 将数据源转换为向量，结果保存在ConvertResult中
	// / </summary>
	// / <param name="datasource"></param>
	void ConvertToVector(Iterable<ISegwordable> datasource);

	// / <summary>
	// / 进行初始化
	// / </summary>
	// / <returns></returns>
	Boolean InitProcess();
}
