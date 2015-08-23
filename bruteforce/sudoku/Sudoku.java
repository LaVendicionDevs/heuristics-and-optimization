import java.util.*;
import java.io.*;
import java.util.ArrayList;

import org.jacop.core.*; 
import org.jacop.constraints.*; 
import org.jacop.search.*; 


public class Sudoku {

    static Store store = new Store();
    static IntVar[][] matrix = new IntVar[9][9];

    public static void main (String[] args) {

        String file_path = "";

        /*comprobar que los argumentos son correctos*/
        if (args.length != 1) {
            System.out.println("execute $ java -classpath .:jacop-4.1.0.jar Sudoku <path_to_file>");
            System.exit(-1);
        } else {
            file_path = args[0];
        }

        read_file(file_path);

        /* Lineas horizontales */
        IntVar horizontal[] = new IntVar[9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                horizontal[j]=matrix[i][j];
            }
            store.impose(new Alldiff(horizontal));
        }

        /* Lineas verticales */
        IntVar vertical[] = new IntVar[9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                vertical[j]=matrix[j][i];
            }
            store.impose(new Alldiff(vertical));
        }

        /* Bloques */
        IntVar bloque[] = new IntVar[9];
        for(int m=0; m<3; m++){
            for(int n=0; n<3; n++){
                for(int i=0; i<3; i++){
                    for(int j=0; j<3; j++){
                        bloque[3*i+j] = matrix[i+3*m][j+3*n];
                    }
                }
                store.impose(new Alldiff(bloque));
            }
        }


        IntVar[] array_from_matrix = new IntVar[81];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                array_from_matrix[9*i+j] = matrix[i][j];
            }
        }
        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(array_from_matrix, new SmallestDomain<IntVar>(),
        new IndomainMin<IntVar>());
        Boolean result = label.labeling(store, select);
        if (result) {
            for(int i=0; i<9; i++){
                for(int j=0; j<9; j++){
                    System.out.print(matrix[i][j].value() + " ");
                }             
                System.out.println();
            }
        } else{
            System.out.println("Error");
        }
    }

    /*metodo para leer el fichero por ruta*/
    public static void read_file(String file_path){
        
        int i = 0;
        int j = 0;

        File file = new File(file_path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null) {
                j = 0;
                for(String letter: text.split(" ")){
                    if(!letter.equals("_")){
                        matrix[i][j] = new IntVar(store, Integer.parseInt(letter), Integer.parseInt(letter));
                    }else{
                        matrix[i][j] = new IntVar(store, 1, 9);
                    }
                    ++j;
                }
                ++i;
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