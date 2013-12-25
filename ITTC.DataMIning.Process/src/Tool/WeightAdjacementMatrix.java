/**
 * TODO
 */
package Tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.sun.org.apache.xpath.internal.axes.IteratorPool;

/**
 * 2013年10月24日 by @author weitao
 * TODO
 */
public class WeightAdjacementMatrix
{
	
	private HashMap<Integer, HashMap<Integer,Nodeinfo>> adjacementMap; // 存放图的边
	private List<String> userNameList; // 每个点的信息
	private int nodenumber;

	/**
	 * @param InputMap
	 *            接受map形式的输入
	 */
	public WeightAdjacementMatrix(HashMap<Integer, HashMap<Integer,Nodeinfo>> InputMap,
			int nodenumber)
	{
		adjacementMap = new HashMap<Integer, HashMap<Integer, Nodeinfo>>();
		adjacementMap = InputMap;
		this.nodenumber = nodenumber;
	}
	
	public WeightAdjacementMatrix(AdjacementMatrix Input,int nodenumber)
	{
		HashMap<Integer,TreeSet<Integer>> temp=Input.getAdjacementMap();
		int number=temp.size();
		Set keysetSet=temp.keySet();
		Iterator iterator=keysetSet.iterator();
		HashMap<Integer,Nodeinfo>  map=new HashMap<Integer, Nodeinfo>();
		while(iterator.hasNext())
		{
			int key1=(Integer)iterator.next();
			TreeSet<Integer> set= temp.get(key1);
			Iterator iterator2=set.iterator();
			
			while(iterator2.hasNext())
			{
				map.put((Integer)iterator2.next(), new Nodeinfo());
			}
			this.adjacementMap.put(key1, map);
		}
		temp=null;
	}

	/**
	 * 无输入，执行过程中构造邻接矩阵
	 */
	public WeightAdjacementMatrix(int nodenumber)
	{
		adjacementMap = new HashMap<Integer, HashMap<Integer,Nodeinfo>>();
		this.nodenumber=nodenumber;
		

	}

	/**
	 * @param Username
	 *            设置用户名List
	 */
	public void setUserNameList(List<String> Username)
	{
		userNameList = new ArrayList();
		userNameList = Username;
	}

	/**
	 * @return 获得用户名list
	 */
	public List<String> getUserNameList()
	{
		return userNameList;
	}

	/**
	 * @return 得到矩阵的行数
	 */
	public int getSize()
	{
		// return adjacementMap.size();
		return this.getNodenumber();
	}

	/**
	 * @param xindex
	 *            
	 * @param yindex
	 *            矩阵的列坐标
	 */
	/**
	 * setValue
	 * TODO
	 * @param xindex 矩阵的横坐标
	 * @param yindex 矩阵的列坐标
	 * @param weight
	 */
	public void setValue(int xindex, int yindex,double weight)
	{
		if (adjacementMap != null)
		{
			if (adjacementMap.containsKey(xindex)){
				if(adjacementMap.get(xindex).containsKey(yindex))
				{
					adjacementMap.get(xindex).get(yindex).setWeight(weight);
				}
				else {
					adjacementMap.get(xindex).put(yindex, new Nodeinfo(weight));
				}
				
			}
			else
			{
				HashMap<Integer, Nodeinfo> neibornodeHashMap=new HashMap<Integer, Nodeinfo>();
				neibornodeHashMap.put(yindex, new Nodeinfo(weight));
				adjacementMap.put(xindex, neibornodeHashMap);
			}
		} else
		{
			adjacementMap = new HashMap<Integer,HashMap<Integer,Nodeinfo>>();
			HashMap<Integer, Nodeinfo> neibornodeHashMap=new HashMap<Integer, Nodeinfo>();
			neibornodeHashMap.put(yindex, new Nodeinfo(weight));
			adjacementMap.put(xindex, neibornodeHashMap);

		}
	}

	/**
	 * @param xindex
	 *            矩阵的横坐标
	 * @param yindex
	 *            矩阵的列坐标
	 * @return 返回处于当前横纵坐标的元素（有值为1，无值为0
	 */
	public double getValue(int xindex, int yindex)
	{
		if (adjacementMap != null)
		{
			if (adjacementMap.containsKey(xindex)){
				if(adjacementMap.get(xindex).containsKey(yindex))
				{
					return adjacementMap.get(xindex).get(yindex).getWeight();
				}
				
				
			}
		}
		return 0;
	}

