/**
 * TODO
 */
package Tool;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

/**
 * 2013年10月26日 by @author weitao
 * TODO
 */
public class MatrixOperation
{
	//可以优化，只遍历有值的部分，稀疏矩阵相乘
	public static WeightAdjacementMatrix MatrixMulti(WeightAdjacementMatrix a,WeightAdjacementMatrix b)
	{
		int nodenumber=a.getNodenumber();
		WeightAdjacementMatrix resultAdjacementMatrix=new WeightAdjacementMatrix(nodenumber);
		for(int i=0;i<nodenumber;i++)
		{
			for(int j=0;j<nodenumber;j++)
			{
				double result=0;
				for(int k=0;k<nodenumber;k++)
				{
					result+=a.getValue(i, k)*b.getValue(k, j);
				}
				resultAdjacementMatrix.setValue(i, j, result);
			}
		}
		
		return resultAdjacementMatrix;
	}
	
	public static WeightAdjacementMatrix MatrixPow(WeightAdjacementMatrix a,int pown)
	{
		int nodenumber=a.getNodenumber();
		WeightAdjacementMatrix resultAdjacementMatrix=new WeightAdjacementMatrix(nodenumber);
		WeightAdjacementMatrix b=a.clone();
		for(int i=0;i<pown;i++)
		{
			resultAdjacementMatrix=MatrixMulti(a, b);
			b=resultAdjacementMatrix.clone();
		}
		b=null;
		return resultAdjacementMatrix;
	}
}
