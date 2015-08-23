package aima.core.environment.hidato;

import aima.core.search.framework.GoalTest;

/**
 * Clase que implementa la funcion que decide si las metas se cumplen en un estado dado o no
 * Puede contener atributos para saber cuales son las metas de este problema o estar incluidas en el estado.
 */
public class FuncionMetas implements GoalTest {
	
	public FuncionMetas(){
		
	}

	public boolean isGoalState(Object state) {
		Estado estado = (Estado)state; /* Estado que se va a comprobar si es final */
		
		int adyacenteNull=0; /* contador de casillas nulas */
		
		/* bucles para contar la cantidad de nulls de la matriz */
		for(int i= 0; i<estado.matrix.length; i++){
			for (int j=0; j<estado.matrix[0].length; j++){
				if(estado.matrix[i][j]==null){
					adyacenteNull++;
				}
			}
		}
		
		
		/* contador de casillas adyacentes con valor valido encontradas (casillas consecutivas) */
		int adyacenteOK=0;

		for(int i= 0; i<estado.matrix.length; i++){ /* Recorrer matriz entera */
			for (int j=0; j<estado.matrix[0].length; j++){
				
				for(int n=i-1; n<=i+1; n++){ /* Recorrer casillas adyacentes para cada posicion de la matriz*/
		            for(int m=j-1; m<=j+1; m++){
		                if(!(n<0 || m<0 || n>=estado.matrix.length || m>=estado.matrix[0].length) /* controlar fuera de limites */
		                		&& estado.matrix[n][m]!=null /* posiciones nulas no se comprueban */
		                		&& estado.matrix[i][j]!=null 
		                		&& estado.matrix[i][j]!=estado.hidato_max){  /* al la casilla con el valor máximo no se le busca adyacente consecutivo porque no va a haber ninguno mayor */           
		                	if(estado.matrix[i][j]+1==estado.matrix[n][m] ){
		                		adyacenteOK++; /* si alguno de los adyacentes es el consecutivo incrementa el contador */
		                	}
		                }			
					}
				}
				/* si el contador + 1 (por el número maximo) es igual que el total de casillas con valor de la matriz (sin nulos), ha encontrado solución */
				if(adyacenteOK+1==estado.matrix.length*estado.matrix[0].length-adyacenteNull){ 
					System.out.println("fin: \n"+estado);
					return true;
				}
			}
		}
		return false;
	}

}



