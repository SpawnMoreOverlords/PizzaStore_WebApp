/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tags;

/**
 *
 * @author Kimi
 */
import Beans.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

public class ProfileTag extends TagSupport {

	private String url = null;

	public void setUrl(String _url) {
        url = _url;
      }

	public int doStartTag() throws JspException {
	    try {

		// get access to the session and to the request
 
		JspWriter out = pageContext.getOut();
		System.out.println("URL = " + url);
		HttpSession sess = pageContext.getSession();
		HttpServletRequest request = 
		    (HttpServletRequest) pageContext.getRequest();

		// get the username and store it in the session

		String user = request.getRemoteUser();
		sess.setAttribute("currentUser",user);

		// create a profile bean and start populate it
		// store it in the request

		ProfileBean pb = new ProfileBean(url);
		pb.populate(user);
		request.setAttribute("profile", pb);
	    } catch (Exception e) {
		throw new JspException(
				       e.getMessage());
	    }
	    return SKIP_BODY;
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}

