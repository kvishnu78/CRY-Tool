import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.math.*;
import java.io.IOException;
import linearAlgebra.operations;
import modularArithmetic.modularOperations;

public class Encryption
{
	public static void encrypt(int[][] key_k1,int[][] key_k2,int n,String F)
	{
		
		operations obj_lin=new operations();
		modularOperations obj_mod=new modularOperations();
		Scanner s=new Scanner(System.in);
		String plainText;
		int[][] plaintext=new int[n][n];
		int[][] ciphertext=new int[n][n];
		System.out.println("Enter the message text!");
		plainText=s.next();
		int length=plainText.length();
		if(length<n*n)
		{
			System.out.println("WARNING: Size of Plain text is less than the matrix size!\n");
		}
		//Generating message matrix
		int k=0;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(k>=length)
				{
					plaintext[i][j]=obj_mod.mod((int)(Math.random()*(128)), 128);
				}
				else
				{

					int x=plainText.charAt(k);
					plaintext[i][j]=obj_mod.mod(x, 128);
					
				}
				k++;
			}
		}
		//performing matrix multiplication
		ciphertext=obj_lin.multiplyMatrix(plaintext,key_k1,n);//C=P*K1
		
		
		//performing matrix addition
		int temp;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				ciphertext[i][j]+=key_k2[i][j];//C=P*K1 +K2
				temp=(int)ciphertext[i][j];
				temp=obj_mod.mod(temp, 128);
				if(temp!=32)
				ciphertext[i][j]=temp;
			}
		}

		
/*W R I T I N G  P L A I N T E X T  A N D  C I P H E R  T E X T  T O  F I L E*/

		String [][] str=new String[n][n];
		String inputFile="\n\n\nENCRYPTION"+"\n"+"\n"+"PlainText:"+plainText+"\n"+"Cipertext:";
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
			str[i][j]=Character.toString((char) ciphertext[i][j]);
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
		System.out.println("ENCRYPTION COMPLETED! DATA ADDED TO THE TARGET FILE\n");
	}
}
