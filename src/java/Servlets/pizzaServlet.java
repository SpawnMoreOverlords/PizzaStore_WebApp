package Servlets;


import Beans.*; //import all of our beans
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "pizzaServlet", urlPatterns = {"/pizzaServlet"})
public class pizzaServlet extends HttpServlet {

    //Varibles to hold the values of all of our JSP pages
    private static String index = null; //login page
    private static String loginCheck = null;
    private static String loginError = null; //login error page
    private static String shop = null; //buy stuff page
    private static String checkout = null; //Confirm order page
    private static String thankYou = null; //After confirming
    private static String profile = null; //user profile page
    private static String manager = null; //add new components / pizza page
    private static String exception = null; //redirect here if any errors. 
    private static String user = null;

    private ProductListBean pizzaList = null;
    private static String jdbcURL = null;//store jdbcURL
    
    //Init the servlet
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        index = "hello servlet";
        System.out.println(index);
       // jdbcURL="jdbc:derby://localhost:1527/pizzaDB?user=pizza&amp;password=pizza&amp;"
         //       + "verifyServerCertificate=false&amp;useSSL=true";
        jdbcURL="jdbc:mysql://localhost:3306/pizzaDB?user=root&amp;password=&amp;"
               + "verifyServerCertificate=false&amp;useSSL=true";
        shop="shop.jsp";
        manager="manager.jsp";
        checkout="checkout.jsp";
        thankYou="thankYou.jsp";
        profile="profile.jsp";
        index="index.jsp";
        loginCheck="loginCheck.jsp";
        loginError="loginError.jsp";
        exception="exception.jsp";
        user="user.jsp";
        // get the books from the database using a bean
      
        try{
            pizzaList = new ProductListBean(jdbcURL);
        }
        catch(Exception e){
            throw new ServletException(e);
        }

        // servletContext is the same as scope Application
	// store the booklist in application scope

