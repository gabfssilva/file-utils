File Utils - THIS PROJECT DOES NOT HAVE A NAME YET, SO, I CALL IT FILE UTILS.

It is a tool that helps you to convert positional txt files (String) to Object and Object to positional txt files based on annotations:

```
package br.com.wehavescience.utils.sample;

import br.com.wehavescience.utils.file.annotations.Field;

/**
 * @author gabriel
 * 
 * Oct 10, 2013
 */
public class ModelSample {
	@Field(firstPosition = 1, lastPosition = 4, pad="0", padDirection=Field.LEFT_PAD)
	private String firstWord;
	@Field(firstPosition = 5, lastPosition = 6)
	private String secondWord;
	@Field(firstPosition = 7, lastPosition = 8)
	private String thirdWord;
	//...
}
```

It helps you to parse String to Object:

```
ModelSample model = FileParser.asObject("MYSTRINGMODEL", ModelSample.class);
```

And Object to String:

```
ModelSample model = new ModelSample();

//...

int stringSize = 20;

String line = FileParser.asString(model, stringSize);
```

Most positional files have a header, a footer and several lines so, you can use the other annotations:

```
package br.com.wehavescience.utils.sample;

import java.util.List;

import br.com.wehavescience.utils.file.annotations.Footer;
import br.com.wehavescience.utils.file.annotations.Header;
import br.com.wehavescience.utils.file.annotations.Lines;

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
	@Lines
	private List<ModelSample> lines;

  //...
}
```

And the same to convert:

```
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
```

