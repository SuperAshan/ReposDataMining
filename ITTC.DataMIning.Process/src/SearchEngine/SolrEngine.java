package SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

import AlgorithmInteractive.ISearchEngine;
import AlgorithmManagement.*;

public class SolrEngine extends AbstractProcessMethod implements ISearchEngine
{

    @Override
    public List<String> SearchResult(String words)
    {

	SolrServer server = new HttpSolrServer("http://localhost:8080/solr");

//	  SolrInputDocument doc1 = new SolrInputDocument();
//	    doc1.addField( "id", "id1", 1.0f );
//	    doc1.addField( "name", "doc1", 1.0f );
//	    doc1.addField( "price", 10 );
//	    
//	    
//	 // 创建另外一个文档
//	    SolrInputDocument doc2 = new SolrInputDocument();
//	    doc2.addField( "id", "id2", 1.0f );
//	    doc2.addField( "name", "doc2", 1.0f );
//	    doc2.addField( "price", 20 );
//	    // 创建文档集合
//	    Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
//	    docs.add( doc1 );
//	    docs.add( doc2 );
//	    // 将文档添加到 Solr 中
//	    try
//	    {
//		server.add( docs );
//		    server.commit();
//	    } catch (SolrServerException e1)
//	    {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	    } catch (IOException e1)
//	    {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	    }
	    // 提交
	
	    
	// 构造一个查询对象
	SolrQuery query = new SolrQuery();
	query.setQuery( "*:*" );
	query.addSortField( "price", SolrQuery.ORDER.asc );
	try
	{
	    QueryResponse response=server.query(query);
	    System.out.println("Find:" + response.getResults().getNumFound());
//		    + "\n\n");
	} catch (SolrServerException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	// 查询结果
 
	
	
	    
//	ModifiableSolrParams params = new ModifiableSolrParams();
//	// 查询关键词
//	params.set("q", "2010");
//	// 分页，，start=0就是从0开始，，rows=5当前返回5条记录，，，第二页就是变化start这个值为5就可以了。
//	params.set("start", 0);
//	params.set("rows", 5);
//
//	// 排序，，如果按照id 排序，，那么将score desc 改成 id desc(or asc)
//	params.set("sort", "score desc");
//
//	// 返回信息 * 为全部 这里是全部加上score，如果不加下面就不能使用score
//	params.set("fl", "*,score");
//
//	QueryResponse response;
//	try
//	{
//	    response = server.query(params);
//	    System.out.println("Find:" + response.getResults().getNumFound()
//		    + "\n\n");
//
//	    // 输出结果
//	    for (SolrDocument doc : response.getResults())
//	    {
//		System.out.println("id: " + doc.getFieldValue("id").toString());
//		System.out.println("title: "
//			+ doc.getFieldValue("title").toString() + "\n");
//	    }
//	} catch (SolrServerException e)
//	{
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}
	// 搜索得到的结果数

	return null;
	// TODO Auto-generated method stub

    }

}
