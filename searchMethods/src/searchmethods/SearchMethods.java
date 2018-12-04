/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchmethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Tania V
 */
public class SearchMethods {
    
    int [][] maze = null;
    int [][] temp = null;
    int fil,col;
    
    public void respaldo(int [][] maze){
        for(int x=0;x<maze.length;x++){
            for(int y=0;y<maze[x].length;y++){
                 temp[x][y] = maze[x][y];
            }
          } 
    }
    
    public void laberinto(){
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Filas: ");
        int fil = sc.nextInt();
        System.out.println("Columnas: ");
        int col = sc.nextInt();
        maze= new int[fil][col];
        System.out.print("Laberinto:");
        for(int x=0;x<maze.length;x++){
            for(int y=0;y<maze[x].length;y++){
                
                maze[x][y]=sc.nextInt();
            }
        }
    }
    
    public void imprimir(int [][] maze){
        System.out.println("Laberinto:");
        for(int x=0;x<maze.length;x++){
            for(int y=0;y<maze[x].length;y++){
                 System.out.print(maze[x][y]);
            }
            System.out.println();
          }     
    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         SearchMethods la = new SearchMethods();
       // List<Integer> path = new ArrayList<Integer>();
        List<Nodo> camino_estrella;
       
         DepthFirstSearch dfs;
         Nodo nodoInicial, nodoFinal;
         AStarSearch Aestrella;
         BreadthFirstSearch bfs;
         
       
        
        
        int [][] maze = null;
        int [][] paredes = null;
        int [][] temp = null;
        
        //int [][] paredes = null;
       
        //SearchMethods la = new SearchMethods();
        int op,fil = 0,col = 0,cont = 0;
        
        Scanner sc = new Scanner(System.in);
        
        
        
        do{
            System.out.println("MENU:\t\n 1)Ingresar Laberinto\t\n 2)DepthFirstSearch \t\n "
              + "3)BreadthFirstSearch \t\n 4)Hill Climbing\t\n 5)A*\t\n 6)BestFirst\t\n 7)Salir\t\nOPCION: ");
            
            op = sc.nextInt();
            
           switch(op){
                case 1:{
                     int val;
                     //int x,y;
                     System.out.println("Filas: ");
                      fil = sc.nextInt();
                     System.out.println("Columnas: ");
                      col = sc.nextInt();
                     maze= new int[fil][col];
                     temp = new int[fil][col];
                     //System.out.println("Laberinto:");
                
                      for(int i=0;i<maze.length;i++){
                        for(int j=0;j<maze[i].length;j++){
                            maze[i][j]=0;// se inicializa el laberinto sin obstaculos
                            temp[i][j]=maze[i][j];
                            
                        }
                        
                      }
                      //la.respaldo(maze);
                      la.imprimir(temp);
                    
                    do{
                            System.out.println("Ingresar paredes: 1)si 2)no");// se ingresan de forma manual las coordenadas de los obstaculos
                            val =sc.nextInt();
                            switch(val){
                             case 1:{
                                 System.out.println("Ingresar coordenadas");
                                 System.out.print("x:");
                                 int i =sc.nextInt();
                                 System.out.print("y:");
                                 int j = sc.nextInt();
                                 maze[i][j]=1; // se marca donde desea el usuario las paredes del laberinto
                                 temp[i][j]=maze[i][j];
                                 paredes = new int[][]{
                                     {i,j} 
                                 }; // se crea una copia de las paredes donde solo se guardan las coordenadas de los blqueos 
                                 // se usa en el A*
                                    
                                 //la.respaldo(maze);        
                                 //la.imprimir(maze);
                                break;
                             }
                             case 2:{
                                 System.out.println("Obstaculos ingresados correctamente");
                                 la.imprimir(temp);
                                 break;
                             }
                             default:{
                                 break;
                             }

                         } 
                    }while(val!=2);            
                 break;   
                }
                
                case 2:{
                    //int [][] lab;
                    
                    System.out.println("Depth First Search");
                    System.out.println("Coordenadas del punto de inicio");
                    System.out.print("x:");
                    int xi = sc.nextInt();
                    System.out.print("y:");
                    int yi = sc.nextInt();
                    temp[xi][yi]=4; //se marca en el laberinto el punto inicial 
                    
                    System.out.println("Coordenadas del punto final");
                    System.out.print("x:");
                    int xf = sc.nextInt();
                    System.out.print("y:");
                    int yf = sc.nextInt();
                    temp[xf][yf]=3; // se marca en el laberinto el punto objetivo 
                    
                   // la.imprimir(maze);
                    
                    dfs=new DepthFirstSearch(temp,fil,col);
                    dfs.solve(new Nodo(xi,yi)); // se crea el nodo inicial con las coordenadas que ingreso el usuario
                    dfs.fillPath();
                    la.imprimir(temp);
                    cont=0;
                    for(int x=0;x<maze.length;x++){
                        for(int y=0;y<maze[x].length;y++){
                             if(temp[x][y] == 2 || temp[x][y] == 3 )
                                 cont++;
                             temp[x][y] = maze[x][y];
                        }
                      } 
                    System.out.println("Num. pasos: "+cont);
                    break;
                }
                case 3:{
                    bfs = new BreadthFirstSearch();
                    System.out.println("Coordenadas del punto de inicio");
                    System.out.print("x:");
                    int xi = sc.nextInt();
                    System.out.print("y:");
                    int yi = sc.nextInt();
                   
                    //temp[xi][yi]=3; //se marca en el laberinto el punto inicial 
                    
                    System.out.println("Coordenadas del punto final");
                    System.out.print("x:");
                    int xf = sc.nextInt();
                    System.out.print("y:");
                    int yf = sc.nextInt();
                    temp[xf][yf]=9;
                            
                    
                    BreadthFirstSearch.set(xi, yi,xf,yf,temp);
                    
                    for(int x=0;x<maze.length;x++){     // se vuelve a inicializar la matriz temporal con el original
                        for(int y=0;y<maze[x].length;y++){
                             temp[x][y] = maze[x][y];
                        }
                      }                  

    
                    break;
                }
                case 4:{
                    
                    break;
                    
                }
                case 5:{
                    
                    
                    System.out.println("Coordenadas del punto de inicio");
                    System.out.print("x:");
                    int xi = sc.nextInt();
                    System.out.print("y:");
                    int yi = sc.nextInt();
                    nodoInicial = new Nodo(xi,yi); // se crea el nodo inicial
                    temp[xi][yi]=5;
                    
                    System.out.println("Coordenadas del punto final");
                    System.out.print("x:");
                    int xf = sc.nextInt();
                    System.out.print("y:");
                    int yf = sc.nextInt();
                    nodoFinal = new Nodo (xf,yf); //se crea el nodo final
                    temp[xf][yf]=9;
                    
                     la.imprimir(temp);
                    cont = 0;
                    Aestrella = new AStarSearch(fil,col,nodoInicial,nodoFinal);
                    Aestrella.setParedes(paredes);
                    
                    camino_estrella = Aestrella.encontrarCamino();
                    for(Nodo nodo : camino_estrella){
                        temp[nodo.getfila()][nodo.getcolumna()] = 2;// se marca el camino que se debe de seguir en el laberinto
                        System.out.println(nodo); // se muestran los valores de cada nodo f,g,h, y posicion en x y y
                        cont++;
                    }
                    System.out.println("Num. pasos: "+cont);
                    la.imprimir(temp);
                    for(int x=0;x<maze.length;x++){
                        for(int y=0;y<maze[x].length;y++){
                             temp[x][y] = maze[x][y];
                        }
                      } 
                    //Aestrella.fillCamino();
                    //la.respaldo(maze);
                    //maze[xi][yi]=0;//se reinician las coordenadas de origen
                    //maze[xf][yf]=0;//se reincian las coordenadas del final
                    
                    break;
                }
                case 6:{
                    break;
                }
                case 7:{
                    
                }
                default:
                    break;
            } 
        } while(op != 7);
 
    }
    
    
    
}
