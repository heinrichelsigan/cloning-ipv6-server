/*
	A basic extension of the java.applet.Applet class
 */

import java.awt.*;
import java.applet.*;

public class mesboard extends Applet
{
	public void init()
	{
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(null);
		setBackground(java.awt.Color.lightGray);
		setSize(445,299);
		add(tName);
		tName.setBounds(72,36,312,24);
		label1.setText("Name:");
		add(label1);
		label1.setBounds(12,36,60,24);
		label2.setText("Email:");
		add(label2);
		label2.setBounds(12,66,60,24);
		add(tEmail);
		tEmail.setBounds(72,66,312,24);
		add(tSubject);
		tSubject.setBounds(72,126,312,24);
		add(tData);
		tData.setBounds(12,156,420,96);
		label4.setText("Topic:");
		add(label4);
		label4.setBounds(12,126,60,24);
		bSend.setLabel("Eintragen");
		add(bSend);
		bSend.setBackground(java.awt.Color.darkGray);
		bSend.setForeground(java.awt.Color.white);
		bSend.setBounds(24,6,95,24);
		bDelete.setLabel("Löschen");
		add(bDelete);
		bDelete.setBackground(java.awt.Color.gray);
		bDelete.setForeground(java.awt.Color.white);
		bDelete.setBounds(156,6,95,24);
		label3.setText("URL:");
		add(label3);
		label3.setBounds(12,96,60,24);
		add(tUrl);
		tUrl.setBounds(72,96,312,24);
		tStatus.setEditable(false);
		add(tStatus);
		tStatus.setBounds(72,264,360,24);
		bClose.setLabel("Beenden");
		add(bClose);
		bClose.setBackground(java.awt.Color.gray);
		bClose.setForeground(java.awt.Color.white);
		bClose.setBounds(288,6,95,24);
		label5.setText("Status:");
		add(label5);
		label5.setBounds(12,264,60,24);
		//}}
	
		//{{REGISTER_LISTENERS
		SymAction lSymAction = new SymAction();
		bSend.addActionListener(lSymAction);
		bDelete.addActionListener(lSymAction);
		bClose.addActionListener(lSymAction);
		SymText lSymText = new SymText();
		tName.addTextListener(lSymText);
		tEmail.addTextListener(lSymText);
		tUrl.addTextListener(lSymText);
		tSubject.addTextListener(lSymText);
		tData.addTextListener(lSymText);
		//}}
	}
	
	//{{DECLARE_CONTROLS
	java.awt.TextField tName = new java.awt.TextField();
	java.awt.Label label1 = new java.awt.Label();
	java.awt.Label label2 = new java.awt.Label();
	java.awt.TextField tEmail = new java.awt.TextField();
	java.awt.TextField tSubject = new java.awt.TextField();
	java.awt.TextArea tData = new java.awt.TextArea();
	java.awt.Label label4 = new java.awt.Label();
	java.awt.Button bSend = new java.awt.Button();
	java.awt.Button bDelete = new java.awt.Button();
	java.awt.Label label3 = new java.awt.Label();
	java.awt.TextField tUrl = new java.awt.TextField();
	java.awt.TextField tStatus = new java.awt.TextField();
	java.awt.Button bClose = new java.awt.Button();
	java.awt.Label label5 = new java.awt.Label();
	//}}

	class SymAction implements java.awt.event.ActionListener
	{
		public void actionPerformed(java.awt.event.ActionEvent event)
		{
			Object object = event.getSource();
			if (object == bSend)
				bSend_ActionPerformed(event);
			else if (object == bDelete)
				bDelete_ActionPerformed(event);
			else if (object == bClose)
				bClose_ActionPerformed(event);
		}
	}

