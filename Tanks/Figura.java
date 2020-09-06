package zadatak2;

import java.awt.Graphics;

public abstract class Figura {
	protected Polje polje;

	public Figura(Polje p) {
		polje = p;
	}

	public Polje polje() {
		return polje;
	}

	public void pomeriFiguru(Polje p) {
		polje = p;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Figura other = (Figura) obj;
		int[] koord1 = this.polje.pozicija();
		int[] koord2 = other.polje.pozicija();
		int k1x = koord1[0];
		int k1y = koord1[1];
		int k2x = koord2[0];
		int k2y = koord2[1];
		return (k1x == k2x && k1y == k2y);
	}

	public abstract void crtaj(Graphics g);
}
