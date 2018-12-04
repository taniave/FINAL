/*
 * Clase que resuelve un laberinto de NxM con el metodo de busqueda de DepthFirstSearch
 *
 */
package searchmethods;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch {

    private int[][] maze;
    // se crea una matriz de nodos para guardar el laberinto
    private Nodo[][] prev;

    private int szX;
    private int szY;

    private Nodo nodoFinal;

    DepthFirstSearch(int[][] maze, int szY, int szX) {
        this.maze = maze;
        this.szY = szY;
        this.szX = szX;

        prev = new Nodo[szY][szX];
    }

    private boolean inBoundsX(int num){ // se verifica que este dentro de los rangos de las filas
        return num >= 0 && num < szX;
    }

    private boolean inBoundsY(int num){// se verifica que este dentro de los rangos de las columnas
        return num >= 0 && num < szY;
    }

    public void solve(Nodo nodoInicial){
        Stack<Nodo> stack = new Stack<>(); // se crea una pila para guardar los nodos que fueron 
        HashSet<Nodo> visitado = new HashSet<>(); // se crea una tabla hash para guardar los nodos que fueron visitados

        stack.push(nodoInicial); // se agrega el punto de inicio a la pila

        while(!stack.isEmpty()) {
            Nodo temp = stack.pop(); // se crea una variable temporal para guardar el ultimo nodo agregado 
            visitado.add(temp);// se agrega el temp a la tabla hash para marcar como nodo visitado y poder avanzar a travez del laberinto

            if (maze[temp.getcolumna()][temp.getfila()] == 3) { //si se encuentra el destino en laberinto
                nodoFinal = temp; // a la variable nodoFinal se le asigna el valor del temporal
                break;
            }

            for(Nodo nodo : this.getAdjacentEdges(temp)) {
                if (!visitado.contains(nodo)) {
                    stack.push(nodo); //
                    prev[nodo.getcolumna()][nodo.getfila()] = temp; // se guarda en la copia del laberinto los nodos temporales que ya fueron visitaods
                }
            }
        }
    }

    //Marca el camino que se debe de seguir en la matriz
    public void fillPath() {
        if (nodoFinal == null) {
            System.out.println("No hay camino en el laberinto");
        } else {
            // se asume que el punto de inicio y el punto final son difrentes
            for (;;) {
                nodoFinal = prev[nodoFinal.getcolumna()][nodoFinal.getfila()]; // 

                // si no existe un nodo previo podemos romper el ciclo
                if (nodoFinal == null) {
                    break;
                }

                maze[nodoFinal.getcolumna()][nodoFinal.getfila()] = 2;

            }
        }
    }

    // se obtienen los nodos que son adjacentes al nodo temporal
    private List<Nodo> getAdjacentEdges(Nodo temp) {
        
        List<Nodo> vecino = new ArrayList<Nodo>();
        
        // se verifican que los nodos no sean bloqueos ( bloqueos representados con un 1)
        if(this.inBoundsX(temp.getfila()+1)){
            if(this.maze[temp.getcolumna()][temp.getfila()+1] != 1){ 
                vecino.add(new Nodo(temp.getfila()+1, temp.getcolumna()));
            }
        }
        if(this.inBoundsX(temp.getfila()-1)){
            if(this.maze[temp.getcolumna()][temp.getfila()-1] != 1){
                vecino.add(new Nodo(temp.getfila()-1, temp.getcolumna()));
            }
        }
        if(this.inBoundsY(temp.getcolumna()+1)){
            if(this.maze[temp.getcolumna()+1][temp.getfila()] != 1){
                vecino.add(new Nodo(temp.getfila(), temp.getcolumna()+1));
            }
        }
        if(this.inBoundsY(temp.getcolumna()-1)){
            if(this.maze[temp.getcolumna()-1][temp.getfila()] != 1){
                vecino.add(new Nodo(temp.getfila(), temp.getcolumna()-1));
            }
        }
        return vecino; // regresa los datos de nodo vecino
    }

}