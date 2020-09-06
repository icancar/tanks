package zadatak2;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {

	public Novcic(Polje p) {
		super(p);
	}

	@Override
	public void crtaj(Graphics g) {
		g.setColor(Color.YELLOW);
		int x=polje.getX();
		int y=polje.getY();
		int sirina=polje.getWidth();
		int duzina=polje.getHeight();
		g.fillOval(x+sirina/4,y+duzina/4,sirina/2,duzina/2);
	}

}
