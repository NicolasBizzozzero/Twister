package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bd.Database;

public class MytestSQL {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection co=Database.getMySQLConnection();
		Statement st=co.createStatement();
		
		
		String query="select * from Utilisateurs";
		
		ResultSet res= st.executeQuery(query);
		
		while(res.next()){
			System.out.println(res.getString("nom"));
		}
		
		st.close();
		co.close();
	}

}