	void bSend_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		bSend_ActionPerformed_Interaction1(event);
	}

	void bSend_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
		try {
			sendPost();
		} catch (Exception e) {
		}
	}

	void bDelete_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		bDelete_ActionPerformed_Interaction1(event);
	}

	void bDelete_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
		try {
			clearFields();
		} catch (Exception e) {
		}
	}
    private void clearFields() {
        tData.setText("");
        tName.setText("");
        tSubject.setText("");
        tEmail.setText("");        
        tUrl.setText("");        
    }
    
	void bClose_ActionPerformed(java.awt.event.ActionEvent event)
	{
		// to do: code goes here.
			 
		bClose_ActionPerformed_Interaction1(event);
	}

	void bClose_ActionPerformed_Interaction1(java.awt.event.ActionEvent event)
	{
		try {
			this.destroy();
		} catch (Exception e) {
		}
	}

	class SymText implements java.awt.event.TextListener
	{
		public void textValueChanged(java.awt.event.TextEvent event)
		{
			Object object = event.getSource();
			if (object == tName)
				tName_TextValueChanged(event);
			else if (object == tEmail)
				tEmail_TextValueChanged(event);
			else if (object == tUrl)
				tUrl_TextValueChanged(event);
			else if (object == tSubject)
				tSubject_TextValueChanged(event);
			else if (object == tData)
				tData_TextValueChanged(event);
		}
	}

	void tName_TextValueChanged(java.awt.event.TextEvent event)
	{
		// to do: code goes here.
			 
		tName_TextValueChanged_Interaction1(event);
	}

	void tName_TextValueChanged_Interaction1(java.awt.event.TextEvent event)
	{
		try {
	        inputSizeExceeded(tName,32,"Name");
		} catch (Exception e) {
		}
	}

	void tEmail_TextValueChanged(java.awt.event.TextEvent event)
	{
		// to do: code goes here.
			 
		tEmail_TextValueChanged_Interaction1(event);
	}

	void tEmail_TextValueChanged_Interaction1(java.awt.event.TextEvent event)
	{
		try {
	        inputSizeExceeded(tEmail,32,"Email");
		} catch (Exception e) {
		}
	}

	void tUrl_TextValueChanged(java.awt.event.TextEvent event)
	{
		// to do: code goes here.
			 
		tUrl_TextValueChanged_Interaction1(event);
	}

	void tUrl_TextValueChanged_Interaction1(java.awt.event.TextEvent event)
	{
		try {
		    inputSizeExceeded(tUrl,64,"Url");
		} catch (Exception e) {
		}
	}

	void tSubject_TextValueChanged(java.awt.event.TextEvent event)
	{
		// to do: code goes here.
			 
		tSubject_TextValueChanged_Interaction1(event);
	}

	void tSubject_TextValueChanged_Interaction1(java.awt.event.TextEvent event)
	{
		try {
		    inputSizeExceeded(tSubject,32,"Topic");
		} catch (Exception e) {
		}
	}

	void tData_TextValueChanged(java.awt.event.TextEvent event)
	{
		// to do: code goes here.
			 
		tData_TextValueChanged_Interaction1(event);
	}

	void tData_TextValueChanged_Interaction1(java.awt.event.TextEvent event)
	{
		try {
			inputSizeExceeded(tData,16384,"Dateneingabe");
		} catch (Exception e) {
		}
	}
    public void inputSizeExceeded(java.awt.TextComponent tInput,int MAX_INPUT,String sInput) {
            String sBuf1 = new String(tInput.getText());
            Integer iTmp1 = new Integer(MAX_INPUT);
                if (sBuf1.length()>MAX_INPUT) {   
                    try {
                        tInput.setText(sBuf1.substring(0,MAX_INPUT));
                            tInput.setCaretPosition(MAX_INPUT);
                        } catch (Exception e) {}
                        statusShow(sInput +" darf maximal "+iTmp1.toString() +" Zeichen enthalten!");
                }
        }
 
    private void statusShow(String s) {
        tStatus.setText(s);
    }
    private boolean parseURL() {    
        String tmpStr3= new String(tUrl.getText());
        
        if (tmpStr3.startsWith("http://")==false) 
        {
            if (tmpStr3.startsWith("ftp://")==false) {
                statusShow("URL muss mit http:// oder ftp:// beginnen !");
                return false;
            }
        }
        if (tmpStr3.indexOf(".")<1) 
        {   statusShow("URL: \".\" fehlt oder ist an falscher Position !");
            return false;
        }
        return true;            
    }
    
    private boolean parseEmail() {
        int i1;
        String tmpStr1= new String(tEmail.getText());
        if (tmpStr1.indexOf(".")<1) 
        {   statusShow("Email: \".\" fehlt oder ist an falscher Position !");
            return false;
        }
        if ((i1=tmpStr1.indexOf("@"))<1) 
        {   statusShow("Email: \"@\" fehlt oder ist an falscher Position !");
            return false;
        }
        if (tmpStr1.endsWith("@"))
        {   statusShow("Email: \"@\" am Ende ungültig !");
            return false;
        }
        if (tmpStr1.endsWith("."))
        {   statusShow("Email: \".\" am Ende ungültig !");
            return false;
        }
        String tmpStr2=new String(tmpStr1.substring(i1+1));
        // statusShow(tmpStr2);
        if (tmpStr2.indexOf("@")!=(-1)) 
        {   statusShow("Email: Mehr als ein \"@\" vorhanden !");
            return false;
        }
        

        return true;
    }
 
    private void sendPost()
    {
        
        String tmpStr2;
        String tmpStr3;
        
        mClientData v = new mClientData();
        
        
        if ((tName.getText().length()<2) || (tSubject.getText().length()<2)
                || (tData.getText().length()<2)) {
            statusShow("Zu wenig Eingaben !!");
            return ;
        }
        if (tEmail.getText().length()>0) {
            if ((parseEmail())==false) return;
        }
        
        if (tUrl.getText().length()>0) {
            if ((parseURL())==false) return;
        }
            
        
            
        v.vStack.removeAllElements();
        v.addIn("<p><b>"+ tName.getText()+"</b></p>");
        v.addIn("<p>Email: <a href=\"mailto:"+ tEmail.getText()+"\">"+tEmail.getText()+"</a>");
        v.addIn("<p>URL: <a href=\""+ tUrl.getText()+"\" target=\"_top\">"+tUrl.getText()+"</a>");        
        v.addIn("<p><i>Topic: "+ tSubject.getText()+"</i></p>");
        v.addIn("<p><B>Message:</B></p><pre>" + tData.getText()+"</pre><hr>");
        if ((v.pushOut())==false)
                statusShow("Fehler ist beim Eintragen passiert !!!");
        else {
                clearFields();
                statusShow("Eingetragen !");
            }
        }


}
