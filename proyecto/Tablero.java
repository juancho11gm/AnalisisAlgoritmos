/**
 * 
 */
package co.edu.javeriana.algoritmos.proyecto;

/**
 * La implementación que se utilizará dentro del robot la suministraré yo, pero es buena idea
 * que ustedes generen la suya para probar.
 *
 */
public interface Tablero extends Cloneable {

    /**
     * Efectúa una jugada sobre el tablero.
     * 
     * @param jugada La jugada a realizar
     * @return puntaje de la jugada
     * @throws IllegalArgumentException si la jugada no es válida
     */
    int efectuarJugada( Casilla jugada ) throws IllegalArgumentException;
    
	/**
	 * Simula una jugada sobre el tablero sin cambiarlo.
     * 
     * @param jugada La jugada a simular
     * @return puntaje de la jugada
     * @throws IllegalArgumentException si la jugada no es válida
	*/
	int simularJugada( Casilla jugada ) throws IllegalArgumentException;
	
    int getNumeroColores();
    
    int getFilas();
    
    int getColumnas();
	
	int colorCasilla( int i, int j );
	
	int[][] coloresTablero();
    
}
