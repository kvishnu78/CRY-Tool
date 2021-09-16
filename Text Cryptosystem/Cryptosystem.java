
/*                                       AUTHOR:VISHNU K
 * A CRYPTOSYSTEM to combine Hill Cipher and Affine ciper to ENCRYPT and DECRYPT text sequences
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import linearAlgebra.operations;
import modularArithmetic.modularOperations;

class Cryptosystem
{
	static operations obj_lin=new operations();
	static modularOperations obj_mod=new modularOperations();
	static String key_s1,key_s2;
	public static void main(String args[])
	{
		System.out.println("*********-->    Welcome to CRY TOOL! <--*********");
		System.out.println("\n         +++++++++++++ Version 1.1  ++++++++++++++++++");
		System.out.println("\n         *** Developed by Vishnu K  ***");
		System.out.println("\n      -->    Department of CSE, ASE - Bengaluru    <--");
		System.out.println("\nFor queries/feedback email at vishnuk782000@gmail.com \n");
		System.out.println("\n\nABOUT:\n");
		System.out.println(" CRY TOOL is a tool that combines the concepts of Hill cipher and Affine cipher to establish security!"
				+ "\n   ENCRYPTION:C=((P*K)+M) mod n"
				+ "\n   DECRYPTION:P=((C-M)*K^(-1)"
				+ "\n   where,\n   P-Plain Text index\n   C-Cipher text index\n   K-Key 1(invertible under mod n)\n   M-Key 2"
				+ "\n NOTE:P,C,K,M are all Matrices of order a*a");
		System.out.println("\n\n INSTRUCTIONSs:\n"
				+ "   1.Give Inputs without SPACE, if there is a space all the characters after the space are neglected"
				+ "\n   2.Make sure the Key Matrix K(key 1) is Invertible(determinant!=0) under mod n-->This tool uses n=128"
				+ "\n   3.If a*a is the order of the matrix make sure that the size of the input is also of the same size,if not:\n"
				+ "\t case a:KEY MATRIX:\n\t\t*If the Key string length if more than the size only the first a*a characters will be considered."
				+ "\n\t\t*If the Key string length is less than the size,key string will be repeated like Vigenere Cipher.\n"
				+ "\t case b:CIPHER TEXT MATRIX/PLAIN TEXT MATRIX:\n\t\t*If the input string length is less than the a*a,FILLER CHARACTERS WILL BE USED."
				+ "\n\t\t*If input string length is more than the size,only the first a*a characters will be taken\n"
				+ "   4.Final Output will be written in a file whose name will be asked in the beginning\n"
				+ "   5.Once the Key pair is set the program has to be reloaded to change the pair\n"
				+ "NOTE:SUGGESTED KEYS-HILLMAGIC(3*3),GYBNQKURP(3*3),vish(2*2)\n\n\n");
		
	
		String filePath = null;
		File myObj = null;
		Scanner s = new Scanner(System.in);
		/* F I L E   C R E A T I O N */
		System.out.println("Enter the output filename : ");
		String Fname = s.next();

		try {
			String os = System.getProperty("os.name");//os.name is a property

			if (os.toUpperCase().startsWith("WINDOWS"))//starts with checks if,it starts with WINDOWS 
			{
				filePath = System.getenv("USERPROFILE");//for file path using enviroment variables
			} 
			else
			{
				filePath = System.getenv("HOME");
			}

			System.out.println(filePath);
			Date date = Calendar.getInstance().getTime();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMMdd_HHmmss");
			String currentTime = sdf.format(date);

			filePath = filePath + File.separatorChar + Fname + "_" + currentTime + ".txt";//File.seperatorChar is \\ in java
			myObj = new File(filePath);
			if (myObj.exists()) {
				boolean fp = myObj.delete();
				try {
					if (!fp)
						throw new FileSystemException("Unable to delete");
				} catch (FileSystemException e) {
					System.out.println(e.getMessage());
				}
			}
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getAbsolutePath());
			} else {
				System.out.println("File already exists");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		/* F I L E   C R E A T E D */		
//<.................................................................................................................................................................................................>		
		/* R E A D I N G   K E Y S*/
		System.out.println("Enter number of rows in key matrix");
		int matSize;
		matSize=keySize(s);
		int[][] key_k1=new int[matSize][matSize];
		int[][] key_k2=new int[matSize][matSize];
		
		
		//key 1
		System.out.println("Enter key 1 String");
		key_s1=s.next();
		key_k1=KeyGetter(s,matSize,key_s1);
		/*
		 * V A L I D A T I N G  K E Y  O N E  E N T R Y 
		 * */
		int num1,num2;
		num1=obj_lin.determinant(key_k1,matSize);
		num2=obj_mod.modularMultiplicativeInverse(num1, 128);
		while(num1==0 || num2==-1)
		{
			System.out.println("Inverse doesn't exist, decryption is not possible!");
			if(num1==0)
			{
				System.out.println("Enter a non Singular key");
	
			}
			if(num2==-1)
			{
				System.out.println("The given key is not invertible under the given modular operation,Try again!");
			}
			key_s1=s.next();
			key_k1=KeyGetter(s,matSize,key_s1);
			num1=obj_lin.determinant(key_k1,matSize);
			num2=obj_mod.modularMultiplicativeInverse(num1, 128);//checking if multiplicative inverse exists for the given matrix's determinant
		}
		//key 2
		System.out.println("Enter key 2 String");
		key_s2=s.next();
		key_k2=KeyGetter(s,matSize,key_s2);
		/* K E Y S  G E N E R A T E D */
//<.................................................................................................................................................................................................>
		/* K E Y  W R I T I N G  I N  F I L E*/
		try {
			BufferedWriter toWrite=new BufferedWriter(new FileWriter(filePath, true));
			toWrite.newLine();
			String intro1="*********-->    Generated using CRY Tool! <--*********";
			String intro2="\n         +++++++++++++ Version 1.1  ++++++++++++++++++";
			String intro3="         *** Developed by Vishnu K ***";
			String intro4="      -->    Department of CSE, ASE - Bengaluru    <--";
			toWrite.write(intro1+"\n"+intro2+"\n"+intro3+"\n"+intro4+"\n\n\n"+"K1="+key_s1+"\n"+"K2="+key_s2+"\n\n\n");
			toWrite.close();
			
		}catch(IOException io) {
			System.out.println("An error occurred.");
			io.printStackTrace();
		}
		/* I N T R O  A N D  K E Y  W R I T I N G  I N  F I L E  C O M P L E T E D*/
//<.................................................................................................................................................................................................>		
		/*M E N U  D R I V E N  C O D E  B E G I N S*/
		Encryption E=new Encryption();
		Decryption D=new Decryption();
		int choice;
		for(int i=0;i==0;)
		{
			System.out.println("Enter Chioce\n1.ENCRYPT\n2.DECRYPT\n3.EXIT\n");
			choice=getChoice(s);
			switch(choice)
			{
			case 1:E.encrypt(key_k1,key_k2,matSize,filePath);
				break;
			case 2:D.decrypt(key_k1,key_k2,matSize,filePath);
				break;
			case 3:System.out.println("THANK YOU FOR USING CRY TOOL, GOOD BYE!");
				i++;
				break;
			default:System.out.println("ENTER ONLY VALID CHOICE");
					break;
			}
		}
	}//End of main
