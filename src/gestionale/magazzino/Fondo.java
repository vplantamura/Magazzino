package gestionale.magazzino;

/**
 * @author Matteo Cal�
 * Questa classe descrive il fondo di investimenti
 */
public class Fondo {
	private int idFondo;
	private String nome;
	private float importo;
	
	/**
	 * @param id_Fondo Identificativo del fondo
	 * @param nome Nome del fondo
	 * @param importo Importo disponibile sul fondo in quel momento
	 */
	public Fondo(int id_Fondo, String nome, float importo) {
		super();
		idFondo = id_Fondo;
		this.nome = nome;
		this.importo = importo;
	}

	

	public Fondo() {
		// TODO Auto-generated constructor stub
	}



	/**
	 * Metodi Get e Set
	 */
	public int getId_Fondo() {
		return idFondo;
	}

	public void setId_Fondo(int id_Fondo) {
		idFondo = id_Fondo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getImporto() {
		return importo;
	}

	public void setImporto(float importo) {
		this.importo = importo;
	}
	
	
}
