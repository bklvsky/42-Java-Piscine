package edu.school21.app;

import edu.school21.classes.Product;
import edu.school21.classes.User;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		printClasses();
		Class<?> classWork = makeClass(sc);
		printFields(classWork);
		printMethods(classWork);
		try {
			Object obj = makeObject(classWork, sc);
			updateObject(obj, sc);
			invokeMethod(obj, sc);
			System.out.println("---------------------");
			System.out.println("Object in the end:\n" + obj);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printFields(Class<?> classWork) {
		Field[] fields = classWork.getDeclaredFields();
		System.out.println("fields:");
		for (Field field: fields) {
			System.out.println("    " + field.getName());
		}
	}

	private static String toMethodDeclaration(Method method) {
		StringBuilder methodDeclaration = new StringBuilder();

		methodDeclaration.append(method.getReturnType().getSimpleName());
		methodDeclaration.append(" " + method.getName() + "(");
		Type[] params = method.getParameterTypes();

		for (int i = 0; i < method.getParameterCount(); i++) {
			methodDeclaration.append(params[i].getTypeName());
			if (i < method.getParameterCount() - 1) {
				methodDeclaration.append(", ");
			}
		}
		methodDeclaration.append(")");

		return methodDeclaration.toString();
	}

	private static void printMethods(Class<?> classWork) {
		Method[] methods = classWork.getDeclaredMethods();
		System.out.println("methods:");
		for (Method method: methods) {
			System.out.println("    " + toMethodDeclaration(method));
		}
		System.out.println("---------------------");
	}

	private static void printClasses() {
		System.out.println("Classes:");
		System.out.println("User");
		System.out.println("Product");
		System.out.println("---------------------");
	}
	private static Class<?> makeClass(Scanner sc) {
		Class<?> newClass = null;

		while (newClass == null) {
			System.out.print("Enter class name:\n-> ");
			String className = sc.nextLine();
			if (className.equals("User")) {
				newClass = new User().getClass();
			} else if (className.equals("Product")) {
				newClass = new Product().getClass();
			} else {
				System.out.println("The class " + className + " doesn't exist");
				System.out.println("---------------------");
			}
		}
		System.out.println("---------------------");
		return newClass;

	}

	private static Object getParamFromScanner(String typeName, String input) {

		switch (typeName.toLowerCase()) {
			case "string":
				return input;
			case "boolean":
				return Boolean.parseBoolean(input);
			case "int":
			case "integer":
				return Integer.parseInt(input);
			case "long":
				return Long.parseLong(input);
			case "double":
				return Double.parseDouble(input);
			case "float":
				return Float.parseFloat(input);
			case "char":
			case "character":
				return input.charAt(0);
			default:
				return null;
		}
	}
	private static Object makeObject(Class<?> classWork, Scanner sc) throws Exception {
		Constructor<?> constructor = null;
		Object obj;

		for (Constructor<?> constr: classWork.getDeclaredConstructors()) {
			if (constr.getParameterCount() > 0) {
				constructor = constr;
				break;
			}
		}
		if (constructor == null) {
			return classWork.getDeclaredConstructor().newInstance();
		}
		List<Object>constParam = new ArrayList<>();
		System.out.println("Let's create an object.");

		for (Parameter param: constructor.getParameters()) {
			while (true) {
				try {
					System.out.print(param.getName() + ":\n-> ");
					constParam.add(getParamFromScanner(param.getType().getSimpleName(), sc.nextLine()));
					break;
				} catch (NumberFormatException e) {
					System.out.println("Invalid data format");
				}
			}
		}
		obj = constructor.newInstance(constParam.toArray());
		System.out.println("Object created: " + obj);
		System.out.println("---------------------");
		return obj;
	}

	private static void updateObject(Object obj, Scanner sc) throws IllegalAccessException {
		Field field;

		while (true) {
			System.out.print("Enter name of the field for changing:\n-> ");
			String name = sc.nextLine();
			try {
				field = obj.getClass().getDeclaredField(name);
				break;
			} catch (NoSuchFieldException e) {
				System.out.println("The field " + name + " doesn't exist");
			}
		}
		field.setAccessible(true);
		while (true) {
			try {
				System.out.print("Enter " + field.getType().getSimpleName()+ " value:\n-> ");
				field.set(obj, getParamFromScanner(field.getType().getSimpleName(), sc.nextLine()));
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid data format");
			}
		}
			System.out.println("Object updated: " + obj);
			System.out.println("---------------------");
	}

	private static Class<?> getClassFromString(String input) {
		switch (input.toLowerCase()) {
			case "string":
				return String.class;
			case "boolean":
				return Boolean.class;
			case "int":
				return int.class;
			case "integer":
				return Integer.class;
			case "long":
				return Long.class;
			case "double":
				return Double.class;
			case "float":
				return Float.class;
			case "char":
				return char.class;
			case "character":
				return Character.class;
			default:
				throw new IllegalArgumentException("Unrecognized argument type");
		}
	}

	private static Class<?>[] parseMethodDeclaration(String input) throws IllegalArgumentException {
		String[] args = input.substring(input.indexOf("(") + 1).split(",");
		if (args.length == 0) {
			return null;
		}
		args[args.length - 1] = args[args.length - 1].replace(')', ' ');
		Class<?>[] argsTypesList = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			argsTypesList[i] = getClassFromString(args[i].trim());
		}
		return argsTypesList;
	}

	private static void invokeMethod(Object obj, Scanner sc) throws SecurityException, IllegalAccessException, InvocationTargetException {
		Method method = null;
		while (true) {
			try {
				System.out.print("Enter name of the method for call:\n-> ");
				String input = sc.nextLine();
				String name = input.substring(0, input.indexOf("("));
				method = obj.getClass().getDeclaredMethod(name, parseMethodDeclaration(input));
				break;

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch (NoSuchMethodException e) {
				System.out.println("No such method");
			}
		}

		List<Object> paramsValues = new ArrayList<>();
		for (Parameter param: method.getParameters()) {
			while (true) {
				try {
					System.out.print("Enter " + param.getType().getSimpleName() + " value\n-> ");
					String input = sc.nextLine();
					paramsValues.add(getParamFromScanner(param.getType().getSimpleName(), input));
					break;
				} catch (NumberFormatException e) {
					System.out.println("Invalid format");
				}
			}
		}

		Object ret = method.invoke(obj, paramsValues.toArray());
		if (ret != null) {
			System.out.println("Method returned: " + ret);
		}
	}
}
