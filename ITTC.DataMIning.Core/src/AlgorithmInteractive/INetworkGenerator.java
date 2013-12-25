package AlgorithmInteractive;

import java.util.List;

import Data.IObjectRelation;

/// <summary>
/// 网络生成器接口
/// </summary>
public interface INetworkGenerator extends IObjectRelation
{

	double LinkedProbability = 0.5;

	int NearestNeighborMount = 2;

	// / <summary>
	// / 输出的网络
	// / </summary>
	List<Data.Node> NetworkNodes = null;

	// / <summary>
	// / 网络规模
	// / </summary>
	int ScaleSize = 100;

	void GenerateNetwork();

}