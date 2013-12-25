package PositionProcess;

public class Node
{
	private double x; /* x coordinate of each node */
	private double y; /* y coordinate of each node */
	private double mass; /* mass of each node */
	protected double freeze; 
	private double old_dx;
	private double old_dy;
	private double dx;
	private double dy;
	private double size = 10.0;

	public void resetNode() {
		this.x = (0.01 + Math.random()) * 1000 - 500;
		this.y = (0.01 + Math.random()) * 1000 - 500;		
		this.old_dx = 0.;
		this.old_dy = 0.;
		this.dx = 0.;
		this.dy = 0.;
		this.mass = 1.0;
		this.freeze = 0.;
	}
	
	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}
	
	public void addX(double adder)
	{
		this.x += adder;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public void addY(double adder)
	{
		this.y += adder;
	}

	
	public double getMass()
	{
		return mass;
	}

	public void setMass(double mass)
	{
		this.mass = mass;
	}

	public double getFreeze() {
		return freeze;
	}
	
	public void setFreeze(double freeze) {
		this.freeze = freeze;
	}
	
	public double getOld_dx()
	{
		return old_dx;
	}

	public void setOld_dx(double old_dx)
	{
		this.old_dx = old_dx;
	}

	public double getOld_dy()
	{
		return old_dy;
	}

	public void setOld_dy(double old_dy)
	{
		this.old_dy = old_dy;
	}

	public double getDx()
	{
		return dx;
	}

	public void setDx(double dx)
	{
		this.dx = dx;
	}
	
	public void addDx(double adder) {
		this.dx += adder;
	}
	
	public void mulDx(double factor) {
		this.dx *= factor;
	}

	public double getDy()
	{
		return dy;
	}

	public void setDy(double dy)
	{
		this.dy = dy;
	}
	
	public void addDy(double adder) {
		this.dy += adder;
	}

	public void mulDy(double factor) {
		this.dy *= factor;
	}
	
	public double getSize()
	{
		return size;
	}

	public void setSize(double size)
	{
		this.size = size;
	}
}
