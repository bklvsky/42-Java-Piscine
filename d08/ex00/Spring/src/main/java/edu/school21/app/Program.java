package edu.school21.app;

import edu.school21.printer.Printer;
import edu.school21.printer.PrinterWithDateTimeImpl;
import edu.school21.printer.PrinterWithPrefixImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;

public class Program {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		Printer printerPrefix = context.getBean(PrinterWithPrefixImpl.class);
		printerPrefix.print("Hello!") ;

		Printer printerDT = context.getBean(PrinterWithDateTimeImpl.class);
		printerDT.print("Message with date");

	}
}
