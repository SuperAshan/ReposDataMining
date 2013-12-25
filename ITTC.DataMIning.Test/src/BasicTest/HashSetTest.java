/**
 * TODO
 */
package BasicTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * 2013年10月25日 by @author weitao
 * TODO
 */
public class HashSetTest
{
   public void Test()
   {
	   HashSet<Integer> hashSet=new HashSet<Integer>();
	   Map hsMap=new HashMap<Integer, Integer>();
	   hsMap.put(1, 1);
//	   hsMap.get(key)
	   for(int i=0;i<10;i++)
	   {
		   hashSet.add(i);
	   }
//	   for(int i=0;i<10;i++)
//	   {
		   Iterator itr=hashSet.iterator();
		   while(itr.hasNext())
		   {
			   System.out.println(itr.next().hashCode());
		   }
	//   }
	   
	   
   }
}
