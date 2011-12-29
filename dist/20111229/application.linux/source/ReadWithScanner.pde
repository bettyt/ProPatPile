/*Reading dimensioning values from a formatted textfile
and writing them to the parameter-fields, which are displayed in the GUI
*/


static class ReadWithScanner {
  File fFile;
  Scanner scanner;
  /**
   Constructor.
   @param aFileName full name of an existing, readable file.
  */
  public ReadWithScanner(String aFileName) {
    fFile = new File(aFileName);  
  }
  
  /** Template method that calls {@link #processLine(String)}.  */
  public final void processLineByLine(JFormattedTextField[] parameters, String[] labels) {
    
    int i = 0;
    String[] NameValue;
    Scanner scanner = null;
    //Note that FileReader is used, not File, since File is not Closeable
    //Scanner scanner = new Scanner(new FileReader(fFile));
    try {
       scanner = new Scanner(new FileReader(fFile));
      //first use a Scanner to get each line
      while (scanner.hasNextLine()){
        NameValue = processLine(scanner.nextLine());
        if ((labels[i]).equals(NameValue[0])){       //wenn die labels übereinstimmen, setze neuen ParamWert
    	  parameters[i].setValue(NameValue[1]);
        }
        else {
          log.append("Invalid name in line" +(i+1)+ ".It has to be:" +labels[i]+"\n");
        }
        //log.append(labels[i] + "=" + parameters[i].getValue() +"\n");
        i++;     
        
      }
    }
    catch (IOException e) {
      e.printStackTrace();
      log.append(e.getMessage());
    }   
    finally {
      //ensure the underlying stream is always closed
      //this only has any effect if the item passed to the Scanner
      //constructor implements Closeable (which it does in this case).
      if (scanner != null){
        scanner.close();
      }
    } 
  }
  
  /** 
   Overridable method for processing lines in different ways.
    
   <P>This simple default implementation expects simple name-value pairs, separated by an 
   '=' sign. Examples of valid input : 
   <tt>height = 167cm</tt>
   <tt>mass =  65kg</tt>
   <tt>disposition =  "grumpy"</tt>
   <tt>this is the name = this is the value</tt>
  */
  
  protected String[] processLine(String aLine){
    //use a second Scanner to parse the content of each line 
    String[] NameValue = new String[2];
    Scanner scanner = new Scanner(aLine);
    scanner.useDelimiter(" = ");
    if (scanner.hasNext()){
      String name = scanner.next();
      String value = scanner.next();
      //log.append("Name is : " + quote(name.trim()) + ", and Value is : " + quote(value.trim()) +"\n" );
      NameValue[0] = name;
      NameValue[1] = value;    
    }
    else {
      log.append("Empty or invalid line. Unable to process.");
      NameValue[0] = "null";
      NameValue[1] = "null";
    }
    //no need to call scanner.close(), since the source is a String
    return NameValue;
  }

 
  private String quote(String aText){
    String QUOTE = "'";
    return QUOTE + aText + QUOTE;
  }
  

} 


