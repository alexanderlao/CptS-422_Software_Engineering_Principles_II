package net.sf.eclipsecs.sample.checks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import antlr.collections.ASTEnumeration;

public class MethodLimitCheck extends AbstractCheck {

    private int max = 3;
    private static int test = 0;

    @Override
    public int[] getDefaultTokens() 
    {
        return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
    }

    public void setMax(int limit) 
    {
        max = limit;
    }
    static List<Integer> totalTokenInObj = new ArrayList<Integer>();
    @Override
    public void visitToken(DetailAST aAST) 
    {	
    	//List<Integer> test = new ArrayList<Integer>();
    	List<Integer> operatorList = new ArrayList<Integer>();
    	operatorList = Arrays.asList(TokenTypes.ASSIGN,TokenTypes.BAND,TokenTypes.BAND_ASSIGN,
    			TokenTypes.BNOT,TokenTypes.BOR,TokenTypes.BOR_ASSIGN,TokenTypes.BSR,TokenTypes.BSR_ASSIGN,
    			TokenTypes.BXOR,TokenTypes.BXOR_ASSIGN,TokenTypes.COLON,TokenTypes.COMMA,TokenTypes.DEC,
    			TokenTypes.DIV,TokenTypes.DIV_ASSIGN,TokenTypes.DOT,TokenTypes.EQUAL,TokenTypes.GE,
    			TokenTypes.GT,TokenTypes.INC,TokenTypes.INDEX_OP,TokenTypes.LAND,TokenTypes.LE,
    			TokenTypes.LITERAL_INSTANCEOF,TokenTypes.LNOT,TokenTypes.LOR,TokenTypes.LT,
    			TokenTypes.MINUS,TokenTypes.MINUS_ASSIGN,TokenTypes.MOD,TokenTypes.MOD_ASSIGN,
    			TokenTypes.NOT_EQUAL,TokenTypes.PLUS,TokenTypes.PLUS_ASSIGN,TokenTypes.POST_DEC,
    			TokenTypes.POST_INC,TokenTypes.QUESTION,TokenTypes.SL,TokenTypes.SL_ASSIGN,
    			TokenTypes.SR,TokenTypes.SR_ASSIGN,TokenTypes.STAR,TokenTypes.STAR_ASSIGN,
    			TokenTypes.UNARY_MINUS,TokenTypes.UNARY_PLUS);
    	
    	List<Integer> operandList = new ArrayList<Integer>();
    	operandList = Arrays.asList(TokenTypes.IDENT,TokenTypes.STRING_LITERAL,
    								TokenTypes.CHAR_LITERAL, TokenTypes.LITERAL_TRUE,TokenTypes.LITERAL_FALSE,
    								TokenTypes.LITERAL_NULL,TokenTypes.NUM_FLOAT,TokenTypes.NUM_INT,
    								TokenTypes.NUM_DOUBLE, TokenTypes.NUM_LONG);
    	
    	List <DetailAST> totalNotUniqueOperator = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operatorList.size(); i++)
    	{
    			totalNotUniqueOperator.addAll(getDetailASTsForTypeInBranch(aAST.findFirstToken(TokenTypes.OBJBLOCK),operatorList.get(i),false));
    	}
    	
    	List <DetailAST> totalNotUniqueOperand = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operandList.size(); i++)
    	{
    			totalNotUniqueOperand.addAll(getDetailASTsForTypeInBranch(aAST.findFirstToken(TokenTypes.OBJBLOCK),operandList.get(i),false));
    	}
    	log(aAST.getLineNo(), "methodlimit", "Halstead Length: " + (totalNotUniqueOperator.size() + totalNotUniqueOperand.size()));
    
    	// Number of operators [3]
    	List <DetailAST> totalUniqueOperator = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operatorList.size(); i++)
    	{
    			totalUniqueOperator.addAll(getDetailASTsForTypeInBranch(aAST.findFirstToken(TokenTypes.OBJBLOCK),operatorList.get(i),true));
    	}
    	
    	// Number of operands [3]
    	List <DetailAST> totalUniqueOperand = new ArrayList<DetailAST>(); 
    	for(int i = 0; i < operandList.size(); i++)
    	{
    			totalUniqueOperand.addAll(getDetailASTsForTypeInBranch(aAST.findFirstToken(TokenTypes.OBJBLOCK),operandList.get(i),true));
    	}
    	
    	// Halstead Vocabulary is the sum of the number of unique operators and unique operands [1,2]
    	log(aAST.getLineNo(), "methodlimit", "Halstead Vocabulary: " + (totalUniqueOperator.size() + totalUniqueOperand.size()));
    	
    	log(aAST.getLineNo(), "methodlimit", "Total Number of operators: " + totalNotUniqueOperator.size());
    	
    	log(aAST.getLineNo(), "methodlimit", "Total Number of operands: " + totalNotUniqueOperand.size());
    	
    	log(aAST.getLineNo(), "methodlimit", "Halstead Volume: " + findVolume(totalNotUniqueOperator.size() + totalNotUniqueOperand.size(),
    																		  totalUniqueOperator.size() + totalUniqueOperand.size()));
    	
    	log(aAST.getLineNo(), "methodlimit", "Halstead Difficulty: " + findDifficulty(totalUniqueOperator.size(),totalNotUniqueOperand.size(),
    																				  totalUniqueOperand.size()));
    	
    	log(aAST.getLineNo(), "methodlimit", "Halstead Effort: " + findEffort(findDifficulty(totalUniqueOperator.size(),totalNotUniqueOperand.size(),
    																						 totalUniqueOperand.size()),
    																						 findVolume(totalNotUniqueOperator.size() + totalNotUniqueOperand.size(),
    																						 totalUniqueOperator.size() + totalUniqueOperand.size())));
    }
    
    // Halstead Volume is the program length (N) times the log2 of the program vocabulary (n) 
    // [1,2] : Volume = N log2 n
    private static double findVolume(int N, int n)
    {
    	return (N * (Math.log(n)/Math.log(2)));
    }
    
    // Halstead Effort is the difficulty multiplied by the volume. Effort = DV. 
    // Effort was intended as a suggestion for how long code review might take [1,2]
    private static double findEffort(double diff, double vol)
    {
    	return diff*vol;
    }
    
    // Halstead Difficulty is half of the unique operators multiplied by the total number of operands, 
    // divided by the number of distinct operators [1,2]
    private static double findDifficulty(double uniqueOperator,double totalOperand,double uniqueOperand)
    {
    	return (uniqueOperator/2) * (totalOperand/uniqueOperand);
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

	@Override
	public int[] getAcceptableTokens() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRequiredTokens() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}