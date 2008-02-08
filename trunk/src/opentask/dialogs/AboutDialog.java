/**
 * 
 */
package opentask.dialogs;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import opentask.*;

/**
 * @author rassler
 *
 */
public class AboutDialog extends JDialog implements ActionListener {

	public AboutDialog(Frame owner)
	{
		super(owner, "OpenTask" + Version.getVersion(), true);

		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		JLabel name = new JLabel("OpenTask " + Version.getVersion());
		name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(name);
		name = new JLabel("(c) 2008, Jochen Rassler\n\n");
		name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(name);
		name = new JLabel("This software is licensed under the GNU GPL Version 2.");
		name.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		add(name);

		JButton bDone = new JButton("Done!");
		bDone.addActionListener(this);
		add(bDone);
		
		pack();
}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		setVisible(false);
	}

}
