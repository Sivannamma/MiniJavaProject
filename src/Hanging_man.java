import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Hanging_man extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// array of strings to store the words we can play with
	JPanel main_panel;
	JPanel main_right_panel ;
	JPanel first_panel ;
	JPanel second_panel ;
	JPanel third_panel;
	int countErrors=0;
	// create a separator
	JSeparator s = new JSeparator();

	String play_word;
	JTextField [] discover_word;
	int words_size=16;
	String [] words = {"Kabul", "Algiers" , "Vienna",
			"Baku", "Bogota", "Tallinn" ,
			"London" , "Jerusalem", "Rome",
			"Tokyo", "Lisbon", "Bangkok","Washington",
			"London", "Madrid", "Bucharest"};

	// Array to store the a , b ,c .... z
	String [] ABC= {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	JButton [] ABC_buttons;
	JButton play;

	public Hanging_man() {
		ABC_buttons= new JButton[26];
		main_panel = new JPanel();
		main_right_panel = new JPanel();
		first_panel = new JPanel();
		second_panel = new JPanel();
		third_panel = new JPanel();
		// initializing the arrays of buttons 
		for (int i = 0; i < ABC_buttons.length; i++) {
			ABC_buttons[i]=new JButton(ABC[i]);
		}
		init_window();
	}

	private void init_window() {
		// the size of the window
		this.setSize(630, 360);
		// setting that the program is terminated when we close 'X' on the window as
		// well.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// not resizeable
		this.setResizable(false);
		// font
		Font f = new Font("Arial", Font.BOLD, 13);
		this.setFont(f);

		// set layout as vertical
		s.setOrientation(SwingConstants.VERTICAL);

		play= new JButton("Start Playing");

		randomize_word();
		settingPanel();
		this.add(main_panel);
		main_panel.setLayout(new GridLayout(1,0));
		main_panel.add(third_panel);
		main_panel.add(main_right_panel);
		setVisibleFalse();
		setActionListiner();
	}


	// adding the buttons of the ABC---Z + adding the labels of the word that need to discover
	private void settingPanel() {

		main_right_panel.setLayout(new GridLayout(0,1));

		for (int i = 0; i < ABC_buttons.length; i++) {
			first_panel.add(ABC_buttons[i], BorderLayout.CENTER);
		}

		main_right_panel.add(first_panel);

		for (int i = 0; i < discover_word.length; i++) {
			second_panel.add(discover_word[i], BorderLayout.AFTER_LINE_ENDS);
		}

		main_right_panel.add(second_panel);
		third_panel.add(play);
		Graphics g =third_panel.getGraphics();

	}

	private void randomize_word() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 15 + 1);
		play_word=words[randomNum];
		System.out.println(randomNum);

		discover_word= new JTextField[play_word.length()];
		for (int i = 0; i < discover_word.length; i++) {
			discover_word[i]= new JTextField("       ");

		}
		System.out.println(play_word);
	}
	public void setVisibleTrue() {
		for (int i = 0; i < ABC_buttons.length; i++) {
			ABC_buttons[i].setVisible(true);
		}
		// adding the labels of the word that need to discover
		for (int i = 0; i < discover_word.length; i++) {
			discover_word[i].setVisible(true);
		}
	}
	public void setVisibleFalse() {
		for (int i = 0; i < ABC_buttons.length; i++) {
			ABC_buttons[i].setVisible(false);
		}
		// adding the labels of the word that need to discover
		for (int i = 0; i < discover_word.length; i++) {
			discover_word[i].setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str= e.getActionCommand();
		if(str.contentEquals("Start Playing"))
		{
			setVisibleTrue();
			play.setVisible(false);
		}
		int pos=checkingWhichALetterIsPressed(str);
		if (pos!=-1) {
			checkingLetter(str,pos);
		}
		checkIfFinished();
	}

	private void checkingLetter(String str, int pos) {
		boolean flag= false;
		for (int i = 0; i < play_word.length(); i++) {
			if(play_word.charAt(i)==str.toLowerCase().charAt(0) || play_word.charAt(i)==str.toUpperCase().charAt(0)) {
				//System.out.println("entered here , position : " +i);
				discover_word[i].setText(str);
				ABC_buttons[pos].setBackground(Color.white);
				flag=true;
			}
		}
		if(flag==false) {
			countErrors++;
			this.repaint();
		}

	}

	// in this function we go over the ABC_BUTTONS, and updating the spesific cell
	// to be pressed -> because we don't want to allow another clicking on the same button.
	private int checkingWhichALetterIsPressed(String str) {
		for (int i = 0; i < ABC_buttons.length; i++) {
			if(ABC_buttons[i].getText().equals(str)) {
				ABC_buttons[i].setEnabled(false); // do not be able to be clicked
				//	ABC_buttons[i].setBackground(Color.cyan);
				return i;
			}
		}
		return -1;
	}

	public void setActionListiner() {
		play.addActionListener(this);
		for (int i = 0; i < ABC_buttons.length; i++) {
			ABC_buttons[i].addActionListener(this);
		}

		//set not editable
		for (int i = 0; i < discover_word.length; i++) {
			discover_word[i].setEnabled(false);
			discover_word[i].setBackground(Color.white);
			discover_word[i].setDisabledTextColor(Color.black);
		}

	}


	// this function is checking if the letters that the user picked filled the entire word.
	// if so we finished, if the count goes over 6 errors - loses
	public void checkIfFinished() {
		if(countErrors>=6) {
			setClickableFalse();
			JOptionPane.showMessageDialog(null, "Game-Over, the word is " + play_word);
			return;
		}
		for (int i = 0; i < discover_word.length; i++) {
			if(discover_word[i].getText().charAt(0)==' ')
				return;
		}
		setClickableFalse();
		JOptionPane.showMessageDialog(null, "You won!!");
	}

	private void setClickableFalse() {
		for (int i = 0; i < ABC_buttons.length; i++) {
			ABC_buttons[i].setEnabled(false);
		}

	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.draw(new Line2D.Float(100, 100, 100, 360));
		g2.draw(new Line2D.Float(220, 100, 100, 100));
		g2.draw(new Line2D.Float(220, 100, 220, 150));
		switch (countErrors) {
		case 1:
			g2.drawOval(185, 150, 65, 65);
			break;

		case 2:
			g2.drawOval(185, 150, 65, 65);
			g2.draw(new Line2D.Float(220, 215, 220, 300));
			break;
		case 3:
			g2.drawOval(185, 150, 65, 65);
			g2.draw(new Line2D.Float(220, 215, 220, 300));
			g2.draw(new Line2D.Float(220, 215, 180, 250));
			break;

		case 4:
			g2.drawOval(185, 150, 65, 65);
			g2.draw(new Line2D.Float(220, 215, 220, 300));
			g2.draw(new Line2D.Float(220, 215, 180, 250));
			g2.draw(new Line2D.Float(220, 215, 260, 250));
			break;
		case 5:
			g2.drawOval(185, 150, 65, 65);
			g2.draw(new Line2D.Float(220, 215, 220, 300));
			g2.draw(new Line2D.Float(220, 215, 180, 250));
			g2.draw(new Line2D.Float(220, 215, 260, 250));
			g2.draw(new Line2D.Float(220, 300, 180, 340));
			break;

		case 6:
			g2.drawOval(185, 150, 65, 65);
			g2.draw(new Line2D.Float(220, 215, 220, 300));
			g2.draw(new Line2D.Float(220, 215, 180, 250));
			g2.draw(new Line2D.Float(220, 215, 260, 250));
			g2.draw(new Line2D.Float(220, 300, 180, 340));
			g2.draw(new Line2D.Float(220, 300, 260, 340));
			break;
		}
	}
}
