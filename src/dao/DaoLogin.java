package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.SingleConnection;

public class DaoLogin {
	
	private Connection connection;
	
	public DaoLogin() {
		connection = SingleConnection.getConnection();
	}

	
	public boolean validarLogin(String login, String senha) throws SQLException {
		
		String sql = "SELECT * FROM usuario WHERE login = '"+login+"' AND senha = '"+senha+"'";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			return true;   //Possue usuário
		}else {
			return false;   //Não possue usuário
		}
		
	}
}
