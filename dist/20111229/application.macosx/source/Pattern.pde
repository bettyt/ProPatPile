import processing.dxf.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

  // this table is used to store all user parameters
  // in the form: name=value
  static HashMap params = new HashMap();
  static JTextArea log = new JTextArea(5,20);

  int a;
  int b;
  int Gr;                                                                         //Konfektionsgr.
  float Tw, Hw, Kw, Fw, Sl, Sh, Sauh;                                             //Unterteil
  float Ow, Rl, Vl, ABsp, Bt, Ht, Rh, Rb, Ad, Bb, Hs, VAb, Lg;                    //Oberteil
  float ral, rab, Bft, talh, talv, tabh, tabv, Bfh, bfo, Abv, Alv, Abh, Alh;      //Abnäher, Bewegungsfreiheit
  float Alg, Asw, Schb;                                                           //Aermel
  float nz, nzS;                                                                  //Nahtzugaben
  float[] Konfektion;


// here we overwrite PApplet's main entry point (for application mode)
// we're parsing all commandline arguments and copy only the relevant ones
static public void main(String args[]) {
  createAndShowGUI.creating();
}
  

void setup() {
  a = 140;
  b = 200;
  size(10*a,10*b, P3D);
  background(255);
  noLoop();
}

void draw(){

//Eingabevariablen//
  //Unterteil
  Tw = floatParam("waist width");            //taillenumfang
  Hw = floatParam("hip width");              //Hueftumfang
  Kw = floatParam("knee width");             //Knieumfang
  Fw = floatParam("foot width");             //fussweite
  Sl = floatParam("side length");            //Seitenlaenge(von taille bis boden)
  Sh = floatParam("sitting height");         //Sitzhoehe (von taille bis sitzflaeche, sitzend)
  Sauh = floatParam("feet hem height");;     //saumhöhe, optional 
  //Oberteil//
  Gr = (int)floatParam("confection size");   //Konfektiosgroesse, optional
  Ow = floatParam("bust size");              //Oberweite
  Rl = floatParam("back length");            //Rueckenlaenge		#ab herausstehenden nackenwirbel bis taille
  Vl = floatParam("front length");           //Vorderlaenge		#vom halspunkt - dort wo schulternaht idealerweise hinlaeuft bis taille
  ABsp = floatParam("nipple distance");      //Brustspitzenabstand
  Bt = floatParam("bust depth");             //Brusttiefe		#vom halspunkt bis nippel
  Ht = floatParam("hip depth");              //Huefttiefe		#vom halspunkt bis Huefte (Rl +20 bis 22)
  Rh = 1.0/10.0 *Ow + 14.0;	             //Rückenhöhe, opional		#rueckenhoehe:von halswirbel bis oberweite #alternativ 1/8 der koerpergroesse
  Rb = 1.0/8.0 *Ow +6.0;                     //Rueckenbreite
  Ad = 1.0/8.0 *Ow;		             //Armlochdurchmesser, optional
  Bb = 1.0/4.0 *Ow - 2.5;
  Konfektion = Konf(Gr, Ow);
  Hs = Konfektion[0];
  VAb = Konfektion[1];
  Lg = floatParam("dress length");           //bemerkung: nicht fuer Overall benötigt
  //Aermel//
  Alg = floatParam("arm length");            //Aermellaenge, von schulterknochen bis zur gewuenschten laenge
  Asw = floatParam("arm hem weight");        //Aermelsaumweite, so dass hand noch durchpasst
  //Schb = 13;                               //Schulterbreite, Schulterknochen bis halsansatz, könnte zur Kontrolle verwendet werden
  //Schulterabnaeher//
  ral = floatParam("shoulder dart length");       //Rueckenabnaeherlaenge
  rab = floatParam("shoulder dart width");        //Rueckenabnaeherbreite
  //Taillenabnaeher// 
  talh = floatParam("waist dart length (back)");        //Gesamtlaenge des hinteren Taillenabnaehers
  talv = floatParam("waist dart length (front)");       //Gesamtlaenge des vorderen Taillenabnaehers
  tabh = floatParam("waist dart width (back)");         //Breite des hinteren taillenabnaehers
  tabv = floatParam("waist dart width (front)");          //Breite des vorderen taillenabnaehers
  //Hüftabnäher (fuer Hosen/Röcke)//
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
  dress_val.put("Lg", Lg);    //bemerkung: nicht fuer Overall benötigt
  
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
