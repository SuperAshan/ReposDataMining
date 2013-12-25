package AlgorithmInteractive;
public interface ISentimentAnalysis
{

	public class TextSentimentInfo
	{
		public TextSentimentInfo(double positive, double negetive)
		{
			this.Positive = positive;
			this.Negetive = negetive;
		}

		// / <summary>
		// / 正负极性
		// / </summary>
		public double Polarity = 0;

		// / <summary>
		// / 情感强度
		// / </summary>
		private double Strength;

		public double getStrength()
		{

			return Math.abs(Positive) + Math.abs(Negetive);

		}

		public double Positive = 0;

		public double Negetive = 0;

	}

	// / <summary>
	// / 情感分析接口
	// / </summary>

	TextSentimentInfo ComputeWordFeeling(String word);

}
