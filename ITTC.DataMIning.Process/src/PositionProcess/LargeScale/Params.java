package PositionProcess.LargeScale;

public enum Params
{
	DEFAULT(new Stage(0, 2000f, 10f, 1f), new Stage(0.25f, 2000f, 10f, 1f),
			new Stage(0.25f, 2000f, 2f, 1f), new Stage(0.25f, 2000f, 1f, 0.1f),
			new Stage(0.10f, 250f, 1f, 0.25f), new Stage(0.15f, 250f, 0.5f, 0f)), COARSEN(
			new Stage(0, 2000f, 10f, 1f), new Stage(200, 2000f, 2f, 1f),
			new Stage(200, 2000f, 10f, 1f), new Stage(200, 2000f, 1f, 0.1f),
			new Stage(50, 250f, 1f, 0.25f), new Stage(100, 250f, 0.5f, 0f)), COARSEST(
			new Stage(0, 2000f, 10f, 1f), new Stage(200, 2000f, 2f, 1f),
			new Stage(200, 2000f, 10f, 1f), new Stage(200, 2000f, 1f, 0.1f),
			new Stage(200, 250f, 1f, 0.25f), new Stage(100, 250f, 0.5f, 0f)), REFINE(
			new Stage(0, 50f, 0.5f, 0f), new Stage(0, 2000f, 2f, 1f),
			new Stage(50, 500f, 0.1f, 0.25f), new Stage(50, 200f, 1f, 0.1f),
			new Stage(50, 250f, 1f, 0.25f), new Stage(0, 250f, 0.5f, 0f)), FINAL(
			new Stage(0, 50f, 0.5f, 0f), new Stage(0, 2000f, 2f, 1f),
			new Stage(50, 50f, 0.1f, 0.25f), new Stage(50, 200f, 1f, 0.1f),
			new Stage(50, 250f, 1f, 0.25f), new Stage(25, 250f, 0.5f, 0f));
	private final Stage initial;
	private final Stage liquid;
	private final Stage expansion;
	private final Stage cooldown;
	private final Stage crunch;
	private final Stage simmer;

	private Params(Stage initial, Stage liquid, Stage expansion,
			Stage cooldown, Stage crunch, Stage simmer)
	{
		this.initial = initial;
		this.liquid = liquid;
		this.expansion = expansion;
		this.cooldown = cooldown;
		this.crunch = crunch;
		this.simmer = simmer;
	}

	public Stage getCooldown()
	{
		return cooldown;
	}

	public Stage getCrunch()
	{
		return crunch;
	}

	public Stage getExpansion()
	{
		return expansion;
	}

	public Stage getInitial()
	{
		return initial;
	}

	public Stage getLiquid()
	{
		return liquid;
	}

	public Stage getSimmer()
	{
		return simmer;
	}

	public float getIterationsSum()
	{
		return liquid.iterations + expansion.iterations + cooldown.iterations
				+ crunch.iterations + simmer.iterations;
	}

	public static class Stage
	{

		private float iterations;
		private float temperature;
		private float attraction;
		private float dampingMult;

		Stage(float iterations, float temperature, float attraction,
				float dampingMult)
		{
			this.iterations = iterations;
			this.temperature = temperature;
			this.attraction = attraction;
			this.dampingMult = dampingMult;
		}

		public float getAttraction()
		{
			return attraction;
		}

		public float getDampingMult()
		{
			return dampingMult;
		}

		public float getIterations()
		{
			return iterations;
		}

		public int getIterationsTotal(int totalIterations)
		{
			return (int) (iterations * totalIterations);
		}

		public int getIterationsPercentage()
		{
			return (int) (iterations * 100f);
		}

		public float getTemperature()
		{
			return temperature;
		}

		public void setIterations(float iterations)
		{
			this.iterations = iterations;
		}
	}
}
