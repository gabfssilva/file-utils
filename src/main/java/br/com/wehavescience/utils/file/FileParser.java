package br.com.wehavescience.utils.file;

import java.util.ArrayList;
import java.util.List;

import br.com.wehavescience.utils.file.annotations.Field;
import br.com.wehavescience.utils.file.annotations.Footer;
import br.com.wehavescience.utils.file.annotations.Header;
import br.com.wehavescience.utils.file.annotations.Lines;
import br.com.wehavescience.utils.sample.CompleteModel;
import br.com.wehavescience.utils.sample.ModelSample;

/**
 * @author gabriel
 * 
 *         Oct 10, 2013
 */
public class FileParser {
	public static <T> T parse(String line, Class<T> objectType) {
		try {
			T instance = objectType.newInstance();

			for (java.lang.reflect.Field field : objectType.getDeclaredFields()) {
				field.setAccessible(true);

				Field annotation = field.getAnnotation(Field.class);

				if (annotation == null) {
					continue;
				}

				String f = "";
				
				if(annotation.padDirection() == Field.LEFT_PAD){
					f = removeLeftPad(line.subSequence(
						annotation.firstPosition() - 1,
						annotation.lastPosition()).toString(), annotation.pad());
				}
				
				if(annotation.padDirection() == Field.RIGHT_PAD){
					f = removeRightPad(line.subSequence(
						annotation.firstPosition() - 1,
						annotation.lastPosition()).toString(), annotation.pad());
				}
				field.set(instance, f);
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T, L, H, F> T parse(List<String> lines, Class<T> objectType) {
		try {
			T instance = objectType.newInstance();
			for (java.lang.reflect.Field field : objectType.getDeclaredFields()) {
				field.setAccessible(true);

				Lines fields = field.getAnnotation(Lines.class);
				Header header = field.getAnnotation(Header.class);
				Footer footer = field.getAnnotation(Footer.class);

				if (header != null) {
					H h = (H) parse(lines.get(0),
							Class.forName(field.getType().getName()));
					field.set(instance, h);
				}

				if (footer != null) {
					F f = (F) parse(lines.get(lines.size() - 1),
							Class.forName(field.getType().getName()));
					field.set(instance, f);
				}

				if (fields != null) {

					/**
					 * MAGIC
					 */
					String lineClassName = field.toGenericString()
							.replaceAll("\\D*List<", "")
							.replaceAll(">\\D*", "");
					Class<L> lineType = (Class<L>) Class.forName(lineClassName);
					List<L> lineList = new ArrayList<L>();

					for (int i = 0; i < lines.size() - 1; i++) {
						if (i == 0) {
							continue;
						}
						L line = (L) parse(lines.get(i), lineType);
						lineList.add(line);
					}
					field.set(instance, lineList);
				}
			}

			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String removeLeftPad(String str, String pad) {
		while(str.startsWith(pad)){
			str = str.replaceFirst(pad, "");
		}
		return str;
	}
	
	private static String removeRightPad(String str, String pad) {
		while(str.endsWith(pad)){
			str = str.substring(0, str.lastIndexOf(pad));
		}
		return str;
	}

	private static String leftPad(String str, int size, String pad) {
		StringBuilder builder = new StringBuilder();
		addPad(str, size, pad, builder);
		builder.append(str);
		return builder.substring(0, size);
	}

	private static String rightPad(String str, int size, String pad) {
		StringBuilder builder = new StringBuilder();
		builder.append(str);
		addPad(str, size, pad, builder);
		return builder.substring(0, size);
	}

	private static void addPad(String str, int size, String pad,
			StringBuilder builder) {
		for (int i = 0; i < size - str.length(); i++) {
			builder.append(pad);
		}
	}

	public static <T> String parse(T object, int lineSize) {
		try {
			StringBuilder builder = new StringBuilder(leftPad("", lineSize, " "));

			for (java.lang.reflect.Field field : object.getClass()
					.getDeclaredFields()) {
				field.setAccessible(true);

				Field annotation = field.getAnnotation(Field.class);

				if (annotation == null) {
					continue;
				}
				
				String f = "";

				if (annotation.padDirection() == Field.LEFT_PAD) {
					f = leftPad(field.get(object).toString(), field.get(object)
							.toString().length(), annotation.pad());
				} else if (annotation.padDirection() == Field.RIGHT_PAD) {
					f = rightPad(field.get(object).toString(), field
							.get(object).toString().length(), annotation.pad());
				}

				builder.replace(annotation.firstPosition() - 1,
						annotation.lastPosition(), f);
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		long init = System.currentTimeMillis();

		List<String> lines = new ArrayList<String>();

		for (int i = 0; i < 30; i++) {
			lines.add(ModelSample.LINE_SAMPLE);
		}

		long f = System.currentTimeMillis();
		System.out.println(f - init);
		CompleteModel c = parse(lines, CompleteModel.class);

		String line = parse(c.getLines().get(0), 150);

		System.out.println(line);
	}
}