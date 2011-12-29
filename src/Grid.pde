void Grid (int a, int b) {
  
  strokeWeight(1);
  stroke(200);
  for (int i = 0; i < a; i = i+1) {
    line(i, 0, i, b);
  }
  for (int x = 0; x < b; x = x+1) {
    line(0, x, a, x);
  }
}
