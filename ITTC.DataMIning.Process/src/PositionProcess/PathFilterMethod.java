package PositionProcess;

/// <summary>
/// 筛边策略
/// </summary>
public enum PathFilterMethod
{
	// / <summary>
	// / 不筛边
	// / </summary>
	None,

	// / <summary>
	// / 只选择距离中心
	// / </summary>
	ClusterCenter,

	// / <summary>
	// / 距离优先
	// / </summary>
	DistancePriority,

	// / <summary>
	// / 每个点选取关系最近的两条边
	// / </summary>
	PointDistancePriority,
}

