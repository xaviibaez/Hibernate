import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Validation extends JFrame {

	private JPanel contentPane;
	private JTextField usuari;
	private JPasswordField password;
	private JLabel lblUsuari;
	private JLabel lblNewLabel;
	private JLabel missatgeError;
	
	int intents = 4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
			
		Logger.getLogger("org.hibernate").setLevel(Level.OFF); 
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Validation frame = new Validation();
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
	public Validation() {
		setResizable(false);
		setTitle("XAVI HIBERNATE XAVI - LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
				
		usuari = new JTextField();
		usuari.setColumns(10);
		
		password = new JPasswordField();
		password.setColumns(10);	
		
		JButton btnLogin = new JButton("Log In");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				missatgeError.setText("");				
				String user = usuari.getText();
				String psword = password.getText();
							
				if(user.isEmpty()) {
					missatgeError.setText("No User.");
				}				
				else 
					if(psword.isEmpty()){
						missatgeError.setText("No Password.");
					}	
				else {
				
					SessionFactory sessionFactory;
				       
			    	Configuration configuration = new Configuration();
			        //configuration.configure();
			        configuration.configure("hibernate.cfg.xml");
			        
			        // Consulta
			        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();			     
			        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			        
			        Session sessio = sessionFactory.openSession();     			        
			        Query queryUsuari = sessio.createQuery("FROM Usuari WHERE codiUsuari LIKE '" + user + "'");
			                
			        ArrayList<Usuari> arrayUsuari = (ArrayList<Usuari>) queryUsuari.list();
			        
			        if(arrayUsuari.isEmpty()) {
			        	missatgeError.setText("Not existing User.");
			        }
			        
			        else 
			        	if(arrayUsuari.get(0).getBloquejat() == true) {
			        		missatgeError.setText(user + " is blocked.");
			        	}
			        
			        else {			     
				        for (Usuari u : arrayUsuari) {
				             System.out.print(u.getCodiUsuari().toString());
				             System.out.println(" " + u.getCognom1usuari().toString());
				        }
				        
				        Query queryPassword = sessio.createQuery("FROM Usuari WHERE password LIKE '" + psword + "'");	        
				        ArrayList<Usuari> arrayPassword = (ArrayList<Usuari>) queryPassword.list();				        			        
				        
				        if(arrayPassword.isEmpty()) {
				        	intents--;				        			        
				        		if(intents <= 0) {			        		
					        		missatgeError.setText("No more attempts. Block User " + user);
					        		
					        		arrayUsuari.get(0).setBloquejat(true);
					        		sessio.beginTransaction();
					        		sessio.update(arrayUsuari.get(0));
					        		sessio.getTransaction().commit();
					        	}			        	
					        	else {				        	
					        		missatgeError.setText("Incorrect Password. Attempts: " + intents);
					        	}				       				        					        	
				        }
				        else {
				        	missatgeError.setText(user + " correct login!");
				        	System.out.println("Entra " + user);
				        	
				        	Tipususuari tipus;
				        	Query queryTipus = sessio.createQuery("FROM Usuari WHERE codiUsuari LIKE '" + user + "'");			        	
				        	Usuari userTipus = (Usuari)queryTipus.uniqueResult();
				        	
				        	tipus = userTipus.getTipususuari();				        	
					        switch(tipus.getIdTipusUsuari()) {
						        case 1://ADM
						        	GestioAdmin gAdmin = new GestioAdmin(arrayUsuari.get(0));
						        	gAdmin.setVisible(true);
						        	sessio.close();
					        		dispose();
						        	break;
						        	
						        case 2://PRF						        	
						        	GestioProfessor gP = new GestioProfessor(arrayUsuari.get(0));
						        	gP.setVisible(true);
						        	sessio.close();
					        		dispose();
						        	break;
						        	
						        case 3://ALU
						        	GestioAlumne gA = new GestioAlumne(arrayUsuari.get(0));
						        	gA.setVisible(true);
						        	sessio.close();
					        		dispose();
						        	break;
					        }			        					        
				        }
			        }
				}
			}
		});
		
		lblUsuari = new JLabel("User");
		lblUsuari.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		lblNewLabel = new JLabel("Password");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		missatgeError = new JLabel("", SwingConstants.CENTER); // Con esto centramos el texto
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(107)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblUsuari)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(usuari, Alignment.LEADING)
						.addComponent(password, Alignment.LEADING)
						.addComponent(btnLogin, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(17, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(27, Short.MAX_VALUE)
					.addComponent(missatgeError, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(57)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(usuari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsuari, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addGap(36)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(48)
					.addComponent(missatgeError)
					.addGap(18)
					.addComponent(btnLogin)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

