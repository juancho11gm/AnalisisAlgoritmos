package co.edu.javeriana.algoritmos.proyecto.mbls;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Tablero;


public class TableroJuego implements Tablero {

	private  int matriz[][];
	
	private int FILMAX;
	private int COLMAX;
	private int NCOL;


	
	public TableroJuego(int matriz[][], int fil, int col, int ncol ) {
		this.matriz = matriz;
		this.FILMAX = fil;
		this.COLMAX = col;
		this.NCOL = ncol;
		
	}

	
	
	public int getFilas() {
		return FILMAX;
	}



	public void setFILMAX(int fILMAX) {
		FILMAX = fILMAX;
	}



	public int getColumnas() {
		return COLMAX;
	}



	public void setCOLMAX(int cOLMAX) {
		COLMAX = cOLMAX;
	}



	public int getNumeroColores() {
		return NCOL;
	}



	public void setNCOL(int nCOL) {
		NCOL = nCOL;
	}



	public int[][] coloresTablero() {
		return matriz;
	}


	public void setMatriz(int[][] matriz) {
		this.matriz = matriz;
	}


	



	public void cambiarXY(int x, int y, int i) {
		matriz[x][y] = i;
		
	}



	public void correrIzquierda(int j) {
			
			for(j=j; j<COLMAX-1; j++){
				for(int i = 0; i < FILMAX; i++){
					matriz[i][j]= matriz[i][j+1];
					matriz[i][j+1]=-1;
					
				}
			}
			

	}




	public void correrAbajo(int i, int j) {
		
		
		matriz[i][j]= matriz[i+1][j];
		matriz[i+1][j]= -1;
	}



	public int efectuarJugada(Casilla jugada) throws IllegalArgumentException {
		int color = matriz[jugada.getFila()][jugada.getColumna()];
		int puntuacion = 0;
		int n = actualizarTablero(jugada.getFila(),jugada.getColumna(),color, 0);
		
		correrAbajo();
		correrIzquierda();
		if(JugadoSol.esVacio(matriz, FILMAX, COLMAX)){
			
			puntuacion = (int) Math.pow((FILMAX*COLMAX), 2.0);
			
		}else{
			puntuacion = (int) Math.pow((n-2), 2.0);
		}
		return puntuacion;
	}

	private void correrAbajo() {
		//Main2.imprimirTablero(tablero.getMatriz(), tablero.getFILMAX(), tablero.getCOLMAX());
		boolean bandera = true;
		while(bandera){
			
			bandera = false;
			for(int j = 0; j < COLMAX; j++){
				
				for (int i = 0; i <FILMAX-1; i++) {
					
					if(matriz[i][j]==-1&&matriz[i+1][j]!=-1){
						
						correrAbajo(i,j);
						bandera = true;
					}
				}
				
			}
		}
		
		
		
		
	}

	private void correrIzquierda() {
		boolean correr= false;
		for(int j = 0; j < COLMAX; j++){
			correr = true;
			for(int i = 0; i < FILMAX; i++){
				
				if(matriz[i][j]!=-1){
					correr = false;
					break;
				}
			}
			if(correr){
				correrIzquierda(j);
			}
		}
		
	}

	private int actualizarTablero(int x, int y, int color, int n) {
		
		
		if(matriz[x][y]!=-1){
			cambiarXY(x,y,-1);
			n++;
		}
		
		
		if(x!=0){
			if(matriz[x-1][y] != -1 && color == matriz[x-1][y]){
				n = actualizarTablero(x-1, y, color,n);
			}
		}
		if(x+1<FILMAX){
			if(matriz[x+1][y] != -1 && color == matriz[x+1][y]){
				n = actualizarTablero(x+1, y, color,n);
			}
		}
		if(y>0){
			if(matriz[x][y-1] != -1 && color == matriz[x][y-1]){
				n = actualizarTablero(x, y-1, color,n);
			}
		}
		if(y+1<COLMAX){
			if(matriz[x][y+1] != -1 && color == matriz[x][y+1]){
				n =  actualizarTablero(x, y+1, color,n);
			}
		}
		
		return n;
		
	}



	@Override
	public int colorCasilla(int i, int j) {
		
		return matriz[FILMAX-i][j];
	}



	@Override
	public int simularJugada(Casilla jugada) throws IllegalArgumentException {
		int color = matriz[jugada.getFila()][jugada.getColumna()];
		int puntuacion = 0;
		int [][]matrizAux = this.matriz;
		int n = actualizarTablero(jugada.getFila(),jugada.getColumna(),color, 0);
		
		correrAbajo();
		correrIzquierda();
		if(JugadoSol.esVacio(matrizAux, FILMAX, COLMAX)){
			
			puntuacion = (int) Math.pow((FILMAX*COLMAX), 2.0);
			
		}else{
			puntuacion = (int) Math.pow((n-2), 2.0);
		}
		this.matriz = matrizAux;
		return puntuacion;
	}




	


	

}
