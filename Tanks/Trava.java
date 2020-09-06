package zadatak2;

import java.awt.Color;
import java.awt.Graphics;

public class Trava extends Polje {

	public Trava(Mreza m) {
		super(m);
	}

	@Override
	public boolean dozvoljeno(Figura f) {
		return true;
	}

	@Override
	public Color dohvBoju() {
		return Color.GREEN;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(this.getX(),this.getY(), getWidth(), getHeight());
	}
	
}
