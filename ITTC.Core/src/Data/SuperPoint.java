package Data;

import java.awt.Point;

public class SuperPoint implements Comparable<Object>
{
	public SuperPoint(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	private double x;

	public double getX()
	{
		return x;
	}

	private void setX(double x)
	{
		this.x = x;
	}

	private double y;

	public double getY()
	{
		return y;
	}

	private void setY(double y)
	{
		this.y = y;
	}

	public Point getPoint()
	{
	  return new Point((int)x,(int)y);
	}
	public int compareTo(Object o)
	{
		// TODO Auto-generated method stub
		SuperPoint point = (SuperPoint) o;
		if (this.getX() > point.getX())
			return 1;
		else if (this.getX() < point.getX())
		{
			return -1;
		} else
		{
			if (this.y > point.y)
				return 1;
			else if (this.y < point.y)
			{
				return -1;
			}
			return 0;
		}
	}

}
