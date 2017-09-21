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

public class TestMethodLimit extends MethodLimitCheck
{
	private DetailAST getAST(String filename) throws Exception
	{
		File file = new File(System.getProperty("user.dir") + "\\src\\UnitTesting\\" + filename);	
		List<String> lines = Files.readAllLines(Paths.get(System.getProperty("user.dir") + "\\src\\" + filename));	
		FileText text = FileText.fromLines(file, lines);		
		FileContents contents = new FileContents(text);
		return TreeWalker.parseWithComments(contents);	
	}
	
	private double getExpectedVolume(String fileName)
	{
		DetailAST rootAST = null;
		
		try 
		{
			rootAST = getAST(fileName);
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
		int length = MethodLimitCheck.getLength(rootAST);
		double vocabulary = MethodLimitCheck.getVocabulary(rootAST);
		return length * (Math.log(vocabulary)/Math.log(2));
	}
	
	private double getExpectedDifficulty(String fileName)
	{
		DetailAST rootAST = null;
		
		try 
		{
			rootAST = getAST(fileName);
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
		double uniqueOperators = MethodLimitCheck.getTotalUniqueOperator(rootAST);
		double totalOperands = MethodLimitCheck.getTotalNotUniqueOperand(rootAST);
		return ((uniqueOperators / 2) * (totalOperands)) / uniqueOperators;
	}
	
	// Halstead Volume is the program length (N) 
	// times the log2 of the program vocabulary (n) [1,2] : Volume = N log2 n
	@Test
	public void testVolume() throws Throwable
	{
		// assuming getLength() and getVocabulary() are both tested
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		double caseOneExpected = getExpectedVolume("TestCaseOne.java");
		assertEquals(Math.round(caseOneExpected), Math.round(MethodLimitCheck.getVolume(caseOneAST)));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		double caseTwoExpected = getExpectedVolume("TestCaseTwo.java");
		assertEquals(Math.round(caseTwoExpected), Math.round(MethodLimitCheck.getVolume(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		double caseThreeExpected = getExpectedVolume("TestCaseThree.java");
		assertEquals(Math.round(caseThreeExpected), Math.round(MethodLimitCheck.getVolume(caseThreeAST)));
	}
	
	// Halstead Difficulty is half of the unique operators 
	// multiplied by the total number of operands, 
	// divided by the number of distinct operators [1,2]
	@Test
	public void testDifficulty() throws Throwable
	{
		// assuming getUniqueOperators() and getTotalOperands() are both tested
		DetailAST caseOneAST = getAST("TestCaseOne.java");
		double caseOneExpected = getExpectedDifficulty("TestCaseOne.java");
		assertEquals(Math.round(caseOneExpected), Math.round(MethodLimitCheck.getDifficulty(caseOneAST)));
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		double caseTwoExpected = getExpectedDifficulty("TestCaseTwo.java");
		assertEquals(Math.round(caseTwoExpected), Math.round(MethodLimitCheck.getDifficulty(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		double caseThreeExpected = getExpectedDifficulty("TestCaseThree.java");
		assertEquals(Math.round(caseThreeExpected), Math.round(MethodLimitCheck.getDifficulty(caseThreeAST)));
	}
}