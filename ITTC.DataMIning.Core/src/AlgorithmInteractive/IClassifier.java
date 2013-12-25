package AlgorithmInteractive;

import java.util.List;

public interface IClassifier
{

	// / <summary>
	// / 对一个数列进行分类
	// / </summary>
	// / <param name="objects">目标序列</param>
	// / <returns>结果</returns>
	double Predict(List<Double> objects);

	// / <summary>
	// / 分类器工作模式
	// / </summary>
	public enum ClassifierMode
	{
		// / <summary>
		// / 训练模式
		// / </summary>
		Training,
		// / <summary>
		// / 工作模式
		// / </summary>
		Working,
	}
}
