void basic_sleeve(int px, int py, float nz, float nzS, float Alg, float Asw, float[] Schv, float[] Bp, float[] Sch, float[] Rp, float[] S, float[] VAE, float[] HAE){
//Aermelkonstruktion
  float ah = Betrag(Schv, Bp) + Betrag(Sch, Rp);		//Armlochhoehe vorne und hinten
  float ad = Betrag(Bp, Rp);
  //print("ad"); println(ad);
  
//Armkugelkonstruktion
  float[] A = {0,0};
  float[] B = {A[0]+ad+5, A[1]};
  float[] C = {A[0], A[1]-(0.5*ah-3)};
  float[] D = {A[0], A[1]-Alg};
  float[] E = {C[0], C[1]-(0.5*Betrag(C,D)-1)};		//Ellenbogenlinie
  float[] aVAE = {C[0], C[1] +3};				//vorderer Aermeleinsatzpunkt am aermel
  float[] F = {aVAE[0]-(S[0]-VAE[0]), aVAE[1]-(VAE[1]-S[1])};  //von aVAE nach F: Rundung vom Vorderteil VAE - S --> mit einem viertelkreis annaehern!
  float[] G = {F[0] +2*ad+8, F[1]};
//Hilfspunkte fuer armkugel
  float[] B1 = {B[0], B[1]-3};
  float[] H1x = Richtungsvektor(B1,G);
  float[] H1={G[0] + 1.0/3.0*Betrag(B1,G)*H1x[0]-0.5*H1x[1], G[1] + 1.0/3.0*Betrag(B1,G)*H1x[1]+0.5*H1x[0]};
  
  float[] aSch = {A[0]+0.5*Betrag(A,B)+2.5, A[1]};
  float[] H2x = Richtungsvektor(aSch, B1);
  float[] H2 = {aSch[0] - 0.5*Betrag(aSch, B1)*H2x[0]+0.5*H2x[1], aSch[1] - 0.5*Betrag(aSch, B1)*H2x[1]-0.5*H2x[0]};
  float[] A1 = {A[0]+0.5*Betrag(A, aSch), A[1]};
  float[] A2x = Richtungsvektor(A1,aVAE);
  float[] A2 = {A1[0]-0.5*Betrag(A1,aVAE)*A2x[0], A1[1]-0.5*Betrag(A1,aVAE)*A2x[1]};
  float[] H3x = Richtungsvektor(aSch,A2);
  float[] H3 = {A2[0]+0.5*(aSch[0]-A2[0])-H3x[1], A2[1]+0.5*(aSch[1]-A2[1])+H3x[0]};

  float[] aHAE = {G[0]-(HAE[0]-S[0]), G[1]+(HAE[1]-S[1])};         //G-aHAE = Strecke S-HAE vom Rückenteil
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
  float[] aAb = {B[0]-1.5, E[1]};
  float[] L = {H[0]+2.0/3.0*Asw*Richtungsvektor(K,H)[0], H[1]+2.0/3.0*Asw*Richtungsvektor(K,H)[1]};
  
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
  line(B1[0], B1[1]+nz, B1[0]-1.0/3.0*Betrag(B1, G)*Richtungsvektor(B1,G)[0], B1[1]-1.0/3.0*Betrag(B1, G)*Richtungsvektor(B1,G)[1]+nz );
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
  arc(F[0], aVAE[1]+1+nz, 2*(aVAE[0]+0.25-F[0]), 2*(aVAE[1]+1-F[1]), 3.0/2.0*PI, 2.0*PI);
  line(F[0], F[1]+nz, F[0]-nz, F[1]+nz);
  arc(G[0], aHAE[1]+nz, 2*(G[0]-aHAE[0]), 2*(aHAE[1]-G[1]), PI, 3.0/2.0*PI);
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

