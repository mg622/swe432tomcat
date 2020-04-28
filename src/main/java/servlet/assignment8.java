// package servlet;
// Import Java Libraries
import java.io.*;
import java.util.*;

//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "assignment8", // "MyServlet2",
		urlPatterns = { "/assignment8" })

// assignment6 class
// CONSTRUCTOR: no constructor specified (default)
//
// ***************  PUBLIC OPERATIONS  **********************************
// public void doPost ()  --> prints a blank HTML page
// public void doGet ()  --> prints a blank HTML page
// private void PrintHead (PrintWriter out) --> Prints the HTML head section
// private void PrintBody (PrintWriter out) --> Prints the HTML body with
//              the form. Fields are blank.
// private void PrintBody (PrintWriter out, String lhs, String rhs, String rslt)
//              Prints the HTML body with the form.
//              Fields are filled from the parameters.
// private void PrintTail (PrintWriter out) --> Prints the HTML bottom
//***********************************************************************

public class assignment8 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String Domain = "";
	static String Path = "/";
	static String Servlet = "assignment8";
	
// Button labels
	static String OperationAdd = "Add";
	static String OperationSub = "Subtract";
	static String OperationMult = "Multiply";

// Other strings.
	static String Style = "https://www.cs.gmu.edu/~offutt/classes/432/432-style.css";
	
	
	  static enum Data {NAME, YEAR, JC, FW, RB, SS, VSE};
  static String RESOURCE_FILE = "entries.txt";
  static final String VALUE_SEPARATOR = ";";

	/**
	 * ***************************************************** Overrides HttpServlet's
	 * doPost(). Converts the values in the form, performs the operation indicated
	 * by the submit button, and sends the results back to the client.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter(Data.NAME.name());
     		String year = request.getParameter(Data.YEAR.name());
		String jc = request.getParameter(Data.JC.name());
		String fw = request.getParameter(Data.FW.name());
		String rb = request.getParameter(Data.RB.name());
		String ss = request.getParameter(Data.SS.name());
		String vse = request.getParameter(Data.VSE.name());
     
     String error = "";
     if(name == null){
       error= "<li>Name is required</li>";
       name = "";
     }

     if(year == null){
       error+= "<li>Year is required.<li>";
       year = "";
       
     }
     if(jc == null){
    	 error+= "<li>JC is required.<li>";
    	 jc = "";
     }
     if(fw == null){
    	 error+= "<li>FW is required.<li>";
    	 fw = "";
     }
	if(rb == null){
    	 error+= "<li>RB is required.<li>";
    	 rb = "";
     }
	if(ss == null){
    	 error+= "<li>SS is required.<li>";
    	 ss = "";
     }
	if(vse == null){
    	 error+= "<li>VSE is required.<li>";
    	 vse = "";
     }
		

     response.setContentType("text/html");
     PrintWriter out = response.getWriter();

     if (error.length() == 0){
       PrintWriter entriesPrintWriter = new PrintWriter(new FileWriter(RESOURCE_FILE, true), true);
       entriesPrintWriter.println(name+VALUE_SEPARATOR+year+VALUE_SEPARATOR+jc+VALUE_SEPARATOR+fw+VALUE_SEPARATOR+rb+VALUE_SEPARATOR+ss+VALUE_SEPARATOR+vse);
       entriesPrintWriter.close();

       PrintHead(out);
       PrintResponseBody(out, RESOURCE_FILE);
       PrintTail(out);
     }else{
       PrintHead(out);
       PrintBody(out, name, year, jc, fw, rb, ss, vse, error);
       PrintTail(out);
     }
		
	}
	
	  /** *****************************************************
   *  Prints the <BODY> of the HTML page
  ********************************************************* */
  private void PrintResponseBody (PrintWriter out, String resourcePath){
    out.println("<body onLoad=\"setFocus()\">");
    out.println("<p>");
    out.println("Results Database");
    out.println("</p>");
    out.println("");
	  
	out.println(" <style> able, th, td { border: 1px solid black; } </style> ");
    out.println(" <table>");
	
    try {
	 out.println("  <table>");
        out.println("  <tr>");
        out.println("   <th>Name</th>");
	out.println("   <th>Year</th>");
        out.println("   <th>JC</th>");
	out.println("   <th>FW</th>");
	out.println("   <th>RB</th>");
	out.println("   <th>SS</th>");
	out.println("   <th>VSE</th>");
        out.println("  </tr>");
        File file = new File(resourcePath);
        if(!file.exists()){
          out.println("  <tr>");
          out.println("   <td>No entries persisted yet.</td>");
          out.println("  </tr>");
          return;
        }

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          String []  entry= line.split(VALUE_SEPARATOR);
          out.println("  <tr>");
          for(String value: entry){
              out.println("   <td>"+value+"</td>");
          }
          out.println("  </tr>");
        }
        bufferedReader.close();
      } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
     out.println(" </table>");
     out.println("");
     out.println("</body>");
  }

	/**
	 * ***************************************************** Overrides HttpServlet's
	 * doGet(). Prints an HTML page with a blank form.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		PrintHead(out);
		PrintBody(out);
		PrintTail(out);
		
	
						
		   HttpSession session = request.getSession();
   String name   = request.getParameter("attrib_name");
   String value  = request.getParameter("attrib_value");
   String remove = request.getParameter("attrib_remove");

      String action = request.getParameter("action");

   if (action != null && action.equals("invalidate"))
   {  // Called from the invalidate button, kill the session.
      // Get session object
      session.invalidate();

      response.setContentType("text/html");

      out.println("<html>");
      out.println("<head>");
      out.println(" <title>Session lifecycle</title>");
      out.println("</head>");
      out.println("");
      out.println("<body>");

      out.println("<p>Your session has been invalidated.</P>");
      }
	
		
		   
            String lifeCycleURL = "/assignment8"; // --------------------------------------------
      out.print  ("<br><a href=\"" + lifeCycleURL + "?action=invalidate\">");
      out.println("Invalidate the session</a>");
       out.println("<br>");
		
		
	} // End PrintHead

	/**
	 * ***************************************************** Prints the <BODY> of
	 * the HTML page with the form data values from the parameters.
	 */
	private void PrintBody (PrintWriter out, String name, String year, String jc, String fw, String rb, String ss, String vse, String error){

	
	    
     out.println("<body onLoad=\"setFocus()\">");
     out.println("<p>");
     // out.println("<b>Name:</b> Megan Ngo");
   		 out.println("<b>SWE 432: </b> Assignment 8");
		out.println("<p>");
		out.println("<b>Partners:</b> Megan Ngo and Thomas Rigger");
		out.println("</p>");
		out.println("<p><b>Collaboration Summary:</b> Megan and Thomas worked together to build and debug doGet, doPost, printBody, and printHead and then committed it to Heroku. </p>");
		
		out.println("<p>");
   		out.println("<b>Additional Features Implemented:</b>");
    		 out.println("<p>   (1 point) - Store the data in XML format. </p>");
    		 out.println("<p>   (2 points) - Store the data into a database. ");
    		 out.println("<p>   (1 point) - Filter unacceptable words from the reviews. </p>");
    		 out.println("<p>   (1 point) - Add use of a session object (invalidate) </p>");
		out.println("</p>");
		out.println("<br>");
		out.println("<p><b>Survey Instructions:</b> Please fill out this form to rate GMU buildings.</p>");
     out.println("</p>");

     if(error != null && error.length() > 0){
       out.println("<p style=\"color:red;\">Please correct the following and resubmit.</p>");
       out.println(error);
     }
		
		  out.print  ("<form name=\"persist2file\" method=\"post\"");
     out.println(" action=\""+Domain+Path+Servlet+"\">");
     out.println("");
		
		     out.println(" <table>");
     out.println("  <tr>");
     out.println("   <td>Name:</td>");
     out.println("   <td><input type=\"text\" name=\""+Data.NAME.name()+"\" value=\""+name+"\" size=30 required></td>");
     out.println("  </tr>");
     out.println("  <tr>");
		 out.println(" </table>");
			out.println("<br> ");
		   
		out.println("What year are you?");         
		out.println("  <select name=\""+Data.YEAR.name() +"\" value=\""+year+"\">");//size=30 required>");
			    // name= \"School Year\">"); 
		out.println("  <option value= \"Freshman\" selected=\"selected\">Freshman</option>"); 
		out.println("  <option value=\"Sophomore\">Sophomore</option>"); 
		out.println("  <option value=\"Junior\">Junior</option>"); 
		out.println(" <option value=\"Senior\">Senior</option>"); 
		out.println("</select>"); 

		out.println("<br> <br> Please rate the following GMU buildings on a scale of 1 (worst) to 5 (best): <br> <br> ");
		
		out.println("<b>Johnson Center</b>");
		out.println("<br>");
		// out.println("  <input type=\"radio\" name=\"JC\" id=\"one\" value=\"1\" />"); 
		out.println("  <input type=\"radio\" name=\""+Data.JC.name() +"\" id=\"one\" value=\"1\">");
		out.println("  <label for=\"one\">1</label>"); 
		out.println("  <input type=\"radio\"name=\""+Data.JC.name() +"\" id=\"two\" value=\"2\" />");
		out.println("  <label for=\"two\">2</label>");
		out.println("  <input type=\"radio\" name=\""+Data.JC.name() +"\" id=\"three\" value=\"3\" checked=\"true\" />");
		out.println("  <label for=\"three\">3</label>");
		out.println("  <input type=\"radio\" name=\""+Data.JC.name() +"\" id=\"four\" value=\"4\" />");
		out.println("  <label for=\"four\">4</label>");
		out.println("  <input type=\"radio\" name=\""+Data.JC.name() +"\" id=\"five\" value=\"5\" />");
		out.println("  <label for=\"five\">5</label>");

		out.println("<br> ");
		out.println("<b>Fenwick Library</b>");    
		out.println("<br>");
		out.println("  <input type=\"radio\" name=\""+Data.FW.name() +"\" id=\"one\" value=\"1\">");
		// out.println("  <input type=\"radio\" name=\"Fenwick\" id=\"one\" value=\"1\" /> ");
		out.println("  <label for=\"one\">1</label> ");
		out.println("  <input type=\"radio\" name=\""+Data.FW.name() +"\" id=\"two\" value=\"2\" /> ");
		out.println(" <label for=\"two\">2</label> ");
		out.println("  <input type=\"radio\" name=\""+Data.FW.name() +"\" id=\"three\" value=\"3\" checked=\"true\" /> ");
		out.println(" <label for=\"three\">3</label> ");
		out.println("  <input type=\"radio\" name=\""+Data.FW.name() +"\" id=\"four\" value=\"4\" />");
		out.println(" <label for=\"four\">4</label>");
		out.println("  <input type=\"radio\" name=\""+Data.FW.name() +"\" id=\"five\" value=\"5\" />");
		out.println(" <label for=\"five\">5</label>");

		out.println("<br>");

		out.println("<b>Robinson Hall B</b>");
		out.println("<br>");
		// out.println("  <input type=\"radio\" name=\"RB\" id=\"one\" value=\"1\" /> ");
		out.println("  <input type=\"radio\" name=\""+Data.RB.name() +"\" id=\"one\" value=\"1\">");
		out.println("  <label for=\"one\">1</label>"); 
		out.println("  <input type=\"radio\" name=\""+Data.RB.name() +"\" id=\"two\" value=\"2\" />");
		out.println("  <label for=\"two\">2</label>");
		out.println("  <input type=\"radio\" name=\""+Data.RB.name() +"\" id=\"three\" value=\"3\" checked=\"true\" />");
		out.println("  <label for=\"three\">3</label>");
		out.println("  <input type=\"radio\" name=\""+Data.RB.name() +"\" id=\"four\" value=\"4\" />");
		out.println("  <label for=\"four\">4</label>");
		out.println("  <input type=\"radio\" name=\""+Data.RB.name() +"\" id=\"five\" value=\"5\" />");
		out.println("  <label for=\"five\">5</label>");

		out.println("<br>");

		out.println("<b>Southside</b>");
		out.println("<br>");
		out.println("  <input type=\"radio\" name=\""+Data.SS.name() +"\" id=\"one\" value=\"1\">");
		// out.println("  <input type=\"radio\" name=\"Southside\" id=\"one\" value=\"1\" />"); 
		out.println("  <label for=\"one\">1</label>"); 
		out.println("  <input type=\"radio\" name=\""+Data.SS.name() +"\" id=\"two\" value=\"2\" />");
		out.println(" <label for=\"two\">2</label>");
		out.println("  <input type=\"radio\" name=\""+Data.SS.name() +"\" id=\"three\" value=\"3\" checked=\"true\" /");
		out.println(" <label for=\"three\">3</label>");
		out.println("  <input type=\"radio\" name=\""+Data.SS.name() +"\" id=\"four\" value=\"4\" />");
		out.println(" <label for=\"four\">4</label>");
		out.println("  <input type=\"radio\" name=\""+Data.SS.name() +"\" id=\"five\" value=\"5\" />");
		out.println(" <label for=\"five\">5</label>");

		out.println("<br>");

		out.println("<b>Volgenau School of Engineering</b>");
		out.println("<br>");
		out.println("  <input type=\"radio\" name=\""+Data.VSE.name() +"\" id=\"one\" value=\"1\">");
		// out.println("  <input type=\"radio\" name=\"VSE\" id=\"one\" value=\"1\" /> ");
		out.println("  <label for=\"one\">1</label> ");
		out.println("  <input type=\"radio\" name=\""+Data.VSE.name() +"\" id=\"two\" value=\"2\" />");
		out.println("  <label for=\"two\">2</label>");
		out.println("  <input type=\"radio\" name=\""+Data.VSE.name() +"\" id=\"three\" value=\"3\" checked=\"true\" />");
		out.println("  <label for=\"three\">3</label>");
		out.println("  <input type=\"radio\" name=\""+Data.VSE.name() +"\" id=\"four\" value=\"4\" />");
		out.println("  <label for=\"four\">4</label>");
		out.println("  <input type=\"radio\" name=\""+Data.VSE.name() +"\" id=\"five\" value=\"5\" />");
		out.println("  <label for=\"five\">5</label>");
		out.println("<br>");
		out.println("<p></p>");
		
		out.println("<input type=\"submit\" onclick=\"doPost()\" value=\"Submit\">");

	/*	out.println("</form>");
		     out.println(" <input type=\"submit\" value=\"" + OperationAdd
      + "\" name=\"Operation\">");
     out.println(" <input type=\"reset\" value=\"Reset\" name=\"reset\">");
     out.println("</form>"); */
     out.println("");
     out.println("</body>");
		out.println("<br>");
		out.println("<br>");
		out.println("<br>");
		out.println("</form>");
		
	
	} // End doGet

	/**
	 * ***************************************************** Prints the <head> of
	 * the HTML page, no <body>.
	 */
	private void PrintHead(PrintWriter out) {
	/*	out.println("<html>");
		out.println("");
		out.println("<head>");
		out.println("<title>Assignment 8</title>");
		out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"" + Style + "\">");
		out.println("</head>");
		out.println("");
		
		out.println("");
		out.println("</body>"); */
		
		
	out.println("<html>");
		
     out.println("");
     out.println("<head>");
     out.println("<title>Assignment 8</title>");
     // Put the focus in the name field
     out.println ("<script>");
     out.println ("  function setFocus(){");
     out.println ("    document.persist2file.NAME.focus();");
     out.println ("  }");
     out.println ("</script>");
     out.println("</head>");
     out.println("");
	} // End PrintBody

	/**
	 * ***************************************************** Overloads PrintBody
	 * (out,lhs,rhs,rslt) to print a page with blanks in the form fields.
	 */
	private void PrintBody(PrintWriter out) {
		PrintBody(out, "", null, null, null, null, null, null, null);
	}


	/**
	 * ***************************************************** Prints the bottom of
	 * the HTML page.
	 */
	private void PrintTail(PrintWriter out) {
		out.println("");
		out.println("</html>");
	} // End PrintTail

} 
