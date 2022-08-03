package edu.school21.renderer;

import edu.school21.preprocessor.PreProcessor;

public class RendererErrImpl implements Renderer{

	private final PreProcessor pr;

	public RendererErrImpl(PreProcessor preProcessor) {
		this.pr = preProcessor;
	}

	@Override
	public void render(String string) {
		System.err.println(pr.preProcess(string));
	}
}
