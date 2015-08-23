#!/usr/bin/env python
# -*- coding: utf-8 -*-
import itertools


'''
- Algoritmo por fuerza bruta
- Alberto Lara, Fco. Javier Honduvilla
- Probado en CPython 2.7.4 y en pypy 2.2.1
'''

''' Objetos disponibles id, valor, peso y volumen '''
# TODO(javier): Modificar entrada para leer fichero con datos
data = [
    (1, 10, 3, 4)
    (2, 20, 8, 6),
    (3, 15, 2, 2),
    (4, 1, 20, 7),
    (5, 2, 2, 0),
    (6, 21, 9, 34),
    (7, 1, 2, 1),
    (8, 2, 9, 5),
    (9, 10, 3, 9),
    (10, 20, 8, 1),
    (11, 15, 2, 8),
    (12, 1, 20, 1),
    (13, 2, 2, 2),
    (14, 21, 9, 11),
    (15, 1, 2, 1),
    (16, 2, 9, 6),
    (17, 10, 3, 8)
]

combinations = []
MAX_WEIGHT = 15  # Constraint de peso
MAX_VOLUME = 14  # Constraint de volumen

for i in xrange(1, len(data)+1):  # Precomputamos las combinaciones posibles
    combinations += list(itertools.combinations(data, i))

possible_combinations = []
for combination in combinations:
    acc_weight = 0
    acc_volume = 0
    for element in combination:
        acc_weight += element[2]
        acc_volume += element[3]
    if acc_weight <= MAX_WEIGHT and acc_volume <= MAX_VOLUME:  # Constraints
        possible_combinations.append(combination)

value_table = {}  # Tabla de valor => objetos
for possible in possible_combinations:
    cost = 0
    for combination in possible:
        cost += combination[1]
    value_table[cost] = possible

max_value = max(value_table)
maximized = value_table[max_value]  # Función de maximización
print '''*Restricciones*:
    Peso    => {peso}
    Volumen => {volumen}

El conjunto de objetos óptimos es:
{objetos_optimos}

Valor óptimo        => {valor_optimo}
Peso alcanzado      => {peso_alcanzado}
Volumen alcanzado   => {volumen_alcanzado}'''.format(
    peso=MAX_WEIGHT,
    volumen=MAX_VOLUME,
    objetos_optimos=maximized,
    valor_optimo=max_value,
    peso_alcanzado=sum([el[2] for el in maximized]),
    volumen_alcanzado=sum([el[3] for el in maximized]))
