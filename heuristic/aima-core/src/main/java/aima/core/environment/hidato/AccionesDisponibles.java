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
 * Clase que implementa la funcion que devuelve las acciones disponibles en un estado dado.
 */
public class AccionesDisponibles  implements ActionsFunction {
	Map<Integer, Integer> map;
	public Set<Action> actions(Object state) {
		Estado estado = (Estado)state;
		Set<Action> acciones = new LinkedHashSet<Action>();
		ArrayList<int[]> arrayAux = new ArrayList<int[]>();
		int[] a;
		/*Las nuevas aplicables dado un estado deben añadirse al conjunto acciones
		 creando instancias de Accion y agregandolas con acciones.add() */
		int i=estado.actual[0];
		int j=estado.actual[1];
		 for (int n=i-1; n<=i+1; n++){ 
			 for (int m=j-1; m<=j+1; m++){ 
				 if(!(n<0 || m<0 || n>=estado.matrix.length || m>=estado.matrix[0].length) 
						 && estado.matrix[n][m] != null 
						 && (i!=n || j!=m)
						 ){ 
					 a =  new int[3];
					 a[0]=estado.matrix[n][m];
					 a[1]=n;
					 a[2]=m;
					 arrayAux.add(a);
				 }
			 }
		 }
		 
		 /*for (int k=0; k<arrayAux.size();k++){
			 System.out.println("valor:"+arrayAux.get(k)[0]+" pos:"+arrayAux.get(k)[1]+" "+arrayAux.get(k)[2]);
		 }*/
		 int[] check = new int[2];
		 check = is_duplicate(arrayAux, estado.matrix[i][j]+1);
		 //System.out.println(check[0]+" "+check[1]);
		 if(check[0]==-1){
			 for (int k=0; k<arrayAux.size();k++){
				 if(arrayAux.get(k)[0]==0){
					 //System.out.println("valor:"+arrayAux.get(k)[0]+" pos:"+arrayAux.get(k)[1]+" "+arrayAux.get(k)[2]);
					 acciones.add(new Accion("escribir", arrayAux.get(k)[1], arrayAux.get(k)[2], estado.matrix[i][j]+1));
				 }
			 }
		 }else{
			 acciones.add(new Accion("escribir", check[0], check[1], estado.matrix[check[0]][check[1]]));
		 }
		 
			
		return acciones;
	}

	public int[] is_duplicate(ArrayList<int[]> lista, Integer nuevo_valor){
		map = new HashMap<Integer, Integer>();
		map.put(nuevo_valor, 1);
		int[] a = new int[2];
		for (int i = 0; i < lista.size(); i++) {
			int el = lista.get(i)[0];
			if(map.containsKey(el)){
				a[0]=lista.get(i)[1];
				a[1]=lista.get(i)[2];
				return a;
			}
			if(el!=0){
				map.put(el, 1);
			}
		}
		a[0]=-1;
		a[1]=-1;
		return a;
	}
	
}
	
