 //  1. Declare an event handler class and specify that the class either implements an ActionListener interface or extends a class that implements an ActionListener interface. For example:

//classCreateAndShowGUI implements ActionListener { 

  //über die bestehende Fkt. legen
  
 //  2. Register an instance of the event handler class as a listener on one or more components. For example:
//in the CreatAndShowGUI function for the buttons:
//    someComponent.addActionListener(this);
//    

 //  3. Include code that implements the methods in listener interface. For example:

//    public void actionPerformed(ActionEvent e) { 
 //                   ...//code that reacts to the action... 
//                }


static class createAndShowGUI implements ActionListener {
  
    static JFrame frame;
    static JFileChooser fc;
    //static JTextArea log;
    static JButton openButton, saveButton, sendButton;
    static JPanel buttonPanel;
    static JLabel title;
    static String[] labels;
    static JFormattedTextField[] parameters;
    static JFormattedTextField file_dxf;
    static JComboBox SketchNames;
    static String[] sketch_name;
    
    static void creating() /*throws FileNotFoundException*/ {
      
      //change default button trigger from space to enter
      setupEnterActionForAllButtons();
     
      //create Jcomponents 
      //Create a file chooser
      fc = new JFileChooser();
      
      //Create the log 
      log = new JTextArea(10,20);
      log.setMargin(new Insets(5,5,5,5));
      log.setEditable(false);
      JScrollPane logScrollPane = new JScrollPane(log);
      
      //the buttons: open, save, daten senden
      openButton = new JButton("Load Values");
      saveButton = new JButton("Save Values");
      sendButton = new JButton("Transmit Values");
      
      //put the buttons in a panel
      buttonPanel = new JPanel(); //use FlowLayout
      buttonPanel.add(openButton);
      buttonPanel.add(saveButton);
      buttonPanel.add(sendButton);
      
      title = new JLabel("Measurements");
      
      //the labels of the textfields and the textfields itsself are stored in arrays 
      labels = new String[32];
      parameters = new JFormattedTextField[labels.length];
      setDefaults(labels, parameters);    //set textfield values for user input
      
      //sketches to choose
      sketch_name = new String[3];
      sketch_name[0] = "Overall";
      sketch_name[1] = "Basic Dress";
      sketch_name[2] = "Basic Pants";
      
      file_dxf = new JFormattedTextField("YourSketch.dxf");
      SketchNames = new JComboBox(sketch_name);  //menu to chose a sketch
      
      //formatting the frame and adding the components
      frame = new JFrame("Dimension Data");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(1200,1100);
      frame.setLayout(new GridBagLayout());
      
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      
      c.gridx = 0;
      c.gridy = 0;      
      frame.getContentPane().add(title, c);
      
      JPanel bodyPanel = new JPanel();
      fillPane(bodyPanel, "body", 0, 9);            
      c.gridy = c.gridy + 1;
      frame.getContentPane().add(bodyPanel, c);
      
      JPanel armPanel = new JPanel();
      fillPane(armPanel, "arm", 15, 16);      
      c.gridy = c.gridy +1;
      frame.getContentPane().add(armPanel, c);

      JPanel legPanel  = new JPanel();
      fillPane(legPanel, "leg", 10, 14);     
      c.gridx = c.gridx +1;      
      c.gridy = 1;
      frame.getContentPane().add(legPanel, c);
      
      JPanel roomPanel = new JPanel();
      fillPane(roomPanel, "room to move", 27, 29);
      c.gridy = c.gridy +1;      
      frame.getContentPane().add(roomPanel, c);
      
      JPanel dartPanel = new JPanel();
      fillPane(dartPanel, "darts", 17, 26);
      c.gridx = c.gridx + 1;
      c.gridy = 1;
      frame.getContentPane().add(dartPanel, c);
      
      JPanel allowancePanel = new JPanel();
      fillPane(allowancePanel, "allowances", 30, 31);
      c.gridy = c.gridy +1;
      frame.getContentPane().add(allowancePanel, c);      

      SketchNames.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Choose a sketch"));      
      file_dxf.setColumns(15);
      file_dxf.setBorder(BorderFactory.createTitledBorder("output file name"));

      JPanel InOutPane = new JPanel();
      InOutPane.setLayout(new BoxLayout(InOutPane, BoxLayout.PAGE_AXIS));      
      InOutPane.add(SketchNames);
      InOutPane.add(Box.createRigidArea(new Dimension(0,10)));
      InOutPane.add(file_dxf);
      c.gridx = c.gridx +1;
      c.gridy = 1;
      frame.getContentPane().add(InOutPane, c);
      
      JPanel optionPanel = new JPanel();
      optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.PAGE_AXIS));
      optionPanel.add(buttonPanel);
      optionPanel.add(logScrollPane);
      c.gridy = c.gridy +1;
      frame.getContentPane().add(optionPanel, c);

      //frame.pack();
      frame.setVisible(true); 
      
      //change default button trigger from space to enter
      setupEnterActionForAllButtons();
      
      SketchNames.addActionListener(new createAndShowGUI());
      SketchNames.setSelectedIndex(0);
      sendButton.addActionListener(new createAndShowGUI());
      openButton.addActionListener(new createAndShowGUI());
      saveButton.addActionListener(new createAndShowGUI());      
    }
      
      public void actionPerformed(ActionEvent e){
             
             //Handle Choose of sketch
             if (e.getSource() == SketchNames) {
               int sketch_index = SketchNames.getSelectedIndex();
               log.append(sketch_name[sketch_index] + "\n");
               params.put("sketch_name", sketch_name[sketch_index]);
             }
             //Handle send button action
             else if (e.getSource() == sendButton) {
                for (int i=0; i<labels.length; i++){
                    params.put(labels[i], parameters[i].getValue());
                    params.put("output file name", file_dxf.getValue());
                }
                log.append("Data transmitted to PApplet \n");
                PApplet.main(new String[] {"Pattern"});
             }
            //Handle open button action  
            else if (e.getSource() == openButton) {
               int returnVal = fc.showOpenDialog(frame);
               if (returnVal == JFileChooser.APPROVE_OPTION) {
                   File file = fc.getSelectedFile();
                   //Reading
		   ReadWithScanner parser = new ReadWithScanner(file.getPath());
                   parser.processLineByLine(parameters, labels);
                   log.append("Opening: " + file.getName() + ".\n");
                 }
                 else {
                     log.append("Open command cancelled by user.\n");
                 }
                 log.setCaretPosition(log.getDocument().getLength());
            }
            //Handle save button action.
          else if (e.getSource() == saveButton) {
                 int returnVal = fc.showSaveDialog(frame);
                 if (returnVal == JFileChooser.APPROVE_OPTION) {
                     File file = fc.getSelectedFile();
                     WriteToFile parser = new WriteToFile(file.getPath());
                     parser.writing(labels, parameters);
                     log.append("Saving: " + file.getName() + ".\n");
                 }
                 else {
                     log.append("Save command cancelled by user.\n");
                 }
                 log.setCaretPosition(log.getDocument().getLength());
          }
      }      


    //change default button trigger from space to enter
    private static void setupEnterActionForAllButtons() {
        InputMap im = (InputMap) UIManager.getDefaults().get("Button.focusInputMap");
        Object pressedAction = im.get(KeyStroke.getKeyStroke("pressed SPACE"));
        Object releasedAction = im.get(KeyStroke.getKeyStroke("released SPACE"));

        im.put(KeyStroke.getKeyStroke("pressed ENTER"), pressedAction);
        im.put(KeyStroke.getKeyStroke("released ENTER"), releasedAction);
    }
    
    //set the labels to the corresponding parameters and some default values
    private static void setDefaults(String[] labels, JFormattedTextField[] parameters){
       
      //body
      labels[1] = "waist width";
      labels[2] = "hip width";
      labels[3] = "sitting height";
      labels[0] = "confection size";
      labels[4] = "bust size";
      labels[5] = "back length";
      labels[6] = "front length";
      labels[7] = "nipple distance";
      labels[8] = "bust depth";
      labels[9] = "hip depth";
      //legs
      labels[10] = "dress length";
      labels[11] = "knee width";
      labels[12] = "foot width";
      labels[13] = "side length";
      labels[14] = "feet hem height";  //fußsaumhöhe
      //arms
      labels[15] = "arm length";
      labels[16] = "arm hem weight";  
      
      //darts
      //shoulder(2)
      labels[17] = "shoulder dart length";
      labels[18] = "shoulder dart width";
      //waist(4)
      labels[19] = "waist dart length (back)";
      labels[20] = "waist dart width (back)";
      labels[21] = "waist dart length (front)";
      labels[22] = "waist dart width (front)";
      //hip(2)
      labels[23] = "hip dart width (front)";
      labels[24] = "hip dart length (front)";
      labels[25] = "hip dart width (back)";
      labels[26] = "hip dart length (back)";
      //room to move
      labels[27] = "waist";
      labels[28] = "hip";
      labels[29] = "vertical (for overalls)";
      //allowance
      labels[30] = "seam";
      labels[31] = "edges";
      
      for (int i=0; i<labels.length; i++){
        parameters[i] = new JFormattedTextField("0");
        parameters[i].setBorder(BorderFactory.createTitledBorder(labels[i]));
        parameters[i].setColumns(20);
      }
      parameters[17].setValue("4");
      parameters[18].setValue("1.5");
      parameters[19].setValue("30");
      parameters[20].setValue("3");
      parameters[21].setValue("28");
      parameters[22].setValue("2");
      parameters[23].setValue("2");
      parameters[24].setValue("9");
      parameters[25].setValue("3");
      parameters[26].setValue("12"); 
      parameters[27].setValue("2");
      parameters[28].setValue("2");     
      parameters[29].setValue("3");
      parameters[30].setValue("1");
      parameters[31].setValue("3");      
      
    }
    
   static void addToPane(JPanel pane, int begin, int end){
        for (int i=begin; i<end+1 ;i++){
          pane.add(parameters[i]);
          pane.add(Box.createRigidArea(new Dimension(0,5)));
        }  
        
      }
   
   static void fillPane(JPanel pane, String title, int begin, int end){
     
     pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
     addToPane(pane, begin, end);
     pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), title));
     
   }
    
}
