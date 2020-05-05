# Algorithm analysis

## SAME GAME

![image](https://user-images.githubusercontent.com/36536646/81089097-8c2fda00-8ec1-11ea-91fc-d1cad433064d.png)

Para el desarrollo de los métodos planteados en la primera entrega del proyecto se planteó la posibilidad de utilizar dos funcionalidades que permiten la búsqueda de jugadas en los tableros del juego. Principalmente son los algoritmos de **BestFirst** y **anchuraPreferente**:

En el primer algoritmo cada nodo tendrá una cola de prioridad, donde el puntaje mayor va estará en primer lugar.
Pseudocódigo para la mejor jugada: 

```java
BestFirst (Grafo g, nodo inicio)
    //Se crea una cola de prioridad 
       Queue colaPrioridad<nodo>;
    //Se inserta el nodo inicio en la cola de prioridad
       colaPrioridad.add(inicio);
    mientras !colaPrioridad.empty()
          v = colaPrioridad.DeleteMin
          si v es el objetivo
             retornar
          si no
             por cada vecino h de vForeach neighbor v of u
                If v "NoVisitado"
                    Marcar v "Visitado"                    
                    colaPrioridad.insert(h);
             Marcar h "Examinado"                    
retornar;
```

En el peor caso la complejidad para el algoritmo Best First es O(n*Log n) donde n será el número de nodos del grafo. En este caso habría que visitar todos los nodos antes de alcanzar el mejor caso. Al agregar la cola de prioridad de máximos la complejidad mejora a O(log n) en cuanto a tiempo y O(n) en cuanto a espacio.


Cada una de las jugadas tendrá asociado un tablero.

```java
Jugada expandir(Jugada jugada)
	tablero = jugada.tablero()
	//se crea una matriz de booleanos del tamaño del tablero
	boolean casillasAfectadas [maxI][maxJ];
	i=0,j=0;
mientras i<tablero.maxI
		j=tablero.columnasIndice[i]	
		mientras j<tablero.maxJ
			 si no es casillasAfectadas[i][j]
			 casillasAfectadas=revisarSiHayJugada(tablero,i,j)
				si casillasAfectadas[i][j]
					nuevaJugada = crearJugada(jugada, i, j)
					jugada.agregar(nuevaJugada)
i++
	
Devolver nodo
```

```java
Tablero actualizarTablero(Tablero t,casillasAfectadas, i, j)
f=t.maxI
c=0
mientras f>0
	mientras c<t.maxJ
//buscamos casillas afectadas desde abajo
		si casillasAfectadas[f][c] es true
//buscamos hacia arriba hasta donde se acaban las casillas afectadas
			aux=f
			mientras aux>0 o casillasAfectadas[aux][c] = true 
			aux—
//si no se eliminan todas las casillas de la columna, se corre toda la columna por encimas de las afectadas hacia abajo
		si aux!= 0
			aux2=0
			mientras f-aux2>=0
//si las casillas que hay que bajar se acaban, se coloca la casilla vacía
			si f-aux2<aux
			  t[f-aux2][c]=vacia
			si no
			  t[f-aux2][c]=t[aux-aux2]
			aux2—
//si la columna se eliminó completamente, se corre lo de la derecha a la izquierda.
		si no
//se elimina la columna c, corriendo todo a la izquierda.
			t=moverALaIzquierda(t,c)
retornar t
```
```java
tablero moverALaIzquierda(tablero t,c)
mientras c< t.maxJ
	i=0
	mientras i<t.maxI
	  si c< t.maxJ-1
	    t[i][c]=t[i][c+1]
	  si no
	    t[i][c]=vacia
	  i++
	c++
retornar t
```

Tamaño de entrada: Sea n el número de casillas
Operaciones Básicas: crear una jugada 
En el peor de los casos el tablero será de 50x50 y se tienen sólo pares de colores que se pueden seleccionar. La complejidad es O(n).

```java
List<Jugada> anchuraPreferente (Jugada jugada, List<Jugada>jugadas, tiempo)
	//jugada es con el tablero limpio
	Si jugada.tableroVacio
		Retornar jugadas
	//inserta la jugada en la lista ordenada descendente
Jugadas.insertar (jugada) 
//crea las posibles jugadas para una jugada en una cola de prioridad
expandirJugadas(jugada)
	
mientras no esté vacía la cola de prioridad o el tiempo > 4,5s
//revisa cual camino es mejor por anchura
	si anchuraPreferente(jugada.jugadas.frente, jugadas) es mayor que jugadas
//almacena el mejor camino
		jugadas = anchuraPreferente(jugada.jugadas.frente, jugadas, tiempo)
	jugada.jugadas.eliminarFrente
	
retornar jugadas
```

Analisis:
Tamaño de entrada: tamaño del tablero
Operación Básica: comparación
En el peor de los casos, revisa toda las jugadas que crea la función expandirJugadas. Con esto, la complejidad es  exponencial, O(n^(n/2)) siendo n el tamaño del tablero, Pero como tenemos una limitante de tiempo, el algoritmo debe retornar la lista de mejor score que haya explorado hasta el momento. 

```java
List<Jugada> solucionarTablero(Tablero t)
	//crea la jugada inicial
	Jugada j = nueva Jugada(t, score= 0)
	Tiempo = 0
	Retornar anchuraPreferente(j, nueva List<Jugada>, tiempo)
```
Es una fachada para iniciar a resolver el tablero, por lo tanto tiene la complejidad de la función anchuraPreferente.


