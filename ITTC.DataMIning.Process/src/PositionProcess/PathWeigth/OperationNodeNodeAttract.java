package PositionProcess.PathWeigth;

import PositionProcess.Node;
import PositionProcess.PathWeigth.ForceFactory.AttractionForce;

public class OperationNodeNodeAttract extends Operation
{

	private final Node n1;
	private final Node n2;
	private final AttractionForce f;
	private final double coefficient;

	public OperationNodeNodeAttract(Node n1, Node n2, AttractionForce f,
			double coefficient)
	{
		this.n1 = n1;
		this.n2 = n2;
		this.f = f;
		this.coefficient = coefficient;
	}

	@Override
	public void execute()
	{
		f.apply(n1, n2, coefficient);
	}
}
