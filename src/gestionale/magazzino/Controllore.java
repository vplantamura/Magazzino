package gestionale.magazzino;

import gestionale.magazzino.grafica.cancelleria.GraficaAccount;
import gestionale.magazzino.grafica.cancelleria.GraficaCarrello;
import gestionale.magazzino.grafica.cancelleria.GraficaDipendente;
import gestionale.magazzino.grafica.cancelleria.GraficaLogin;
import gestionale.magazzino.grafica.cancelleria.GraficaProdotti;
import gestionale.magazzino.grafica.cancelleria.GraficaRegistrazione;
import gestionale.magazzino.grafica.cancelleria.ModificaProdotto;
import gestionale.magazzino.grafica.cancelleria.MyModel;
import gestionale.magazzino.grafica.cancelleria.VisualizzaProdotto;
import gestionale.magazzino.grafica.responsabile.GraficaAccountResponsabile;
import gestionale.magazzino.grafica.responsabile.GraficaMagazzino;
import gestionale.magazzino.grafica.responsabile.GraficaResponsabile;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class Controllore {
	
	
	
	/** 
	 * Controlla campo vuoto, lunghezza e uguaglianza con la seconda password in fase di registrazione
	 * @return il numero dell'errore, in modo d poterlo gestire singolarmente
	 * @return 1 se la password � vuota
	 * @return 2 se la password � maggiore di 12
	 * @return 3 se le password non sono uguali
	 */
	public boolean checkPassword(String pass, String pass2){
		int noErr = 0;
		boolean b = false;
		if(pass.isEmpty()){
			noErr = 1;
			b = false;
		}else if(pass.length() >12){
			noErr = 2;
			b = false;
		}else if(!pass.equals(pass2)){
			noErr = 3;
			b = false;
		}else if(pass.equals(pass2)){
			b = true;
		}
		
		return b;
	}
	
	/**
	 * Controlla la validit� sintattica della email
	 * @param email l'email da prendere in esame
	 * @return true o false in base alla validit� della formattazione
	 */
	public boolean validateSintassiEmail(String email){
		EmailValidator ev = new EmailValidator();
		boolean emailValida = ev.validate(email);
		
		return emailValida;
	}

	/////////////////////////////////////////////////////////////////////////////////
	// metodi pendy
	
	private GraficaLogin gl;
	private GraficaDipendente gd;
	private GraficaRegistrazione gr;
	private GraficaResponsabile gresp;
	private GraficaProdotti gp;
	private GraficaAccount ga;
	private VisualizzaProdotto vp;
	private ModificaProdotto mp;
	private MyModel modello;
	private MyModel modello2;
	private GraficaCarrello gc;
	private int Prodotto_selezionato = 0;
	private int Ordine_selezionato = 0;
	private ArrayList<Prodotto> prodotti;
	private ArrayList<Prodotto> carrello;
	private GraficaAccountResponsabile gar;
	private GraficaMagazzino gm;
	/**
	 * Costruttore controllore
	 * inizializza tutte le finestre grafiche,senza pero caricarne i componenti
	 */
	public Controllore()
	{
		modello = new MyModel();
		modello2 = new MyModel();
		gar = new GraficaAccountResponsabile();
		gm = new GraficaMagazzino();
		gresp = new GraficaResponsabile();
		gl = new GraficaLogin();
		gp = new GraficaProdotti();
		gr = new GraficaRegistrazione();
		gd = new GraficaDipendente();
		ga = new GraficaAccount();
		vp = new VisualizzaProdotto();
		gc = new GraficaCarrello();
		mp = new ModificaProdotto();
	}
	
	/**
	 * mostra la finestra iniziale del programma
	 */
	public void start()
	{
		gl.init();
	}
	
	/**
	 * raccoglie i dati dai campi di login e controlla se l'utente puo effettuare l'accesso interrogando il database.
	 * in caso negativo mostra gli errori commessi dall'utente nel inserire le credenziali
	 * in caso positivo mostra l'avvenuto accesso al sistema e mostra la finestra del catalogo
	 */
	public void isConnected()
	{
		gl.pulisciErrori();
		String email = gl.getEmail();
		String password = gl.getPassword();
		boolean b1 = gestionale.magazzino.models.Dipendente.validateEmail(email);
		boolean b2 = gestionale.magazzino.models.Dipendente.validatePassword(email, password);
		boolean b3 = gestionale.magazzino.models.Dipendente.validateResponsabile(email, password);
		if(b2)
		{
			if(b3)
			{
				JOptionPane.showMessageDialog(gl, "Login Effetuato");
				gresp.init();
				gl.disposeF();
			}
			else
			{
				JOptionPane.showMessageDialog(gl, "Login Effetuato");
				gd.init();
				gl.disposeF();
			}

		}
		else
		{
			if(!b1 && !b2)
			{
				gl.setErroreEmail("Email errata");
				gl.setErrorePass("Password errata");
			}
			if(!b1)
			{
				gl.setErroreEmail("Email errata");
			}
			else
			{
				gl.setErrorePass("Password errata");
			}
		}
		
	}
	
	/**
	 * disalloca le componenti grafiche alla disconnessione dell'utente
	 */
	public void disconnect()
	{
		gl.disposeF();
	}
	
	/**
	 * mostra la finestra grafica di registrazione
	 */
	public void registering()
	{
		gr.init();
		gl.disposeF();
	}
	
	/**
	 * mostra la finestra grafica di login dopo che l'utente si � registrato 
	 */
	public void logging()
	{
		gl.init();
		gr.disposeF();
	}
	
	/**
	 * pulisce i campi della finestra di registrazione e gli eventuali errori
	 */
	public void resetRegistrazione()
	{
		gr.pulisciErrori();
		gr.pulisci();
	}
	
	/**
	 * controlla se i campi inseriti dall'utente al momento della registrazione sono corretti.
	 * in caso negativo mostra quali campi sono errati
	 * in caso positivo invia i dati al database e notifica l'utente dell'avvenuta registrazione
	 */
	public void registered()
	{
		gr.pulisciErrori();
		boolean b = false;
		String nome = gr.getNome();
		String cognome = gr.getCognome();
		String email = gr.getEmail();
		String password = gr.getPassword();
		String password2 = gr.getPassword2();
		b = this.validateSintassiEmail(email);
		if(b)
		{
			b = checkPassword(password, password2);
			if(!b)
			{
				gr.setErrorePassword2("Le password non coincidono");
				b = false;
			}
			if(nome.isEmpty())
			{
				gr.setErroreNome("Nome non valido");
				b = false;
			}
			if(cognome.isEmpty())
			{
				gr.setErroreCognome("Cognome non valid");
				b = false;
			}
			if(b)
			{
				b = gestionale.magazzino.models.Dipendente.validateEmail(email);
				if(!b)
				{
					this.isRegistered(nome,cognome,password,email);
					JOptionPane.showMessageDialog(gr, "Registrazione effetuata");
					gl.init();
					gr.disposeF();
					
				}
				else
				{
					b = false;
					gr.setErroreEmail("Email gia presente nell'archivio");
				}
			}
		}
		else 
		{
			b = false;
			gr.setErroreEmail("Email non valida");
			b = checkPassword(password, password2);
			if(!b)
			{
				gr.setErrorePassword2("Le password non coincidono");
				b = false;
			}
			if(nome.isEmpty())
			{
				gr.setErroreNome("Nome non valido");
				b = false;
			}
			if(cognome.isEmpty())
			{
				gr.setErroreCognome("Cognome non valid");
				b = false;
			}
		}	
		
		
	}
	
	/**
	 * controlla se l'utente puo effettuare la registrazione oppure se � gia registrato al sistema
	 * 
	 * @param nome
	 * @param cognome
	 * @param password
	 * @param email
	 */
	public void isRegistered(String nome,String cognome,String password,String email)
	{
		String tipo = "dipendente";
		gestionale.magazzino.models.Dipendente.inserisciDipendente(nome, cognome, password, email, tipo);
	}
	
	/**
	 * mostra la tabella dell'account con i dati dell'utente
	 */
	
	//////////////////// implementare query per ricevere i dati dal singolo utente
	public void showAccount()
	{
		String email;
		String nome;
		String cognome;
		String tipo;
		gestionale.magazzino.models.Dipendente dipendente;
		gd.setPannelloSelezionato("account");
	}

	/**
	 * inizializza il catalogo prendendo i dati dal database e caricandoni in un modello astratto per una tabella
	 */
	public void initCatalogo()
	{
		int ID;
		String nome;
		int qta;
		float prezzo;
		prodotti = new ArrayList<gestionale.magazzino.Prodotto>();
		prodotti = gestionale.magazzino.models.Prodotto.visualizzaProdotti();
		String[] colonne = {"ID","nome","prezzo","quantita","acquista"};
		MyModel model = new MyModel(prodotti.size(),5,colonne);
		for(int i = 0;i < prodotti.size(); i++)
		{
			ID = prodotti.get(i).getId_Prodotto();
			nome = prodotti.get(i).getNome();
			qta = prodotti.get(i).getQuantit�();
			prezzo = prodotti.get(i).getPrezzo();
			model.setValueAt(ID, i, 0);
			model.setValueAt(nome, i, 1);
			model.setValueAt(qta, i, 2);
			model.setValueAt(prezzo, i, 3);
			model.setValueAt(Boolean.FALSE, i, 4);
		}
		modello = model;
	}
	
	/**
	 * funzione che restituisce il numero di record presenti nel modello della tabella
	 * @return
	 */
	public int getRowCount()
	{
		return modello.getRowCount();
	}
	/**
	 * funzione che restituisce il numero di campi presenti nel modello della tabella
	 * @return
	 */
	public int getColumnCount()
	{
		return modello.getColumnCount();
	}
	/**
	 * funzione che restituisce il modello della tabella
	 * @return
	 */
	public AbstractTableModel getCatalogo()
	{
		return modello;
	}
	/**
	 * funzione che restituisce il nome dei campi del modello della tabella
	 * @return
	 */
	public String[] getColumnNames()
	{
		String[] s = new String[5];
		for(int i = 0;i <5;i++)
		{
			s[i] = modello.getColumnName(i);
		}
		return s;
	}
	/**
	 * riabilita la finestra dipendente in caso fosse stata disabilitata
	 * mostra la tabella del catalogo
	 */
	public void showCatalogo()
	{
		gd.setState(true);
		gd.setPannelloSelezionato("prodotti");
	}
	/**
	 * reinizializza la finestra del catalogo (da ottimizzare)
	 */
	public void updateCatalogo()
	{
		gd.setState(true);
		gd.setPannelloSelezionato("prodotti");
	}
	/**
	 * riabilita la finestra dipendente in caso fosse stata disabilitata
	 * mostra la tabella del carrello
	 */
	public void showCarrello()
	{
		gd.setState(true);
		gd.setPannelloSelezionato("carrello");
	}
	/**
	 * reinizializza la finestra del carrello (da ottimizzare)
	 */
	public void updateCarrello()
	{
		gd.setState(true);
		gd.setPannelloSelezionato("carrello");
	}
	/**
	 * restituisce il modello della tabella
	 * @return
	 */
	public AbstractTableModel getCarrrello()
	{
		return modello2;
	}
	/**
	 * carica dal database i prodotti scelti da un utente
	 */
	////////// modificare la query per la raccolta dei dati
	public void initCarrello()
	{
		int ID;
		String nome;
		int qta;
		float prezzo;
		carrello = new ArrayList<Prodotto>();
		//prodotti = modelsCancelleria.Prodotto.visualizzaProdotti();
		String[] colonne = {"ID","nome","prezzo","quantita","acquista"};
		MyModel model = new MyModel(carrello.size(),5,colonne);
		for(int i = 0;i < carrello.size(); i++)
		{
			ID = prodotti.get(i).getId_Prodotto();
			nome = prodotti.get(i).getNome();
			qta = prodotti.get(i).getQuantit�();
			prezzo = prodotti.get(i).getPrezzo();
			model.setValueAt(ID, i, 0);
			model.setValueAt(nome, i, 1);
			model.setValueAt(qta, i, 2);
			model.setValueAt(prezzo, i, 3);
			model.setValueAt(Boolean.FALSE, i, 4);
		}
		modello2 = model;
	}
	/**
	 * disalloca tutte le risorse create all'accesso di un dipendente al sistema
	 */
	public void disposeDipendente()
	{
		gl.dispose();
		gp.dispose();
		gr.dispose();
		vp.dispose();
		mp.dispose();
		gd.disposeF();

	}
	/**
	 * effettua il logout dall'account del dipendente mostrando di nuovo la finestra del login
	 */
	public void doLogout()
	{
		gd.disposeF();
		this.disposeDipendente();
		gl.init();
	}
	
	/**
	 * visualizza il prodotto selezionato dal catalogo
	 * @param p
	 */
	// inserire query di controllo su ID prodotto
	public void showProdotto(int p)
	{
		vp.init();
		ArrayList<Fondo> fondi = new ArrayList<Fondo>();
		fondi = gestionale.magazzino.models.Fondo.visualizzaFondi();
		vp.setFondi(fondi);
		Prodotto_selezionato = p;
		gd.setState(false);
	}
	
	/**
	 * visualizza il prodotto selezionato dagli ordini
	 * @param p
	 */
	// inserire query di controllo su ID prodotto
	public void showOrdinato(int p)
	{
		mp.init();
		Ordine_selezionato = p;
		gd.setState(false);
	}
	
	
	public void logoutResp()
	{
		gresp.disposeF();
		this.disposeResp();
		gl.init();
	}
	
	public void disposeResp()
	{
		
		gm.dispose();
		gl.dispose();
		gp.dispose();
		gr.dispose();
		vp.dispose();
		mp.dispose();
		gresp.disposeF();
	}
	
	public void showAccountResp()
	{
		gresp.setPannelloSelezionato("account");
	}
	
	public void showMagazzino()
	{
		gresp.setPannelloSelezionato("magazzino");
	}
	
	public void showNotifiche()
	{
		gresp.setPannelloSelezionato("notifiche");
	}
	
	public void showListaDip()
	{
		gresp.setPannelloSelezionato("listaDip");
	}
}