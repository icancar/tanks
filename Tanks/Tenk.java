package zadatak2;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {
	private Thread nit;
	private boolean radi = false;

	public Tenk(Polje p) {
		super(p);
		nit = new Thread(this);
		nit.start();
	}
	public void zaustavi() {
		nit.interrupt();
	}

	public synchronized void pokreni() {
		radi = true;
		notify();
	}

	private synchronized void pomeri() {
		Polje staroPolje=this.polje;
		int rand = (int) (Math.random() * 4);
		switch (rand) {
		case 0/* GORE */:
			int[] koord = polje.pozicija();
			int x = koord[0];
			int y = koord[1];
			Polje p = polje.dohvati(0, -1);
			if (p != null) {
				if (p.dozvoljeno(this))
					this.pomeriFiguru(p);
			}
			break;
		case 1/* DOLE */:
			int[] koord1 = polje.pozicija();
			int x1 = koord1[0];
			int y1 = koord1[1];
			Polje p1 = polje.dohvati(0, 1);
			if (p1 != null) {
				if (p1.dozvoljeno(this))
					this.pomeriFiguru(p1);
			}
			break;
		case 2 /* LEVO */:
			int[] koord2 = polje.pozicija();
			int x2 = koord2[0];
			int y2 = koord2[1];
			Polje p2 = polje.dohvati(-1, 0);
			if (p2 != null) {
				if (p2.dozvoljeno(this))
					this.pomeriFiguru(p2);
			}
			break;
		case 3 /* DESNO */:
			int[] koord3 = polje.pozicija();
			int x3 = koord3[0];
			int y3 = koord3[1];
			Polje p3 = polje.dohvati(1, 0);
			if (p3 != null) {
				if (p3.dozvoljeno(this))
					this.pomeriFiguru(p3);
				break;
			}
		}
		staroPolje.repaint();
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				while (!radi)
					wait();
			}
			while (radi) {
				Thread.sleep(500);
				pomeri();

			}
		} catch (InterruptedException g) {
		}

	}

	@Override
	public void crtaj(Graphics g) {
		g.setColor(Color.BLACK);
		int x=polje.getX();
		int y=polje.getY();
		int sirina=polje.getWidth();
		int duzina=polje.getHeight();
		g.drawLine(x,y,x+sirina,y+duzina);
		g.drawLine(x, y+duzina, x+sirina, y);

	}

}
