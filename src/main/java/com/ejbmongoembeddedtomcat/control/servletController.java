/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejbmongoembeddedtomcat.control;

import com.ejbmongoembeddedtomcat.entity.Customer;
import com.ejbmongoembeddedtomcat.service.MongoService;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author saddamtbg
 */
@WebServlet(name = "servletController", urlPatterns = {"/addCustomer", "/deleteCustomer", "/editCustomer"})
public class servletController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    String userPath;

    // <editor-fold defaultstate="collapsed" desc="mail:saddamtbg@gmail.com">
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

        userPath = request.getServletPath();

        String id = request.getParameter("id");

        if (userPath.equals("/deleteCustomer")) {

            if (id == null || "".equals(id)) {
                throw new ServletException("id missing for delete operation");
            }

            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoService service = new MongoService(mongo);

            Customer cus = new Customer();
            cus.setId(id);
            service.deleteCustomer(cus);
            System.out.println("Person deleted successfully with id=" + id);
            request.setAttribute("success", "Person deleted successfully");
            List<Customer> customers = service.readAllCustomer();
            request.setAttribute("customers", customers);

            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/persons.jsp");
            rd.forward(request, response);
        }

        
        if (userPath.equals("/editCustomer")) {

            if (id == null || "".equals(id)) {
                throw new ServletException("id missing for edit operation");
            }
            
            System.out.println("Person edit requested with id=" + id);
            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoService service = new MongoService(mongo);
            Customer cus = new Customer();

            cus.setId(id);
            cus = service.readCustomer(cus);
            request.setAttribute("customer", cus);
            List<Customer> customers = service.readAllCustomer();
            request.setAttribute("customers", customers);

            RequestDispatcher rd = getServletContext().getRequestDispatcher(
                    "/persons.jsp");
            rd.forward(request, response);
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

        userPath = request.getServletPath();

        if (userPath.equals("/addCustomer")) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String kota = request.getParameter("kota");

            if (name == null || address == null || name.equals("") || address.equals("")) {

                request.setAttribute("error", "mandatory parameters missing");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/persons.jsp");
                rd.forward(request, response);

            } else {
                Customer c = new Customer();
                c.setId(id);
                c.setName(name);
                c.setAddress(address);
                c.setKota(kota);

                MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
                MongoService service = new MongoService(mongo);
                service.createCustomer(c);

                System.out.println("Person Added Successfully with id=" + c.getId());
                request.setAttribute("success", "Person Added Successfully");
                List<Customer> customers = service.readAllCustomer();
                request.setAttribute("customers", customers);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/persons.jsp");
                rd.forward(request, response);
            }

        }

        if (userPath.equals("/editCustomer")) {

            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String kota = request.getParameter("kota");

            if (id == null || "".equals(id)) {
                throw new ServletException("id missing for edit operation");
            }

            if ((name == null || name.equals("")) || (address == null || address.equals(""))) {

                request.setAttribute("error", "Name and Addres can't empty");
                MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
                MongoService service = new MongoService(mongo);
                Customer cus = new Customer();
                cus.setId(id);
                cus.setName(name);
                cus.setAddress(address);
                cus.setKota(kota);
                request.setAttribute("customer", cus);

                List<Customer> customers = service.readAllCustomer();
                request.setAttribute("customers", customers);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/persons.jsp");
                rd.forward(request, response);

            } else {

                MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
                MongoService service = new MongoService(mongo);
                Customer cus = new Customer();
                cus.setId(id);
                cus.setName(name);
                cus.setAddress(address);
                cus.setKota(kota);
                service.updateCustomer(cus);

                System.out.println("Person edited successfully with id=" + id);
                request.setAttribute("success", "Person edited successfully");
                List<Customer> customers = service.readAllCustomer();
                request.setAttribute("customers", customers);

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/persons.jsp");
                rd.forward(request, response);
            }
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
