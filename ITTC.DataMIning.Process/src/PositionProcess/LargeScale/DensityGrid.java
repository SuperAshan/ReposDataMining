package PositionProcess.LargeScale;

import java.util.ArrayDeque;


public class DensityGrid
{
	private static final int GRID_SIZE = 1000; // size of Density grid
	private static final double VIEW_SIZE = 4000; // actual physical size of
													// layout plane
	private static final int RADIUS = 10; // radius for density fall-off:
	private static final int HALF_VIEW = 2000;
	private static final double VIEW_TO_GRID = 0.25f;
	private double[][] density;
	private double[][] fallOff;
	private ArrayDeque<Node>[][] bins;

	public void init()
	{
		density = new double[GRID_SIZE][GRID_SIZE];
		fallOff = new double[RADIUS * 2 + 1][RADIUS * 2 + 1];
		bins = new ArrayDeque[GRID_SIZE][GRID_SIZE];

		for (int i = -RADIUS; i <= RADIUS; i++)
		{
			for (int j = -RADIUS; j <= RADIUS; j++)
			{
				fallOff[i + RADIUS][j + RADIUS] = (double) ((RADIUS - Math
						.abs((double) i)) / RADIUS)
						* (double) ((RADIUS - Math.abs((double) j)) / RADIUS);
			}
		}
	}

	public double getDensity(double nX, double nY, boolean fineDensity)
	{
		int xGrid, yGrid;
		double xDist, yDist, distance, density = 0;
		int boundary = 10; // boundary around plane

		xGrid = (int) ((nX + HALF_VIEW + .5) * VIEW_TO_GRID);
		yGrid = (int) ((nY + HALF_VIEW + .5) * VIEW_TO_GRID);

		// Check for edges of density grid (10000 is arbitrary high density)
		if (xGrid > GRID_SIZE - boundary || xGrid < boundary)
		{
			return 10000;
		}
		if (yGrid > GRID_SIZE - boundary || yGrid < boundary)
		{
			return 10000;
		}

		if (fineDensity)
		{
			for (int i = yGrid - 1; i <= yGrid + 1; i++)
			{
				for (int j = xGrid - 1; j <= xGrid + 1; j++)
				{
					ArrayDeque<Node> deque = bins[i][j];
					if (deque != null)
					{
						for (Node bi : deque)
						{
							xDist = nX - bi.x;
							yDist = nY - bi.y;
							distance = xDist * xDist + yDist * yDist;
							density += 1e-4 / (distance + 1e-50);
						}
					}
				}
			}
		} else
		{
			density = this.density[yGrid][xGrid];
			density *= density;
		}
		return density;
	}

	public void add(Node n, boolean fineDensity)
	{
		if (fineDensity)
		{
			fineAdd(n);
		} else
		{
			add(n);
		}
	}

	public void substract(Node n, boolean firstAdd, boolean fineFirstAdd,
			boolean fineDensity)
	{
		if (fineDensity && !fineFirstAdd)
		{
			fineSubstract(n);
		} else if (!firstAdd)
		{
			substract(n);
		}
	}

	private void substract(Node n)
	{
		int xGrid, yGrid, diam;

		xGrid = (int) ((n.subX + HALF_VIEW + 0.5f) * VIEW_TO_GRID);
		yGrid = (int) ((n.subY + HALF_VIEW + 0.5f) * VIEW_TO_GRID);
		xGrid -= RADIUS;
		yGrid -= RADIUS;
		diam = 2 * RADIUS;

		for (int i = 0; i <= diam; i++)
		{
			int oldXGrid = xGrid;
			for (int j = 0; j <= diam; j++)
			{
				density[yGrid][xGrid] -= fallOff[i][j];
				xGrid++;
			}
			yGrid++;
			xGrid = oldXGrid;
		}
	}

	private void add(Node n)
	{
		int xGrid, yGrid, diam;

		xGrid = (int) ((n.x + HALF_VIEW + .5) * VIEW_TO_GRID);
		yGrid = (int) ((n.y + HALF_VIEW + .5) * VIEW_TO_GRID);

		n.subX = n.x;
		n.subY = n.y;

		xGrid -= RADIUS;
		yGrid -= RADIUS;
		diam = 2 * RADIUS;

		if ((xGrid + RADIUS >= GRID_SIZE) || (xGrid < 0)
				|| (yGrid + RADIUS >= GRID_SIZE) || (yGrid < 0))
		{
			throw new RuntimeException("Error: Exceeded density grid with "
					+ "xGrid = " + xGrid + " and yGrid = " + yGrid);
		}

		for (int i = 0; i <= diam; i++)
		{
			int oldXGrid = xGrid;
			for (int j = 0; j <= diam; j++)
			{
				density[yGrid][xGrid] += fallOff[i][j];
				xGrid++;
			}
			yGrid++;
			xGrid = oldXGrid;
		}
	}

	private void fineSubstract(Node n)
	{
		int xGrid, yGrid;

		xGrid = (int) ((n.subX + HALF_VIEW + .5) * VIEW_TO_GRID);
		yGrid = (int) ((n.subY + HALF_VIEW + .5) * VIEW_TO_GRID);
		ArrayDeque<Node> deque = bins[yGrid][xGrid];
		if (deque != null)
		{
			deque.pollFirst();
		}
	}

	private void fineAdd(Node n)
	{
		int xGrid, yGrid;

		xGrid = (int) ((n.x + HALF_VIEW + .5) * VIEW_TO_GRID);
		yGrid = (int) ((n.y + HALF_VIEW + .5) * VIEW_TO_GRID);

		n.subX = n.x;
		n.subY = n.y;
		ArrayDeque<Node> deque = bins[yGrid][xGrid];
		if (deque == null)
		{
			deque = new ArrayDeque<Node>();
			bins[yGrid][xGrid] = deque;
		}
		deque.addLast(n);
	}

	public static double getViewSize()
	{
		return (VIEW_SIZE * 0.8f) - (RADIUS / 0.25f) * 2f;
	}
}
