package testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.easymock.EasyMock;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;
import org.xml.sax.InputSource;

import com.google.common.base.Charsets;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import antlr.ANTLRException;
import antlr.RecognitionException;
import antlr.TokenStreamException;
import net.sf.eclipsecs.sample.checks.*;

public class TestMethodLimit 
{
	
	private DetailAST getAST(String filename) throws Exception
	{
		File file = new File(System.getProperty("user.dir") + "\\src\\UnitTesting\\" + filename);	
		List<String> lines = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "\\src\\" + filename));	
		FileText text = FileText.fromLines(file, lines);		
		FileContents contents = new FileContents(text);
		return TreeWalker.parseWithComments(contents);	
	}
	
	/********************************ALEX LAO*****************************/
	// Unit Testing
	// Halstead Volume is the program length (N) 
	// times the log2 of the program vocabulary (n) [1,2] : Volume = N log2 n
	@Test
	public void testVolume() throws Throwable
	{
		/*
		// assuming getLength() and getVocabulary() are both tested
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		double caseOneOutput = MethodLimitCheck.getLength(caseOneAST) * 
							   (Math.log(MethodLimitCheck.getVocabulary(caseOneAST))/Math.log(2));
		assertEquals(25, Math.round(caseOneOutput));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		double caseTwoOutput = MethodLimitCheck.getLength(caseTwoAST) * 
				   			   (Math.log(MethodLimitCheck.getVocabulary(caseTwoAST))/Math.log(2));
		assertEquals(5, Math.round(caseTwoOutput));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		double caseThreeOutput = MethodLimitCheck.getLength(caseThreeAST) * 
				   				 (Math.log(MethodLimitCheck.getVocabulary(caseThreeAST))/Math.log(2));
		assertEquals(693, Math.round(caseThreeOutput));
		*/
	}
	
	// Halstead Difficulty is half of the unique operators 
	// multiplied by the total number of operands, 
	// divided by the number of distinct operators [1,2]
	@Test
	public void testDifficulty() throws Throwable
	{
		/*
		// assuming getUniqueOperators() and getTotalOperands() are both tested
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		double caseOneUniqueOperators = MethodLimitCheck.getTotalUniqueOperator(caseOneAST);
		double caseOneTotalOperands = MethodLimitCheck.getTotalNotUniqueOperand(caseOneAST);
		double caseOneOutput = ((caseOneUniqueOperators / 2) * caseOneTotalOperands) / caseOneUniqueOperators;
		assertEquals(4, Math.round(caseOneOutput));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		double caseTwoUniqueOperators = MethodLimitCheck.getTotalUniqueOperator(caseTwoAST);
		double caseTwoTotalOperands = MethodLimitCheck.getTotalNotUniqueOperand(caseTwoAST);
		double caseTwoOutput = ((caseTwoUniqueOperators / 2) * caseTwoTotalOperands) / caseTwoUniqueOperators;
		assertEquals(0, Math.round(caseTwoOutput));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		double caseThreeUniqueOperators = MethodLimitCheck.getTotalUniqueOperator(caseThreeAST);
		double caseThreeTotalOperands = MethodLimitCheck.getTotalNotUniqueOperand(caseThreeAST);
		double caseThreeOutput = ((caseThreeUniqueOperators / 2) * caseThreeTotalOperands) / caseThreeUniqueOperators;
		assertEquals(41, Math.round(caseThreeOutput));
		*/
	}
	
	// Integration Testing
	@Test
	public void testVolumeWithLengthAndVocab() throws Throwable  
	{
		/*
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(25, Math.round(MethodLimitCheck.getVolume(caseOneAST)));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(5, Math.round(MethodLimitCheck.getVolume(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(693, Math.round(MethodLimitCheck.getVolume(caseThreeAST)));	
		*/
	}

	@Test
	public void testDifficultyWithOperatorsAndOperands() throws Throwable 
	{
		/*
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(4, Math.round(MethodLimitCheck.getDifficulty(caseOneAST)));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, Math.round(MethodLimitCheck.getDifficulty(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(41, Math.round(MethodLimitCheck.getDifficulty(caseThreeAST)));
		*/
	}
	/*************************************************************************/
	
	/*********************************MINH NGUYEN*****************************/
	@Test
	public void checkLength() throws Exception  
	{
		///////////////// Boundary Testing /////////////////
		// There are 2 variables that we used for this function.
		// Therefore, there are two parts.
		/********************
		 * Part 1: Tests for not unique operator.
		 * 
		 * From the testset that we came up in our documentation, we come up with the value for the test.
		 * 
		 ********************/
		
		/**********
		 * Step 1: Read in a testcase*.java file and parse it into a DetailAST*
		 **********/
		DetailAST test1AST = getAST("TestCaseOne.java");
		DetailAST test2AST = getAST("TestCaseTwo.java");
		DetailAST test3AST = getAST("TestCaseThree.java");	
		
		// Create a spy.
		MethodLimitCheck part1 = spy(new MethodLimitCheck());
		/**********/
		
		
		/**********
		 * Step 2: Using the the ***UNIT TESTING METHOD*** and the values from the testset to test the method
		 **********/
		
		/*
		 *  For the first test case, we use <O3,Op3> from the testset to come up with a testcase
		 *  O3  = 2 (Not Unique Operator = 2)
		 *  OP3 = 7 (Not Unique Operand = 7)
		 *  Expected: 9
		 */
		assertEquals(9,part1.getLength(test1AST));
	
		/*
		 *  For the first test case, we use <O1,Op3> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  OP3 = 3 (Not Unique Operand = 3)
		 *  Expected: 3
		 */
		assertEquals(3,part1.getLength(test2AST));	
		
		/*
		 *  For the first test case, we use <O3,Op3> from the testset to come up with a testcase
		 *  O3  = 51 (Not Unique Operator = 51)
		 *  OP3 = 81 (Not Unique Operand = 81)
		 *  Expected: 132
		 */
		assertEquals(132,part1.getLength(test3AST));
		
		/**********/
		
		
		/**********
		 * Step 3: Using the the ***INTEGRATED TESTING METHOD*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the first test case, we use <O3,Op3> from the testset to come up with a testcase
		 *  O3  = 2 (Not Unique Operator = 2)
		 *  OP3 = 7 (Not Unique Operand = 7)
		 *  Expected: 9
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		assertEquals(part1.getTotalNotUniqueOperator(test1AST) + part1.getTotalNotUniqueOperand(test1AST),part1.getLength(test1AST));	
	
		/*
		 *  For the first test case, we use <O1,Op3> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  OP3 = 3 (Not Unique Operand = 3)
		 *  Expected: 3
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		assertEquals(part1.getTotalNotUniqueOperator(test2AST) + part1.getTotalNotUniqueOperand(test2AST),part1.getLength(test2AST));	
		
		/*
		 *  For the first test case, we use <O3,Op3> from the testset to come up with a testcase
		 *  O3  = 51 (Not Unique Operator = 51)
		 *  OP3 = 81 (Not Unique Operand = 81)
		 *  Expected: 132
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		assertEquals(part1.getTotalNotUniqueOperator(test3AST) + part1.getTotalNotUniqueOperand(test3AST),part1.getLength(test3AST));		
	
		// Create a new spy for the next part.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 4: Using the the ***MOCK*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the fourth test case, we use <O1,Op1> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  OP3 = 0 (Not Unique Operand = 0)
		 *  Expected: 9
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		// This list is going to store the possible for the O1
		List<Integer> x = new ArrayList<Integer>();
		// This list is going to store the possible number for OP1;
		List<Integer> y = new ArrayList<Integer>();
		// This list is going to store our expectation.
		List<Integer> expected = new ArrayList<Integer>();
		
		// Add values to x
		x = Arrays.asList(0,1,50,99,100);
		// Add values to y
		y = Arrays.asList(0,1,50,99,100);
		// Add expected value using the testset in the document.
		expected = Arrays.asList(0,1,50,99,100,1,2,51,100,101,50,51,100,149,150,99,100,149,198,199,
				100,101,150,199,200);
		// Interator for expect list.
		int iter1 = 0;
		
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				doReturn(x.get(i)).when(part2).getTotalNotUniqueOperator();
				doReturn(y.get(j)).when(part2).getTotalNotUniqueOperand();
				assertEquals(expected.get(iter1),(Integer)part2.getLength());
				iter1++;
			}
		}
		
		
		
		//testCases = = Arrays.asList(
		doReturn(0).when(part2).getTotalNotUniqueOperator();
		doReturn(0).when(part2).getTotalNotUniqueOperand();
		assertEquals(0,part2.getLength());	
	}
	
	@Test
	public void testToString() throws Exception
	{
		/*
		// Test to string
		// Mocking (spy)
		MethodLimitCheck spying = spy(new MethodLimitCheck());
		doReturn(9).when(spying).getLength();
		doReturn(7).when(spying).getVocabulary();
		doReturn(2).when(spying).getNumberOperators();
		doReturn(7).when(spying).getNumberOperand();
		doReturn(25).when(spying).getVolume();
		doReturn(4).when(spying).getDifficulty();
		doReturn(100).when(spying).getEffort();
		
		assertEquals("Halstead Length: 9\n"
				+ "Halstead Vocabulary: 7\n"
				+ "Total Number of operators: 2\n"
				+ "Total Number of operands: 7\n"
				+ "Halstead Volume: 25\n"
				+ "Halstead Difficulty: 4\n"
				+ "Halstead Effort: 100\n",spying.ToString());
				*/
	}
	
	@Test
	public void checkProcess() throws Exception
	{
		/*
		// System testing.
		DetailAST rootAST = getAST("TestCaseOne.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertTrue(MethodLimitCheck.process(rootAST));	
		rootAST = getAST("TestCaseTwo.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertTrue(MethodLimitCheck.process(rootAST));	
		rootAST = getAST("TestCaseThree.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertTrue(MethodLimitCheck.process(rootAST));
		rootAST = getAST("TestCaseFive.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertFalse(MethodLimitCheck.process(rootAST));
		*/
	}
	
	@Test
	public void checkVocab() throws Exception  
	{
		/*
		// Unit testing.
		DetailAST rootAST = getAST("TestCaseOne.java");	
		assertEquals(7,MethodLimitCheck.getVocabulary(rootAST));	
		rootAST = getAST("TestCaseTwo.java");
		assertEquals(3,MethodLimitCheck.getVocabulary(rootAST));	
		rootAST = getAST("TestCaseThree.java");
		assertEquals(38,MethodLimitCheck.getVocabulary(rootAST));
		
		// Integration testing.
		rootAST = getAST("TestCaseOne.java");
		// Assuming MethodLimitCheck.getTotalUniqueOperator() and MethodLimitCheck.getTotalUniqueOperand are tested.
		assertEquals(MethodLimitCheck.getTotalUniqueOperator(rootAST) + MethodLimitCheck.getTotalUniqueOperand(rootAST),MethodLimitCheck.getVocabulary(rootAST));		
		rootAST = getAST("TestCaseOne.java");	
		// Assuming MethodLimitCheck.getTotalUniqueOperator() and MethodLimitCheck.getTotalUniqueOperand are tested.
		assertEquals(MethodLimitCheck.getTotalUniqueOperator(rootAST) + MethodLimitCheck.getTotalUniqueOperand(rootAST),MethodLimitCheck.getVocabulary(rootAST));	
		rootAST = getAST("TestCaseOne.java");	
		// Assuming MethodLimitCheck.getTotalUniqueOperator() and MethodLimitCheck.getTotalUniqueOperand are tested.
		assertEquals(MethodLimitCheck.getTotalUniqueOperator(rootAST) + MethodLimitCheck.getTotalUniqueOperand(rootAST),MethodLimitCheck.getVocabulary(rootAST));
		*/
	}
	
	@Test
	public void checkGetDetailASTsForTypeInBranch() throws Exception
	{
		/*
		List<Integer> operatorList = new ArrayList<Integer>();
    	operatorList = Arrays.asList(TokenTypes.ASSIGN,TokenTypes.BAND,TokenTypes.BAND_ASSIGN,
    			TokenTypes.BNOT,TokenTypes.BOR,TokenTypes.BOR_ASSIGN,TokenTypes.BSR,TokenTypes.BSR_ASSIGN,
    			TokenTypes.BXOR,TokenTypes.BXOR_ASSIGN,TokenTypes.COLON,TokenTypes.COMMA,TokenTypes.DEC
    			,TokenTypes.DIV,TokenTypes.DIV_ASSIGN,TokenTypes.DOT,TokenTypes.EQUAL,TokenTypes.GE
    			,TokenTypes.GT,TokenTypes.INC,TokenTypes.INDEX_OP,TokenTypes.LAND,TokenTypes.LE
    			,TokenTypes.LITERAL_INSTANCEOF,TokenTypes.LNOT,TokenTypes.LOR,TokenTypes.LT
    			,TokenTypes.MINUS,TokenTypes.MINUS_ASSIGN,TokenTypes.MOD,TokenTypes.MOD_ASSIGN
    			,TokenTypes.NOT_EQUAL,TokenTypes.PLUS,TokenTypes.PLUS_ASSIGN,TokenTypes.POST_DEC
    			,TokenTypes.POST_INC,TokenTypes.QUESTION,TokenTypes.SL,TokenTypes.SL_ASSIGN
    			,TokenTypes.SR,TokenTypes.SR_ASSIGN,TokenTypes.STAR,TokenTypes.STAR_ASSIGN
    			,TokenTypes.UNARY_MINUS,TokenTypes.UNARY_PLUS);
    	
    	List<Integer> operandList = new ArrayList<Integer>();
    	operandList = Arrays.asList(TokenTypes.IDENT,TokenTypes.STRING_LITERAL
    			,TokenTypes.CHAR_LITERAL, TokenTypes.LITERAL_TRUE,TokenTypes.LITERAL_FALSE
    			,TokenTypes.LITERAL_NULL,TokenTypes.NUM_FLOAT,TokenTypes.NUM_INT,TokenTypes.NUM_DOUBLE
    			,TokenTypes.NUM_LONG);
    	DetailAST rootAST = getAST("TestCaseOne.java");	
    	List<DetailAST> total = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operatorList.size(); i++)
    	{
    			total.addAll(MethodLimitCheck.getDetailASTsForTypeInBranch(rootAST.findFirstToken(TokenTypes.OBJBLOCK),operatorList.get(i),false));
    	}	
    	assertEquals(MethodLimitCheck.getTotalNotUniqueOperator(rootAST),total.size());
    	rootAST = getAST("TestCaseTwo.java");	
    	total = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operandList.size(); i++)
    	{
    			total.addAll(MethodLimitCheck.getDetailASTsForTypeInBranch(rootAST.findFirstToken(TokenTypes.OBJBLOCK),operandList.get(i),false));
    	}	
    	assertEquals(MethodLimitCheck.getTotalNotUniqueOperand(rootAST),total.size());
    	
    	rootAST = getAST("TestCaseThree.java");	
    	total = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operatorList.size(); i++)
    	{
    			total.addAll(MethodLimitCheck.getDetailASTsForTypeInBranch(rootAST.findFirstToken(TokenTypes.OBJBLOCK),operatorList.get(i),false));
    	}	
    	for(int i = 0; i < operandList.size(); i++)
    	{
    			total.addAll(MethodLimitCheck.getDetailASTsForTypeInBranch(rootAST.findFirstToken(TokenTypes.OBJBLOCK),operandList.get(i),false));
    	}	
    	assertEquals(MethodLimitCheck.getTotalNotUniqueOperand(rootAST) + MethodLimitCheck.getTotalNotUniqueOperator(rootAST),total.size());
		*/
	}
	/*************************************************************************/
	
	/*********************************ANGIE PARK*****************************/
	// Unit Testing
	@Test
	public void checkEffort() throws Throwable  
	{
		/*
		// assuming getLength() and getVocabulary() are both tested
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(88, Math.round(MethodLimitCheck.getEffort(caseOneAST)));
		assertEquals(Math.round(MethodLimitCheck.getDifficulty(caseOneAST)*MethodLimitCheck.getVolume(caseOneAST)), Math.round(MethodLimitCheck.getEffort(caseOneAST)));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java"); 
		assertEquals(0, Math.round(MethodLimitCheck.getEffort(caseTwoAST)));
		assertEquals(Math.round(MethodLimitCheck.getDifficulty(caseTwoAST)*MethodLimitCheck.getVolume(caseTwoAST)), Math.round(MethodLimitCheck.getEffort(caseTwoAST)));
		
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java"); 
		assertEquals(28055, Math.round(MethodLimitCheck.getEffort(caseThreeAST)));
		assertEquals(Math.round(MethodLimitCheck.getDifficulty(caseThreeAST)*MethodLimitCheck.getVolume(caseThreeAST)), Math.round(MethodLimitCheck.getEffort(caseThreeAST)));
		 */
	}
	
	@Test
	public void checkTotalOperator() throws Throwable  
	{
		/*
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(2, MethodLimitCheck.getNumberOperators(caseOneAST));
	
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, MethodLimitCheck.getNumberOperators(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(51, MethodLimitCheck.getNumberOperators(caseThreeAST));
		*/
	}
	@Test
	public void checkTotalOperand() throws Throwable  
	{
		/*
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(7, MethodLimitCheck.getNumberOperand(caseOneAST));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(3, MethodLimitCheck.getNumberOperand(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(81, MethodLimitCheck.getNumberOperand(caseThreeAST));
		*/
	}
	@Test
	public void checkUniqueOperator() throws Throwable  
	{
		/*
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(1, MethodLimitCheck.getTotalUniqueOperator(caseOneAST));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, MethodLimitCheck.getTotalUniqueOperator(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(14, MethodLimitCheck.getTotalUniqueOperator(caseThreeAST));
		*/
	}
	@Test
	public void checkUniqueOperand() throws Throwable  
	{
		/*
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(6, MethodLimitCheck.getTotalUniqueOperand(caseOneAST));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(3, MethodLimitCheck.getTotalUniqueOperand(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(24, MethodLimitCheck.getTotalUniqueOperand(caseThreeAST));
		*/
	}
	
	
	// Integration Testing
	@Test
	public void checkEffortWithDifficulty() throws Throwable  
	{
		/*
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(Math.round(3.5*25.2662), Math.round(MethodLimitCheck.getEffort(caseOneAST)));
		assertNotSame(Math.round(4*25.2662), Math.round(MethodLimitCheck.getEffort(caseOneAST)));
		assertNotSame(Math.round(1*25.2662), Math.round(MethodLimitCheck.getEffort(caseOneAST)));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(Math.round(0*4.755), Math.round(MethodLimitCheck.getEffort(caseTwoAST)));
		assertNotSame(Math.round(4*4.755), Math.round(MethodLimitCheck.getEffort(caseTwoAST)));
		assertNotSame(Math.round(1*4.755), Math.round(MethodLimitCheck.getEffort(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(Math.round(40.5*692.7264), Math.round(MethodLimitCheck.getEffort(caseThreeAST)));
		assertNotSame(Math.round(4*692.7264), Math.round(MethodLimitCheck.getEffort(caseThreeAST)));
		assertNotSame(Math.round(1*692.7264), Math.round(MethodLimitCheck.getEffort(caseThreeAST)));
		*/	
	}

	@Test
	public void checkEffortWithVolume() throws Throwable 
	{
		/*
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(Math.round(3.5*25.2662), Math.round(MethodLimitCheck.getEffort(caseOneAST)));
		assertNotSame(Math.round(3.5*2), Math.round(MethodLimitCheck.getEffort(caseOneAST)));
		assertNotSame(Math.round(3.5*100), Math.round(MethodLimitCheck.getEffort(caseOneAST)));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(Math.round(0*4.755), Math.round(MethodLimitCheck.getEffort(caseTwoAST)));
		assertNotSame(Math.round(0*25), Math.round(MethodLimitCheck.getEffort(caseTwoAST)));
		assertNotSame(Math.round(0*11), Math.round(MethodLimitCheck.getEffort(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(Math.round(40.5*692.7264), Math.round(MethodLimitCheck.getEffort(caseThreeAST)));
		assertNotSame(Math.round(40.5*200), Math.round(MethodLimitCheck.getEffort(caseThreeAST)));
		assertNotSame(Math.round(40.5*16), Math.round(MethodLimitCheck.getEffort(caseThreeAST)));
		*/
	}
	
	// system testing
	@Test
	public void UsabilityTesting() throws Throwable 
	{
		/*
		Scanner scanner = new Scanner(System.in); 
		boolean pass = false;
		
		while (!pass)
		{
		
			System.out.println("JAVA Filename: "); //TestCaseFour.java
			String userInput = scanner.nextLine();
			
			try 
			{
				DetailAST rootAST = getAST(userInput);
				System.out.println("Halstead Length: " + MethodLimitCheck.getLength(rootAST));
				System.out.println("Halstead Vocabulary: " + MethodLimitCheck.getVocabulary(rootAST));
				System.out.println("Total Number of operators: " + MethodLimitCheck.getNumberOperators(rootAST));
				System.out.println("Total Number of operands: " + MethodLimitCheck.getNumberOperand(rootAST));
				System.out.println("Halstead Volume: " + MethodLimitCheck.getVolume(rootAST));
				System.out.println("Halstead Difficulty: " + MethodLimitCheck.getDifficulty(rootAST));
				System.out.println("Halstead Effort: " + MethodLimitCheck.getEffort(rootAST));
				System.out.println("notuniqueoperator: " + MethodLimitCheck.getTotalNotUniqueOperator(rootAST));
				System.out.println("notuniqueoperand: " + MethodLimitCheck.getTotalNotUniqueOperand(rootAST));
				
				
				pass = true;
			}
			catch (Exception e)
			{
				System.out.println("There is no such file...");
				//e.printStackTrace();
				continue;
			}
		}
		
		scanner.close();
		*/
	}
	
	/*************************************************************************/
}


 
