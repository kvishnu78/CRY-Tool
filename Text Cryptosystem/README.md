# CRY-Tool
## BASICS:
#### Affine Cipher:
Affine Cipher is a monoalphabetic cipher which combines the multiplicative cipher and addictive cipher to create a new encryption/decryption scheme.

	ENCRYPTION:C=((P*K1)+K2) mod n  
	DECRYPTION:P=((C-K2)*K1^(-1)) mod n
	Here P-Plain Text index
	C-Cipher text index
        K1-Key 1(invertible under mod n)
        K2-Key 2
        K1^(-1)-Multiplicative Inverse of Key 1 under mod n
        0 ≤ K1,K2,C,P ≤ n-1 (mod n)
	note that K1*K1^(-1)=1(mod n)

#### Hill Cipher:
Hill cipher is a polyalphabetic cipher which uses linear algebraic concepts such as matrices, determinant, inverses to compute the plain text, cipher text. In this scheme, the key is converted to a matrix of order a×a, and the plain text/cipher text is converted to a vector of order a×1 or to another matrix of order a×b, where b is the column number of the message matrix.
	
	ENCRYPTION:C= K*P mod n
   	DECRYPTION:P= K^(-1)*C mod n
	Here P-Plain Text matrix/vector
        C-Cipher text matrix/vector
        K-Key (invertible under mod n) 
        K^(-1)-Multiplicative Inverse of Key under mod n
        0 ≤ Elements of matrix K ,C, P ≤ n-1 (mod n)
	note that K^(-1)*K=K* K^(-1)=I (mod n)


## CRY TOOL-CRypt it Yourway Tool

#### About:
CRY TOOL is a tool that combines the concepts of Hill cipher and Affine cipher to create a polyalphabetic cipher to ensure security. It replaces the single character keys from the Affine Cipher with a matrix and thus creates the effect of Hill cipher and polyalphabetic cipher. The Encryption and Decryption Scheme are as follows: 

	ENCRYPTION:C=((P*K)+M) mod n
	DECRYPTION:P=((C-M)*K^(-1)) mod n
	Such that, K^(-1)K=K*K^(-1)=I
   	where,P-Plain Text index
   	      C-Cipher text index
   	      K-Key 1(invertible under mod n)
   	      M-Key 2
	      K^(-1)-Inverse of Key 1 under mod n
	      I-Identity matrix of order a×a
	NOTE:P, C, K, M are all Matrices of order a×a.

There are Five Java Files to perform the complete task in hand.
CRY TOOL completes its task by displaying a suitable message and writing the output to a text file.

## Reference
A. Elfadel, “Linear Algebra Cryptographic Techniques,” in Cryptography by Means of Linear Algebra and Number Theory, Eastern Mediterranean University, 2018, ch. 3, pp. 14–16. 

