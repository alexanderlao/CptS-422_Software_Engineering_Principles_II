
public class TestCaseThree 
{
	public static void main(String[] args)
	{
		// long class in terms of length
		int a = 56;
		int b = 21;
		
		int c = a + b;
		int d = c * a;
		int e = c / 2;
		int f = b % 3;
		
		System.out.println("Hello World!");
		
		a = 23;
		b = 12;
		c = 24;
		d = b + 2;
		e = c / 12;
		f = d - 23;
		
		System.out.println("Hello again World!");
		
		a = b + c - d * e;
		b = a - c - d - e - f;
		c = b;
		d = a * b * c;
		e = f + 1;
		f = f - 1;
		
		System.out.println("Hello for the third time World!");
		
		a++;
		++b;
		c--;
		--c;
		d *= -1;
		e /= 2;
		
		System.out.println("Goodbye World!");
	}
}
