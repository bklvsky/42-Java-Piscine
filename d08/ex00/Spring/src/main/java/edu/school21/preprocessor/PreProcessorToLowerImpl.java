package edu.school21.preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor {

	@Override
	public String preProcess(String string) {
		return string.toLowerCase();
	}

	public PreProcessorToLowerImpl() { }
}
