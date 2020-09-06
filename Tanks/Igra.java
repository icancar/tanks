package zadatak2;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.*;

public class Igra extends Frame {
	private Mreza mreza;
	private int inic = 0;
	private Label label1, label2;
	private Button start;
	private TextField textBox;
	private Checkbox odluka1=new Checkbox(), odluka2=new Checkbox();
	private Label podloga = new Label("Podloga: ");
	private Label vrijeme;
	private Casovnik sat;
	private Dialog kraj;
	private boolean bacenDijalog=false;
	public Igra() {
		super("Igra");
		mreza = new Mreza(this);

		setBounds(700, 200, 500, 450);
		mreza.setSize(360, 360);

		add(mreza, BorderLayout.CENTER);

		mreza.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent dog) {
				mreza.pomeriIgraca(dog);
				//System.out.println("Pritisnuto");
			}

		});

		dodajMeni();
		label1 = new Label("Novcici:");
		textBox = new TextField("18");
		label2 = new Label("Poena: 0");
		mreza.postLabelu(label2);
		add(juzniPanel(), BorderLayout.SOUTH);
		add(istocniPanel(), BorderLayout.EAST);
		mreza.pocetnainicijalizacija();
		setResizable(false);
		sat=new Casovnik(vrijeme);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mreza.zaustavi();
				mreza.interapt();
				dispose();
			}
		});
		mreza.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				mreza.rezimIzmene(arg0);
			}
			
		});
		setVisible(true);
	}

	private void dodajMeni() {
		MenuBar meni = new MenuBar();
		setMenuBar(meni);
		Menu rezim = new Menu("Rezim");
		meni.add(rezim);
		MenuItem rezimIzmena = new MenuItem("Rezim izmena");
		MenuItem rezimIgranje = new MenuItem("Rezim igranje");
		rezimIzmena.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				start.setEnabled(false);
				mreza.postRezim(false);
				
			}

		});
		rezimIgranje.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start.setEnabled(true);
				mreza.postRezim(true);
			}

		});
		Menu Igor=new Menu("Igor");
		MenuItem Cancar=new MenuItem("Cancar");
		MenuItem Doktor=new MenuItem("Doktor");
		Cancar.setShortcut(new MenuShortcut(KeyEvent.VK_A, false));
		meni.add(Igor);
		Igor.add(Cancar);
		Igor.add(Doktor);
		rezim.add(rezimIgranje);
		rezim.add(rezimIzmena);

	}
	private Panel istocniPanel() {
		Panel istocni = new Panel(new BorderLayout());
		istocni.add(podloga, BorderLayout.CENTER);
		CheckboxGroup g = new CheckboxGroup();
		odluka1 = new Checkbox("Trava", g, true);
		odluka1.setBackground(Color.GREEN);
		odluka2 = new Checkbox("Zid", g, false);
		odluka2.setBackground(Color.LIGHT_GRAY);
		Panel izbor = new Panel(new GridLayout(0, 1));
		izbor.add(odluka1);
		izbor.add(odluka2);
		istocni.add(izbor, BorderLayout.EAST);
		return istocni;
	}

	private Panel juzniPanel() {// zamalo Juzni Vetar
		Panel juzniSever = new Panel();
		vrijeme=new Label("00:00");
		start = new Button("Pocni");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				label2.setText("Poeni: 0");
				mreza.kreni();
				mreza.pocni(Integer.parseInt(textBox.getText()));		
				if(mreza.rezimRada() || !mreza.rezimRada())
				start.setEnabled(false);
				sat.reset();
				sat.kreni();
			}
			
		});
		mreza.dodajDugme(start);
		juzniSever.add(label1);
		juzniSever.add(textBox);
		juzniSever.add(label2);
		juzniSever.add(start);
		juzniSever.add(vrijeme);
		return juzniSever;

	}

	public void dugme() {
		start.setEnabled(true);
	}
	public Checkbox odluka1() {return odluka1;}
	public Checkbox odluka2() {return odluka2;}
	public static void main(String[] args) {
		new Igra();
	}
	public void baciDijalog() {
		if(!bacenDijalog) {
		Frame frejm=new Frame();
		TextArea poruka=new TextArea();
		poruka.append("KRAJ IGRE");
		frejm.add(poruka);
		
		kraj=new Dialog(frejm);
		kraj.setTitle("Kraj igre");
		kraj.setVisible(true);
		kraj.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				kraj.dispose();
			}
			
		});
		bacenDijalog=true;
		}
		
	}
	public Casovnik sat() {return sat;}
}
