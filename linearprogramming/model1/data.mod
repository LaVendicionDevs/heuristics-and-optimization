data;
/* data  section */

set BUSES := bus1 bus2 bus3 bus4;
set ROUTES := route1 route2 route3 route4 route5 route6;


param Consumo_buses:=
bus1 40
bus2 50
bus3 35
bus4 45;

param Velocidad_media:=
bus1 100
bus2 90
bus3 80
bus4 90;

param Distancia_rutas:=
route1 500
route2 700
route3 550
route4 200
route5 300
route6 100;


end;