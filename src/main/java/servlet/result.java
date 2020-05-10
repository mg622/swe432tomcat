package servlet;

import com.google.gson.Gson;
import java.io.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "result", urlPatterns = {"/result"})
public class result extends HttpServlet{
  @Override
   protected void doPost  (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException{

     
     

     res.setContentType ("aplication/json");
     res.setHeader("Access-Control-Allow-Origin", "*");
     res.setHeader("Access-Control-Allow-Methods", "POST");
     res.setHeader("Access-Control-Allow-Headers", "*");

     PrintWriter out = res.getWriter();

     Map<String, String> data = new HashMap<String, String>();

     Map<String, String[]> parameterMap = req.getParameterMap();
     for (String key: parameterMap.keySet()) {
         
         if(key.equals("loc")){ //input validation
            String parameter = parameterMap.get(key)[0].toLowerCase();
            if(parameter.equals("null")){parameter = "Please input a location.";}
            else if(parameter.contains("fuck") || parameter.contains("shit") || parameter.contains("bitch") || parameter.contains("ass")){
               parameter = "****";
            }
            data.put(key, parameter);
         }
         else{
            String parameter = parameterMap.get(key)[0];
            if(parameter.equals("null")){parameter = "Please Select an option.";}
            data.put(key, parameter);
         }
         //data.put(key, parameter);
     }


     out.print(new Gson().toJson(data));
     out.flush();
     out.close();
    }

    @Override
     protected void doGet  (HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
     {

          res.setContentType ("text/html");
          PrintWriter out = res.getWriter ();

          out.println ("<HTML>");
          out.println ("<HEAD>");
          out.println ("<TITLE>Invalid request</TITLE>");
          out.println ("</HEAD>");

          out.println ("<BODY>");
          out.println ("<CENTER>");
          out.println (
           "<P>Invalid GET request: This service only accepts POST requests</P>"
          );
          out.println ("</CENTER>");
          out.println ("</BODY>");

          out.println ("</HTML>");
          out.flush();

          out.close ();

      }
}
