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

@WebServlet(name = "finalExam", urlPatterns = { "/finalExam" })

public class finalExam extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static String Domain = "";
	static String Path = "/";
	static String Servlet = "finalExam";
	
	static String OperationAdd = "Add";
	static String OperationSub = "Subtract";
	static String OperationMult = "Multiply";

	static String Style = "https://www.cs.gmu.edu/~offutt/classes/432/432-style.css";
	
	static enum Data {P1};
  	static String RESOURCE_FILE = "entries.txt";
  	static final String VALUE_SEPARATOR = ";";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String part1=request.getParameter(Data.P1.name());
		//String op=request.getParameter(Data.OP.name());
		//String part2=request.getParameter(Data.P2.name());
		
		//String noise = request.getParameter(Data.NOISE.name());
		//String crowd = request.getParameter(Data.CROWD.name());
		//String comfort = request.getParameter(Data.COMFORT.name());
     
     String error="";
     if(part1==null){
       error= "<li>Predicate is required</li>";
       part1="";
     }
     

     /*if(noise==null){
    	 error+= "<li>Rating for noise level is required.<li>";
    	 noise="";
     }
     if(crowd == null){
    	 error+= "<li>Rating for crowdedness is required.<li>";
    	 crowd="";
     }
	if(comfort==null){
    	 error+= "<li>Rating for comfort is required.<li>";
    	 comfort="";
     }*/
		

     response.setContentType("text/html");
     PrintWriter out = response.getWriter();

     if (error.length() == 0){
       PrintWriter entriesPrintWriter = new PrintWriter(new FileWriter(RESOURCE_FILE, true), true);
       entriesPrintWriter.println(part1);
       entriesPrintWriter.close();

       PrintHead(out);
       PrintResponseBody(out, RESOURCE_FILE);
       PrintTail(out);
     }else{
       PrintHead(out);
       PrintBody(out, part1, error);
       PrintTail(out);
     }
		
	}

	
