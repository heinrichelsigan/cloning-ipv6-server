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
		jf.setJMenuBar(JMenuBar1);
		jf.getContentPane().add(JMenuBar1);
		JMenuBar1.move(0,267);
		
		fileMenu.setText("File");
		fileMenu.setActionCommand("File");
		fileMenu.setFont(new Font("Dialog", Font.PLAIN, 12));
		fileMenu.setMnemonic((int)'F');
		JMenuBar1.add(fileMenu);

		newItem.setHorizontalTextPosition(SwingConstants.RIGHT);
		newItem.setText("New");
		newItem.setActionCommand("New");
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		newItem.setMnemonic((int)'N');
		fileMenu.add(newItem);
		
		openItem.setHorizontalTextPosition(SwingConstants.RIGHT);
		openItem.setText("Open...");
		openItem.setActionCommand("Open...");
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		openItem.setMnemonic((int)'O');
		fileMenu.add(openItem);
		
		saveItem.setHorizontalTextPosition(SwingConstants.RIGHT);
		saveItem.setText("Save");
		saveItem.setActionCommand("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		saveItem.setMnemonic((int)'S');
		fileMenu.add(saveItem);

		exitItem.setText("Exit");
		exitItem.setActionCommand("Exit");
		exitItem.setMnemonic((int)'X');
		fileMenu.add(exitItem);
		
		editMenu.setText("Edit");
		editMenu.setActionCommand("Edit");
		editMenu.setMnemonic((int)'E');
		JMenuBar1.add(editMenu);
		
		cutItem.setHorizontalTextPosition(SwingConstants.RIGHT);
		cutItem.setText("Cut");
		cutItem.setActionCommand("Cut");
		cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		cutItem.setMnemonic((int)'T');
		editMenu.add(cutItem);
		
		copyItem.setHorizontalTextPosition(SwingConstants.RIGHT);
		copyItem.setText("Copy");
		copyItem.setActionCommand("Copy");
		copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
		copyItem.setMnemonic((int)'C');
		editMenu.add(copyItem);
		
		pasteItem.setHorizontalTextPosition(SwingConstants.RIGHT);
		pasteItem.setText("Paste");
		pasteItem.setActionCommand("Paste");
		pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
		pasteItem.setMnemonic((int)'P');
		editMenu.add(pasteItem);
		
		helpMenu.setText("Help");
		helpMenu.setActionCommand("Help");
		helpMenu.setMnemonic((int)'H');
		
		JMenuBar1.add(helpMenu);
		aboutItem.setHorizontalTextPosition(SwingConstants.RIGHT);
		aboutItem.setText("About...");
		aboutItem.setActionCommand("About...");
		aboutItem.setMnemonic((int)'A');
		helpMenu.add(aboutItem);

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
		aboutItem.addActionListener(lSymAction);
		JButton1.addActionListener(lSymAction);
		//}}
	}

	//{{DECLARE_CONTROLS
	JButton JButton1 = new JButton();
	JTextArea JTextArea1 = new JTextArea();
	JMenuBar JMenuBar1 = new JMenuBar();
	JMenu fileMenu = new JMenu();
	JMenuItem newItem = new JMenuItem();
	JMenuItem openItem = new JMenuItem();
	JMenuItem saveItem = new JMenuItem();
	JMenuItem exitItem = new JMenuItem();
	JMenu editMenu = new JMenu();
	JMenuItem cutItem = new JMenuItem();
	JMenuItem copyItem = new JMenuItem();
	JMenuItem pasteItem = new JMenuItem();
	JMenu helpMenu = new JMenu();
	JMenuItem aboutItem = new JMenuItem();
	//}}

	
	public static void main(String args[]) {
        	JFrameApp jFrameApp = new JFrameApp();
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
			if (object == aboutItem)
				aboutItem_actionPerformed(event);
			else if (object == JButton1)
				JButton1_actionPerformed(event);
			
		}
	}

	void aboutItem_actionPerformed(ActionEvent event)
	{
		// to do: code goes here.
        JTextArea1.append("just a test\n");
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
