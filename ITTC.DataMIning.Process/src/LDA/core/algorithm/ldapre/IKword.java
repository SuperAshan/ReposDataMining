package LDA.core.algorithm.ldapre;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKword {
	private static int docunum = 49950;
	private static int count = 0;
	private static List<String> stoplist = Arrays.asList(new String[] { "1",
			"2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d",
			"e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
			"r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" });
	private static List<String> list = new ArrayList<String>();
	static HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

	public static void main(String[] args) throws IOException {
		File file = new File("F:/LDA/corpus");
		File[] files = file.listFiles(); 
		String outpath = "f:/LDA/weibo.txt";
		File outFile = new File(outpath);
		FileWriter writer = new FileWriter(outFile);
		String outtopath = "f:/LDA/sequence.txt";
		File outtofile = new File(outtopath);
		FileWriter writerplus = new FileWriter(outtofile);
		
		writer.append(docunum + "\r\n");
		Analyzer analyzer = new IKAnalyzer(true);// true�趨Ϊ���ִܷʣ�false�趨��ϸ���ȷִ�
		for (File f : files) {
			writer.append(("document" + count++) + "=");
			FileReader reader = new FileReader(f);
			// �ִ�
			try{
			TokenStream ts = analyzer.tokenStream("", reader);
			CharTermAttribute term = ts.getAttribute(CharTermAttribute.class);
			ts.reset();
			while (ts.incrementToken()) {
				String termString = term.toString();
				
				if (termString.length() > 1) { // ȥ����
					//int index = list.indexOf(termString);
					// ����������λ�ú���stopw.dic�а�ķ��
					String singleword = termString;
					List<String> slist = new ArrayList<String>();

					while (singleword.length() > 1) {
						slist.add(singleword.substring(0, 1));
						singleword = singleword.substring(1);
					}

					if (singleword.length() > 0) {
						slist.add(singleword);
					}
					for (String item : slist) {
						if (stoplist.contains(item)) {
							break;
						}
						writer.append(termString + " ");
//						if (!list.contains(termString)) {
//							list.add(termString);
//							}
						break;
					}
				}
			}
			writer.append("\r\n");
//			for (String word : list) {
//				writerplus.append(word + " " + list.indexOf(word) + "\r\n");
//				hashMap.put(list.indexOf(word), word);
//			}
			ts.end();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		writer.flush();
		writer.close();
		writerplus.flush();
		writerplus.close();
	}
}
