package Clusters;

 
 
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
 

public class LeaderCluster extends ClusterBase
{
    public LeaderCluster()
    {
	clusterResult=new TreeMap<Integer,List<Integer>>();
	
    }
    private double Leaderpara;

    protected    void ClusterProcess()
    {
	 this.Leaderpara = this.minRelation + (this.maxRelation - this.minRelation) * 0.10;

         

         this.clusterResult.put (0, new ArrayList<Integer>());
        
         for (int index = 1; index < this.nodes.size(); index++)
         {
          
             Data.INode node = this.nodes.get(index);

           
             int leaderIndex =  this.FindNearestLeader(index);
             Data.INode leaderNode = this.nodes.get(leaderIndex);
             if (index == leaderIndex)
             {
                 continue;
             }
             double dis =   matrix.GetR (index,leaderIndex);

             if (dis > this.Leaderpara)
             {
                 this.clusterResult.get(leaderIndex).add(index);
                 node.setGroup(leaderNode.getGroup());  
             }
             else
             {
        	 node.setGroup(this.clusterResult.size());  
                 
                 this.clusterResult.put(index, new  ArrayList<Integer>());
             }
         }
    }
    private int FindNearestLeader(int node)
    {
	 Set<Integer> array=  this.clusterResult.keySet();
	 int max=0;
	 double maxValue=0;
         for (Integer integer : array)
	{
	     double relation= matrix.GetR(node, integer);
	     if(relation>maxValue)
	     {
		 maxValue=relation;
		 max=integer;
	     }             
	}
         return max;
       
    }

}
