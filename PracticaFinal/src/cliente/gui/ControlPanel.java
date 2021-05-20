package cliente.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import cliente.Controller;
import coms.FileInfo;
import coms.UserInfo;

public class ControlPanel extends JPanel implements ObserverCliente {
	
	Controller c;
	MainWindow mw;
	
	JButton pedirTabla;
	JButton pedirFichero;
	JButton conectado;
	JButton desconectarse;
	
	public ControlPanel(Controller ctrl, MainWindow mw) {
		c = ctrl;
		this.mw = mw;
		
		initGUI();
		ctrl.addObserver(this);
		this.setVisible(true);
	}
	
	private void initGUI() {
		JToolBar tb = new JToolBar();
		this.setLayout(new BorderLayout());
		
		pedirTabla = new JButton("Tabla ficheros");
		pedirTabla.setToolTipText("Pedirle al servidor la tabla de ficheros disponibles");
		pedirTabla.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				mw.pedirTabla();
			}
			
		});
		
		pedirFichero = new JButton("Pedir fichero");
		pedirFichero.setToolTipText("Pedir un fichero a un cliente a traves del servidor");
		pedirFichero.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mw.pedirFichero();
			}
			
		});
		
		conectado = new JButton("");
		conectado.setBackground(Color.red);
		
		desconectarse = new JButton("Desconectarse");
		desconectarse.setToolTipText("Desconectarse del servidor");
		desconectarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mw.desconectar();
			}
			
		});
		
		tb.add(conectado);
		tb.add(desconectarse);
		tb.add(pedirTabla);
		tb.add(pedirFichero);
		
		this.add(tb);
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
	public void alRegistrarse(boolean conectado, List<FileInfo> m, UserInfo inf) {
		// TODO Auto-generated method stub
		if(conectado) { this.conectado.setBackground(Color.green); }
		else { this.conectado.setBackground(Color.red);}
	}

	@Override
	public void alRecibirConfirmacionConectar() {
		// TODO Auto-generated method stub
		this.conectado.setBackground(Color.green); 
	}

	@Override
	public void alRecibirConfirmacionCerrar() {
		// TODO Auto-generated method stub
		this.conectado.setBackground(Color.red); 
	}

}
