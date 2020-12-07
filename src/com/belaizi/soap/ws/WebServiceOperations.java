package com.belaizi.soap.ws;

public class WebServiceOperations {
	
	public  double  annuite(double capital, double duree, double taux) {
	   	 double tm = Math.pow(1 + taux,(double)1/12) - 1;
	   	 double a = (Math.pow(1+tm, duree)* tm * capital)/(Math.pow(1+tm, duree) - 1 );
	   	 return a ;  
	   	 
	    }
	
	    public double capital(double annuite , double duree, double taux) {
	   	 double tm = Math.pow(1 + taux,(double)1/12) - 1; 
	   	 double C = ( annuite * (Math.pow(1+tm, duree) - 1 ) )/(Math.pow(1+tm, duree)* tm );
	   	 return C ;
	    }
	    
	    public int duree(double capital , double annuite, double taux) {
	   	 double tm = Math.pow(1 + taux,(double)1/12) - 1; 
	   	 double d = Math.log(annuite/(annuite- tm*capital)) / Math.log(1+tm);
	   	      
	   	 return (int) Math.round(d);                      
	    }
	

}
