package rest;


import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import beans.ResultsBean;
import beans.TokensBean;

@WebServlet("app/api")
public class ApiResource extends HttpServlet {

    @Inject
    ResultsBean resultsBean;

    
    @Inject
    TokensBean tokensBean;

    private final Object lock = new Object();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized(lock){
            HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (request.getParameter("x")!="" && request.getParameter("y")!=""&& !request.getParameter("y").isEmpty() && !request.getParameter("x").isEmpty()  && tokensBean.find(request.getHeader("Authorization"))) {
            float x =Float.parseFloat(request.getParameter("x"));
            float y =Float.parseFloat(request.getParameter("y"));
            float r =Float.parseFloat(request.getParameter("r"));
            if (x<5 && x>-5 && y>=-5 && y<=3 && r>=-5 && r<3){
                resultsBean.add(x, y, r);
                System.out.println(resultsBean.getResults().size());
            }
        }        

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println((new Gson()).toJson(resultsBean.getResults()));
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        synchronized(lock){
        System.out.println(resultsBean.getResults().size());
        resultsBean.clear();
        System.out.println(resultsBean.getResults().size());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println((new Gson()).toJson(resultsBean.getResults()));
        }
    }

}
