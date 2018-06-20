import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

public class GestioAdmin extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldUp;
	private JTextField textFieldDown;
	private JTextField textFieldMod;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestioAdmin frame = new GestioAdmin(null);
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
	public GestioAdmin(Usuari usuari) {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();			     
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session sessio = sessionFactory.openSession();
		
		setResizable(false);
		setTitle("XAVI HIBERNATE XAVI - ADMIN PROFILE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		System.out.println("Gestio Administrador: " + usuari.getCodiUsuari());
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(289, 37, 73, 14);
		contentPane.add(lblId);
		
		JLabel lblNewLabel = new JLabel("User:");
		lblNewLabel.setBounds(289, 62, 73, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(289, 87, 73, 14);
		contentPane.add(lblDni);
		
		JLabel label = new JLabel("Up date:");
		label.setBounds(289, 112, 73, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Down date:");
		label_1.setBounds(289, 132, 71, 14);
		contentPane.add(label_1);
		
		
		
		textFieldUp = new JTextField();
		textFieldUp.setBounds(460, 59, 102, 20);
		contentPane.add(textFieldUp);
		textFieldUp.setColumns(10);
		
		textFieldDown = new JTextField();
		textFieldDown.setColumns(10);
		textFieldDown.setBounds(460, 139, 102, 20);
		contentPane.add(textFieldDown);
		
		textFieldMod = new JTextField();
		textFieldMod.setColumns(10);
		textFieldMod.setBounds(460, 216, 102, 20);
		contentPane.add(textFieldMod);

		/* DESBLOQUEJAR USUARIS */
		JLabel lblBloked = new JLabel(" BLOCKED USERS:");
		lblBloked.setBounds(10, 11, 112, 14);
		contentPane.add(lblBloked);
		
		JList blockedUsersList = new JList();
		blockedUsersList.setBounds(10, 36, 119, 200);
		contentPane.add(blockedUsersList);
		
		DefaultListModel listModelBlockedUser = new DefaultListModel();
		blockedUsersList.setModel(listModelBlockedUser);
		
		JButton btnUnLock = new JButton("UNLOCK");
		btnUnLock.setBounds(20, 247, 102, 23);
		contentPane.add(btnUnLock);
		
		JLabel lblAVISO = new JLabel("");
		lblAVISO.setBounds(289, 256, 310, 14);
		contentPane.add(lblAVISO);
			
		
		JLabel lblAllUsers = new JLabel("ALL USERS:");
		lblAllUsers.setBounds(167, 11, 112, 14);
		contentPane.add(lblAllUsers);
		
			
		List<Usuari> listSelectBlocked = new ArrayList<Usuari>();		
		Query queryBlocked = sessio.createQuery("FROM Usuari WHERE bloquejat LIKE 1");
		listSelectBlocked = queryBlocked.list();
		
		for (Usuari usersBlocked : listSelectBlocked) {     	
			listModelBlockedUser.addElement(usersBlocked.getCodiUsuari());		
        }
		 
		blockedUsersList.addListSelectionListener(new ListSelectionListener() {	
			List<Usuari> listSelectBlockedSelected = new ArrayList<Usuari>();		
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {	
				Query queryBlockedSelected = sessio.createQuery("FROM Usuari WHERE codiUsuari LIKE '" + blockedUsersList.getSelectedValue() + "'");
				listSelectBlockedSelected = queryBlockedSelected.list();
				
				btnUnLock.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {						
						listSelectBlockedSelected.get(0).setBloquejat(false);
						
		        		sessio.beginTransaction();
		        		sessio.update(listSelectBlockedSelected.get(0));
		        		sessio.getTransaction().commit();
		        		
						lblAVISO.setText(listSelectBlockedSelected.get(0).getCodiUsuari() + " UNLOCKED");
					}				
				});
			}			
		});
		
		DefaultListModel listModelAllUsers = new DefaultListModel();
		
		JButton btnUpDate = new JButton("UP DATE");
		btnUpDate.setBounds(460, 33, 102, 23);
		contentPane.add(btnUpDate);
		
		JButton btnDownDate = new JButton("DOWN DATE");
		btnDownDate.setBounds(460, 108, 102, 23);
		contentPane.add(btnDownDate);
		
		JButton btnModUser = new JButton("MOD USER");
		btnModUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		
		btnModUser.setBounds(460, 180, 102, 23);
		contentPane.add(btnModUser);
		
		JLabel lblNewLabel_1 = new JLabel("SEARCH USERS:");
		lblNewLabel_1.setBounds(10, 290, 112, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblSearchGroups = new JLabel("SEARCH GROUPS:");
		lblSearchGroups.setBounds(10, 362, 112, 14);
		contentPane.add(lblSearchGroups);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(167, 33, 100, 23);
		contentPane.add(comboBox);
		
		DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
		comboBox.setModel(comboBoxModel);
		
		JLabel idtext = new JLabel("");
		idtext.setBounds(372, 37, 78, 14);
		contentPane.add(idtext);
		
		JLabel usertext = new JLabel("");
		usertext.setBounds(372, 62, 78, 14);
		contentPane.add(usertext);
		
		JLabel dnitext = new JLabel("");
		dnitext.setBounds(372, 87, 78, 14);
		contentPane.add(dnitext);
		
		JLabel updatetext = new JLabel("");
		updatetext.setBounds(372, 112, 78, 14);
		contentPane.add(updatetext);
		
		JLabel downdatetext = new JLabel("");
		downdatetext.setBounds(372, 132, 78, 14);
		contentPane.add(downdatetext);
		
		JLabel abracadabra = new JLabel("");
		abracadabra.setBounds(460, 251, 102, 14);
		contentPane.add(abracadabra);
		
		/* COMBO BOX */
		List<Usuari> listUsers1 = new ArrayList<Usuari>();		
		Query queryUsers1 = sessio.createQuery("FROM Usuari WHERE bloquejat LIKE 0");
		listUsers1 = queryUsers1.list();
		
		for (Usuari usersAll : listUsers1) {   
			comboBoxModel.addElement(usersAll.getCodiUsuari());
        }
		
		
		/*List<Usuari> listUsers = new ArrayList<Usuari>();		
		Query queryUsers = sessio.createQuery("FROM Usuari WHERE bloquejat LIKE 0");
		listUsers = queryUsers.list();

		for (Usuari usersAll : listUsers) {   
			listModelAllUsers.addElement(usersAll.getCodiUsuari());
        }*/
		
		comboBox.addActionListener(new ActionListener() {
			List<Usuari> listSelectAllUser = new ArrayList<Usuari>();		

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Query queryAllUser = sessio.createQuery("FROM Usuari WHERE codiUsuari LIKE '" + comboBox.getSelectedItem() + "'");
				listSelectAllUser = queryAllUser.list();
			
				System.out.println(comboBox.getSelectedItem());
				idtext.setText(String.valueOf(listSelectAllUser.get(0).getIdUsuari()));
				usertext.setText(listSelectAllUser.get(0).getCodiUsuari());
				dnitext.setText(listSelectAllUser.get(0).getDni());
				//updatetext.setText(String.valueOf(listSelectAllUser.get(0).getDataAlta()));
				//downdatetext.setText(String.valueOf(listSelectAllUser.get(0).getDataBaixa()));
				
				if(listSelectAllUser.get(0).getDataAlta() != null) {
					updatetext.setText(String.valueOf(listSelectAllUser.get(0).getDataAlta()));
				}
				else {
					updatetext.setText("");
				}
				
				if(listSelectAllUser.get(0).getDataBaixa() != null) {
					downdatetext.setText(String.valueOf(listSelectAllUser.get(0).getDataBaixa()));
				}
				else {
					downdatetext.setText("");
				}
				
				/* UP DATE BUTTON */
				btnUpDate.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						abracadabra.setText("YEAR-MONTH-DAY");
						if(textFieldUp.getText().isEmpty()) {
							lblAVISO.setText("UP DATE IS EMPTY");
						}
						else {							
							listSelectAllUser.get(0).setDataAlta(Date.valueOf(textFieldUp.getText()));

							sessio.beginTransaction();
			        		sessio.update(listSelectAllUser.get(0));
			        		sessio.getTransaction().commit();
			        		
							lblAVISO.setText(listSelectAllUser.get(0).getDataAlta() + " MODIFIED");
							textFieldUp.setText("");
						}									
					}			
				});
				
				/* DOWN DATE BUTTON */
				btnDownDate.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						abracadabra.setText("YEAR-MONTH-DAY");
						if(textFieldDown.getText().isEmpty()) {
							lblAVISO.setText("DOWN DATE IS EMPTY");
						}
						else {							
							listSelectAllUser.get(0).setDataBaixa(Date.valueOf(textFieldDown.getText()));
							
			        		sessio.beginTransaction();
			        		sessio.update(listSelectAllUser.get(0));
			        		sessio.getTransaction().commit();
			        		
							lblAVISO.setText(listSelectAllUser.get(0).getDataBaixa() + " MODIFIED");
							textFieldDown.setText("");
						}									
					}			
				});
				
				/* MODIFICATION USER */
				btnModUser.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(textFieldMod.getText().isEmpty()) {
							lblAVISO.setText("USER IS EMPTY");
						}
						else {							
							listSelectAllUser.get(0).setCodiUsuari(textFieldMod.getText());

			        		sessio.beginTransaction();
			        		sessio.update(listSelectAllUser.get(0));
			        		sessio.getTransaction().commit();
			        		
							lblAVISO.setText(listSelectAllUser.get(0).getCodiUsuari() + " MODIFIED");
							textFieldMod.setText("");
						}							
					}			
				});		
			}						
		});	
		
		
		/* SEARCH USERS */
		
		
		
	}
}
