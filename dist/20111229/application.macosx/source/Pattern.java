import processing.core.*; 
import processing.xml.*; 

import processing.dxf.*; 
import javax.swing.*; 
import java.io.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.util.Scanner; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Pattern extends PApplet {








  // this table is used to store all user parameters
  // in the form: name=value
  static HashMap params = new HashMap();
  static JTextArea log = new JTextArea(5,20);

  int a;
  int b;
  int Gr;                                                                         //Konfektionsgr.
  float Tw, Hw, Kw, Fw, Sl, Sh, Sauh;                                             //Unterteil
  float Ow, Rl, Vl, ABsp, Bt, Ht, Rh, Rb, Ad, Bb, Hs, VAb, Lg;                    //Oberteil
  float ral, rab, Bft, talh, talv, tabh, tabv, Bfh, bfo, Abv, Alv, Abh, Alh;      //Abn\u00e4her, Bewegungsfreiheit
  float Alg, Asw, Schb;                                                           //Aermel
  float nz, nzS;                                                                  //Nahtzugaben
  float[] Konfektion;


// here we overwrite PApplet's main entry point (for application mode)
// we're parsing all commandline arguments and copy only the relevant ones
static public void main(String args[]) {
  createAndShowGUI.creating();
}
  

public void setup() {
  a = 140;
  b = 200;
  size(10*a,10*b, P3D);
  background(255);
  noLoop();
}

public void draw(){

//Eingabevariablen//
  //Unterteil
  Tw = floatParam("waist width");            //taillenumfang
  Hw = floatParam("hip width");              //Hueftumfang
  Kw = floatParam("knee width");             //Knieumfang
  Fw = floatParam("foot width");             //fussweite
  Sl = floatParam("side length");            //Seitenlaenge(von taille bis boden)
  Sh = floatParam("sitting height");         //Sitzhoehe (von taille bis sitzflaeche, sitzend)
  Sauh = floatParam("feet hem height");;     //saumh\u00f6he, optional 
  //Oberteil//
  Gr = (int)floatParam("confection size");   //Konfektiosgroesse, optional
  Ow = floatParam("bust size");              //Oberweite
  Rl = floatParam("back length");            //Rueckenlaenge		#ab herausstehenden nackenwirbel bis taille
  Vl = floatParam("front length");           //Vorderlaenge		#vom halspunkt - dort wo schulternaht idealerweise hinlaeuft bis taille
  ABsp = floatParam("nipple distance");      //Brustspitzenabstand
  Bt = floatParam("bust depth");             //Brusttiefe		#vom halspunkt bis nippel
  Ht = floatParam("hip depth");              //Huefttiefe		#vom halspunkt bis Huefte (Rl +20 bis 22)
  Rh = 1.0f/10.0f *Ow + 14.0f;	             //R\u00fcckenh\u00f6he, opional		#rueckenhoehe:von halswirbel bis oberweite #alternativ 1/8 der koerpergroesse
  Rb = 1.0f/8.0f *Ow +6.0f;                     //Rueckenbreite
  Ad = 1.0f/8.0f *Ow;		             //Armlochdurchmesser, optional
  Bb = 1.0f/4.0f *Ow - 2.5f;
  Konfektion = Konf(Gr, Ow);
  Hs = Konfektion[0];
  VAb = Konfektion[1];
  Lg = floatParam("dress length");           //bemerkung: nicht fuer Overall ben\u00f6tigt
  //Aermel//
  Alg = floatParam("arm length");            //Aermellaenge, von schulterknochen bis zur gewuenschten laenge
  Asw = floatParam("arm hem weight");        //Aermelsaumweite, so dass hand noch durchpasst
  //Schb = 13;                               //Schulterbreite, Schulterknochen bis halsansatz, k\u00f6nnte zur Kontrolle verwendet werden
  //Schulterabnaeher//
  ral = floatParam("shoulder dart length");       //Rueckenabnaeherlaenge
  rab = floatParam("shoulder dart width");        //Rueckenabnaeherbreite
  //Taillenabnaeher// 
  talh = floatParam("waist dart length (back)");        //Gesamtlaenge des hinteren Taillenabnaehers
  talv = floatParam("waist dart length (front)");       //Gesamtlaenge des vorderen Taillenabnaehers
  tabh = floatParam("waist dart width (back)");         //Breite des hinteren taillenabnaehers
  tabv = floatParam("waist dart width (front)");          //Breite des vorderen taillenabnaehers
  //H\u00fcftabn\u00e4her (fuer Hosen/R\u00f6cke)//
  Abv = floatParam("hip dart width (front)");
  Alv = floatParam("hip dart length (front)");
  Abh = floatParam("hip dart width (back)");
  Alh = floatParam("hip dart length (back)");
  //Bewgeungsfreiheiten//
  Bft = floatParam("waist");                             //Taille
  Bfh = floatParam("hip");                               //Huefte
  bfo = floatParam("vertical (for overalls)");          //Bewegungsfreiheit overall
  //Nahtzugaben//
  nz = floatParam("seam");
  nzS = floatParam("edges");
 
  //put all variables needed for the basic dress sketch in a hashmap//  
  HashMap dress_val = new HashMap();
  dress_val.put("VAb", VAb);
  dress_val.put("Hs", Hs);
  dress_val.put("Ow", Ow);
  dress_val.put("Tw", Tw);
  dress_val.put("Hw", Hw);
  dress_val.put("Rl", Rl);
  dress_val.put("Vl", Vl);
  dress_val.put("ABsp", ABsp);
  dress_val.put("Bt", Bt);
  dress_val.put("Ht", Ht);
  dress_val.put("Rh", Rh);
  dress_val.put("Rb", Rb);
  dress_val.put("Ad", Ad);
  dress_val.put("Bb", Bb);
  dress_val.put("ral", ral);
  dress_val.put("rab", rab);  
  dress_val.put("Bft", Bft);
  dress_val.put("talh", talh);
  dress_val.put("talv", talv);
  dress_val.put("tabh", tabh);
  dress_val.put("tabv", tabv);
  dress_val.put("Bfh", Bfh);
  dress_val.put("Lg", Lg);    //bemerkung: nicht fuer Overall ben\u00f6tigt
  
  //get all points needed for the creation of the dress and the pants sketch//
  HashMap front = basic_pants(Tw, Hw, Kw, Fw, Sl, Sh, Sauh, Abv, Alv, Abh, Alh); 
  HashMap dress = basic_dress(dress_val);
  
  scale(10);
  Grid(a, b);
  
  String output = StringParam("output file name");
  //name of the output file
  if (output.indexOf(".")==-1) {
    output = output + ".dxf";
  }
  RawDXF dxf = (RawDXF) createGraphics(a, b, DXF, output);

  //Choose the right sketch to draw
  String sketch = StringParam("sketch_name");
  if (sketch == "Overall"){
    log.append("Writing sketch " + sketch + " to file " + output + "\n");
    Overall(front, dress, dxf);
  }
  else if (sketch.equals("Basic Dress")){
    log.append("Writing sketch " + sketch + " to file " + output + "\n");
    Dress(dress, dxf);
  }
  else if (sketch.equals("Basic Pants")){
    log.append("Writing sketch " + sketch + " to file " + output + "\n");
    Pants(front, dxf);
  }
  else {
    exit();
  }
  
}
  public void Dress(HashMap dress, RawDXF dxf){
  
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
public void Grid (int a, int b) {
  
  strokeWeight(1);
  stroke(200);
  for (int i = 0; i < a; i = i+1) {
    line(i, 0, i, b);
  }
  for (int x = 0; x < b; x = x+1) {
    line(0, x, a, x);
  }
}
public void HomePrint(int start_x, int start_y, int a, int b){  
  
  stroke(255, 0, 0);
  strokeWeight(0.8f);    //A4-Format - je 3cm fuer die Raender = 15*24;
  
   //Testsquare 10x10cm
  rect(start_x + 3, start_y + 5, 10, 10);
  
  for (int y = start_y; y < b; y = y + 24) {
    line(0, y, a, y);
    
    for (int x = start_x; x < a; x = x + 15){ 
      line(x, 0, x, b);
      
      if (x > start_x && y > start_y) {
        ellipse(x - 15/2.0f, y, 2, 2);
        ellipse (x, y - 24/2.0f, 2, 2);
      }
    }
  }
}
    
public float[] Konf(int Gr, float Ow) {
  
   if (Gr==32 || Gr==34){
    float Hs = 1.0f/12.0f * Ow;
    float VAb = 1.0f/20.0f *Ow -2.0f;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else if(Gr==36 || Gr==38){
    float Hs = 1.0f/12.0f *Ow -0.5f;		//Halsspiegelbreite
    float VAb = 1.0f/20.0f *Ow -1.0f;
    float[] konf = {Hs, VAb};
    return konf;
  }		//Vorderteil Abnaeher
  else if (Gr==40 || Gr==42){
    float Hs = 1.0f/12.0f *Ow -0.5f;
    float VAb = 1.0f/20.0f *Ow;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else if (Gr==44){
    float Hs = 1.0f/12.0f *Ow -0.5f;
    float VAb = 1.0f/20.0f *Ow +1.0f;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else if (Gr==46){
    float Hs = 1.0f/12.0f *Ow -1.0f;
    float VAb = 1.0f/20.0f *Ow +1.0f;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else if (Gr==48 || Gr==50){
    float Hs = 1.0f/12.0f *Ow -1.0f;
    float VAb = 1.0f/20.0f *Ow +2.0f;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else{
    log.append("invalid confection size (choose 32-50) \n");
    float[] konf = {0,0};
    return konf;
  }
}  
public void Overall(HashMap front, HashMap dress, RawDXF dxf){
 
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
public void Pants(HashMap front, RawDXF dxf){
  
    
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
        if ((labels[i]).equals(NameValue[0])){       //wenn die labels \u00fcbereinstimmen, setze neuen ParamWert
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


//Funktion fuer normierten Richtungsvekor einer Geraden durch zwei punkte
//A hat die groessere x-Koordinate
//Richtungsvektor zeigt in Richtung steigenden x
public float[] Richtungsvektor(float[] A, float[] B){
  float[] C = {A[0] - B[0], A[1] - B[1]};
  float n = sqrt(sq(C[0]) + sq(C[1]));
  float[] RV = {(1/n)*C[0], (1/n)*C[1]};
  return RV;
}

//Funktion fuer Schnittpunkt von zwei Geraden
public float[] Geradenschnittpunkt(float[] A1, float[] B1, float[] A2, float[] B2){
  float x = 0;
  float y = 0;
  if ((A1[0]-B1[0] != 0) && (A2[0]-B2[0] != 0)){
	float m1 = (A1[1]-B1[1])/(A1[0]-B1[0]);
	float n1 = A1[1] -m1*A1[0];	
	float m2 = (A2[1]-B2[1])/(A2[0]-B2[0]);
	float n2 = A2[1] -m2*A2[0];
	x = (n2-n1)/(m1-m2);
	y = m1*x + n1;
  }
  else if ((A1[0]-B1[0] == 0) && (A2[0]-B2[0] != 0)){		//Fall: eine senkrechte Gerade
	x = A1[0];
	float n2 = A2[1] -(A2[1]-B2[1])/(A2[0]-B2[0])*A2[0];
	float m2 = (A2[1]-B2[1])/(A2[0]-B2[0]);
	y = m2*x + n2;
  }
  else if ((A2[0]-B2[0] == 0) && (A1[0]-B1[0] != 0)){
        x = A2[0];
	float n1 = A1[1] -(A1[1]-B1[1])/(A1[0]-B1[0])*A1[0];
	float m1 = (A1[1]-B1[1])/(A1[0]-B1[0]);
	y = (m1*x + n1);
  }
  float[] SP = {x,y};
  return SP;
}

//Funktion zur Berechnung des Betrags eines Vektors, welcher durch zwei Punkte A, B gegeben wird
public float Betrag(float[] A, float[] B){
  float x = A[0]-B[0];
  float y = A[1]-B[1];
  return sqrt(sq(x)+sq(y));
}
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
/*function calculates all points needed for
the construction of the basic dress sketch.
The points are stored in a HashMap*/

public HashMap basic_dress(HashMap dress_val){
  HashMap dress = new HashMap();
  
  float Hs = (Float) dress_val.get("Hs");
  float VAb = (Float) dress_val.get("VAb");
  float Ow = (Float) dress_val.get("Ow");
  float Tw = (Float) dress_val.get("Tw");
  float Hw = (Float) dress_val.get("Hw");
  float Rl = (Float) dress_val.get("Rl");
  float Vl = (Float) dress_val.get("Vl");
  float ABsp = (Float) dress_val.get("ABsp");
  float Bt = (Float) dress_val.get("Bt");
  float Ht = (Float) dress_val.get("Ht");
  float Rh = (Float) dress_val.get("Rh");
  float Rb = (Float) dress_val.get("Rb");
  float Ad = (Float) dress_val.get("Ad");
  float Bb = (Float) dress_val.get("Bb");
  float ral = (Float) dress_val.get("ral");
  float rab = (Float) dress_val.get("rab");
  float talv = (Float) dress_val.get("talv");
  float talh = (Float) dress_val.get("talh");
  float tabv = (Float) dress_val.get("tabv");
  float tabh = (Float) dress_val.get("tabh");
  float Bfh = (Float) dress_val.get("Bfh");
  float Bft = (Float) dress_val.get("Bft");
  float Lg = (Float) dress_val.get("Lg");                //bemerkung: wird fuer overalls nicht ben\u00f6tigt (nfo)
  
  float[] W = {0, Ht};
  float[] R = {0, Ht-Rh};				//Rueckenlinie hinterseite
  float[] T = {0, Ht-Rl};				//Taillenlinie
  float[] H = {0, 0};				//Hueftlinie
  float[] L = {0, Ht-Lg}; //nfo
  
//Hintere Mitte(Naht)
  float[] T1 = {-2, T[1]};			//T 2cm nach links einstellen
  float[] H1 = {-2, H[1]};
  float[] L1 = {-2, L[1]}; //nfo			//H 2cm nach li ks einstellen
  float[] Rx = {R[0]-1, R[1]};
  float[] R1 = Geradenschnittpunkt(W, T1, R, Rx);
  float[] Rp = {R1[0]-Rb, R1[1]};
  float[] S = {Rp[0] - (0.5f*Ad + 1), Rp[1]};		//Seite, unter den achseln
  float[] Bp = {S[0] - (0.5f*Ad - 1), S[1]};
  float[] B = {Bp[0] - Bb, Bp[1]};				//Brustlinie vorderseite
  float[] Bsp = {B[0] + ABsp/2.0f, B[1]};		//optional: Absp fuer (1/10 Ow +1)
  float[] T2 = {Bsp[0], T[1]};				//Mitte der vorderen Abnaht
  float[] T3 = {B[0],T[1]};
  float[] H2 = {B[0], H[1]};
  float[] V = {T2[0], T2[1]+(Vl-1)};			//Halspunkt am Vorderteil
  float[] VT1 = {B[0], V[1]};
  float[] VT2 = {Bp[0], V[1]};
  float[] Bh = {V[0], V[1]-Bt};				//Brusthoehe
//Rueckenteil//
  float[] Hp = {W[0]-Hs, W[1]+2};				//Halspunkt: Hp nach W Rundung fuer Halsloch
  float[] Wp = {Rp[0], W[1]};
  float[] H1h = {Wp[0], Wp[1] -1};				//1. Hilfspunkt, hinten
  float[] Sch = {H1h[0]-2.0f*Richtungsvektor(Hp, H1h)[0], H1h[1]-2.0f*Richtungsvektor(Hp, H1h)[1]};	//Schulterpunkt
//Rueckenabnaeher
  float[] Abh1 = {Hp[0]-ral*Richtungsvektor(Hp, H1h)[0],Hp[1]-ral*Richtungsvektor(Hp, H1h)[1]};      //Abnaeherpunkt
  float[] Abh2 = {Abh1[0], Rp[1]+0.5f*(Wp[1]-Rp[1])};
  float[] Abh3 = {Rp[0], Abh2[1]+rab/2.0f};
  float[] Abh4 = {Rp[0], Abh2[1]-rab/2.0f};
//hinteres Armloch
  float[] HAE = {Rp[0]-1.5f, Rp[1]+0.25f*Rh};		                //Hinterer Aermeleinsatzpunkt  //hinteres Armloch ueber Sch,HAE,S einzeichnen
  //println(HAE);
  
//Vorderteil//
//Halsloch
  float[] Vt1 = {VT1[0] +VAb, VT1[1]};
  float[] Hsv = {Vt1[0] +Hs, Vt1[1]};
  float[] RV = Richtungsvektor(B,Vt1);		//richungsvektor zu Vt1-B in pos. x-Richtung
  float[] sRV = {RV[1], -RV[0]};		//dazu senkrechter Richtungsvektor in pos. x-Richtung
  float[] Vt2x = {Hsv[0]+sRV[0], Hsv[1]+sRV[1]};
  float[] Vt2 = Geradenschnittpunkt(B, Vt1, Hsv, Vt2x);
  float[] A = {Vt2[0] +(Hs+1)*RV[0], Vt2[1] +(Hs+1)*RV[1]};	//Halsloch ausschnitt  //ueber A-Hsv Halsloch einzeichnen
//vorderes Armloch
  float[] H1v = {Bp[0], H1h[1]-1};			//1. Hilfspunkt, vorne
  float sb = Betrag(Hp, Sch);			        //Schulterbreite
  log.append("calculated shoulder width:" + sb + "\n");    //Vergleich mit gemessener Schulterbreite?
  float[] Schv = {Hsv[0]-sb*Richtungsvektor(Hsv, H1v)[0], Hsv[1]-sb*Richtungsvektor(Hsv, H1v)[1]};      //vorderer Schulterpunkt
  float[] VAE = {Bp[0], Bp[1] +3};			//vorderer Aermeleinsatzpunkt  //Armloch ueber Schv-VAE-S einzeichnen
//Taillenabnaeher//
  float tab = Betrag(T1,T3)-(0.5f*Tw+Bft);			//Betrag des Taillenabnaehers
//hinten
  float[] TAbh = {T1[0]-7, T1[1]};
  float[] TAbh1 = {TAbh[0]-tabh/2.0f, TAbh[1]};			//rechter
  float[] TAbh2 = {TAbh[0]+tabh/2.0f, TAbh[1]};			//linker
  float[] TAbh3 = {TAbh[0], TAbh[1]+(0.5f*talh+1)};			//oberer
  float[] TAbh4 = {TAbh[0], TAbh[1]-(0.5f*talh-1)};			//unterer Begrenzungspunkt
//vorne
  float[] TAbv1 = {T2[0]+tabv/2.0f, T2[1]};
  float[] TAbv2 = {T2[0]-tabv/2.0f, T2[1]};
  float[] TAbv3 = {T2[0], Bh[1]-3};			//beginnt zwei cm unter der Brust
  float[] TAbv4 = {T2[0], TAbv3[1]-talv};			//sollte enden unterhalb der Taillenlinie
//an den Seitennaehten
  float tabs = tab-tabh-tabv;
  float[] TAbs1 = {S[0]-0.5f*tabs, T[1]+1};		//linker
  float[] TAbs2 = {S[0]+0.5f*tabs, T[1]+1};		//rechter begrenzungspunkt, rund nach unten bis zur Taillenlinie einzeichnen = neue taillenlinie
//Brustabnaeher
  float[] BAb;
  if (Bh[1] >= S[1]){
    BAb = S;
  }
  else{  
    BAb = Geradenschnittpunkt(B, Bh, S, TAbs1);	        //Linie fuer den Brustabnaeher: B-Bh-BAb
  }
  //println("BAb " + BAb[0] + ", " + BAb[1]);
  
  //Berechnung der Hueftweite
  float Fehlbetrag = Betrag(H1,H2)-(0.5f*Hw + Bfh);
  //println("Fehlbetrag " + Fehlbetrag);
  float[] Hh = {S[0]+0.5f*Fehlbetrag, H[1]};			//neue Heuftlinie fuer hinten
  float[] Hv = {S[0]-0.5f*Fehlbetrag, H[1]};			//neue hueftlinie fuer vorne
  float[] Lh = {S[0]+0.5f*Fehlbetrag, L[1]};
  float[] Lv = {S[0]-0.5f*Fehlbetrag, L[1]};
  
  dress.put("H1", H1);
  dress.put("T1", T1);
  dress.put("R1", R1);
  dress.put("W", W);
  dress.put("Hp", Hp);
  dress.put("Sch", Sch);
  dress.put("S", S);
  dress.put("VAE", VAE);
  dress.put("Schv", Schv);
  dress.put("Hsv", Hsv);
  dress.put("A", A);
  dress.put("B", B);
  dress.put("T3", T3);
  dress.put("H2", H2);
  dress.put("Hh", Hh);
  dress.put("Hv", Hv);
  dress.put("TAbs1", TAbs1);
  dress.put("TAbs2", TAbs2);
  dress.put("TAbh1", TAbh1);
  dress.put("TAbh2", TAbh2);
  dress.put("TAbh3", TAbh3);
  dress.put("TAbh4", TAbh4);
  dress.put("BAb", BAb);
  dress.put("TAbv1", TAbv1);
  dress.put("TAbv2", TAbv2);
  dress.put("TAbv3", TAbv3);
  dress.put("TAbv4", TAbv4);
  dress.put("Abh1", Abh1);
  dress.put("Abh2", Abh2);
  dress.put("Abh3", Abh3);
  dress.put("Abh4", Abh4);
  dress.put("HAE", HAE);
  dress.put("Rp", Rp);
  dress.put("Bp", Bp);
  dress.put("Lh", Lh);  //nfo
  dress.put("Lv", Lv);  //nfo
  dress.put("L1", L1);  //nfo
  
  return dress;
}

public HashMap basic_pants(float Tw, float Hw, float Kw, float Fw, float Sl, float Sh,float Sauh, float Abv, float Alv, float Abh, float Alh){
  float[] T = {0, Sl};
  float[] S = {0, Sauh};
  float[] Scha = {0, Sl-Sh};
  float[] A = {0, Sl -1};
  float[] H = {0,Sl-Sh+8};
  float[] K = {0, 0.5f*H[1]};
  
  //VORDERHOSE
//A-Linie
  float[] C = {0.25f*Hw, A[1]};
  float[] Vm = {C[0] - 1, A[1]};			//vorderer Mittelpunkt
//H-Linie
  float[] B = {0.25f*Hw, H[1]};
  float[] E = {(0.25f+1/20.0f)*Hw +1.5f, H[1]};
  float[] F = {0.5f*E[0], H[1]};			//Fadenlauf, Buegelfalte
//Scha-Linie
  float[] D = {0.25f*Hw, Scha[1]};		
//K-Linie
  float[] Kp = {F[0], K[1]};				//Kniepunkt
  float[] Ka = {Kp[0]-(0.25f*Kw +1), K[1]};
  float[] Kb = {Kp[0]+(0.25f*Kw +1), K[1]};
//S-Linie
  float[] Fp = {F[0], S[1]};
  float[] Fa = {Fp[0] -(0.25f*Fw +1), S[1]};
  float[] Fb = {Fp[0] +(0.25f*Fw +1), S[1]};
  float[] Sch = Geradenschnittpunkt(Scha, D, E, Kb);
  
//HINTERHOSE
//A-Linie
  float[] Vmh = {Vm[0]-4, A[1]};		//vordere Mitte hinten, wird mit Ba verbunden
//H-Linie
  float[] Ba = {B[0]-1, H[1]};
  float[] Ha = {Ba[0] - (0.25f*Hw +1.5f), H[1]};
  float[] HAh = {0.5f*(Ba[0]+Ha[0]), H[1]};		//Abnaeherhilfspunkt (von hieraus senkrecht bis auf Hm-STa-Linie verlaengert
//Scha-Linie
  float[] G = {Sch[0]+5, Scha[1]};
  float[] Ga = {G[0], G[1]-1};		//fuer Faltenfreiensitz, der cm muss vor dem naehen wieder ausgebuegelt werden	
//K-Linie
  float[] Kc = {Kp[0]-(0.25f*Kw +1), K[1]};
  float[] Kd = {Kp[0]+(0.25f*Kw +1), K[1]};
//Beinrundung
  float[] Br = {0.5f*(Ga[0]+Kd[0]-1), 0.5f*(Ga[1]+Kd[1])};	//Hifspunkt zum einzeichnen der beinrundung
//F-Linie
  float[] Fc = {Fp[0]-(0.25f*Fw +1), S[1]};
  float[] Fd = {Fp[0]+(0.25f*Fw +1), S[1]};

  ////////////////wird nicht f\u00fcr Overalls ben\u00f6tigt/////////////////
 //zur Vorderhose
//Berechnung von ST auf schraeger Linie Vm-T
  float a = T[1]-A[1];					//Dreieck a,b,c
  float b = Vm[0];
  float c1 = 0.25f*Tw + Abv; 
  float c = sqrt(sq(a) + sq(b));		//Pythagoras
  float h = (a*c1)/c;					//Strahlensatz
  float b1 = sqrt(sq(c1) - sq(h));	//Pythagoras
  float b2 = b - b1;
  float[] ST = {b2, A[1]+h}; 
//Abnaeher
  float[] Fh = {F[0],F[1]+1};
  float[] v = Geradenschnittpunkt(F, Fh, Vm, T);
  float[] HAv = {v[0], v[1] -Alv};			//abnaeherspitze
  float[] TAv1 = {v[0] + 0.5f*Abv*(Richtungsvektor(Vm, T)[0]), v[1] + 0.5f*Abv*(Richtungsvektor(Vm, T)[1])};
  float[] TAv2 = {v[0] - 0.5f*Abv*(Richtungsvektor(Vm, T)[0]), v[1] - 0.5f*Abv*(Richtungsvektor(Vm, T)[1])};
//zur Hinterhose
//T-Linie
  float[] R = Richtungsvektor(Vmh, Ba);
  float[] Hm = {Vmh[0]+4*R[0], Vmh[1]+4*R[1]};	//verlaengerung der ba-Vmh Linie 3-4cm nach oben
  float x = Hm[0] - sqrt(sq(0.25f*Tw + Abh) - sq(Hm[1]-ST[1]));
  float[] STa = {x, ST[1]};				//von Hm (0.25*Tw + Abh)
//Abnaeherhilfspunkte
  float[] HAhx = {HAh[0], HAh[1]+1};
  float[] TAh1 = Geradenschnittpunkt(HAh, HAhx , Hm, STa);
  float[] TAh2 = {TAh1[0] - Richtungsvektor(Hm, STa)[0],TAh1[1] - Abh*(Richtungsvektor(Hm, STa)[1])};
  

  HashMap pants = new HashMap();
  pants.put("Fa", Fa);
  pants.put("Fb", Fb);
  pants.put("Kb", Kb);
  pants.put("Sch", Sch);
  pants.put("B", B);
  pants.put("H", H);
  pants.put("Ka", Ka);  
  pants.put("Fc", Fc);
  pants.put("Fd", Fd);
  pants.put("Kd", Kd);
  pants.put("Br", Br);
  pants.put("Ga", Ga);
  pants.put("Ba", Ba);
  pants.put("Ha", Ha);
  pants.put("Kc", Kc);
  
  ///nicht gebraucht f\u00fcr Overalls
  pants.put("Vm", Vm);  
  pants.put("ST", ST);  
  pants.put("HAv", HAv);
  pants.put("TAv1", TAv1);
  pants.put("TAv2", TAv2);
  pants.put("Hm", Hm);
  pants.put("STa", STa);
  pants.put("HAh", HAh);
  pants.put("TAh1", TAh1);
  pants.put("TAh2", TAh2);
  ///
  return pants;
}  
public void basic_sleeve(int px, int py, float nz, float nzS, float Alg, float Asw, float[] Schv, float[] Bp, float[] Sch, float[] Rp, float[] S, float[] VAE, float[] HAE){
//Aermelkonstruktion
  float ah = Betrag(Schv, Bp) + Betrag(Sch, Rp);		//Armlochhoehe vorne und hinten
  float ad = Betrag(Bp, Rp);
  //print("ad"); println(ad);
  
//Armkugelkonstruktion
  float[] A = {0,0};
  float[] B = {A[0]+ad+5, A[1]};
  float[] C = {A[0], A[1]-(0.5f*ah-3)};
  float[] D = {A[0], A[1]-Alg};
  float[] E = {C[0], C[1]-(0.5f*Betrag(C,D)-1)};		//Ellenbogenlinie
  float[] aVAE = {C[0], C[1] +3};				//vorderer Aermeleinsatzpunkt am aermel
  float[] F = {aVAE[0]-(S[0]-VAE[0]), aVAE[1]-(VAE[1]-S[1])};  //von aVAE nach F: Rundung vom Vorderteil VAE - S --> mit einem viertelkreis annaehern!
  float[] G = {F[0] +2*ad+8, F[1]};
//Hilfspunkte fuer armkugel
  float[] B1 = {B[0], B[1]-3};
  float[] H1x = Richtungsvektor(B1,G);
  float[] H1={G[0] + 1.0f/3.0f*Betrag(B1,G)*H1x[0]-0.5f*H1x[1], G[1] + 1.0f/3.0f*Betrag(B1,G)*H1x[1]+0.5f*H1x[0]};
  
  float[] aSch = {A[0]+0.5f*Betrag(A,B)+2.5f, A[1]};
  float[] H2x = Richtungsvektor(aSch, B1);
  float[] H2 = {aSch[0] - 0.5f*Betrag(aSch, B1)*H2x[0]+0.5f*H2x[1], aSch[1] - 0.5f*Betrag(aSch, B1)*H2x[1]-0.5f*H2x[0]};
  float[] A1 = {A[0]+0.5f*Betrag(A, aSch), A[1]};
  float[] A2x = Richtungsvektor(A1,aVAE);
  float[] A2 = {A1[0]-0.5f*Betrag(A1,aVAE)*A2x[0], A1[1]-0.5f*Betrag(A1,aVAE)*A2x[1]};
  float[] H3x = Richtungsvektor(aSch,A2);
  float[] H3 = {A2[0]+0.5f*(aSch[0]-A2[0])-H3x[1], A2[1]+0.5f*(aSch[1]-A2[1])+H3x[0]};

  float[] aHAE = {G[0]-(HAE[0]-S[0]), G[1]+(HAE[1]-S[1])};         //G-aHAE = Strecke S-HAE vom R\u00fcckenteil
  //float b = PI*(HAE[0]-S[0])/2.0;      //1/4 Kreisumfang
  //float h= PI*(HAE[1]-S[1])/2.0;
  //float[] aHAE = {G[0]-b*Richtungsvektor(G,B1)[0], G[1]-b*Richtungsvektor(G,B1)[1]};

  float[] Ea = {F[0]+3, E[1]};
  float[] Eb = {G[0]-3, E[1]};
  float[] Hx = {D[0]+1, D[1]};
  float[] H = Geradenschnittpunkt(F, Ea, D, Hx);
  float[] I = Geradenschnittpunkt(G, Eb, D, Hx);
  float[] J = {H[0]+Asw, H[1]};
  float[] K = {J[0], J[1]-2};

  float[] aRV = Richtungsvektor(Eb,I);
  float[] asRV = {aRV[1], -aRV[0]};			//Lot faellen mit senkrechtem RV zu Eb
  float[] Kxx = {K[0]+asRV[0],K[1]+asRV[1]};
  float[] Kx = Geradenschnittpunkt(K,Kxx, I,Eb);
  float c = sqrt(sq(Betrag(Ea,H)) - sq(Betrag(K,Kx)));	//Pythagor
  float[] M = {Kx[0]+c*aRV[0], Kx[1]+c*aRV[1]};
//Abnaeher
  float[] aAb = {B[0]-1.5f, E[1]};
  float[] L = {H[0]+2.0f/3.0f*Asw*Richtungsvektor(K,H)[0], H[1]+2.0f/3.0f*Asw*Richtungsvektor(K,H)[1]};
  
  pushMatrix();
  translate(px, py);
  line(L[0], L[1]-nzS, H[0]-nz, H[1]-nzS);
  line(H[0]-nz, H[1]-nzS, Ea[0]-nz, Ea[1]);
  line(Ea[0]-nz, Ea[1], F[0]-nz, F[1]+nz);
  line(aVAE[0]+1, aVAE[1]+nz, aVAE[0]-1, aVAE[1]+nz);
  line(aVAE[0], aVAE[1]+1+nz, aVAE[0], aVAE[1]-1+nz);
  line(A2[0]+1, A2[1]+nz, A2[0]-1, A2[1]+nz);
  line(A2[0], A2[1]+1+nz, A2[0], A2[1]-1+nz);  
  line(aSch[0]+1, aSch[1]+nz, aSch[0]-1, aSch[1]+nz);
  line(aSch[0], aSch[1]+1+nz, aSch[0], aSch[1]-1+nz);
  line(H3[0]+1, H3[1]+nz, H3[0]-1, H3[1]+nz);
  line(H3[0], H3[1]+1+nz, H3[0], H3[1]-1+nz);  
  line(H2[0]+1, H2[1]+nz, H2[0]-1, H2[1]+nz);
  line(H2[0], H2[1]+1+nz, H2[0], H2[1]-1+nz);
  line(B1[0], B1[1]+nz, B1[0]-1.0f/3.0f*Betrag(B1, G)*Richtungsvektor(B1,G)[0], B1[1]-1.0f/3.0f*Betrag(B1, G)*Richtungsvektor(B1,G)[1]+nz );
  stroke(255, 0, 0);
  line(H1[0]+1, H1[1]+nz, H1[0]-1, H1[1]+nz);
  line(H1[0], H1[1]+1+nz, H1[0], H1[1]-1+nz);
  stroke(0,0,0);
  line(aHAE[0]+1, aHAE[1]+nz, aHAE[0]-1, aHAE[1]+nz);
  line(aHAE[0], aHAE[1]+1+nz, aHAE[0], aHAE[1]-1+nz);
  line(G[0]+nz, G[1]+nz, Eb[0]+nz, Eb[1]);
  line(aAb[0], aAb[1], L[0], L[1]-nzS);
  line(aAb[0], aAb[1], Eb[0]+nz, Eb[1]);
  
  noFill();
  arc(F[0], aVAE[1]+1+nz, 2*(aVAE[0]+0.25f-F[0]), 2*(aVAE[1]+1-F[1]), 3.0f/2.0f*PI, 2.0f*PI);
  line(F[0], F[1]+nz, F[0]-nz, F[1]+nz);
  arc(G[0], aHAE[1]+nz, 2*(G[0]-aHAE[0]), 2*(aHAE[1]-G[1]), PI, 3.0f/2.0f*PI);
  line(G[0], G[1]+nz, G[0]+nz, G[1]+nz);
  
  /*beginShape();
  curveVertex(G[0]+nz, G[1]+nz);
  curveVertex(G[0]+nz, G[1]+nz);
  curveVertex(H1[0]-3*Richtungsvektor(B1,G)[0], H1[1]-3*Richtungsvektor(B1,G)[1]+nz);
  curveVertex(H1[0], H1[1]+nz);
  curveVertex(aHAE[0], aHAE[1]+nz);
  curveVertex(B1[0]-1.0/3.0*Betrag(B1, G)*Richtungsvektor(B1,G)[0], B1[1]-1.0/3.0*Betrag(B1, G)*Richtungsvektor(B1,G)[1]+nz);
  curveVertex(B1[0]-1.0/3.0*Betrag(B1, G)*Richtungsvektor(B1,G)[0], B1[1]-1.0/3.0*Betrag(B1, G)*Richtungsvektor(B1,G)[1]+nz);
  endShape();
  */
  popMatrix();
  
  PVector Ell = new PVector(Eb[0] - aAb[0], Eb[1] - aAb[1]);
  PVector Help = new PVector(M[0]-aAb[0] , M[1]-aAb[1]);
  //println(Ell); println(Help);
  float rot = PVector.angleBetween(Ell, Help);
  //print("rot"); println(rot);
  
  pushMatrix();
  translate(px+aAb[0], py+aAb[1]);
  rotate(rot);
  translate(-aAb[0], -aAb[1]);
  line(aAb[0], aAb[1], M[0]+nz, M[1]);
  line(M[0]+nz, M[1], K[0]+nz, K[1]-nzS);
  line(K[0]+nz, K[1]-nzS, L[0], L[1]-nzS);
  line(L[0], L[1]-nzS, aAb[0], aAb[1]);
  
  popMatrix();
  
}

//Kontrolle: 	F-aVAE-aSch-aHAE-G mit Rundungen nicht merh als 4cm weiter als aumf
//		F-aVAE-aSch soll nicht groesser als B-aHAE-aSch

 //  1. Declare an event handler class and specify that the class either implements an ActionListener interface or extends a class that implements an ActionListener interface. For example:

//classCreateAndShowGUI implements ActionListener { 

  //\u00fcber die bestehende Fkt. legen
  
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
    
    public static void creating() /*throws FileNotFoundException*/ {
      
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
      labels[14] = "feet hem height";  //fu\u00dfsaumh\u00f6he
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
    
   public static void addToPane(JPanel pane, int begin, int end){
        for (int i=begin; i<end+1 ;i++){
          pane.add(parameters[i]);
          pane.add(Box.createRigidArea(new Dimension(0,5)));
        }  
        
      }
   
   public static void fillPane(JPanel pane, String title, int begin, int end){
     
     pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
     addToPane(pane, begin, end);
     pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), title));
     
   }
    
}


public String StringParam(String id) {
  String value = String.valueOf(params.get(id));
  if(value == null) {
    println("Parameter '" + id + "' is undefined!");
    exit();
  }
  return value;
}

public float floatParam(String id) {
  String value = String.valueOf(params.get(id));
  if(value.equals("null")) {
    println("Parameter '" + id + "' is undefined!");
    exit();
  }
  return Float.parseFloat(value);
}
}
