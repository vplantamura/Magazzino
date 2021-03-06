package gestionale.magazzino.models;

import static org.junit.Assert.*;
import gestionale.magazzino.Connettore;
import gestionale.magazzino.models.utils.Utils;
import gestionale.magazzino.utils.RandomString;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author <a reef="https://github.com/DavidePastore">DavidePastore</a>
 *
 */
public class AcquistoTest {
	
	private static int idDipendente;
	private static int idProdotto;
	private static int idFondo;
	private static int qta;
	private int idAcquisto;
	private gestionale.magazzino.Acquisto acquisto;
	private static ArrayList<gestionale.magazzino.Acquisto> acquisti;
	private int dimensione;
	private static RandomString randomString;

	/**
	 * Setup del test.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		idDipendente = 3;
		idProdotto = 2;
		idFondo = 1;
		qta = 120;
		acquisti = new ArrayList<gestionale.magazzino.Acquisto>();
		randomString = new RandomString(10);
		Connettore.caricadriver();
		Connettore.collegati();
	}

	/**
	 * Verifica che l'elemento inserito corrisponda con quello letto successivamente.
	 */
	@Test
	public void testInserisciAcquisto() {
		Acquisto.inserisciAcquisto(idDipendente, idProdotto, idFondo, qta); //Inserisce l'acquisto
		idAcquisto = Utils.lastInsertID();
		acquisto = Acquisto.visualizzaAcquisto(idAcquisto);
		assertTrue("inserisciAcquisto() non funziona correttamente", 
			acquisto.getIdAcquisto() == idAcquisto &&
			acquisto.getIdDipendente() == idDipendente &&
			acquisto.getIdProdotto() == idProdotto &&
			acquisto.getIdFondo() == idFondo &&
			acquisto.getQta() == qta
		);
	}

	/**
	 * Verifica che l'elemento inserito venga cancellato.
	 */
	@Test
	public void testCancellaAcquisto() {
		Acquisto.inserisciAcquisto(idDipendente, idProdotto, idFondo, qta); //Inserisce l'acquisto
		idAcquisto = Utils.lastInsertID();
		Acquisto.cancellaAcquisto(idAcquisto);
		assertNull("cancellaAcquisto() non funziona correttamente", Acquisto.visualizzaAcquisto(idAcquisto));
	}

	/**
	 * Verifica che la dimensione aumenti di uno quando si aggiunge un acquisto.
	 */
	@Test
	public void testVisualizzaAcquisti() {
		acquisti = Acquisto.visualizzaAcquisti();
		dimensione = acquisti.size();
		Acquisto.inserisciAcquisto(idDipendente, idProdotto, idFondo, qta); //Inserisce l'acquisto
		
		acquisti = Acquisto.visualizzaAcquisti();
		assertEquals("visualizzaAcquisti() non funziona correttamente", dimensione + 1, acquisti.size());
	}

	/**
	 * Verifica che la dimensione aumenti di uno quando si aggiunge un acquisto di un dipendente.
	 * Test method for {@link gestionale.magazzino.models.Acquisto#visualizzaAcquistiDaDipendente(int)}.
	 */
	@Test
	public void testVisualizzaAcquistiDaDipendente() {
		acquisti = Acquisto.visualizzaAcquistiDaDipendente(idDipendente);
		dimensione = acquisti.size();
		Acquisto.inserisciAcquisto(idDipendente, idProdotto, idFondo, qta); //Inserisce l'acquisto
		
		acquisti = null;
		acquisti = Acquisto.visualizzaAcquistiDaDipendente(idDipendente);
		assertEquals("visualizzaAcquistiDaDipendente() non funziona correttamente", dimensione + 1, acquisti.size());
	}

	/**
	 * Verifica che l'elemento inserito corrisponda con quello letto successivamente.
	 */
	@Test
	public void testVisualizzaAcquistoInt() {
		Acquisto.inserisciAcquisto(idDipendente, idProdotto, idFondo, qta); //Inserisce l'acquisto
		idAcquisto = Utils.lastInsertID();
		acquisto = Acquisto.visualizzaAcquisto(idAcquisto);
		assertTrue("visualizzaAcquistoInt() non funziona correttamente", 
			acquisto.getIdAcquisto() == idAcquisto &&
			acquisto.getIdDipendente() == idDipendente &&
			acquisto.getIdProdotto() == idProdotto &&
			acquisto.getIdFondo() == idFondo &&
			acquisto.getQta() == qta
		);
	}

	/**
	 * Verifica che l'elemento inserito corrisponda con quello letto successivamente.
	 */
	@Test
	public void testVisualizzaAcquistoString() {
		String nome = randomString.nextString();
		gestionale.magazzino.Prodotto prodotto;
		Prodotto.inserisciProdotto(nome, qta, 21.4f);
		idProdotto = Utils.lastInsertID();
		
		Acquisto.inserisciAcquisto(idDipendente, idProdotto, idFondo, qta); //Inserisce l'acquisto
		idAcquisto = Utils.lastInsertID();
		acquisto = Acquisto.visualizzaAcquisto(idAcquisto);
		prodotto = Prodotto.visualizzaProdotto(acquisto.getIdProdotto());
		assertEquals("visualizzaAcquistoString() non funziona correttamente", 
			prodotto.getNome(),
			Prodotto.visualizzaProdotto(idProdotto).getNome()
		);
	}

	/**
	 * Test method for {@link gestionale.magazzino.models.Acquisto#reindexTable()}.
	 */
	@Test
	public void testReindexTable() {
		fail("Not yet implemented");
	}

}
