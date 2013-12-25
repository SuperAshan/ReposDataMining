package Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MatrixConverter
{

	private static Random rand = new Random();

	// public static void CreatePath(
	// List<INode> nodes,
	// RelationMatrix relationMatrix,
	// double threshold,
	// out double maxRelation,
	// out double minRelation)
	// {
	// double dMin = 100.0, dMax = 0.0;
	// relationMatrix.TraversalExecute(
	// (i, j, d) =>
	// {
	// if (d > threshold)
	// {
	// if (d > dMax)
	// {
	// dMax = d;
	// }
	// if (d < dMin)
	// {
	// dMin = d;
	// }
	// LinkedPath.BuildLinkedPath(nodes[i], nodes[j]);
	// }
	// });
	//
	// maxRelation = dMax;
	// minRelation = dMin;
	// }

	// public static void CreatePath(
	// Iterable<INode> nodes,
	// GetRelationMethod getRelationMethod,
	// double threshold,
	// out double maxRelation,
	// out double minRelation)
	// {
	// double dMin = 100.0, dMax = 0.0;
	// INode[] enumerable = nodes as INode[] ?? nodes.ToArray();
	// foreach (INode node1 in enumerable)
	// {
	// foreach (INode node2 in enumerable)
	// {
	// if (node1 == node2)
	// {
	// continue;
	// }
	// double d = getRelationMethod(node1.Data, node2.Data);
	// if (d > threshold)
	// {
	// if (d > dMax)
	// {
	// dMax = d;
	// }
	// if (d < dMin)
	// {
	// dMin = d;
	// }
	// LinkedPath.BuildLinkedPath(node1, node2);
	// }
	// }
	// }
	// maxRelation = dMax;
	// minRelation = dMin;
	// }

	// / <summary>
	// / 计算邻接矩阵中点之间的距离，点i和点j之间临接点的交集个数减去点i和点j之间邻接点的并集的个数
	// / </summary>
	// / <param name="i">点i</param>
	// / <param name="j">点j</param>
	// / <param name="nodeMatrix">原邻接矩阵</param>
	// / <param name="nodenumber">点个数</param>
	// / <returns>返回点i和点j之间的距离</returns>
	public static double EdgeLength(int i, int j, double[][] nodeMatrix,
			int nodenumber)
	{
		List degree1 = new ArrayList<Integer>();
		List degree2 = new ArrayList<Integer>();
		for (int k = 0; k < nodenumber; k++)
		{
			if (k < j + 1)
			{
				if (nodeMatrix[i][k] > 0 || nodeMatrix[j][k] > 0)
				{
					degree1.add(k);
				}
				if (nodeMatrix[i][k] > 0 && nodeMatrix[j][k] > 0)
				{
					degree2.add(k);
				}
			} else
			{
				if (k < i + 1)
				{
					if (nodeMatrix[k][j] > 0 || nodeMatrix[i][k] > 0)
					{
						degree1.add(k);
					}
					if (nodeMatrix[k][j] > 0 && nodeMatrix[i][k] > 0)
					{
						degree2.add(k);
					}
				} else
				{
					if (nodeMatrix[k][j] > 0 || nodeMatrix[k][i] > 0)
					{
						degree1.add(k);
					}
					if (nodeMatrix[k][j] > 0 && nodeMatrix[k][i] > 0)
					{
						degree2.add(k);
					}
				}
			}
		}

		return (degree1.size() - degree2.size());
	}

	public static double GetDistance(double x0, double y0, double x1, double y1)
	{
		return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));
	}

	public static double[][] GetLinkedMatrix(List<IPositionComputeable> nodes,
			Iterable<LinkedPath> linkedPaths)
	{
		int l = nodes.size();

		double[][] matrix = new double[l][];
		for (int i = 0; i < l; i++)
		{
			matrix[i] = new double[i + 1];
		}
		Iterator e = linkedPaths.iterator();
		while (e.hasNext())
		{
			int index1 = nodes.indexOf(((LinkedPath) e.next()).Node1);
			if (index1 <= -1)
			{
				continue;
			}
			int index2 = nodes.indexOf(((LinkedPath) e.next()).Node2);
			if (index2 > -1)
			{
				if (index1 > index2)
				{
					matrix[index1][index2] = 1;
				} else
				{
					matrix[index2][index1] = 1;
				}
			}

		}
		return matrix;
	}

	// public static double[][] GetLinkedMatrix(IList<IPositionComputeable>
	// nodes)
	// {
	// int l = nodes.Count;
	// var matrix = new double[l][];
	// for (int i = 0; i < l; i++)
	// {
	// matrix[i] = new double[i + 1];
	// }
	// for (int i = 0; i < nodes.Count; i++)
	// {
	// List<LinkedPath> links = nodes[i].LinkedPathCollection;
	// foreach (LinkedPath t in links)
	// {
	// int index = nodes.IndexOf(t.Node2);
	// if (index > -1)
	// {
	// if (i > index)
	// {
	// matrix[i][index] = 1;
	// }
	// }
	// }
	// }
	// return matrix;
	// }

	public static List<Integer> GetLinkedTable(
			List<IPositionComputeable> nodes, Iterable<LinkedPath> linkedPaths)
	{
		List edgecollection = new ArrayList<Integer>();
		Iterator e = linkedPaths.iterator();
		while (e.hasNext())
		{
			int index1 = nodes.indexOf(((LinkedPath) e.next()).Node1);
			if (index1 <= -1)
			{
				continue;
			}
			int index2 = nodes.indexOf(((LinkedPath) e.next()).Node2);
			if (index2 > -1) // && index1 > index2
			{
				edgecollection.add(index1);
				edgecollection.add(index2);
			}

		}

		return edgecollection;
	}

	public static List<Double> GetLinkedWeightTable(
			List<IPositionComputeable> nodes, Iterable<LinkedPath> allPath)
	{
		ArrayList edgecollection = new ArrayList<Double>();

		Iterator e = allPath.iterator();
		while (e.hasNext())
		{
			int index1 = nodes.indexOf(((LinkedPath) e.next()).Node1);
			if (index1 <= -1)
			{
				continue;
			}
			int index2 = nodes.indexOf(((LinkedPath) e.next()).Node2);
			if (index2 > -1 && index1 > index2) // && index1 > index2
			{
				edgecollection.add(index1);
				edgecollection.add(index2);
			}
			edgecollection.add(index1);
			edgecollection.add(index2);
			edgecollection.add(((LinkedPath) e.next()).Dist * 100.0);

		}

		return edgecollection;
	}

	// / <summary>
	// / 产生Lw矩阵
	// / </summary>
	// / <param name="Dij">最短路径矩阵</param>
	// / <param name="n">点个数</param>
	// / <returns>返回lw矩阵</returns>
	/*
	 * public static double[][] LwProduct(double[][] Dij, int n) { double[][] lw
	 * = new double[n][];
	 * 
	 * for (int i = 0; i < n; i++) { for (int j = 0; j < i; j++) { lw[i][j] =
	 * -(1 / (Dij[i][j] * Dij[i][j])); } } for (int i = 0; i < n; i++) { for
	 * (int k = 0; k < i; k++) { lw[i][i] += (-lw[i][k]); } for (int k = i + 1;
	 * k < n; k++) { lw[i][i] += (-lw[k][i]); } }
	 * 
	 * for (int j = 0; j < n; j++) { lw[j][0] = 0; }
	 * 
	 * return lw; }
	 */

	// / <summary>
	// / 处理孤立点
	// / </summary>
	// / <param name="nodeMatrix"></param>
	public static void OutlierProcess(double[][] nodeMatrix)
	{
		int n = nodeMatrix.length;
		double maxdistance = 0;
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < i; j++)
			{
				if (nodeMatrix[i][j] > maxdistance && nodeMatrix[i][j] < n * 10)
				{
					maxdistance = nodeMatrix[i][j];
				}
			}
		}

		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < i; j++)
			{
				if (nodeMatrix[i][j] > (n * 10 - 1))
				{
					nodeMatrix[i][j] = maxdistance + 2;
				}
			}
		}
	}

	public static double[] ProductMatrix(int nodeNumber)
	{
		double[] matrix = new double[nodeNumber];

		for (int i = 0; i < nodeNumber; i++)
		{
			if (i == 0)
			{
				matrix[i] = 0;
			} else
			{
				matrix[i] = rand.nextDouble() * 10;
			}
		}
		return matrix;
	}

	// / <summary>
	// / 将邻接矩阵打印到NodeMatrix1.txt
	// / </summary>
	// / <param name="nodeMatrix"></param>
	// public void PrintNodeMatrix(float[][] nodeMatrix)
	// {
	// string path = @"E:\";
	// var sw1 = new StreamWriter(path + @"NodeMatrix1.txt", true);
	// var stringBuilder1 = new StringBuilder();
	// int n = nodeMatrix.Length;
	// for (int i = 0; i < n; i++)
	// {
	// for (int j = 0; j < i + 1; j++)
	// {
	// stringBuilder1.Append(nodeMatrix[i][j].ToString());
	// stringBuilder1.Append(" ");
	// }
	// }
	// sw1.WriteLine(stringBuilder1.ToString());
	// sw1.Flush();
	// sw1.Close();
	// }

}
