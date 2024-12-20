/*
A basic extension of the JApplet class
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JFrameApp extends JFrame
{

	public void JFrameApp() {

	}

	public void Init(JFrame jf)
	{
		// symantec.itools.lang.Context.setApplet(this);
		
		// getRootPane().putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);
		

		jf.setLayout(null);
		jf.setSize(640,480);
		
		jMenuBar = new JMenuBar();
		jf.setJMenuBar(jMenuBar);
		jf.getContentPane().add(jMenuBar);
		jMenuBar.move(0,267);
		
		AddMenus(jMenuBar);
		

		JButton1.setText("jbutton");
		jf.getContentPane().add(JButton1);
		JButton1.setBounds(24,120,144,144);
		JButton1.setActionCommand("jbutton");
		jf.getContentPane().add(JTextArea1);
		JTextArea1.setBounds(24,48,400,108);
		jf.setVisible(true);
		//}}
	
		//{{REGISTER_LISTENERS
		SymAction lSymAction = new SymAction();
		
		menuFile_itemExit.addActionListener(lSymAction);
		
		menuView_itemLeftRight.addActionListener(lSymAction);
		menuView_itemTopBottom.addActionListener(lSymAction);
		menuView_item1View.addActionListener(lSymAction);
		
		menuChat_itemSend.addActionListener(lSymAction);
		menuChat_itemRefresh.addActionListener(lSymAction);
		menuChat_itemClear.addActionListener(lSymAction);
		
		menuContacts_itemMy.addActionListener(lSymAction);
		menuContacts_itemAdd.addActionListener(lSymAction);
		menuContacts_itemImport.addActionListener(lSymAction);
		menuContacts_itemView.addActionListener(lSymAction);
		
		menuHelp_itemAbout.addActionListener(lSymAction);
		
		JButton1.addActionListener(lSymAction);
		//}}
	}

	//{{DECLARE_CONTROLS
	public static JFrameApp jFrameApp;
	JButton JButton1 = new JButton();
	JTextArea JTextArea1 = new JTextArea();
	JMenuBar jMenuBar = new JMenuBar();
	JMenu menuFile;
	JMenuItem menuFile_itemOpen;
	JMenuItem menuFile_itemSave;
	JMenuItem menuFile_itemExit = new JMenuItem();
	JMenu menuView;
	JMenuItem menuView_itemLeftRight = new JMenuItem();
	JMenuItem menuView_itemTopBottom = new JMenuItem();
	JMenuItem menuView_item1View = new JMenuItem();
	JMenu menuIPAddrs = new JMenu();
	JMenu menuIPAddrs_menuMyIps = new JMenu();
	JMenu menuIPAddrs_menuFriendIps = new JMenu();
	JMenu menuIPAddrs_menuProxies = new JMenu();
	JMenuItem menuIPAddrs_itemIPv6Secure = new JMenuItem();
	JMenu menuChat = new JMenu();
	JMenuItem menuChat_itemSend = new JMenuItem();
	JMenuItem menuChat_itemRefresh = new JMenuItem();
	JMenuItem menuChat_itemClear = new JMenuItem();
	JMenu menuContacts = new JMenu();
	JMenuItem menuContacts_itemMy = new JMenuItem();
	JMenuItem menuContacts_itemAdd = new JMenuItem();
	JMenuItem menuContacts_itemImport = new JMenuItem();
	JMenuItem menuContacts_itemView = new JMenuItem();
	
	JMenu menuHelp = new JMenu();
	JMenuItem menuHelp_itemAbout = new JMenuItem();
	//}}

	
	public void AddMenus(JMenuBar jBar) {
		
		menuFile = new JMenu();
		menuFile.setText("File");
		menuFile.setActionCommand("File");
		menuFile.setFont(new Font("Dialog", Font.PLAIN, 12));
		menuFile.setMnemonic((int)'F');
		jBar.add(menuFile);
		
		menuFile_itemOpen = new JMenuItem();
		menuFile_itemOpen.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuFile_itemOpen.setText("Open...");
		menuFile_itemOpen.setActionCommand("Open...");
		menuFile_itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		menuFile_itemOpen.setMnemonic((int)'O');
		menuFile.add(menuFile_itemOpen);
		
		menuFile_itemSave = new JMenuItem();
		menuFile_itemSave.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuFile_itemSave.setText("Save");
		menuFile_itemSave.setActionCommand("Save");
		menuFile_itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		menuFile_itemSave.setMnemonic((int)'S');
		menuFile.add(menuFile_itemSave);

		menuFile_itemExit.setText("Exit");
		menuFile_itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.ALT_MASK));
		menuFile_itemExit.setActionCommand("Exit");
		menuFile_itemExit.setMnemonic((int)'X');
		menuFile.add(menuFile_itemExit);
		
		
		menuView = new JMenu();
		menuView.setText("View");
		menuView.setActionCommand("View");
		menuView.setMnemonic((int)'V');
		jBar.add(menuView);
				
		menuView_itemLeftRight = new JMenuItem();
		menuView_itemLeftRight.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuView_itemLeftRight.setText("Left-Right");
		menuView_itemLeftRight.setActionCommand("LeftRight");
		menuView_itemLeftRight.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Event.CTRL_MASK));
		menuView_itemLeftRight.setMnemonic((int)'L');
		menuView.add(menuView_itemLeftRight);
		
		menuView_itemTopBottom = new JMenuItem();
		menuView_itemTopBottom.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuView_itemTopBottom.setText("Top-Bottom");
		menuView_itemTopBottom.setActionCommand("TopBottom");
		menuView_itemTopBottom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
		menuView_itemTopBottom.setMnemonic((int)'C');
		menuView.add(menuView_itemTopBottom);
		
		
		menuView_item1View = new JMenuItem();
		menuView_item1View.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuView_item1View.setText("1-View");
		menuView_item1View.setActionCommand("1View");
		menuView_item1View.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, Event.CTRL_MASK));
		menuView_item1View.setMnemonic((int)'P');
		menuView.add(menuView_item1View);
		
		
		menuIPAddrs = new JMenu();
		menuIPAddrs.setText("IP Addresses");
		menuIPAddrs.setActionCommand("IPAddrs");
		menuIPAddrs.setMnemonic((int)'I');
		jBar.add(menuIPAddrs);
		
		
		menuIPAddrs_menuMyIps = new JMenu();
		menuIPAddrs_menuMyIps.setText("My IP's");
		menuIPAddrs_menuMyIps.setActionCommand("MyIPs");
		menuIPAddrs_menuMyIps.setMnemonic((int)'M');
		menuIPAddrs.add(menuIPAddrs_menuMyIps);
		
		menuIPAddrs_menuFriendIps = new JMenu();
		menuIPAddrs_menuFriendIps.setText("Friend IP's");
		menuIPAddrs_menuFriendIps.setActionCommand("FriendIPs");
		menuIPAddrs_menuFriendIps.setMnemonic((int)'F');
		menuIPAddrs.add(menuIPAddrs_menuFriendIps);
		
		menuIPAddrs_menuProxies = new JMenu();
		menuIPAddrs_menuProxies.setText("Proxy IP's");
		menuIPAddrs_menuProxies.setActionCommand("ProxyIPs");
		menuIPAddrs_menuProxies.setMnemonic((int)'P');
		menuIPAddrs.add(menuIPAddrs_menuProxies);
		
		menuIPAddrs_itemIPv6Secure = new JMenuItem();
		menuIPAddrs_itemIPv6Secure.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuIPAddrs_itemIPv6Secure.setText("IPv6 Secure");
		menuIPAddrs_itemIPv6Secure.setActionCommand("IPv6secure");		
		menuIPAddrs_itemIPv6Secure.setMnemonic((int)'6');
		menuIPAddrs.add(menuIPAddrs_itemIPv6Secure);
		
		
		menuChat = new JMenu();
		menuChat.setText("Chat");
		menuChat.setActionCommand("Chat");
		menuChat.setMnemonic((int)'H');
		jBar.add(menuChat);
		
		menuChat_itemSend = new JMenuItem();
		menuChat_itemSend.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuChat_itemSend.setText("Send");
		menuChat_itemSend.setActionCommand("Send");
		menuChat_itemSend.setMnemonic((int)'S');
		menuChat.add(menuChat_itemSend);
		
		menuChat_itemRefresh = new JMenuItem();
		menuChat_itemRefresh.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuChat_itemRefresh.setText("Re-Fresh");
		menuChat_itemRefresh.setActionCommand("ReFresh");
		menuChat_itemRefresh.setMnemonic((int)'F');
		menuChat.add(menuChat_itemRefresh);
		
		menuChat_itemClear = new JMenuItem();
		menuChat_itemClear.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuChat_itemClear.setText("Clear");
		menuChat_itemClear.setActionCommand("Clear");
		menuChat_itemClear.setMnemonic((int)'C');
		menuChat.add(menuChat_itemClear);
		
		
		menuContacts = new JMenu();
		menuContacts.setText("Contacts");
		menuContacts.setActionCommand("Contacts");
		menuContacts.setMnemonic((int)'C');
		jBar.add(menuContacts);
		
		menuContacts_itemMy = new JMenuItem();
		menuContacts_itemMy.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuContacts_itemMy.setText("My Contact");
		menuContacts_itemMy.setActionCommand("My Contact");
		menuContacts_itemMy.setMnemonic((int)'M');
		menuContacts.add(menuContacts_itemMy);
		
		menuContacts_itemAdd = new JMenuItem();
		menuContacts_itemAdd.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuContacts_itemAdd.setText("Add Contact");
		menuContacts_itemAdd.setActionCommand("AddContact");
		menuContacts_itemAdd.setMnemonic((int)'A');
		menuContacts.add(menuContacts_itemAdd);
				
		menuContacts_itemImport = new JMenuItem();
		menuContacts_itemImport.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuContacts_itemImport.setText("Import Contacts");
		menuContacts_itemImport.setActionCommand("ImportContacts");
		menuContacts_itemImport.setMnemonic((int)'I');
		menuContacts.add(menuContacts_itemImport);
		
		
		menuContacts_itemView = new JMenuItem();
		menuContacts_itemView.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuContacts_itemView.setText("View Contacts");
		menuContacts_itemView.setActionCommand("ViewContacts");
		menuContacts_itemView.setMnemonic((int)'V');
		menuContacts.add(menuContacts_itemView);
		
		
		menuHelp = new JMenu();
		menuHelp.setText("Help");
		menuHelp.setActionCommand("Help");
		menuHelp.setMnemonic((int)'H');		
		jBar.add(menuHelp);
		
		menuHelp_itemAbout = new JMenuItem();
		menuHelp_itemAbout.setHorizontalTextPosition(SwingConstants.RIGHT);
		menuHelp_itemAbout.setText("About...");
		menuHelp_itemAbout.setActionCommand("About...");
		menuHelp_itemAbout.setMnemonic((int)'A');
		menuHelp.add(menuHelp_itemAbout);
		
	}
	
	public static void main(String args[]) {
		
		jFrameApp = new JFrameApp();
		jFrameApp.setLayout(new GridLayout(1,1));
		jFrameApp.setSize(480,360);
		jFrameApp.Init(jFrameApp);
		jFrameApp.setVisible(true);
		jFrameApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	class SymAction implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Object object = event.getSource();

			if (object == menuFile_itemExit)
				appExit(event);
			
			else if (object == menuView_itemLeftRight)
				viewChange(event, "LeftRight");
			else if (object == menuView_itemTopBottom)
				viewChange(event, "TopBottom");
			else if (object == menuView_item1View)
				viewChange(event, "1View");
			
			else if (object == menuChat_itemSend) 
				chatCommand(event, "Send");
			else if (object == menuChat_itemRefresh) 
				chatCommand(event, "Refresh");
			else if (object == menuChat_itemClear) 
				chatCommand(event, "Clear");
			
			else if (object == menuContacts_itemMy) 
				addEditContact(event, 0);
			else if (object == menuContacts_itemAdd) 
				addEditContact(event, 1);
			else if (object == menuContacts_itemImport) 
				addEditContact(event, -1);
			else if (object == menuContacts_itemView) 
				viewContact(event);
			
			else if (object == menuHelp_itemAbout)
				about(event);
			
			else if (object == JButton1)
				JButton1_actionPerformed(event);
			
		}
	}

	void appExit(ActionEvent event) {
		// We don't log exit events ;)
		System.exit(0);
	}

	void viewChange(ActionEvent event, String whichView) {
		JTextArea1.append("View menu, view changed to " + whichView + ", event: " + event + "\n");
	}
	
	
	void chatCommand(ActionEvent event, String whichCommand) { 
		JTextArea1.append("Menu Chat = command " + whichCommand + ", event: " + event + "\n");
	}
	
	
	void addEditContact(ActionEvent event, int who) {
		if (who == 0)
			JTextArea1.append("Menu Contact => edit \"My Contact\", event: " + event + "\n");
		else if (who > 0)
			JTextArea1.append("Menu Contact => add/edit contacts, event: " + event + "\n");
		else if (who < 0)
			JTextArea1.append("Menu Contact => import contacts, event: " + event + "\n");
	}
	
	void viewContact(ActionEvent event) {
	
		JTextArea1.append("Menu Contact => view contacts, event: " + event + "\n");
	}


	void about(ActionEvent event) {
	
		JTextArea1.append("About menu clicked, event: " + event + "\n");
	}
	

	void JButton1_actionPerformed(ActionEvent event)
	{
		// to do: code goes here.
			 
		JButton1_actionPerformed_Interaction1(event);
	}

	void JButton1_actionPerformed_Interaction1(ActionEvent event)
	{
		try {
			JTextArea1.setText("hallo");
		} catch (Exception e) {
		}
	}
}
