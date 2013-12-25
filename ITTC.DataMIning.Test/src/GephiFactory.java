import it.uniroma1.dis.wsngroup.gexf4j.core.Edge;
import it.uniroma1.dis.wsngroup.gexf4j.core.EdgeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.Gexf;
import it.uniroma1.dis.wsngroup.gexf4j.core.Graph;
import it.uniroma1.dis.wsngroup.gexf4j.core.Metadata;
import it.uniroma1.dis.wsngroup.gexf4j.core.Mode;
import it.uniroma1.dis.wsngroup.gexf4j.core.Node;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.Attribute;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.viz.ColorImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.viz.PositionImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.viz.Color;
import it.uniroma1.dis.wsngroup.gexf4j.core.viz.NodeShape;

//import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;




import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Data.IPositionComputeable;
import Data.RelationMatrix;



public class GephiFactory {
	
	public void GephiOutput(List<Data.Node> Nodes,RelationMatrix Matrixresult,String searchname) 
	{
		Gexf gexf = new GexfImpl();
		Calendar date = Calendar.getInstance();
		// date.set(2012, 4, 02);
//		gexf.getMetadata().setLastModified(date.getTime())
		Metadata me=gexf.getMetadata();
		me.setLastModified(date.getTime());
		me.setCreator("Gephi.org");
		me.setDescription("Weibo network");
//		gexf.getMetadata().setLastModified(date.getTime())
//				.setCreator("Gephi.org").setDescription("Weibo network");
		Graph graph = gexf.getGraph();
		graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.DYNAMIC);

		AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
		graph.getAttributeLists().add(attrList);

		Attribute attUrl = attrList.createAttribute("0", AttributeType.STRING,
				"url");
		Attribute attIndegree = attrList.createAttribute("1",
				AttributeType.FLOAT, "indegree");
		Attribute attFrog = attrList.createAttribute("2",
				AttributeType.BOOLEAN, "frog").setDefaultValue("true");
		
		int startnumber=0;
		List<Node> nodelist = new ArrayList<Node>();
		for(Data.Node node:Nodes.subList(0, Nodes.size()-1))
		{
			Node Gephinode=graph.createNode(Integer.toString(startnumber));
			nodelist.add(Gephinode);
			
			System.out.println("名字："+node.getKey());
			System.out.println("\t点大小："+node.getWeight()+"\n");
			Gephinode.setLabel(node.getKey());
			if(node.getLinkedPathCollection().size()==0)
			{
				Gephinode.setColor(new ColorImpl(255,0,0));
			}
			else {
				Gephinode.setColor(new ColorImpl(node.getGroup()*16%255,(node.getGroup()*16+64)%255,(node.getGroup()*16+128)%255));
				
			}
			
			Gephinode.setPosition(new PositionImpl((float)node.getPositionX(),(float)node.getPositionY(),(float)node.getPositionZ()));
		//	Gephinode.setSize((float) Math.log10(node.getLinkedPathCollection().size())>1 ? (float)Math.log10(node.getLinkedPathCollection().size()):1);
			Gephinode.setSize((float)node.getWeight()*8);
		//	Gephinode.getShapeEntity().setNodeShape(NodeShape.IMAGE);
		//	Gephinode.getShapeEntity().setUri(vec.getProperties().get("imgUrl"));
		//	Gephinode.getShapeEntity().setUri(node.DictSerialize()["imgurl"];
			
			startnumber++;
		}
		
