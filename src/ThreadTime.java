
public class ThreadTime extends Thread {

	@Override
	public void run() {
		while(true) {
			String[] tempo = Clock.time.getText().split(":");
			int horas = Integer.parseInt(tempo[0]);
			int minutos = Integer.parseInt(tempo[1]);
			
			minutos++;
			
			if(minutos < 0 ) {
				minutos = 0;
			}
			
			if(horas < 0) {
				horas = 0;
			}
			
			if(minutos == 60) {
				minutos = 0;
				horas++;
			}
			
			if(horas == 24) {
				horas = 0;
			}
			
			Clock.time.setText(horas+":"+minutos);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Clock.time.setText("ERRO#");
			}
		}
	}
}
