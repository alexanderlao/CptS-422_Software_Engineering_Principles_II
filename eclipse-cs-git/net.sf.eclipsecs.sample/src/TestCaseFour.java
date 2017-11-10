public class TestCaseFour {

	// Total number of unique operators = 9
	// Total number of unique operands = 23
	
	// Total number of operators = 30
	// Total number of operands = 44
	
	// Length: total operators + total operands = 30 + 44 = 74
	// Vocabulary: total unique operators + total unique operands = 9 + 23 = 32
	// Volume: length * log2(vocabulary) = 74 * log2(32) = 370
	// Difficulty: ((uniqueOperators / 2) * (totalOperands)) / uniqueOperators = 
	//			   ((9 / 2) * 44) / 9 = 22
	// Effort: difficulty * volume = 22 * 370 = 8140
	
	public static void main(String[] args) 
	{
		int a,b = 0,c,d,e,f;
		a = 30;
		b += a;
		if (a==b)
		{
			c = 2;
		}
		else
		{
			c = 3;
		}
		
		d = b/c;
		e = d-5;
		f = e*e;
		
		System.out.println("Total result: a = " + a + ", b = " + b + ", c = " + c + ", d = " + d + ", e = " + e + ", f = " + f);	
	}
}