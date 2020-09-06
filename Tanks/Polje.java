package zadatak2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public abstract class Polje extends Canvas{
	protected Mreza mreza;
	
	protected Polje (Mreza m) {
		mreza=m;
	}
	
	public Mreza mreza() {return mreza;}
	public int[] pozicija() {
		int[] niz=new int [2];
		Polje[][] pom=mreza.matrica();
		for(int i=0;i<mreza.kolona();i++){
			for(int j=0;j<mreza.vrsta();j++) {
				if(pom[i][j].equals(this)){
					niz[0]=i;
					niz[1]=j;
				}
			}
		}
		return niz;
	}
	public Polje dohvati(int x, int y) {
		Polje[][] pom=mreza.matrica();
		int[] koordinate=this.pozicija();
		if(koordinate[0]+x>=mreza.kolona() || koordinate[1]+y>=mreza.vrsta()|| koordinate[0]+x<0 || koordinate[1]+y<0) return null;
		Polje trazeno=pom[koordinate[0]+x][koordinate[1]+y];
		return trazeno;
	}
	public abstract boolean dozvoljeno(Figura f);
	public abstract Color dohvBoju();

	
}
