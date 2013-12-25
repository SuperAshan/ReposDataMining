package AlgorithmInteractive;

import Data.IComputable;

/// <summary>
/// 可以将数据持久化存储的方案
/// </summary>
public interface IDataPersister
{
	Iterable<IComputable> DataSource = null;
}