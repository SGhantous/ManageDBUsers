/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author 339250
 */
@WebServlet(name="Validate", urlPatterns={"/Validate"})
public class Validate extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NamingException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
        {
           HttpSession session=request.getSession();
           
           
           String username = request.getParameter("username");
           String password = request.getParameter("password");
           String logout = request.getParameter("logout");
           
           if(logout != null)
           {
               session.invalidate();
               response.sendRedirect("index.jsp?message=Logged Out");
               return;
           }
           
           if(username == null || password == null || username.equals("") || password.equals(""))
           {
               response.sendRedirect("index.jsp?message=Both Values Are Required!");
           }
           Class.forName("com.mysql.jdbc.Driver").newInstance();
           InitialContext ic = new InitialContext();
           DataSource ds = (DataSource) ic.lookup("jdbc/Assignment3Pool");
           Connection conn=ds.getConnection();
           
           CallableStatement cstmt = conn.prepareCall("{call validateUser(?,?)}");
           cstmt.setString(1, username);
           cstmt.setString(2, password);
           ResultSet rs = cstmt.executeQuery();
           while(rs.next())
           {
                if(rs.getBoolean(1) == false)
                {
                    response.sendRedirect("index.jsp?message=Invalid Username or Password");
                }
                else
                {
                    session.setAttribute("username", username);
                    cstmt = conn.prepareCall("{call isAdmin(?)}");
                    cstmt.setString(1, username);
                    rs = cstmt.executeQuery();
                    while(rs.next())
                    {
                        if(rs.getString(1).equals("0"))
                        {
                            response.sendRedirect("main.jsp");
                        }
                        else
                        {
                            response.sendRedirect("admin.jsp");
                        }
                    }
                }
           }
           rs.close();
           cstmt.close();
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NamingException | SQLException ex) {
            Logger.getLogger(Validate.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NamingException | SQLException ex) {
            Logger.getLogger(Validate.class.getName()).log(Level.SEVERE, null, ex);
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
