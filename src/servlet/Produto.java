package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeansProduto;
import dao.DaoProduto;

/**
 * Servlet implementation class Produto
 */
@WebServlet("/salvarProduto")
public class Produto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DaoProduto daoProduto = new DaoProduto();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Produto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		String acao = request.getParameter("acao");
		String id = request.getParameter("id");

		try {
			
	    if(acao != null) {	
			if (acao.equalsIgnoreCase("listartodos")) {
				RequestDispatcher rd = request.getRequestDispatcher("cadastroProduto.jsp");	
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("categorias", daoProduto.listarCategoria());
				rd.forward(request, response);
			} else if (acao.equalsIgnoreCase("delete")) {
				daoProduto.deletar(Long.parseLong(id));
				RequestDispatcher rd = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("categorias", daoProduto.listarCategoria());
				rd.forward(request, response);
			} else if (acao.equalsIgnoreCase("editar")) {
				BeansProduto produto = daoProduto.consultar(Long.parseLong(id));
				RequestDispatcher rd = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("produto", produto);
				request.setAttribute("categorias", daoProduto.listarCategoria());
				rd.forward(request, response);
			}
	    }else {
			RequestDispatcher rd = request.getRequestDispatcher("cadastroProduto.jsp");
			request.setAttribute("produtos", daoProduto.listar());
			request.setAttribute("categorias", daoProduto.listarCategoria());
			rd.forward(request, response);
	    }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// acao=listartodos

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		String acao = request.getParameter("acao");
		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher rd = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("categorias", daoProduto.listarCategoria());
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			BeansProduto produto = new BeansProduto();
			try {
				String id = request.getParameter("id");
				String nome = request.getParameter("nome");
				String qtd = request.getParameter("qtd");
				String valor = request.getParameter("valor");
				String categoria_id = request.getParameter("categoria_id");
	
				
				//Consertando o valor vindo da máscara
				if(valor != null && !valor.isEmpty()) {
					String valorParse = valor.replace("R$ ","") ;  //R$ 1.444,44
					valorParse = valorParse.replaceAll("\\.","") ;  //1444,44
					valor = valorParse.replaceAll("\\,",".") ;  //1444.44
				}
				
				
				
				
				produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
				produto.setNome(nome);
				produto.setQtd(qtd.isEmpty() ? 0 : Long.parseLong(qtd));
				produto.setValor(valor.isEmpty() ? 0 : Double.parseDouble(valor));
				
				produto.setCategoria_id(Long.parseLong(categoria_id));

				if (nome.isEmpty() || nome == null) {
					request.setAttribute("msg", "O campo de nome está vazio!");
					request.setAttribute("produto", produto);
				} else {

					if (id.isEmpty() || id == null) {

						if (daoProduto.validarNome(nome)) {
							daoProduto.salvar(produto);
							request.setAttribute("msg2", "Produto salvo com sucesso!");
						} else {
							request.setAttribute("msg", "Já existe produto com o mesmo nome!");
							request.setAttribute("produto", produto);
						}

					} else {

						if (daoProduto.validarNomeUpdate(nome, id)) {
							daoProduto.atualizar(produto);
							request.setAttribute("msg2", "Produto atualizado com sucesso!");
						} else {
							request.setAttribute("msg", "Já existe produto com o mesmo nome!");
							request.setAttribute("produto", produto);
						}

					}
				}
				RequestDispatcher rd = request.getRequestDispatcher("cadastroProduto.jsp");
				request.setAttribute("produtos", daoProduto.listar());
				request.setAttribute("categorias", daoProduto.listarCategoria());
				rd.forward(request, response);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					RequestDispatcher rd = request.getRequestDispatcher("cadastroProduto.jsp");
					request.setAttribute("msg", "- A informação do campo quantidade e valor devem ser no formato númerico!<br>"+
										"- No campo valor use ponto (.) para separar casas decimais!");
					request.setAttribute("produto", produto);					
					request.setAttribute("produtos", daoProduto.listar());
					request.setAttribute("categorias", daoProduto.listarCategoria());
					rd.forward(request, response);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

			}
		}
	}

}
