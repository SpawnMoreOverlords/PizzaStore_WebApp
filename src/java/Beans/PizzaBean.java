/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

/**
 *
 * @author Kimi
 */
public class PizzaBean {
    //describe a pizza
    private String name;
    private int price;
    private String description;
    private int component_number;
    
    public PizzaBean(){
    }
   
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int _price) {
        price = _price;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String _name) {
        name=_name;
    }
    
     public String getDes() {
        return description;
    }
    
    public void setDes(String _des) {
        description=_des;
    }
    
    public int getCompNum() {
        return component_number;
    }
    
    public void setCompNum(int _compnum) {
        component_number = _compnum;
    }
    // create an XML document describing the pizza
    
    public String getXml() {

	// use a Stringbuffer (not String) to avoid multiple
	// object creation

     StringBuffer xmlOut = new StringBuffer();
      
      xmlOut.append("<pizza>");
      xmlOut.append("<name>");
      xmlOut.append(name);
      xmlOut.append("</pizza>");      
      xmlOut.append("<price>");
      xmlOut.append(price);      
      xmlOut.append("</price>");
      xmlOut.append("<description><![CDATA[");
      xmlOut.append(description);      
      xmlOut.append("]]></description>");   
      xmlOut.append("</book>");
      
      return xmlOut.toString();
    
        
    }   
}
