HashMap basic_pants(float Tw, float Hw, float Kw, float Fw, float Sl, float Sh,float Sauh, float Abv, float Alv, float Abh, float Alh){
  float[] T = {0, Sl};
  float[] S = {0, Sauh};
  float[] Scha = {0, Sl-Sh};
  float[] A = {0, Sl -1};
  float[] H = {0,Sl-Sh+8};
  float[] K = {0, 0.5*H[1]};
  
  //VORDERHOSE
//A-Linie
  float[] C = {0.25*Hw, A[1]};
  float[] Vm = {C[0] - 1, A[1]};			//vorderer Mittelpunkt
//H-Linie
  float[] B = {0.25*Hw, H[1]};
  float[] E = {(0.25+1/20.0)*Hw +1.5, H[1]};
  float[] F = {0.5*E[0], H[1]};			//Fadenlauf, Buegelfalte
//Scha-Linie
  float[] D = {0.25*Hw, Scha[1]};		
//K-Linie
  float[] Kp = {F[0], K[1]};				//Kniepunkt
  float[] Ka = {Kp[0]-(0.25*Kw +1), K[1]};
  float[] Kb = {Kp[0]+(0.25*Kw +1), K[1]};
//S-Linie
  float[] Fp = {F[0], S[1]};
  float[] Fa = {Fp[0] -(0.25*Fw +1), S[1]};
  float[] Fb = {Fp[0] +(0.25*Fw +1), S[1]};
  float[] Sch = Geradenschnittpunkt(Scha, D, E, Kb);
  
//HINTERHOSE
//A-Linie
  float[] Vmh = {Vm[0]-4, A[1]};		//vordere Mitte hinten, wird mit Ba verbunden
//H-Linie
  float[] Ba = {B[0]-1, H[1]};
  float[] Ha = {Ba[0] - (0.25*Hw +1.5), H[1]};
  float[] HAh = {0.5*(Ba[0]+Ha[0]), H[1]};		//Abnaeherhilfspunkt (von hieraus senkrecht bis auf Hm-STa-Linie verlaengert
//Scha-Linie
  float[] G = {Sch[0]+5, Scha[1]};
  float[] Ga = {G[0], G[1]-1};		//fuer Faltenfreiensitz, der cm muss vor dem naehen wieder ausgebuegelt werden	
//K-Linie
  float[] Kc = {Kp[0]-(0.25*Kw +1), K[1]};
  float[] Kd = {Kp[0]+(0.25*Kw +1), K[1]};
//Beinrundung
  float[] Br = {0.5*(Ga[0]+Kd[0]-1), 0.5*(Ga[1]+Kd[1])};	//Hifspunkt zum einzeichnen der beinrundung
//F-Linie
  float[] Fc = {Fp[0]-(0.25*Fw +1), S[1]};
  float[] Fd = {Fp[0]+(0.25*Fw +1), S[1]};

  ////////////////wird nicht für Overalls benötigt/////////////////
 //zur Vorderhose
//Berechnung von ST auf schraeger Linie Vm-T
  float a = T[1]-A[1];					//Dreieck a,b,c
  float b = Vm[0];
  float c1 = 0.25*Tw + Abv; 
  float c = sqrt(sq(a) + sq(b));		//Pythagoras
  float h = (a*c1)/c;					//Strahlensatz
  float b1 = sqrt(sq(c1) - sq(h));	//Pythagoras
  float b2 = b - b1;
  float[] ST = {b2, A[1]+h}; 
//Abnaeher
  float[] Fh = {F[0],F[1]+1};
  float[] v = Geradenschnittpunkt(F, Fh, Vm, T);
  float[] HAv = {v[0], v[1] -Alv};			//abnaeherspitze
  float[] TAv1 = {v[0] + 0.5*Abv*(Richtungsvektor(Vm, T)[0]), v[1] + 0.5*Abv*(Richtungsvektor(Vm, T)[1])};
  float[] TAv2 = {v[0] - 0.5*Abv*(Richtungsvektor(Vm, T)[0]), v[1] - 0.5*Abv*(Richtungsvektor(Vm, T)[1])};
//zur Hinterhose
//T-Linie
  float[] R = Richtungsvektor(Vmh, Ba);
  float[] Hm = {Vmh[0]+4*R[0], Vmh[1]+4*R[1]};	//verlaengerung der ba-Vmh Linie 3-4cm nach oben
  float x = Hm[0] - sqrt(sq(0.25*Tw + Abh) - sq(Hm[1]-ST[1]));
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
  
  ///nicht gebraucht für Overalls
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