         ServletContext sc = getServletContext();
         sc.setAttribute("pizzaList",pizzaList);
    }
    
  /** Destroys the servlet.
     */
    public void destroy() {
        
    }
    //Default servlet stuff - that was created when I made the servlet.
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // get access to the session and to the shoppingcart
	// Store the logged in username in the session
	// and get jdbc-URL provided in web.xml as init-parameter
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sess = request.getSession();
        RequestDispatcher rd = null;
        ShoppingBean shoppingCart = getCart(request);
	//sess.setAttribute("currentUser", sess.getAttribute(""));
        sess.setAttribute("jdbcURL",jdbcURL);
        
        
        // find out what to do based on the attribute "action"
	// no action or show
        if(request.getParameter("action") == null || 
           request.getParameter("action").equals("show")){
	    
            // A request dispatcher that's connected to the page.
	    
            rd = request.getRequestDispatcher(shop); 
            rd.forward(request,response);
        }
        
        // add a pizza to the shopping cart
        else if(request.getParameter("action").equals("add")){
            
	    // verify pizzaname and quantity
            System.out.println("adds a pizza");
            if (request.getParameter("pizzaname") != null && 
                request.getParameter("quantity")!=null ){
                PizzaBean pb = null;
		System.out.println("got pizzaname and quantity");
		// search the pizza in our shop
                  System.out.println(request.getParameter("pizzaname"));
  	        pb = pizzaList.getByName(request.getParameter("pizzaname"));
                if(pb==null){
                    throw new ServletException("The pizza is not in stock.");
                    
                }
                else {

		    // found, add it to the cart

		    try {
		        int q = Integer.parseInt(
                                      request.getParameter("quantity"));
			if(q <= 0) throw new NumberFormatException(
						       "Invalid quantity");
                        System.out.println(pb.getName());
			shoppingCart.addPizza(pb,q);                            
		    }
		    catch (NumberFormatException e) {
			throw new ServletException("Invalid quantity specified");
		    }
                }
            }
            
	    // back to the showpage

            rd = request.getRequestDispatcher(shop);
            rd.forward(request,response);
       }

	// remove a pizza from the cart

	else if(request.getParameter("action").equals("remove")){
	    if (request.getParameter("pizzaname") != null && 
		request.getParameter("quantity")!=null ){
		try {
                    int q = Integer.parseInt(request.getParameter("quantity"));
                    if(q <= 0) throw new NumberFormatException(
                                                           "Illegal quantity");
		    shoppingCart.removePizza(request.getParameter("pizzaname"),q);
		}
		catch (NumberFormatException e) {
		    throw new ServletException("Illegal quantity specified");
		}
           }
           else{
             throw new ServletException(
		    "No bookid or quantity when removing book from cart");
           }
            rd = request.getRequestDispatcher(shop);
            rd.forward(request,response);
	}
        // make an order from our cart, empty the cart

	else if(request.getParameter("action").equals("save")){
	    
	    // if we have a shoppingcart, verify that we have
	    // valid userdata, then create an orderbean and
	    // save the order in the database

	    if (shoppingCart != null &&
		request.getParameter("shipping_name") != null && 
		request.getParameter("shipping_city") != null && 
		request.getParameter("shipping_zipcode") != null && 
		request.getParameter("shipping_address") != null){
		OrderBean ob = new OrderBean(jdbcURL, shoppingCart, 
			      request.getParameter("shipping_name").trim(), 
                              request.getParameter("shipping_address").trim(), 
                              request.getParameter("shipping_zipcode").trim(), 
                              request.getParameter("shipping_city").trim());
		try{
		    ob.saveOrder();
		}
		catch(Exception e){
		    throw new ServletException("Error saving order", e);
		}
	    }
	    else{
		throw new ServletException(
		       "Not all parameters are present or no " + 
		       " shopping cart when saving pizza");
	    }
            rd = request.getRequestDispatcher(thankYou);
            rd.forward(request,response);
       }

	// checkout, get user data, we must have a valid user

       else if(request.getParameter("action").equals("checkout")){       
	       if(sess.getAttribute("currentUser") != null) {

		   // create an profile and populate it from the
		   // database

	       ProfileBean p = new ProfileBean(jdbcURL);
	       try {
                  p.populate((String)sess.getAttribute("currentUser"));
               }
               catch(Exception e) {
                  throw new ServletException("Error loading profile", e);
               }
               sess.setAttribute("profile", p);
           
            }
 
	       // redirect (not forward)

            response.sendRedirect(checkout);
          
       }

	// logout, just delete the session (where we have the user data)
	
       else if(request.getParameter("action").equals("logout")) {
	    sess.invalidate();
          rd = request.getRequestDispatcher(index);
	    rd.forward(request,response);
        }	 

	// get a user profile from the database and store it in 
	// our session

	else if(request.getParameter("action").equals("profile")) {
	    HashMap<String,Boolean> role = null;

	    // create a profile object, fill it in from the database
	    // also store all user roles in the map "role"

	    ProfileBean p = new ProfileBean(jdbcURL);
	    try {
		p.populate((String)sess.getAttribute("currentUser"));
		//role = p.getRoles();
            }
            catch(Exception e) {
                throw new ServletException("Error loading profile", e);
            }
	    sess.setAttribute("profile", p);

	    // flag all roles that the user is associated with

//	    Set<String> k = role.keySet();
//	    Iterator<String> i = k.iterator();
//	    while (i.hasNext()) {
//		String st = i.next();
//		if(request.isUserInRole(st)) role.put(st,true);
//	    }
//	    p.setRole(role);
//	    sess.setAttribute("roles",role);
	    rd = request.getRequestDispatcher(profile);
	    rd.forward(request, response);
        }   


	// update the user profile or create a new profile
	// the profile is already create but may be empty

	else if(request.getParameter("action").equals("profilechange") ||
                request.getParameter("action").equals("usercreate")){
	    ProfileBean pb = (ProfileBean)sess.getAttribute("profile");
            if(pb==null)
            {
                        System.out.println("cannot get profile");
            }
	    String u;
	    if (request.getParameter("action").equals("profilechange")) 
		u = (String)sess.getAttribute("currentUser");
	    else 
		u = request.getParameter("user");

	    // get all data needed

	    String p1 = request.getParameter("password");
	    String p2 = request.getParameter("password2");
	    String name = request.getParameter("name");
	    String street = request.getParameter("street");
	    String zip = request.getParameter("zip");
	    String city = request.getParameter("city");
	    String country = request.getParameter("country");
	    if(pb==null)
            {
                pb=new ProfileBean(jdbcURL);
            }
	    pb.setUser(u);
	    pb.setPassword(p1);
	    pb.setName(name);
	    pb.setStreet(street);
	    pb.setZip(zip);
	    pb.setCity(city);
	    pb.setCountry(country);   

	    // if this a new user, try to add him to the database

	    if (request.getParameter("action").equals("usercreate")) {
		boolean b;

		// make sure the the username is not used already

		try {
                    b = pb.testUser(u);
		}
		catch(Exception e) {
                    throw new ServletException("Error loading user table", e);
		}
		if(b) { 
                    sess.setAttribute("passwordInvalid",
				      "User name already in use");
   		    rd = request.getRequestDispatcher(user);
		    rd.forward(request, response);

		    // note that a return is needed here because forward
		    // will not cause our servlet to stop execution, just
		    // forward the request processing

		    return; 
		}
	    }
	    
	    // now we know that we have a valid user name
	    // validate all data, 

	    boolean b = profileValidate(request,sess);
	    if (!b && request.getParameter("action").equals("profilechange")) {
		rd = request.getRequestDispatcher(profile);
		rd.forward(request, response);
	    }
	    else if (!b) {
		rd = request.getRequestDispatcher(user);
		rd.forward(request, response);
	    }

	    // validated OK,  update the database

	    else {
		
    	        ProfileUpdateBean pu = new ProfileUpdateBean(jdbcURL);
                if (request.getParameter("action").equals("profilechange")) {
		    try {
			pu.setProfile(pb);
		    }
		    catch(Exception e){
			throw new ServletException("Error saving profile", e);
		    }
		    rd = request.getRequestDispatcher(shop);
		    rd.forward(request, response);
		}
		else {
		    try {
			pu.setUser(pb);
		    }
		    catch(Exception e){
			throw new ServletException("Error saving profile", e);
		    }
                    request.setAttribute("currentUser", pb.getName());
		    rd = request.getRequestDispatcher(shop);
		    rd.forward(request, response);
		} 
	    }
	}	

	// create an empty profile, store in the in session
	// with all available roles

	else if(request.getParameter("action").equals("newuser")) {
	    ProfileBean p = new ProfileBean(jdbcURL);
	    try {
              HashMap<String,Boolean> role = p.getRoles();
	        sess.setAttribute("roles",role);
             }
            catch(Exception e) {
                throw new ServletException("Error loading profile", e);
            }
            
          sess.setAttribute("profile", p);

	    rd = request.getRequestDispatcher(user);
	    rd.forward(request, response);
        }   
       
    }
    
    
    // valide a profile

    private boolean profileValidate(HttpServletRequest request, 
				    HttpSession sess) {

	// use the attribute "passwordInvalid" as error messages

	sess.setAttribute("passwordInvalid", null);
	String u;

	// get all data

	if (request.getParameter("action").equals("profilechange")) 
	    u = (String)sess.getAttribute("currentUser");
	else 
	    u = request.getParameter("user");
	String p1 = request.getParameter("password");
	String p2 = request.getParameter("password2");
	String name = request.getParameter("name");
	String street = request.getParameter("street");
	String zip = request.getParameter("zip");
	String city = request.getParameter("city");
	String country = request.getParameter("country");
	
	if(p1 == null || p2 == null || p1.length() < 1) {
	    sess.setAttribute("passwordInvalid",
			      "Password must not be empty, retry!");
	    return false;
	    
	}
	else if (!(p1.equals(p2))){
	    sess.setAttribute("passwordInvalid",
			      "Passwords do not match, retry!");
	    return false;
	}
	else if (name == null || name.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "Name must not be empty, retry!");
	    return false;
	}
	else if (street == null || street.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "Street must no be empty, retry!");
	    return false;
	}
	else if (zip == null || zip.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "Zip code must not be empty, retry!");
	    return false;
	}
	else if (city == null || city.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "City must not be empty, retry!");
	    return false;
	}
	else if (country == null || country.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "County must not be empty, retry!");
	    return false;
	}
        
	// validation OK

	return true;
    }
    // get the shoppingcart, create it if needed
    private ShoppingBean getCart(HttpServletRequest request){
        HttpSession se = null;
        se=request.getSession();
        ShoppingBean sb =null;
        sb = (ShoppingBean)se.getAttribute("shoppingCart");
        if(sb==null){
            sb = new ShoppingBean();
            se.setAttribute("shoppingCart",sb);
        }

        return sb;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "The main PizzaShop";
    }// </editor-fold>

}
