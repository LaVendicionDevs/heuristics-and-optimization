package aima.core.environment.hidato;

import aima.core.search.framework.HeuristicFunction;


public class Heuristica2 implements HeuristicFunction{

	@Override
	public double h(Object state) {
		Estado estado = (Estado)state;
		
		/* calcula casillas adyacentes nulas */
		
		int adyacenteNull=0;
		for(int i= 0; i<estado.matrix.length; i++){
			for (int j=0; j<estado.matrix[0].length; j++){
				if(estado.matrix[i][j]==null){
					adyacenteNull++;
				}
			}
		}
		/* calcula casillas adyacentes que cumplen las condiciones del juego */
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

			}
		}
		/* hidato_rank. fórmula explicada en la memoria */
		float accurate_heuristic = (1-((adyacenteOK+1)/(float)(estado.matrix.length*estado.matrix[0].length-adyacenteNull))*100);
		return accurate_heuristic;
	}

}
