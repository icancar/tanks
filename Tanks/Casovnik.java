package zadatak2;

import java.awt.Label;

public class Casovnik  extends Thread{
	private Label labela;
	private int m, s;
	private boolean radi;
	
	public Casovnik(Label e) {
		labela=e;
		start();
	}
	
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
			synchronized (this) {
				while(!radi) wait();
			}
			azuriraj();
			labela.revalidate();
			sleep(1000);
			s++;
			if(s%60==0) {
				m++;
				s=0;
			}
			
			
			}
		}catch(InterruptedException g) {}
	}


	public synchronized void kreni() {
		radi=true;
		notify();
	}
	public void azuriraj() {
		labela.setText(toString());
	}
	public synchronized String toString() {
		return String.format("%02d:%02d", m,s);
	}
	public synchronized void reset() {
		s=m=0;
	}
	public synchronized void pauza() {
		radi=false;
	}
	
	
	
}
