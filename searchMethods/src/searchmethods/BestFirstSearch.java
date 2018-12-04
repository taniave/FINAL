/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchmethods;

import java.math.*;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Scanner;
 
public class BestFirstSearch
{
    private PriorityQueue<Vertex> priorityQueue;
    private int heuristicvalues[];
    private int NumeroDeNodos;
 
    public static final int MAX_VALUE = 999;
 
    public BestFirstSearch(int NumeroDeNodos)
    {
        this.NumeroDeNodos = NumeroDeNodos;
        this.priorityQueue = new PriorityQueue<Vertex>(this.NumeroDeNodos,
        new Vertex());
    }
 
    public void bestFirstSearch(int adjacencyMatrix[][], int[] heuristicvalues,int source)
    {
        int evaluationNode;
        int destinationNode;
        int visited[] = new int [NumeroDeNodos + 1];
        this.heuristicvalues = heuristicvalues;
 
        priorityQueue.add(new Vertex(source, this.heuristicvalues[source]));
        visited[source] = 1;
 
        while (!priorityQueue.isEmpty())// Aplicación de la Heurística BFS
        {
            evaluationNode = getNodeWithMinimumHeuristicValue();
            destinationNode = 1;
 
            System.out.print(evaluationNode + "\t");			
            while (destinationNode <= NumeroDeNodos)
            {
                Vertex vertex = new Vertex(destinationNode,this.heuristicvalues[destinationNode]);
                if ((adjacencyMatrix[evaluationNode][destinationNode] != MAX_VALUE 			                                           
                      && evaluationNode != destinationNode)&& visited[destinationNode] == 0)
                {
                    priorityQueue.add(vertex);
                    visited[destinationNode] = 1;
                }
                destinationNode++;
            }
        }
    }
 
    private int getNodeWithMinimumHeuristicValue()//Recibe los nodos ingresados
    {
        Vertex vertex = priorityQueue.remove();
        return vertex.node;
    }
 
    public static void main(String... arg)
    {
        int adjacency_matrix[][];
        int numero_de_vertices;
        int source = 0;
        int heuristicvalues[];
 
        Scanner scan = new Scanner(System.in);
        try
        {
            System.out.println("Ingrese el número de Vertices");
            numero_de_vertices= scan.nextInt();
            adjacency_matrix = new int[numero_de_vertices+ 1][numero_de_vertices+ 1];
            heuristicvalues = new int[numero_de_vertices+ 1];
 
            System.out.println("Ingresa toda la matriz para crear el gráfico");
            for (int i = 1; i <= numero_de_vertices; i++)
            {
                for (int j = 1; j <= numero_de_vertices; j++)
                {
                    adjacency_matrix[i][j] = scan.nextInt();
                    if (i == j)
                    {
                        adjacency_matrix[i][j] = 0;
                        continue;
                    }
                    if (adjacency_matrix[i][j] == 0)
                    {
                        adjacency_matrix[i][j] = MAX_VALUE;
                    }
                }
            }
            for (int i = 1; i <= numero_de_vertices; i++)
            {
                for (int j = 1; j <= numero_de_vertices; j++)
                {
                    if (adjacency_matrix[i][j] == 1 && adjacency_matrix[j][i] == 0)
                    {
                        adjacency_matrix[j][i] = 1;
                    }
                }
            }
 
            System.out.println("Ingrese el heuristic value de los nodos");
            for (int vertex = 1; vertex <= numero_de_vertices; vertex++)// for para crear  ingresar los nodos para saber el camino a seguir
            {
                System.out.print(vertex + ".");
                heuristicvalues[vertex] = scan.nextInt();
                System.out.println();
            }
 
            System.out.println("Ingrese el valor con el que quiere comenzar ");
            source = scan.nextInt();
 
            System.out.println("El camino que seguirá es el siguiente");//imprimir el camino
            BestFirstSearch bestFirstSearch = new BestFirstSearch(numero_de_vertices);
            bestFirstSearch.bestFirstSearch(adjacency_matrix, heuristicvalues,source);
 
       } catch (InputMismatchException inputMismatch)// excepción para formato erroneo del programa
       {
           System.out.println("El formato de entrada no es el correcto");
       }
       scan.close();
   }
}
 
class Vertex implements Comparator<Vertex>
{
    public int heuristicvalue;
    public int node;
 
    public Vertex(int node, int heuristicvalue)
    {
        this.heuristicvalue = heuristicvalue;
        this.node = node;
    }
 
    public Vertex()
    {
            
    } 
 
    @Override 
    public int compare(Vertex vertex1, Vertex vertex2)
    {
        if (vertex1.heuristicvalue < vertex2.heuristicvalue)
            return -1;
        if (vertex1.heuristicvalue > vertex2.heuristicvalue)
            return 1;
        return 0;
    }
 
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Vertex)
        {
            Vertex node = (Vertex) obj;
            if (this.node == node.node)
            {
                return true;
            }
        }
        return false;
    }
}

//Caso de Prueba
/*
Ingrese el número de Vertices
6
Ingresa toda la matriz para crear el gráfico
0 0 1 1 0 1 
0 0 0 1 1 1 
1 0 0 1 0 0
1 1 1 0 1 0
0 1 0 1 0 0
1 1 0 0 0 0
Ingrese el heuristic value de los nodos
1.2

2.3

3.5

4.4

5.3

6.5

Ingrese el valor con el que quiere comenzar 
2
El camino que seguirá es el siguiente
2	5	4	1	3	6	*/
 
