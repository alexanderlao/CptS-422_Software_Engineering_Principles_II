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
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;
import org.xml.sax.InputSource;

import com.google.common.base.Charsets;
import com.puppycrawl.tools.checkstyle.TreeWalker;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

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
	// Halstead Volume is the program length (N) 
	// times the log2 of the program vocabulary (n) [1,2] : Volume = N log2 n
	@Test
	public void testVolume() throws Throwable
	{
		// assuming getLength() and getVocabulary() are both tested
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(25, Math.round(MethodLimitCheck.getVolume(caseOneAST)));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(5, Math.round(MethodLimitCheck.getVolume(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(693, Math.round(MethodLimitCheck.getVolume(caseThreeAST)));
	}
	
	// Halstead Difficulty is half of the unique operators 
	// multiplied by the total number of operands, 
	// divided by the number of distinct operators [1,2]
	@Test
	public void testDifficulty() throws Throwable
	{
	
		// assuming getUniqueOperators() and getTotalOperands() are both tested
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(4, Math.round(MethodLimitCheck.getDifficulty(caseOneAST)));
		/*
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, Math.round(MethodLimitCheck.getDifficulty(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(41, Math.round(MethodLimitCheck.getDifficulty(caseThreeAST)));*/
	}
	/*************************************************************************/
	
	/*********************************MINH NGUYEN*****************************/
	@Test
	public void checkLength() throws Exception  {
		///////////////// Unit testing ///////////
		DetailAST rootAST = getAST("TestCaseOne.java");
		assertEquals(9,MethodLimitCheck.getLength(rootAST));	
		rootAST = getAST("TestCaseTwo.java");
		assertEquals(3,MethodLimitCheck.getLength(rootAST));	
		rootAST = getAST("TestCaseThree.java");
		assertEquals(132,MethodLimitCheck.getLength(rootAST));
		
		// Integration testing.
		rootAST = getAST("TestCaseOne.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertEquals(MethodLimitCheck.getTotalNotUniqueOperator(rootAST) + MethodLimitCheck.getTotalNotUniqueOperand(rootAST),MethodLimitCheck.getLength(rootAST));	
		rootAST = getAST("TestCaseTwo.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertEquals(MethodLimitCheck.getTotalNotUniqueOperator(rootAST) + MethodLimitCheck.getTotalNotUniqueOperand(rootAST),MethodLimitCheck.getLength(rootAST));	
		rootAST = getAST("TestCaseThree.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertEquals(MethodLimitCheck.getTotalNotUniqueOperator(rootAST) + MethodLimitCheck.getTotalNotUniqueOperand(rootAST),MethodLimitCheck.getLength(rootAST));		
	}
	
	@Test
	public void testToString() throws Exception
	{
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
	}
	
	
	@Test
	public void checkProcess() throws Exception
	{
		
		// System testing.
		DetailAST rootAST = getAST("TestCaseOne.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertTrue(MethodLimitCheck.process(rootAST));	
		rootAST = getAST("TestCaseTwo.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertFalse(MethodLimitCheck.process(rootAST));	
		rootAST = getAST("TestCaseThree.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertTrue(MethodLimitCheck.process(rootAST));
	}
	
	@Test
	public void checkVocab() throws Exception  {
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
	}
	/*************************************************************************/
	
	/*********************************EUNJI PARK*****************************/
	// Unit Testing
	@Test
	public void checkTotalOperator() throws Throwable  {
		DetailAST rootAST = getAST("TestCaseOne.java");
		// Expected: 2
		assertEquals(2,MethodLimitCheck.getTotalNotUniqueOperator(rootAST));
		
	}
	@Test
	public void checkTotalOperand() throws Throwable  {
		DetailAST rootAST = getAST("TestCaseOne.java");
		// Expected: 7
		assertEquals(7,MethodLimitCheck.getTotalNotUniqueOperand(rootAST));	
	}
	@Test
	public void checkUniqueOperator() throws Throwable  {
		DetailAST rootAST = getAST("TestCaseOne.java");
		// Expected: 1
		assertEquals(1,MethodLimitCheck.getTotalUniqueOperator(rootAST));	
	}
	@Test
	public void checkUniqueOperand() throws Throwable  {
		DetailAST rootAST = getAST("TestCaseOne.java");
		// Expected: 6
		assertEquals(6,MethodLimitCheck.getTotalUniqueOperand(rootAST));		
	}
	/*************************************************************************/
	
	
	
}


 