package PositionProcess.LargeScale;

import java.util.Random;

import gnu.trove.TIntDoubleHashMap;
import gnu.trove.TIntDoubleIterator;
import gnu.trove.TIntHashingStrategy;
import gnu.trove.TIntIntHashMap;
import Data.RelationMatrix;

 
public class OpenOrdLayout
{
	public Node[] nodes;
	// Architecture
	private boolean running = true;
	// Settings
	private Params param;
	private double edgeCut;
	private long randSeed;
	private int numIterations;
	private double realTime;
	// Layout
	private Worker worker;
	private Combine combine;
	private Control control;
	private boolean firstIteration = true;

	public OpenOrdLayout(int nodeNum)
	{
		this.nodes = new Node[nodeNum];
	}

	public void resetPropertiesValues()
	{
		edgeCut = 0.8f;
		numIterations = 750;
		Random r = new Random();
		randSeed = r.nextLong();
		running = true;
		realTime = 0.2f;
		param = Params.DEFAULT;
	}

	public void initAlgo(RelationMatrix relationMatrix)
	{
		// Verify param
		if (param.getIterationsSum() != 1f)
		{
			param = Params.DEFAULT;
		}
		// Get the count of nodes
		int nodeNum = nodes.length;
		// Prepare data structure - nodes and neighbors map
		TIntDoubleHashMap[] neighbors = new TIntDoubleHashMap[nodeNum];
		TIntHashingStrategy hashingStrategy = new TIntHashingStrategy()
		{
			@Override
			public int computeHashCode(int i)
			{
				return i;
			}
		};
		// Load nodes and edges
		TIntIntHashMap idMap = new TIntIntHashMap(nodeNum, 1f);
		for (int i = 0; i < nodeNum; i++)
		{
			nodes[i] = new Node(i);
			nodes[i].x = (0.01 + Math.random()) * 1000 - 500;
			nodes[i].y = (0.01 + Math.random()) * 1000 - 500;
			nodes[i].fixed = false;
			idMap.put(i + 1, i);
		}
		double highestSimilarity = Double.NEGATIVE_INFINITY;
		for (int i = 0; i < relationMatrix.getSize(); i++)
		{
			for (int j = 0; j < relationMatrix.getSize(); j++)
			{
				int source = idMap.get(i + 1);
				int target = idMap.get(j + 1);
				if (source != target)
				{ // No self-loop
					double weight = getWeight(i);
					if (neighbors[source] == null)
					{
						neighbors[source] = new TIntDoubleHashMap(
								hashingStrategy);
					}
					if (neighbors[target] == null)
					{
						neighbors[target] = new TIntDoubleHashMap(
								hashingStrategy);
					}
					neighbors[source].put(target, weight);
					neighbors[target].put(source, weight);
					highestSimilarity = Math.max(highestSimilarity, weight);
				}
			}
		}

		// Reset position
		boolean someFixed = false;
		for (int i = 0; i < nodes.length; i++)
		{
			Node n = nodes[i];
			if (!n.fixed)
			{
				n.x = 0;
				n.y = 0;
			} else
			{
				someFixed = true;
			}
		}

		// Recenter fixed nodes and rescale to fit into grid
		if (someFixed)
		{
			double minX = Double.POSITIVE_INFINITY;
			double maxX = Double.NEGATIVE_INFINITY;
			double minY = Double.POSITIVE_INFINITY;
			double maxY = Double.NEGATIVE_INFINITY;
			for (int i = 0; i < nodes.length; i++)
			{
				Node n = nodes[i];
				if (n.fixed)
				{
					minX = Math.min(minX, n.x);
					maxX = Math.max(maxX, n.x);
					minY = Math.min(minY, n.y);
					maxY = Math.max(maxY, n.y);
				}
			}
			double shiftX = minX + (maxX - minX) / 2f;
			double shiftY = minY + (maxY - minY) / 2f;
			double ratio = Math.min(DensityGrid.getViewSize() / (maxX - minX),
					DensityGrid.getViewSize() / (maxY - minY));
			ratio = Math.min(1f, ratio);
			for (int i = 0; i < nodes.length; i++)
			{
				Node n = nodes[i];
				if (n.fixed)
				{
					n.x = (double) (n.x - shiftX) * ratio;
					n.y = (double) (n.y - shiftY) * ratio;
				}
			}
		}

		// Init control and workers
		control = new Control();
		combine = new Combine(this);
		control.setEdgeCut(edgeCut);
		control.setRealParm(realTime);
		control.initParams(param, numIterations);
		control.setNumNodes(nodeNum);
		control.setHighestSimilarity(highestSimilarity);

		worker = new Worker();
		worker.setRandom(new Random(randSeed));
		control.initWorker(worker);

		// Load workers with data
		// Deep copy of all nodes positions
		// Deep copy of a partition of all neighbors for each workers
		Node[] nodesCopy = new Node[nodes.length];
		for (int i = 0; i < nodes.length; i++)
		{
			nodesCopy[i] = new Node(nodes[i]);
		}
		TIntDoubleHashMap[] neighborsCopy = new TIntDoubleHashMap[nodeNum];
		for (int i = 0; i < neighbors.length; i++)
		{
			if (neighbors[i] != null)
			{
				int neighborsCount = neighbors[i].size();
				neighborsCopy[i] = new TIntDoubleHashMap(neighborsCount, 1f,
						hashingStrategy);
				for (TIntDoubleIterator itr = neighbors[i].iterator(); itr
						.hasNext();)
				{
					itr.advance();
					double weight = normalizeWeight(itr.value(),
							highestSimilarity);
					neighborsCopy[i].put(itr.key(), weight);
				}
			}
		}
		worker.setPositions(nodesCopy);
		worker.setNeighbors(neighborsCopy);

		// Add real nodes
		for (int i = 0; i < nodes.length; i++)
		{
			Node n = nodes[i];
			if (n.fixed)
			{
				worker.getDensityGrid().add(n, worker.isFineDensity());
			}
		}

		running = true;
		firstIteration = true;
	}

