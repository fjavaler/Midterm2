package savingsAccount;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.SwingConstants;

public class About extends JFrame
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					About frame = new About();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public About()
	{
		setTitle("SLCC");
		setIconImage(Toolkit.getDefaultToolkit().getImage(About.class.getResource("/savingsAccount/slcclogo.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 336, 209);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(About.class.getResource("/savingsAccount/SLCCLogo1.png")));
		lblNewLabel.setBounds(10, 85, 296, 71);
		contentPane.add(lblNewLabel);

		JLabel lblCsis = new JLabel(" CSIS1410 Object Oriented Programming");
		lblCsis.setHorizontalAlignment(SwingConstants.CENTER);
		lblCsis.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblCsis.setBounds(10, 13, 300, 19);
		contentPane.add(lblCsis);

		JLabel lblMyName = new JLabel("Frederick Javalera");
		lblMyName.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMyName.setBounds(6, 58, 300, 22);
		contentPane.add(lblMyName);

		JLabel lblMidTerm = new JLabel("Mid Term 2 Exam");
		lblMidTerm.setHorizontalAlignment(SwingConstants.CENTER);
		lblMidTerm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMidTerm.setBounds(10, 35, 300, 22);
		contentPane.add(lblMidTerm);
	}
}
