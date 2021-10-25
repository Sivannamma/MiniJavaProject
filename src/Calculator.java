import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Calculator extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isFirst=true; // in order to know if its the first time we enter the calculator, than we dont want to calc just yet
	double ans; // main variable that stores the ans
	double ans_help; // the second val we enter for the operation
	double ans_second_help; // the first val we enter for the calc
	String op; // stores our operation - * / +
	boolean setOp; // flag to determine if we now want the second val after we chose operation
	JPanel p;
	JButton calc_number [];
	JButton calc_op [];
	JTextField label_cost;

	public Calculator() {
		setOp= false; // because we haven't started the calculation yet.
		op="?"; // we haven't set any operation yet.

		// the size of the window
		this.setSize(200, 200);
		// setting that the program is terminated when we close 'X' on the window as
		// well.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// not resizeable
		this.setResizable(false);
		// font
		Font f = new Font("Arial", Font.BOLD, 13);
		this.setFont(f);



		// setting the label that will show the cost each time, starts with 0, and closing the option to be editable.
		label_cost= new JTextField(16);
		//	label_cost.setText("0");
		label_cost.setEditable(false);

		// setting the numbers 0...9
		calc_number= new JButton[10];
		for (int i = 0; i < calc_number.length; i++) {
			calc_number[i]= new JButton(i+"");

			// adding the listener to the numbers buttons
			calc_number[i].addActionListener(this);
		}

		// setting the operations - * / + =
		calc_op= new JButton[6];
		calc_op[0]=new JButton("-");
		calc_op[1]=new JButton("+");
		calc_op[2]=new JButton("/");
		calc_op[3]=new JButton("*");
		calc_op[4]=new JButton("=");
		calc_op[5]=new JButton("C");

		// adding the listener to the operations buttons
		for (int i = 0; i < calc_op.length; i++) {
			calc_op[i].addActionListener(this);
		}

		// defining p, setting the buttons inside, and adding it to the frame.
		p = new JPanel();
		settingPanel();
		this.add(p);
	}
	// adding the buttons to the panel - p
	private void settingPanel() {
		p.add(label_cost);
		p.add(calc_op[0]);
		p.add(calc_number[0]);
		p.add(calc_number[1]);
		p.add(calc_number[2]);
		p.add(calc_op[1]);
		p.add(calc_number[3]);
		p.add(calc_number[4]);
		p.add(calc_number[5]);
		p.add(calc_op[2]);
		p.add(calc_number[6]);
		p.add(calc_number[7]);
		p.add(calc_number[8]);
		p.add(calc_op[3]);
		p.add(calc_op[4]);
		p.add(calc_number[9]);
		p.add(calc_op[5]);
	}

	private void setNum(int num)
	{
		if(setOp==false) {
			ans_second_help=ans_second_help*10+num;
			label_cost.setText(Double.toString(ans_second_help));
		}
		else {
			ans_help=ans_help*10 + num;
			label_cost.setText(Double.toString(ans_help));
		}
	}

	private void calc_answer() {
		switch (op) {
		case "*" :
			ans=ans_second_help*ans_help;
			break;
		case "/" :
			ans=ans_second_help/ans_help;
			break;
		case "-" :
			ans=ans_second_help-ans_help;
			break;
		case "+" :
			ans=ans_second_help+ans_help;
			break;
		}
		setOp=false;
	}

	public void setOp(String e_op) {
		ans_help=0;
		if(!isFirst)
			calc_answer();
		setOp=true;
		op="*";
		isFirst=false;
	}

	public void setOp() {
		ans_help=0;
		if(!isFirst)
			calc_answer();
		setOp=true;
		isFirst=false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str= e.getActionCommand();
		if(str.equals("C")) {
			ans=0;
			ans_help=0;
			ans_second_help=0;
			setOp=false;
			isFirst=true;
			label_cost.setText(Double.toString(ans));
		}
		else if(str.equals("*") || str.equals("/") || str.equals("-") || str.equals("+")) {
			op=str;
			setOp();
		}
		else if(str.equals("="))
		{
			calc_answer();
			isFirst=true;
			setOp=false;
			ans_second_help=ans;
			//ans_help=0;
			label_cost.setText(Double.toString(ans));
		}
		else
			// extracting the string from the e get action.
			// the default means we pressed a number
			setNum(Integer.valueOf(e.getActionCommand()));
	}


}
