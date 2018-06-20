import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;

public class GestioProfessor extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordFieldUpdatePassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestioProfessor frame = new GestioProfessor(null);
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
	public GestioProfessor(Usuari Professor) {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();			     
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session sessio = sessionFactory.openSession();
		
		setResizable(false);
		setTitle("XAVI HIBERNATE XAVI - PROFESSOR PROFILE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);	
		
		System.out.println("Gestio Professor: " + Professor.getCodiUsuari());
		
		/* INFORMACIÓ BASICA PASSWORD ------------------------------------------- */

		JLabel label = new JLabel("ID:");
		label.setBounds(15, 16, 58, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Name:");
		label_1.setBounds(15, 36, 58, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("Surname 1:");
		label_2.setBounds(15, 56, 73, 14);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("Surname 2:");
		label_3.setBounds(15, 76, 73, 14);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("DNI:");
		label_4.setBounds(15, 96, 55, 14);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("User:");
		label_5.setBounds(15, 116, 58, 14);
		contentPane.add(label_5);
		
		JLabel label_7 = new JLabel("Up date:");
		label_7.setBounds(15, 141, 73, 14);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("Down date:");
		label_8.setBounds(17, 166, 71, 14);
		contentPane.add(label_8);
		
		/* - TEXT - */
		
		JLabel IDText = new JLabel("");
		IDText.setBounds(122, 16, 80, 14);
		contentPane.add(IDText);
		
		JLabel NameText = new JLabel("");
		NameText.setBounds(122, 36, 80, 14);
		contentPane.add(NameText);
		
		JLabel Surname1Text = new JLabel("");
		Surname1Text.setBounds(122, 56, 80, 14);
		contentPane.add(Surname1Text);
		
		JLabel Surname2Text = new JLabel("");
		Surname2Text.setBounds(122, 76, 80, 14);
		contentPane.add(Surname2Text);
		
		JLabel DNIText = new JLabel("");
		DNIText.setBounds(122, 96, 80, 14);
		contentPane.add(DNIText);
		
		JLabel UserText = new JLabel("");
		UserText.setBounds(122, 116, 80, 14);
		contentPane.add(UserText);
		
		JLabel UpDateText = new JLabel("");
		UpDateText.setBounds(122, 141, 80, 14);
		contentPane.add(UpDateText);
		
		JLabel DownDateText = new JLabel("");
		DownDateText.setBounds(122, 166, 80, 14);
		contentPane.add(DownDateText);
		
		/* DETALLS PROFESSOR */
		IDText.setText(Professor.getIdUsuari().toString());
		NameText.setText(Professor.getNomUsuari());
		Surname1Text.setText(Professor.getCognom1usuari());
		Surname2Text.setText(Professor.getCognom2usuari());
		DNIText.setText(Professor.getDni());	
		UserText.setText(Professor.getCodiUsuari());
		//CourseText.setText(Professor.getCursos().getDescripcio());
		
		if(Professor.getDataAlta() != null) {
			UpDateText.setText(Professor.getDataAlta().toString());
		}
		else {
			UpDateText.setText("");
		}
		
		if(Professor.getDataBaixa() != null) {
			DownDateText.setText(Professor.getDataBaixa().toString());
		}
		else {
			DownDateText.setText("");
		}
		
		
		/* CANVIAR PASSWORD ------------------------------------------- */
		JLabel label_9 = new JLabel("New Password:");
		label_9.setBounds(15, 223, 100, 14);
		contentPane.add(label_9);
		
		passwordFieldUpdatePassword = new JPasswordField();
		passwordFieldUpdatePassword.setBounds(15, 247, 153, 20);
		contentPane.add(passwordFieldUpdatePassword);
		
		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.setBounds(15, 278, 153, 23);
		contentPane.add(btnChangePassword);		
				
		JLabel lblUpdatePassword = new JLabel("");
		lblUpdatePassword.setBounds(10, 322, 192, 14);
		contentPane.add(lblUpdatePassword);
		
		btnChangePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(passwordFieldUpdatePassword.getText().equals(Professor.getPassword())) {//Si la nova password és igual a l'anterior
					passwordFieldUpdatePassword.setText("");
					lblUpdatePassword.setText("Password not changed");
				}
				else {//Sino, fa el update
					String psdNova = passwordFieldUpdatePassword.getText();
					Professor.setPassword(psdNova);
		    		sessio.beginTransaction();
		    		sessio.update(Professor);
		    		sessio.getTransaction().commit();
		    		lblUpdatePassword.setText("Password changed");
				}
			}		
		});
		
		/* INFORMACIÓ GRUP MOODLE ------------------------------------------- */
		
		JLabel lblResponsableGroups = new JLabel("RESPONSABLE GROUPS:");
		lblResponsableGroups.setBounds(237, 16, 153, 14);
		contentPane.add(lblResponsableGroups);
		
		JList listGrupsMoodle = new JList();
		listGrupsMoodle.setBounds(237, 35, 119, 128);
		contentPane.add(listGrupsMoodle);
		
		JLabel label_11 = new JLabel("DETAILS:");
		label_11.setBounds(237, 181, 87, 14);
		contentPane.add(label_11);
		
		JLabel label_12 = new JLabel("ID Group Moodle:");
		label_12.setBounds(238, 206, 122, 14);
		contentPane.add(label_12);
		
		JLabel label_13 = new JLabel("Name Group Moodle:");
		label_13.setBounds(238, 231, 122, 14);
		contentPane.add(label_13);
		
		JLabel label_14 = new JLabel("Description:");
		label_14.setBounds(237, 256, 100, 14);
		contentPane.add(label_14);
		
		JLabel label_15 = new JLabel("Active:");
		label_15.setBounds(237, 282, 100, 14);
		contentPane.add(label_15);
		
		JLabel label_17 = new JLabel("Pending tasks:");
		label_17.setBounds(500, 346, 87, 14);
		contentPane.add(label_17);
		
		JLabel label_18 = new JLabel("Evaluated tasks:");
		label_18.setBounds(500, 371, 100, 14);
		contentPane.add(label_18);
		
		
		/* VEURE GRUPS MOODLE */			
		DefaultListModel modelLlista = new DefaultListModel();
		listGrupsMoodle.setModel(modelLlista);	
		
		JLabel IDGroupText = new JLabel("");
		IDGroupText.setBounds(370, 206, 93, 14);
		contentPane.add(IDGroupText);
		
		JLabel NameGroupText = new JLabel("");
		NameGroupText.setBounds(370, 231, 93, 14);
		contentPane.add(NameGroupText);
		
		JLabel DescriptionGroupText = new JLabel("");
		DescriptionGroupText.setBounds(370, 256, 93, 14);
		contentPane.add(DescriptionGroupText);
		
		JLabel ActiveGroupText = new JLabel("");
		ActiveGroupText.setBounds(370, 282, 93, 14);
		contentPane.add(ActiveGroupText);
		
		JLabel PendingTaskGroupText = new JLabel("");
		PendingTaskGroupText.setBounds(610, 346, 93, 14);
		contentPane.add(PendingTaskGroupText);
		
		JLabel EvaluatedTaskGroupText = new JLabel("");
		EvaluatedTaskGroupText.setBounds(610, 371, 93, 14);
		contentPane.add(EvaluatedTaskGroupText);
		
		JLabel lblRegisteredStudents = new JLabel("REGISTERED STUDENTS:");
		lblRegisteredStudents.setBounds(500, 16, 153, 14);
		contentPane.add(lblRegisteredStudents);
		
		JList listRegisteredStudents = new JList();
		listRegisteredStudents.setBounds(500, 35, 119, 301);
		contentPane.add(listRegisteredStudents);
		
		DefaultListModel modelLlistaAlumnseInscrits = new DefaultListModel();
        listRegisteredStudents.setModel(modelLlistaAlumnseInscrits);
        
        /*
         * *
         */
        
        /*JList listNotRegisteredStudents = new JList();
        listNotRegisteredStudents.setBounds(681, 35, 119, 361);
        contentPane.add(listNotRegisteredStudents);
        
        DefaultListModel modelLlistaAlumnseNOInscrits = new DefaultListModel();
        listNotRegisteredStudents.setModel(modelLlistaAlumnseNOInscrits);*/
        
        JComboBox comboBox = new JComboBox();
		comboBox.setBounds(237, 342, 153, 23);
		contentPane.add(comboBox);
		
		DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
		comboBox.setModel(comboBoxModel);
        
        /*
         * 
         */
        
        JLabel lblNotRegisteredStudents = new JLabel("NOT REGISTERED STUDENTS:");
        lblNotRegisteredStudents.setBounds(237, 322, 153, 14);
        contentPane.add(lblNotRegisteredStudents);
        
        JButton btnRegistrarAlumne = new JButton("INSERT");
        btnRegistrarAlumne.setBounds(237, 382, 153, 23);
        contentPane.add(btnRegistrarAlumne);
        
        JButton btnEvaluate = new JButton("EVALUATE");
        btnEvaluate.setBounds(500, 396, 119, 23);
        contentPane.add(btnEvaluate);
        
        JLabel lblAVISO = new JLabel("");
        lblAVISO.setBounds(15, 416, 448, 14);
        contentPane.add(lblAVISO);
		
		List<Grupmoodle> grupmoodle = new ArrayList<Grupmoodle>();		
		Query queryIncrits = sessio.createQuery("SELECT g FROM Grupmoodle g WHERE ProfessorReponsable LIKE '" + Professor.getIdUsuari() + "'");
		grupmoodle = queryIncrits.list();

        for (Grupmoodle grupmoodles : grupmoodle) {     	
			if(grupmoodles.getActiu()) {
				modelLlista.addElement(grupmoodles.getDesciripcioGrupMoodle());
			}
        }
        
		/* DETALLS GRUPS MOODLE */	      
        listGrupsMoodle.addListSelectionListener(new ListSelectionListener() {    
            List<Grupmoodle> llistaDetallGrupMoodle = new ArrayList<Grupmoodle>();
	        List<Inscripcions> listTasquesPendents = new ArrayList<Inscripcions>();
		    List<Inscripcions> listAlumnesInscrits = new ArrayList<Inscripcions>();

			@Override
			public void valueChanged(ListSelectionEvent arg0) {					
				Query queryDetallGrupMoodle = sessio.createQuery("FROM Grupmoodle WHERE desciripcioGrupMoodle LIKE '" + modelLlista.get(listGrupsMoodle.getSelectedIndex()) + "'");
				llistaDetallGrupMoodle = queryDetallGrupMoodle.list();
								
				IDGroupText.setText(String.valueOf(llistaDetallGrupMoodle.get(0).getIdGrupMoodle()));
				NameGroupText.setText(llistaDetallGrupMoodle.get(0).getNomGrupMoodle());
				DescriptionGroupText.setText(llistaDetallGrupMoodle.get(0).getDesciripcioGrupMoodle());
				ActiveGroupText.setText(String.valueOf(llistaDetallGrupMoodle.get(0).getActiu()));								
								
				/* LLISTA ALUMNES REGISTRATS */
				Query queryAlumnesRegistrats = sessio.createQuery("FROM Inscripcions WHERE idGrup LIKE '" + String.valueOf(llistaDetallGrupMoodle.get(0).getIdGrupMoodle()) + "'");
		        listAlumnesInscrits = queryAlumnesRegistrats.list();
		        
		        for (Inscripcions inscrits : listAlumnesInscrits) {     	
					modelLlistaAlumnseInscrits.addElement(inscrits.getUsuari().getCodiUsuari());		
		        }
		        
		        /* VEURE TASQUES PENDENTS */	
		        listRegisteredStudents.addListSelectionListener(new ListSelectionListener() {
		        	@Override
					public void valueChanged(ListSelectionEvent arg0) {
		        		Query queryTasquesPendents = sessio.createQuery("FROM Inscripcions WHERE idAlumne = (SELECT idUsuari FROM Usuari WHERE codiUsuari LIKE '" + listRegisteredStudents.getSelectedValue() + "')");
		        		listTasquesPendents = queryTasquesPendents.list();

		        		PendingTaskGroupText.setText(String.valueOf(listTasquesPendents.get(0).getTasquesPendents()));
		        		EvaluatedTaskGroupText.setText(String.valueOf(listTasquesPendents.get(0).getTasquesAvaluades()));
		        		
		        		btnEvaluate.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								listTasquesPendents.get(0).setTasquesAvaluades(listTasquesPendents.get(0).getTasquesPendents());
								listTasquesPendents.get(0).setTasquesPendents(0);
								
								PendingTaskGroupText.setText(String.valueOf(listTasquesPendents.get(0).getTasquesPendents()));
				        		EvaluatedTaskGroupText.setText(String.valueOf(listTasquesPendents.get(0).getTasquesAvaluades()));
				        		
				        		lblAVISO.setText("Task of " + listTasquesPendents.get(0).getUsuari().getCodiUsuari() + " avaluated");
							}	        			
		        		});	        		
					}	        	
		        });
		        
		        /* LLISTA ALUMNES NO REGISTRATS */
		        String sQ = "";
		        List<Usuari> listAlumnesNoInscrits = new ArrayList<Usuari>();
		        
		        for( int i = 0; i<listAlumnesInscrits.size(); i++) {
		        	if(i == 0) {
		        		sQ = "WHERE NOT ( idUsuari = " + listAlumnesInscrits.get(i).getUsuari().getIdUsuari();
		        	}
		        	else {
		        		sQ = sQ + " OR idUsuari = " + listAlumnesInscrits.get(i).getUsuari().getIdUsuari();
		        	}
		        }
		        sQ = sQ + " ) AND idTipusUsuari = 3";
		        
		        Query queryAlumnesNoRegistrats = sessio.createQuery("FROM Usuari " + sQ);
		        listAlumnesNoInscrits = queryAlumnesNoRegistrats.list();

		        for (Usuari noInscrits : listAlumnesNoInscrits) {
		        	if(noInscrits.getDataBaixa() == null && !noInscrits.getBloquejat()) {	        					       	
		        		comboBoxModel.addElement(noInscrits.getCodiUsuari());		
		        	}
		        }
		       // comboBox.listen
		        comboBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
					  	btnRegistrarAlumne.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								Query queryDetallgrupMoodleUnregisteredStudent = sessio.createQuery("FROM Grupmoodle WHERE desciripcioGrupMoodle LIKE '" + listGrupsMoodle.getSelectedValue() + "'");      	
								Grupmoodle grup = new Grupmoodle(); 	        
								grup = (Grupmoodle) queryDetallgrupMoodleUnregisteredStudent.uniqueResult();
								
								Query queryAlumneAInscriure = sessio.createQuery("FROM Usuari WHERE codiUsuari LIKE '" + comboBox.getSelectedItem() + "'");      												
								List<Usuari> llistaAlumneAInscriure = new ArrayList<Usuari>();		
								llistaAlumneAInscriure = queryAlumneAInscriure.list();												
								Usuari alumneAInscriure = new Usuari(); 	        
								alumneAInscriure = (Usuari) queryAlumneAInscriure.uniqueResult();
								
								//System.out.println(alumneAInscriure.getCodiUsuari());

								Inscripcions alumneInscrit = new Inscripcions (alumneAInscriure, grup, 0, 0, true);		
								
								sessio.beginTransaction();
								sessio.save(alumneInscrit);
								sessio.getTransaction().commit();
															
								lblAVISO.setText("Student " + alumneAInscriure.getCodiUsuari() + ", " + alumneAInscriure.getIdUsuari() + " registered into " + grup.getDesciripcioGrupMoodle());
							}			  		
					  	});
					}

					        	
		        });
			}									
        });	
	}
}
