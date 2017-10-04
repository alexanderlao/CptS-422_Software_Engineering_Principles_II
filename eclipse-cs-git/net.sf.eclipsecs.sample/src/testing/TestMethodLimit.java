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
		
		DetailAST caseTwoAST = getAST("TestCaseTwo.java");
		assertEquals(0, Math.round(MethodLimitCheck.getDifficulty(caseTwoAST)));
		
		DetailAST caseThreeAST = getAST("TestCaseThree.java");
		assertEquals(41, Math.round(MethodLimitCheck.getDifficulty(caseThreeAST)));
	}
	/*************************************************************************/
	
	/*********************************MINH NGUYEN*****************************/
	
	/*************************************************************************/
	
	/*********************************EUNJI PARK*****************************/
	
	/*************************************************************************/
	
	
	
}


 