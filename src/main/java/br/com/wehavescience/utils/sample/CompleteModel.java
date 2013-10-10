package br.com.wehavescience.utils.sample;

import java.util.List;

import br.com.wehavescience.utils.file.annotations.Field;
import br.com.wehavescience.utils.file.annotations.Fields;
import br.com.wehavescience.utils.file.annotations.Footer;
import br.com.wehavescience.utils.file.annotations.Header;

/**
 * @author gabriel
 * 
 * Oct 10, 2013
 */
public class CompleteModel {
	@Header
	private HeaderSample header;
	@Footer
	private FooterSample footer;
	@Fields
	private List<Model> lines;

	public HeaderSample getHeader() {
		return header;
	}

	public void setHeader(HeaderSample header) {
		this.header = header;
	}

	public FooterSample getFooter() {
		return footer;
	}

	public void setFooter(FooterSample footer) {
		this.footer = footer;
	}

	public List<Model> getLines() {
		return lines;
	}

	public void setLines(List<Model> lines) {
		this.lines = lines;
	}
}
