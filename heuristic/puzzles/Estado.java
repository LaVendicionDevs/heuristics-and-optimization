package aima.core.environment.hidato;

import java.util.Arrays;


public class Estado {
	

	public Integer[][] matrix; 
	public int hidato_max;
	public int[] actual = new int[2];
	
    /** Constructor de Estado para el estado inicial.  Se construye a partir de
      * estructuas que contengan la informacion de los ficheros de entrada 
      */
	public Estado(Integer[][] input_matrix, int hidato_max, int[] actual){
		matrix = new Integer[input_matrix.length][input_matrix[0].length];
		for (int i = 0; i < input_matrix.length; i++) {
		    System.arraycopy(input_matrix[i], 0, matrix[i], 0, input_matrix[0].length);
		}
		this.hidato_max = hidato_max;
		System.arraycopy(actual, 0, this.actual, 0, 2);

	}

	/**
	 * Constructor de copia de un Estado.
	 * El nuevo estado debe ser una copia del anterior.
	 * 
	 * @param otro estado que debe copiarse
	 */
	public Estado(Estado otro) {
		/* se copian la matriz y el array con arraycopy y no con referencia porque entonces no es una copia real, solo de puntero */
		matrix = new Integer[otro.matrix.length][otro.matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
		    System.arraycopy(otro.matrix[i], 0, matrix[i], 0, matrix[0].length);
		}
		this.hidato_max = otro.hidato_max;
		System.arraycopy(otro.actual, 0, this.actual, 0, 2);

	}


	/* hashcode y equals para comprobar que no se creen estados repetidos */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(actual);
		result = prime * result + hidato_max;
		result = prime * result + Arrays.hashCode(matrix);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (!Arrays.equals(actual, other.actual))
			return false;
		if (hidato_max != other.hidato_max)
			return false;
		if (!Arrays.deepEquals(matrix, other.matrix))
			return false;
		return true;
	}

	/*para imprimir un estado*/
	@Override
	public String toString() {
		String resultado = "";
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				resultado += matrix[i][j] + " ";
			}
			resultado += '\n';
		}
		return resultado;
		//return "Estado [matrix=" + Arrays.toString(matrix) + ", hidato_max="
		//		+ hidato_max + ", actual=" + Arrays.toString(actual) + "]";
	}


        
}
