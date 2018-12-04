/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchmethods;

/**
 *
 * @author Tania V
 */
public class Nodo {
   
    private int fila,columna,g,h,f,x,y;
    private boolean bloqueado;
    private Nodo padre;
    
    public Nodo(int fila, int columna){
        super();
        this.fila = fila;
        this.columna = columna;
    }
    

     public void heuristica(Nodo nodoFinal){
        this.h = Math.abs(nodoFinal.getfila()-getfila()) + Math.abs(nodoFinal.getcolumna()- getcolumna());
    }
     
    public void setDatosNodo(Nodo currNodo, int costo){
        int g = currNodo.getG() + costo;
        setPadre(currNodo);
        setG(g);
        CostoFinal();
    }
    
    public boolean checarMejorCamino(Nodo currNodo, int costo){
        int ng = currNodo.getG()+costo;
        if(ng < getG()){
            setDatosNodo(currNodo,costo);
            return true;
        }
        return false;
    }
    
    private void CostoFinal(){
        int CostoFinal = getG()+getH();
        setF(CostoFinal);
    }
    @Override
        public boolean equals(Object arg0){
            Nodo otro = (Nodo) arg0;
            return this.getfila() == otro.getfila() && this.getcolumna()==otro.getcolumna();
        }
        
    @Override
    public String toString(){
        return "[x =" + fila +",y ="+columna+"] f = "+f+" g = "+g+" h = "+h;
    }
       
    
    public int getH(){
        return h;
    }
    public void setH(int h){
        this.h = h;
    }
    
    
    public int getG(){
        return g;
    }
    public void setG(int g){
        this.g = g;
    }
    
    public int getF(){
        return f;
    }
    public void setF(int f){
        this.f = f;
    }
    
    
    public Nodo getPadre(){
        return padre;
    }
    public void setPadre(Nodo padre){
        this.padre = padre;
    }
   
    
    public boolean EsObstaculo(){
        return bloqueado;
    }
     public void setObstaculo(boolean bloqueado){
        this.bloqueado = bloqueado;
    }
     
     
    public int getfila(){
        return fila;
    }
    public void setfila(int fila){
        this.fila = fila;
    }
    
    
    
    public int getcolumna(){
        
        return columna;
    }
    public void setcolumna(int columna){
        this.columna = columna;
    }
     @Override
    public int hashCode(){
        return this.getfila()+this.getcolumna()+31;
    }

    
   
    
}
