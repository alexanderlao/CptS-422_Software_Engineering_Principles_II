package net.sf.eclipsecs.sample.checks;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.*;

import antlr.collections.ASTEnumeration;

public class MethodLimitCheck extends AbstractCheck {

	
    private int max = 3;
    private static int test = 0;

    private static List <DetailAST> totalNotUniqueOperator;
    private static List <DetailAST> totalNotUniqueOperand;
    private static List <DetailAST> totalUniqueOperator;
    private static List <DetailAST> totalUniqueOperand;
    
    
    static boolean check = false;

    
    public static int getTotalNotUniqueOperator(DetailAST root)
    {
    	process(root);
    	return totalNotUniqueOperator.size();
    	
    }
    
    public static int getTotalNotUniqueOperand(DetailAST root)
    {
    	process(root);
    	return totalNotUniqueOperand.size();
    	
    }
    
    public static int getTotalUniqueOperator(DetailAST root)
    {
    	process(root);
    	return totalUniqueOperator.size();
    	
    }
    
    public static int getTotalUniqueOperand(DetailAST root)
    {
    	process(root);
    	return totalUniqueOperand.size();
    }
    
    public static int getLength(DetailAST root)
    {
    	process(root);
    	return totalNotUniqueOperator.size() + totalNotUniqueOperand.size();
    }
    
    public static int getVocabulary(DetailAST root)
    {
    	process(root);
    	return totalUniqueOperator.size() + totalUniqueOperand.size();
    }
    
    public static int getNumberOperators(DetailAST root)
    {
    	process(root);
    	return totalNotUniqueOperator.size();
    }
    
    public static int getNumberOperand(DetailAST root)
    {
    	process(root);
    	return totalNotUniqueOperand.size();
    }
    
    public static double getVolume(DetailAST root)
    {
    	process(root);
    	return findVolume(totalNotUniqueOperator.size() + totalNotUniqueOperand.size(),
    			totalUniqueOperator.size() + totalUniqueOperand.size());
    }
    
    public static double getDifficulty(DetailAST root)
    {
    	process(root);
    	return findDifficulty(totalUniqueOperator.size(),totalNotUniqueOperand.size());
    }
    
    public static double getEffort(DetailAST root)
    {
    	process(root);
    	return findEffort(findDifficulty(totalUniqueOperator.size(),totalNotUniqueOperand.size())
    			,findVolume(totalNotUniqueOperator.size() + totalNotUniqueOperand.size()
				,totalUniqueOperator.size() + totalUniqueOperand.size()));
    }
    
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
    }

    public void setMax(int limit) {
        max = limit;
    }
    static List<Integer> totalTokenInObj = new ArrayList<Integer>();
    @Override
    public void visitToken(DetailAST aAST) {
    	process(aAST);
    	log(aAST.getLineNo(), "methodlimit",aAST.toStringList());
    	if(checkDone())
    	{
    	log(aAST.getLineNo(), "methodlimit", "Halstead Length: " + (totalNotUniqueOperator.size() + totalNotUniqueOperand.size()));
    	log(aAST.getLineNo(), "methodlimit", "Halstead Vocabulary: " + (totalUniqueOperator.size() + totalUniqueOperand.size()));
    	log(aAST.getLineNo(), "methodlimit", "Total Number of operators: " + totalNotUniqueOperator.size());
    	log(aAST.getLineNo(), "methodlimit", "Total Number of operands: " + totalNotUniqueOperand.size());
    	log(aAST.getLineNo(), "methodlimit", "Halstead Volume: " + findVolume(totalNotUniqueOperator.size() + totalNotUniqueOperand.size()
    																	,totalUniqueOperator.size() + totalUniqueOperand.size()));
    	log(aAST.getLineNo(), "methodlimit", "Halstead Difficulty: " + findDifficulty(totalUniqueOperator.size(),totalNotUniqueOperand.size()
    																						));
    	log(aAST.getLineNo(), "methodlimit", "Halstead Effort: " + findEffort(findDifficulty(totalUniqueOperator.size(),totalNotUniqueOperand.size()
    																						),findVolume(totalNotUniqueOperator.size() + totalNotUniqueOperand.size()
    																	,totalUniqueOperator.size() + totalUniqueOperand.size())));    	
    	}
    }
    
    private boolean checkDone()
    {
    	if( !totalNotUniqueOperator.isEmpty() && !totalNotUniqueOperand.isEmpty()
    			&& !totalUniqueOperator.isEmpty() && !totalUniqueOperand.isEmpty())
    	{
    		return true;
    	}
    	return false;
    }
    
    public static void process(DetailAST aAST)
    {
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
    	
    	totalNotUniqueOperator = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operatorList.size(); i++)
    	{
    			totalNotUniqueOperator.addAll(getDetailASTsForTypeInBranch(aAST.findFirstToken(TokenTypes.OBJBLOCK),operatorList.get(i),false));
    	}
    	totalNotUniqueOperand = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operandList.size(); i++)
    	{
    			totalNotUniqueOperand.addAll(getDetailASTsForTypeInBranch(aAST.findFirstToken(TokenTypes.OBJBLOCK),operandList.get(i),false));
    	}
    	totalUniqueOperator = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operatorList.size(); i++)
    	{
    			totalUniqueOperator.addAll(getDetailASTsForTypeInBranch(aAST.findFirstToken(TokenTypes.OBJBLOCK),operatorList.get(i),true));
    	}
    	
    	totalUniqueOperand = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operandList.size(); i++)
    	{
    			totalUniqueOperand.addAll(getDetailASTsForTypeInBranch(aAST.findFirstToken(TokenTypes.OBJBLOCK),operandList.get(i),true));
    	}
    }

    private static double findVolume(int N, int n)
    {
    	return (N * (Math.log(n)/Math.log(2)));
    }
    
    private static double findEffort(double diff, double vol)
    {
    	return diff*vol;
    }
    
    private static double findDifficulty(double uniqueOperator,double totalOperand)
    {
    	return ((uniqueOperator/2) * totalOperand)/ uniqueOperator;
    }
    
    public static List<DetailAST> getDetailASTsForTypeInBranch(DetailAST expr,int tokenType, boolean unique) 
    {
        return getDetailASTsForTypeInBranch(expr, tokenType, null,unique);
    }

    private static List<DetailAST> getDetailASTsForTypeInBranch(DetailAST expr,int tokenType, List<DetailAST> list, boolean unique) 
    {
        if (list == null)
        {
            list = new ArrayList<DetailAST>();
        }
        for(DetailAST child = (DetailAST) expr.getFirstChild(); child != null; child = (DetailAST) child.getNextSibling()) {                    
            getDetailASTsForTypeInBranch(child, tokenType, list,unique); 
            if(child.getType() == tokenType)
            {
            	if(!unique)
            	{
            		list.add(child);
            	}
            	else
            	{
            		boolean check = false;
            		for(int i = 0; i < list.size();i++)
            		{
            			if(list.get(i).equals(child))
            			{
            				check = true;
            			}
            		}
            		if(check == false)
            		{
            			list.add(child);
            		}
            	}
            }
        }
        return list;
    }
}

    
    
