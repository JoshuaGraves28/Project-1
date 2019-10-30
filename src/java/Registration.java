/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.logging.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
/**
 *
 * @author JSU
 */
@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {

   
  

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Database db = new Database();
        
        try {
            out.println( db.getResultsAsTable( Integer.parseInt( request.getParameter("session") ) ) );
        }
        catch( Exception e){}
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Database db = new Database();
        
        try {
            out.println( db.uploadUserDataToDatabase( request.getParameter("firstname"),request.getParameter("lastname"), request.getParameter("displayname"),Integer.parseInt( request.getParameter("session") ) ) ) ;
        }
        catch( Exception e){}
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
