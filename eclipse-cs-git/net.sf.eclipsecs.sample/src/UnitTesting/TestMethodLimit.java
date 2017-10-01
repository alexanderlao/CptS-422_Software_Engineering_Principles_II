package UnitTesting;

import static org.junit.Assert.*;

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

public class TestMethodLimit extends MethodLimitCheck{
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
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, Math.round(MethodLimitCheck.getDifficulty(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(41, Math.round(MethodLimitCheck.getDifficulty(caseThreeAST)));
	}
	/*************************************************************************/
	
	/*********************************MINH NGUYEN*****************************/
	@Test
	public void checkLength() throws Exception  {
		DetailAST rootAST = getAST("TestCaseOne.java");
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
	public void checkVocab() throws Exception  {
		DetailAST rootAST = getAST("TestCaseOne.java");	
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
	
	/*********************************ANGIE PARK*****************************/
	// Unit Testing
	@Test
	public void checkEffort() throws Throwable  {
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
	}
	
	@Test
	public void checkTotalOperator() throws Throwable  {
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(2, MethodLimitCheck.getNumberOperators(caseOneAST));
	
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, MethodLimitCheck.getNumberOperators(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(51, MethodLimitCheck.getNumberOperators(caseThreeAST));
	}
	@Test
	public void checkTotalOperand() throws Throwable  {
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(7, MethodLimitCheck.getNumberOperand(caseOneAST));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(3, MethodLimitCheck.getNumberOperand(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(81, MethodLimitCheck.getNumberOperand(caseThreeAST));
	}
	@Test
	public void checkUniqueOperator() throws Throwable  {
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(1, MethodLimitCheck.getTotalUniqueOperator(caseOneAST));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, MethodLimitCheck.getTotalUniqueOperator(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(14, MethodLimitCheck.getTotalUniqueOperator(caseThreeAST));
	}
	@Test
	public void checkUniqueOperand() throws Throwable  {
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		assertEquals(6, MethodLimitCheck.getTotalUniqueOperand(caseOneAST));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(3, MethodLimitCheck.getTotalUniqueOperand(caseTwoAST));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(24, MethodLimitCheck.getTotalUniqueOperand(caseThreeAST));
	}
	
	
	// Integration Testing

	@Test
	public void checkEffortWithDifficulty() throws Throwable  {
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
	}
	@Test
	public void checkEffortWithVolume() throws Throwable {
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
	}
	/*************************************************************************/
}
	
 
