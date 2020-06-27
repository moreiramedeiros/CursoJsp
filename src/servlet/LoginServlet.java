package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeansCursoJsp;
import dao.DaoLogin;

@WebServlet("/LoginServlet") // Mapeamento nome que vai como atributo de action="LoginServlet"

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoLogin daoLogin = new DaoLogin();

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
		// Obter informações do formulário JSP via id
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
				
		
	// Verificar login

		
          if(login != null && !login.isEmpty() && senha != null && !senha.isEmpty()){
			
			if (daoLogin.validarLogin(login, senha)) {
				// Abrir página "acessoliberado.jsp"
				RequestDispatcher dispatcher = request.getRequestDispatcher("acessoliberado.jsp");
				dispatcher.forward(request, response);
			} else {
				// Abrir página "acessonegado.jsp"
				RequestDispatcher dispatcher = request.getRequestDispatcher("acessonegado.jsp");
				dispatcher.forward(request, response);
			}
          }else {
        	  RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			  dispatcher.forward(request, response);
          }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
