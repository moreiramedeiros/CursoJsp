package servlet;

import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeansCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario") // Mapeamento nome que vai como atributo de action="SalvarUsuario"
@MultipartConfig
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();
	}
    
	
	/* Ação de link */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		// doPost(request, response);

		try {
			String acao = request.getParameter("acao");
			String id = request.getParameter("id");

		 if(acao != null) {
			if (acao.equalsIgnoreCase("delete")) {

				daoUsuario.deletar(Long.parseLong(id));
				// Redirecionar página e listar
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("editar")) {

				BeansCursoJsp beanCursoJsp = daoUsuario.consultar(Long.parseLong(id));

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("listartodos")) {

				// Redirecionar página e listar
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao.equalsIgnoreCase("download")) {
				BeansCursoJsp usuario = daoUsuario.consultar(Long.parseLong(id));
				if (usuario != null) {

					String contentType = "";
					byte[] filebytes = null;

					String tipo = request.getParameter("tipo");

					// Recupera a base64 (converte em bytes) e contentType de imagem ou curriculo
					if (tipo.equals("imagem")) {
						contentType = usuario.getContentType();
						filebytes = new Base64().decodeBase64(usuario.getFotoBase64());
					} else if (tipo.equals("curriculo")) {
						contentType = usuario.getCurriculoContentType();
						filebytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
						
					}
					
					
					
					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					// Coloca bytes em um objeto para processar
					InputStream is = new ByteArrayInputStream(filebytes);

					// Inicio da resposta ao navegador
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}

					os.flush();
					os.close();
					
					
					
				}
			}
			
		} else {
			// Redirecionar página e listar
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			request.setAttribute("usuarios", daoUsuario.listar());
			view.forward(request, response);
		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	
	/* Ação do fornulário */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			// Redirecionar página e listar
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				String id = request.getParameter("id");
				String login = request.getParameter("login");
				String senha = request.getParameter("senha");
				String nome = request.getParameter("nome");
				String telefone = request.getParameter("telefone");
				String cep = request.getParameter("cep");
				String rua = request.getParameter("rua");
				String bairro = request.getParameter("bairro");
				String cidade = request.getParameter("cidade");
				String estado = request.getParameter("estado");
				String ibge = request.getParameter("ibge");
				String ativo = request.getParameter("ativo"); //  on  |  null
				String sexo = request.getParameter("sexo");  
				String perfil = request.getParameter("perfil");  
				 
				
				BeansCursoJsp usuario = new BeansCursoJsp();
				usuario.setSenha(senha);
				usuario.setLogin(login);
				usuario.setNome(nome);
				usuario.setTelefone(telefone);
				usuario.setCep(cep);
				usuario.setRua(rua);
				usuario.setBairro(bairro);
				usuario.setCidade(cidade);
				usuario.setEstado(estado);
				usuario.setIbge(ibge);
				
				if(ativo != null) {
					usuario.setAtivo(true);
				}else {
					usuario.setAtivo(false);
				}

				if(sexo != null) {
					if(sexo.equalsIgnoreCase("masculino")) {
						usuario.setSexo(sexo);
					}else if(sexo.equalsIgnoreCase("feminino")) {
						usuario.setSexo(sexo);
					} 
				} else {
					usuario.setSexo("indefinido");
				}
				
				usuario.setPerfil(perfil);
				
				usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);

				if (ServletFileUpload.isMultipartContent(request)) {
					 
					/* Recupera imagem do formulário*/
					Part imagemFoto = request.getPart("foto");
			                  
					/*Verifica se existe imagem selecionado no formulário*/
			  		if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
									
						
			  			
			  			/* Inicio Imagem original */
			  			
			  			// Converte a entrada de fluxo de dados para bytes 
		            	byte[] bytesImagem = convertStreamParaByte(imagemFoto.getInputStream());
									
						// Converte o arquivo byte para base64
						String fotobase64 = new Base64().encodeBase64String(bytesImagem);

						// Salva arquivo na base 64
						usuario.setFotoBase64(fotobase64);
									
						// Salva o tipo de arquivo
						usuario.setContentType(imagemFoto.getContentType());	
						
						/* Fim Imagem original */
						
						
						
						
						/*Inicio da miniatura*/

					
						//Transforma em bufferImagem
						byte[] imageByteDecode = new Base64().decodeBase64(fotobase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
						
						//Pega o tipo de imagem
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

						//Cria imagem em miniatura redefinindo largura, altura e tipo
						BufferedImage resizeImage = new BufferedImage(100,100,type);
						Graphics2D g = resizeImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();
						
						//Escrever a imagem novamente
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizeImage, "png", baos);
						
						String miniaturaBase64 = "data:image/png;base64,"+ DatatypeConverter.printBase64Binary(baos.toByteArray());
						
						//Adicionar ao objeto
						usuario.setFotoBase64Miniatura(miniaturaBase64);
						
						/*Fim da miniatura*/
					
				} else {
						// Caso não exista arquivo selecionado no formulário, recuperar do banco de dados 
						 /*	BeansCursoJsp usuarioVelho = daoUsuario.consultar(Long.parseLong(id));
						    usuario.setFotoBase64(usuarioVelho.getFotoBase64());
						    usuario.setContentType(usuarioVelho.getContentType()); 
						    usuario.setFotoBase64Miniatura(usuarioVelho.getFotoBase64Miniatura());		*/
					
				        	usuario.setAtualizarImagem(false);
					
				}

					/* Salvar Curriculo */
					Part curriculoPDF = request.getPart("curriculo");

					if (curriculoPDF != null  && curriculoPDF.getInputStream().available() > 0) {
						// Obter o tipo de arquivo
						String curricuContentType = curriculoPDF.getContentType();

						// Converte o arquivo byte para base64
						String curriculobase64 = new Base64()
								.encodeBase64String(convertStreamParaByte(curriculoPDF.getInputStream()));

						usuario.setCurriculoBase64(curriculobase64);
						usuario.setCurriculoContentType(curricuContentType);
					} else {
						// Caso não exista arquivo selecionado no formulário, recuperar do banco de dados 
					/*	BeansCursoJsp usuarioVelho = daoUsuario.consultar(Long.parseLong(id));
						usuario.setCurriculoBase64(usuarioVelho.getCurriculoBase64());
						usuario.setCurriculoContentType(usuarioVelho.getCurriculoContentType());*/
						usuario.setAtualizarPdf(false);
					}
				}
				
				
				
				

				/* FIM */

				/*   */

				// Validar campos vazios

				if (login.isEmpty() || login == null) {
					request.setAttribute("msg", "O campo de login está vazio!");
					request.setAttribute("user", usuario);
				} else if (senha.isEmpty() || senha == null) {
					request.setAttribute("msg", "O campo de senha está vazio!");
					request.setAttribute("user", usuario);
				} else if (nome.isEmpty() || nome == null) {
					request.setAttribute("msg", "O campo de nome está vazio!");
					request.setAttribute("user", usuario);
				} else {

					// Validar usuario e login para salvar

					String msg = "";

					if (id == null || id.isEmpty()) {

						if (!daoUsuario.validarLogin(login)) {
							msg = "Já existe usuário com o mesmo Login!";
							request.setAttribute("msg", msg);
							request.setAttribute("user", usuario);
						}

						if (!daoUsuario.validarSenha(senha)) {
							msg = msg + "<br>Já existe usuário com a mesma Senha!";
							request.setAttribute("msg", msg);
							request.setAttribute("user", usuario);
						}

					}

					// Salvar Usuário se o campo ID é vazio ou null, caso contrário Editar
					if (id == null
							|| id.isEmpty() && daoUsuario.validarLogin(login) && daoUsuario.validarSenha(senha)) {
						daoUsuario.salvar(usuario);
						msg = "Salvo com sucesso!";
						request.setAttribute("msg2", msg);
					} else if (id != null && !id.isEmpty()) {

						// Editar Usuário
						if (daoUsuario.validarLoginUpdate(login, id) && daoUsuario.validarSenhaUpdate(senha, id)) {
							daoUsuario.atualizar(usuario);
							msg = "Edição feita com sucesso!";
							request.setAttribute("msg2", msg);
						} else {

							// Validar usuario e login para editar

							if (!daoUsuario.validarLoginUpdate(login, id)) {
								msg = "Já existe usuário com o mesmo Login!";
								request.setAttribute("msg", msg);
								request.setAttribute("user", usuario);
							}

							if (!daoUsuario.validarSenhaUpdate(senha, id)) {
								msg = msg + "<br>Já existe usuário com a mesma Senha!";
								request.setAttribute("msg", msg);
								request.setAttribute("user", usuario);
							}
						}
					}

				}
				// Redirecionar página e listar

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/* Converte a entrada de fluxo de dados da imagem em um array de bytes */
	private byte[] convertStreamParaByte(InputStream imagem) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();

		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}

		return baos.toByteArray();
	}

}
