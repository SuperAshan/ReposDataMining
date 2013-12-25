package Data;

import java.util.List;

public interface IPositionComputeable extends IComputable
{

  

    // / <summary>
    // / 分组
    // / </summary>
    int getGroup();

    void setGroup(int group);

    // / <summary>
    // / X坐标，指代画图板中的位置
    // / </summary>
    double getPositionX();

    void setPositionX(double value);

    double getPositionY();

    void setPositionY(double value);

    double getPositionZ();

    void setPositionZ(double value);

    // / <summary>
    // / 与该节点相关联的边
    // / </summary>
    List<LinkedPath> getLinkedPathCollection();

}
