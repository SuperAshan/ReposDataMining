package Statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Data.Node;

public class NetworkStatistic {
	
	public List<Node> nodeInputList;     
	public int[][] adjaceInputMatrix;    //上三角矩阵
	public Map<Integer, List<Integer>> edgeInputMap;  //Map<点的序号，连接的点的序号>
	
	private List<Integer> degreeDistributionX;  //度分布
	private List<Integer> degreeDistributionY;
	private double clusteringCoefficient;       //簇系数
	private List<Double> nodeClusterCoffcient;
	private int nodenumber;
	
	
	/**
	 * @return the degreeDistributionY
	 */
	public List<Integer> getDegreeDistributionY() {
		return degreeDistributionY;
	}


	/**
	 * @return the degreeDistributionX
	 */
	public List<Integer> getDegreeDistributionX() {
		return degreeDistributionX;
	}

	

	/**
	 * @return the clusteringCoefficient
	 */
	private double getClusteringCoefficient() {
		return clusteringCoefficient;
	}


	public NetworkStatistic(ArrayList<Node> dataInput)
	{
		nodeInputList=new ArrayList<Node>();
		nodeInputList=dataInput;
	}
	
	public NetworkStatistic(int[][] dataInput)
	{
		int nodenumber=dataInput.length;
		adjaceInputMatrix=new int[nodenumber][];
		for(int i=0;i<nodenumber;i++)
			adjaceInputMatrix[i]=new int[nodenumber-i];
	}
	
	public NetworkStatistic(Map<Integer, List<Integer>> edgeInput,int nodenubmertemp)
	{
		edgeInputMap=new HashMap<Integer, List<Integer>>();
		edgeInputMap=edgeInput;
		this.nodenumber=nodenubmertemp;
	}
	
	public void InitializeProcess()
	{
		nodenumber=adjaceInputMatrix.length;
		degreeDistributionX=new ArrayList<Integer>();
		degreeDistributionY=new ArrayList<Integer>();
		
		nodeClusterCoffcient=new ArrayList<Double>();
	}
	
	public void DataProcess()
	{
		double nodePossibleNeiboredge=nodenumber*(nodenumber-1)/2;
		int[] nodedegree=new int[nodenumber];
		
		for(int i=0;i<nodenumber;i++)                             //统计每个点的度和聚类系数
		{
			int nodeNeiberEdges=0;
			List<Integer> neiborlist=new ArrayList<Integer>();
			for(int j=i;j<nodenumber;j++)
			{
				if(adjaceInputMatrix[i][j]==1) 
				{
					nodedegree[i]++;
					neiborlist.add(j);
				}
			}
			for(int k=0;k<i;k++)
			{
				if(adjaceInputMatrix[k][i]==1) 
				{
					nodedegree[i]++;
					neiborlist.add(k);
				}
			}
			
			int nodeNeiborNumber=neiborlist.size();
			for(int j=0;j<nodeNeiborNumber;j++)
			{
				for(int k=j+1;k<nodeNeiborNumber;k++)
				{
					int xindex=neiborlist.get(j);
					int yindex=neiborlist.get(j);
					if(adjaceInputMatrix[Math.min(xindex, yindex)][Math.max(xindex,yindex)]==1)
						nodeNeiberEdges++;
					
				}
			}
			double nodeclustecoffcient=(double)nodePossibleNeiboredge/nodePossibleNeiboredge;
			nodeClusterCoffcient.add(nodeclustecoffcient);
			clusteringCoefficient+=nodeclustecoffcient;
		}
		clusteringCoefficient/=nodenumber;
		for(int i=0;i<nodedegree.length;i++)
		{
			int index=0;
			index=degreeDistributionX.indexOf(nodedegree[i]);
			
			if(index<0)
			{
				degreeDistributionX.add(nodedegree[i]);
				degreeDistributionY.add(1);
			}
			else
			{
				int degree=degreeDistributionX.get(index);
				degreeDistributionY.set(index, degree++);
			}
		}
		
	}

}
