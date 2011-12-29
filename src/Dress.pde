  void Dress(HashMap dress, RawDXF dxf){
  
  float[] Lh = (float[]) dress.get("Lh");
  float[] Lv = (float[]) dress.get("Lv");
  float[] L1 = (float[]) dress.get("L1");
  float[] H1 = (float[]) dress.get("H1");
  float[] T1 = (float[]) dress.get("T1");
  float[] R1 = (float[]) dress.get("R1");
  float[] W = (float[]) dress.get("W");
  float[] Hp = (float[]) dress.get("Hp");
  float[] Sch = (float[]) dress.get("Sch");
  float[] S = (float[]) dress.get("S");
  float[] VAE = (float[]) dress.get("VAE");
  float[] Schv = (float[]) dress.get("Schv");
  float[] Hsv = (float[]) dress.get("Hsv");
  float[] A = (float[]) dress.get("A");
  float[] B = (float[]) dress.get("B");
  float[] T3 = (float[]) dress.get("T3");
  float[] H2 = (float[]) dress.get("H2");
  float[] Hh = (float[]) dress.get("Hh");
  float[] Hv = (float[]) dress.get("Hv");
  float[] HAE = (float[]) dress.get("HAE");
  float[] TAbs1 = (float[]) dress.get("TAbs1");
  float[] TAbs2 = (float[]) dress.get("TAbs2");
  float[] TAbh1 = (float[]) dress.get("TAbh1");
  float[] TAbh2 = (float[]) dress.get("TAbh2");
  float[] TAbh3 = (float[]) dress.get("TAbh3");
  float[] TAbh4 = (float[]) dress.get("TAbh4");
  float[] BAb = (float[]) dress.get("BAb");
  float[] TAbv1 = (float[]) dress.get("TAbv1");
  float[] TAbv2 = (float[]) dress.get("TAbv2");
  float[] TAbv3 = (float[]) dress.get("TAbv3");
  float[] TAbv4 = (float[]) dress.get("TAbv4");
  float[] Abh1 = (float[]) dress.get("Abh1");
  float[] Abh2 = (float[]) dress.get("Abh2");
  float[] Abh3 = (float[]) dress.get("Abh3");
  float[] Abh4 = (float[]) dress.get("Abh4");
  
  float[] Bp = (float[]) dress.get("Bp");
  float[] Rp =(float[]) dress.get("Rp");
  
  
  PVector VoMi = new PVector(A[0] - B[0], A[1] - B[1]);
  PVector Help = new PVector(0 , 1);
  println(VoMi); println(Help);
  float rot = PVector.angleBetween(VoMi, Help) - 2*PI;
  print("rot"); println(rot);
  
  //Zeichnung
  beginRaw(dxf); 
  dxf.setLayer(0);
  int start_x = 10 - 2;
  int start_y = (int) (L1[1] - nzS - 2);
  HomePrint(start_x, start_y, a, b);
  dxf.setLayer(1);
  stroke(0,0,0);
  strokeWeight(1);
  
  printMatrix();
  
  pushMatrix();
  translate(100,0 );
  //Vorderteil//die beiden Vorderteile werden hier nicht zusammengenaeht sondern als ein stueck ausgeschnitten//es muesste noch eine nachtzugabe vorgesehn werden
  println(H2); println(Lv); println(L1);
  line(H2[0], L1[1]-nzS, Lv[0]+nz, Lv[1]-nzS);
  line(Lv[0]+nz, Lv[1]-nzS, Hv[0]+nz, Hv[1]);
  line(TAbs1[0]+nz, TAbs1[1], BAb[0]+nz, BAb[1]);
  //line(BAb[0]+nz, BAb[1], S[0]+nz, S[1]+nz);
  //line(VAE[0]+1+nz, VAE[1], VAE[0]-1+nz, VAE[1]);
  //line(VAE[0]+nz, VAE[1]+1, VAE[0]+nz, VAE[1]-1);
  //line(Schv[0]+nz, Schv[1]+nz, Hsv[0], Hsv[1]+nz);
  //line(A[0], A[1], B[0], B[1]);
  line(T3[0], T3[1], B[0], B[1]);
  line(T3[0], T3[1], H2[0], H2[1]);
  line(H2[0], H2[1], H2[0], L1[1]-nzS);
  line(B[0], B[1], BAb[0]+nz, BAb[1]);
  line(TAbv1[0], TAbv1[1], TAbv3[0], TAbv3[1]);
  line(TAbv2[0], TAbv2[1], TAbv3[0], TAbv3[1]);
  line(TAbv1[0], TAbv1[1], TAbv4[0], TAbv4[1]);
  line(TAbv2[0], TAbv2[1], TAbv4[0], TAbv4[1]);
  popMatrix();
  //Abnaeher verschieben
  pushMatrix();
  stroke(150,0,0);
  translate(B[0]+100, B[1]);
  rotate(rot);
  translate(-B[0], -B[1]);
  line(BAb[0]+nz, BAb[1], S[0]+nz, S[1]+nz);
  line(VAE[0]+1+nz, VAE[1], VAE[0]-1+nz, VAE[1]);
  line(VAE[0]+nz, VAE[1]+1, VAE[0]+nz, VAE[1]-1);
  line(Schv[0]+nz, Schv[1]+nz, Hsv[0], Hsv[1]+nz);
  line(A[0], A[1], B[0], B[1]);
  line(B[0], B[1], BAb[0]+nz, BAb[1]);
  //armhole_front
//  armhole_fr(S, VAE, Schv, nz);
  //neckline_front
//  neckline_fr(A, Hsv, nz);
  popMatrix();
  
  stroke(0,0,0);
  pushMatrix();
  translate(110,0);
  //Hinterteil//die beiden Hinterteile werden hier nicht zusammengenaeht sondern als ein stueck ausgeschnitten//es muesste noch eine nachtzugabe vorgesehn werden
  line(Lh[0]-nz, Lh[1]-nzS, L1[0], L1[1]-nzS);
  line(L1[0], L1[1]-nzS, H1[0], H1[1]);
  line(H1[0], H1[1], T1[0], T1[1]);
  line(T1[0], T1[1], R1[0], R1[1]);
  line(R1[0], R1[1], W[0], W[1]);
  line(Hp[0], Hp[1]+nz, Sch[0]-nz, Sch[1]+nz);
  line(HAE[0]+1-nz, HAE[1], HAE[0]-1-nz, HAE[1]);
  line(HAE[0]-nz, HAE[1]+1, HAE[0]-nz, HAE[1]-1);
  line(S[0]-nz, S[1]+nz, TAbs2[0]-nz, TAbs2[1]);
  line(Hh[0]-nz, Hh[1], Lh[0]-nz, Lh[1]);
  line(TAbh1[0], TAbh1[1], TAbh3[0], TAbh3[1]);
  line(TAbh2[0], TAbh2[1], TAbh3[0], TAbh3[1]);
  line(TAbh1[0], TAbh1[1], TAbh4[0], TAbh4[1]);
  line(TAbh2[0], TAbh2[1], TAbh4[0], TAbh4[1]);
  //armhole_back
//  armhole_ba(S, HAE, Sch, nz);
  //neckline_back
//  neckline_ba(W, Hp, nz);
  popMatrix();
  
  dxf.setLayer(2);
  //sleeve
  basic_sleeve(0, 70, nz, nzS, Alg, Asw, Schv, Bp, Sch, Rp, S, VAE, HAE);
  
  endRaw();
  log.append("DONE");
}
