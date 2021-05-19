package cliente.gui;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cliente.Controller;
import coms.FileInfo;
import coms.UserInfo;

public class StatusBar extends JPanel implements ObserverCliente {
	
	Controller ctrl;
	JLabel letrero;
	JLabel ultimaAccion;
	JLabel ipLabel;
	
	public StatusBar(Controller c) {
		ctrl = c;
		ctrl.addObserver(this);
		initGUI();
		setVisible(true);
	}
	
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		letrero = new JLabel("Ultima Accion = ");
		ultimaAccion = new JLabel("");
		ipLabel = new JLabel("");
		this.add(letrero);
		this.add(Box.createHorizontalGlue());
		this.add(ultimaAccion);
		this.add(Box.createHorizontalGlue());
		this.add(ipLabel);
		letrero.setVisible(true);
		ultimaAccion.setVisible(true);
	}
	
	@Override
	public void alRecibirTabla(List<FileInfo> m) {
		// TODO Auto-generated method stub
		ultimaAccion.setText("Tabla de usuarios y ficheros recibida");
	}

	@Override
	public void alRecibirPeticionDeEmision(UserInfo inf, String fichero) {
		// TODO Auto-generated method stub
		ultimaAccion.setText("El cliente con id = " + inf.getId() + " ha pedido el fichero " + fichero);
	}

	@Override
	public void alRecibirDatosServidorCliente(UserInfo inf, InetAddress ip) {
		// TODO Auto-generated method stub
		ultimaAccion.setText("El cliente " + inf.getId() + " en la ip " + ip.toString() + " va a transmitir el fichero pedido");
	}

	@Override
	public void alRegistrarse(boolean conectado, List<FileInfo> m, UserInfo inf) {
		// TODO Auto-generated method stub
		String ip = inf.getIP().toString();
		ipLabel.setText("ID = " + inf.getId() + " IP = " + ip);
		if(conectado) {
			ultimaAccion.setText("conectado");
		}
		else {
			ultimaAccion.setText("desconectado");
		}
	}

	@Override
	public void alRecibirConfirmacionConectar() {
		// TODO Auto-generated method stub
		ultimaAccion.setText("Conectado");
	}

	@Override
	public void alRecibirConfirmacionCerrar() {
		// TODO Auto-generated method stub
		ultimaAccion.setText("Desconectado");
	}

}
