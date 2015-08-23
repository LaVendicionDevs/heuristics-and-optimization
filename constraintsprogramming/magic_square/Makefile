all: compile execute

compile:
	@javac -classpath .:jacop-4.1.0.jar MagicSquare.java
	
execute:
	@time java -classpath .:jacop-4.1.0.jar MagicSquare ${n}
