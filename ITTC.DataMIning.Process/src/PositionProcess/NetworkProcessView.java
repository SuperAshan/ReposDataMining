package PositionProcess;

 
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import net.hydromatic.linq4j.Enumerable;
import net.hydromatic.linq4j.Linq4j;
import net.hydromatic.linq4j.function.Function1;
import AlgorithmManagement.IDataPorcess;
import Data.IPositionComputeable;
import Data.IPositionProcess;
import Data.LinkedPath;
import Data.RelationMatrix;
import PositionProcess.FastForceDirected.FastForceDirected;
import PositionProcess.ForceDirected.ForceDirected;
import PositionProcess.LargeScale.LargeScaleAlgorithm;
import PositionProcess.PathWeigth.PathWeightAlgorithm;
import PositionProcess.SimplePathWeigth.SimplePathWeigthAlgorithm;
import PositionProcess.StressMajorization.StressMajorazation;

 
public class NetworkProcessView implements IDataPorcess
{

    public List<IPositionComputeable> nodes;

    private IPositionProcess GlobalPositionProcess;
    private Node virtualNode;
    public PathFilterMethod pathFilrterMethod;
    public RelationMatrix matrix;

    public String getName()
    {

	return "网络图形显示";
    }
    protected IPositionProcess PositionProcessFactory(PositionProcessType type)
	{
	IPositionProcess iPositionProcess = null;
		switch (type)
		{
		case FastForceDirected:
			iPositionProcess = new FastForceDirected();
			break;
		case ForceDirected:
			iPositionProcess =  new ForceDirected();
			break;
		case PathWeigth:
			iPositionProcess =  new PathWeightAlgorithm();
			break;
		case StressMojorization:
			iPositionProcess =  new StressMajorazation();
			break;
		case SimplePathWeigth:
			iPositionProcess =  new SimplePathWeigthAlgorithm();
			break;
		case LargeScale:
			iPositionProcess =  new LargeScaleAlgorithm();
			break;
		}
		return iPositionProcess;
	}
    public boolean DataProcess()
    {
//	this.GlobalPositionProcess = PositionProcessFactory(PositionProcessType.FastForceDirected);
    	this.GlobalPositionProcess = PositionProcessFactory(PositionProcessType.StressMojorization);
//	this.virtualNode = new Node();
//	this.virtualNode.setData(this.virtualNode);
//	nodes.add(0, virtualNode);

	GlobalPositionProcess.Nodes=nodes;
	 GlobalPositionProcess.relationMatrix=matrix;
	GlobalPositionProcess.PositionComputeProcess();

	 
	return false;
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

    public List<LinkedPath> getAllPath()
    {
	/*List<LinkedPath> paths = new ArrayList<LinkedPath>();
	Enumerable<Data.Node> newNodes = Linq4j.asEnumerable(nodes).ofType(
		Data.Node.class);
	for (Data.Node node : newNodes)
	{
	    List<LinkedPath> path = Linq4j
		    .asEnumerable(node)
		    .orderByDescending(new Function1<LinkedPath, Double>()
		    {

			public Double apply(LinkedPath arg0)
			{

			    return arg0.Dist;
			}
		    }).take(3).toList();
	    paths.addAll(path);
	}
	return paths;*/
	return new ArrayList<LinkedPath>();
    }

}
