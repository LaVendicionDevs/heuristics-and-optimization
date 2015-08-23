set BUSES;
set ROUTES;
set TASKS;


/* parameters */
param Consumo_buses 	{i in BUSES};
param Velocidad_media 	{i in BUSES};
param Distancia_rutas 	{i in ROUTES};
param Coste_rutas		{i in ROUTES};

/* decision variables */
var matrix {i in BUSES, j in ROUTES} binary;
var tasks_matrix {i in BUSES, k in TASKS} binary;
var tickets {i in BUSES} binary;

/* objective function */
minimize Costs: 	
	sum{i in BUSES, j in ROUTES} ((matrix[i,j]*Distancia_rutas[j]/Velocidad_media[i])*20 + /* Sueldo */
								 (matrix[i,j]*Distancia_rutas[j]*Consumo_buses[i])/100) /* Combustible */

	+ sum{i in BUSES, k in TASKS} (tasks_matrix[i,k] * 20) /* Sueldo por tareas */
	+ sum{i in BUSES} (tickets[i] * 2); /* Precio de tickets */



/* Constraints */
s.t. check_route 	{j in ROUTES}: sum{i in BUSES} matrix[i,j] == 1; /*Comprueba que la ruta se ha realizado*/

/*s.t. routes_limit 	{i in BUSES}: sum{j in ROUTES} matrix[i,j] <= 2; Esta restriccion se ha eliminado despues de comprobar que es redundante al incluir la restriccion del coste de rutas*/

s.t. routes_cost_limit 	{i in BUSES}: sum{j in ROUTES} (matrix[i,j] * Coste_rutas[j]) <= 2; /*Comprueba que un autobus que haga la ruta 1 no pueda hacer mas y que el max de rutas por autobus sea 2*/

s.t. tasks_completion 	{k in TASKS}: sum{i in BUSES} tasks_matrix[i,k] == 1; /*Comprueba que todas las tareas se realicen*/

s.t. or_with_constraints1 {i in BUSES}: sum{k in TASKS} tasks_matrix[i,k] >= tickets[i]; /*estas dos restricciones forman el OR */
s.t. or_with_constraints2 {i in BUSES}: sum{k in TASKS} tasks_matrix[i,k] <= tickets[i]*5;


s.t. hours_limit 	{i in BUSES}: sum{j in ROUTES} (matrix[i,j] * Distancia_rutas[j] / Velocidad_media[i]) + 
						sum{k in TASKS} (tasks_matrix[i,k]) <= 8; /*comprueba que no se trabaje mas de 8 horas, las horas trabajadas en las tareas tambien influyen*/

s.t. half_salary    {i in BUSES, i2 in BUSES}: sum{j in ROUTES} ( (matrix[i,j] * Distancia_rutas[j] / Velocidad_media[i])
						+ sum{k in TASKS} (tasks_matrix[i,k]) )* 2
						>= sum{j in ROUTES} (matrix[i2,j] * Distancia_rutas[j] / Velocidad_media[i2])
						+ sum{k in TASKS} (tasks_matrix[i2,k]); /*comprueba que nadie cobre menos que la mitad que otro, el dinero ganado por trabajar en tareas tambien influye*/

end;
