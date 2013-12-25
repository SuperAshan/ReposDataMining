package PositionProcess.PathWeigth;

import PositionProcess.Node;
import PositionProcess.PathWeigth.ForceFactory.RepulsionForce;

public class OperationNodeRegionRepulse extends Operation
{

	private final Node n;
	private final Region r;
	private final RepulsionForce f;
	private final double theta;

	public OperationNodeRegionRepulse(Node n, Region r, RepulsionForce f,
			double theta)
	{
		this.n = n;
		this.f = f;
		this.r = r;
		this.theta = theta;
	}

	@Override
	public void execute()
	{
		r.applyForce(n, f, theta);
	}
}
