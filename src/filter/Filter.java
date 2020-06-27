package filter;   // É um filtro entre a conexão

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import connection.SingleConnection;

@WebFilter(urlPatterns= {"/*"})  //Toda url requisitada vai passar pelo filter

public class Filter implements javax.servlet.Filter{
	
	public static Connection connection;
	@Override
	public void destroy(){
	
	}

	//Confirma se os dados foram commitados no bando de dados
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		try {
			arg2.doFilter(arg0, arg1);
			
			//Se não há erro commita informações no banco de dados
			connection.commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				//Caso haja erro desfaz commit
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} 
		}
		
		
	}

	//Inicia connexão
	
	@Override
	public void init(FilterConfig arg0) throws ServletException{
		// TODO Auto-generated method stub
		connection = SingleConnection.getConnection();
	}

	
	
 
}
