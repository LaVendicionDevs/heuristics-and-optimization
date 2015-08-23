package aima.core.environment.hidato;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Metrics;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;


import java.io.*;

/**
 * Clase que Ejecuta los algoritmos en el problema deseado.
 */

public class Ejecutar {

    private static Algoritmo getAlgoritmo(String arg) {
        arg = arg.toLowerCase();
        if (arg.equals("amplitud")) {
            return Algoritmo.Amplitud;
        } else if (arg.equals("profundidad")) {
            return Algoritmo.Profundidad;
        } else if (arg.equals("astar")) {
            return Algoritmo.Astar;
        } else if (arg.equals("gbfs")) {
            return Algoritmo.GBFS;
        } else {
            System.err.println("Algoritmo desconocido: " + arg);
            System.exit(-1);
            return null;
        }
    }

    private static Heuristica getHeuristica(String arg) {
        arg = arg.toLowerCase();
        if (arg.equals("h1")) {
            return Heuristica.H1;
        } else {
            System.err.println("Heuristica desconocida: " + arg);
            System.exit(-1);
            return null;
        }
    }
    // Identificador para los algoritmos que pueden ejecutarse

    public enum Algoritmo {
        Amplitud, Profundidad, Astar, GBFS
    };

    // Poner aqui un nuevo identificador para cada heurística creada
    public enum Heuristica {

        H1
    };

    public static void main(String args[]) {
        if (args.length != 3) {
            System.out.println("Utilizacion: java Ejecutar <algoritmo> <heuristica> <ruta fichero>");
            System.exit(0);
        }
        
        Algoritmo algoritmo = getAlgoritmo(args[0]);
        Heuristica heuristica = getHeuristica(args[1]);
        
        /*Lectura del ficheros*/ 
        
        Estado initialState = read_file(args[2]); //new Estado(0,0);

        // Inicializacion de las funciones que definen el problema.
        // Pueden incluirse parametros en los constructores segun sea necesario
        AccionesDisponibles accDisponibles = new AccionesDisponibles();
        ResultadoAccion resAccion = new ResultadoAccion();
        FuncionMetas fMetas = new FuncionMetas();
        CosteAccion costeAccion = new CosteAccion();

	/* Imprime el estado para comprobar que se ha cargado correctamente */
        
        System.out.println(initialState.toString());	
        
        try {
            Problem problem = new Problem(initialState, accDisponibles,
                    resAccion, fMetas, costeAccion);

            HeuristicFunction hf = null;
            switch (heuristica) {
                case H1:
                    System.out.println("Heuristica: h1");
                    hf = new Heuristica1();
                    break;
                // Poner aqui el resto de heuristicas
                default:
                    System.out.println("Heuristica invalida");
                    System.exit(-1);
            }

            Search search = null;
            switch (algoritmo) {
                case Amplitud:
                    System.out.println("Algoritmo: Amplitud");
                    search = new BreadthFirstSearch();
                    break;
                case Profundidad:
                    System.out.println("Algoritmo: Profundidad");
                    search = new DepthFirstSearch(new GraphSearch());
                    break;
                case Astar:
                    System.out.println("Algoritmo: A star");
                    search = new AStarSearch(new GraphSearch(), hf);
                    break;
                case GBFS:
                    System.out.println("Algoritmo: GBFS");
                    search = new GreedyBestFirstSearch(new GraphSearch(), hf);
                    break;
                default:
                    System.out.println("Algoritmo invalido");
                    System.exit(-1);
            }

            long t = System.currentTimeMillis();
            List<Action> actionList = search.search(problem);
            long t2 = System.currentTimeMillis();
           
            printActions(actionList);

            System.out.println("Time: " + (t2 - t) / 1000.0 + " s");
            printInstrumentation(search.getMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Metrics metric) {
        for (String key : metric.keySet()) {
            String property = metric.get(key);
            System.out.println(key + " : " + property);
        }
    }

    private static void printActions(List<Action> actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i).toString();
            System.out.println(action);
        }
        System.out.println("Plan Length: " + actions.size());
    }
    
    public static Estado read_file(String file_path){
        int i = 0;
        File file = new File(file_path);
        BufferedReader reader = null;
        String[] headers;
        int hidato_width = 0;
        int hidato_height = 0;
        int max_value = 0;
        
        int[] min_value_pos = new int[2];

        Integer[][] step_matrix = new Integer[0][0];
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null) {
                if(i==0){
                    headers = text.split(" ");
                    hidato_width = Integer.parseInt(headers[0]);
                    hidato_height = Integer.parseInt(headers[1]);
                    step_matrix = new Integer[hidato_width][hidato_height];
                    text = reader.readLine();
                }

                String[] raw_line = text.split(" ");
                for(int j=0; j<hidato_width; j++){
                    String letter = raw_line[j];
                    if(letter.equals("#")){
                        step_matrix[i][j] = null;
                    }else if (letter.equals("_")){
                        step_matrix[i][j] = 0;
                    }else{
                        step_matrix[i][j] = Integer.parseInt(letter);
                        if(Integer.parseInt(letter)>max_value){
                            max_value = Integer.parseInt(letter);
                        }
                        if(step_matrix[i][j]==1){
                        	min_value_pos[0] = i;
                        	min_value_pos[1] = j;
                        }
                    }
                }
                ++i;
            }
            return new Estado(step_matrix, max_value, min_value_pos);

            

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Unknown error.");
            System.exit(-1);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Unknown error.");
                System.exit(-1);
            }
        }
        return null;
    }
    
}
