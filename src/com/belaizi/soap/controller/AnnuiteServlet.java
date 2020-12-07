package com.belaizi.soap.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.belaizi.soap.dao.SaveCreditDAO;
import com.belaizi.soap.model.Credit;
import com.belaizi.soap.ws.WebServiceOperationsStub;
import com.belaizi.soap.ws.WebServiceOperationsStub.Annuite;
import com.belaizi.soap.ws.WebServiceOperationsStub.AnnuiteResponse;

/**
 * Servlet implementation class AnnuiteServlet
 */
public class AnnuiteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AnnuiteServlet() {
        // TODO Auto-generated constructor stub
    }
 
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
   		 HttpSession session = request.getSession();
   		 WebServiceOperationsStub wbstub= new WebServiceOperationsStub();
   		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date d = new Date();
		 String date = dateFormat.format(d);
	     Credit credit =new Credit();
	     
   		double capitalValue=Double.parseDouble(request.getParameter("inputCapital")) ;
   		int dureeValue=Integer.parseInt(request.getParameter("inputDuree")) ;
   		double tauxValue=(Double.parseDouble(request.getParameter("inputTaux")))*0.01;
   		
   		
   		Annuite annuite=new Annuite();
   		annuite.setCapital(capitalValue);
   		annuite.setDuree(dureeValue);
   		annuite.setTaux(tauxValue);
         
   		AnnuiteResponse res=wbstub.annuite(annuite);  		
   		double resultatAnnuite = res.get_return();
   		
   		session.setAttribute("capitalResA", capitalValue);
   		session.setAttribute("dureeResA", dureeValue);
   		session.setAttribute("tauxResA", tauxValue);
   		session.setAttribute("annuiteResA", resultatAnnuite);
	

		 
		 int numeroClient = ((Integer)session.getAttribute("numClient")).intValue();

		 credit.setAnnuiteCre(resultatAnnuite);
		 credit.setDureeCre(dureeValue);
		 credit.setCapitalCre(capitalValue);
		 credit.setTauxCre(tauxValue);

		 credit.setDateCre(date);
		 credit.setNumClient(numeroClient);
			
		  SaveCreditDAO.saveDB(credit);
   		
   		
   		
        response.sendRedirect("Annuite.jsp");
  	
   	}

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
    
    
    
   
	

}
