package Data;

public class WordData implements Comparable
{

	// / <summary>
	// / 词
	// / </summary>
	public String Word;
	// / <summary>
	// / TF/IDF值
	// / </summary>
	public double TFIDF = 1;

	@Override
	public int compareTo(Object obj)
	{

		WordData info = (WordData) obj;
		if (info == null)
		{
			return -1;
		}
		return this.Word.compareTo(info.Word);

	}
}
