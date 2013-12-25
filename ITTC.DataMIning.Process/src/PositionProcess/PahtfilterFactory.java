package PositionProcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.hydromatic.linq4j.Linq4j;
import Data.CompressType;
import Data.INode;
import Data.IPositionComputeable;
import Data.LinkedPath;
import Data.RelationMatrix;
import PositionProcess.FastForceDirected.FastForceDirected;
import PositionProcess.ForceDirected.ForceDirected;
import PositionProcess.LargeScale.LargeScaleAlgorithm;
import PositionProcess.PathWeigth.PathWeightAlgorithm;
import PositionProcess.SimplePathWeigth.SimplePathWeigthAlgorithm;

public class PahtfilterFactory {
	private PathFilterMethod pathfilt;
	
	public  PahtfilterFactory(PathFilterMethod select)
	{
		this.pathfilt=select;
	}
	
//	Map<Integer,List<Integer>> clusterresult=cluster.getClusterResult();
//	List<IPositionComputeable> Nodesresult=Linq4j.ofType(cluster.getNodes(),
//			IPositionComputeable.class).toList();
//	RelationMatrix Matrixresult=calcultor.getRelationMatrix();
	
	public RelationMatrix selectPathMethod(Map<Integer,List<Integer>> clusterresult,List<IPositionComputeable> Nodesresult,RelationMatrix Matrixresult)
	{
		switch(this.pathfilt)
		{
		
		case None:
			return Matrixresult;
			//break;
		case ClusterCenter:
			return this.ClusterCenter(clusterresult,Nodesresult,Matrixresult);
			//break;
		case DistancePriority:
			return this.PointCenter(clusterresult,Nodesresult,Matrixresult);
			//break;
		case PointDistancePriority:
			return this.DistanceCenter(clusterresult,Nodesresult,Matrixresult);
			//break;
		default:
			return Matrixresult;
		//	break;
		}
		
//		switch (this.pathfilt)
//		{
//		case No:
//			iPositionProcess = new FastForceDirected();
//			break;
//		case ForceDirected:
//			iPositionProcess =  new ForceDirected();
//			break;
//		case PathWeigth:
//			iPositionProcess =  new PathWeightAlgorithm();
//			break;
//		case StressMojorization:
//			iPositionProcess =  new FastForceDirected();
//			break;
//		case SimplePathWeigth:
//			iPositionProcess =  new SimplePathWeigthAlgorithm();
//			break;
//		case LargeScale:
//			iPositionProcess =  new LargeScaleAlgorithm();
//			break;
//		}
	}
	
	private RelationMatrix ClusterCenter(Map<Integer,List<Integer>> clusterresult,List<IPositionComputeable> Nodesresult,RelationMatrix Matrixresult)
	{
		INode self=new Data.Node();
		int nodenumber=Nodesresult.size();
	//	self.
		self.setData(self);
		for(IPositionComputeable node:Nodesresult)
		{
			node.getLinkedPathCollection().clear();
		}
		CompressType type=Matrixresult.getCompressType();
		Matrixresult=new RelationMatrix(nodenumber+1, type);
		for(int corenumber:clusterresult.keySet())
		{
			if(clusterresult.get(corenumber).size()!=0)
			{
			LinkedPath link=new LinkedPath(self,(INode)Nodesresult.get(corenumber));
			self.getLinkedPathCollection().add(link);
			Nodesresult.get(corenumber).getLinkedPathCollection().add(new LinkedPath((INode)Nodesresult.get(corenumber),self));
			Matrixresult.SetR(nodenumber, corenumber, 1);
			Matrixresult.SetR(corenumber, nodenumber, 1);
			List<Integer> integerCollection=clusterresult.get(corenumber);
			for(Integer pointnumber:integerCollection)
			{
				LinkedPath link1=new LinkedPath((INode)Nodesresult.get(corenumber),(INode)Nodesresult.get(pointnumber));
			    self.getLinkedPathCollection().add(link1);
			    Nodesresult.get(pointnumber).getLinkedPathCollection().add(new LinkedPath((INode)Nodesresult.get(pointnumber),(INode)Nodesresult.get(corenumber)));
			    Matrixresult.SetR(corenumber, pointnumber, 1);
			    Matrixresult.SetR(pointnumber, corenumber, 1);
			}
			}
			
		}
		Nodesresult.add(self);
		return Matrixresult;
		
//		for(int i=0;i<clusterresult.size();i++)
//		{
//			
//		}
//		Node VirtualNode=new Node();
	
	}
	
	private RelationMatrix PointCenter(Map<Integer,List<Integer>> clusterresult,List<IPositionComputeable> Nodesresult,RelationMatrix Matrixresult)
	{
		int n=Nodesresult.size();
		for(int i=0;i<n;i++)
		{
			int linkpathnumber=Nodesresult.get(i).getLinkedPathCollection().size();
			if(linkpathnumber<=2)
				break;
			else
			{
	//			Collections.sort(Nodesresult.get(i).getLinkedPathCollection());
				Collections.sort(Nodesresult.get(i).getLinkedPathCollection());
				List<LinkedPath> linkedpathCollection=Nodesresult.get(i).getLinkedPathCollection();
///				Nodesresult.get(i).getLinkedPathCollection().r
				for(int j=linkpathnumber-1;j>1;j--)
				{
					INode node2=linkedpathCollection.get(j).Node2;
					int index=Nodesresult.indexOf(node2);
					if(Matrixresult.IsLinked(i, index) || Matrixresult.IsLinked(index, i))
					{
						Matrixresult.SetR(i, index,(float)linkedpathCollection.get(j).Dist);
						Matrixresult.SetR(index, i,(float)linkedpathCollection.get(j).Dist);
					}
					linkedpathCollection.remove(j);
					
				}
//				Collections.sor
			}
		}
		return Matrixresult;
	}
	
	private RelationMatrix DistanceCenter(Map<Integer,List<Integer>> clusterresult,List<IPositionComputeable> Nodesresult,RelationMatrix Matrixresult)
	{
//		int edgenumber=0;
		List<LinkedPath> linkedpathcollection=new ArrayList<LinkedPath>();
		for(IPositionComputeable node:Nodesresult)
		{
			linkedpathcollection.addAll(node.getLinkedPathCollection());
		}
		Collections.sort(linkedpathcollection);
		if(linkedpathcollection.size()>Nodesresult.size()*2)
		{
			double deadline=linkedpathcollection.get(Nodesresult.size()*2-1).Dist;
			int nodenumber=0;
			for(IPositionComputeable node:Nodesresult)
			{
				int edgenumber=node.getLinkedPathCollection().size();
				if(edgenumber>0)
				{
					for(int j=edgenumber-1;j>-1;j--)
					{
						if(node.getLinkedPathCollection().get(j).Dist>deadline)
							node.getLinkedPathCollection().remove(j);
					}
				}
				nodenumber++;
			}
		}
		return Matrixresult;
		
		
	}

}
