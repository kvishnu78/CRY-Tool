import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import linearAlgebra.operations;
import modularArithmetic.modularOperations;

public class Decryption
{
	public static void decrypt(int[][] key_k1,int[][] key_k2,int n,String F)
	{
		
		operations obj_lin=new operations();
		modularOperations obj_mod=new modularOperations();
		Scanner s=new Scanner(System.in);
		String cipherText;
		int[][] plaintext=new int[n][n];
		int[][] ciphertext=new int[n][n];
		System.out.println("Enter the Cipher message text!");
		cipherText=s.nextLine();
		int length=cipherText.length();
		try {
			if(length==0)
				throw new Exception();
		}catch(Exception e)
		{
			System.out.println("ENDING ABRUPTLY AS NO STRING ENTERED,Good bye");
			System.exit(1);
		}
		if(length<n*n)
		{
			System.out.println("WARNING: Size of Cipher Text is less than the matrix size!\n");
		}
		//Generating Cipher text matrix
		int k=0;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(k==length)
				{
					k=0;
				}
				
					int x=cipherText.charAt(k);
					ciphertext[i][j]=obj_mod.mod(x, 128);
				
			k++;
			}
		}
		
		//performing matrix addition
		int temp;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				temp=key_k2[i][j];
				temp=obj_mod.modularAddictiveInverse(temp,128);
				ciphertext[i][j]+=temp;//P=C-K2
			}
		}
		//performing matrix multiplication
		int[][] keyK1_inverse=new int[n][n];
		keyK1_inverse=obj_lin.inverse(key_k1,n);
		plaintext=obj_lin.multiplyMatrix(ciphertext,keyK1_inverse,n);//P=(C-K2)+K1				
		
		//bringing it under mod 128
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				temp=(int)plaintext[i][j];
				temp=obj_mod.mod(temp, 128);
				plaintext[i][j]=temp;
			}
		}
		
		/*W R I T I N G  P L A I N T E X T  A N D  C I P H E R  T E X T  T O  F I L E*/
		String [][] str=new String[n][n];
		String inputFile="\n\n\nDECRYPTION"+"\n"+"\n"+"CipherText:"+cipherText+"\n"+"Plaintext:";
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				str[i][j]=Character.toString((char) plaintext[i][j]);
			}
		}
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				inputFile+=str[i][j];
			}
		}
		try {
			BufferedWriter toWrite=new BufferedWriter(new FileWriter(F, true));
			toWrite.newLine();
			toWrite.write(inputFile);
			toWrite.close();
			
		}catch(Exception io) {
			System.out.println("An error occurred.");
			io.printStackTrace();
		}
		System.out.println("DECRYPTION COMPLETED! DATA ADDED TO THE TARGET FILE\n");
	}
	
}