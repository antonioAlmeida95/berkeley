import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ThreadCliente extends Thread {
	private MulticastSocket mcs;
	
	@Override
	public void run() {
		try {
		mcs = new MulticastSocket(6001);

		
		String data = "0";
		
		byte rec[] = new byte[256];
		DatagramPacket pkg = new DatagramPacket(rec, rec.length);
		mcs.receive(pkg);
		data = new String(pkg.getData(), 0, pkg.getLength());
							
		Clock.lblMensagem.setText("Iniciando");
				

		String[] timeServidor = data.split(":");
		String[] timeCliente = Clock.time.getText().split(":");

		int diferenca = CalculoDiferencial(timeServidor, timeCliente);
		
		System.out.println("Recebido do Master "+pkg.getAddress()+" "+pkg.getPort() +" "+data);
		
		Clock.lblMensagem.setText("Enviando");
		byte[] b = (""+diferenca).getBytes();
		DatagramPacket resp = new DatagramPacket(b, b.length, pkg.getAddress(),pkg.getPort());
		DatagramSocket ds = new DatagramSocket();
		ds.send(resp);//enviando pacote broadcast
		

		
		Clock.lblMensagem.setText("Recebendo");
		rec = new byte[256];
		pkg = new DatagramPacket(rec, rec.length);
		ds.receive(pkg);
		data = new String(pkg.getData(), 0, pkg.getLength());
		System.out.println("Sincronização do master "+pkg.getAddress()+" "+pkg.getPort() +" "+data);
		
		Ajustar(data);
		Clock.lblMensagem.setText("Sincronizado");
		
		
		}catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}

		
		
	}

	private int CalculoDiferencial(String[] timeServidor, String[] timeCliente) {
		return ((Integer.parseInt(timeCliente[0]) - Integer.parseInt(timeServidor[0])) * 60)
				+ (Integer.parseInt(timeCliente[1]) - Integer.parseInt(timeServidor[1]));

	}

	
	private void Ajustar(String tempoMedio) {
		String[] tp = Clock.time.getText().split(":");
		
		int horas = Integer.parseInt(tp[0]);
		int minutos = Integer.parseInt(tp[1]);
		
		minutos +=  Integer.parseInt(tempoMedio);
		
		Clock.time.setText(horas+":"+minutos);
	}
}
