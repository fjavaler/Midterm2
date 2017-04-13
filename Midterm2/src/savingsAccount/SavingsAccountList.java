package savingsAccount;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SavingsAccountList
{
	public static ArrayList<SavingsAccount> saList = new ArrayList<SavingsAccount>();
	private static ObjectOutputStream output; // writes data to file
	private static ObjectInputStream input; // reads data from a file
	private static File file;

	public SavingsAccountList()
	{
	}

	/** read the account list from accounts.ser **/
	public static boolean readAccountFile()
	{
		// set success to true if file read successfully
		boolean success = false;
		/*
		 * try statement to open, write and close the file one or more catch
		 * blocks to handle exceptions optional finally
		 */
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int result = 0;
		try
		{
			result = fileChooser.showOpenDialog(null);
		}
		catch (HeadlessException e)
		{
			JOptionPane.showMessageDialog(null, "An error occurred while opening this file.");
		}

		if (result == JFileChooser.CANCEL_OPTION)
		{
			return false;
		}

		file = fileChooser.getSelectedFile();
		deserialize();
		return success = true;
	}// end ReadAccountList()

	/****************************************************
	 * Method : deserialize
	 *
	 * Purpose : The deserialize method reads the file located at fileName and
	 * stores its contents in a Person ArrayList which it then returns.
	 *
	 * Parameters : fileName - The name and location of the file to be written.
	 *
	 * Returns : This method returns an ArrayList of Persons.
	 *
	 ****************************************************/
	@SuppressWarnings("unchecked")
	public static void deserialize()
	{
		try (ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(file.getPath())))
		{
			saList = (ArrayList<SavingsAccount>) inStream.readObject();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("A problem occurred during de-serialization.");
			System.out.println(e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("A problem occurred during de-serialization.");
			System.out.println(e.getMessage());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("A problem occurred during de-serialization.");
			System.out.println(e.getMessage());
		}
	}

	/** write the account list to accounts.ser **/
	public static void writeAccountFile()
	{
		serialize();
	}// end writeAccountList()
	
	/** write the account list to accounts.ser **/
	public static void writeAccountFileSaveAs()
	{
		/*
		 * try statement to open, write and close the file one or more catch
		 * blocks to handle exceptions optional finally
		 */
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int result = 0;
		try
		{
			result = fileChooser.showSaveDialog(null);
		}
		catch (HeadlessException e)
		{
			JOptionPane.showMessageDialog(null, "An error occurred while opening this file.");
		}

		if (result == JFileChooser.CANCEL_OPTION)
		{
			return;
		}

		file = fileChooser.getSelectedFile();
		serialize();
	}

	/****************************************************
	 * Method : serialize
	 *
	 * Purpose : The serialize method serializes a list and writes it to a file
	 *
	 * Parameters : None.
	 *
	 * Returns : This method does not return a value.
	 *
	 ****************************************************/
	public static void serialize()
	{
		if (file != null)
		{
			try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file.getPath())))
			{
				outStream.writeObject(saList);
			}
			catch (IOException e)
			{
				System.out.println("A problem occurred during serialization.");
				System.out.println(e.getMessage());
			}
		}
		else
		{
			writeAccountFileSaveAs();
			try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file.getPath())))
			{
				outStream.writeObject(saList);
			}
			catch (IOException e)
			{
				System.out.println("A problem occurred during serialization.");
				System.out.println(e.getMessage());
			}
		}
	}
}// end SavingsAccountList
