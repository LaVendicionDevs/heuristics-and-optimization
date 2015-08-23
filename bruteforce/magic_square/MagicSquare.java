import java.util.*;
import java.io.*;
import java.util.ArrayList;

import org.jacop.core.*; 
import org.jacop.constraints.*; 
import org.jacop.search.*; 

public class MagicSquare {

    public static void main (String[] args) {
        int size = 3; 

        /*comprobar argumentos de entrada*/
        if (args.length != 1) {
            System.out.println("execute $ java -classpath .:jacop-4.1.0.jar MagicSquare <Int_matrix_size>");
            System.exit(-1);
        } else {
            size = Integer.parseInt(args[0]);
        }


        Store store = new Store();
        IntVar[][] matrix = new IntVar[size][size];
        int m_n = size*(size*size+1)/2; /*numero magico*/
        IntVar magic_number = new IntVar(store, m_n, m_n);

        /* Almacenar elementos de la matriz, con un valor entre 1 y size*size */
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                matrix[i][j] = new IntVar(store, 1, size*size);
            }
        }

        /* Todos los elementos de la matriz */
        ArrayList<IntVar> all_elements = new ArrayList<IntVar>();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                all_elements.add(matrix[i][j]);
            }
        }
        store.impose(new Alldiff(all_elements));

        /* Diagonal principal */
        IntVar diagonal[] = new IntVar[size];
        for(int i=0; i<size; i++){
            diagonal[i]=matrix[i][i];
        }
        store.impose(new Sum(diagonal, magic_number));

        /* Diagonal negativa */
        IntVar diagonal_neg[] = new IntVar[size];
        for(int i=0; i<size; i++){
            diagonal_neg[i]=matrix[i][size-i-1];
        }
        store.impose(new Sum(diagonal_neg, magic_number));

        /* Lineas horizontales */
        IntVar horizontal[] = new IntVar[size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                horizontal[j]=matrix[i][j];
            }
            store.impose(new Sum(horizontal, magic_number));
        }

       /* Lineas verticales */
        IntVar vertical[] = new IntVar[size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                vertical[j]=matrix[j][i];
            }
            store.impose(new Sum(vertical,magic_number));
        }


        /*convertir la matriz a un array*/
        IntVar[] array_from_matrix = new IntVar[all_elements.size()];
        array_from_matrix = all_elements.toArray(array_from_matrix);

        Search<IntVar> label = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>(array_from_matrix, new SmallestDomain<IntVar>(),
        new IndomainMin<IntVar>());
        Boolean result = label.labeling(store, select);
        if (result) {
            for(int i=0; i<size; i++){
                for(int j=0; j<size; j++){
                    System.out.print(matrix[i][j].value() + "\t");
                }             
                System.out.println();
            }
        } else{
            System.out.println("Error");
        }
        System.out.println();
    }
}