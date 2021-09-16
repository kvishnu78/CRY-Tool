package modularArithmetic;
public class modularOperations
{
	public static int mod(int a,int b)
	{
		if(a>=0)
		{
			return (a % b);
		}
		else
		{
			a=a%b;
			a=a+b;
			return a;
		}
	}
	public static int modularMultiplicativeInverse(int a,int n)
	{
		a=mod(a,n);
		for(int i=1;i<n;i++)
		{
			if(mod(a*i,n)==1)
				return i;
		}
		return -1;
	}
	public static int modularAddictiveInverse(int a,int n)
	{
		a=mod(a,n);
		return(-a);
	}
}