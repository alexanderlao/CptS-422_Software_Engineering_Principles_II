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
	@Test
	public void checkLength() throws Exception  {
		DetailAST rootAST = getAST("TestCaseOne.java");
		// Assuming MethodLimitCheck.getTotalNotUniqueOperator() and MethodLimitCheck.getTotalNotUniqueOperand are tested.
		assertEquals(MethodLimitCheck.getTotalNotUniqueOperator(rootAST) + MethodLimitCheck.getTotalNotUniqueOperand(rootAST),MethodLimitCheck.getLength(rootAST));
	}
	@Test
	public void checkVocab() throws Exception  {
		DetailAST rootAST = getAST("TestCaseOne.java");	
		// Assuming MethodLimitCheck.getTotalUniqueOperator() and MethodLimitCheck.getTotalUniqueOperand are tested.
		assertEquals(MethodLimitCheck.getTotalUniqueOperator(rootAST) + MethodLimitCheck.getTotalUniqueOperand(rootAST),MethodLimitCheck.getVocabulary(rootAST));
	}
}
 