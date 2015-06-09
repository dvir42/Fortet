package jStuff;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JForm extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8217358615628467671L;

	private final JTextField text;
	private final JButton send;

	public JForm(String name) {
		super(name);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 100);
		JLabel label = new JLabel("Name:");
		text = new JTextField();
		text.setSize(200, 20);
		send = new JButton("submit");
		send.addActionListener(this);
		text.addActionListener(this);
		add(label, BorderLayout.NORTH);
		add(text);
		add(send, BorderLayout.SOUTH);
		setVisible(true);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		notifyAll();
		dispose();
	}

	public synchronized String getResult() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return text.getText();
	}

	public static void main(String[] args) {
		System.out.println(new JForm("").getResult());
	}

}
