package PositionProcess.LargeScale;

public class Node
{
	final int id;
	boolean fixed;
	double x;
	double y;
	double subX;
	double subY;
	double energy;

	public Node(int id)
	{
		this.id = id;
		fixed = false;
		x = y = 0;
	}

	public Node(Node node)
	{
		this.id = node.id;
		this.fixed = node.fixed;
		this.energy = node.energy;
		this.x = node.x;
		this.y = node.y;
		this.subX = node.subX;
		this.subY = node.subY;
		this.energy = node.energy;
	}
}
