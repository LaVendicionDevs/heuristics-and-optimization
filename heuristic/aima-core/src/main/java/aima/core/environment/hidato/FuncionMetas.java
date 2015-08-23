package aima.core.environment.hidato;

import aima.core.search.framework.GoalTest;
import java.util.Map;

/**
 * Clase que implementa la funcion que decide si las metas se cumplen en un estado dado o no
 * Puede contener atributos para saber cuales son las metas de este problema o estar incluidas en el estado.
 */
public class FuncionMetas implements GoalTest {
	Map<Integer, Integer> map;
	
	public FuncionMetas(){
		
	}

	public boolean isGoalState(Object state) {
		Estado estado = (Estado)state;
		
		int adyacenteNull=0;
		for(int i= 0; i<estado.matrix.length; i++){
			for (int j=0; j<estado.matrix[0].length; j++){
				if(estado.matrix[i][j]==null){
					adyacenteNull++;
				}
			}
		}
		
		
		int adyacenteOK=0;
		for(int i= 0; i<estado.matrix.length; i++){
			for (int j=0; j<estado.matrix[0].length; j++){
				
				for(int n=i-1; n<=i+1; n++){
		             for(int m=j-1; m<=j+1; m++){
		                 if(!(n<0 || m<0 || n>=estado.matrix.length || m>=estado.matrix[0].length) 
		                		 && estado.matrix[n][m]!=null 
		                		 && estado.matrix[i][j]!=null
		                		 && estado.matrix[i][j]!=estado.hidato_max){             
		                	 if(estado.matrix[i][j]+1==estado.matrix[n][m] ){
		                		 adyacenteOK++;
		                	 }
		                 }			
					}
				}
				if(adyacenteOK+1==estado.matrix.length*estado.matrix[0].length-adyacenteNull){
					System.out.println("fin: \n"+estado);
					return true;
				}
			}
		}
		return false;
	}

}



