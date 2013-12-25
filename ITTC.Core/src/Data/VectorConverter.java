package Data;


public class VectorConverter
{
	public static double[] Multiply(double[] vector, double[][] matrix)
	{
		int n = vector.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++)
		{
			result[i] = 0;
			for (int j = 0; j < i; j++)
				result[i] += vector[j] * matrix[i][j];
			for (int j = i; j < n; j++)
				result[i] += vector[j] * matrix[j][i];
		}
		return result;
	}

	// / <summary>
	// / 重载乘号，数字乘以向量
	// / </summary>
	// / <param name="number"></param>
	// / <param name="values"> </param>
	// / <returns></returns>
	// public static IEnumerable<double> Multiply(double number,
	// IEnumerable<double>values)
	// {
	// return values.Select(value => value * number);
	// }

	// / <summary>
	// / 重载乘号，向量乘以向量
	// / </summary>
	// / <param name="vector1"></param>
	// / <param name="vector2"></param>
	// / <returns></returns>
	public static double Multiply(double[] vector1, double[] vector2)
	{
		int n = vector1.length;
		double result = 0;
		for (int i = 0; i < n; i++)
			result += vector1[i] * vector2[i];
		return result;
	}

	// end of function

	public static double[] Multiply(double[][] matrix, double[] vector)
	{
		int n = vector.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++)
		{
			result[i] = 0;
			for (int j = 0; j < i; j++)
				result[i] += matrix[i][j] * vector[j];
			for (int j = i; j < n; j++)
				result[i] += matrix[j][i] * vector[j];
		}
		return result;
	}

	// / <summary>
	// / 重载加号，向量相加
	// / </summary>
	// / <param name="vector1"></param>
	// / <param name="vector2"></param>
	// / <returns></returns>
	public static double[] Add(double[] vector1, double[] vector2)
	{
		int n = vector1.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++)
		{
			result[i] = vector1[i] + vector2[i];
		}
		return result;

	}

	// end of function

	// / <summary>
	// / 重载减号，向量相减
	// / </summary>
	// / <param name="vector1"></param>
	// / <param name="vector2"></param>
	// / <returns></returns>
	public static double[] Sub(double[] vector1, double[] vector2)
	{
		int n = vector1.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++)
		{
			result[i] = vector1[i] - vector2[i];
		}
		return result;
	}

	// end of function

	// / <summary>
	// / 重载大于号，判断共轭梯度法是否落入允许误差之内
	// / </summary>
	// / <param name="vector"></param>
	// / <param name="e"></param>
	// / <returns></returns>
	public static boolean End(double[] vector, double e)
	{
		// return vector.All(d => !(Math.Abs(d) > e));
		for (int i = 0; i < vector.length; i++)
		{
			if (Math.abs(vector[i]) > e)
				return false;
		}
		return true;
	}

	// end of function

	public static double[] Copy(double[] vector)
	{
		double[] result = new double[vector.length];
		for (int i = 0; i < vector.length; i++)
		{
			result[i] = vector[i];
		}

		return result;
	}

	public static double[][] Copy(double[][] LatterCoordinate)
	{
		// int rowNumber = LatterCoordinate.GetLength(0);
		int rowNumber = LatterCoordinate.length;
		int columnNumber = LatterCoordinate[0].length;
		double[][] formerCoordinate = new double[rowNumber][];
		for (int i = 0; i < rowNumber; i++)
		{
			formerCoordinate[i] = new double[columnNumber];
			for (int j = 0; j < columnNumber; j++)
				formerCoordinate[i][j] = LatterCoordinate[i][j];
		}
		return formerCoordinate;
	}

}
