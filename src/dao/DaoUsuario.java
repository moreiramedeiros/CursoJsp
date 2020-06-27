package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeansCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {
	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeansCursoJsp usuario) {
		try {

			String sql = "INSERT INTO usuario (login, senha, nome, telefone, cep, rua, bairro, cidade, estado, ibge, fotobase64, contenttype, curriculobase64, curriculocontenttype, fotobase64miniatura, ativo, sexo, perfil) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getSenha());
			preparedStatement.setString(3, usuario.getNome());
			preparedStatement.setString(4, usuario.getTelefone());
			preparedStatement.setString(5, usuario.getCep());
			preparedStatement.setString(6, usuario.getRua());
			preparedStatement.setString(7, usuario.getBairro());
			preparedStatement.setString(8, usuario.getCidade());
			preparedStatement.setString(9, usuario.getEstado());
			preparedStatement.setString(10, usuario.getIbge());
			preparedStatement.setString(11, usuario.getFotoBase64());
			preparedStatement.setString(12, usuario.getContentType());
			preparedStatement.setString(13, usuario.getCurriculoBase64());
			preparedStatement.setString(14, usuario.getCurriculoContentType());
			preparedStatement.setString(15, usuario.getFotoBase64Miniatura());
			preparedStatement.setBoolean(16, usuario.isAtivo());
			preparedStatement.setString(17, usuario.getSexo());
			preparedStatement.setString(18, usuario.getPerfil());
			preparedStatement.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	
	
 
	
	public List<BeansCursoJsp> listar() throws SQLException {
		List<BeansCursoJsp> lista = new ArrayList<BeansCursoJsp>();
		String sql = "SELECT * FROM usuario where login <> 'admin' " + " order by id";	
		consultaUsuario(lista, sql);
		return lista;
	}

	public List<BeansCursoJsp> pesquisar(String nome) throws SQLException {
		List<BeansCursoJsp> lista = new ArrayList<BeansCursoJsp>();
		String sql = "SELECT * FROM usuario where login <> 'admin' " + " and nome like '%"+nome+"%' order by id";
		consultaUsuario(lista, sql);
		return lista;
	}
	
	private void consultaUsuario(List<BeansCursoJsp> lista, String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeansCursoJsp usuario = new BeansCursoJsp();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setTelefone(resultSet.getString("telefone"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setIbge(resultSet.getString("ibge"));
			usuario.setFotoBase64(resultSet.getString("fotobase64"));
			usuario.setContentType(resultSet.getString("contenttype"));
			usuario.setCurriculoBase64(resultSet.getString("curriculobase64"));
			usuario.setCurriculoContentType(resultSet.getString("curriculocontenttype"));
			usuario.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
			usuario.setAtivo(resultSet.getBoolean("ativo"));
			usuario.setSexo(resultSet.getString("sexo"));
			usuario.setPerfil(resultSet.getString("perfil"));
			lista.add(usuario);
		}
	}

	public void deletar(Long id) {
		try {

			String sql = "delete from usuario where id = " + id + " and login <> 'admin'";
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}


	public BeansCursoJsp consultar(Long id) throws SQLException {
		String sql = "select * FROM usuario where id = " + id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			BeansCursoJsp usuario = new BeansCursoJsp();
			usuario.setId(resultSet.getLong("id"));
			usuario.setLogin(resultSet.getString("login"));
			usuario.setSenha(resultSet.getString("senha"));
			usuario.setNome(resultSet.getString("nome"));
			usuario.setTelefone(resultSet.getString("telefone"));
			usuario.setCep(resultSet.getString("cep"));
			usuario.setRua(resultSet.getString("rua"));
			usuario.setBairro(resultSet.getString("bairro"));
			usuario.setCidade(resultSet.getString("cidade"));
			usuario.setEstado(resultSet.getString("estado"));
			usuario.setIbge(resultSet.getString("ibge"));
			usuario.setFotoBase64(resultSet.getString("fotobase64"));
			usuario.setContentType(resultSet.getString("contenttype"));
			usuario.setCurriculoBase64(resultSet.getString("curriculobase64"));
			usuario.setCurriculoContentType(resultSet.getString("curriculocontenttype"));
			usuario.setFotoBase64Miniatura(resultSet.getString("fotobase64miniatura"));
			usuario.setAtivo(resultSet.getBoolean("ativo"));
			usuario.setSexo(resultSet.getString("sexo"));
			usuario.setPerfil(resultSet.getString("perfil"));
			return usuario;
		}
		return null;
	}

	public void atualizar(BeansCursoJsp usuario) {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update usuario set login = ?, senha = ?, nome = ?, telefone = ? ");
			sql.append(", cep = ?, rua = ?, bairro = ?, cidade = ?, estado = ?, ibge = ? ");

			// Atualizar imagem se metodo retorna true
			if (usuario.isAtualizarImagem()) {
				sql.append(", fotobase64 = ?, contenttype = ?");
			}

			// Atualizar curriculo se metodo retorna true
			if (usuario.isAtualizarPdf()) {
				sql.append(", curriculobase64  = ?, curriculocontenttype  = ? ");
			}

			// Atualizar miniatura de metodo retorna true
			if (usuario.isAtualizarImagem()) {
				sql.append(", fotobase64miniatura = ? ");
			}
			sql.append(", ativo = ?, sexo  = ?, perfil  = ?  ");
			sql.append("where id = " + usuario.getId());

			PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getSenha());
			preparedStatement.setString(3, usuario.getNome());
			preparedStatement.setString(4, usuario.getTelefone());
			preparedStatement.setString(5, usuario.getCep());
			preparedStatement.setString(6, usuario.getRua());
			preparedStatement.setString(7, usuario.getBairro());
			preparedStatement.setString(8, usuario.getCidade());
			preparedStatement.setString(9, usuario.getEstado());
			preparedStatement.setString(10, usuario.getIbge());

			if (usuario.isAtualizarImagem() && usuario.isAtualizarPdf()) {
				preparedStatement.setString(11, usuario.getFotoBase64());
				preparedStatement.setString(12, usuario.getContentType());
				preparedStatement.setString(13, usuario.getCurriculoBase64());
				preparedStatement.setString(14, usuario.getCurriculoContentType());
				preparedStatement.setString(15, usuario.getFotoBase64Miniatura());				
				preparedStatement.setBoolean(16, usuario.isAtivo());
				preparedStatement.setString(17, usuario.getSexo());
				preparedStatement.setString(18, usuario.getPerfil());
			}else if (usuario.isAtualizarImagem() && !usuario.isAtualizarPdf()) {
				preparedStatement.setString(11, usuario.getFotoBase64());
				preparedStatement.setString(12, usuario.getContentType());
				preparedStatement.setString(13, usuario.getFotoBase64Miniatura());				
				preparedStatement.setBoolean(14, usuario.isAtivo());
				preparedStatement.setString(15, usuario.getSexo());
				preparedStatement.setString(16, usuario.getPerfil());
			}else if (!usuario.isAtualizarImagem() && usuario.isAtualizarPdf()) {
				preparedStatement.setString(11, usuario.getCurriculoBase64());
				preparedStatement.setString(12, usuario.getCurriculoContentType());				
				preparedStatement.setBoolean(13, usuario.isAtivo());
				preparedStatement.setString(14, usuario.getSexo());
				preparedStatement.setString(15, usuario.getPerfil());
			}else {
				preparedStatement.setBoolean(11, usuario.isAtivo());
				preparedStatement.setString(12, usuario.getSexo());
				preparedStatement.setString(13, usuario.getPerfil());
			}

			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/*
	 * public void atualizar(BeansCursoJsp usuario) { try {
	 * 
	 * String sql =
	 * "update usuario set login = ?, senha = ?, nome = ?, telefone = ?, "+
	 * "cep = ?, rua = ?, bairro = ?, cidade = ?, estado = ?, ibge = ?, fotobase64 = ?, contenttype = ?, curriculobase64  = ?, curriculocontenttype  = ?, fotobase64miniatura = ? "
	 * + "where id = "+usuario.getId();
	 * 
	 * PreparedStatement preparedStatement = connection.prepareStatement(sql);
	 * preparedStatement.setString(1, usuario.getLogin());
	 * preparedStatement.setString(2, usuario.getSenha());
	 * preparedStatement.setString(3, usuario.getNome());
	 * preparedStatement.setString(4, usuario.getTelefone());
	 * preparedStatement.setString(5, usuario.getCep());
	 * preparedStatement.setString(6, usuario.getRua());
	 * preparedStatement.setString(7, usuario.getBairro());
	 * preparedStatement.setString(8, usuario.getCidade());
	 * preparedStatement.setString(9, usuario.getEstado());
	 * preparedStatement.setString(10,usuario.getIbge());
	 * preparedStatement.setString(11, usuario.getFotoBase64());
	 * preparedStatement.setString(12,usuario.getContentType());
	 * preparedStatement.setString(13, usuario.getCurriculoBase64());
	 * preparedStatement.setString(14,usuario.getCurriculoContentType());
	 * preparedStatement.setString(15,usuario.getFotoBase64Miniatura());
	 * 
	 * preparedStatement.executeUpdate(); connection.commit(); } catch (SQLException
	 * e) { // TODO Auto-generated catch block e.printStackTrace();
	 * 
	 * try { connection.rollback(); } catch (SQLException e1) { // TODO
	 * Auto-generated catch block e1.printStackTrace(); } }
	 * 
	 * }
	 * 
	 */

	// Retorna true se o login é válido, e false se o login é inválido ou já existe

	public boolean validarLogin(String login) throws SQLException {
		String sql = "select count(1) as qtd FROM usuario where login = '" + login + "'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0; /* Deve retornar true */

		}

		return false;
	}

	// retorna false se o login já existe com outro usuário com id diferente

	public boolean validarLoginUpdate(String login, String id) throws SQLException {
		String sql = "select count(1) as qtd FROM usuario where login = '" + login + "' and id <> " + id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0; /* Deve retornar true */

		}

		return false;
	}

	// Retorna true se o senha é válido, e false se o login é inválido ou já existe

	public boolean validarSenha(String senha) throws SQLException {
		String sql = "select count(1) as qtd FROM usuario where senha = '" + senha + "'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0; /* Deve retornar true */

		}

		return false;
	}

	// retorna false se o senha já existe com outro usuário com id diferente

	public boolean validarSenhaUpdate(String senha, String id) throws SQLException {
		String sql = "select count(1) as qtd FROM usuario where senha = '" + senha + "' and id <> " + id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0; /* Deve retornar true */

		}

		return false;
	}

	
	
	 

 
	
	
	
	
	
	
	
	
}
