package zadatak2;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Mreza extends Panel implements Runnable {
	Polje[][] matrica;
	private List<Tenk> tenkovi;
	private List<Novcic> novci;
	private Igra igra;
	private Igrac igrac;
	private int poeni = 0;
	private boolean radi = false;
	private boolean rezimRada = true; // true=Rezim igranja, false rezim editovanja
	private Label poencici;
	private int kolona, vrsta;
	private Button s;
	// private boolean rezimIzmena = false;
	private Thread nit = new Thread(this);

	public Mreza(int dim, Igra ig) {
		matrica = new Polje[dim][dim];
		kolona = vrsta = dim;
		igra = ig;
		tenkovi = new ArrayList<>();
		novci = new ArrayList<>();
		nit.start();

	}

	public void postLabelu(Label m) {
		poencici = m;
	}

	public Mreza(Igra i) {
		this(17, i);
	}

	@Override
	public void run() {
		try {
			if (rezimRada) {
				synchronized (this) {
					while (!radi)
						wait();
				}
				while (true) {
					if (rezimRada) {
						nit.sleep(40);
						azuriraj();
						azurirajLabelu();
						repaint();
					} else {
						nit.sleep(500);
						repaint();
					}
				}
			}
		} catch (InterruptedException e) {
		}

	}

	public synchronized void kreni() {
		radi = true;
		notify();
	}

	public synchronized boolean rezimRada() {
		return rezimRada;
	}

	public synchronized void pocni(int b) {
		if (rezimRada) {
			kreni();
			poeni = 0;
			novci.clear();
			tenkovi.clear();
			postIgraca();
			dodajNovce(b);
			dodajTenkice(b);
		}
	}

	public synchronized void stani() {
		radi = false;
	}

	private void azurirajLabelu() {
		poencici.setText("Poena: " + this.poeni);
	}

	private void azuriraj() {
		for (int i = 0; i < novci.size(); i++) {
			int[] koord1 = novci.get(i).polje.pozicija();
			int[] koord2 = igrac.polje.pozicija();
			if (koord1[0] == koord2[0] && koord2[1] == koord1[1]) {
				poeni++;
				novci.remove(novci.get(i));
			}
		}
		for (int i = 0; i < tenkovi.size(); i++) {
			int[] koord1 = tenkovi.get(i).polje.pozicija();
			int[] koord2 = igrac.polje.pozicija();
			if (koord1[0] == koord2[0] && koord2[1] == koord1[1]) {
				this.zaustavi();
				igrac.zabraniKretanje();
				igra.sat().pauza();
				igra.baciDijalog();
			}
		}
	}

	public int kolona() {
		return kolona;
	}

	public int vrsta() {
		return vrsta;
	}

	public Polje[][] matrica() {
		return matrica;
	}

	public void inicijalizuj(int br) {
		int sirinaPolja = getWidth() / kolona;
		int visinaPolja = getHeight() / kolona;
		int sirinaTable = sirinaPolja * kolona;
		int visinaTable = visinaPolja * kolona;
		setSize(new Dimension(sirinaTable, visinaTable));
		for (int i = 0; i < kolona; i++) {
			for (int j = 0; j < kolona; j++) {
				double broj = Math.random();
				if (broj < 0.8) {
					matrica[i][j] = new Trava(this);
				} else {
					matrica[i][j] = new Zid(this);
				}
				matrica[i][j].setSize(sirinaPolja, visinaPolja);
				matrica[i][j].setLocation(getX() + i * sirinaPolja, getY() + j * visinaPolja);
			}
		}
		if (rezimRada) {
			this.postIgraca();
			this.dodajTenkice(br);
			this.dodajNovce(br);
		}
	}

	private void iscrtajMatricu(Graphics g) {
		for (int i = 0; i < kolona; i++) {
			for (int j = 0; j < kolona; j++) {
				matrica[i][j].paint(g);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		if (rezimRada) {
			iscrtajMatricu(g);
			if (igrac != null) {
				igrac.crtaj(g);
			}
			for (int i = 0; i < tenkovi.size(); i++) {
				tenkovi.get(i).crtaj(this.getGraphics());
			}
			for (int i = 0; i < novci.size(); i++) {
				novci.get(i).crtaj(this.getGraphics());
			}
		} else {
			iscrtajMatricu(g);
		}

	}

	public void postRezim(boolean a) {
		if(!a) s.setEnabled(false);
		rezimRada = a;
	}

	private void postIgraca() {
		boolean postavio = false;
		while (true) {
			if (postavio == true)
				break;
			int x = (int) (Math.random() * kolona);
			int y = (int) (Math.random() * kolona);
			for (int i = 0; i < kolona; i++)
				for (int j = 0; j < kolona; j++) {
					if (x == i && y == j) {
						if (!(matrica[i][j] instanceof Zid)) {
							System.out.println("IGRAC JE KREIRAN SA DIMENZIJAMA: " + i + "," + j);
							igrac = new Igrac(matrica[i][j]);
							igrac.pomeriFiguru(matrica[i][j]);
							postavio = true;
							break;
						}
					}
				}
			if (postavio == true)
				break;
		}
	}

	public void pomeriIgraca(KeyEvent dog) {
		if (igrac.dozvoljenoKretanje() && rezimRada) {
			switch (dog.getKeyCode()) {
			case KeyEvent.VK_W:// pomeri na gore
				Polje sledece1 = igrac.polje.dohvati(0, -1);
				if (sledece1 != null && sledece1 instanceof Trava)
					igrac.pomeriFiguru(sledece1);

				break;
			case KeyEvent.VK_S: // pomeri na dole
				Polje sledece2 = igrac.polje.dohvati(0, 1);
				if (sledece2 != null && sledece2 instanceof Trava)
					igrac.pomeriFiguru(sledece2);
				break;
			case KeyEvent.VK_A: // pomeri na levo
				Polje sledece3 = igrac.polje.dohvati(-1, 0);
				if (sledece3 != null && sledece3 instanceof Trava)
					igrac.pomeriFiguru(sledece3);
				break;
			case KeyEvent.VK_D: // pomeri na desno
				Polje sledece4 = igrac.polje.dohvati(1, 0);
				if (sledece4 != null && sledece4 instanceof Trava)
					igrac.pomeriFiguru(sledece4);
				break;
			}
		}
	}

	private void dodajTenkice(int broj) {
		int brojTenkica = broj / 3;
		int postavljeno = 0;

		while (postavljeno < brojTenkica) {
			boolean postavio = false;
			while (!postavio) {
				int x = (int) (Math.random() * kolona);
				int y = (int) (Math.random() * kolona);
				if (matrica[x][y] instanceof Trava && igrac.polje != matrica[x][y]) {
					Tenk t = new Tenk(matrica[x][y]);
					System.out.println("NAPRAVLJEN TENK: " + x + "," + y);
					t.pokreni();
					postavio = true;
					tenkovi.add(t);
					break;
				}
			}
			postavljeno++;
		}
	}

	private void dodajNovce(int broj) {
		int postavljeno = 0;
		while (postavljeno < broj) {
			boolean postavio = false;
			while (!postavio) {
				int x = (int) (Math.random() * kolona);
				int y = (int) (Math.random() * kolona);
				if (matrica[x][y] instanceof Trava && igrac.polje != matrica[x][y]) {
					Novcic n = new Novcic(matrica[x][y]);
					System.out.println("NAPRAVLJEN Novcic: " + x + "," + y);
					postavio = true;
					novci.add(n);
					break;
				}
			}
			postavljeno++;
		}
	}

	public void zaustavi() {
		for (int i = 0; i < tenkovi.size(); i++) {
			tenkovi.get(i).zaustavi();
		}
		igra.dugme();
		// nit.interrupt();

	}

	public void interapt() {
		nit.interrupt();
	}

	public void pocetnainicijalizacija() {
		int sirinaPolja = getWidth() / kolona;
		int visinaPolja = getHeight() / kolona;
		int sirinaTable = sirinaPolja * kolona;
		int visinaTable = visinaPolja * kolona;
		setSize(new Dimension(sirinaTable, visinaTable));
		for (int i = 0; i < kolona; i++) {
			for (int j = 0; j < kolona; j++) {
				double broj = Math.random();
				if (broj < 0.8) {
					matrica[i][j] = new Trava(this);
				} else {
					matrica[i][j] = new Zid(this);
				}
				matrica[i][j].setSize(sirinaPolja, visinaPolja);
				matrica[i][j].setLocation(getX() + i * sirinaPolja, getY() + j * visinaPolja);
			}
		}
	}

	public void rezimIzmene(MouseEvent arg0) {
		if (!rezimRada) {
			int x = arg0.getX();
			int y = arg0.getY();
			Polje novo=null;
			for (int i = 0; i < vrsta; i++) {
				for (int j = 0; j < kolona; j++) {
					if (matrica[i][j].getX() + matrica[i][j].getHeight() >= x
							&& matrica[i][j].getY() + matrica[i][j].getWidth() >= y && matrica[i][j].getX() < x
							&& matrica[i][j].getY() < y) {
						System.out.println("UPAD: " + i + "," + j);
						Polje staro=matrica[i][j];
						int starix=matrica[i][j].getX();
						int stariy=matrica[i][j].getY();
						if(igra.odluka1().getState()) {
							novo=new Trava(this);
						}else novo=new Zid(this);
						matrica[i][j]=novo;
						matrica[i][j].setLocation(starix, stariy);
						matrica[i][j].setSize(staro.getWidth(), staro.getHeight());
						repaint();
						
					}
				}
			}
		}
	}

	public void dodajDugme(Button start) {
		s=start;
		
	}
}
