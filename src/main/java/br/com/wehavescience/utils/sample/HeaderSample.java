package br.com.wehavescience.utils.sample;

import br.com.wehavescience.utils.file.annotations.Field;

/**
 * @author gabriel
 *
 * Oct 10, 2013
 */
public class HeaderSample {
	@Field(firstPosition = 1, lastPosition = 5)
	private String field1;
	@Field(firstPosition = 6, lastPosition = 10)
	private String field2;
	
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	
	@Override
	public String toString() {
		return "HeaderSample [field1=" + field1 + ", field2=" + field2 + "]";
	}
}