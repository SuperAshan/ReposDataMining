package PositionProcess.PathWeigth;

import PositionProcess.Node;
import PositionProcess.PathWeigth.ForceFactory.RepulsionForce;

public class OperationNodeRepulse extends Operation
{

	private Node n;
	private RepulsionForce f;
	private double coefficient;

	public OperationNodeRepulse(Node n, RepulsionForce f, double coefficient)
	{
		this.n = n;
		this.f = f;
		this.coefficient = coefficient;
	}

	@Override
	public void execute()
	{
		f.apply(n, coefficient);
	}
}
