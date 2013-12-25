package BasicTest;

import java.util.Iterator;
import java.util.TreeSet;

public class cloneExample {
	public void test()
	{
		TreeSet<Integer> formerSet=new TreeSet<Integer>();
		for(int i=0;i<5;i++)
		{
			formerSet.add(i);
		}
		TreeSet<Integer> latterSet=new TreeSet<Integer>();
		latterSet=(TreeSet<Integer>)formerSet.clone();
		System.out.print("前：");
		Iterator<Integer> iterator=latterSet.iterator();
		while(iterator.hasNext())
		{
			System.out.print(iterator.next());
			System.out.print(" ");
		}
		System.out.print("\n");
		formerSet=new TreeSet<Integer>();
		System.out.print("后：");
		Iterator<Integer> iterator1=latterSet.iterator();
		while(iterator1.hasNext())
		{
			System.out.print(iterator1.next());
			System.out.print(" ");
		}
		System.out.print("\n");
		
		
		
	}

}
