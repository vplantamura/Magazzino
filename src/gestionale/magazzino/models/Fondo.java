package gestionale.magazzino.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gestionale.magazzino.Querist;

public class Fondo {
	static Querist que;

	/**
	 * Questo metodo inserisce un nuovo fondo nel database
	 * @param nome nome del fondo
	 * @param fondoDisponibile l'importo ancora disponibile per il fondo
	 */
	public static void inserisciFondo(String nome,float fondoDisponibile){
		que = new Querist();
		String query = "INSERT INTO Fondo(nome,fondoDisponibile) VALUES"+
				"('"+nome+"',"+fondoDisponibile+")";
		que.eseguiQueryUpdate(query);
		}

	/**
	 * Questo metodo restituisce i nomi dei fondi presenti nel database aventi un fondo disponibile maggiore di 0
	 * @return Il ritorno � un arraylist di stringhe contenenti i risultati, in caso di errore ritorna l'arraylist con il messaggio di errore
	 */
	public static ArrayList<gestionale.magazzino.Fondo> visualizzaFondi (){
		que = new Querist();
		String query = "SELECT F.idFondo, F.Nome , F.fondoDisponibile " +
					   "FROM Fondo F " +
					   "GROUP BY (F.nome) " +
					   "HAVING F.fondoDisponibile > 0";
		ResultSet rs = que.eseguiQuery(query);
		ArrayList<gestionale.magazzino.Fondo> risultato = new ArrayList<gestionale.magazzino.Fondo>();
		
		try {
			while(rs.next()){
				gestionale.magazzino.Fondo fnd = new gestionale.magazzino.Fondo(rs.getInt("idFondo"), rs.getString("nome"), rs.getFloat("fondoDisponibile"));
				risultato.add(fnd);
			}
		} catch (SQLException e) {
			risultato.add(null);
			return risultato;
		}
		return risultato; 
	}

	/**
	 * Questo metodo restituisce l'ID del fondo voluto
	 * @param nomeFondo nome del fondo di cui si vuole l'identificatore
	 * @return l'indice del fondo voluto
	 */
	public static int getIdFondo (String nomeFondo){
		que = new Querist();
		int id = 0;
		String query = "SELECT F.idFondo " +
					   "FROM Fondo F " +
					   "WHERE F.nome = '"+nomeFondo+"'";
		System.out.println(query);
		ResultSet rs = que.eseguiQuery(query);
		try {
			id = rs.getInt(1);
		} catch (SQLException e) {
			id = -1;
		}
		return id;
	}
	
	/**
	 * Restituisce un fondo in base al suo nome.
	 * @param nome il nome del fondo
	 * @return Restituisce il fondo in base al suo nome.
	 */
	public static gestionale.magazzino.Fondo visualizzaFondo(String nome){
		que = new Querist();
		String query = "SELECT * " +
					   "FROM Fondo F " +
					   "WHERE F.nome = '"+nome+"'";
		ResultSet rs = que.eseguiQuery(query);
		System.out.println(query);
		gestionale.magazzino.Fondo fondo;
		try{
			fondo = new gestionale.magazzino.Fondo(rs.getInt("idFondo"), rs.getString("nome"), rs.getFloat("fondoDisponibile"));
		}catch(SQLException e){
			fondo = null;
		}
		
		return fondo;
	}
	
	/**
	 * Restituisce un fondo in base al suo id.
	 * @param idFondo l'id del fondo
	 * @return Restituisce il fondo in base al suo id.
	 */
	public static gestionale.magazzino.Fondo visualizzaFondo(int idFondo){
		que = new Querist();
		String query = "SELECT * " +
					   "FROM Fondo F " +
					   "WHERE F.idFondo = '"+idFondo+"'";
		ResultSet rs = que.eseguiQuery(query);
		System.out.println(query);
		gestionale.magazzino.Fondo fondo;
		try{
			fondo = new gestionale.magazzino.Fondo(rs.getInt("idFondo"), rs.getString("nome"), rs.getFloat("fondoDisponibile"));
		}catch(SQLException e){
			fondo = null;
		}
		
		return fondo;
	}
	
	public static void cancellaFondo(String nome){
		que = new Querist();
		String query = "DELETE " +
				       "FROM Fondo " +
				       "WHERE Fondo.nome = '"+nome+"'";
		System.out.println(query);
		que.eseguiQueryUpdate(query);
	}
	
	public static void reindexTable(){
		que = new Querist();
		String query = "REINDEX 'Fondo'";
		que.eseguiQueryUpdate(query);
	}
	
	/**
	 * Questo metodo permette di cambiare il prezzo di un fondo
	 * @param idFondo identificativo del fondo
	 * @param importo nuovo importo
	 */
	public static void cambiaImporto (int idFondo, float importo){
		que = new Querist();
		String query = "UPDATE Fondo " +
					   "SET fondoDisponibile = " +importo+" "+
					   "WHERE idFondo = "+idFondo;
		System.out.println(query);
		que.eseguiQueryUpdate(query);
	}
}
