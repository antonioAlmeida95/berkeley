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
import java.util.ArrayList;

public class ThreadServidor extends Thread {
	private String tempo = "";
	private ArrayList<DatagramPacket> address;
	private int tempoMedio = 0;

	@Override
	public void run() {

		try {
			address = new ArrayList<>();
			tempo= Clock.time.getText();
			byte[] b = tempo.getBytes();

			InetAddress addr = InetAddress.getByName("255.255.255.255");
			DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 6001);
			MulticastSocket ds = new MulticastSocket();
			ds.send(pkg);// enviando pacote broadcast

			try {
				ds.setSoTimeout(10000);
				while(true) {
					byte[] d = new byte[256];
					DatagramPacket rec = new DatagramPacket(d, d.length);
					ds.receive(rec);
					String tempo =  new String(rec.getData(), 0, rec.getLength());
					tempoMedio += Integer.parseInt(tempo);
					address.add(rec);
					System.out.println(rec.getAddress() + " " + rec.getPort()+ " "+tempo);
					
					
				}
			}catch(SocketTimeoutException e1) {
				tempoMedio = tempoMedio/address.size();
				System.out.println("TEMPO "+tempoMedio);
			}
			
		} catch (Exception e) {
			System.out.println("Erro Servidor "+e.getMessage());
		}
		
		
		try{
			for(DatagramPacket e : address) {
				String valor = new String(e.getData(), 0, e.getLength());
				valor = Diferencial(valor);
				byte[] b = valor.getBytes();
				e.setData(b);
				DatagramSocket ds = new DatagramSocket();
				ds.send(e);
			}
			
		}catch(IOException e2) {
			System.out.println("Erro envio etapa 3 "+e2.getMessage());
		}
		
		Ajustar();
	}

	
	private String Diferencial(String valor) {
		int resul = Integer.parseInt(valor);
		resul = (resul * -1)+tempoMedio;
		return ""+resul;
	}
	
	private void Ajustar() {
		String[] tp = Clock.time.getText().split(":");
		
		int horas = Integer.parseInt(tp[0]);
		int minutos = Integer.parseInt(tp[1]);
		
		minutos +=  + tempoMedio;
		
		Clock.time.setText(horas+":"+minutos);
	}
	
	


}
