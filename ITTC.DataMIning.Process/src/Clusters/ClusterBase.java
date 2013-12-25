package Clusters;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import AlgorithmInteractive.ICLusterResult;
import AlgorithmManagement.IDataPorcess;
import Data.INode;
import Data.IObjectRelation;
import Data.IRelationComputeable;
import Data.LinkedPath;
import Data.Node;
import Data.RelationMatrix;

public class ClusterBase implements IDataPorcess, ICLusterResult
{

    IObjectRelation objectRelation;
    List<Data.INode> nodes;
    protected Map<Integer, List<Integer>> clusterResult;
    protected double maxRelation;
    protected double minRelation;
    protected RelationMatrix matrix;

    public String getName()
    {

	return "LeaderCluster";
    }

    public boolean DataProcess()
    {

	this.nodes = new ArrayList<INode>();
	for (IRelationComputeable data : objectRelation.getDataSource())
	{
	    INode node = new Node();
	    node.setData(data);
	    this.nodes.add(node);

	}

	matrix = this.objectRelation.getRelationMatrix();

	double dMin = 100.0, dMax = 0.0;

	for (int i = 0; i < nodes.size(); i++)
	{
	    INode node1 = nodes.get(i);
	    for (int j = 0; j < nodes.size(); j++)
	    {
		INode node2 = nodes.get(j);
		if (i == j)
		{
		    continue;
		}
		double d = matrix.GetR(i, j);
		if (d > RelationMatrix.threshold)
		{
		    if (d > dMax)
		    {
			dMax = d;
		    }
		    if (d < dMin)
		    {
			dMin = d;
		    }
		    LinkedPath.BuildLinkedPath(node1, node2);
		}
	    }
	}

	maxRelation = dMax;
	minRelation = dMin;

	this.ClusterProcess();

	return true;
    }

    protected void ClusterProcess()
    {
	// TODO Auto-generated method stub

    }

    public boolean InitProcess()
    {
	// TODO Auto-generated method stub
	return false;
    }

    public boolean CloseProcess()
    {
	// TODO Auto-generated method stub
	return false;
    }

    public Map<Integer, List<Integer>> getClusterResult()
    {
	return clusterResult;
    }

    public List<INode> getNodes()
    {
	// TODO Auto-generated method stub
	return nodes;
    }

    public IObjectRelation setRelation(IObjectRelation relation)
    {
	objectRelation = relation;
	return null;
    }

}
