/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchmethods;
import java.util.*;
/**
 *
 * @author Tania V
 */
public class AStarSearch { 
    /**
     *          10 | 5 | 10
     *          5  | N | 5
     *          10 | 5 | 10
     */
    private static int UP_DOWN_costo = 5;// se evaluan como 5 los nodos que esten arriba o abajo del nodo actual
    private static int DIAG_costo = 10;// se evaluan en 10 los nodos que esten en las diagonales del nodo actual
    private int up_down_costo;
    private int diag_costo;
    //private int [][] maze;
    private Nodo[][] AreaBusqueda;
    private PriorityQueue<Nodo> ListaAbierta;
    private List<Nodo> ListaCerrada;
    private Nodo nodoInicial, nodoFinal;
    
    
    public AStarSearch(int filas,int columnas, Nodo nodoInicial,Nodo nodoFinal,int up_down_costo,int diag_costo){
        this.up_down_costo = up_down_costo;
        this.diag_costo = diag_costo;
        
        setNodoInicial(nodoInicial);
        setNodoFinal(nodoFinal);
        
        this.AreaBusqueda = new Nodo[filas][columnas];
        this.ListaAbierta = new PriorityQueue<Nodo>(new Comparator<Nodo>(){
            
            @Override // se ordenan los nodos de menor a mayor costo
            public int compare(Nodo nodo0, Nodo nodo1){
                return nodo0.getF() < nodo1.getF() ? -1 : nodo0.getF() > nodo1.getF() ? 1 : 0;
            }
        });
        
        setNodos();
        this.ListaCerrada = new ArrayList<Nodo>();   
        
    }
    
    public AStarSearch(int filas, int columnas, Nodo nodoInicial, Nodo nodoFinal){
        this(filas,columnas,nodoInicial,nodoFinal,UP_DOWN_costo,DIAG_costo);
    }

    public void setNodos(){// se evaluan los nodos de acuerdo a la heuristica
        for(int i=0;i < AreaBusqueda.length; i++) {
            for(int j=0; j<AreaBusqueda[0].length;j++){
                Nodo nodo = new Nodo(i,j);
                nodo.heuristica(getNodoFinal());
                this.AreaBusqueda[i][j] = nodo;
            }
        }
    }
    
    public void setParedes(int[][]obstaculos){
        for(int i=0;i<obstaculos.length;i++){
            int fila = obstaculos[i][0];
            int columna = obstaculos[i][1];
            setObstaculos(fila,columna);
        }
    }
    
    
    public List<Nodo> encontrarCamino(){
        ListaAbierta.add(nodoInicial);
        while(!isEmpty(ListaAbierta)){
            Nodo currNodo = ListaAbierta.poll();
            ListaCerrada.add(currNodo);
            if(EsNodoFinal(currNodo)){
               // maze[currNodo.getfila()][currNodo.getcolumna()] = 2;
                return getCamino(currNodo);
            }    
            else{
               // maze[currNodo.getfila()][currNodo.getcolumna()] = 2;
                AgregarNodosAdj(currNodo);
            }    
        }
        return new ArrayList<Nodo>();
    }
    
    
    private List<Nodo> getCamino(Nodo currNodo){ //se obtiene el camino
        List<Nodo> camino = new ArrayList<Nodo>();
        camino.add(currNodo);
        Nodo padre;
        
        while((padre = currNodo.getPadre())!=null){
            camino.add(0,padre);
            currNodo = padre;
            
        }
       
        return camino;
    }
    
    private void AgregarNodosAdj(Nodo currNodo){ // se agregan los nodos adjacentes
       
        AgregarFilaSuperiorAdj(currNodo);
        AgregarFilaMediaAdj(currNodo);
        AgregarFilaInferiorAdj(currNodo);
    }    
    
    
   private void AgregarFilaInferiorAdj(Nodo currNodo){ // se agregan los nodos adjacentes de la fila inferior al nodo actual
       int fila = currNodo.getfila();
        int columna = currNodo.getcolumna();
        int filaInf = fila+1;
        
        if(filaInf < getAreaBusqueda().length){
            VerificarNodo(currNodo, columna, filaInf, getCostoArribaAbaj());
        }
   }
    
