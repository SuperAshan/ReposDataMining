package Data;

import java.util.List;
 
public abstract class IPositionProcess
{

    public boolean IsPositionChange = false;

    // / <summary>
    // / 考虑3D
    // / </summary>
    public boolean Is3D = false;
    // / <summary>
    // / 考虑权重
    // / </summary>
    public boolean IsWeight = false;

    // / <summary>
    // / 点集
    // / </summary>
    public List<IPositionComputeable> Nodes = null;
 
    public double EdgeMagnification = 1;

     
    public RelationMatrix relationMatrix;

    // / <summary>
    // / 执行布点计算的函数
    // / </summary>
    public abstract void PositionComputeProcess();

}
