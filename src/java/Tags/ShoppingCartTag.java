/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tags;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.*;
import java.io.*;
import Beans.*;
/**
 *
 * @author Kimi
 */
/**
 *  Generated tag class.
 */
public class ShoppingCartTag extends TagSupport {


    public ShoppingCartTag() {
        super();
    }
    
    
    /** .//GEN-BEGIN:doStartTag
     *
     * This method is called when the JSP engine encounters the start tag,
     * after the attributes are processed.
     * Scripting variables (if any) have their values set here.
     * @return EVAL_BODY_INCLUDE if the JSP engine should evaluate the tag 
     * body, otherwise return SKIP_BODY.
     *
     */

    public int doStartTag() throws JspException, JspException {
	return EVAL_BODY_INCLUDE;
    }
    
    /** .//GEN-BEGIN:doEndTag
     *
     *
     * This method is called after the JSP engine finished processing the tag.
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP
     *  page, otherwise return SKIP_PAGE.
     *
     */

    public int doEndTag() throws JspException, JspException {
	try{ 

	    // get a writer from the page context
	    // then get access to the session
	    // get the shoppingcart from the session
	    // and generate an XML document using the proper method
	    // in the shoppingcart
	    
            
	    JspWriter out = pageContext.getOut();
	    javax.servlet.http.HttpSession sess = pageContext.getSession();
	    ShoppingBean sb = (ShoppingBean)sess.getAttribute("shoppingCart");
	    out.println(sb.getXml());
            
	}
	catch(Exception e){
	    throw new JspException(e);
	}
	return EVAL_PAGE;
    }
}

