package br.com.wehavescience.utils.test.main;

import java.util.ArrayList;
import java.util.List;

import br.com.wehavescience.utils.file.FileParser;
import br.com.wehavescience.utils.sample.CompleteModel;
import br.com.wehavescience.utils.sample.ModelSample;

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
		for(int i = 0;i<30;i++){
			file.add(ModelSample.LINE_SAMPLE);
		}
		
		/**
		 * Converting the list to the object
		 * 
		 * This object contains: @Header, @Footer, @Lines
		 */
		CompleteModel model = FileParser.asObject(file, CompleteModel.class);
		
		System.out.println(model.toString());
		
		/**
		 * Converting again to List<String>
		 */
		List<String> lines = FileParser.asStringList(model, 150, 30, 30);
		
		System.out.println(lines.toString());
	}
}
