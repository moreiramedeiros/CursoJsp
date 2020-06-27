package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoUsuario;

@WebServlet("/servletPesquisa")
public class servletPesquisa extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public servletPesquisa() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			DaoUsuario daoUsuario = new DaoUsuario();
			String descricaoconsulta = request.getParameter("descricaoconsulta");			
			request.setAttribute("usuarios", daoUsuario.pesquisar(descricaoconsulta));
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			view.forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
