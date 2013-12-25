package Process;

import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import Data.CompressType;
import Data.IObjectRelation;
import Data.IRelationComputeable;
import Data.RelationMatrix;
import Datas.UserData;
 

public class UserRelationCalcultor extends Core.WeiboProcessBase implements
	 IObjectRelation
{
    SortedMap<Long, SortedSet<Long>> relationMap;
    List<Data.IRelationComputeable>dataSource;
    Data.RelationMatrix relationMatrix;
    public boolean InitProcess()
    {
	relationMap = new TreeMap<Long, SortedSet<Long>>();
	
	return true;

    }

    public boolean DataProcess()
    {
	 
	relationMatrix=new RelationMatrix(dataSource.size(), CompressType.Matrix);
	relationMap.clear();
	for (Data.IRelationComputeable data : dataSource)
	{
	    Datas.UserData userData = (UserData) data;
	    List<Long> relations = WeiboAPIService.GetRelationIDs(
		    Core.RelationType.FriendsOnBilateral, String.valueOf(userData.getID()), 2000);
	    relationMap.put(userData.getID(), new TreeSet<Long>(relations));
	}
	
	 for (int i = 0; i < dataSource.size(); i++)
	{
	    for (int j = 0; j < dataSource.size(); j++)
	    {
		if(j==i)
		    continue;
		float relation = (float) GetRelation(dataSource.get(i), dataSource.get(j));
		if(relation!=0)
	        	relationMatrix.SetR(i, j, relation);
	    }
	}

	// TODO Auto-generated method stub

	return false;
    }

    private double GetRelation(IRelationComputeable data1,
	    IRelationComputeable data2)
    {
	UserData user1 = (UserData) data1;
	UserData user2 = (UserData) data2;
	long userId1 = user1.getID();
	long userId2 = user2.getID();
	double rela = 0.0;
	int friendsNum = 0;
	SortedSet<Long> friends1 = this.relationMap.get(userId1);
	SortedSet<Long> friends2 = this.relationMap.get(userId2);
	if (friends2.contains(userId1))
	{
	    friendsNum += 10;
	    
	
	for (Long long1 : friends2)
	    {

		if (friends1.contains(long1)==true)
		{
		    friendsNum += 1;
		}
	    }}
	if (friends1.contains(userId2))
	{
	    friendsNum += 10;
	    

	for (Long long1 : friends1)
	    {

		if (friends2.contains(long1)==true)
		{
		    friendsNum += 1;
		}
	    }
	}
	rela = friendsNum / 600.0;
	return rela;

    }

    public boolean CloseProcess()
    {
	// TODO Auto-generated method stub
	return false;
    }

  

    public Data.RelationMatrix getRelationMatrix()
    {
	 
	return  relationMatrix;
    }

    public void setDataSource(List<IRelationComputeable> data)
    {
	dataSource=data;
	
    }

    public String getName()
    {
	 return "用户关系计算";
 
    }

    public List<IRelationComputeable> getDataSource()
    {
	 return dataSource;
 
    }

}
