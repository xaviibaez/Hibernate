import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JList;

public class GestioAlumne extends JFrame {

	private JPanel contentPane;
	private JPasswordField psdChange;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestioAlumne frame = new GestioAlumne(null);
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
	public GestioAlumne(Usuari alumne) {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();			     
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session sessio = sessionFactory.openSession();
		
		setResizable(false);
		setTitle("XAVI HIBERNATE XAVI - ALUMNE PROFILE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel AVIS = new JLabel("");
        AVIS.setBounds(20, 416, 194, 14);
        contentPane.add(AVIS);
		
		JLabel lblId = new JLabel("ID:");		
		lblId.setBounds(15, 16, 58, 14);
		JLabel lblName = new JLabel("Name:");		
		lblName.setBounds(15, 36, 58, 14);
		JLabel lblSurname = new JLabel("Surname 1:");		
		lblSurname.setBounds(15, 56, 73, 14);
		JLabel lblSurname_1 = new JLabel("Surname 2:");		
		lblSurname_1.setBounds(15, 76, 73, 14);
		JLabel lblDni = new JLabel("DNI:");	
		lblDni.setBounds(15, 96, 55, 14);
		JLabel lblUser = new JLabel("User:");	
		lblUser.setBounds(15, 116, 58, 14);
		JLabel lblCourse = new JLabel("Course:");		
		lblCourse.setBounds(15, 136, 73, 14);
		JLabel lblHighDate = new JLabel("Up date:");		
		lblHighDate.setBounds(15, 156, 73, 14);
		JLabel lblDownDate = new JLabel("Down date:");
		lblDownDate.setBounds(15, 176, 71, 14);
		
		JLabel lblIdText = new JLabel((String) null);		
		lblIdText.setBounds(98, 16, 87, 14);
		JLabel lblNomText = new JLabel("");
		lblNomText.setBounds(98, 36, 87, 14);
		JLabel lblSurnameText = new JLabel((String) null);		
		lblSurnameText.setBounds(98, 56, 87, 14);
		JLabel lblSurname2Text = new JLabel((String) null);		
		lblSurname2Text.setBounds(98, 76, 87, 14);
		JLabel lblDNIText = new JLabel((String) null);		
		lblDNIText.setBounds(98, 96, 84, 14);
		JLabel lblUserText = new JLabel("");
		lblUserText.setBounds(98, 116, 84, 14);
		JLabel lblCourseText = new JLabel((String) null);		
		lblCourseText.setBounds(98, 136, 84, 14);
		JLabel lblDataAltaText = new JLabel((String) null);
		lblDataAltaText.setBounds(79, 156, 84, 0);
		JLabel lblDataBaixaText = new JLabel((String) null);
		lblDataBaixaText.setBounds(79, 176, 84, 0);
		
		JLabel lblGroups = new JLabel("GROUPS:");
		lblGroups.setBounds(237, 16, 95, 14);
		JLabel lblDetails = new JLabel("DETAILS:");
		lblDetails.setBounds(237, 181, 87, 14);
		
		JLabel lblPasswordUpdate = new JLabel("");
		lblPasswordUpdate.setBounds(20, 322, 192, 14);
		contentPane.add(lblPasswordUpdate);

		
		System.out.println("Gestio Alumne: " + alumne.getCodiUsuari());
		
		/* DETALLS ALUMNE */
		lblIdText.setText(alumne.getIdUsuari().toString());
		lblNomText.setText(alumne.getNomUsuari());
		lblSurnameText.setText(alumne.getCognom1usuari());
		lblSurname2Text.setText(alumne.getCognom2usuari());
		lblDNIText.setText(alumne.getDni());	
		lblUserText.setText(alumne.getCodiUsuari());
		lblCourseText.setText(alumne.getCursos().getDescripcio());
		
		if(alumne.getDataAlta() != null) {
			lblDataAltaText.setText(alumne.getDataAlta().toString());
		}
		else {
			lblDataAltaText.setText("");
		}
		
		if(alumne.getDataBaixa() != null) {
			lblDataBaixaText.setText(alumne.getDataBaixa().toString());
		}
		else {
			lblDataBaixaText.setText("");
		}
		
		/* CANVIAR PASSWORD */		
		JLabel lblPasswordNew = new JLabel("New Password:");	
		lblPasswordNew.setBounds(15, 223, 100, 14);
		psdChange = new JPasswordField();
		psdChange.setBounds(10, 247, 153, 20);
		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.setBounds(10, 278, 153, 23);
		
		btnChangePassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(psdChange.getText().equals(alumne.getPassword())) {//Si la nova password és igual a l'anterior
					psdChange.setText("");
					lblPasswordUpdate.setText("Password not changed");
				}
				else {//Sino, fa el update
					String psdNova = psdChange.getText();
					alumne.setPassword(psdNova);
		    		sessio.beginTransaction();
		    		sessio.update(alumne);
		    		sessio.getTransaction().commit();
					lblPasswordUpdate.setText("Password changed");
				}
			}		
		});
		
		/* ----------------------------------------------------------------- */
		
		contentPane.setLayout(null);
		contentPane.add(lblHighDate);
		contentPane.add(lblName);
		contentPane.add(lblId);
		contentPane.add(lblDownDate);
		contentPane.add(lblPasswordNew);
		contentPane.add(psdChange);
		contentPane.add(btnChangePassword);
		contentPane.add(lblSurname_1);
		contentPane.add(lblSurname);
		contentPane.add(lblIdText);
		contentPane.add(lblNomText);
		contentPane.add(lblSurnameText);
		contentPane.add(lblSurname2Text);
		contentPane.add(lblCourse);
		contentPane.add(lblDataAltaText);
		contentPane.add(lblDataBaixaText);
		contentPane.add(lblCourseText);
		contentPane.add(lblDni);
		contentPane.add(lblUser);
		contentPane.add(lblDNIText);
		contentPane.add(lblUserText);
		contentPane.add(lblGroups);
		contentPane.add(lblDetails);
		
		JLabel lblIdGroupMoodle = new JLabel("ID Group Moodle:");
		lblIdGroupMoodle.setBounds(238, 206, 122, 14);
		contentPane.add(lblIdGroupMoodle);
		
		JLabel lblNameGroupMoodle = new JLabel("Name Group Moodle:");
		lblNameGroupMoodle.setBounds(238, 231, 122, 14);
		contentPane.add(lblNameGroupMoodle);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(237, 256, 100, 14);
		contentPane.add(lblDescription);
		
		JLabel lblActive = new JLabel("Active:");
		lblActive.setBounds(237, 282, 100, 14);
		contentPane.add(lblActive);
		
		JLabel lblTeacher = new JLabel("Teacher:");
		lblTeacher.setBounds(237, 307, 100, 14);
		contentPane.add(lblTeacher);
		
		JLabel lblIDGroupMoodleText = new JLabel("");
		lblIDGroupMoodleText.setBounds(370, 206, 84, 14);
		contentPane.add(lblIDGroupMoodleText);
		
		JLabel lblNameGroupMoodleText = new JLabel("");
		lblNameGroupMoodleText.setBounds(370, 231, 100, 14);
		contentPane.add(lblNameGroupMoodleText);
		
		JLabel lblDexcriptionText = new JLabel("");
		lblDexcriptionText.setBounds(370, 256, 84, 14);
		contentPane.add(lblDexcriptionText);
		
		JLabel lblActiveText = new JLabel("");
		lblActiveText.setBounds(370, 287, 84, 14);
		contentPane.add(lblActiveText);
		
		JLabel lblTeacherText = new JLabel("");
		lblTeacherText.setBounds(370, 307, 84, 14);
		contentPane.add(lblTeacherText);
		
		JLabel lblTasques = new JLabel("Pending tasks:");
		lblTasques.setBounds(237, 332, 87, 14);
		contentPane.add(lblTasques);
		
		JLabel lblPendingTasksText = new JLabel("");
		lblPendingTasksText.setBounds(370, 332, 84, 14);
		contentPane.add(lblPendingTasksText);
			
		JButton btnAddTask = new JButton("Add Task");
		btnAddTask.setBounds(237, 388, 89, 23);
		contentPane.add(btnAddTask);
		
		JLabel lblNotPendign = new JLabel("Evaluated tasks:");
		lblNotPendign.setBounds(237, 357, 87, 14);
		contentPane.add(lblNotPendign);
		
		JLabel lblEvaluatedTasksText = new JLabel("");
		lblEvaluatedTasksText.setBounds(370, 357, 84, 14);
		contentPane.add(lblEvaluatedTasksText);
		
		JLabel lblUnregisteredGroups = new JLabel("UNREGISTERED GROUPS:");
		lblUnregisteredGroups.setBounds(500, 16, 194, 14);
		contentPane.add(lblUnregisteredGroups);
		
		JButton btnInsertGroup = new JButton("INSERT");
		btnInsertGroup.setBounds(500, 246, 119, 23);
		contentPane.add(btnInsertGroup);		
		
		/* ----------------------------------------------------------------- */
		
		/* VEURE GRUPS MOODLE */	
		JList listGrupsMoodle = new JList();
		listGrupsMoodle.setBounds(240, 42, 119, 128);
		DefaultListModel modelLlista = new DefaultListModel();
		listGrupsMoodle.setModel(modelLlista);	
		
		List<Inscripcions> inscripcions = new ArrayList<Inscripcions>();		
		Query queryIncrits = sessio.createQuery("SELECT i FROM Inscripcions i");
        inscripcions = queryIncrits.list();

        for (Inscripcions inscripcio : inscripcions) {
			if(inscripcio.getUsuari().getIdUsuari().equals(alumne.getIdUsuari())) {
				if(inscripcio.getGrupmoodle().getActiu()) {
					modelLlista.addElement(inscripcio.getGrupmoodle().getNomGrupMoodle());
				}
			}
        }
        
		/* DETALLS GRUPS MOODLE */	      
        listGrupsMoodle.addListSelectionListener(new ListSelectionListener() {    
            List<Grupmoodle> llistaDetallGrupMoodle = new ArrayList<Grupmoodle>();
	        List<Inscripcions> listTasquesPendents = new ArrayList<Inscripcions>();		

			@Override
			public void valueChanged(ListSelectionEvent arg0) {					
				Query queryDetallGrupMoodle = sessio.createQuery("FROM Grupmoodle WHERE nomGrupMoodle LIKE '" + modelLlista.get(listGrupsMoodle.getSelectedIndex()) + "'");
				llistaDetallGrupMoodle = queryDetallGrupMoodle.list();
								
				lblIDGroupMoodleText.setText(String.valueOf(llistaDetallGrupMoodle.get(0).getIdGrupMoodle()));
				lblNameGroupMoodleText.setText(llistaDetallGrupMoodle.get(0).getNomGrupMoodle());
				lblDexcriptionText.setText(llistaDetallGrupMoodle.get(0).getDesciripcioGrupMoodle());
				lblActiveText.setText(String.valueOf(llistaDetallGrupMoodle.get(0).getActiu()));
								
				/* VEURE TASQUES PENDENTS */
				Query queryTasquesPendents = sessio.createQuery("FROM Inscripcions WHERE idAlumne LIKE '" + alumne.getIdUsuari() + "' AND idGrup LIKE '" + llistaDetallGrupMoodle.get(0).getIdGrupMoodle() + "'");
				listTasquesPendents = queryTasquesPendents.list();	
				
				lblPendingTasksText.setText(String.valueOf(listTasquesPendents.get(0).getTasquesPendents()));				
				
				/* AFEGIR TASCA */
				btnAddTask.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						for (Inscripcions tasca : listTasquesPendents) {
							tasca.setTasquesPendents(tasca.getTasquesPendents() + 1); 	
							//listTasquesPendents.get(0).setTasquesPendents(listTasquesPendents.get(0).getTasquesPendents() + 1);
			        		sessio.beginTransaction();
			        		sessio.update(tasca);
			        		//sessio.update(listTasquesPendents.get(0));
			        		sessio.getTransaction().commit();
			        		
							lblPendingTasksText.setText(String.valueOf(listTasquesPendents.get(0).getTasquesPendents()));		
							AVIS.setText("Pending task modified: " + String.valueOf(listTasquesPendents.get(0).getTasquesPendents()));
						}
					}					
				});
				
				/* VEURE TASQUES AVALUADES */
				Query queryTasquesAvaluades = sessio.createQuery("FROM Inscripcions WHERE idAlumne LIKE '" + alumne.getIdUsuari() + "' AND idGrup LIKE '" + llistaDetallGrupMoodle.get(0).getIdGrupMoodle() + "'");
				listTasquesPendents = queryTasquesAvaluades.list();	
				
				lblEvaluatedTasksText.setText(String.valueOf(listTasquesPendents.get(0).getTasquesAvaluades()));					
			}									
        });   
        
        contentPane.add(listGrupsMoodle);								  
        
        /* AUTOINSCRIPCIÓ UNREGISTERED GROUPS */
        JList listUnregisteredAlumne = new JList();
		listUnregisteredAlumne.setBounds(500, 42, 119, 195);
		contentPane.add(listUnregisteredAlumne);
		
		DefaultListModel modelLlistaUnregistered = new DefaultListModel();
		listUnregisteredAlumne.setModel(modelLlistaUnregistered);			
						
		Query querySelectInscripcions = sessio.createQuery("SELECT i FROM Inscripcions i");              
        ArrayList<Inscripcions> inscripcionsNoInscrits = (ArrayList<Inscripcions>) querySelectInscripcions.list();
     
        Query querySelectgrupmoodle = sessio.createQuery("SELECT g FROM Grupmoodle g");             
        ArrayList<Grupmoodle> grups = (ArrayList<Grupmoodle>) querySelectgrupmoodle.list();
        
        DefaultListModel listModelInscrits = new DefaultListModel();
        DefaultListModel listModelNoInscrits = new DefaultListModel();
        DefaultListModel listModel4 = new DefaultListModel();
        
        for(Inscripcions noInscrits : inscripcionsNoInscrits) {			       
	        if(noInscrits.getUsuari().getIdUsuari().equals(alumne.getIdUsuari())) {			       
		        if(noInscrits.getGrupmoodle().getActiu()) {				       
		        	listModelInscrits.addElement(noInscrits.getGrupmoodle().getNomGrupMoodle());
		        }
	        }
        }     
        
        for(Grupmoodle gm : grups) {		        
        	listModelNoInscrits.addElement(gm.getNomGrupMoodle());		       
        } 
        
        /* COMPARAR LES DUES LLISTES */
        List<Object> elements1 = Arrays.asList(listModelInscrits.toArray());
        List<Object> elements2 = Arrays.asList(listModelNoInscrits.toArray());

        Set<Object> unionSet = new HashSet<Object>();
        unionSet.addAll(elements1);
        unionSet.addAll(elements2);
        
        elements1.retainAll(elements2);
        unionSet.removeAll(elements1);
        
        List<Object> elements3 = Arrays.asList(unionSet.toArray());     
        
        for(int i = 0; i < elements3.size(); i++) {
        	listModel4.addElement(elements3.get(i));
        } 
        
        listUnregisteredAlumne.setModel(listModel4);       
        
        /* FER INSCRIPCIÓ */
        listUnregisteredAlumne.addListSelectionListener(new ListSelectionListener() {    			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {		
				
				btnInsertGroup.addActionListener(new ActionListener() {					
					@Override
					public void actionPerformed(ActionEvent arg0) {			
						Query queryDetallgrupMoodleUnregisteredStudent = sessio.createQuery("FROM Grupmoodle WHERE nomGrupMoodle LIKE '" + listUnregisteredAlumne.getSelectedValue() + "'");      	
						Grupmoodle nou = new Grupmoodle(); 	        
						nou = (Grupmoodle) queryDetallgrupMoodleUnregisteredStudent.uniqueResult();
						System.out.println(nou.getDesciripcioGrupMoodle());
						
						Inscripcions alumneInscrit = new Inscripcions (alumne, nou, 0, 0, true);						
						
						sessio.beginTransaction();
						sessio.save(alumneInscrit);
						sessio.getTransaction().commit();
						
						AVIS.setText(alumne.getCodiUsuari() + " INSERT INTO " + nou.getNomGrupMoodle());
						/* ACTUALITZAR LLISTES */
						//listUnregisteredAlumne.setModel(listModel4);
												
					}      	
		        });
			}       	
        });	              
	}
}
