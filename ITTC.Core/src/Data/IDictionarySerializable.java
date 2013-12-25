package Data;

import java.util.HashMap;

public interface IDictionarySerializable
{
	HashMap<String, Object> DictSerialize();

	void DictDeserialize(HashMap<String, Object> dict);
}
