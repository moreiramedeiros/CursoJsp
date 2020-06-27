package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeansTelefone;
import connection.SingleConnection;

public class DaoTelefone {
	private Connection connection;


	public DaoTelefone() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeansTelefone telefone) {
		try {

			String sql = "INSERT INTO telefone (numero, tipo, id_usuario) VALUES (?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, telefone.getNumero());
			preparedStatement.setString(2, telefone.getTipo());
			preparedStatement.setLong(3, telefone.getId_usuario());
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

	public List<BeansTelefone> listar(Long id_usuario) throws SQLException {
		List<BeansTelefone> lista = new ArrayList<BeansTelefone>();

		String sql = "SELECT * FROM telefone WHERE id_usuario = "+id_usuario;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeansTelefone telefone = new BeansTelefone();
			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setId_usuario(resultSet.getLong("id_usuario"));
			lista.add(telefone);
		}
		return lista;
	}

	public void deletar(Long id) {
		try {

			String sql = "delete from telefone where id = " + id;
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

}
