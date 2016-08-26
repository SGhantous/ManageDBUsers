/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userservices;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author 339250
 */
@WebServlet(name = "UserServices", urlPatterns = {"/UserServices"})
public class UserServices extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, InstantiationException, NamingException, IllegalAccessException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
            String adminChange = request.getParameter("adminChange");
            String deleteUser = request.getParameter("deleteUser");
            String passwordChange = request.getParameter("passwordChange");
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("jdbc/Assignment3Pool");
            Connection conn=ds.getConnection();
            
            if(passwordChange != null)
            {
                CallableStatement cstmt = conn.prepareCall("{call resetPassword(?)}");
                cstmt.setString(1, passwordChange);
                ResultSet rs = cstmt.executeQuery();
                rs.close();
                cstmt.close();
                response.sendRedirect("admin.jsp");
            }
            
            if(adminChange != null)
            {
                CallableStatement cstmt = conn.prepareCall("{call toggleAdminStatus(?)}");
                cstmt.setString(1, adminChange);
                ResultSet rs = cstmt.executeQuery();
                rs.close();
                cstmt.close();
                response.sendRedirect("admin.jsp");
            }
            
            if(deleteUser != null)
            {
                CallableStatement cstmt = conn.prepareCall("{call deleteUser(?)}");
                cstmt.setString(1, deleteUser);
                ResultSet rs = cstmt.executeQuery();
                rs.close();
                cstmt.close();
                response.sendRedirect("admin.jsp");
            }
            
            
            conn.close();
            
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | InstantiationException | NamingException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | InstantiationException | NamingException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(UserServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