//<.................................................................................................................................................................................................>
	
	
	/* F U N C T I O N S  B E G I N*/
	private static int keySize(Scanner s)
	{
		int c = -1;
		try {
			c = s.nextInt();
		} catch (InputMismatchException ie) {
			s.next();
			System.out.println("Invalid number...Enter only Numbers greater than 2");
			c = keySize(s);
		}
		try {
			if(c<2)
			{
				throw new Exception();
			}
		}catch(Exception e)
		{
			System.out.println("Invalid number...Enter only Numbers greater than 2");
			c = keySize(s);
		}
		return c;
	}
	private static int getChoice(Scanner s) 
	{
		int c = -1;
		try {
			c = s.nextInt();
		} catch (InputMismatchException ie) {
			s.next();
			System.out.println("Enter a valid option");
			c = getChoice(s);
		}
		try {
			if(!(c==1||c==2||c==3))
			{
				throw new Exception();
			}
		}catch(Exception e)
		{
			System.out.println("Enter a Valid option");
			c = getChoice(s);
		}
		return c;
	}

	private static int[][] KeyGetter(Scanner s,int m,String key_s)
	{
		int[][] keyMatrix=new int [m][m];
		int k=0;
		if(key_s.length()>m*m)
			System.out.println("Since the Key length is greater than matrix size only the first "+m*m+ " characters will be considered");
		if(key_s.length()<m*m)
			System.out.println("Since the Key length is less than matriz size,Key will be repeated.");
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<m;j++)
			{
				if(k==key_s.length())
				{
					k=0;
				}
				keyMatrix[i][j]=obj_mod.mod(key_s.charAt(k),128);
				k++;
			}
		}
		return keyMatrix;
	}
	
	
}
		
