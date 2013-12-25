package Data;

 
public interface IRelationComputeable extends IComputable
{
 // / <summary>
 	// / 求解相关性
 	// / </summary>
 	// / <param name="r1"> </param>
 	// / <param name="r2"></param>
 	// / <returns></returns>
 	double GetRelation(IRelationComputeable r1, IRelationComputeable r2);
 	
 	  double getWeight();

 	 
}