		Node Gephinode=graph.createNode(Integer.toString(startnumber));
		nodelist.add(Gephinode);
	    Data.Node node=Nodes.get(Nodes.size()-1);
		Gephinode.setLabel(searchname);
		Gephinode.setColor(new ColorImpl(node.getGroup()*16%255,(node.getGroup()*16+32)%255,(node.getGroup()*16+64)%255));
		Gephinode.setPosition(new PositionImpl((float)node.getPositionX(),(float)node.getPositionY(),(float)node.getPositionZ()));
	//	Gephinode.setSize((float) Math.log10(node.getLinkedPathCollection().size())>1 ? (float)Math.log10(node.getLinkedPathCollection().size()):1);
		Gephinode.setSize((float)Math.log(node.getWeight()));
		
		
		int nodenumber=Nodes.size();
		int k = 1;
		for (int i = 0; i < nodenumber; i++)
		{
			for (int j = 0; j < i; j++)
			{
				if (Matrixresult.IsLinked(i, j))
				{
					// Edge edge=new
					// EdgeImpl(Integer.nodelist.get(i),nodelist.get(j));
					Edge edge = nodelist.get(i).connectTo(Integer.toString(k),
							nodelist.get(j));
				//	edge.setColor(new ColorImpl(0, 0, 0));
					edge.setColor(nodelist.get(j).getColor());
					k++;
				}
			}
		}
		
		

//		Color o = new ColorImpl(255, 255, 255);
//		List<Node> nodelist = new ArrayList<Node>();
//		Node gephi = graph.createNode("0");
//		gephi.setLabel(this.centreTag).setColor(o)
//				.setPosition(new PositionImpl(0, 0, 0)).setSize(3);// /////////////////////////////////////////
//		gephi.getShapeEntity().setNodeShape(NodeShape.IMAGE);
//		gephi.getShapeEntity().setUri(centerUri);
//		nodelist.add(gephi);
//
//		// .getAttributeValues()
//		// .addValue(attUrl, "http://gephi.org")
//		// .addValue(attIndegree, "1");
//		// List<Integer> CoreNodenumber = new ArrayList<Integer>();
//		// List<Integer> NodeDegree = new ArrayList<Integer>();
//		int startnumber = 1;
//		int colornumber = 1;
//		for (List<PropertyNode> nodeL : NodeMap.values())
//		{
//			for (int j = 0; j < nodeL.size(); j++)
//			{
//				// String s=Integer.toString(startnumber);
//				// String s=nodeL.get(j).getCn().getData().getKey();
//				Node node = graph.createNode(Integer.toString(startnumber));
//				nodelist.add(node);
//				// if(nodeL.get(j).getCn().isCore())
//				Vectorizable vec=(Vectorizable)nodeL.get(j).getCn().getData();
//				node.setLabel(vec.getKey());
//				node.setPosition(new PositionImpl((float) CoordinateResult[0][startnumber],
//						(float) CoordinateResult[1][startnumber], (float)CoordinateResult[2][startnumber]));
//				node.setColor(new ColorImpl(colornumber % 255,
//						(colornumber + 64) % 255, (colornumber + 128) % 255));
//				node.setSize((float) vec.getWeight());
//				node.getShapeEntity().setNodeShape(NodeShape.IMAGE);
//				node.getShapeEntity().setUri(vec.getProperties().get("imgUrl"));
//				startnumber++;
//			}
//			colornumber += 32;
//		}

//		int k = 1;
//		for (int i = 0; i < nodenumber; i++)
//		{
//			for (int j = 0; j < i; j++)
//			{
//				if (InputOriginal[i][j] == 1)
//				{
//					// Edge edge=new
//					// EdgeImpl(Integer.nodelist.get(i),nodelist.get(j));
//					Edge edge = nodelist.get(i).connectTo(Integer.toString(k),
//							nodelist.get(j));
//					edge.setColor(new ColorImpl(0, 0, 0));
//					k++;
//				}
//			}
//		}

		// StaxGraphWriter graphWriter = new StaxGraphWriter();
		// StringWriter stringWriter = new StringWriter();
		//
		// graphWriter.writeToStream(gexf, stringWriter, "UTF-8");
		//
		// String outputXml = stringWriter.toString();
		//
		// InputStream inputStream =
		// GexfImplDefaultTest.class.getResourceAsStream("/dynamicGraph.gexf");

		// StaxGraphWriter graphWriter = new StaxGraphWriter();
		// File f = new File("dynamic_graph_sample.gexf");
		// Writer out;
		// try
		// {
		// out = new FileWriter(f, false);
		// graphWriter.writeToStream(gexf, out, "UTF-8");
		// System.out.println(f.getAbsolutePath());
		// } catch (IOException e)
		// {
		// }
		StaxGraphWriter graphWriter = new StaxGraphWriter();
		StringWriter stringWriter = new StringWriter();
		
		String datadir = null;
		try {
			datadir = new String("data/".getBytes("utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String namedir = null;
		try {
			namedir = new String(searchname.getBytes("utf-8"),"utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		luceneDir=new String(luceneDir.getBytes("utf-8"),"utf-8");
//		fileName=new String(fileName.getBytes("utf-8"),"utf-8");
//		isCache=new String(isCache.getBytes("utf-8"),"utf-8");
//		gexfDir=new String(gexfDir.getBytes("utf-8"),"utf-8");
		File filePath = new File(datadir,namedir);
		
//		searchname.mkdirs();
		filePath.mkdirs();
		File file=new File(filePath, "dynamicGraph.gexf");
		try {
			graphWriter.writeToStream(gexf, new FileWriter(file), "UTF-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			graphWriter.writeToStream(gexf, stringWriter, "UTF-8");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		XMLFileConverter converter = new XMLFileConverter();
	//	converter.Convert(new File(filePath, "dynamicGraph.gexf"), new File(filePath,
	//			centreTag+"-"+dimension+".gexf"));
		try {
			converter.Convert(file, new File(filePath,"Graph.gexf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		file.delete();
//		if(file.isFile() && file.exists()){     
//  //          file.delete();  
//            System.out.println("删除单个文件"+file+"成功！");     
//   
//        }else{     
//            System.out.println("删除单个文件"+file+"失败！");     
//     
//        }     
	
		
		System.out.println("完毕！");
	}

}
