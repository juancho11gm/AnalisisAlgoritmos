package co.edu.javeriana.algoritmos.proyecto.mbls;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Jugador;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class JugadoSol implements Jugador {

	private List<JugadaAux> jugadasG;
	private long MAXT = 4455;
	
	
	private int FILMAX = 15;
	private int COLMAX = 15;
	private int NCOLORES = 5;

	public JugadoSol() {
		
		
			
		int [][] tableroM= new int[FILMAX][COLMAX];
		for(int i=0;i<FILMAX;i++) {
			for(int j=0;j<COLMAX;j++) {
				tableroM[i][j]=(int) (Math.random()*NCOLORES);
			}
		}
		
		//imprimirTablero(tablero, COLMAX, FILMAX);
		Tablero t = new TableroJuego(tableroM, FILMAX, COLMAX, NCOLORES);
		long tiempo =  System.currentTimeMillis();
		List<Casilla> jugadas = jugar(t);
		long finalT = System.currentTimeMillis()-tiempo;

		
		for (int i = 0; i < jugadasG.size(); i++) {
			imprimirTablero(jugadasG.get(i).getTablero().coloresTablero(), jugadasG.get(i).getTablero().getColumnas(), jugadasG.get(i).getTablero().getFilas());
			System.out.println("X: "+(jugadas.get(i).getFila()) + " Y: "+jugadas.get(i).getColumna());
			
		}
		System.out.println("PuntuacionJ: "+jugadasG.get(jugadasG.size()-1).getPuntuacionTotal());
		System.out.println("Tiempo total: "+finalT);
		
	}

	public List<Casilla> jugar(Tablero tablero) {

		Tablero t =  tablero;
		List<JugadaAux> jugadas = solucionarTablero(t);


		jugadas.remove(0);
		jugadasG = jugadas;

		List<Casilla> respuesta = new ArrayList<Casilla>();
		for (JugadaAux jugadaAux : jugadas) {
			Casilla j = new Casilla(jugadaAux.getX(), jugadaAux.getY());
			respuesta.add(j);
			//imprimirTablero(jugadaAux.getTablero().getMatriz(), tablero.getColumnas(), tablero.getFilas());
		}

		return respuesta;
	}
	private List<JugadaAux> solucionarTablero(Tablero t) {

		JugadaAux j = new JugadaAux(t, -1, -1, 0);
		List<JugadaAux> jugadas = new ArrayList<JugadaAux>();
		jugadas.add(j);
		long tiempo =  System.currentTimeMillis();
		if(t.getFilas()<=50&&t.getColumnas()<=50){
			return anchuraPreferente(j, jugadas, tiempo);
		}else{
			return monteCasino(j, jugadas, tiempo);
		}
		
	}
	
	private List<JugadaAux> anchuraPreferente(JugadaAux j, List<JugadaAux> jugadas, long tiempo) {
		long auxT = System.currentTimeMillis()-tiempo;
		if(!(auxT<MAXT)){
			return jugadas;
		}
		if(esVacio(j.getTablero().coloresTablero(), j.getTablero().getFilas(),  j.getTablero().getColumnas())){

			jugadas.add(j);

			return jugadas;

		}
		if(j.getX()!=-1 && j.getY() !=-1){

			jugadas.add(j);
		}

		
		Queue<JugadaAux> cola;
		List<JugadaAux> jugadasMax = jugadas;
		//System.out.println(jugadasMax.get(jugadasMax.size()-1).getPuntuacionTotal());
		if((auxT<MAXT)){
			cola = expandir(j, tiempo);

		}
		else{
			return jugadas;
		}



		while(!cola.isEmpty()&&auxT<MAXT){
			JugadaAux siguienteJugada = cola.poll();


			//System.out.println(auxT);

			List<JugadaAux> jugadasAux = anchuraPreferente(siguienteJugada, new ArrayList<JugadaAux>(jugadas), tiempo);
			if(jugadasAux.get(jugadasAux.size()-1).getPuntuacionTotal()>=jugadasMax.get(jugadasMax.size()-1).getPuntuacionTotal()){
				//System.out.println("entro");
				jugadasMax= jugadasAux;
			}
			auxT =  System.currentTimeMillis()-tiempo;
		}

		return jugadasMax;





	}
	
	 
	private List<JugadaAux> monteCasino(JugadaAux j, List<JugadaAux> jugadas, long tiempo) {
		long auxT = System.currentTimeMillis()-tiempo;
		if(!(auxT<MAXT)){
			return jugadas;
		}
		if(esVacio(j.getTablero().coloresTablero(), j.getTablero().getFilas(),  j.getTablero().getColumnas())){

			jugadas.add(j);

			return jugadas;

		}
		if(j.getX()!=-1 && j.getY() !=-1){

			jugadas.add(j);
		}

		
		JugadaAux siguienteJugada; 
		
		List<JugadaAux> jugadasMax = jugadas;
		//System.out.println(jugadasMax.get(jugadasMax.size()-1).getPuntuacionTotal());
		if((auxT<MAXT)){
			siguienteJugada  = expandirUna(j, tiempo);

		}
		else{
			return jugadas;
		}



		if(siguienteJugada!=null){
			


			//System.out.println(auxT);

			List<JugadaAux> jugadasAux = monteCasino(siguienteJugada, new ArrayList<JugadaAux>(jugadas), tiempo);
			if(jugadasAux.get(jugadasAux.size()-1).getPuntuacionTotal()>=jugadasMax.get(jugadasMax.size()-1).getPuntuacionTotal()){
				//System.out.println("entro");
				jugadasMax= jugadasAux;
			}
			
			
			Queue<JugadaAux> cola;
			

			if((auxT<MAXT)){
				cola = expandir(j, tiempo);

			}
			else{
				return jugadasMax;
			}



			while(!cola.isEmpty()&&auxT<MAXT){
				siguienteJugada = cola.poll();


				//System.out.println(auxT);
				
				jugadasAux = monteCasino(siguienteJugada, new ArrayList<JugadaAux>(jugadas), tiempo);
				if(jugadasAux.get(jugadasAux.size()-1).getPuntuacionTotal()>=jugadasMax.get(jugadasMax.size()-1).getPuntuacionTotal()){
					//System.out.println("entro");
					jugadasMax= jugadasAux;
				}
				auxT =  System.currentTimeMillis()-tiempo;
			}
			
		
		}else{
			return jugadasMax;
		}

		return jugadasMax;





	}
	
	private Queue<JugadaAux> expandir(JugadaAux jugada, long tiempo) {
		Queue<JugadaAux> jugadas = new PriorityQueue<JugadaAux>();
		Tablero t = jugada.getTablero();



		boolean casillasAfectadas [][] = new boolean[t.getFilas()][t.getColumnas()];
		for(int i = 0; i <t.getFilas(); i++ ){
			for (int j = 0; j < t.getColumnas(); j++) {

				if(t.coloresTablero()[i][j]==-1){
					casillasAfectadas[i][j] = true;
				}else{
					casillasAfectadas[i][j] = false;
				}

			}
		}
		int i = 0, j = 0;
		long auxT = System.currentTimeMillis()-tiempo;
		while(i<t.getFilas()&&auxT<MAXT){
			j=0;
			while(j<t.getColumnas()&&auxT<MAXT){
				auxT = System.currentTimeMillis()-tiempo;
				if(casillasAfectadas[i][j]==false){

					if(revisarSiHayJugada(t, i, j, casillasAfectadas, t.coloresTablero()[i][j])&&auxT<MAXT){

						casillasAfectadas = afectarCasillas(t, i,j, casillasAfectadas, t.coloresTablero()[i][j]);
						int matriz[][] = new int[t.getFilas()][t.getColumnas()];
						for(int x = 0; x <t.getFilas(); x++ ){
							for (int y = 0; y < t.getColumnas(); y++) {

								matriz[x][y]= t.coloresTablero()[x][y];

							}
						}
						int filx= t.getFilas();
						TableroJuego nuevoT = new TableroJuego(matriz,filx, t.getColumnas(),t.getNumeroColores());



						JugadaAux nuevaj = new JugadaAux(nuevoT,i,j, jugada.getPuntuacionTotal(),0); 
						//imprimirTablero(nuevoT.getMatriz(), nuevoT.getFILMAX(), nuevoT.getCOLMAX());

						jugadas.add(nuevaj);
					}


				}
				j++;
			}
			i++;

		}


		return jugadas;
	}
	private JugadaAux expandirUna(JugadaAux jugada, long tiempo) {

		Tablero t = jugada.getTablero();



		boolean casillasAfectadas [][] = new boolean[t.getFilas()][t.getColumnas()];
		for(int i = 0; i <t.getFilas(); i++ ){
			for (int j = 0; j < t.getColumnas(); j++) {

				if(t.coloresTablero()[i][j]==-1){
					casillasAfectadas[i][j] = true;
				}else{
					casillasAfectadas[i][j] = false;
				}

			}
		}
		int i = 0, j = 0;
		long auxT = System.currentTimeMillis()-tiempo;
		while(i<t.getFilas()&&auxT<MAXT){
			j=0;
			while(j<t.getColumnas()&&auxT<MAXT){
				auxT = System.currentTimeMillis()-tiempo;
				if(casillasAfectadas[i][j]==false){

					if(revisarSiHayJugada(t, i, j, casillasAfectadas, t.coloresTablero()[i][j])&&auxT<MAXT){

						casillasAfectadas = afectarCasillas(t, i,j, casillasAfectadas, t.coloresTablero()[i][j]);
						int matriz[][] = new int[t.getFilas()][t.getColumnas()];
						for(int x = 0; x <t.getFilas(); x++ ){
							for (int y = 0; y < t.getColumnas(); y++) {

								matriz[x][y]= t.coloresTablero()[x][y];

							}
						}
						TableroJuego nuevoT = new TableroJuego(matriz,jugada.getTablero().getFilas(), jugada.getTablero().getColumnas(),jugada.getTablero().getNumeroColores());



						JugadaAux nuevaj = new JugadaAux(nuevoT,i,j, jugada.getPuntuacionTotal(),0); 
						//imprimirTablero(nuevoT.getMatriz(), nuevoT.getFILMAX(), nuevoT.getCOLMAX());

						return nuevaj;
					}


				}
				j++;
			}
			i++;

		}


		return null;
	}
	private boolean[][] afectarCasillas(Tablero t, int x, int y, boolean[][] casillasAfectadas, int color) {

		casillasAfectadas[x][y] = true;

		if(x!=0){
			if(casillasAfectadas[x-1][y] == false && color == t.coloresTablero()[x-1][y]){
				casillasAfectadas = afectarCasillas(t, x-1, y, casillasAfectadas, color);
			}
		}
		if(x+1<t.getFilas()){
			if(casillasAfectadas[x+1][y] == false && color == t.coloresTablero()[x+1][y]){
				casillasAfectadas = afectarCasillas(t, x+1, y, casillasAfectadas, color);
			}
		}
		if(y>0){
			if(casillasAfectadas[x][y-1] == false && color == t.coloresTablero()[x][y-1]){
				casillasAfectadas = afectarCasillas(t, x, y-1, casillasAfectadas, color);
			}
		}
		if(y+1<t.getColumnas()){
			if(casillasAfectadas[x][y+1] == false&& color == t.coloresTablero()[x][y+1]){
				casillasAfectadas = afectarCasillas(t, x, y+1, casillasAfectadas, color);
			}
		}

		return casillasAfectadas;
	}

	private boolean revisarSiHayJugada(Tablero t, int x, int y, boolean casillasAfectadas [][], int color) {



		if(x!=0){
			if(casillasAfectadas[x-1][y] == false && color == t.coloresTablero()[x-1][y]){
				return true;
			}
		}
		if(x+1<t.getFilas()){
			if(casillasAfectadas[x+1][y] == false && color == t.coloresTablero()[x+1][y]){
				return true;
			}
		}
		if(y>0){
			if(casillasAfectadas[x][y-1] == false && color == t.coloresTablero()[x][y-1]){
				return true;
			}
		}
		if(y+1<t.getColumnas()){
			if(casillasAfectadas[x][y+1] == false&& color == t.coloresTablero()[x][y+1]){
				return true;
			}
		}

		return false;

	}
	public static void imprimirTablero(int tablero[][], int COLMAX, int FILMAX) {
		System.out.println();
		System.out.print("  ");
		for(int k=0;k<COLMAX;k++){

			System.out.print(" "+k+" ");
		}
		System.out.println();
		for(int i=FILMAX-1;i>=0;i--) {
			for(int j=0;j<COLMAX;j++) {
				if(j==0) {
					System.out.print(i+" ");
				}
				if(tablero[i][j]!=-1) {
					System.out.print(" "+tablero[i][j]+" ");
				}else {
					System.out.print("   ");
				}
			}
			System.out.println("");
		}
	}
	
	public static void main(String[] args) {
		JugadoSol main = new JugadoSol();

	}
	public  static boolean esVacio(int [][] matriz,int FILMAX, int COLMAX) {
		for(int i = FILMAX-1; i>0; i--){
			for(int j = 0; j < COLMAX; j++ ){
				if(matriz[i][j]!=-1){
					return false;
				}
			}
		}
		
		return true;
	}
	@Override
	public String identificacionJugador() {
		// TODO Auto-generated method stub
		return new String("MSLS111");
	}
}
