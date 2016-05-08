package eu.sarunas.atf.generators.tests;

import java.util.List;
import eu.sarunas.atf.generators.tests.data.Randomizer;
import eu.sarunas.atf.meta.sut.Method;
import eu.sarunas.atf.meta.sut.Parameter;
import eu.sarunas.atf.meta.tests.TestCase;
import eu.sarunas.atf.meta.tests.TestInput;
import eu.sarunas.atf.meta.tests.TestInputParameter;
import eu.sarunas.atf.meta.tests.TestProject;
import eu.sarunas.atf.meta.tests.TestSuite;
import eu.sarunas.atf.model.checker.ITestDataValidator;

public class RandomGenerator implements ITestGenerator
{
	public RandomGenerator(Randomizer randomizer)
	{
		this.randomizer = randomizer;
	};

	public TestSuite generate(Method method, ITestsGeneratorManager manager, TestProject project) throws Exception
	{
		assert (null != method);

		TestSuite tests = new TestSuite(project, "TestSuite" + method.getName());

		while (false == manager.isDone())
		{
			TestCase testCase = generateTestCase(method, tests, manager.getValidator());

			if (true == manager.acceptTest(testCase))
			{
				tests.addTestCase(testCase);
			}
		}

		return tests;
	};

	private TestCase generateTestCase(Method method, TestSuite testSuite, ITestDataValidator validator)
	{
		TestCase testCase = new TestCase(method, testSuite);
		List<Parameter> parameters = method.getParameters();
		TestInput testInput = new TestInput(testCase);

		for (int i = 0; i < parameters.size(); i++)
		{
			testInput.addParameter(new TestInputParameter(parameters.get(i), this.randomizer.getRandomValue(parameters.get(i).getType(), validator)));
		}

		testCase.addInput(testInput);

		return testCase;
	};

	private Randomizer randomizer = null;
}
