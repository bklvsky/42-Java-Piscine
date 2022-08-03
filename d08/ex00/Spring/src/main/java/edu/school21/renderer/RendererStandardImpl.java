package edu.school21.renderer;

import edu.school21.preprocessor.PreProcessor;

public class RendererStandardImpl implements Renderer {

	private final PreProcessor pr;

	public RendererStandardImpl(PreProcessor preProcessor) {
		this.pr = preProcessor;
	}

	@Override
	public void render(String string) {
		System.out.println(pr.preProcess(string));
	}
}
