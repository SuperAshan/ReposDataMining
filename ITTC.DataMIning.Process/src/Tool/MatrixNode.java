/**
 * TODO
 */
package Tool;

/**
 * 2013年10月24日 by @author weitao TODO
 */
public class MatrixNode implements Comparable
{
	private int nodeIndex;
	private double nodeWeight;

	public MatrixNode(int nodeindex, int nodeweight)
	{
		this.nodeIndex = nodeindex;
		this.nodeWeight = nodeweight;
	}
	
	public MatrixNode(int nodeindex)
	{
		this.nodeIndex = nodeindex;
		this.nodeWeight = 1;
	}

	/*
	 * (non-Javadoc) TODO
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object arg0)
	{
		// TODO Auto-generated method stub
		MatrixNode nodeb = (MatrixNode) (arg0);
		if (this.nodeIndex > nodeb.nodeIndex)
		{
			return 1;
		} else
		{
			if (this.nodeIndex == nodeb.nodeIndex)
			{
				return 0;
			} else
			{
				return -1;
			}
		}
	}

	// public void setIndex(int index)
	// {
	// this.nodeIndex=index;
	// }

	public void setWeight(double nodeweight)
	{
		this.nodeWeight = nodeweight;
	}

	public boolean equal(Object o)
	{
		MatrixNode nodeb = (MatrixNode) (o);
		if (this.nodeIndex == nodeb.nodeIndex)
		{
			return true;
		}
		return false;
	}

	/**
	 * hashcode
	 * TODO   返回hashcode值，用来实现hashset
	 * @return
	 */
	public int hashcode()
	{
		return this.nodeIndex;
	}

}
