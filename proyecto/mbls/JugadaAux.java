package co.edu.javeriana.algoritmos.proyecto.mbls;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Tablero;


public class JugadaAux implements Comparable<JugadaAux> {
	
	private Tablero tablero;
	private float puntuacionTotal;
	private int fila;
	private int columna;
	private Casilla jugada;

	public JugadaAux(Tablero t, float puntuacion, int x, int y) {
		this.fila = x;
		this.columna = y;
		this.tablero = t;
		
		
	}
	
	public JugadaAux(Tablero t, int x, int y, float puntuacionPadre, int aux) {
		this.fila = x;
		this.columna = y;
		this.tablero = t;
		
		this.jugada = new Casilla(x, y);
		this.puntuacionTotal = tablero.efectuarJugada(jugada)+puntuacionPadre;
		
		
		
	}

	

	

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(TableroJuego tablero) {
		this.tablero = tablero;
	}

	public float getPuntuacionTotal() {
		return puntuacionTotal;
	}

	public void setPuntuacionTotal(float puntuacionTotal) {
		this.puntuacionTotal = puntuacionTotal;
	}

	public int getX() {
		return fila;
	}

	public void setX(int x) {
		this.fila = x;
	}

	public int getY() {
		return columna;
	}

	public void setY(int y) {
		this.columna = y;
	}

	
	public int compareTo(JugadaAux j) {
		 if (puntuacionTotal < j.getPuntuacionTotal()) {
	            return 1;
	        } else if (puntuacionTotal > j.getPuntuacionTotal()) {
	            return -1;
	        } else {
	            return 0;
	        }
	}

	public Casilla getJugada() {
		return jugada;
	}

	public void setJugada(Casilla jugada) {
		this.jugada = jugada;
	}
	
}
