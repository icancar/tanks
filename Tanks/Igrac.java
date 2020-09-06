package zadatak2;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {
	private boolean dozvoljenoKretanje=true;
	public Igrac(Polje p) {
		super(p);
	}

	@Override
	public void crtaj(Graphics g) {
		if(dozvoljenoKretanje) {
		g.setColor(Color.RED);
		int x=polje.getX();
		int y=polje.getY();
		int d=polje.getHeight();
		int s=polje.getWidth();
		g.drawLine(x+s/2,y,x+s/2,y+d);
		g.drawLine(x,y+d/2, x+s,y+d/2);
		}
		else {};
	}

	public void zabraniKretanje() {
		dozvoljenoKretanje=false;		
	}

	public boolean dozvoljenoKretanje() {
		return dozvoljenoKretanje;
	}
}
