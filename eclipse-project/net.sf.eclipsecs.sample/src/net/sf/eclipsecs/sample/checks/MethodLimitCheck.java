package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.List;

public class MethodLimitCheck extends AbstractCheck {

    private int max = 30;

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
    }

    public void setMax(int limit) {
        max = limit;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // find the OBJBLOCK node below the CLASS_DEF/INTERFACE_DEF
        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        // count the number of direct children of the OBJBLOCK
        // that are METHOD_DEFS
        int methodDefs = objBlock.getChildCount(TokenTypes.METHOD_DEF);
        // report error if limit is reached
        if (methodDefs > max) {
            log(ast.getLineNo(), "methodlimit", max);
        }
    }
    
    public static List<DetailAST> getDetailASTsForTypeInBranch(DetailAST expr,
            int tokenType) {
        return getDetailASTsForTypeInBranch(expr, tokenType, null);
    }

    private static List<DetailAST> getDetailASTsForTypeInBranch(DetailAST expr,
            int tokenType, List<DetailAST> list) {
        if (list == null)
            list = new ArrayList<DetailAST>();
        
        for(DetailAST child = (DetailAST) expr.getFirstChild(); child != null; child = (DetailAST) child.getNextSibling()) {
            if (child.getType() == tokenType) {
                list.add(child);
            } else {
                list = getDetailASTsForTypeInBranch(child, tokenType, list);
            }
        }
        
        return list;
    }

	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return null;
	}
}
