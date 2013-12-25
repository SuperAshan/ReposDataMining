package Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Data.RelationMatrix;

public class ConvertClass {
	
	//将库中定义的Relation转换成存放边的数组{1 2 1 3 1 5}
	public static int[] RelationToAdjancy(RelationMatrix matrixresult)
	{
		List<Integer> result=new ArrayList<Integer>();
		int len=matrixresult.getSize();
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<i;j++)
			{
				if(matrixresult.IsLinked(i, j))
				{
					result.add(i);
					result.add(j);
				}
			}
		}
		int resultlen=result.size();
		int[] adjacency= new int[resultlen];
		int loopnumber=0;
		for(Object number:result)
		{
			adjacency[loopnumber++]=(Integer)number;
		}
		return adjacency;
	}
	
	
	//带权重的转换
	public static double[] RelationToAdjancyWeight(RelationMatrix matrixresult)
	{
		List<Double> result=new ArrayList<Double>();
		int len=matrixresult.getSize();
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<i;j++)
			{
				if(matrixresult.IsLinked(i, j))
				{
					result.add((double) i);
					result.add((double) j);
					result.add((double) matrixresult.GetR(i, j));
				}
			}
		}
		int resultlen=result.size();
		double[] adjacency= new double[resultlen];
		int loopnumber=0;
		for(Object number:result)
		{
			adjacency[loopnumber++]=(Double)number;
		}
		return adjacency;
	}
	
	public static Map<Integer, List<Integer>> RelationToEdgeMap(RelationMatrix matrixresult)
	{
		Map<Integer, List<Integer>> result=new HashMap<Integer, List<Integer>>();
		
		int len=matrixresult.getSize();
		for(int i=0;i<len;i++)
		{
			List edge=new ArrayList<Integer>();
			for(int j=0;j<i;j++)
			{
				if(matrixresult.IsLinked(i, j))
				{
					edge.add(j);
				}
			}
		}
		
		return  result;
	}

}
