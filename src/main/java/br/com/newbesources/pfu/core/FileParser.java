package br.com.newbesources.pfu.core;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import br.com.newbesources.pfu.core.annotations.Field;
import br.com.newbesources.pfu.core.annotations.Footer;
import br.com.newbesources.pfu.core.annotations.Header;
import br.com.newbesources.pfu.core.annotations.Lines;
import br.com.newbesources.pfu.core.exception.InvalidTypeException;
import br.com.newbesources.pfu.core.utils.PadUtils;

/**
 * @author gabriel
 * 
 *         Oct 10, 2013
 */
@SuppressWarnings("unchecked")
public class FileParser {
	public static <T> T asObject(String line, Class<T> objectType) {
		try {
			T instance = objectType.newInstance();

			for (java.lang.reflect.Field field : objectType.getDeclaredFields()) {
				field.setAccessible(true);

				Field annotation = field.getAnnotation(Field.class);

				if (annotation == null) {
					continue;
				}

				String f = "";

				if (annotation.padDirection() == Field.LEFT_PAD) {
					f = PadUtils.removeLeftPad(
							line.subSequence(annotation.firstPosition() - 1,
									annotation.lastPosition()).toString(),
							annotation.pad());
				}

				if (annotation.padDirection() == Field.RIGHT_PAD) {
					f = PadUtils.removeRightPad(
							line.subSequence(annotation.firstPosition() - 1,
									annotation.lastPosition()).toString(),
							annotation.pad());
				}
				field.set(instance, f);
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T, L, H, F> T asObject(List<String> lines,
			Class<T> objectType) {
		try {
			T instance = objectType.newInstance();
			for (java.lang.reflect.Field field : objectType.getDeclaredFields()) {
				field.setAccessible(true);

				Lines fields = field.getAnnotation(Lines.class);
				Header header = field.getAnnotation(Header.class);
				Footer footer = field.getAnnotation(Footer.class);

				if (header != null) {
					H h = (H) asObject(lines.get(0),
							Class.forName(field.getType().getName()));
					field.set(instance, h);
				}

				if (footer != null) {
					F f = (F) asObject(lines.get(lines.size() - 1),
							Class.forName(field.getType().getName()));
					field.set(instance, f);
				}

				/**
				 * The @Lines is always a list, and, a list have a type, so, we
				 * need to specify this type to the string converter
				 */

				if (fields != null) {
					/**
					 * If it isn't a list...
					 */
					ParameterizedType type = validateListType(field);

					Class<L> lineType = (Class<L>) type
							.getActualTypeArguments()[0];
					List<L> lineList = new ArrayList<L>();

					for (int i = 0; i < lines.size() - 1; i++) {
						if (i == 0) {
							continue;
						}
						L line = (L) asObject(lines.get(i), lineType);
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

	private static ParameterizedType validateListType(
			java.lang.reflect.Field field) {
		try {
			ParameterizedType type = (ParameterizedType) field.getGenericType();

			if (!type.getRawType().equals(List.class)) {
				throw new InvalidTypeException(
						"You can use @Lines only on List objects");
			}
			return type;
		} catch (Exception e) {
			throw new InvalidTypeException(
					"You can use @Lines only on List objects");
		}
	}

	public static <T> String asString(T object, int lineSize) {
		try {
			StringBuilder builder = new StringBuilder(PadUtils.leftPad("",
					lineSize, " "));

			for (java.lang.reflect.Field field : object.getClass()
					.getDeclaredFields()) {
				field.setAccessible(true);

				Field annotation = field.getAnnotation(Field.class);

				if (annotation == null) {
					continue;
				}

				String f = "";

				int fieldSize = annotation.lastPosition() - annotation.firstPosition() + 1;
				
				if (annotation.padDirection() == Field.LEFT_PAD) {
					f = PadUtils.leftPad(field.get(object).toString(), fieldSize, annotation.pad());
				} else if (annotation.padDirection() == Field.RIGHT_PAD) {
					f = PadUtils.rightPad(field.get(object).toString(), fieldSize, annotation.pad());
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

	public static <T, L, H, F> List<String> asStringList(T object,
			int lineSize, int headerSize, int footerSize) {
		try {
			List<String> list = new ArrayList<String>();

			boolean hasHeader = false;
			
			for (java.lang.reflect.Field field : object.getClass()
					.getDeclaredFields()) {
				field.setAccessible(true);

				Lines lines = field.getAnnotation(Lines.class);
				Header header = field.getAnnotation(Header.class);
				Footer footer = field.getAnnotation(Footer.class);

				if (header != null) {
					list.add(0, asString(field.get(object), headerSize));
					hasHeader = true;
					continue;
				}
				if (footer != null) {
					list.add(asString(field.get(object), footerSize));
					continue;
				}
				if (lines != null) {
					validateListType(field);
					
					int index = hasHeader ? 1 : 0;
					
					for (L line : (List<L>) field.get(object)) {
						list.add(index, asString(line, lineSize));
					}
					continue;
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}