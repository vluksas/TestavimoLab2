package eu.sarunas.atf.generators.tests.data;

import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.projects.atf.metadata.generic.Type;

public class ShortGenerator extends NumberGenerator
{
	public ShortGenerator(Randomizer randomizer)
	{
		super(randomizer, -10.0, 10.0);
	};

	public Object generate(Type type, ITestDataValidator validator)
	{
		return (short) generate();
	};
};
