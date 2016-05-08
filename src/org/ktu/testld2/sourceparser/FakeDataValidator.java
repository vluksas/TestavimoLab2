package org.ktu.testld2.sourceparser;

import eu.sarunas.atf.meta.sut.Project;
import eu.sarunas.atf.meta.testdata.TestObject;
import eu.sarunas.atf.model.checker.ITestDataValidator;
import eu.sarunas.atf.model.checker.TestDataValidationResult;

public class FakeDataValidator implements ITestDataValidator{

	@Override
	public TestDataValidationResult validate(Project model, String contraints, TestObject testDataToValidate)
			throws Exception {
		return new TestDataValidationResult(true);
	}

	@Override
	public TestDataValidationResult validate(TestObject testDataToValidate) throws Exception {
		return new TestDataValidationResult(true);
	}

}
