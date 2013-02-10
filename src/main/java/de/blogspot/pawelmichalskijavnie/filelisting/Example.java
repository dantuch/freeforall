package de.blogspot.pawelmichalskijavnie.filelisting;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Example extends JPanel {
	private static final String PATH_TO_LIST = "C:";

	public Example() {
		initUI();
	}

	public final void initUI() {

		setLayout(new BorderLayout());
		final DefaultListModel model = new DefaultListModel();
		JList list = new JList(model);
		JScrollPane pane = new JScrollPane(list);

		add(pane, BorderLayout.NORTH);


		final JButton runButton = new JButton("Run");
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(false);
				SwingUtilities.invokeLater(new FilesListerAsProducerConsumer(model, PATH_TO_LIST, runButton));
			}
		});

		add(runButton);

	}

	public static void main(String s[]) {
		JFrame frame = new JFrame("Producer-Consumer example on listing files");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Example());
		frame.setSize(260, 200);
		frame.setVisible(true);
	}
}