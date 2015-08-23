package aima.core.environment.hidato;

import aima.core.agent.impl.DynamicAction;



/**
 * La clase accion representa las acciones del dominio.
 * Pueden incluirse atributos y/o metodos auxiliares para poder 
   representar las acciones o bien utilizar el nombre de la accion.
 */
public class Accion extends DynamicAction{

	public String nombre;
	public int x;
	public int y;
	public int valor;

	/**
	 * Crea la accion a partir de su nombre
	 */

	public Accion(String name, int x, int y, int valor) {
		super(name);
		this.x = x;
		this.y = y;
		this.valor = valor;
		this.nombre = name;
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + valor;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Accion other = (Accion) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (valor != other.valor)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
