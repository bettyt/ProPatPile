//Funktion fuer normierten Richtungsvekor einer Geraden durch zwei punkte
//A hat die groessere x-Koordinate
//Richtungsvektor zeigt in Richtung steigenden x
float[] Richtungsvektor(float[] A, float[] B){
  float[] C = {A[0] - B[0], A[1] - B[1]};
  float n = sqrt(sq(C[0]) + sq(C[1]));
  float[] RV = {(1/n)*C[0], (1/n)*C[1]};
  return RV;
}

//Funktion fuer Schnittpunkt von zwei Geraden
float[] Geradenschnittpunkt(float[] A1, float[] B1, float[] A2, float[] B2){
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
float Betrag(float[] A, float[] B){
  float x = A[0]-B[0];
  float y = A[1]-B[1];
  return sqrt(sq(x)+sq(y));
}
