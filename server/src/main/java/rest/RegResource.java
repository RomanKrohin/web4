package rest;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import beans.AuthBean;
import beans.TokensBean;
import utils.User;


@WebServlet("app/reg")
public class RegResource extends HttpServlet {

    @Inject
    AuthBean authBean;

    @Inject
    TokensBean tokensBean;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                ObjectMapper objectMapper= new ObjectMapper();
                response.setStatus(HttpServletResponse.SC_OK);
                String res =authBean.reg(request.getParameter("login"), request.getParameter("password"));
                if (res == "reg"){
                    String token = UUID.randomUUID().toString();
                    tokensBean.add(token);
                    response.getWriter().println("{\"message\": \""+token +"\"}");
                }
                else{
                    response.setStatus(401);
                    response.getWriter().println(res);
                }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                ObjectMapper objectMapper= new ObjectMapper();
                response.setStatus(HttpServletResponse.SC_OK);
                String res =authBean.reg(request.getParameter("login"), request.getParameter("password"));
                if (res == "reg"){
                    String token = UUID.randomUUID().toString();
                    tokensBean.add(token);
                    response.getWriter().println("{\"message\": \""+token +"\"}");
                }
                else{
                    response.setStatus(401);
                    response.getWriter().println(res);
                }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        tokensBean.delete(request.getHeader("Authorization"));
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{\"message\": \"logout\"}");
    }

}