static String censor(String text, String word)  
{ 
    String[] word_list = text.split("\\s+"); 
  
    String result = ""; 
  
    String stars = ""; 
    for (int i = 0; i < word.length(); i++) 
        stars += '*'; 
   
    int index = 0; 
    for (String i : word_list)  
    { 
        if (i.compareTo(word) == 0)  
            word_list[index] = stars; 
        index++; 
    } 
    for (String i : word_list) 
        result += i + ' '; 
  
    return result; 
} 

	

  private void PrintResponseBody (PrintWriter out, String resourcePath){
    out.println("<body onLoad=\"setFocus()\">");
    out.println("<p>");
    out.println("Results");
    out.println("</p>");
    out.println("");
	  
	out.println(" <style> able, th, td { border: 1px solid black; } </style> ");
    	out.println(" <table>");
	
	try {
		out.println("  <table>");
        	out.println("  <tr>");
        	//out.println("   <th></th>");
        	//out.println("   <th>Noise</th>");
			//out.println("   <th>Crowd</th>");
			//out.println("   <th>Comfort</th>");
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
		//filter out curse words
		String result=censor(value, "fuck");
		result=censor(result, "Fuck");
		result=censor(result, "Shit");
		result=censor(result, "shit");
		  result=censor(result, "ass");
		  result=censor(result, "Ass");
		  result=censor(result, "bitch");
		  result=censor(result, "Bitch");
		  result=censor(result, "hell");
		  result=censor(result, "Hell");
		  
		  	
		  
		  	if (result.contains("and") || result.contains("And") || result.contains("AND") || result.contains("&")) 
		  	{
		  		//char ch1=result.charAt(0);
		  		//char ch2=result.charAt(result.length() - 1);
		  	
		  		out.println(result.substring(0, 1));
		  		out.println(result.substring(result.length() - 1);
		  		out.println("<br>");
		  		out.println(result);
		  		out.println("<br>0 0 &nbsp;&nbsp; 0<br>");
		  		out.println("<br>0 1 &nbsp;&nbsp; 0<br>");
		  		out.println("<br>1 0 &nbsp;&nbsp; 0<br>");
		  		out.println("<br>1 1 &nbsp;&nbsp; 1<br>");
		  	}
		  	
		  	if (result.contains("xor") || result.contains("Xor") || result.contains("XOR") || result.contains("|")) 
		  	{
		  		out.println("Or:");
		  		out.println("<br>0 0 &nbsp;&nbsp; 0<br>");
		  		out.println("<br>0 1 &nbsp;&nbsp; 1<br>");
		  		out.println("<br>1 0 &nbsp;&nbsp; 1<br>");
		  		out.println("<br>1 1 &nbsp;&nbsp; 1<br>");

		  	}
		  	
		  	else if (result.contains("or") || result.contains("Or") || result.contains("OR") || result.contains("|")) 
		  	{
		  		out.println("Or:");
		  		out.println("<br>0 0 &nbsp;&nbsp; 0<br>");
		  		out.println("<br>0 1 &nbsp;&nbsp; 1<br>");
		  		out.println("<br>1 0 &nbsp;&nbsp; 1<br>");
		  		out.println("<br>1 1 &nbsp;&nbsp; 0<br>");
		  	}
		  	
		  
		  
		  //out.println("   <td>"+result+"</td>");
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

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		PrintHead(out);
		PrintBody(out);
		PrintTail(out);
						
		HttpSession session = request.getSession();
   		String name=request.getParameter("attrib_name");
   		String value=request.getParameter("attrib_value");
   		String remove=request.getParameter("attrib_remove");
      	String action=request.getParameter("action");

   		if (action != null && action.equals("invalidate"))
   		{  
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
		   
        String lifeCycleURL = "/finalExam"; 
      	out.print  ("<br><a href=\"" + lifeCycleURL + "?action=invalidate\">");
	out.println("<center>");
      	out.println("Invalidate the session</a>");
	out.println("</center>");
       	out.println("<br>");
	} 

	
	private void PrintBody (PrintWriter out, String part1, String error){
	    
		out.println("<body onLoad=\"setFocus()\">");
     		out.println("<p>");
   		out.println("<b>Final Exam</b>");
		out.println("<p>");
		out.println("<b>Mei Gibbons</b>");
		out.println("</p>");		
		out.println("<p>");
   		out.println("<b>OREs:</b>");
    		out.println("<p>Input validation </p>");
    		out.println("<p>Exclusive or ");
    		out.println("<p>Multiple syntaxes in the input box for logical operators</p>");
    		out.println("<p>Add use of a session object.</p>");
		out.println("</p>");
		out.println("<br>");
		out.println("<p><b><center>Final Exam</center></b> </p>");
		out.println("<p><center></center></p>");
     		out.println("</p>");

     		if(error != null && error.length() > 0){
			out.println("<p style=\"color:red;\">Please correct the following and resubmit.</p>");
       			out.println(error);
     		}
		
		out.print  ("<form name=\"persist2file\" method=\"post\"");
     		out.println(" action=\""+Domain+Path+Servlet+"\">");
     		out.println("");
		
		out.println("<center>");
		out.println(" <table>");
     		out.println("  <tr>");
     		out.println("   <td>Predicate (Ex: A and B, A && B, etc.):</td>");
     		out.println("   <td><input type=\"text\" name=\""+Data.P1.name()+"\" value=\""+part1+"\" size=30 required></td>");
     		out.println("  </tr>");
     		out.println("  <tr>");
     		
     		/*out.println("  <tr>");
     		out.println("   <td>Operator (and, or, xor):</td>");
     		out.println("   <td><input type=\"text\" name=\""+Data.OP.name()+"\" value=\""+op+"\" size=30 required></td>");
     		out.println("  </tr>");
     		out.println("  <tr>");
     		
     		out.println("  <tr>");
     		out.println("   <td>2nd Variable:</td>");
     		out.println("   <td><input type=\"text\" name=\""+Data.P2.name()+"\" value=\""+part2+"\" size=30 required></td>");
     		out.println("  </tr>");
     		out.println("  <tr>");*/
		out.println(" </table>");
		out.println("</center>");
		out.println("<br>");
		
		/*out.println("<center>");
		out.println("<b>Rating for Noise Level:</b> (1-very loud, 5-silent)");
		out.println("<br>");
		out.println("  <input type=\"radio\" name=\""+Data.NOISE.name() +"\" id=\"one\" value=\"1\">");
		out.println("  <label for=\"one\">1</label>"); 
		out.println("  <input type=\"radio\"name=\""+Data.NOISE.name() +"\" id=\"two\" value=\"2\" />");
		out.println("  <label for=\"two\">2</label>");
		out.println("  <input type=\"radio\" name=\""+Data.NOISE.name() +"\" id=\"three\" value=\"3\" checked=\"true\" />");
		out.println("  <label for=\"three\">3</label>");
		out.println("  <input type=\"radio\" name=\""+Data.NOISE.name() +"\" id=\"four\" value=\"4\" />");
		out.println("  <label for=\"four\">4</label>");
		out.println("  <input type=\"radio\" name=\""+Data.NOISE.name() +"\" id=\"five\" value=\"5\" />");
		out.println("  <label for=\"five\">5</label>");
		out.println("<p></p>");

		out.println("<br> ");
		out.println("<b>Rating for Crowdedness:</b> (1-way too crowded, 5-hardly any people)");    
		out.println("<br>");
		out.println("  <input type=\"radio\" name=\""+Data.CROWD.name() +"\" id=\"one\" value=\"1\">");
		out.println("  <label for=\"one\">1</label> ");
		out.println("  <input type=\"radio\" name=\""+Data.CROWD.name() +"\" id=\"two\" value=\"2\" /> ");
		out.println(" <label for=\"two\">2</label> ");
		out.println("  <input type=\"radio\" name=\""+Data.CROWD.name() +"\" id=\"three\" value=\"3\" checked=\"true\" /> ");
		out.println(" <label for=\"three\">3</label> ");
		out.println("  <input type=\"radio\" name=\""+Data.CROWD.name() +"\" id=\"four\" value=\"4\" />");
		out.println(" <label for=\"four\">4</label>");
		out.println("  <input type=\"radio\" name=\""+Data.CROWD.name() +"\" id=\"five\" value=\"5\" />");
		out.println(" <label for=\"five\">5</label>");
		out.println("<p></p>");

		out.println("<br>");
		out.println("<b>Rating for Comfort of Seats:</b> (1-uncomfortable, 5-very comfortable)");
		out.println("<br>");
		out.println("  <input type=\"radio\" name=\""+Data.COMFORT.name() +"\" id=\"one\" value=\"1\">");
		out.println("  <label for=\"one\">1</label>"); 
		out.println("  <input type=\"radio\" name=\""+Data.COMFORT.name() +"\" id=\"two\" value=\"2\" />");
		out.println("  <label for=\"two\">2</label>");
		out.println("  <input type=\"radio\" name=\""+Data.COMFORT.name() +"\" id=\"three\" value=\"3\" checked=\"true\" />");
		out.println("  <label for=\"three\">3</label>");
		out.println("  <input type=\"radio\" name=\""+Data.COMFORT.name() +"\" id=\"four\" value=\"4\" />");
		out.println("  <label for=\"four\">4</label>");
		out.println("  <input type=\"radio\" name=\""+Data.COMFORT.name() +"\" id=\"five\" value=\"5\" />");
		out.println("  <label for=\"five\">5</label>");
		out.println("<p></p>");*/
		
		out.println("<center>");
		out.println("<input type=\"submit\" onclick=\"doPost()\" value=\"Submit\">");

     		out.println("");
     		out.println("</body>");
		out.println("<br>");
		out.println("</form>");
		
		out.println("</center>");
	} 

	
	private void PrintHead(PrintWriter out) {
		out.println("<html>");
     		out.println("");
     		out.println("<head>");
     		out.println("<title>Final Exam</title>");
     		out.println ("<script>");
     		out.println ("  function setFocus(){");
     		out.println ("    document.persist2file.NAME.focus();");
     		out.println ("  }");
     		out.println ("</script>");
     		out.println("</head>");
     		out.println("");
	} 
	
	
	private void PrintBody(PrintWriter out) {
		PrintBody(out, "", null);
	}

	
	private void PrintTail(PrintWriter out) {
		out.println("");
		out.println("</html>");
	} 
} 
