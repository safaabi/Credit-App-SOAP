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
import com.belaizi.soap.ws.WebServiceOperationsStub.Duree;
import com.belaizi.soap.ws.WebServiceOperationsStub.DureeResponse;

/**
 * Servlet implementation class DureeServlet
 */
public class DureeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DureeServlet() {
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
   		double capitalValue=Double.parseDouble(request.getParameter("inputCapital")) ;
   		double tauxValue=(Double.parseDouble(request.getParameter("inputTaux")))*0.01;
   		
   		Duree dur=new Duree();
   		dur.setAnnuite(annuiteValue);
   		dur.setCapital(capitalValue);
   		dur.setTaux(tauxValue);
    
   		DureeResponse res=wbstub.duree(dur);  		
   		int resultatDuree = res.get_return();
   		
   		session.setAttribute("annuiteResD", annuiteValue);
   		session.setAttribute("capitalResD", capitalValue);
   		session.setAttribute("tauxResD", tauxValue);
   		session.setAttribute("dureeResD", resultatDuree);
        
	   	 int numeroClient = ((Integer)session.getAttribute("numClient")).intValue();
	 	
		 credit.setAnnuiteCre(annuiteValue);
		 credit.setDureeCre(resultatDuree);
		 credit.setCapitalCre(capitalValue);
		 credit.setTauxCre(tauxValue);
	
		 credit.setDateCre(date);
		 credit.setNumClient(numeroClient);
			
		  SaveCreditDAO.saveDB(credit);
	   		
   		response.sendRedirect("Duree.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
