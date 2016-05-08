package eu.sarunas.atf.meta.sut;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class Enum extends Class
{
	public Enum(String name, EnumSet<Modifier> modifiers, Package packge, Object sourceElement)
	{
		super(name, modifiers, packge, sourceElement);
	};

	public Set<String> getValues()
	{
		return this.values;
	};

	private void addValue(String value)
	{
		this.values.add(value);
	};

	public void addField(Field field)
	{
		if (field.getType() == this)
		{
			addValue(field.getName());
		}
		else
		{
			super.addField(field);
		}
	};	
	
	private Set<String> values = new HashSet<String>();
};
