package cliente.gui;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import cliente.Controller;
import coms.FileInfo;
import coms.UserInfo;

public class FicherosTableModel extends AbstractTableModel implements ObserverCliente {
	
	private static String[] headers = {"Propietario", "IP" , "Fichero"};
	
	List<FileInfo> elementos;
	
	public FicherosTableModel(Controller ctrl) {
		super();
		elementos = new ArrayList<FileInfo>();
		ctrl.addObserver(this);
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return headers.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return elementos.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		FileInfo fi = elementos.get(arg0);
		switch (arg1) {
			case 0: return fi.getUser().getId();
			case 1: return fi.getUser().getIP().toString();
			case 2: return fi.getFileName();
			default: return "";
		}
	}

	@Override
	public void alRecibirConfirmacionConectar() {
		// TODO Auto-generated method stub
	}

	@Override
	public void alRecibirTabla(List<FileInfo> m) {
		// TODO Auto-generated method stub
		elementos = m;
		fireTableDataChanged();
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
		elementos = m;
		fireTableDataChanged();
	}
	
	public String getColumnName(int arg) {
		return headers[arg];
	}
	
}
