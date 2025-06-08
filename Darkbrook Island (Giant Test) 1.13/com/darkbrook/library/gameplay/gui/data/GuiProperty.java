package com.darkbrook.library.gameplay.gui.data;

import com.darkbrook.library.data.object.ObjectDataKeyValue;
import com.darkbrook.library.data.object.ObjectDataParsed;

public class GuiProperty extends ObjectDataParsed<ObjectDataKeyValue>
{

	public GuiProperty(String identity) 
	{
		super(identity);
	}
	
	public <T extends ObjectDataParsed<?>> T apply(T data)
	{
		ObjectDataKeyValue keyValue = getParsedValue();
		data.addMapping(keyValue.keys, keyValue.values);
		return data;
	}

	@Override
	protected ObjectDataKeyValue onParsedValueLoad() 
	{
		ObjectDataKeyValue keyValue = new ObjectDataKeyValue();
		
		keyValue.keys.addAll(getArrayKeys());
		keyValue.keys.addAll(getDataKeys());
		
		for(String key : keyValue.keys)
		{
			keyValue.values.add(getValue(key));
		}
		
		keyValue.keys.add("prop.name");
		keyValue.values.add(getVariableName());
		
		return keyValue;
	}

}