   private void AgregarFilaMediaAdj(Nodo currNodo){// se agregan los nodos adjacentes de la fila del medio al nodo actual
        int fila = currNodo.getfila();
        int columna = currNodo.getcolumna();
        int filaMed = fila;
        
        if (columna - 1 >= 0) {
            VerificarNodo(currNodo, columna - 1, filaMed, getCostoArribaAbaj());
        }
        if (columna + 1 < getAreaBusqueda()[0].length) {
            VerificarNodo(currNodo, columna + 1, filaMed, getCostoArribaAbaj());
        }
       
   }
    
    private void AgregarFilaSuperiorAdj(Nodo currNodo){// se agregan los nodos adjacentes de la fila superior al nodo actual
        int fila = currNodo.getfila();
        int columna = currNodo.getcolumna();
        int filaSup = fila-1;
        
        if(filaSup >= 0){
            if(columna - 1 >= 0)
               VerificarNodo(currNodo, columna-1, filaSup, getCostoDiagonal());
            if(columna + 1 < getAreaBusqueda()[0].length)
               VerificarNodo(currNodo, columna+1, filaSup, getCostoDiagonal()); 
           VerificarNodo(currNodo, columna, filaSup, getCostoArribaAbaj());
                
        }
  }   
    
    private void VerificarNodo(Nodo currNodo, int columna,int fila, int costo){ // se verifica
        
        Nodo nodoAdjacente = getAreaBusqueda()[fila][columna];
        
        if(!nodoAdjacente.EsObstaculo() && !getListaCerrada().contains(nodoAdjacente)){
            if(!getListaAbierta().contains(nodoAdjacente)){
                nodoAdjacente.setDatosNodo(currNodo,costo);
                getListaAbierta().add(nodoAdjacente);
            }
            else{
                boolean cambio = nodoAdjacente.checarMejorCamino(currNodo,costo);
                if(cambio){
                    /**
                     * Cambiar y agregar el nodo, hara que se vuelva a ordenar la lista abierta
                     * con el costo final modificado del nodo
                     */
                    getListaAbierta().remove(nodoAdjacente);
                    getListaAbierta().add(nodoAdjacente);
                }
            }
        }
        
    }
    
    
    
    
     private boolean EsNodoFinal(Nodo currNodo) {
        return currNodo.equals(nodoFinal);
    }

    private boolean isEmpty(PriorityQueue<Nodo> listaAbierta) {
        return listaAbierta.isEmpty();
    }

    private void setObstaculos(int fila, int columna) {
        this.AreaBusqueda[fila][columna].setObstaculo(true);
    }

    public Nodo getNodoInicial() {
        return nodoInicial;
    }

    public void setNodoInicial(Nodo nodoInicial) {
        this.nodoInicial = nodoInicial;
    }

    public Nodo getNodoFinal() {
        return nodoFinal;
    }

    public void setNodoFinal(Nodo nodoFinal) {
        this.nodoFinal = nodoFinal;
    }

    public Nodo[][] getAreaBusqueda() {
        return AreaBusqueda;
    }

    public void setAreaBusqueda(Nodo[][] AreaBusqueda) {
        this.AreaBusqueda = AreaBusqueda;
    }
    

    public PriorityQueue<Nodo> getListaAbierta() {
        return ListaAbierta;
    }

    public void setOpenList(PriorityQueue<Nodo> ListaAbierta) {
        this.ListaAbierta = ListaAbierta;
    }

    public List<Nodo> getListaCerrada() {
        return ListaCerrada;
    }

    public void setListaCerrada(List<Nodo> ListaCerrada) {
        this.ListaCerrada = ListaCerrada;
    }

    public int getCostoArribaAbaj() {
        return up_down_costo;
    }

    public void setCostoArribaAbajo(int up_down_costo) {
        this.up_down_costo = up_down_costo;
    }

    private int getCostoDiagonal() {
        return diag_costo;
    }

    private void setCostoDiagonal(int diag_costo) {
        this.diag_costo = diag_costo;
    }   
}
