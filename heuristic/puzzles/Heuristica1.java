package aima.core.environment.hidato;

import aima.core.search.framework.HeuristicFunction;


public class Heuristica1 implements HeuristicFunction{

	@Override
	public double h(Object state) {
		Estado estado = (Estado)state;
		
		/* cálculo de casillas vacías */
		int h_empty = 0;
		for (int i = 0; i < estado.matrix.length; i++) {
			for (int j = 0; j < estado.matrix[0].length; j++) {
				if(estado.matrix[i][j] != null
					&& estado.matrix[i][j]==0){
					h_empty++;
				}
			}
		}

		return h_empty;
	}

}
