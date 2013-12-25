package PositionProcess.PathWeigth;

import Data.RelationMatrix;
import PositionProcess.Node;
import PositionProcess.PathWeigth.ForceFactory.AttractionForce;
import PositionProcess.PathWeigth.ForceFactory.RepulsionForce;

public class ForceAtlas2
{
	private Node[] nodes;
	private int[] degree; /* degree of each node */
	private RelationMatrix relationMatrix;
	private double edgeWeightInfluence;
	private double jitterTolerance;
	private double scalingRatio;
	private double gravity;
	private double speed;
	private boolean outboundAttractionDistribution;
	private boolean adjustSizes;
	private boolean barnesHutOptimize;
	private double barnesHutTheta;
	private boolean linLogMode;
	private boolean strongGravityMode;
	private Region rootRegion;
	double outboundAttCompensation = 1;

	public ForceAtlas2(int nodeNum)
	{
		this.nodes = new Node[nodeNum];
		for (int i = 0; i < nodes.length; i++)
		{
			this.nodes[i] = new Node();
		}
		this.degree = new int[nodeNum];
	}

	public void initAlgo(RelationMatrix relationMatrix)
	{
		for (Node n : nodes)
		{
			n.resetNode();
		}
		speed = 1.0;
		this.relationMatrix = relationMatrix;
		int nodeNum = nodes.length;
		for (int i = 0; i < nodeNum; i++)
		{
			nodes[i].setMass(1 + degree[i]);
		}
	}

	public void goAlgo()
	{
		if (nodes.length == 0)
			return;
		// //initialize node data
		int nodeNum = nodes.length;
		for (Node n : nodes)
		{
			n.setOld_dx(n.getDx());
			n.setOld_dy(n.getDy());
			n.setDx(0);
			n.setDy(0);
		}

		// If Barnes Hut active, initialize root RegionImpl
		if (isBarnesHutOptimize())
		{
			rootRegion = new Region(nodes);
			rootRegion.buildSubRegions();
		}

		// If outboundAttractionDistribution active, compensate.
		if (isOutboundAttractionDistribution())
		{
			outboundAttCompensation = 0;
			for (Node n : nodes)
			{
				outboundAttCompensation += n.getMass();
			}
			outboundAttCompensation /= nodeNum;
		}

		// Repulsion (and gravity)
		// NB: Muti-threaded
		RepulsionForce Repulsion = ForceFactory.builder.buildRepulsion(
				isAdjustSizes(), getScalingRatio());
		int from = 0;
		int to = nodeNum;
		NodesThread nt = new NodesThread(nodes, from, to,
				isBarnesHutOptimize(), getBarnesHutTheta(), getGravity(),
				(isStrongGravityMode()) ? (ForceFactory.builder
						.getStrongGravity(getScalingRatio())) : (Repulsion),
				getScalingRatio(), rootRegion, Repulsion);
		nt.run();

		// Attraction
		AttractionForce Attraction = ForceFactory.builder
				.buildAttraction(
						isLinLogMode(),
						isOutboundAttractionDistribution(),
						isAdjustSizes(),
						1 * ((isOutboundAttractionDistribution()) ? (outboundAttCompensation)
								: (1)));
		if (getEdgeWeightInfluence() == 0)
		{
			for (int i = 0; i < relationMatrix.getSize(); i++)
			{
				for (int j = 0; j < i; j++) //relationMatrix.getSize()
				{
					if (relationMatrix.IsLinked(i, j))
					{
						Attraction.apply(nodes[i], nodes[j], 1);
					}
				}
			}
		} else if (getEdgeWeightInfluence() == 1)
		{
			for (int i = 0; i < relationMatrix.getSize(); i++)
			{
				for (int j = 0; j < i; j++) //relationMatrix.getSize()
				{
					if (relationMatrix.IsLinked(i, j))
					{
						Attraction.apply(nodes[i], nodes[j], getWeight(i, j));
					}
				}
			}
		} else
		{
			for (int i = 0; i < relationMatrix.getSize(); i++)
			{
				for (int j = 0; j < i; j++) //relationMatrix.getSize()
				{
					if (relationMatrix.IsLinked(i, j))
					{
						Attraction.apply(nodes[i], nodes[j], Math.pow(
								getWeight(i, j), getEdgeWeightInfluence()));
					}
				}
			}
		}

		// Auto adjust speed
		double totalSwinging = 0d; // How much irregular movement
		double totalEffectiveTraction = 0d; // Hom much useful movement
		for (Node n : nodes)
		{
			double swinging = Math.hypot(n.getOld_dx() - n.getDx(),
					n.getOld_dy() - n.getDy());
			totalSwinging += n.getMass() * swinging; // If the node has a burst
			// change of direction, then
			// it's not converging.
			totalEffectiveTraction += n.getMass()
					* 0.5
					* Math.hypot(n.getOld_dx() + n.getDx(),
							n.getOld_dy() + n.getDy());
		}
		// We want that swingingMovement < tolerance * convergenceMovement
		double targetSpeed = getJitterTolerance() * getJitterTolerance()
				* totalEffectiveTraction / totalSwinging;

		// But the speed shoudn't rise too much too quickly, since it would make
		// the convergence drop dramatically.
		double maxRise = 0.5; // Max rise: 50%
		speed = speed + Math.min(targetSpeed - speed, maxRise * speed);

		// Apply forces
		for (Node n : nodes)
		{
			// Adaptive auto-speed: the speed of each node is lowered
			// when the node swings.
			double swinging = Math.hypot(n.getOld_dx() - n.getDx(),
					n.getOld_dy() - n.getDy());
			// double factor = speed / (1f + Math.sqrt(speed * swinging));
			double factor = speed / (1 + speed * Math.sqrt(swinging));

			n.setX(n.getX() + n.getDx() * factor);
			n.setY(n.getY() + n.getDy() * factor);
		}
	}