	public void goAlgo()
	{
		if (firstIteration)
		{
			combine.run();
		}
		firstIteration = false;

		combine.run();
	}

	public void endAlgo()
	{
		running = false;
		combine = null;
	}

	private double normalizeWeight(double weight, double highestSimilarity)
	{
		weight /= highestSimilarity;
		weight = weight * Math.abs(weight);
		return weight;
	}

	public boolean canAlgo()
	{
		return running;
	}

	public double getEdgeCut()
	{
		return edgeCut;
	}

	public void setEdgeCut(double edgeCut)
	{
		edgeCut = Math.min(1f, edgeCut);
		edgeCut = Math.max(0, edgeCut);
		this.edgeCut = edgeCut;
	}

	public Long getRandSeed()
	{
		return randSeed;
	}

	public void setRandSeed(Long randSeed)
	{
		this.randSeed = randSeed;
	}

	public void setRunning(Boolean running)
	{
		this.running = running;
	}

	public int getNumIterations()
	{
		return numIterations;
	}

	public void setNumIterations(int numIterations)
	{
		numIterations = Math.max(100, numIterations);
		this.numIterations = numIterations;
	}

	public double getRealTime()
	{
		return realTime;
	}

	public void setRealTime(double realTime)
	{
		realTime = Math.min(1f, realTime);
		realTime = Math.max(0, realTime);
		this.realTime = realTime;
	}

	public int getLiquidStage()
	{
		return param.getLiquid().getIterationsPercentage();
	}

	public int getExpansionStage()
	{
		return param.getExpansion().getIterationsPercentage();
	}

	public int getCooldownStage()
	{
		return param.getCooldown().getIterationsPercentage();
	}

	public int getCrunchStage()
	{
		return param.getCrunch().getIterationsPercentage();
	}

	public int getSimmerStage()
	{
		return param.getSimmer().getIterationsPercentage();
	}

	public void setLiquidStage(int value)
	{
		int v = Math.min(100, value);
		v = Math.max(0, v);
		param.getLiquid().setIterations(v / 100f);
	}

	public void setExpansionStage(int value)
	{
		int v = Math.min(100, value);
		v = Math.max(0, v);
		param.getExpansion().setIterations(v / 100f);
	}

	public void setCooldownStage(int value)
	{
		int v = Math.min(100, value);
		v = Math.max(0, v);
		param.getCooldown().setIterations(v / 100f);
	}

	public void setCrunchStage(int value)
	{
		int v = Math.min(100, value);
		v = Math.max(0, v);
		param.getCrunch().setIterations(v / 100f);
	}

	public void setSimmerStage(int value)
	{
		int v = Math.min(100, value);
		v = Math.max(0, v);
		param.getSimmer().setIterations(v / 100f);
	}

	public Worker getWorker()
	{
		return worker;
	}

	public Control getControl()
	{
		return control;
	}

	public boolean cancel()
	{
		return true;
	}

	public double getWeight(int edgeIdx)
	{
		return 1.0;
	}

	public double[] getX()
	{
		double[] xcoor = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++)
		{
			xcoor[i] = nodes[i].x;
		}
		return xcoor;
	}

	public double[] getY()
	{
		double[] ycoor = new double[nodes.length];
		for (int i = 0; i < nodes.length; i++)
		{
			ycoor[i] = nodes[i].y;
		}
		return ycoor;
	}
}
