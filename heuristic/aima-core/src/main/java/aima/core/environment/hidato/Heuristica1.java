package aima.core.environment.hidato;

import aima.core.search.framework.HeuristicFunction;

/**
 * Implementa una heuristica para el problema de los ascensores
 */
public class Heuristica1 implements HeuristicFunction{

	@Override
	public double h(Object state) {
		Estado estado = (Estado)state;
		
		int hvalue = 1;

		/*Calcular el valor de la heuristica para el estado y almacenarlo en hvalue */
 
		return hvalue;
	}

}
