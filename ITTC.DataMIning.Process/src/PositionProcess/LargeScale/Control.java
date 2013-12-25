package PositionProcess.LargeScale;

public class Control
{
	// Settings
	private int STAGE;
	private int iterations;
	private double temperature;
	private double attraction;
	private double dampingMult;
	private double minEdges;
	private double cutEnd;
	private double cutLengthEnd;
	private double cutOffLength;
	private double cutRate;
	private boolean fineDensity;
	// Vars
	private double edgeCut;
	private double realParm;
	// Exec
	private int nodeNum;
	private double highestSimilarity;
	private int realIterations;
	private boolean realFixed;
	private int totIterations;
	private int totExpectedIterations;
	private Params params;

	public void initParams(Params params, int totalIterations)
	{
		this.params = params;
		STAGE = 0;
		iterations = 0;
		initStage(params.getInitial());
		minEdges = 20;
		fineDensity = false;

		cutEnd = cutLengthEnd = 40000f * (1f - edgeCut);
		if (cutLengthEnd <= 1f)
		{
			cutLengthEnd = 1f;
		}

		double cutLengthStart = 4f * cutLengthEnd;

		cutOffLength = cutLengthStart;
		cutRate = (cutLengthStart - cutLengthEnd) / 400f;

		totExpectedIterations = totalIterations;

		int fullCompIters = totExpectedIterations + 3;

		if (realParm < 0)
		{
			realIterations = (int) realParm;
		} else if (realParm == 1)
		{
			realIterations = fullCompIters
					+ params.getSimmer().getIterationsTotal(totalIterations)
					+ 100;
		} else
		{
			realIterations = (int) (realParm * fullCompIters);
		}
		if (realIterations > 0)
		{
			realFixed = true;
		} else
		{
			realFixed = false;
		}
	}

	private void initStage(Params.Stage stage)
	{
		temperature = stage.getTemperature();
		attraction = stage.getAttraction();
		dampingMult = stage.getDampingMult();
	}

	public void initWorker(Worker worker)
	{
		worker.setAttraction(attraction);
		worker.setCutOffLength(cutOffLength);
		worker.setDampingMult(dampingMult);
		worker.setMinEdges(minEdges);
		worker.setSTAGE(STAGE);
		worker.setTemperature(temperature);
		worker.setFineDensity(fineDensity);
	}

	public boolean udpateStage(double totEnergy)
	{
		int MIN = 1;

		totIterations++;
		if (totIterations >= realIterations)
		{
			realFixed = false;
		}

		if (STAGE == 0)
		{
			if (iterations < params.getLiquid().getIterationsTotal(
					totExpectedIterations))
			{
				initStage(params.getLiquid());
				iterations++;
			} else
			{
				initStage(params.getExpansion());
				iterations = 0;
				STAGE = 1;
			}
		}

		if (STAGE == 1)
		{

			if (iterations < params.getExpansion().getIterationsTotal(
					totExpectedIterations))
			{
				// Play with vars
				if (attraction > 1)
				{
					attraction -= .05;
				}
				if (minEdges > 12)
				{
					minEdges -= .05;
				}
				cutOffLength -= cutRate;
				if (dampingMult > .1)
				{
					dampingMult -= .005;
				}
				iterations++;

			} else
			{
				STAGE = 2;
				minEdges = 12;
				initStage(params.getCooldown());
				iterations = 0;
			}
		} else if (STAGE == 2)
		{

			if (iterations < params.getCooldown().getIterationsTotal(
					totExpectedIterations))
			{

				// Reduce temperature
				if (temperature > 50)
				{
					temperature -= 10;
				}

				// Reduce cut length
				if (cutOffLength > cutLengthEnd)
				{
					cutLengthEnd -= cutRate * 2;
				}
				if (minEdges > MIN)
				{
					minEdges -= .2;
				}
				// min_edges = 99;
				iterations++;

			} else
			{
				cutOffLength = cutLengthEnd;
				minEdges = MIN;
				// min_edges = 99; // In other words: no more cutting
				STAGE = 3;
				iterations = 0;
				initStage(params.getCrunch());
			}
		} else if (STAGE == 3)
		{

			if (iterations < params.getCrunch().getIterationsTotal(
					totExpectedIterations))
			{
				iterations++;
			} else
			{
				iterations = 0;
				initStage(params.getSimmer());
				minEdges = 99;
				fineDensity = true;
				STAGE = 5;
			}
		} else if (STAGE == 5)
		{

			if (iterations < params.getSimmer().getIterationsTotal(
					totExpectedIterations))
			{
				if (temperature > 50)
				{
					temperature -= 2;
				}
				iterations++;
			} else
			{
				STAGE = 6;
			}
		} else if (STAGE == 6)
		{
			return false;
		}

		return true;
	}

	public boolean isRealFixed()
	{
		return realFixed;
	}

	public double getHighestSimilarity()
	{
		return highestSimilarity;
	}

	public void setHighestSimilarity(double highestSimilarity)
	{
		this.highestSimilarity = highestSimilarity;
	}

	public void setNumNodes(int nodeNum)
	{
		this.nodeNum = nodeNum;
	}

	public void setEdgeCut(double edgeCut)
	{
		this.edgeCut = edgeCut;
	}

	public void setRealParm(double realParm)
	{
		this.realParm = realParm;
	}
}
