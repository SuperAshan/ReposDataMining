/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PositionProcess.StressMajorization;

/**
 * 
 * @author Tao
 */
public class CG
{
	private double[] X;
	private double[] b;
	private double[][] A;
	private int MaXIterationNumber = 500;
	private double e = 1e-4; // //////////////////////////////////////

	public CG()
	{
	}

	public CG(double[] X0, double[] b, double[][] A)
	{
		this.X = X0;
		this.b = b;
		this.A = A;
	}

	// / <summary>
	// / 共轭梯度法求解线性方程组AX=b;
	// / </summary>
	// / <returns></returns>
	public double[] SolveEqutions()
	{
		double[] AX;
		double[] Ap;
		double pAp;
		double a;
		double[] ap;
		double[] r;
		double[] p;
		double r_r;
		double r_r_new;
		double beta;
		AX = this.Multiply(this.A, this.X);
		r = this.Sub(this.b, AX);
		p = this.Copy(r);
		r_r = this.Multiply(r, r);
		for (int i = 0; i < this.MaXIterationNumber && !this.End(r, this.e); i++)
		{
			Ap = this.Multiply(this.A, p);
			pAp = this.Multiply(p, Ap);
			a = r_r / pAp;
			ap = this.Multiply(a, p);
			this.X = this.Add(this.X, ap);
			if (i < this.MaXIterationNumber - 1)
			{
				Ap = this.Multiply(a, Ap);
				r = this.Sub(r, Ap);
				r_r_new = this.Multiply(r, r);
				beta = r_r_new / r_r;
				r_r = r_r_new;
				p = this.Multiply(beta, p);
				p = this.Add(r, p);
			}
		}
		return this.X;
	}

	// end of function SolveEqutions

	public double[] Multiply(double[][] Matrix, double[] vector)
	{
		int n = vector.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++)
		{
			result[i] = 0;
			for (int j = 0; j < i; j++)
				result[i] += Matrix[i][j] * vector[j];
			for (int j = i; j < n; j++)
				result[i] += Matrix[j][i] * vector[j];
		}
		return result;
	}

	// / <summary>
	// / 重载乘号，向量乘以向量
	// / </summary>
	// / <param name="vector1"></param>
	// / <param name="vector2"></param>
	// / <returns></returns>
	public double Multiply(double[] vector1, double[] vector2)
	{
		int n = vector1.length;
		double result = 0;
		for (int i = 0; i < n; i++)
			result += vector1[i] * vector2[i];
		return result;
	}

	// end of function

	public double[] Multiply(double[] vector, double[][] Matrix)
	{
		int n = vector.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++)
		{
			result[i] = 0;
			for (int j = 0; j < i; j++)
				result[i] += vector[j] * Matrix[i][j];
			for (int j = i; j < n; j++)
				result[i] += vector[j] * Matrix[j][i];
		}
		return result;
	}

	// / <summary>
	// / 重载乘号，数字乘以向量
	// / </summary>
	// / <param name="number"></param>
	// / <param name="vector"></param>
	// / <returns></returns>
	public double[] Multiply(double number, double[] vector)
	{
		int n = vector.length;
		double[] result = new double[n];
		for (int i = 0; i < n; i++)
		{
			result[i] = number * vector[i];
		}
		return result;
	}

	// end of function

	// / <summary>
	// / 重载加号，向量相加
	// / </summary>
	// / <param name="vector1"></param>
	// / <param name="vector2"></param>
	// / <returns></returns>
	public double[] Add(double[] vector1, double[] vector2)
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
	public double[] Sub(double[] vector1, double[] vector2)
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
	public Boolean End(double[] vector, double e)
	{
		int n = vector.length;
		for (int i = 0; i < n; i++)
		{
			if (Math.abs(vector[i]) > e)
				return false;
		}
		return true;
	}

	// end of function

	public double[] Copy(double[] vector)
	{
		int n = vector.length;
		double[] result = new double[n];
		System.arraycopy(vector, 0, result, 0, n);
		return result;
	}
	// end of function

}
