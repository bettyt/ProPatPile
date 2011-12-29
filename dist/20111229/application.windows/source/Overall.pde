void Overall(HashMap front, HashMap dress, RawDXF dxf){
 
  float[] Fa = (float[]) front.get("Fa");
  float[] Fb = (float[]) front.get("Fb");
  float[] Kb = (float[]) front.get("Kb");
  float[] Schritt = (float[]) front.get("Sch");  
  float[] BH = (float[]) front.get("B");  
  float[] H = (float[]) front.get("H");
  float[] Ka = (float[]) front.get("Ka");  
  float[] Fc = (float[]) front.get("Fc");
  float[] Fd = (float[]) front.get("Fd");
  float[] Kd = (float[]) front.get("Kd");
  float[] Br = (float[]) front.get("Br");
  float[] Ga = (float[]) front.get("Ga");
  float[] Ba = (float[]) front.get("Ba");
  float[] Ha = (float[]) front.get("Ha");
  float[] Kc = (float[]) front.get("Kc");
 
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
  float[] Rp = (float[]) dress.get("Rp");
  
  //Zeichnung 
  beginRaw(dxf);
  dxf.setLayer(0);

  int start_x = 10 - 2;
  int start_y = (int) (Fc[1] - nzS - 2);
  HomePrint(start_x, start_y, a, b);
  dxf.setLayer(1);

  stroke(0,0,0);
  strokeWeight(1);

  pushMatrix();
  translate(10,0);
  //Vorderhose        //Nathzugabe am Saum: 3cm, sonst 1 cm 
  line(Fa[0]-nz, Fa[1] - nzS, Fb[0]+nz, Fb[1]-nzS);
  line(Fb[0]+nz, Fb[1]-nzS, Kb[0]+nz, Kb[1]);
  line(Kb[0]+nz, Kb[1], Schritt[0]+nz, Schritt[1]+nz);
  line(BH[0]+nz, BH[1]+nz, H[0]-nz, H[1]+nz);
  line(H[0]-nz, H[1]+nz, Ka[0]-nz, Ka[1]);
  line(Ka[0]-nz, Ka[1], Fa[0]-nz, Fa[1]-nzS);
  popMatrix();
  
  //Hinterhose
  pushMatrix();
  translate(15 + 2*(Hv[0]-H2[0]) - Ha[0], 0); 
  line(Fc[0]-nz, Fc[1]-nzS, Fd[0]+nz, Fd[1]-nzS);
  line(Fd[0]+nz, Fd[1]-nzS, Kd[0]+nz, Kd[1]);
  line(Kd[0]+nz, Kd[1], Br[0]+nz, Br[1]);
  line(Br[0]+nz, Br[1], Ga[0]+nz, Ga[1]+nz);
  line(Schritt[0]+nz+1, Schritt[1]+nz, Schritt[0]+nz-1, Schritt[1]+nz);
  line(Schritt[0]+nz, Schritt[1]+nz+1, Schritt[0]+nz, Schritt[1]+nz-1);
  line(Ba[0]+nz, Ba[1], Ha[0]-nz, Ha[1]);
  line(Ha[0]-nz, Ha[1], Kc[0]-nz, Kc[1]);
  line(Kc[0]-nz, Kc[1], Fc[0]-nz, Fc[1]-nzS);
  popMatrix();
  
  //Vorderteil//    //Nahtzigabe nur an einer Seiten und unten, das Vorderzteil wird aus einem Stueck ausgeschnitten
  pushMatrix();
  translate(10-H2[0]+Hv[0]-H2[0], H[1]-Fc[1]-H2[1]+Sauh+bfo+2*nz+2);
  line(Hv[0]+nz, Hv[1]-nz-bfo-1, Hv[0]+nz, Hv[1]-bfo-nz+1);
  line(Hv[0]+nz-1, Hv[1]-nz-bfo, Hv[0]+nz+1, Hv[1]-bfo-nz);
  line(TAbs1[0]+nz, TAbs1[1], BAb[0]+nz, BAb[1]);
  //line(T3[0], T3[1], B[0], B[1]);
  //line(T3[0], T3[1], H2[0], H2[1]);
  line(H2[0], H2[1]-nz-bfo, Hv[0]+nz, Hv[1]-nz-bfo);
  line(B[0], B[1], BAb[0]+nz, BAb[1]);
  line(TAbv1[0], TAbv1[1], TAbv3[0], TAbv3[1]);
  line(TAbv2[0], TAbv2[1], TAbv3[0], TAbv3[1]);
  line(TAbv1[0], TAbv1[1], TAbv4[0], TAbv4[1]);
  line(TAbv2[0], TAbv2[1], TAbv4[0], TAbv4[1]);
  popMatrix();
  
  //Spiegelung
  pushMatrix();
  translate(10+H2[0]+Hv[0]-H2[0], H[1]-Fc[1]-H2[1]+Sauh+bfo+2*nz+2);
  line(-(Hv[0]+nz), Hv[1]-nz-bfo-1, -(Hv[0]+nz), Hv[1]-nz-bfo+1);
  line(-(Hv[0]+nz-1), Hv[1]-nz-bfo, -(Hv[0]+nz+1), Hv[1]-nz-bfo);
  line(-(TAbs1[0]+nz), TAbs1[1], -(BAb[0]+nz), BAb[1]);
  line(-B[0], B[1], -(BAb[0]+nz), BAb[1]);
  line(-H2[0], H2[1]-nz-bfo, -(Hv[0]+nz), Hv[1]-nz-bfo);
  line(-TAbv1[0], TAbv1[1], -TAbv3[0], TAbv3[1]);
  line(-TAbv2[0], TAbv2[1], -TAbv3[0], TAbv3[1]);
  line(-TAbv1[0], TAbv1[1], -TAbv4[0], TAbv4[1]);
  line(-TAbv2[0], TAbv2[1], -TAbv4[0], TAbv4[1]);
  popMatrix();
  
  //Rotation
  PVector VoMi = new PVector(A[0] - B[0], A[1] - B[1]);
  PVector Help = new PVector(0 , 1);
  float rot = PVector.angleBetween(VoMi, Help) - 2*PI;
  
  pushMatrix();
  stroke(150,0,0);
  translate(B[0]+10-H2[0]+Hv[0]-H2[0], B[1]+H[1]-Fc[1]-H2[1]+Sauh+bfo+2*nz+2);
  rotate(rot);  
  translate(-B[0], -B[1]);
  line(BAb[0]+nz, BAb[1], S[0]+nz, S[1]+nz);
  line(VAE[0]+nz+1, VAE[1], VAE[0]+nz-1, VAE[1]);
  line(VAE[0]+nz, VAE[1]+1, VAE[0]+nz, VAE[1]-1);
  line(Schv[0]+nz, Schv[1]+nz, Hsv[0]-nz, Hsv[1]+nz);
  line(A[0], A[1]+nz, B[0], B[1]);
  line(B[0], B[1], BAb[0]+nz, BAb[1]);
  popMatrix();
  
  //Spiegelung
  pushMatrix();
  translate(-B[0]+10+H2[0]+Hv[0]-H2[0],B[1]+H[1]-Fc[1]-H2[1]+Sauh+bfo+2*nz+2);
  rotate(-rot); 
  translate(B[0], -B[1]);
  line(-(BAb[0]+nz), BAb[1], -(S[0]+nz), S[1]+nz);
  line(-(VAE[0]+nz+1), VAE[1], -(VAE[0]+nz-1), VAE[1]);
  line(-(VAE[0]+nz), VAE[1]+1, -(VAE[0]+nz), VAE[1]-1);
  line(-(Schv[0]+nz), Schv[1]+nz, -(Hsv[0]-nz), Hsv[1]+nz);
  line(-B[0], B[1], -(BAb[0]+nz), BAb[1]);
  popMatrix();
  
  //Hinterteil//    //aus zwei Steucken Stoff: Nahtzugabe auf beiden Seiten
  stroke(0,0,0);
  pushMatrix();
  translate(15 + 2*(Hv[0]-H2[0])-Hh[0], H[1]-Fc[1]-H2[1]+Sauh+bfo);
  line(Hh[0]-nz, Hh[1]-bfo-1, Hh[0]-nz, Hh[1]-bfo+1);
  line(Hh[0]-nz-1, Hh[1]-bfo, Hh[0]-nz+1, Hh[1]-bfo);
  line(H1[0]+nz+1, H1[1]-bfo, T1[0]+nz, T1[1]);
  line(T1[0]+nz, T1[1], R1[0]+nz, R1[1]);
  line(R1[0]+nz, R1[1], W[0]+nz, W[1]+nz);
  line(Hp[0]-nz, Hp[1]+nz, Sch[0]-nz, Sch[1]+nz);
  line(HAE[0]-nz+1, HAE[1], HAE[0]-nz-1, HAE[1]);
  line(HAE[0]-nz, HAE[1]+1, HAE[0]-nz, HAE[1]-1);
  line(S[0]-nz, S[1]+nz, TAbs2[0]-nz, TAbs2[1]);
  line(TAbh1[0], TAbh1[1], TAbh3[0], TAbh3[1]);
  line(TAbh2[0], TAbh2[1], TAbh3[0], TAbh3[1]);
  line(TAbh1[0], TAbh1[1], TAbh4[0], TAbh4[1]);
  line(TAbh2[0], TAbh2[1], TAbh4[0], TAbh4[1]);
  line(Abh2[0], Abh2[1], Abh1[0], Abh1[1]+nz);
  line(Abh2[0], Abh2[1], Abh3[0]-nz, Abh3[1]);
  line(Abh2[0], Abh2[1], Abh4[0]-nz, Abh4[1]);
  popMatrix();
  
  dxf.setLayer(2);

  //sleeve
  basic_sleeve(110, 100, nz, nzS, Alg, Asw, Schv, Bp, Sch, Rp, S, VAE, HAE);
  
  endRaw();
  log.append("DONE \n");
}
