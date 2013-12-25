package PositionProcess.LargeScale;

import gnu.trove.TIntDoubleHashMap;
import gnu.trove.TIntDoubleIterator;

import java.util.Random;


public class Worker
{
	private boolean done = false;
	// Data
	private Node[] positions;
	private TIntDoubleHashMap[] neighbors;
	private DensityGrid densityGrid;
	private boolean firstAdd = true;
	private boolean fineFirstAdd = true;
	// Settings
	private double attraction;
	private int STAGE;
	private double temperature;
	private double dampingMult;
	private double minEdges;
	private double cutEnd;
	private double cutOffLength;
	private boolean fineDensity;
	protected Random random;

	public Worker()
	{
		this.densityGrid = new DensityGrid();
		this.densityGrid.init();
	}

	public void run()
	{
		// Updates nodes
		for (int i = 0; i < positions.length; i++)
		{
			updateNodePos(i);
		}

		firstAdd = false;
		if (fineDensity)
		{
			fineFirstAdd = false;
		}
	}

	private void updateNodePos(int nodeIndex)
	{
		Node n = positions[nodeIndex];
		if (n.fixed)
		{
			getNextRandom();
			getNextRandom();
			return;
		}

		double[] energies = new double[2];
		double[][] updatedPos = new double[2][2];
		double jumpLength = 0.01f * temperature;
		densityGrid.substract(n, firstAdd, fineFirstAdd, fineDensity);

		energies[0] = getNodeEnergy(nodeIndex);
		solveAnalytic(nodeIndex);
		updatedPos[0][0] = n.x;
		updatedPos[0][1] = n.y;

		updatedPos[1][0] = updatedPos[0][0] + (.5f - getNextRandom())
				* jumpLength;
		updatedPos[1][1] = updatedPos[0][1] + (.5f - getNextRandom())
				* jumpLength;

		n.x = updatedPos[1][0];
		n.y = updatedPos[1][1];
		energies[1] = getNodeEnergy(nodeIndex);

		if (energies[0] < energies[1])
		{
			n.x = updatedPos[0][0];
			n.y = updatedPos[0][1];
			n.energy = energies[0];
		} else
		{
			n.x = updatedPos[1][0];
			n.y = updatedPos[1][1];
			n.energy = energies[1];
		}

		densityGrid.add(n, fineDensity);
	}

	private double getNodeEnergy(int nodeIndex)
	{
		double attraction_factor = attraction * attraction * attraction
				* attraction * 2e-2;

		double xDis, yDis;
		double energyDistance;
		double nodeEnergy = 0;

		Node n = positions[nodeIndex];

		if (neighbors[nodeIndex] != null)
		{
			for (TIntDoubleIterator itr = neighbors[nodeIndex].iterator(); itr
					.hasNext();)
			{
				itr.advance();
				double weight = itr.value();
				Node m = positions[itr.key()];

				xDis = n.x - m.x;
				yDis = n.y - m.y;

				energyDistance = xDis * xDis + yDis * yDis;
				if (STAGE < 2)
				{
					energyDistance *= energyDistance;
				}

				if (STAGE == 0)
				{
					energyDistance *= energyDistance;
				}

				nodeEnergy += weight * attraction_factor * energyDistance;
			}
		}

		nodeEnergy += densityGrid.getDensity(n.x, n.y, fineDensity);

		return nodeEnergy;
	}

	private void solveAnalytic(int nodeIndex)
	{
		double totalWeight = 0;
		double xDis, yDis, xCen = 0, yCen = 0;
		double x = 0, y = 0;
		double damping;

		TIntDoubleHashMap map = neighbors[nodeIndex];
		if (map != null)
		{
			Node n = positions[nodeIndex];
			for (TIntDoubleIterator itr = map.iterator(); itr.hasNext();)
			{
				itr.advance();
				double weight = itr.value();
				Node m = positions[itr.key()];
				totalWeight += weight;
				x += weight * m.x;
				y += weight * m.y;
			}
			if (totalWeight > 0)
			{
				xCen = x / totalWeight;
				yCen = y / totalWeight;
				damping = 1f - dampingMult;
				double posX = damping * n.x + (1f - damping) * xCen;
				double posY = damping * n.y + (1f - damping) * yCen;
				n.x = posX;
				n.y = posY;
			}

			if (minEdges == 99)
			{
				return;
			}
			if (cutEnd >= 39500)
			{
				return;
			}

			double maxLength = 0;
			int maxIndex = -1;
			int neighborsCount = map.size();
			if (neighborsCount >= minEdges)
			{
				for (TIntDoubleIterator itr = neighbors[nodeIndex].iterator(); itr
						.hasNext();)
				{
					itr.advance();
					Node m = positions[itr.key()];

					xDis = xCen - m.x;
					yDis = yCen - m.y;
					double dis = xDis * xDis + yDis * yDis;
					dis *= Math.sqrt(neighborsCount);
					if (dis > maxLength)
					{
						maxLength = dis;
						maxIndex = itr.key();
					}
				}
			}

			if (maxLength > cutOffLength && maxIndex != -1)
			{
				map.remove(maxIndex);
			}
		}
	}

	public double getTotEnergy()
	{
		double myTotEnergy = 0;
		for (int i = 0; i < positions.length; i++)
		{
			myTotEnergy += positions[i].energy;
		}
		return myTotEnergy;
	}

	public double getNextRandom()
	{
		double rand = 0;
		rand = random.nextFloat();
		return rand;
	}

	public boolean isDone()
	{
		return done;
	}

	public void setDone(boolean done)
	{
		this.done = done;
	}

	public void setPositions(Node[] positions)
	{
		this.positions = positions;
	}

	public void setNeighbors(TIntDoubleHashMap[] neighbors)
	{
		this.neighbors = neighbors;
	}

	public Node[] getPositions()
	{
		return positions;
	}

	public boolean isFineDensity()
	{
		return fineDensity;
	}

	public boolean isFineFirstAdd()
	{
		return fineFirstAdd;
	}

	public boolean isFirstAdd()
	{
		return firstAdd;
	}

	public DensityGrid getDensityGrid()
	{
		return densityGrid;
	}

	public TIntDoubleHashMap[] getNeighbors()
	{
		return neighbors;
	}

	public void setSTAGE(int STAGE)
	{
		this.STAGE = STAGE;
	}

	public void setAttraction(double attraction)
	{
		this.attraction = attraction;
	}

	public void setCutOffLength(double cutOffLength)
	{
		this.cutOffLength = cutOffLength;
	}

	public void setDampingMult(double dampingMult)
	{
		this.dampingMult = dampingMult;
	}

	public void setMinEdges(double minEdges)
	{
		this.minEdges = minEdges;
	}

	public void setTemperature(double temperature)
	{
		this.temperature = temperature;
	}

	public void setRandom(Random random)
	{
		this.random = random;
	}

	public void setFineDensity(boolean fineDensity)
	{
		this.fineDensity = fineDensity;
	}

	public void setDensityGrid(DensityGrid densityGrid)
	{
		this.densityGrid = densityGrid;
	}
}