	public boolean canAlgo()
	{
		return (nodes.length != 0);
	}

	public void endAlgo()
	{

	}

	public void resetPropertiesValues()
	{
		int nodesCount = nodes.length;
		// Tuning
		if (nodesCount >= 100)
		{
			setScalingRatio(2.0);
		} else
		{
			setScalingRatio(10.0);
		}
		setStrongGravityMode(false);
		setGravity(1.);

		// Behavior
		setOutboundAttractionDistribution(false);
		setLinLogMode(false);
		setAdjustSizes(false);
		setEdgeWeightInfluence(1.);

		// Performance
		if (nodesCount >= 50000)
		{
			setJitterTolerance(10d);
		} else if (nodesCount >= 5000)
		{
			setJitterTolerance(1d);
		} else
		{
			setJitterTolerance(0.1d);
		}
		if (nodesCount >= 1000)
		{
			setBarnesHutOptimize(true);
		} else
		{
			setBarnesHutOptimize(false);
		}
		setBarnesHutTheta(1.2);
	}

	public double[] getX()
	{
		double[] xcoor = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++)
		{
			xcoor[i] = nodes[i].getX();
		}
		return xcoor;
	}

	public double[] getY()
	{
		double[] ycoor = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++)
		{
			ycoor[i] = nodes[i].getY();
		}
		return ycoor;
	}

	public double getBarnesHutTheta()
	{
		return barnesHutTheta;
	}

	public void setBarnesHutTheta(double barnesHutTheta)
	{
		this.barnesHutTheta = barnesHutTheta;
	}

	public double getEdgeWeightInfluence()
	{
		return edgeWeightInfluence;
	}

	public void setEdgeWeightInfluence(double edgeWeightInfluence)
	{
		this.edgeWeightInfluence = edgeWeightInfluence;
	}

	public double getJitterTolerance()
	{
		return jitterTolerance;
	}

	public void setJitterTolerance(double jitterTolerance)
	{
		this.jitterTolerance = jitterTolerance;
	}

	public Boolean isLinLogMode()
	{
		return linLogMode;
	}

	public void setLinLogMode(Boolean linLogMode)
	{
		this.linLogMode = linLogMode;
	}

	public double getScalingRatio()
	{
		return scalingRatio;
	}

	public void setScalingRatio(double scalingRatio)
	{
		this.scalingRatio = scalingRatio;
	}

	public Boolean isStrongGravityMode()
	{
		return strongGravityMode;
	}

	public void setStrongGravityMode(Boolean strongGravityMode)
	{
		this.strongGravityMode = strongGravityMode;
	}

	public double getGravity()
	{
		return gravity;
	}

	public void setGravity(double gravity)
	{
		this.gravity = gravity;
	}

	public Boolean isOutboundAttractionDistribution()
	{
		return outboundAttractionDistribution;
	}

	public void setOutboundAttractionDistribution(
			Boolean outboundAttractionDistribution)
	{
		this.outboundAttractionDistribution = outboundAttractionDistribution;
	}

	public Boolean isAdjustSizes()
	{
		return adjustSizes;
	}

	public void setAdjustSizes(Boolean adjustSizes)
	{
		this.adjustSizes = adjustSizes;
	}

	public Boolean isBarnesHutOptimize()
	{
		return barnesHutOptimize;
	}

	public void setBarnesHutOptimize(Boolean barnesHutOptimize)
	{
		this.barnesHutOptimize = barnesHutOptimize;
	}

	private double getWeight(int sourceIdx, int targetIdx)
	{
		return relationMatrix.GetR(sourceIdx, targetIdx);
	}
}
