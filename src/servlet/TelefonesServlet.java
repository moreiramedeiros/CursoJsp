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
import beans.BeansTelefone;
import dao.DaoTelefone;
import dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class TelefonesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoUsuario daoUsuario = new DaoUsuario();
	DaoTelefone daoTelefone = new DaoTelefone();

	public TelefonesServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String acao = request.getParameter("acao");

			if (acao != null) {

				if (acao.equalsIgnoreCase("addFone")) {

					// Obter id de um usuario da lista da página de Usuarios
					String id = request.getParameter("id");

					// Consultar dados do Usuario e salvar em uma sessão
					BeansCursoJsp usuario = daoUsuario.consultar(Long.parseLong(id));
					request.getSession().setAttribute("usuarioSession", usuario);

					// Atribuir
					request.setAttribute("usuario", usuario);

					// Redirecionar para a página telefones listando os telefones
					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
					view.forward(request, response);

				} else if (acao.equalsIgnoreCase("deleteFone")) {

					String foneId = request.getParameter("foneId");
					daoTelefone.deletar(Long.parseLong(foneId));

					// Recuperar dados do usuario salvos na sessão
					BeansCursoJsp beanCursoJsp = (BeansCursoJsp) request.getSession().getAttribute("usuarioSession");
					// Atribuir
					request.setAttribute("usuario", beanCursoJsp);

					// Redirecionar para a página telefones listando os telefones
					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefones", daoTelefone.listar(beanCursoJsp.getId()));
					view.forward(request, response);

				}

			} else {
				BeansCursoJsp usuario = (BeansCursoJsp) request.getSession().getAttribute("usuarioSession");
				
				if(usuario!=null) {
					// Atribuir
					request.setAttribute("usuario", usuario);
					// Redirecionar para a página telefones listando os telefones
					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
					request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
					view.forward(request, response);
				}else {
					// Redirecionar página e listar
					RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
					request.setAttribute("usuarios", daoUsuario.listar());
					view.forward(request, response);
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// Recuperar dados do usuario salvos na sessão
			BeansCursoJsp beanCursoJsp = (BeansCursoJsp) request.getSession().getAttribute("usuarioSession");

			// Obter parametros do formulario
			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");

			if (numero.isEmpty() || numero == null) {
				// Redireciona para a página de telefones listando telefones
				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", daoTelefone.listar(beanCursoJsp.getId()));
				request.setAttribute("msg", "O telefone não foi salvo, campo vazio!");
				request.setAttribute("usuario", beanCursoJsp);
				view.forward(request, response);
			} else {

				// Salvar telefone
				BeansTelefone telefone = new BeansTelefone();
				telefone.setNumero(numero);
				telefone.setTipo(tipo);
				telefone.setId_usuario(beanCursoJsp.getId());
				daoTelefone.salvar(telefone);

				// Redireciona para a página de telefones listando telefones
				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("telefones", daoTelefone.listar(beanCursoJsp.getId()));
				request.setAttribute("msg", "Salvo com sucesso!");
				request.setAttribute("usuario", beanCursoJsp);
				view.forward(request, response);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
