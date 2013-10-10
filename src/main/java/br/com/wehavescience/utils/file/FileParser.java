package br.com.wehavescience.utils.file;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import br.com.wehavescience.utils.file.annotations.Field;
import br.com.wehavescience.utils.file.annotations.Fields;
import br.com.wehavescience.utils.file.annotations.Footer;
import br.com.wehavescience.utils.file.annotations.Header;
import br.com.wehavescience.utils.sample.CompleteModel;
import br.com.wehavescience.utils.sample.Model;

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

				field.set(instance, line.subSequence(
						annotation.firstPosition() - 1,
						annotation.lastPosition()));
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T, L, H, F> T parse(List<String> lines, Class<T> objectType) {
		try {
			T instance = objectType.newInstance();
			for(java.lang.reflect.Field field : objectType.getDeclaredFields()){
				field.setAccessible(true);

				Fields fields = field.getAnnotation(Fields.class);
				Header header = field.getAnnotation(Header.class);
				Footer footer = field.getAnnotation(Footer.class);

				if (header != null) {
					H h = (H) parse(lines.get(0), Class.forName(field.getType().getName()));
					field.set(instance, h);
				}

				if (footer != null) {
					F f = (F) parse(lines.get(lines.size() - 1), Class.forName(field.getType().getName()));
					field.set(instance, f);
				}

				if (fields != null) {

					/**
					 * MAGIC
					 */
					String lineClassName = field.toGenericString().replaceAll("\\D*List<", "").replaceAll(">\\D*", "");
					Class<L> lineType = (Class<L>) Class.forName(lineClassName);
					List<L> lineList = new ArrayList<L>();

					for(int i = 0;i<lines.size()-1;i++){
						if(i == 0){
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

	private static String leftPad(String str, int size, char pad) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size - str.length(); i++) {
			builder.append(pad);
		}
		builder.append(str);

		return builder.substring(0, size);
	}

	public static <T> String parse(T object, int stringSize, char pad) {
		try {
			StringBuilder builder = new StringBuilder(leftPad("", stringSize,
					pad));

			for (java.lang.reflect.Field field : object.getClass()
					.getDeclaredFields()) {
				field.setAccessible(true);

				Field annotation = field.getAnnotation(Field.class);

				if (annotation == null) {
					continue;
				}

				String f = leftPad(field.get(object).toString(),
						field.get(object).toString().length(), pad);

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

		for(int i = 0;i<30;i++){
			lines.add(Model.LINE_SAMPLE);
		}

		long f = System.currentTimeMillis();
		System.out.println(f-init);
		CompleteModel c = parse(lines, CompleteModel.class);



		c.toString();
	}
}