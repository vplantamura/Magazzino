package gestionale.magazzino.models;

import static org.junit.Assert.*;

import java.util.ArrayList;

import gestionale.magazzino.Connettore;
import gestionale.magazzino.models.utils.Utils;
import gestionale.magazzino.utils.RandomString;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test per la classe Fondo.
 * @author <a href="https://github.com/DavidePastore">Davide Pastore</a>
 *
 */
public class FondoTest {
	
	private static gestionale.magazzino.Fondo fondo;
	private static ArrayList<gestionale.magazzino.Fondo> fondi;
	//private static Connettore connettore;
	private static RandomString randomString;
	
	private static float importo;
	private int idFondo;
	private String nome;
	
	private int dimensione;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		importo = 2.321f;
		fondi = new ArrayList<gestionale.magazzino.Fondo>();
		randomString = new RandomString(12);
		
		Connettore.caricadriver();
		Connettore.collegati();
	}
	
	@Before
	public void setUp() throws Exception {
		nome = randomString.nextString();
	}

	/**
	 * Verifica che l'elemento inserito corrisponda con quello letto successivamente.
	 */
	@Test
	public void testInserisciFondo() {
		Fondo.inserisciFondo(nome, importo);
		idFondo = Utils.lastInsertID();
		fondo = Fondo.visualizzaFondo(nome);
		assertTrue("inserisciFondo() non funziona correttamente", 
				fondo.getNome().equals(nome) &&
				fondo.getImporto() == importo &&
				fondo.getId_Fondo() == idFondo
		);
	}

	/**
	 * Verifica che la dimensione aumenti di uno quando si aggiunge un fondo.
	 */
	@Test
	public void testVisualizzaFondi() {
		fondi = Fondo.visualizzaFondi();
		dimensione = fondi.size();
		nome = randomString.nextString();
		
		Fondo.inserisciFondo(nome, importo);
		
		fondi = Fondo.visualizzaFondi();
		assertEquals("visualizzaFondi() non funziona correttamente", dimensione + 1, fondi.size());
	}

	/**
	 * Verifica che un fondo sia leggibile dopo un inserimento.
	 */
	@Test
	public void testGetIdFondo() {
		Fondo.inserisciFondo(nome, importo);
		idFondo = Utils.lastInsertID();
		fondo = Fondo.visualizzaFondo(nome);
		assertTrue("getIdFondo() non funziona correttamente", fondo.getId_Fondo() == idFondo);
	}

	/**
	 * Verifica che un fondo sia leggibile dopo un inserimento.
	 */
	@Test
	public void testVisualizzaFondo() {
		Fondo.inserisciFondo(nome, importo);
		idFondo = Utils.lastInsertID();
		fondo = Fondo.visualizzaFondo(nome);
		assertTrue("visualizzaFondo() non funziona correttamente", 
				fondo.getNome().equals(nome) &&
				fondo.getImporto() == importo &&
				fondo.getId_Fondo() == idFondo
		);
	}
	
	/**
	 * Verifica che un fondo sia leggibile dopo un inserimento.
	 */
	@Test
	public void testVisualizzaFondoInt() {
		Fondo.inserisciFondo(nome, importo);
		idFondo = Utils.lastInsertID();
		fondo = Fondo.visualizzaFondo(idFondo);
		assertTrue("visualizzaFondoInt() non funziona correttamente", 
				fondo.getNome().equals(nome) &&
				fondo.getImporto() == importo &&
				fondo.getId_Fondo() == idFondo
		);
	}

	/**
	 * Verifica che l'elemento inserito venga cancellato.
	 */
	@Test
	public void testCancellaFondo() {
		Fondo.inserisciFondo(nome, importo);
		idFondo = Utils.lastInsertID();
		Fondo.cancellaFondo(nome);
		assertNull("cancellaFondo() non funziona correttamente", Fondo.visualizzaFondo(nome));
	}
	
	/**
	 * Test method for {@link gestionale.magazzino.models.Fondo#reindexTable()}.
	 */
	@Test
	public void testReindexTable() {
		fail("Not yet implemented");
	}
	
	/**
	 * Verifica che l'importo venga cambiato dopo la sua modifica.
	 */
	@Test
	public void testCambiaImporto() {
		float nuovoImporto = 15.678f;
		Fondo.inserisciFondo(nome, importo);
		idFondo = Utils.lastInsertID();
		
		Fondo.cambiaImporto(idFondo, nuovoImporto);
		fondo = Fondo.visualizzaFondo(idFondo);
		assertTrue("cambiaImporto() non funziona correttamente", 
				fondo.getNome().equals(nome) &&
				fondo.getImporto() == nuovoImporto &&
				fondo.getId_Fondo() == idFondo
		);
	}
	
	

}
