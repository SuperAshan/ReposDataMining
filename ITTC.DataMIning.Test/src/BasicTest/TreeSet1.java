package BasicTest;

import java.util.TreeSet;

public class TreeSet1 {
	public void Test()
	{
		TreeSet treeSet=new TreeSet();
		for(int i=0;i<5;i++)
		{
			treeSet.add(i);
		}
		TreeSet treeSet1=new TreeSet();
		for(int i=0;i<5;i++)
		{
			treeSet1.add(i);
		}
		treeSet.addAll(treeSet1);
        System.out.println(treeSet.size());		
	}

}
