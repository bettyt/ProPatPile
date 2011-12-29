/**
Simple Program to write a text file
*/


static class WriteToFile {

  File fFile;
  
  /**
   Constructor.
   @param aFileName full name of an existing, readable file.
  */
  public WriteToFile(String aFileName){
    fFile = new File(aFileName);
  }
  
  /**
  Method
  */
  
  public final void writing(String[] labels, JFormattedTextField[] parameters){
     
                PrintWriter out = null;
                int i;            
    
		try {
		     out = new PrintWriter(new FileWriter(fFile));
		        
                     for (i=0;i<parameters.length;i++){ 
			// Write text to file
			out.println(labels[i]+" = "+parameters[i].getValue());
                     }
                     out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
                finally {
                  if (out!=null){
                    out.close();
                  }
                }

  }
  
}
