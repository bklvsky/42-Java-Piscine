package edu.school21.printer;

import edu.school21.renderer.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {

	private final Renderer renderer;
	private LocalDateTime dateTime;

	public PrinterWithDateTimeImpl(Renderer renderer) {
		this.renderer = renderer;
		dateTime = LocalDateTime.now();
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public void print(String string) {
		renderer.render(string + " " + dateTime);
	}
}
