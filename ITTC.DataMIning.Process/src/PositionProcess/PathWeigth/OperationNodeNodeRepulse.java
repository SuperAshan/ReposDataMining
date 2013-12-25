package PositionProcess.PathWeigth;

import PositionProcess.Node;
import PositionProcess.PathWeigth.ForceFactory.RepulsionForce;

public class OperationNodeNodeRepulse extends Operation
{

	private final Node n1;
	private final Node n2;
	private final RepulsionForce f;

	public OperationNodeNodeRepulse(Node n1, Node n2, RepulsionForce f)
	{
		this.n1 = n1;
		this.n2 = n2;
		this.f = f;
	}

	@Override
	public void execute()
	{
		f.apply(n1, n2);
	}
}
