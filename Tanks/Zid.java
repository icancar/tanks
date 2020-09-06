package zadatak2;

import java.awt.Color;
import java.awt.Graphics;

public class Zid extends Polje {

	protected Zid(Mreza m) {
		super(m);
		//setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public boolean dozvoljeno(Figura f) {
		return false;
	}

	@Override
	public Color dohvBoju() {
		return Color.LIGHT_GRAY;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(this.getX(),this.getY(), getWidth(), getHeight());
	}
	
}
