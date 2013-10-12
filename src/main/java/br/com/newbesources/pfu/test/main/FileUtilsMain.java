package br.com.newbesources.pfu.test.main;

import java.util.ArrayList;
import java.util.List;

import br.com.newbesources.pfu.core.FileParser;
import br.com.newbesources.pfu.sample.CompleteModelSample;
import br.com.newbesources.pfu.sample.ModelSample;

/**
 * @author gabriel
 *
 * Oct 11, 2013
 */
public class FileUtilsMain {
	public static void main(String[] args) {
		List<String> file = new ArrayList<String>();
		
		/**
		 * Adding 30 lines
		 * 
		 * First line = header
		 * Last line = footer
		 */
		
		file.add("MYHEADER");
		
		for(int i = 1;i<30;i++){
			file.add(ModelSample.LINE_SAMPLE);
		}
		
		file.add("MYFOOTER");
		
		/**
		 * Converting the list to the object
		 * 
		 * This object contains: @Header, @Footer, @Lines
		 */
		CompleteModelSample model = FileParser.asObject(file, CompleteModelSample.class);
		
		System.out.println(model.toString());
		
		/**
		 * Converting again to List<String>
		 */
		List<String> lines = FileParser.asStringList(model, 50, 30, 30);
		
		System.out.println(lines.toString());
	}
}
