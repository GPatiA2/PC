package cliente.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import cliente.Controller;
import coms.FileInfo;
import coms.UserInfo;

public class MainWindow extends JFrame implements ObserverCliente {
	
	Controller ctrl;
	
	
	
	public MainWindow(Controller c) {
		super("Cliente");
		ctrl = c;
		
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(new StatusBar(ctrl), BorderLayout.PAGE_END);
		mainPanel.add(new ControlPanel(ctrl, this), BorderLayout.PAGE_START);
		
		JPanel infoPanel = createInfoPanel(new JTable(new FicherosTableModel(ctrl)));
		mainPanel.add(infoPanel, BorderLayout.CENTER);
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				exit();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generatd method stub
			}
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	
	private JPanel createInfoPanel(JComponent c) {
		JPanel p = new JPanel(new BorderLayout());
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		p.setBorder(b);
		p.add(new JScrollPane(c));
		return p;
	}
	
	public void exit() {
		Object[] options = {"SI", "NO"};
		int r = JOptionPane.showOptionDialog(null,  "Seguro que quieres salir?", "Salir", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(r == 0) {
			System.exit(0);
		}
		else {
			return;
		}
	}


	public void pedirTabla() {
		// TODO Auto-generated method stub
		ctrl.pedirTabla();
	}

	public void pedirFichero() {
		// TODO Auto-generated method stub
		String s = (String) JOptionPane.showInputDialog(this, "Introduce el fichero que quieres descargar" , "Fichero", JOptionPane.PLAIN_MESSAGE, null, null, "fichero.txt");
		ctrl.pedirFichero(s);
	}

	public void desconectar() {
		// TODO Auto-generated method stub
		ctrl.desconectar();
	}

	@Override
	public void alRecibirConfirmacionConectar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void alRecibirTabla(List<FileInfo> m) {
		// TODO Auto-generated method stub
	}

	@Override
	public void alRecibirPeticionDeEmision(UserInfo inf, String fichero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alRecibirDatosServidorCliente(UserInfo inf, InetAddress ip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alRecibirConfirmacionCerrar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alRegistrarse(boolean conectado, List<FileInfo> m, UserInfo inf) {
		// TODO Auto-generated method stub
		
	}

}
