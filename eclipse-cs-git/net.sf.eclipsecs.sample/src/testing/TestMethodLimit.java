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
	private int max;
    private int min;
    private int randA;
    private int randB;
	
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
		///////////////// Boundary Testing ////////////////////////////
		// There are two parts. The first part is step 1 - 3 (deli1),// 
		// and the second part is step 4 (Black-box(deli2)) and 5    //
		// (White-box(deli2)                                         //
		///////////////////////////////////////////////////////////////
		
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
		 *  For the first test case, we use  <O3,Op3,UO3,UOP3> from the testset to come up with a testcase
		 *  O3  = 2 (Not Unique Operator = 2)
		 *  OP3 = 7 (Not Unique Operand = 7)
		 *  UO3  = 1 (Unique Operator = 1)
		 *  UOP3 = 6 (Unique Operand = 6)
		 *  Expected: 25
		 */
		assertEquals(25,Math.round((part1.getVolume(test1AST))));
		
		/*
		 *  For the second test case, we use  <O3,Op3,UO3,UOP3> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  OP3 = 3 (Not Unique Operand = 3)
		 *  UO3  = 1 (Unique Operator = 0)
		 *  UOP3 = 6 (Unique Operand = 3)
		 *  Expected: 5
		 */
		assertEquals(5,Math.round((part1.getVolume(test2AST))));	
		
		/*
		 *  For the third test case, we use <O3,Op3,UO3,UOP3> from the testset to come up with a testcase
		 *  O3  = 51 (Not Unique Operator = 51)
		 *  OP3 = 81 (Not Unique Operand = 81)
		 *  UO3  = 14 (Unique Operator = 14)
		 *  UOP3 = 24 (Unique Operand = 24)
		 *  Expected: 693
		 */
		assertEquals(693,Math.round((part1.getVolume(test3AST))));
		
		/**********/
		
		
		/**********
		 * Step 3: Using the the ***INTEGRATED TESTING METHOD*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the first test case, we use  <O3,Op3,UO3,UOP3> from the testset to come up with a testcase
		 *  O3  = 2 (Not Unique Operator = 2)
		 *  OP3 = 7 (Not Unique Operand = 7)
		 *  UO3  = 1 (Unique Operator = 1)
		 *  UOP3 = 6 (Unique Operand = 6)
		 *  Expected: 25
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		double N = part1.getTotalNotUniqueOperator(test1AST) + part1.getTotalNotUniqueOperand(test1AST);
		double n = part1.getTotalUniqueOperator(test1AST) + part1.getTotalUniqueOperand(test1AST);
		double result =	N * (Math.log((double)n)/Math.log(2));
		assertEquals(Math.round(result),Math.round(part1.getVolume(test1AST)));	
		
		/*
		 *  For the second test case, we use  <O3,Op3,UO3,UOP3> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  OP3 = 3 (Not Unique Operand = 3)
		 *  UO3  = 0 (Unique Operator = 0)
		 *  UOP3 = 3 (Unique Operand = 3)
		 *  Expected: 5
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		N = part1.getTotalNotUniqueOperator(test2AST) + part1.getTotalNotUniqueOperand(test2AST);
		n = part1.getTotalUniqueOperator(test2AST) + part1.getTotalUniqueOperand(test2AST);
		result = N * (Math.log((double)n)/Math.log(2));
		assertEquals(Math.round(result),Math.round(part1.getVolume(test2AST)));	
		
		/*
		 *  For the third test case, we use  <O3,Op3,UO3,UOP3> from the testset to come up with a testcase
		 *  O3  = 51 (Not Unique Operator = 51)
		 *  OP3 = 81 (Not Unique Operand = 81)
		 *  UO3  = 14 (Unique Operator = 14)
		 *  UOP3 = 24 (Unique Operand = 24)
		 *  Expected: 693
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		N = part1.getTotalNotUniqueOperator(test3AST) + part1.getTotalNotUniqueOperand(test3AST);
		n = part1.getTotalUniqueOperator(test3AST) + part1.getTotalUniqueOperand(test3AST);
		result = N * (Math.log((double)n)/Math.log(2));
		assertEquals(Math.round(result),Math.round(part1.getVolume(test3AST)));	
		
		// Create a new spy for the next part.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********/
		
		
		/**********
		 * Step 4: Using the the ***MOCK*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the fourth test case, we use <O*,OP*,UO*,UOp*> from the testset to come up with a testcase
		 *  O  = {-1,0,1,50,99,100,101} (Not Unique Operator)
		 *  OP = {-1,0,1,50,99,100,101} (Not Unique Operand)
		 *  UO  = {-1,0,1,50,99,100,101} (Unique Operator)
		 *  UOP = {-1,0,1,50,99,100,101} (Unique Operand)
		 *  Expected: {...} (2401 test frames)
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		// This list is going to store the possible for the UO1
		List<Integer> x = new ArrayList<Integer>();
		// This list is going to store the possible number for UOP1;
		List<Integer> y = new ArrayList<Integer>();
		// This list is going to store the possible for the O1
		List<Integer> z = new ArrayList<Integer>();
		// This list is going to store the possible number for OP1;
		List<Integer> w = new ArrayList<Integer>();
		// This list is going to store our expectation.
		List<Double> expected = new ArrayList<Double>();
		
		// Setup max,min, and nom **ASSUMPTION: min = 0; max = 100
		max = 100;
		min = 0;
		randA = 50;
		randB = 50;
		
		// Add values to x
		x = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		// Add values to y
		y = Arrays.asList(min-1,min,min+1,randB,max-1,max,max+1);
		// Add values to z
		z = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		// Add values to w
		w = Arrays.asList(min-1,min,min+1,randB,max-1,max,max+1);
	
		// Add expected value using the testset in the document.
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				for(int h = 0; h < z.size(); h++)
				{
					for(int u = 0; u < w.size(); u++)
					{
						if(x.get(i) >= min && x.get(i) <= max 
								&& y.get(j) >= min && y.get(j) <= max 
								&& z.get(h) >= min && z.get(h) <= max 
								&& w.get(u) >= min && w.get(u) <= max) 
						{
							N = z.get(h).doubleValue() + w.get(u).doubleValue();
							n = x.get(i).doubleValue() + y.get(j).doubleValue();
							result = N * (Math.log((double)n)/Math.log(2));
							expected.add(result);
						}
						else
						{
							expected.add(-1.0);
						}
					}
				}
			}
		}
		
		// Interator for expect list.
		int iter1 = 0;
		
		// Running test (Cross product)
		
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				for(int h = 0; h < z.size(); h++)
				{
					for(int u = 0; u < w.size(); u++)
					{
						doReturn(x.get(i)).when(part2).getTotalUniqueOperator();
						doReturn(y.get(j)).when(part2).getTotalUniqueOperand();
						doReturn(z.get(h)).when(part2).getTotalNotUniqueOperator();
						doReturn(w.get(u)).when(part2).getTotalNotUniqueOperand();
						assertEquals(Math.round(expected.get(iter1)),Math.round((Double)part2.getVolume()));
						iter1++;
					}
				}
			}
		}
		
		// Create a new spy for the next part.
		MethodLimitCheck part3 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 5: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
	     * getDoneProcessing() = false
		 */
		doReturn(false).when(part3).getDoneProcessing();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T2
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = -90
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(-90).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T3
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 150
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(150).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T4
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = -40
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(-40).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T5
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 120
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(120).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T6
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = -40
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(-40).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T7
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 101
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(101).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T8
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = -2
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(-2).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T9
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 101
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(101).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getVolume()));
		/*
		 * T10
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 50
	     * getTotalNotUniqueOperand() = 60
	     * getTotalUniqueOperator() = 70
	     * getTotalUniqueOperand() = 80
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(50).when(part3).getTotalNotUniqueOperator();
		doReturn(60).when(part3).getTotalNotUniqueOperand();
		doReturn(70).when(part3).getTotalUniqueOperator();
		doReturn(80).when(part3).getTotalUniqueOperand();
		assertEquals(795,Math.round(part3.getVolume()));
	}
	
	// Halstead Difficulty is half of the unique operators 
	// multiplied by the total number of operands, 
	// divided by the number of distinct operators [1,2]
	@Test
	public void testDifficulty() throws Throwable
	{
		///////////////// Boundary Testing ////////////////////////////
		// There are two parts. The first part is step 1 - 3 (deli1),// 
		// and the second part is step 4 (Black-box(deli2)) and 5    //
		// (White-box(deli2)                                         //
		///////////////////////////////////////////////////////////////
		
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
		 *  For the first test case, we use <UO3,Op3> from the testset to come up with a testcase
		 *  UO3 = 1 (Unique Operator = 1)
		 *  OP3 = 7 (Not Unique Operand = 7)
		 *  Expected: 4
		 */
		assertEquals(4,Math.round(part1.getDifficulty(test1AST)));
		
		/*
		 *  For the first test case, we use <UO3,Op3> from the testset to come up with a testcase
		 *  UO3 = 0 (Unique Operator = 0)
		 *  OP3 = 3 (Not Unique Operand = 3)
		 *  Expected: -1
		 */
		assertEquals(-1,Math.round(part1.getDifficulty(test2AST)));
		
		/*
		 *  For the third test case, we use <O3,Op3> from the testset to come up with a testcase
		 *  UO3  = 14 (Not Unique Operator = 14)
		 *  OP3 = 81 (Not Unique Operand = 81)
		 *  Expected: 132
		 */
		assertEquals(41,Math.round(part1.getDifficulty(test3AST)));
		
		/**********/
		
		
		/**********
		 * Step 3: Using the the ***INTEGRATED TESTING METHOD*** and the values from the testset to test the method
		 **********/
		/*
		 * 	For the first test case, we use <UO3,Op3> from the testset to come up with a testcase
		 *  UO3 = 1 (Unique Operator = 1)
		 *  OP3 = 7 (Not Unique Operand = 7)
		 *  Expected: 4
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */	
		double result = ((double)part1.getTotalUniqueOperator(test1AST) / 2) * ((double)part1.getTotalNotUniqueOperand(test1AST)/(double)part1.getTotalUniqueOperator(test1AST));	
		assertEquals(Math.round(result),Math.round(part1.getDifficulty(test1AST)));	
		
		/*
		 * 	For the first test case, we use <UO3,Op3> from the testset to come up with a testcase
		 *  UO3 = 0 (Unique Operator = 0)
		 *  OP3 = 3 (Not Unique Operand = 3)
		 *  Expected: -1
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		assertEquals(-1,Math.round(part1.getDifficulty(test2AST)));		
		
		/*
		 * 	For the third test case, we use <UO3,Op3> from the testset to come up with a testcase
		 *  UO3  = 14 (Unique Operator = 14)
		 *  OP3 = 81 (Not Unique Operand = 81)
		 *  Expected: 132
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		result = ((double)part1.getTotalUniqueOperator(test3AST) / 2) * ((double)part1.getTotalNotUniqueOperand(test3AST)/(double)part1.getTotalUniqueOperator(test3AST));	
		assertEquals(Math.round(result),Math.round(part1.getDifficulty(test3AST)));	
		
		// Create a new spy for the next part.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********/
		
		/**********
		 * Step 4: Using the the ***MOCK*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the fourth test case, we use <UO*,Op*> from the testset to come up with a testcase
		 *  UO  = {-1,0,1,50,99,100,101} (Not Unique Operator)
		 *  OP = {-1,0,1,50,99,100,101} (Not Unique Operand)
		 *  Expected: {-1,-1,-1,-1,-1,-1,-1,-1,0,1,50,99,100,-1,-1,1,2,51,100,101,-1,-1,50,51,100,149,150,-1,
		 *  -1,99,100,149,198,199,-1,-1,100,101,150,199,200,-1}
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		// This list is going to store the possible for the UO1
		List<Integer> x = new ArrayList<Integer>();
		// This list is going to store the possible number for OP1;
		List<Integer> y = new ArrayList<Integer>();
		// This list is going to store our expectation.
		List<Double> expected = new ArrayList<Double>();
		
		// Setup max,min, and nom **ASSUMPTION: min = 0; max = 100
		max = 100;
		min = 0;
		randA = 50;
		randB = 50;
		
		// Add values to x
		x = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		// Add values to y
		y = Arrays.asList(min-1,min,min+1,randB,max-1,max,max+1);
		// Add expected value using the testset in the document.
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				if(x.get(i) >= min && x.get(i) <= max
						&& y.get(j) >= min && y.get(j) <= max && x.get(i) != 0)
				{
					result = (x.get(i).doubleValue() / 2) * (y.get(j).doubleValue()/x.get(i).doubleValue());						
					expected.add(result);		
				}
				else
				{
					expected.add(-1.0);	
				}
			}
		}
		
		// Interator for expect list.
		int iter1 = 0;
		
		// Running test (Cross product)
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				doReturn(x.get(i)).when(part2).getTotalUniqueOperator();
				doReturn(y.get(j)).when(part2).getTotalNotUniqueOperand();
				assertEquals(Math.round(expected.get(iter1)),Math.round(part2.getDifficulty()));
				iter1++;
			}
		}	
		
		// Create a new spy for the next part.
		MethodLimitCheck part3 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 5: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
	     * getDoneProcessing() = false
		 */
		doReturn(false).when(part3).getDoneProcessing();
		assertEquals(-1,Math.round(part3.getDifficulty()));
		/*
		 * T2
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperand() = -79
	     * getTotalUniqueOperator() = 0
		 **/
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(-79).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		assertEquals(-1,Math.round(part3.getDifficulty()));
		/*
		 * T3
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperand() = 179
	     * getTotalUniqueOperator() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(179).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		assertEquals(-1,Math.round(part3.getDifficulty()));
		/*
		 * T4
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = -80
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(-80).when(part3).getTotalUniqueOperator();
		assertEquals(-1,Math.round(part3.getDifficulty()));
		/*
		 * T5
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 120
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(120).when(part3).getTotalUniqueOperator();
		assertEquals(-1,Math.round(part3.getDifficulty()));
		/*
		 * T6
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperand() = 50
	     * getTotalUniqueOperator() = 80
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(50).when(part3).getTotalNotUniqueOperand();
		doReturn(80).when(part3).getTotalUniqueOperator();
		assertEquals(25,Math.round(part3.getDifficulty()));	
		/*
		 * T7
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperand() = 50
	     * getTotalUniqueOperator() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(50).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		assertEquals(-1,Math.round(part3.getDifficulty()));

	}
	/*
	 * Combined in to the checkVolume method.
	 */
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

	/*
	 * Combined in to the checkDiffictulty method.
	 */
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
	/*
     * This method is going to run junit on the method getLength().
     * 
     * @exception if something go wrong, then throws exception.	
     * 
     */
	@Test
	public void checkLength() throws Exception  
	{
		///////////////// Boundary Testing ////////////////////////////
		// There are two parts. The first part is step 1 - 3 (deli1),// 
		// and the second part is step 4 (Black-box(deli2)) and 5    //
		// (White-box(deli2)                                         //
		///////////////////////////////////////////////////////////////
		
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
		 *  For the second test case, we use <O3,Op3> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  OP3 = 3 (Not Unique Operand = 3)
		 *  Expected: 3
		 */
		assertEquals(3,part1.getLength(test2AST));	
		
		/*
		 *  For the third test case, we use <O3,Op3> from the testset to come up with a testcase
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
		 *  For the second test case, we use <O1,Op3> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  OP3 = 3 (Not Unique Operand = 3)
		 *  Expected: 3
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		assertEquals(part1.getTotalNotUniqueOperator(test2AST) + part1.getTotalNotUniqueOperand(test2AST),part1.getLength(test2AST));	
		
		/*
		 *  For the third test case, we use <O3,Op3> from the testset to come up with a testcase
		 *  O3  = 51 (Not Unique Operator = 51)
		 *  OP3 = 81 (Not Unique Operand = 81)
		 *  Expected: 132
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		assertEquals(part1.getTotalNotUniqueOperator(test3AST) + part1.getTotalNotUniqueOperand(test3AST),part1.getLength(test3AST));		
	
		// Create a new spy for the next part.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********/
		
		/**********
		 * Step 4: Using the the ***MOCK*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the fourth test case, we use <O*,Op*> from the testset to come up with a testcase
		 *  O  = {-1,0,1,50,99,100,101} (Not Unique Operator)
		 *  OP = {-1,0,1,50,99,100,101} (Not Unique Operand)
		 *  Expected: {-1,-1,-1,-1,-1,-1,-1,-1,0,1,50,99,100,-1,-1,1,2,51,100,101,-1,-1,50,51,100,149,150,-1,
		 *  -1,99,100,149,198,199,-1,-1,100,101,150,199,200,-1}
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		// This list is going to store the possible for the O1
		List<Integer> x = new ArrayList<Integer>();
		// This list is going to store the possible number for OP1;
		List<Integer> y = new ArrayList<Integer>();
		// This list is going to store our expectation.
		List<Integer> expected = new ArrayList<Integer>();
		
		// Setup max,min, and nom **ASSUMPTION: min = 0; max = 100
		max = 100;
	    min = 0;
	    randA = 50;
	    randB = 50;
		
		// Add values to x
		x = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		// Add values to y
		y = Arrays.asList(min-1,min,min+1,randB,max-1,max,max+1);
		// Add expected value using the testset in the document.
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				if(x.get(i) >= min && x.get(i) <= max
						&& y.get(j) >= min && y.get(j) <= max)
				{
					expected.add(x.get(i) + y.get(j));		
				}
				else
				{
					expected.add(-1);	
				}
			}
		}
		
		// Interator for expect list.
		int iter1 = 0;
		
		// Running test (Cross product)
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
	
		// Create a new spy for the next part.
		MethodLimitCheck part3 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 5: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
	     * getDoneProcessing() = false
		 */
		doReturn(false).when(part3).getDoneProcessing();
		assertEquals(-1,part3.getLength());
		/*
		 * T2
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = -50
	     * getTotalNotUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(-50).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		assertEquals(-1,part3.getLength());
		/*
		 * T3
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 150
	     * getTotalNotUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(150).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		assertEquals(-1,part3.getLength());
		/*
		 * T4
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = -100
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(-50).when(part3).getTotalNotUniqueOperand();
		assertEquals(-1,part3.getLength());
		/*
		 * T5
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 150
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(150).when(part3).getTotalNotUniqueOperand();
		assertEquals(-1,part3.getLength());
		/*
		 * T6
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 10
	     * getTotalNotUniqueOperand() = 20
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(10).when(part3).getTotalNotUniqueOperator();
		doReturn(20).when(part3).getTotalNotUniqueOperand();
		assertEquals(30,part3.getLength());
		
	}
	
	/*
     * This method is going to run junit on the method ToString().
     * 
     * @exception if something go wrong, then throws exception.	
     * 
     */
	@Test
	public void testToString() throws Exception
	{
		/**********
		 * Using the the ***MOCK*** to test toString()
		 **********/
		/*
		 *  For this test, we mock the methods that the toString() is going to use
		 *  to display the outputs.
		 */
		// Mocking (spy)
		MethodLimitCheck spying = spy(new MethodLimitCheck());
		// Mocking legnth
		doReturn(9).when(spying).getLength();
		// Mocking vocab
		doReturn(7).when(spying).getVocabulary();
		// Mocking operator
		doReturn(2).when(spying).getNumberOperators();
		// Mocking operand
		doReturn(7).when(spying).getNumberOperand();
		// Mocking volume
		doReturn((double)25).when(spying).getVolume();
		// Mocking difficulty
		doReturn((double)4).when(spying).getDifficulty();
		// Mocking effort
		doReturn((double)100).when(spying).getEffort();
		// Running test.
		assertEquals("Halstead Length: 9\n"
				+ "Halstead Vocabulary: 7\n"
				+ "Total Number of operators: 2\n"
				+ "Total Number of operands: 7\n"
				+ "Halstead Volume: 25.0\n"
				+ "Halstead Difficulty: 4.0\n"
				+ "Halstead Effort: 100.0\n",spying.ToString());
				
	}
	
	/*
     * This method is going to run junit on the method process().
     * 
     * @exception if something go wrong, then throws exception.	
     * 
     */
	@Test
	public void checkProcess() throws Exception
	{
		///////////////// Boundary Testing ////////////////////////////
		// There are two parts. The first part is step 1 - 2(deli1), // 
		// and the second part is step 3  (White-box(deli2)          //
		///////////////////////////////////////////////////////////////
		
		/**********
		 * Step 1: Read in a testcase*.java file and parse it into a DetailAST*
		 **********/
		DetailAST test1AST = getAST("TestCaseOne.java");
		DetailAST test2AST = getAST("TestCaseTwo.java");
		DetailAST test3AST = getAST("TestCaseThree.java");	
		DetailAST test5AST = getAST("TestCaseFive.java");	
		
		// Create a spy.
		MethodLimitCheck part1 = spy(new MethodLimitCheck());
		/**********/
		
		/**********
		 * Step 2: Using the the ***UNIT TESTING METHOD*** and the values from the testset to test the method
		 **********/
		
		/*
		 *  For the first test case, we will process the TestCaseOne.java
		 *  Expected: True
		 */
		assertTrue(part1.process(test1AST));	
	
		/*
		 *  For the second test case, we will process the TestCaseTwo.java
		 *  Expected: True
		 */
		assertTrue(part1.process(test2AST));	
		
		/*
		 *  For the third test case, we will process the TestCaseThree.java
		 *  Expected: True
		 */
		assertTrue(part1.process(test3AST));	
		
		/*
		 *  For the third test case, we will process the TestCaseThree.java
		 *  Expected: False
		 */
		assertFalse(part1.process(test5AST));	
		
		/**********/
		
		// Create a spy.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 3: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
	     * aAst is null.
	     * Expected: False
		 */
		assertFalse(part2.process(null));
		/*
		 * T2
		 * aAst is not null.
		 * Expected: True
		 */
		assertTrue(part2.process(test3AST));
		
	}
	
	/*
     * This method is going to run junit on the method getVocabulary().
     * 
     * @exception if something go wrong, then throws exception.	
     * 
     */
	@Test
	public void checkVocab() throws Exception  
	{
		///////////////// Boundary Testing ////////////////////////////
		// There are two parts. The first part is step 1 - 3 (deli1),// 
		// and the second part is step 4 (Black-box(deli2)) and 5    //
		// (White-box(deli2)                                         //
		///////////////////////////////////////////////////////////////
		
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
		 *  For the first test case, we use <UO3,UOp3> from the testset to come up with a testcase
		 *  UO3  = 1 (Unique Operator = 1)
		 *  UOP3 = 6 (Unique Operand = 6)
		 *  Expected: 7
		 */
		assertEquals(7,part1.getVocabulary(test1AST));
		
		/*
		 *  For the second test case, we use <UO1,UOp3> from the testset to come up with a testcase
		 *  UO3  = 0 (Unique Operator = 0)
		 *  UOP3 = 3 (Unique Operand = 3)
		 *  Expected: 3
		 */
		assertEquals(3,part1.getVocabulary(test2AST));	
		
		/*
		 *  For the third test case, we use <UO3,UOp3> from the testset to come up with a testcase
		 *  UO3  = 14 (Unique Operator = 14)
		 *  UOP3 = 24 (Unique Operand = 24)
		 *  Expected: 38
		 */
		assertEquals(38,part1.getVocabulary(test3AST));
		
		/**********/
		
		/**********
		 * Step 3: Using the the ***INTEGRATED TESTING METHOD*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the first test case, we use <UO3,UOp3> from the testset to come up with a testcase
		 *  UO3  = 1 (Unique Operator = 1)
		 *  UOP3 = 6 (Unique Operand = 6)
		 *  Expected: 9
		 *  Assumption:MethodLimitCheck.getTotalUniqueOperator() and MethodLimitCheck.getTotalUniqueOperand are tested.
		 */
		assertEquals(part1.getTotalUniqueOperator(test1AST) + part1.getTotalUniqueOperand(test1AST),part1.getVocabulary(test1AST));	
		
		/*
		 *  For the second test case, we use <UO3,UOp3> from the testset to come up with a testcase
		 *  UO3  = 0 (Unique Operator = 0)
		 *  UOP3 = 3 (Unique Operand = 3)
		 *  Expected: 3
		 *  Assumption:MethodLimitCheck.getTotalUniqueOperator() and MethodLimitCheck.getTotalUniqueOperand are tested.
		 */
		assertEquals(part1.getTotalUniqueOperator(test2AST) + part1.getTotalUniqueOperand(test2AST),part1.getVocabulary(test2AST));	
		
		/*
		 *  For the third test case, we use <UO3,UOp3> from the testset to come up with a testcase
		 *  UO3  = 14 (Unique Operator = 14)
		 *  UOP3 = 24 (Unique Operand = 24)
		 *  Expected: 38
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		assertEquals(part1.getTotalUniqueOperator(test3AST) + part1.getTotalUniqueOperand(test3AST),part1.getVocabulary(test3AST));		
		
		// Create a new spy for the next part.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********/
		
		/**********
		 * Step 4: Using the the ***MOCK*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the fourth test case, we use <UO*,UOp*> from the testset to come up with a testcase
		 *  UO  = {-1,0,1,50,99,100,101} (Not Unique Operator)
		 *  UOP = {-1,0,1,50,99,100,101} (Not Unique Operand)
		 *  Expected: {-1,-1,-1,-1,-1,-1,-1,-1,0,1,50,99,100,-1,-1,1,2,51,100,101,-1,-1,50,51,100,149,150,-1,
		 *  -1,99,100,149,198,199,-1,-1,100,101,150,199,200,-1}
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		// This list is going to store the possible for the UO1
		List<Integer> x = new ArrayList<Integer>();
		// This list is going to store the possible number for UOP1;
		List<Integer> y = new ArrayList<Integer>();
		// This list is going to store our expectation.
		List<Integer> expected = new ArrayList<Integer>();
		
		// Setup max,min, and nom **ASSUMPTION: min = 0; max = 100
		max = 100;
	    min = 0;
	    randA = 50;
	    randB = 50;
		
		// Add values to x
		x = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		// Add values to y
		y = Arrays.asList(min-1,min,min+1,randB,max-1,max,max+1);
		// Add expected value using the testset in the document.
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				if(x.get(i) >= min && x.get(i) <= max
						&& y.get(j) >= min && y.get(j) <= max)
				{
					expected.add(x.get(i) + y.get(j));		
				}
				else
				{
					expected.add(-1);	
				}
			}
		}
		
		// Interator for expect list.
		int iter1 = 0;
		
		// Running test (Cross product)
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				doReturn(x.get(i)).when(part2).getTotalUniqueOperator();
				doReturn(y.get(j)).when(part2).getTotalUniqueOperand();
				assertEquals(expected.get(iter1),(Integer)part2.getVocabulary());
				iter1++;
			}
		}	
		
		// Create a new spy for the next part.
		MethodLimitCheck part3 = spy(new MethodLimitCheck());
				
		/**********
		 * Step 5: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
	     * getDoneProcessing() = false
		 */
		doReturn(false).when(part3).getDoneProcessing();
		assertEquals(-1,part3.getVocabulary());
		/*
		 * T2
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = -50
	     * getTotalNotUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(-60).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,part3.getVocabulary());
		/*
		 * T3
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 150
	     * getTotalNotUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(160).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,part3.getVocabulary());
		/*
		 * T4
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = -50
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(-110).when(part3).getTotalUniqueOperand();
		assertEquals(-1,part3.getVocabulary());
		/*
		 * T5
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 150
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(160).when(part3).getTotalUniqueOperand();
		assertEquals(-1,part3.getVocabulary());
		/*
		 * T6
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 10
	     * getTotalNotUniqueOperand() = 20
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(40).when(part3).getTotalUniqueOperator();
		doReturn(30).when(part3).getTotalUniqueOperand();
		assertEquals(70,part3.getVocabulary());
	}
	
	/*
     * This method is going to run junit on the method ToString().
     * 
     * @exception if something go wrong, then throws exception.	
     * 
     */
	@Test
	public void checkGetDetailASTsForTypeInBranch() throws Exception
	{
		///////////////// Whitebox testing CFG ////////////////////////
		// There are two parts. The first part is step 1 - 2 (deli1),// 
		// and the second part is step 3 (deli2)                     //
		///////////////////////////////////////////////////////////////
		
		/**********
		 * Step 1: Read in a testcase*.java file and parse it into a DetailAST*
		 **********/
		DetailAST test1AST = getAST("TestCaseOne.java");
		
		// Create a spy.
		MethodLimitCheck part1 = spy(new MethodLimitCheck());
		/**********/
		
		/**********
		 * Step 2: Perform testing.
		 **********/
		// Create operatorList.
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
    	
    	// Create operandList
    	List<Integer> operandList = new ArrayList<Integer>();
    	operandList = Arrays.asList(TokenTypes.IDENT,TokenTypes.STRING_LITERAL
    			,TokenTypes.CHAR_LITERAL, TokenTypes.LITERAL_TRUE,TokenTypes.LITERAL_FALSE
    			,TokenTypes.LITERAL_NULL,TokenTypes.NUM_FLOAT,TokenTypes.NUM_INT,TokenTypes.NUM_DOUBLE
    			,TokenTypes.NUM_LONG);
    	
    	// Create a list to store the tokentype from the parsed file
    	List<DetailAST> total = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operatorList.size(); i++)
    	{
    			total.addAll(part1.getDetailASTsForTypeInBranch(test1AST.findFirstToken(TokenTypes.OBJBLOCK),operatorList.get(i),false));
    	}	
    	assertEquals(part1.getTotalNotUniqueOperator(test1AST),total.size());
    	
    	/**********/
		
		// Create a spy.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 3: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
	     * aAst is test1AST.findFirstToken(TokenTypes.OBJBLOCK)
	     * tokeTypes is TokenTypes.ASSIGN
	     * unique is false
	     * Expected: 2
		 */
		// Create a list to store the tokentype from the parsed file
    	List<DetailAST> totalAssign = new ArrayList<DetailAST>();  	
    	totalAssign.addAll(part1.getDetailASTsForTypeInBranch(test1AST.findFirstToken(TokenTypes.OBJBLOCK),TokenTypes.ASSIGN,false));
    	assertEquals(2,totalAssign.size());
    	/*
		 * T2
	     * aAst is test1AST.findFirstToken(TokenTypes.OBJBLOCK)
	     * tokeTypes is TokenTypes.ASSIGN
	     * unique is true
	     * Expected: 1
		 */
		// Create a list to store the tokentype from the parsed file
    	totalAssign = new ArrayList<DetailAST>();  	
    	totalAssign.addAll(part1.getDetailASTsForTypeInBranch(test1AST.findFirstToken(TokenTypes.OBJBLOCK),TokenTypes.ASSIGN,true));
    	assertEquals(1,totalAssign.size());
		
	}
	/*************************************************************************/
	
	/*********************************ANGIE PARK*****************************/
	// Unit Testing
	@Test
	public void checkEffort() throws Throwable  
	{
		///////////////// Boundary Testing ////////////////////////////
		// There are two parts. The first part is step 1 - 3 (deli1),// 
		// and the second part is step 4 (Black-box(deli2)) and 5    //
		// (White-box(deli2)                                         //
		///////////////////////////////////////////////////////////////
		
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
		 *  For the first test case, we use <UO3,UOp3,O3,OP3> from the testset to come up with a testcase
		 *  UO3  = 1 (Unique Operator = 1)
		 *  UOP3 = 6 (Unique Operand = 6)
		 *  O3   = 2 (Operator = 2)
		 *  OP3  = 7 (Operand = 7)
		 *  Expected: 88
		 */
		assertEquals(88, Math.round(part1.getEffort(test1AST)));
		
		/*
		 *  For the first test case, we use <UO3,UOp3,O3,OP3> from the testset to come up with a testcase
		 *  UO3  = 0 (Unique Operator = 0)
		 *  UOP3 = 3 (Unique Operand = 3)
		 *  O3   = 2 (Operator = 0)
		 *  OP3  = 7 (Operand = 3)
		 *  Expected: -1
		 */
		assertEquals(-1, Math.round(part1.getEffort(test2AST)));
		
		/*
		 *  For the first test case, we use <UO3,UOp3,O3,OP3> from the testset to come up with a testcase
		 *  UO3  = 14 (Unique Operator = 14)
		 *  UOP3 = 24 (Unique Operand = 24)
		 *  O3   = 51 (Operator = 51)
		 *  OP3  = 81 (Operand = 81)
		 *  Expected: 28055
		 */
		assertEquals(28055, Math.round(part1.getEffort(test3AST)));
		
		/**********/
		
		/**********
		 * Step 3: Using the the ***INTEGRATED TESTING METHOD*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the first test case, we use <UO3,UOp3,O3,OP3> from the testset to come up with a testcase
		 *  UO3  = 1 (Unique Operator = 1)
		 *  UOP3 = 6 (Unique Operand = 6)
		 *  O3   = 2 (Operator = 2)
		 *  OP3  = 7 (Operand = 7)
		 *  Expected: 88
		 */
		assertEquals(Math.round(part1.getDifficulty(test1AST)*part1.getVolume(test1AST)), Math.round(part1.getEffort(test1AST)));
		
		/*
		 *  For the first test case, we use <UO3,UOp3,O3,OP3> from the testset to come up with a testcase
		 *  UO3  = 0 (Unique Operator = 0)
		 *  UOP3 = 3 (Unique Operand = 3)
		 *  O3   = 0 (Operator = 0)
		 *  OP3  = 3 (Operand = 3)
		 *  Expected: -1
		 */
		if(part1.getDifficulty(test2AST) != -1 && part1.getVolume(test2AST) != -1)
		{
			assertEquals(Math.round(part1.getDifficulty(test2AST) * part1.getVolume(test2AST)), Math.round(part1.getEffort(test2AST)));;	
		}
		else
		{
			assertEquals(-1, Math.round(part1.getEffort(test2AST)));;	
			
		}
		/*
		 *  For the first test case, we use <UO3,UOp3,O3,OP3> from the testset to come up with a testcase
		 *  UO3  = 14 (Unique Operator = 14)
		 *  UOP3 = 24 (Unique Operand = 24)
		 *  O3   = 51 (Operator = 51)
		 *  OP3  = 81 (Operand = 81)
		 *  Expected: 28055
		 */
		assertEquals(Math.round(part1.getDifficulty(test3AST)*part1.getVolume(test3AST)), Math.round(part1.getEffort(test3AST)));		
		
		// Create a new spy for the next part.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********/
		
		/**********
		 * Step 4: Using the the ***MOCK*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the fourth test case, we use <UO*,UOp*> from the testset to come up with a testcase
		 *  O  = {-1,0,1,50,99,100,101} (Not Unique Operator)
		 *  OP = {-1,0,1,50,99,100,101} (Not Unique Operand)
		 *  UO  = {-1,0,1,50,99,100,101} (Unique Operator)
		 *  UOP = {-1,0,1,50,99,100,101} (Unique Operand)
		 *  Expected: {...} (2401 test frames)
		 *  Assumption:MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		 */
		// This list is going to store the possible for the UO1
		List<Integer> x = new ArrayList<Integer>();
		// This list is going to store the possible number for UOP1;
		List<Integer> y = new ArrayList<Integer>();
		// This list is going to store the possible for the O1
		List<Integer> z = new ArrayList<Integer>();
		// This list is going to store the possible number for OP1;
		List<Integer> w = new ArrayList<Integer>();
		// This list is going to store our expectation.
		List<Double> expected = new ArrayList<Double>();
		
		// Setup max,min, and nom **ASSUMPTION: min = 0; max = 100
		max = 100;
		min = 0;
		randA = 50;
		randB = 50;
		
		// Add values to x
		x = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		// Add values to y
		y = Arrays.asList(min-1,min,min+1,randB,max-1,max,max+1);
		// Add values to z
		z = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		// Add values to w
		w = Arrays.asList(min-1,min,min+1,randB,max-1,max,max+1);
	
		// Add expected value using the testset in the document.
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				for(int h = 0; h < z.size(); h++)
				{
					for(int u = 0; u < w.size(); u++)
					{
						if(x.get(i) >= min && x.get(i) <= max 
								&& y.get(j) >= min && y.get(j) <= max 
								&& z.get(h) >= min && z.get(h) <= max 
								&& w.get(u) >= min && w.get(u) <= max && x.get(i) != 0)
						{
							double totaltemp1 = z.get(h).doubleValue() + w.get(u).doubleValue();
							double totaltemp2 = x.get(i).doubleValue() + y.get(j).doubleValue();
							expected.add(x.get(i).doubleValue()/2 * w.get(u).doubleValue()/x.get(i).doubleValue() * 
									(totaltemp1 * (Math.log(totaltemp2)/Math.log(2))));
						}
						else
						{
							expected.add(-1.0);
						}
					}
				}
			}
		}
		
		// Interator for expect list.
		int iter1 = 0;
		
		// Running test (Cross product)
		
		for(int i = 0; i < x.size(); i++)
		{
			for(int j = 0; j < y.size(); j++)
			{
				for(int h = 0; h < z.size(); h++)
				{
					for(int u = 0; u < w.size(); u++)
					{
						doReturn(x.get(i)).when(part2).getTotalUniqueOperator();
						doReturn(y.get(j)).when(part2).getTotalUniqueOperand();
						doReturn(z.get(h)).when(part2).getTotalNotUniqueOperator();
						doReturn(w.get(u)).when(part2).getTotalNotUniqueOperand();
						assertEquals(Math.round(expected.get(iter1)),Math.round((Double)part2.getEffort()));
						iter1++;
					}
				}
			}
		}
		
		// Create a new spy for the next part.
		MethodLimitCheck part3 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 5: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
	     * getDoneProcessing() = false
		 */
		doReturn(false).when(part3).getDoneProcessing();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T2
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = -79
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(-79).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T3
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 179
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(179).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T4
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = -80
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(-80).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T5
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 120
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(120).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T6
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = -30
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(-30).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T7
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 101
	     * getTotalUniqueOperand() = 0
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(101).when(part3).getTotalUniqueOperator();
		doReturn(0).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T8
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = -2
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(-2).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T9
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 0
	     * getTotalNotUniqueOperand() = 0
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 102
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(0).when(part3).getTotalNotUniqueOperator();
		doReturn(0).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(102).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
		/*
		 * T10
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 50
	     * getTotalNotUniqueOperand() = 50
	     * getTotalUniqueOperator() = 50
	     * getTotalUniqueOperand() = 50
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(50).when(part3).getTotalNotUniqueOperator();
		doReturn(50).when(part3).getTotalNotUniqueOperand();
		doReturn(50).when(part3).getTotalUniqueOperator();
		doReturn(50).when(part3).getTotalUniqueOperand();
		assertEquals(16610,Math.round(part3.getEffort()));
		/*
		 * T11
	     * getDoneProcessing() = false
	     * getTotalNotUniqueOperator() = 50
	     * getTotalNotUniqueOperand() = 50
	     * getTotalUniqueOperator() = 0
	     * getTotalUniqueOperand() = 50
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(50).when(part3).getTotalNotUniqueOperator();
		doReturn(50).when(part3).getTotalNotUniqueOperand();
		doReturn(0).when(part3).getTotalUniqueOperator();
		doReturn(50).when(part3).getTotalUniqueOperand();
		assertEquals(-1,Math.round(part3.getEffort()));
	}

	@Test
	public void checkTotalOperator() throws Throwable  
	{
		////////////////// Boundary Testing ////////////////////////////
		// There are two parts. The first part is step 1 - 3 (deli1),// 
		// and the second part is step 4 (Black-box(deli2)) and 5    //
		// (White-box(deli2)                                         //
		///////////////////////////////////////////////////////////////
		
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
		 *  For the first test case, we use <O3> from the testset to come up with a testcase
		 *  O3  = 2 (Not Unique Operator = 2)
		 *  Expected: 2
		 */
		assertEquals(2, part1.getNumberOperators(test1AST));
		
		/*
		 *  For the first test case, we use <O3> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  Expected: 0
		 */
		assertEquals(0,part1.getNumberOperators(test2AST));	
		
		/*
		 *  For the first test case, we use <O3> from the testset to come up with a testcase
		 *  O3  = 51 (Not Unique Operator = 51)
		 *  Expected: 0
		 */
		assertEquals(51,part1.getNumberOperators(test3AST));
		
		/**********/
		
		/**********
		 * Step 3: Using the the ***INTEGRATED TESTING METHOD*** and the values from the testset to test the method
		 **********/
		/*
		 *   For the first test case, we use <O3> from the testset to come up with a testcase
		 *  O3  = 2 (Not Unique Operator = 2)
		 *  Expected: 2
		 */
		assertEquals(part1.getTotalNotUniqueOperator(test1AST),part1.getNumberOperators(test1AST));	
		
		/*
		 *  For the first test case, we use <O3> from the testset to come up with a testcase
		 *  O3  = 0 (Not Unique Operator = 0)
		 *  Expected: 0
		 */
		assertEquals(part1.getTotalNotUniqueOperator(test2AST),part1.getNumberOperators(test2AST));
		
		/*
		 *  For the first test case, we use <O3> from the testset to come up with a testcase
		 *  O3  = 51 (Not Unique Operator = 51)
		 *  Expected: 51
		 */
		assertEquals(part1.getTotalNotUniqueOperator(test3AST),part1.getNumberOperators(test3AST));		
		
		// Create a new spy for the next part.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********/
		
		/**********
		 * Step 4: Using the the ***MOCK*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the fourth test case, we use <O*> from the testset to come up with a testcase
		 *  O  = {-1,0,1,50,99,100,101} (Not Unique Operator)
		 *  Expected: {-1,0,1,50,99,100,-1} 
		 */
		// This list is going to store the possible for the UO1
		List<Integer> x = new ArrayList<Integer>();
		List<Integer> expected = new ArrayList<Integer>();
		
		// Setup max,min, and nom **ASSUMPTION: min = 0; max = 100
		max = 100;
		min = 0;
		randA = 50;
		randB = 50;
		
		// Add values to x
		x = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		
		
		// Add expected value using the testset in the document.
		for(int i = 0; i < x.size(); i++)
		{			
			if(x.get(i) >= min && x.get(i) <= max)						
			{
				expected.add(x.get(i));		
			}
			else
			{
				expected.add(-1);	
			}		
		}
		
		// Interator for expect list.
		int iter1 = 0;
		
		// Running test (Cross product)
		for(int i = 0; i < x.size(); i++)
		{
			doReturn(x.get(i)).when(part2).getTotalNotUniqueOperator();
			assertEquals(expected.get(iter1),(Integer)part2.getNumberOperators());
			iter1++;		
		}	
		
		// Create a new spy for the next part.
		MethodLimitCheck part3 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 5: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
		 * getDoneProcessing() = false
		 */
		doReturn(false).when(part3).getDoneProcessing();
		assertEquals(-1,part3.getNumberOperators());
		/*
		 * T2
		 * getDoneProcessing() = true
		 * getTotalNotUniqueOperator() = -1
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(-1).when(part3).getTotalNotUniqueOperator();
		assertEquals(-1,part3.getNumberOperators());
		/*
		 * T3
		 * getDoneProcessing() = true
		 * getTotalNotUniqueOperator() = 101
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(101).when(part3).getTotalNotUniqueOperator();
		assertEquals(-1,part3.getNumberOperators());
		/*
		 * T4
		 * getDoneProcessing() = true
		 * getTotalNotUniqueOperator() = 30
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(30).when(part3).getTotalNotUniqueOperator();
		assertEquals(30,part3.getNumberOperators());
		
	}
	@Test
	public void checkTotalOperand() throws Throwable  
	{
		///////////////// Boundary Testing ////////////////////////////
		// There are two parts. The first part is step 1 - 3 (deli1),// 
		// and the second part is step 4 (Black-box(deli2)) and 5    //
		// (White-box(deli2)                                         //
		///////////////////////////////////////////////////////////////
			
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
		 *  For the first test case, we use <OP3> from the testset to come up with a testcase
		 *  OP3  = 7 (Not Unique Operand = 7)
		 *  Expected: 7
		 */
		assertEquals(7, part1.getNumberOperand(test1AST));
		
		/*
		 *  For the first test case, we use <OP3> from the testset to come up with a testcase
		 *  OP3  = 3 (Not Unique Operand = 3)
		 *  Expected: 3
		 */
		assertEquals(3,part1.getNumberOperand(test2AST));	
		
		/*
		 *  For the first test case, we use <OP3> from the testset to come up with a testcase
		 *  OP3  = 81 (Not Unique Operand = 81)
		 *  Expected: 81
		 */
		assertEquals(81,part1.getNumberOperand(test3AST));
		
		/**********/
		
		/**********
		 * Step 3: Using the the ***INTEGRATED TESTING METHOD*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the first test case, we use <OP3> from the testset to come up with a testcase
		 *  OP3  = 7 (Not Unique Operand = 7)
		 *  Expected: 7
		 */
		assertEquals(part1.getTotalNotUniqueOperand(test1AST),part1.getNumberOperand(test1AST));	
		
		/*
		 *  For the first test case, we use <OP3> from the testset to come up with a testcase
		 *  OP3  = 3 (Not Unique Operand = 3)
		 *  Expected: 3
		 */
		assertEquals(part1.getTotalNotUniqueOperand(test2AST),part1.getNumberOperand(test2AST));	
		
		/*
		 *  For the first test case, we use <OP3> from the testset to come up with a testcase
		 *  OP3  = 81 (Not Unique Operand = 81)
		 *  Expected: 81
		 */
		assertEquals(part1.getTotalNotUniqueOperand(test3AST),part1.getNumberOperand(test3AST));		
		
		// Create a new spy for the next part.
		MethodLimitCheck part2 = spy(new MethodLimitCheck());
		
		/**********/
		
		/**********
		 * Step 4: Using the the ***MOCK*** and the values from the testset to test the method
		 **********/
		/*
		 *  For the fourth test case, we use <OP*> from the testset to come up with a testcase
		 *  OP  = {-1,0,1,50,99,100,101} (Not Unique Operand)
		 *  Expected: {-1,0,1,50,99,100,-1} 
		 */
		// This list is going to store the possible for the OP
		List<Integer> x = new ArrayList<Integer>();
		// This list is going to store the expected value
		List<Integer> expected = new ArrayList<Integer>();
		
		// Setup max,min, and nom **ASSUMPTION: min = 0; max = 100
		max = 100;
		min = 0;
		randA = 50;
		randB = 50;
		
		// Add values to x
		x = Arrays.asList(min-1,min,min+1,randA,max-1,max,max+1);
		
		
		// Add expected value using the testset in the document.
		for(int i = 0; i < x.size(); i++)
		{			
			if(x.get(i) >= min && x.get(i) <= max)						
			{
				expected.add(x.get(i));		
			}
			else
			{
				expected.add(-1);	
			}		
		}
		
		// Interator for expect list.
		int iter1 = 0;
		
		// Running test (Cross product)
		for(int i = 0; i < x.size(); i++)
		{
			doReturn(x.get(i)).when(part2).getTotalNotUniqueOperand();
			assertEquals(expected.get(iter1),(Integer)part2.getNumberOperand());
			iter1++;		
		}	
		
		// Create a new spy for the next part.
		MethodLimitCheck part3 = spy(new MethodLimitCheck());
		
		/**********
		 * Step 5: Whitebox testing, please read the provided document.
		 **********/
		// Using test cases from the document
		/*
		 * T1
		 * getDoneProcessing() = false
		 */
		doReturn(false).when(part3).getDoneProcessing();
		assertEquals(-1,part3.getNumberOperand());
		/*
		 * T2
		 * getDoneProcessing() = true
		 * getTotalNotUniqueOperator() = -1
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(-1).when(part3).getTotalNotUniqueOperand();
		assertEquals(-1,part3.getNumberOperand());
		/*
		 * T3
		 * getDoneProcessing() = true
		 * getTotalNotUniqueOperator() = 101
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(101).when(part3).getTotalNotUniqueOperand();
		assertEquals(-1,part3.getNumberOperand());
		/*
		 * T4
		 * getDoneProcessing() = true
		 * getTotalNotUniqueOperator() = 30
		 */
		doReturn(true).when(part3).getDoneProcessing();
		doReturn(30).when(part3).getTotalNotUniqueOperand();
		assertEquals(30,part3.getNumberOperand());
	}

	@Test
	public void checkUniqueOperator() throws Throwable  
	{
		// This method is not one of the metrics, instead, it is a helper function
		// for the metric. Since the method is really simple, we can just perform
		// unit test on it.		
		// Create a new spy for the next part.
		MethodLimitCheck part1 = spy(new MethodLimitCheck());
		
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(1, part1.getTotalUniqueOperator(caseOneAST));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, part1.getTotalUniqueOperator(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(14, part1.getTotalUniqueOperator(caseThreeAST));
	}
	
	@Test
	public void checkUniqueOperand() throws Throwable  
	{
		// This method is not one of the metrics, instead, it is a helper function
		// for the metric. Since the method is really simple, we can just perform
		// unit test on it.
		// Create a new spy for the next part.
		MethodLimitCheck part1 = spy(new MethodLimitCheck());
		
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(6, part1.getTotalUniqueOperand(caseOneAST));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(3, part1.getTotalUniqueOperand(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(24, part1.getTotalUniqueOperand(caseThreeAST));
	}
	
	/*
	 * Combined with checkEffort method.
	 */
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

	/*
	 * Combined with checkEffort method.
	 */
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
		Scanner scanner = new Scanner(System.in); 
		boolean pass = false;
		// Create a new spy for the next part.
		MethodLimitCheck part1 = spy(new MethodLimitCheck());
		
		while (!pass)
		{
		
			System.out.println("JAVA Filename: "); //TestCaseFour.java
			String userInput = scanner.nextLine();
			
			try 
			{
				DetailAST rootAST = getAST(userInput);
				System.out.println("Halstead Length: " + part1.getLength(rootAST));
				System.out.println("Halstead Vocabulary: " + part1.getVocabulary(rootAST));
				System.out.println("Total Number of operators: " + part1.getNumberOperators(rootAST));
				System.out.println("Total Number of operands: " + part1.getNumberOperand(rootAST));
				System.out.println("Halstead Volume: " + part1.getVolume(rootAST));
				System.out.println("Halstead Difficulty: " + part1.getDifficulty(rootAST));
				System.out.println("Halstead Effort: " + part1.getEffort(rootAST));
				System.out.println("notuniqueoperator: " + part1.getTotalNotUniqueOperator(rootAST));
				System.out.println("notuniqueoperand: " + part1.getTotalNotUniqueOperand(rootAST));				
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
	}
	
	/*************************************************************************/
}


 
