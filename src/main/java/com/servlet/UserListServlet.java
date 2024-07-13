package com.servlet;



import java.io.IOException;

import java.sql.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DAO.UserDAO;
import com.entity.User;
@WebServlet("/")
public class UserListServlet extends HttpServlet
{

	private UserDAO userdao;

	public void init() {

		userdao = new UserDAO();
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException
	{
		doGet(request,response);
	}
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException
	{
		String action = request.getServletPath();
		System.out.println(action);

		try {

			switch(action)
			{
			case "/new":
				showNewForm(request,response);
				break;

			case "/insert":
				insertUser(request,response);
				break;

			case "/update":
				updateUser(request,response);
				break;

			case "/edit":
				showEditForm(request,response);
				break;

			case "/delete":
				deleteUser(request,response);
				break;


			default:
				listUser(request,response);
				break;
			}

		}
		catch(Exception e) {
			System.out.println(e+"error");
		}

	}





	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

			System.out.println("show newForm");
			
			RequestDispatcher dispatcher=request.getRequestDispatcher("form.jsp");
			
			dispatcher.forward(request, response);

		
	}
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IOException {

		System.out.println("update profile");
	
		int id = Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("username");
		String mail=request.getParameter("mail");
		int age=Integer.parseInt(request.getParameter("age"));
		String city=request.getParameter("city");

		User user=new User();
		user.setAge(age);
		user.setCity(city);
		user.setMail(mail);
		user.setUserName(name);
		user.setId(id);
		
		userdao.updateUser(user);
		
		response.sendRedirect("list");

	}


	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, ServletException, IOException {

		System.out.println("edit");
		int id = Integer.parseInt(request.getParameter("id"));
		
		User u = userdao.getUser(id);
		
		request.setAttribute("user", u);
		
		RequestDispatcher dispatch=request.getRequestDispatcher("form.jsp");
		dispatch.forward(request, response);
	}



	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException {


		System.out.println("delete");

		int id = Integer.parseInt(request.getParameter("id"));

		userdao.deleteUser(id);
		

		response.sendRedirect("list");

	}
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, IOException {

		System.out.println("insert profile");

		String name=request.getParameter("username");
		String mail=request.getParameter("mail");
		int age=Integer.parseInt(request.getParameter("age"));
		String city=request.getParameter("city");

		User user=new User();
		
		user.setAge(age);
		user.setCity(city);
		user.setMail(mail);
		user.setUserName(name);

		userdao.createUser(user);
		
		
		sendmail(mail);
		

		response.sendRedirect("list");

	}

	private void listUser(HttpServletRequest request,HttpServletResponse response) throws ClassNotFoundException, ServletException, IOException {
		List<User> userList=userdao.listUser();

		request.setAttribute("users", userList);
		RequestDispatcher dispatch=request.getRequestDispatcher("list.jsp");
		dispatch.forward(request, response);


	}
	
	
	public void sendmail (String tomailId) {
		
		final String user="ajai2458@gmail.com";//change accordingly 
		  
		  final String password="mcurlptajissryxf";
		  
		  String to = tomailId.trim();//change accordingly  
		  
		  System.out.println("111222"); 
		   //Get the session object  
		  
		  Properties properties = new Properties();
	        properties.put("mail.smtp.host", "smtp.gmail.com");
	        properties.put("mail.smtp.port", "587");
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
	        properties.setProperty("mail.smtp.ssl.ciphers", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");

	        // creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(user, password);
	            }
	        };
	 
	        Session session = Session.getInstance(properties, auth);
	 
	        // creates a new e-mail message
	      
	        try{
	        	Message msg = new MimeMessage(session);
	        
	 
	        msg.setFrom(new InternetAddress(user));
	        InternetAddress[] toAddresses = { new InternetAddress(to) };
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        msg.setSubject("JavaMail API ");
	        msg.setSentDate(new Date(0));
	        msg.setText("you are successfully registered ");
	        System.setProperty("https.protocols", "TLSv1.2");

	        
	        System.setProperty("https.cipherSuites", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");

	        // sends the e-mail
	        Transport.send(msg);
      	System.out.println("Sent done");

	        }catch(Exception e) {
	        	System.out.println("Exception"+e.getMessage());
	        	System.out.println(e);
	        }
		 } 

	}






