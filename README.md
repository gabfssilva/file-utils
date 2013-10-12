###Positional File Utils (PFU)

There are a lot of situations that we need to deal with positional files, maybe to interact with a legacy system or whatever.
Create a parser probably it's not the best way to deal with it.

PFU is a tool that helps you to convert positional txt files (String) to Object and Object to positional txt files based on annotations:

```
@Field(firstPosition = 1, lastPosition = 4)
private String firstWord;
@Field(firstPosition = 5, lastPosition = 6)
private String secondWord;

...

@Field(firstPosition = 18, lastPosition = 50, padDirection=Field.LEFT_PAD)
private String seventhWord;
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

Most positional files have a header, a footer and several lines, so, there are other annotations to deal with it:

```
@Header
private HeaderSample header;
@Footer
private FooterSample footer;
@Lines
private List<ModelSample> lines;
```

And the same to convert:

```
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
```

If you are interested in it, run the class br.com.newbesources.pfu.test.main.FileUtilsMain, you will easily understand how PFU works.

