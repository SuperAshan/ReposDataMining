package Data;

import java.util.List;

public interface IObjectRelation
{
     void  setDataSource(List<IRelationComputeable> data);
     List<IRelationComputeable>    getDataSource();
    RelationMatrix getRelationMatrix();
}
