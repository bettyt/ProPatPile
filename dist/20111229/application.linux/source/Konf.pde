float[] Konf(int Gr, float Ow) {
  
   if (Gr==32 || Gr==34){
    float Hs = 1.0/12.0 * Ow;
    float VAb = 1.0/20.0 *Ow -2.0;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else if(Gr==36 || Gr==38){
    float Hs = 1.0/12.0 *Ow -0.5;		//Halsspiegelbreite
    float VAb = 1.0/20.0 *Ow -1.0;
    float[] konf = {Hs, VAb};
    return konf;
  }		//Vorderteil Abnaeher
  else if (Gr==40 || Gr==42){
    float Hs = 1.0/12.0 *Ow -0.5;
    float VAb = 1.0/20.0 *Ow;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else if (Gr==44){
    float Hs = 1.0/12.0 *Ow -0.5;
    float VAb = 1.0/20.0 *Ow +1.0;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else if (Gr==46){
    float Hs = 1.0/12.0 *Ow -1.0;
    float VAb = 1.0/20.0 *Ow +1.0;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else if (Gr==48 || Gr==50){
    float Hs = 1.0/12.0 *Ow -1.0;
    float VAb = 1.0/20.0 *Ow +2.0;
    float[] konf = {Hs, VAb};
    return konf;
  }
  else{
    log.append("invalid confection size (choose 32-50) \n");
    float[] konf = {0,0};
    return konf;
  }
}  
