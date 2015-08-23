package aima.core.environment.hidato;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import aima.core.agent.Action;
import aima.core.environment.hidato.Accion;
import aima.core.search.framework.ActionsFunction;
import java.util.LinkedHashSet;


/**
 * Clase que implementa la funcion que devuelve las acciones disponibles en un estado dado
 */
public class AccionesDisponibles  implements ActionsFunction {
	/* hashtable para el metodo is_duplicate */
	Map<Integer, Integer> map; 
	public Set<Action> actions(Object state) {
		/* estado sobre el que se comprueban las acciones disponibles */
		Estado estado = (Estado)state; 
		/* conjunto de acciones donde vamos a meter las acciones disponibles */
		Set<Action> acciones = new LinkedHashSet<Action>(); 
		/* array auxiliar para almacenar las casillas adyacentes a la posición central sobre las que podemos escribir */
		ArrayList<int[]> arrayAux = new ArrayList<int[]>(); 
		/* cada posicion del arrayAux contiene 3 enteros. En 0 valor, en 1 posicionX y en 2 posicionY */
		int[] a; 
		
		/* cargamos actual, que es la ultima posicion escrita en la matriz */
		int i=estado.actual[0]; 
		int j=estado.actual[1];
		for (int n=i-1; n<=i+1; n++){ /* Recorrer todos los adyacentes a la posicion actual */
			for (int m=j-1; m<=j+1; m++){ 
				if(!(n<0 || m<0 || n>=estado.matrix.length || m>=estado.matrix[0].length) /* precondicion de dentro de los limites de la matriz */
						&& estado.matrix[n][m] != null /* precondicion que la posicion adyacente no sea nula */
						&& (i!=n || j!=m) /* no metemos en la lista la posicion central */
						){ 
					a =  new int[3];
					a[0]=estado.matrix[n][m]; /* valor de la casilla adyacente */
					a[1]=n; /* posicion en filas de la casilla adyacente */
					a[2]=m; /* posicion en columnas de la casilla adyacente */
					arrayAux.add(a); /* metemos al arrayAux todas las adyacentes que cumplen las condiciones */
				}
			}
		}
		
		/* con check y el metodo is_duplicate comprobamos que ninguna de las posiciones adyacentes contenga ya el valor actual + 1 */
		int[] check = new int[2];
		check = is_duplicate(arrayAux, estado.matrix[i][j]+1);

		if(check[0]==-1){ /* si is_duplicate devuelve -1, ninguna de las posiciones adyacentes contenia al siguiente valor a escribir */
			for (int k=0; k<arrayAux.size();k++){
				if(arrayAux.get(k)[0]==0){
					/* incluimos como acciones todas las casillas adyacentes que cumplen las precondiciones y con el valor a escribir=valorActual+1 */
					acciones.add(new Accion("escribir", arrayAux.get(k)[1], arrayAux.get(k)[2], estado.matrix[i][j]+1));
				}
			}
		}else{
			/* si is_duplicate encuentra el valor a escribir en una posición adyacente devuelve la casilla en la que esta y le dejamos el valor que ya estaba escrito */
			acciones.add(new Accion("escribir", check[0], check[1], estado.matrix[check[0]][check[1]]));
		}
		 
			
		return acciones;
	}

	public int[] is_duplicate(ArrayList<int[]> lista, Integer nuevo_valor){
		/* limpiar el map con cada llamada a is_duplicate */
		map = new HashMap<Integer, Integer>(); 
		map.put(nuevo_valor, 1);
		/* variable para devolver resultados a check */
		int[] a = new int[2]; 
		for (int i = 0; i < lista.size(); i++) {
			/* cogemos el valor de todas las posiciones del arrayAux */
			int el = lista.get(i)[0];
			/* si se encuentra el valor esperado en la hashtable devuelve la posicion en la que lo ha encontrado */
			if(map.containsKey(el)){ 
				a[0]=lista.get(i)[1];
				a[1]=lista.get(i)[2];
				return a;
			}
			/* las casillas con valor 0 no se deben comprobar en el map porque son las vacias */
			if(el!=0){ 
				map.put(el, 1);
			}
		}
		/* si no se encuentra el valor esperado en el map devuelve -1 */
		a[0]=-1;
		a[1]=-1;
		return a;
	}
	
}
	
