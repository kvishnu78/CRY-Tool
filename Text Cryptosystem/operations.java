package linearAlgebra;
import modularArithmetic.modularOperations;
public class operations
{
	static modularOperations obj_mod=new modularOperations(); 
	//MATRIX DETERMINANT
	//Determinant from Upper triangular matrix
 	public static int determinant(int[][]m,int n)
	{	
 		double[][] mat=new double[n][n];
 		for(int i=0;i<n;i++)
 		{
 			for(int j=0;j<n;j++)
 			{
 				mat[i][j]=m[i][j];
 			}
 		}
 		if(n==2)
 			return((int)(mat[0][0]*mat[1][1]-mat[0][1]*mat[1][0]));
 		//converting the input matrix to a upper triangular matrix to find the determinant
 		int swapc=0;
 		double det=1;
 		for(int i=0;i<n;i++)
 		{
 			for(int k=0;k<=i-1;k++)
 			{
 				double factor=mat[i][k]/mat[k][k];//finding the factor by which the element below the diagonal element is varying from diagonal element
 				for(int j=k+1;j<n;j++)
 				{//NOTE : ELEMENTS OF THE LOWER TRIANGLE ARE NOT ALTERED HERE!
 					mat[i][j]=mat[i][j]-(factor*mat[k][j]);//doing a row reduction for all elements in the upper triangle
 					//Equivalent to a23=a23-(a21/a11)*a13 i.e. r2-->k*r2-m*r1=r2-(m/k)*r1
 				}
 			}
 			//if diagonal is set to zero replace it by some column j where mat[i][j]!=0
 			if(mat[i][i]==0)
 			{
 				for(int j=i+1;j<n;j++)
 				{
 					if(mat[i][j]!=0)
 						mat=swap(mat,i,j,n);
 						swapc++;//retaining swap count to multiply by -1 in the end as changing columns will alter the determinant value(sign)
 					break;
 				}
 			}
 		}
 		//changing all the lower triangular elements to 0(not necessary in this application )
 		for(int i=0;i<n;i++)
 		{
 			for(int j=i+1;j<n;j++)
 			{
 				mat[j][i]=0;
 			}
 		}
 		//for a upper triangular matrix the product of the diagonal elements is the determinant 
 		for (int i=0;i<n;i++)
		{
 			if (mat[i][i]==0)
 				return 0;
			det=det*mat[i][i];
		}
 		int deti=(int)Math.ceil(det);//determinant of a integer matrix is always integer
 		//changing the sign of determinant based on the number of swaps
		if(swapc%2!=0)
			return (-1*deti);
		else return deti;	
	}
 	
 	static double[][] swap(double[][] m,int c1,int c2,int n)
	{
		double temp;
		for(int i=0;i<n;i++)
		{
			temp=m[i][c1];
			m[i][c1]=m[i][c2];
			m[i][c2]=temp;
		}
		return m;
	}
 	
 	//COFACTOR
 	public static int[][] cofactor(int mat[][],int n)
 	{
 		int sign=1;
 		int[][] minor=new int[n][n];//minor is the new matrix whose determinant needs to be found
 		int[][] cofactor=new int[n][n];
 		//for each element of the matrix find minor to find total cofactor matrix
 		for(int i=0;i<n;i++)
 		{
 			for(int j=0;j<n;j++)
 			{
 				for(int a=0,l=0,m=0;a<n;a++)
 				{
 					for(int b=0;b<n;b++)
 					{
 						if(a!=i && b!=j)
 						{
 							minor[l][m++]=mat[a][b];
 							if(m==n-1)//column number has reached n-1, so reset it to 0
 							{
 								m=0;
 								l++;//as column number reached n-1,update row to next row number
 							}
 						}
 					}
 				}
 				sign=((i+j)%2==0)?1:-1;//cofact Aij=(-1)^(i+j)*aij; where aij=minor at (i,j)
 				cofactor[i][j]=sign*determinant(minor,n-1);
 			}
 		}
 		return cofactor;
 	}
 	
 	//ADJOINT
 	public static int[][] adjoint(int[][] mat,int n)
 	{
 		int[][] cofactorMatrix=new int[n][n];
 		cofactorMatrix=cofactor(mat,n);
 		int[][] Adjoint=new int[n][n];
 		//adjoint=transpose of cofactor matrix
 		for(int i=0;i<n;i++)
 		{
 			for(int j=0;j<n;j++)
 			{
 				Adjoint[j][i]=cofactorMatrix[i][j];
 				int x=Adjoint[i][j];
 				Adjoint[i][j]=obj_mod.mod(x,128);
 			}
 		}
 		return Adjoint;
 	}
 	
 	
 	// MATRIX INVERSE
 	public static int[][] inverse(int[][] mat,int n)
 	{
 		int[][] adj=new int[n][n];
 		adj=adjoint(mat,n);
 		int det;
 		det=determinant(mat,n);
 		int det_m=obj_mod.modularMultiplicativeInverse(det, 128);
 		int[][] inverse=new int[n][n];
 		for(int i=0;i<n;i++)
 		{
 			for(int j=0;j<n;j++)
 			{
 				int x=adj[i][j]*det_m;
 				inverse[i][j]=obj_mod.mod(x,128);
 			}
 		}
 		return inverse;
 	}
 	
 	public static int[][] multiplyMatrix(int[][]m,int[][]n,int size)
 	{
 		int[][] result=new int[size][size];
 		for(int i=0;i<size;i++)
 		{
 			for(int j=0;j<size;j++)
 			{
 				for(int k=0;k<size;k++)
 				{
 					result[i][j]+=m[i][k]*n[k][j];
 				}
 				result[i][j]=obj_mod.mod(result[i][j],128);
 			}
 		}
 		return result;
 	}
 }

	


	