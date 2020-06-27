package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeansCategoria;
import beans.BeansProduto;
import connection.SingleConnection;

public class DaoProduto {
	private Connection connection;

	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeansProduto produto) {
		try {

			String sql = "INSERT INTO produto (nome, qtd, valor, categoria_id) VALUES (?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, produto.getNome());
			preparedStatement.setLong(2, produto.getQtd());
			preparedStatement.setDouble(3, produto.getValor());
			preparedStatement.setLong(4, produto.getCategoria_id());
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

	public List<BeansProduto> listar() throws SQLException {
		List<BeansProduto> lista = new ArrayList<BeansProduto>();

		String sql = "SELECT * FROM produto  order by id";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeansProduto produto = new BeansProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQtd(resultSet.getLong("qtd"));
			produto.setValor(resultSet.getDouble("valor"));
			produto.setCategoria_id(resultSet.getLong("categoria_id"));
			lista.add(produto);
		}
		return lista;
	}

	
	
	
	
	
	public void deletar(Long id) {
		try {

			String sql = "delete from produto where id = "+id;
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

	public BeansProduto consultar(Long id) throws SQLException {
		String sql = "select * FROM produto where id = "+id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			BeansProduto produto = new BeansProduto();
			produto.setId(resultSet.getLong("id"));
			produto.setNome(resultSet.getString("nome"));
			produto.setQtd(resultSet.getLong("qtd"));
			produto.setValor(resultSet.getDouble("valor"));
			produto.setCategoria_id(resultSet.getLong("categoria_id"));
			return produto;
		}
		return null;
	}
	
	
	
 
	public void atualizar(BeansProduto produto) {
		try {

			String sql = "update produto set nome = ?, valor = ?, qtd  = ?, categoria_id = ? where id = "+produto.getId();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, produto.getNome());
			preparedStatement.setDouble(2, produto.getValor());
			preparedStatement.setLong(3, produto.getQtd());
			preparedStatement.setLong(4, produto.getCategoria_id());
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
	
	
	
	//Retorna true se o nome é válido, e false se o nome é inválido ou já existe
	
		public boolean validarNome(String nome) throws SQLException {
			String sql = "select count(1) as qtd FROM produto where nome = '"+nome+"'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			 while(resultSet.next()){
				 
				return resultSet.getInt("qtd")<=0;  /*Deve retornar true*/
				
			 }
			 
			return false;
		}

		//retorna false se o login já existe com outro usuário com id diferente
		
		public boolean validarNomeUpdate(String nome, String id) throws SQLException {
			String sql = "select count(1) as qtd FROM produto where nome = '"+nome+"' and id <> "+id;
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			 while(resultSet.next()){
				 
				return resultSet.getInt("qtd")<=0;  /*Deve retornar true*/
				
			 }
			
			return false;
		}	 
		
	
	 
	
	     public List<BeansCategoria> listarCategoria() throws SQLException{
	    	 List<BeansCategoria> lista = new ArrayList<BeansCategoria>();
	    	 String sql = "SELECT * FROM categoria";
	    	 PreparedStatement preparedstatment = connection.prepareStatement(sql);
	    	 ResultSet resultSet = preparedstatment.executeQuery();
	    	 while(resultSet.next()) {
	    		 BeansCategoria beansCategoria = new BeansCategoria();
	    		 beansCategoria.setId(resultSet.getLong("id"));
	    		 beansCategoria.setNome(resultSet.getString("nome"));
	    		 lista.add(beansCategoria);
	    	 }
	    	 return lista;	    	 
	     }
	
	
	
}
