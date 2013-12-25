/**
 * TODO
 */
package Tool;

/**
 * 2013年10月25日 by @author weitao
 * TODO
 */
public class Nodeinfo
{
	private double weight;

	public double getWeight()
	{
		return weight;
	}

	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	/**
	 * TODO
	 */
	public Nodeinfo(double weightvalue)
	{
		// TODO Auto-generated constructor stub
		this.weight=weightvalue;
	}
	
	public Nodeinfo()
	{
		// TODO Auto-generated constructor stub
		this.weight=1;
	}
	
	public Nodeinfo clone()
	{
		return(new Nodeinfo(this.weight));
	}

}
