/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Kimi
 */
public class ShoppingBean {
    private Collection cart;
    
    /** Creates a new instance of ShoppingBean */

    public ShoppingBean() {
        cart = new ArrayList();
    }
    
    // add some copies of a pizza to the shopping cart

    public void addPizza(PizzaBean pb, int quantity) {
        
        Object newItem[] = null;
        PizzaBean tmpBean = null;

	// if the cart is empty just add the pizza

        if (cart.isEmpty()){
            newItem = new Object[2];
            newItem[0]=pb;
            newItem[1]=new Integer(quantity);    
            cart.add(newItem);
        }

	// otherwise we need to check if this pizza already
	// is in the cart

        else{
	    Iterator iter = cart.iterator();  // get an iterator
	    Object tmpArr[];
	    boolean found = false;
	    while(iter.hasNext()){
		tmpArr=(Object[])iter.next();

		// check if we found the pizza

		if(((PizzaBean)tmpArr[0]).getName()==pb.getName()){ 

		    // yes, increase the quantity

		    Integer tmpAntal = (Integer)tmpArr[1];
		    tmpArr[1]=new Integer(tmpAntal.intValue()+quantity); 
		    found= true;
		}
		
	    }
	    
	    // if we didn't find it, add it
	    
	    if(!found){
		newItem = new Object[2];
		newItem[0]=pb;
		newItem[1]=new Integer(quantity);    
		cart.add(newItem);
		System.out.println("addProduct: cart.size():" + cart.size());
	    }
	    
        }          
    }    

    // remove some copies of a pizza from the cart

    public void removePizza(String name, int quantity) {

	// if must not be empty

        if(!cart.isEmpty()){
            Iterator iter = cart.iterator();
            Object tmpArr[];

	    // search for the book

            while(iter.hasNext()){
                tmpArr=(Object[])iter.next();
                if(((PizzaBean)tmpArr[0]).getName()==name){

		    // found

                    Integer tmpAntal = (Integer)tmpArr[1];

		    // if all copies removed, remove the pizza
		    // from the cart

                    if(tmpAntal.intValue()<=quantity){
                        iter.remove();
                    }
                    else{

			// else reduce quantity

                        tmpArr[1]=new Integer(tmpAntal.intValue()-quantity);
                    }
                }
            }
        }
    }     
    
    // clear the cart

    void clear() {
        cart.clear();
    }
    
    // create an XML document out of the shopping cart

    public String getXml(){
        StringBuffer buff = new StringBuffer();
        
        Iterator iter = cart.iterator();
        Object objBuff[] = null;
        buff.append("<shoppingcart>");
        
        while(iter.hasNext()){
            objBuff =(Object[])iter.next();
            buff.append("<order>");
            buff.append(((PizzaBean)objBuff[0]).getXml());
            buff.append("<quantity>");
            buff.append(((Integer)objBuff[1]).intValue());
            buff.append("</quantity>");
            buff.append("</order>");            
        }
        buff.append("</shoppingcart>");
        return buff.toString();
    }

    // get the cart

    public Collection getCart(){
      return cart;
    }
    
}

