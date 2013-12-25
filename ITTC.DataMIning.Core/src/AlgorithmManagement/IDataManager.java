package AlgorithmManagement;

import java.util.List;

import Data.IComputable;

public interface IDataManager
{

	Iterable<String> DataNameCollection = null;
	Iterable<DataCollection> DataCollectionList = null;

	void AddDataCollection(Iterable<IComputable> source, String collectionName);

	List<IComputable> Get(String name);

}
