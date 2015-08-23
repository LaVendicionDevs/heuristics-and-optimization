set BUSES;
set ROUTES;


/* parameters */
param Consumo_buses 	{i in BUSES};
param Velocidad_media 	{i in BUSES};
param Distancia_rutas 	{i in ROUTES};

/* decision variables */
var matrix {i in BUSES, j in ROUTES} binary;


/* objective function */
minimize Costs: 	
	sum{i in BUSES, j in ROUTES} ((matrix[i,j]*Distancia_rutas[j]/Velocidad_media[i])*20 + 
								 (matrix[i,j]*Distancia_rutas[j]*Consumo_buses[i])/100);


/* Constraints */
s.t. check_route 	{j in ROUTES}: sum{i in BUSES} matrix[i,j] == 1;
s.t. routes_limit 	{i in BUSES}: sum{j in ROUTES} matrix[i,j] <= 2;
s.t. hours_limit 	{i in BUSES}: sum{j in ROUTES} (matrix[i,j] * Distancia_rutas[j] / Velocidad_media[i]) <= 8;
s.t. half_salary    {i in BUSES, i2 in BUSES}: sum{j in ROUTES} (matrix[i,j] * Distancia_rutas[j] / Velocidad_media[i] * 2) >= 
						 sum{j in ROUTES} (matrix[i2,j] * Distancia_rutas[j] / Velocidad_media[i2]);

end;