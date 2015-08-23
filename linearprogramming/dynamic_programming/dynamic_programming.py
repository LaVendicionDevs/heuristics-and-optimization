#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sys


'''
- Algoritmo mediante la técnica de programación dinámica
- Alberto Lara, Fco. Javier Honduvilla
- Probado en CPython 2.7.4 y en pypy 2.2.1
'''
def bus(data, vol_limit, weight_limit):
	
	# construimos la matrix donde se harán los cálculos bottom-up
	matrix = [[[0 for k in xrange(vol_limit + 1)] 
						for j in xrange(weight_limit + 1)] 
							for i in xrange(len(data) + 1)]

	
	for i in xrange(1, len(data) + 1):
		item, weight, value, volume = data[i-1]
		for j in xrange(1, weight_limit + 1):
			for k in xrange(1, vol_limit + 1):
				if weight > j or volume > k:
					matrix[i][j][k] = matrix[i-1][j][k]
				else:
					matrix[i][j][k] = max(
						matrix[i-1][j][k], 
						matrix[i-1][j-weight][k-volume] + value, 
						matrix[i-1][j-weight][k]             + value)
						
	results = []
	t_weight = weight_limit
	t_value = vol_limit
	for j in range(len(data), 0, -1):
		if matrix[j][t_weight][vol_limit] != matrix[j-1][t_weight][vol_limit]:
			item, weight, value, volume = data[j-1]
			results.append(data[j-1])
			t_weight -= weight
			vol_limit -= volume
	return results
 
if __name__ == '__main__':
	#TODO(javier): comprobar más entradas y cosos varios
	if len(sys.argv) == 1:
		print 'Introduce un nombre de archivo.'
	# parser stúpido. no time...
	def parse(ha):
		try:
			return int(ha)
		except ValueError:
			return str(ha)

	# id, valor, peso, volumen
	'''data = [
		(1, 3, 2, 5), 
		(2, 1, 5, 8), 
		(3, 50, 1, 2), 
		(4, 14, 12, 32),
	]'''
	raw_file = open(sys.argv[1]).read()
	data = [map(parse, line.split(' ')) for line in raw_file.split('\n')]

	
	MAX_VOLUME, MAX_WEIGHT = 24, 2900
	result = bus(data, MAX_VOLUME, MAX_WEIGHT)


	print '''{dinero_total}
{id_de_paquetes}'''.format(
		dinero_total=sum([el[1] for el in result]), 
		id_de_paquetes=' '.join([el[0] for el in result])), 
