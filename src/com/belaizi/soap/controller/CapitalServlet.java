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
import com.belaizi.soap.ws.WebServiceOperationsStub.Capital;
import com.belaizi.soap.ws.WebServiceOperationsStub.CapitalResponse;

/**
 * Servlet implementation class CapitalServlet
 */
public class CapitalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CapitalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
   		WebServiceOperationsStub wbstub= new WebServiceOperationsStub();
   		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date d = new Date();
		 String date = dateFormat.format(d);
	     Credit credit =new Credit();
	     
   		double annuiteValue=Double.parseDouble(request.getParameter("inputAnnuite")) ;
   		int dureeValue=Integer.parseInt(request.getParameter("inputDuree")) ;
   		double tauxValue=(Double.parseDouble(request.getParameter("inputTaux")))*0.01;
   		
   		Capital cap=new Capital();
   		cap.setAnnuite(annuiteValue);
   		cap.setDuree(dureeValue);
   		cap.setTaux(tauxValue);
    
   		CapitalResponse res=wbstub.capital(cap);  		
   		double resultatCapital = res.get_return();
   		
   		session.setAttribute("annuiteResC", annuiteValue);
   		session.setAttribute("dureeResC", dureeValue);
   		session.setAttribute("tauxResC", tauxValue);
   		session.setAttribute("capitalResC", resultatCapital);
	        
	   	 int numeroClient = ((Integer)session.getAttribute("numClient")).intValue();
	
		 credit.setAnnuiteCre(annuiteValue);
		 credit.setDureeCre(dureeValue);
		 credit.setCapitalCre(resultatCapital);
		 credit.setTauxCre(tauxValue);
	
		 credit.setDateCre(date);
		 credit.setNumClient(numeroClient);
			
		  SaveCreditDAO.saveDB(credit);
			
   		
   		response.sendRedirect("Capital.jsp");
  	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
