package edu.school21.printer;

import edu.school21.renderer.Renderer;


public class PrinterWithPrefixImpl implements Printer {

	private final Renderer renderer;
	private String prefix;

	public PrinterWithPrefixImpl(Renderer renderer) {
		this.renderer = renderer;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public void print(String string) {
		renderer.render(prefix + " " + string);
	}
}
