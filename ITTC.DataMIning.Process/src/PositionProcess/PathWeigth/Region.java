package PositionProcess.PathWeigth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import PositionProcess.Node;
import PositionProcess.PathWeigth.ForceFactory.RepulsionForce;

/**
 * Barnes Hut optimization
 * 
 * @author Mathieu Jacomy
 */
public class Region
{
	private double mass;
	private double massCenterX;
	private double massCenterY;
	private double size;
	private final List<Node> nodes;
	private final List<Region> subregions = new ArrayList<Region>();

	public Region(Node[] nodes)
	{
		this.nodes = new ArrayList<Node>();
		this.nodes.addAll(Arrays.asList(nodes));
		updateMassAndGeometry();
	}

	public Region(ArrayList<Node> nodes)
	{
		this.nodes = new ArrayList<Node>(nodes);
		updateMassAndGeometry();
	}

	private void updateMassAndGeometry()
	{
		if (nodes.size() > 1)
		{
			// Compute Mass
			mass = 0;
			double massSumX = 0;
			double massSumY = 0;
			for (Node n : nodes)
			{
				mass += n.getMass();
				massSumX += n.getX() * n.getMass();
				massSumY += n.getY() * n.getMass();
			}
			massCenterX = massSumX / mass;
			massCenterY = massSumY / mass;

			// Compute size
			size = Double.MIN_VALUE;
			for (Node n : nodes)
			{
				double distance = Math.sqrt((n.getX() - massCenterX)
						* (n.getX() - massCenterX) + (n.getY() - massCenterY)
						* (n.getY() - massCenterY));
				size = Math.max(size, 2 * distance);
			}
		}
	}

	public synchronized void buildSubRegions()
	{
		if (nodes.size() > 1)
		{
			ArrayList<Node> leftNodes = new ArrayList<Node>();
			ArrayList<Node> rightNodes = new ArrayList<Node>();
			for (Node n : nodes)
			{
				ArrayList<Node> nodesColumn = (n.getX() < massCenterX) ? (leftNodes)
						: (rightNodes);
				nodesColumn.add(n);
			}

			ArrayList<Node> topleftNodes = new ArrayList<Node>();
			ArrayList<Node> bottomleftNodes = new ArrayList<Node>();
			for (Node n : leftNodes)
			{
				ArrayList<Node> nodesLine = (n.getY() < massCenterY) ? (topleftNodes)
						: (bottomleftNodes);
				nodesLine.add(n);
			}

			ArrayList<Node> bottomrightNodes = new ArrayList<Node>();
			ArrayList<Node> toprightNodes = new ArrayList<Node>();
			for (Node n : rightNodes)
			{
				ArrayList<Node> nodesLine = (n.getY() < massCenterY) ? (toprightNodes)
						: (bottomrightNodes);
				nodesLine.add(n);
			}

			if (topleftNodes.size() > 0)
			{
				if (topleftNodes.size() < nodes.size())
				{
					Region subregion = new Region(topleftNodes);
					subregions.add(subregion);
				} else
				{
					for (Node n : topleftNodes)
					{
						ArrayList<Node> oneNodeList = new ArrayList<Node>();
						oneNodeList.add(n);
						Region subregion = new Region(oneNodeList);
						subregions.add(subregion);
					}
				}
			}
			if (bottomleftNodes.size() > 0)
			{
				if (bottomleftNodes.size() < nodes.size())
				{
					Region subregion = new Region(bottomleftNodes);
					subregions.add(subregion);
				} else
				{
					for (Node n : bottomleftNodes)
					{
						ArrayList<Node> oneNodeList = new ArrayList<Node>();
						oneNodeList.add(n);
						Region subregion = new Region(oneNodeList);
						subregions.add(subregion);
					}
				}
			}
			if (bottomrightNodes.size() > 0)
			{
				if (bottomrightNodes.size() < nodes.size())
				{
					Region subregion = new Region(bottomrightNodes);
					subregions.add(subregion);
				} else
				{
					for (Node n : bottomrightNodes)
					{
						ArrayList<Node> oneNodeList = new ArrayList<Node>();
						oneNodeList.add(n);
						Region subregion = new Region(oneNodeList);
						subregions.add(subregion);
					}
				}
			}
			if (toprightNodes.size() > 0)
			{
				if (toprightNodes.size() < nodes.size())
				{
					Region subregion = new Region(toprightNodes);
					subregions.add(subregion);
				} else
				{
					for (Node n : toprightNodes)
					{
						ArrayList<Node> oneNodeList = new ArrayList<Node>();
						oneNodeList.add(n);
						Region subregion = new Region(oneNodeList);
						subregions.add(subregion);
					}
				}
			}

			for (Region subregion : subregions)
			{
				subregion.buildSubRegions();
			}
		}
	}

	public void applyForce(Node n, RepulsionForce Force, double theta)
	{
		if (nodes.size() < 2)
		{
			Node regionNode = nodes.get(0);
			Force.apply(n, regionNode);
		} else
		{
			double distance = Math.sqrt((n.getX() - massCenterX)
					* (n.getX() - massCenterX) + (n.getY() - massCenterY)
					* (n.getY() - massCenterY));
			if (distance * theta > size)
			{
				Force.apply(n, this);
			} else
			{
				for (Region subregion : subregions)
				{
					subregion.applyForce(n, Force, theta);
				}
			}
		}
	}

	public double getMass()
	{
		return mass;
	}

	public void setMass(double mass)
	{
		this.mass = mass;
	}

	public double getMassCenterX()
	{
		return massCenterX;
	}

	public void setMassCenterX(double massCenterX)
	{
		this.massCenterX = massCenterX;
	}

	public double getMassCenterY()
	{
		return massCenterY;
	}

	public void setMassCenterY(double massCenterY)
	{
		this.massCenterY = massCenterY;
	}
}
