package br.com.wehavescience.utils.sample;

import br.com.wehavescience.utils.file.annotations.Field;

/**
 * @author gabriel
 *
 * Oct 10, 2013
 */
public class FooterSample {
	@Field(firstPosition = 1, lastPosition = 5)
	private String field1;
	@Field(firstPosition = 6, lastPosition = 10)
	private String field2;
}
