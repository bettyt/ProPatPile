void Pants(HashMap front, RawDXF dxf){
  
    
  float[] Fa = (float[]) front.get("Fa");
  float[] Fb = (float[]) front.get("Fb");
  float[] Kb = (float[]) front.get("Kb");
  float[] Sch = (float[]) front.get("Sch");  
  float[] B = (float[]) front.get("B");  
  float[] Vm = (float[]) front.get("Vm");  
  float[] ST = (float[]) front.get("ST");
  float[] H = (float[]) front.get("H");
  float[] Ka = (float[]) front.get("Ka");
  float[] HAv = (float[]) front.get("HAv");
  float[] TAv1 = (float[]) front.get("TAv1");
  float[] TAv2 = (float[]) front.get("TAv2");
  
  float[] Fc = (float[]) front.get("Fc");
  float[] Fd = (float[]) front.get("Fd");
  float[] Kd = (float[]) front.get("Kd");
  float[] Br = (float[]) front.get("Br");
  float[] Ga = (float[]) front.get("Ga");
  float[] Ba = (float[]) front.get("Ba");
  float[] Hm = (float[]) front.get("Hm");
  float[] STa = (float[]) front.get("STa");
  float[] Ha = (float[]) front.get("Ha");
  float[] Kc = (float[]) front.get("Kc");
  float[] HAh = (float[]) front.get("HAh");
  float[] TAh1 = (float[]) front.get("TAh1");
  float[] TAh2 = (float[]) front.get("TAh2");
  
//Zeichnung
  beginRaw(dxf); 
  dxf.setLayer(0);
  int start_x = 10 - 2;
  int start_y = 0;
  HomePrint(start_x, start_y, a, b);
  dxf.setLayer(1);
  stroke(0,0,0);
  strokeWeight(1);
  
  translate(10,0);
  //Vorderhose
  line(Fa[0], Fa[1], Fb[0], Fb[1]);
  line(Fb[0], Fb[1], Kb[0], Kb[1]);
  line(Kb[0], Kb[1], Sch[0], Sch[1]);
  line(B[0], B[1], Vm[0], Vm[1]);
  line(Vm[0], Vm[1], ST[0], ST[1]); 
  line(ST[0], ST[1], H[0], H[1]);
  line(H[0], H[1], Ka[0], Ka[1]);
  line(Ka[0], Ka[1], Fa[0], Fa[1]);
  //Abnaeher
  line(HAv[0], HAv[1], TAv1[0], TAv1[1]);
  line(HAv[0], HAv[1], TAv2[0], TAv2[1]);
  //Hinterhose
  pushMatrix();
  translate(50,0); 
  line(Fc[0], Fc[1], Fd[0], Fd[1]);
  line(Fd[0], Fd[1], Kd[0], Kd[1]);
  line(Kd[0], Kd[1], Br[0], Br[1]);
  line(Br[0], Br[1], Ga[0], Ga[1]);
  line(Sch[0]+1, Sch[1], Sch[0]-1, Sch[1]);
  line(Sch[0], Sch[1]+1, Sch[0], Sch[1]-1);
  line(Ba[0], Ba[1], Hm[0], Hm[1]);
  line(Hm[0], Hm[1], STa[0], STa[1]);
  line(STa[0], STa[1], Ha[0], Ha[1]);
  line(Ha[0], Ha[1], Kc[0], Kc[1]);
  line(Kc[0], Kc[1], Fc[0], Fc[1]);
  //Abnaeher
  line(HAh[0], HAh[1]+Alh, TAh1[0], TAh1[1]);
  line(HAh[0], HAh[1]+Alh, TAh2[0], TAh2[1]);
  popMatrix();
  
  endRaw();
  log.append("DONE");
  
}
