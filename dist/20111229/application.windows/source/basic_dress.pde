/*function calculates all points needed for
the construction of the basic dress sketch.
The points are stored in a HashMap*/

HashMap basic_dress(HashMap dress_val){
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
  float Lg = (Float) dress_val.get("Lg");                //bemerkung: wird fuer overalls nicht benötigt (nfo)
  
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
  float[] S = {Rp[0] - (0.5*Ad + 1), Rp[1]};		//Seite, unter den achseln
  float[] Bp = {S[0] - (0.5*Ad - 1), S[1]};
  float[] B = {Bp[0] - Bb, Bp[1]};				//Brustlinie vorderseite
  float[] Bsp = {B[0] + ABsp/2.0, B[1]};		//optional: Absp fuer (1/10 Ow +1)
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
  float[] Sch = {H1h[0]-2.0*Richtungsvektor(Hp, H1h)[0], H1h[1]-2.0*Richtungsvektor(Hp, H1h)[1]};	//Schulterpunkt
//Rueckenabnaeher
  float[] Abh1 = {Hp[0]-ral*Richtungsvektor(Hp, H1h)[0],Hp[1]-ral*Richtungsvektor(Hp, H1h)[1]};      //Abnaeherpunkt
  float[] Abh2 = {Abh1[0], Rp[1]+0.5*(Wp[1]-Rp[1])};
  float[] Abh3 = {Rp[0], Abh2[1]+rab/2.0};
  float[] Abh4 = {Rp[0], Abh2[1]-rab/2.0};
//hinteres Armloch
  float[] HAE = {Rp[0]-1.5, Rp[1]+0.25*Rh};		                //Hinterer Aermeleinsatzpunkt  //hinteres Armloch ueber Sch,HAE,S einzeichnen
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
  float tab = Betrag(T1,T3)-(0.5*Tw+Bft);			//Betrag des Taillenabnaehers
//hinten
  float[] TAbh = {T1[0]-7, T1[1]};
  float[] TAbh1 = {TAbh[0]-tabh/2.0, TAbh[1]};			//rechter
  float[] TAbh2 = {TAbh[0]+tabh/2.0, TAbh[1]};			//linker
  float[] TAbh3 = {TAbh[0], TAbh[1]+(0.5*talh+1)};			//oberer
  float[] TAbh4 = {TAbh[0], TAbh[1]-(0.5*talh-1)};			//unterer Begrenzungspunkt
//vorne
  float[] TAbv1 = {T2[0]+tabv/2.0, T2[1]};
  float[] TAbv2 = {T2[0]-tabv/2.0, T2[1]};
  float[] TAbv3 = {T2[0], Bh[1]-3};			//beginnt zwei cm unter der Brust
  float[] TAbv4 = {T2[0], TAbv3[1]-talv};			//sollte enden unterhalb der Taillenlinie
//an den Seitennaehten
  float tabs = tab-tabh-tabv;
  float[] TAbs1 = {S[0]-0.5*tabs, T[1]+1};		//linker
  float[] TAbs2 = {S[0]+0.5*tabs, T[1]+1};		//rechter begrenzungspunkt, rund nach unten bis zur Taillenlinie einzeichnen = neue taillenlinie
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
  float Fehlbetrag = Betrag(H1,H2)-(0.5*Hw + Bfh);
  //println("Fehlbetrag " + Fehlbetrag);
  float[] Hh = {S[0]+0.5*Fehlbetrag, H[1]};			//neue Heuftlinie fuer hinten
  float[] Hv = {S[0]-0.5*Fehlbetrag, H[1]};			//neue hueftlinie fuer vorne
  float[] Lh = {S[0]+0.5*Fehlbetrag, L[1]};
  float[] Lv = {S[0]-0.5*Fehlbetrag, L[1]};
  
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

