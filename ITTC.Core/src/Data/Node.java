package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Helpers.MathHelper;

 



public class Node implements INode, IDictionarySerializable
{
    public Node()
    {
	linkedPathCollection = new ArrayList<LinkedPath>();
    }

    protected IRelationComputeable Data;

    public double GetRelation(IRelationComputeable r1, IRelationComputeable r2)
    {
	// TODO Auto-generated method stub
	return 0;
    }

    public Boolean Contains(String data)
    {
	// TODO Auto-generated method stub
	return Data.Contains(data);
    }

    public Object[] GetData()
    {
	// TODO Auto-generated method stub
	return null;
    }

    public void SetData(Object[] value)
    {
	// TODO Auto-generated method stub

    }

    public int compareTo(Object o)
    {
	// TODO Auto-generated method stub
	return 0;
    }

    public Iterable<LinkedPath> GetLinkedPath()
    {
	// TODO Auto-generated method stub
	return linkedPathCollection;
    }

    public String getKey()
    {
	// TODO Auto-generated method stub
	return Data.getKey();
    }

    public IRelationComputeable getData()
    {

	return Data;
    }

    public void setData(IRelationComputeable data)
    {

	Data = data;
    }

    protected int group = 0;

    public int getGroup()
    {
	// TODO Auto-generated method stub
	return group;
    }

    private double weight=1;
    protected double PositionX;
    protected double PositionY;
    protected double PositionZ;
    private double force;
    private List<LinkedPath> linkedPathCollection;

    public void setGroup(int group1)
    {
	group = group1;

    }

    public double getForce()
    {
	return force;
    }

    public double getPositionX()
    {
	return PositionX;
    }

    public void setPositionX(double value)
    {
	PositionX = value;

    }

    public double getPositionY()
    {
	return PositionY;
    }

    public void setPositionY(double value)
    {
	PositionY = value;
    }

    public double getPositionZ()
    {
	return PositionZ;
    }

    public void setPositionZ(double value)
    {
	PositionZ = value;

    }

    public List<LinkedPath> getLinkedPathCollection()
    {
	return linkedPathCollection;
    }
    
    public void setLinkedPathCollection(List<LinkedPath> linkedpathcollection)
    {
    	this.linkedPathCollection=linkedpathcollection;
    }

    public HashMap<String, Object> DictSerialize()
    {
	HashMap<String, Object> map = new HashMap<String, Object>();
	map.put("Key", getKey());
	map.put("X", MathHelper.roundNumber(PositionX, 3));
	map.put("Y", MathHelper.roundNumber(PositionY, 3));
	map.put("Z", MathHelper.roundNumber(PositionZ, 3));
	map.put("Group", group);
	map.put("Weight", MathHelper.roundNumber(getWeight(), 3));
	return map;

    }

    public void DictDeserialize(HashMap<String, Object> dict)
    {
	// TODO Auto-generated method stub

    }

    public double getWeight()
    {
	if (Data == null)
	    return weight;

	if (Data == this)
	    return weight;

	// TODO Auto-generated method stub
	return Data.getWeight();
    }

    
}
