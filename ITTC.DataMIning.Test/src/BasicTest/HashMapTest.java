/**
 * TODO
 */
package BasicTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 2013年10月25日 by @author weitao
 * TODO
 */
public class HashMapTest
{
     public void test()
     {
    	 HashMap<Integer, yingyongTest> a=new HashMap<Integer, yingyongTest>();
    	 a.put(1, new yingyongTest(2.5));
    	 HashMap<Integer, HashMap<Integer, yingyongTest>> map=new HashMap<Integer, HashMap<Integer, yingyongTest>>();
    	map.put(2, a);
    	HashMap<Integer, HashMap<Integer, yingyongTest>> map2=new HashMap<Integer, HashMap<Integer,yingyongTest>>();
    	Set keysetSet=map.keySet();
    	Iterator iterator=keysetSet.iterator();
    	while(iterator.hasNext())
    	{
    	}
 //   	HashMap<Integer, HashMap<Integer, yingyongTest>> map2=(HashMap<Integer, HashMap<Integer,yingyongTest>>)map.clone();
    	
    	System.out.println("改变之前:\t"+map.get(2).get(1).getWeight());
  //  	System.out.println("克隆之后:\t"+map2.get(2).get(1).getWeight());
    	map2.get(2).get(1).setWeight(100);
    	System.out.println("改变之后:");
    	System.out.println("1:\t"+map.get(2).get(1).getWeight());
    	System.out.println("2:\t"+map2.get(2).get(1).getWeight());
//    	 double c=a.get(1).getWeight();
//    	 System.out.println("改变之后"+c);
     }
}
