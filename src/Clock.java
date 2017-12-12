import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import java.awt.Choice;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;

public class Clock extends JFrame {

	private JPanel contentPane;
	protected JToggleButton tgMaster;
	protected JToggleButton tgCliente;
	protected static JLabel time;
	protected static JLabel lblMensagem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clock frame = new Clock();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Clock() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		tgMaster = new JToggleButton("Master");
		tgMaster.setBounds(10, 55, 104, 23);
		contentPane.add(tgMaster);
		
		time = new JLabel("00:00");
		time.setFont(new Font("Tahoma", Font.PLAIN, 55));
		time.setHorizontalAlignment(SwingConstants.CENTER);
		time.setBounds(192, 31, 233, 81);
		contentPane.add(time);
		
		lblMensagem = new JLabel("Mensagem");
		lblMensagem.setBounds(25, 155, 74, 14);
		contentPane.add(lblMensagem);
		
		tgCliente = new JToggleButton("Cliente");
		tgCliente.setBounds(10, 11, 104, 23);
		contentPane.add(tgCliente);
		
		ThreadTime t = new ThreadTime();
		t.start();
		
		
		
		
		tgMaster.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(tgMaster.isSelected()) {
					tgCliente.setEnabled(false);
					ThreadServidor s = new ThreadServidor();
					s.start();
					
				}else {
					tgCliente.setEnabled(true);
					
					
				}
				
			}
		});
		
		
		tgCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(tgCliente.isSelected()) {
					tgMaster.setEnabled(false);
					ThreadCliente c = new ThreadCliente();
					c.start();
				}else {
					tgMaster.setEnabled(true);
				}
				
			}
		});

	}
}
