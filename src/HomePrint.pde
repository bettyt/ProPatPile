void HomePrint(int start_x, int start_y, int a, int b){  
  
  stroke(255, 0, 0);
  strokeWeight(0.8);    //A4-Format - je 3cm fuer die Raender = 15*24;
  
   //Testsquare 10x10cm
  rect(start_x + 3, start_y + 5, 10, 10);
  
  for (int y = start_y; y < b; y = y + 24) {
    line(0, y, a, y);
    
    for (int x = start_x; x < a; x = x + 15){ 
      line(x, 0, x, b);
      
      if (x > start_x && y > start_y) {
        ellipse(x - 15/2.0, y, 2, 2);
        ellipse (x, y - 24/2.0, 2, 2);
      }
    }
  }
}
    