	/**
	 * @param xindex
	 *            矩阵的横坐标
	 * @return 返回当前行的列数（即点的度)
	 */
	public int getRowLength(int xindex)
	{
		if(adjacementMap!=null)
		{
			if(adjacementMap.containsKey(xindex))
				return adjacementMap.get(xindex).size();
		}
		return 0;

	}

	/**
	 * @param xindex
	 *            根据横坐标，返回邻居节点集合
	 * @return
	 */
	public HashMap<Integer, Nodeinfo> get(int xindex)
	{
		if(adjacementMap!=null){
		if (adjacementMap.containsKey(xindex))
		{
			return adjacementMap.get(xindex);
		} 
		}
		return null;
	}

	/**
	 * @return 返回二维数组形式的矩阵
	 */
	public double[][] getMatrix()
	{
		int size = this.getNodenumber();
		double[][] Matrix = new double[size][];
		for (int i = 0; i < size; i++)
			Matrix[i] = new double[size-i];
		for (int i = 0; i < size; i++)
		{
			for (int j = i; j < size; j++)
			{
				Matrix[i][j] = this.getValue(i, j);
			}
		}
		return Matrix;
	}

	/**
	 * @param edgeFinalPointcollection
	 *            添加点所连接的边
	 */
	private void addValue(HashMap<Integer,Nodeinfo> edgeFinalPointcollection)
	{
		int key = this.getNodenumber();
		adjacementMap.put(key, edgeFinalPointcollection);
		Set keyset=edgeFinalPointcollection.keySet();
		Iterator iterator = keyset.iterator();
		while (iterator.hasNext())
		{
			int key1=(Integer)iterator.next();
			adjacementMap.get(key1).put(key, edgeFinalPointcollection.get(key1).clone());
		}
	}

	/**
	 * @param edgeFinalPointcollection
	 *            添加点所连接的边
	 * @param name
	 *            点的名称
	 */
	private void addValue(HashMap<Integer,Nodeinfo> edgeFinalPointcollection, String name)
	{
		int key = this.getNodenumber();
		adjacementMap.put(key, edgeFinalPointcollection);
		Set keyset=edgeFinalPointcollection.keySet();
		Iterator iterator = keyset.iterator();
		while (iterator.hasNext())
		{
			int key1=(Integer)iterator.next();
			adjacementMap.get(key1).put(key, edgeFinalPointcollection.get(key1).clone());
		}
		
		if (userNameList != null)
			userNameList.add(name);
		this.nodenumber++;
	}

	/**
	 * @param xindex
	 *            要删除的点的索引
	 */
	public void deleteValue(int xindex)
	{
		if (adjacementMap.containsKey(xindex))
		{
			HashMap<Integer,Nodeinfo> valueCollectionSet = adjacementMap.get(xindex);
			Set keyset=valueCollectionSet.keySet();
			Iterator iterator = keyset.iterator();
			while (iterator.hasNext())
			{
				adjacementMap.get(iterator.next()).remove(xindex);
			}
			adjacementMap.remove(xindex);
			if (userNameList != null)
				userNameList.remove(xindex);
			this.nodenumber--;
		} else
		{
			this.nodenumber--;
			
		}
	}

	/* (non-Javadoc)
	 *  复制矩阵
	 * @see java.lang.Object#clone()
	 */
	public WeightAdjacementMatrix clone()
	{
		HashMap<Integer, HashMap<Integer,Nodeinfo>> cloneadjacement=new HashMap<Integer, HashMap<Integer,Nodeinfo>>() ; // 存放图的边
        Set keySet=adjacementMap.keySet();
        Iterator iterator =keySet.iterator();
        while(iterator.hasNext())
        {
        	int key=(Integer) iterator.next();
        	HashMap<Integer, Nodeinfo> temp=adjacementMap.get(key);
        	HashMap<Integer, Nodeinfo> xinHashMap=new HashMap<Integer, Nodeinfo>();
        	 Set keySet1=temp.keySet();
             Iterator iterator1 =keySet1.iterator();
             while(iterator1.hasNext())
             {
            	 int key1=(Integer)iterator1.next();
            	 xinHashMap.put(key1, new Nodeinfo(temp.get(key1).getWeight()));
             }
             cloneadjacement.put(key, xinHashMap);

        }

		WeightAdjacementMatrix resultAdjacementMatrix = new WeightAdjacementMatrix(cloneadjacement, nodenumber);

		return resultAdjacementMatrix;
	}

	
	/**
	 * @return 返回矩阵的点数
	 */
	public int getNodenumber()
	{
		return nodenumber;
	}

	public void print()
	{

	}

}
