package Data;

//import java.util.Comparator;
import java.util.HashMap;

 

public class LinkedPath implements IDictionarySerializable,Comparable<LinkedPath>
    // / <summary>
{
    // / 两点间的距离
    // / </summary>
    public double Dist = 0;

    public LinkedPath(INode node12, INode node22)
    {
	Node1 = node12;
	Node2 = node22;
    }

    public HashMap<String, Object> DictSerialize()
    {
	 HashMap<String, Object> map=new HashMap<String, Object>();
	 map.put("Node1", Node1.getKey());
	 map.put("Node2", Node2.getKey());
	 map.put("Dist", Dist);
	 
	 return map;
    }

    public void DictDeserialize(HashMap<String, Object> dict)
    {
	// TODO Auto-generated method stub

    }

    // / <summary>
    // / Path节点1
    // / </summary>
    public INode Node1;

    // / <summary>
    // / Path节点2
    // / </summary>
    public INode Node2;

    // / <summary>
    // / 数据上下文
    // / </summary>
    public Object Context;

    public static void BuildLinkedPath(INode node12, INode node22)
    {
	node12.getLinkedPathCollection().add(new LinkedPath(node12, node22));
	node22.getLinkedPathCollection().add(new LinkedPath(node22, node12));
    }

//	@Override
//	public int compare(Object o1, Object o2) {
//		// TODO Auto-generated method stub
//		LinkedPath linkedpath1=(LinkedPath) o1;
//		LinkedPath linkedpath2=(LinkedPath) o2;
//		
//		if(linkedpath1.Dist>linkedpath2.Dist)
//			return 1;
//		else if(linkedpath1.Dist==linkedpath2.Dist)
//			return 0;
//		else
//			return -1;
//		
//	//	return linkedpath1.Dist.compareTo(linkedpath2.Dist);
//		
//		
////		int flag=linkedpath1.getAge().compareTo(user1.getAge());
////		  if(flag==0){
////		   return user0.getName().compareTo(user1.getName());
////		  }else{
////		   return flag;
////		  }  
////		return 0;
//	}

//	@Override
//	public int compare(LinkedPath o1, LinkedPath o2) {
//		// TODO Auto-generated method stub
//		if(o1.Dist>o2.Dist)
//			return 1;
//		else if(o1.Dist==o2.Dist)
//			return 0;
//		else
//			return -1;
//
//	}

	@Override
	public int compareTo(LinkedPath o) {
		// TODO Auto-generated method stub
		if(this.Dist>o.Dist)
			return 1;
		else if(this.Dist==o.Dist)
			return 0;
		else
			return -1;
	}

}
