package aima.core.environment.hidato;


import aima.core.agent.Action;
import aima.core.search.framework.ResultFunction;

/**
 * Clase que implementa la funcion que devuelve el estado resultante de aplicar una accion sobre un estado.
 */
public class ResultadoAccion implements ResultFunction{

	@Override
	public Object result(Object s, Action a) {
		Estado estado = (Estado) s; //Estado sobre el que se aplica la accion
		Accion accion = (Accion) a; //Accion a aplicar sobre el estado
		
		Estado sucesor = new Estado(estado);
		/* Realizar el cambio de estado sobre el estado 'sucesor' al ejecutar la accion 'a' 
                   en el estado anterior 's' */
		for (int i=0; i<estado.matrix.length; i++){
			for (int j=0; j<estado.matrix[i].length; j++){
				if(accion.x == i && accion.y == j){
					sucesor.matrix[i][j]=accion.valor;
					sucesor.actual[0]=accion.x;
					sucesor.actual[1]=accion.y;
				}else{
					sucesor.matrix[i][j]=estado.matrix[i][j];
				}
			}
		}

		return sucesor;
	}

}
