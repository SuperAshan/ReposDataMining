package Data;

 

public interface INode extends IRelationComputeable, IPositionComputeable
{
    Iterable<LinkedPath> GetLinkedPath();

    IRelationComputeable getData();

    void setData(IRelationComputeable data);
}
