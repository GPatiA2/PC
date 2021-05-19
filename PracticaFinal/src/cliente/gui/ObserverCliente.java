package cliente.gui;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import coms.FileInfo;
import coms.UserInfo;

public interface ObserverCliente {
	public void alRecibirConfirmacionConectar();
	public void alRecibirTabla(List<FileInfo> m);
	public void alRecibirPeticionDeEmision(UserInfo inf, String fichero);
	public void alRecibirDatosServidorCliente(UserInfo inf, InetAddress ip);
	public void alRecibirConfirmacionCerrar(/*args*/);
	public void alRegistrarse(boolean conectado, List<FileInfo> m, UserInfo inf);
}
