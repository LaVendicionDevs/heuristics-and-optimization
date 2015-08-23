import java.util.*;
import java.io.*;
import java.util.ArrayList;

import org.jacop.core.*; 
import org.jacop.constraints.*; 
import org.jacop.search.*; 

public class Hidato {

    static Store store = new Store();
    static IntVar[][] matrix = new IntVar[0][0];
    static int hidato_width = 0;
    static int hidato_height = 0;
    static int max_value = 0;

    public static void main (String[] args) {

        String file_path = "";

        if (args.length != 1) {
            System.out.println("execute $ java -classpath .:jacop-4.1.0.jar Hidato <path_to_file>");
            System.exit(-1);
        } else {
            file_path = args[0];
        }

        read_file(file_path);

        /* Todos los elementos deben ser distintos*/
        ArrayList<IntVar> all_elements = new ArrayList<IntVar>();
        for(int i=0; i<hidato_height; i++){
            for(int j=0; j<hidato_width; j++){
                if (matrix[i][j]!=null){
                    all_elements.add(matrix[i][j]);
                }
            }
        }
        store.impose(new Alldiff(all_elements));


        ArrayList<PrimitiveConstraint> computed_cells;
        for(int i=0; i<hidato_height; i++){
            for (int j=0; j<hidato_width; j++) {
                if(matrix[i][j]!=null){
                    /*se comprueban todas las casillas adyacentes a cada posicion*/
                    computed_cells = new ArrayList<PrimitiveConstraint>();
                    for(int n=i-1; n<=i+1; n++){
                        for(int m=j-1; m<=j+1; m++){
                            if(!(n<0 || m<0 || n>=hidato_height || m>=hidato_width) && matrix[n][m]!=null){ /*casilla adyacente dentro de los limites de la matriz y distinto de null*/
                                
                                if(matrix[i][j].value()!=max_value && (i!=n || m!=j)){ /*no comprobamos para la casilla central ni para la ultima casilla*/
                                    computed_cells.add(new XplusCeqZ(matrix[i][j], 1, matrix[n][m])); /*restriccion de que la casilla adyacente tenga valor+1 que la central*/
                                }
                            }
                        }
                    }
                    if (computed_cells.size() > 0) {
                        Collections.shuffle(computed_cells);
                        store.impose(new Or(computed_cells));/*al menos una de las restricciones de las casillas adyacentes se debe cumplir*/
                    }
                }
            }
        }

        /* Quitamos los nulls y pasamos a array la matriz*/
        IntVar[] array_from_matrix = new IntVar[max_value];

        int l = 0;
        for(int i=0; i<hidato_height; i++){
            for(int j=0; j<hidato_width; j++){
                if(matrix[i][j] != null){
                    array_from_matrix[l] = matrix[i][j];
                    l++;
                }
            }             
        }

        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(array_from_matrix, new SmallestDomain<IntVar>(),
        new IndomainMin<IntVar>());
        Boolean result = label.labeling(store, select);
        if (result) {
            for(int i=0; i<hidato_height; i++){
                for(int j=0; j<hidato_width; j++){
                    System.out.print(((matrix[i][j]==null)?"#":matrix[i][j].value()) + "\t");
                }             
                System.out.println();
            }
        } else{
            System.out.println("Error");
        }
    }

    public static void read_file(String file_path){
        int i = 0;
        File file = new File(file_path);
        BufferedReader reader = null;
        String[] headers;
        hidato_width = 0;
        hidato_height = 0;

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
                    }
                }
                ++i;
            }

            /* Escribir sobre la matrix de IntVar*/
            matrix = new IntVar[hidato_width][hidato_height];
            for(i=0; i<hidato_height; i++){
                for(int j=0; j<hidato_width; j++){
                    if (step_matrix[i][j]==null){
                        matrix[i][j] = null;
                    } else if (step_matrix[i][j]==0){
                        matrix[i][j] = new IntVar(store, 1, max_value);
                    } else{
                        matrix[i][j] = new IntVar(store, step_matrix[i][j], step_matrix[i][j]);
                    }
                }
            }

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
    }
}