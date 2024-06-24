package com.raja.lib.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DateYYYYMMDDtoDDMMYYYY {

	public static void main(String[] args) {
		 
		try
		{
			
		    int    memberId;
		    String registerDate;
		    String dateOfBirth;
		    String confirmDate;

		    String registerDateC;
		    String dateOfBirthC;
		    String confirmDateC;

		    String driver = "com.mysql.cj.jdbc.Driver";
		    // com.mysql.cj.jdbc.Driver
			Class.forName(driver).newInstance();
			String url="jdbc:mysql://localhost:3306/rajalib?user=root&password=root";

			Connection conServerInsert  = DriverManager.getConnection(url);
			String queryServerEdit = "update auth_permanent_members set registerDate = ?, dateOfBirth = ?, confirmDate = ? Where permanentMemberId = ?";
			PreparedStatement pstmtServerInsert =	conServerInsert.prepareStatement(queryServerEdit);
			
			Connection con = DriverManager.getConnection(url);;
			Statement stmt = con.createStatement();
			String query = "select * from auth_permanent_members";
			ResultSet rst = stmt.executeQuery(query);
			while(rst.next())
			{
				memberId = rst.getInt("permanentMemberId") ;
				registerDate = rst.getString("registerDate") ;
				dateOfBirth = rst.getString("dateOfBirth") ;
				confirmDate = rst.getString("confirmDate") ;

			    registerDateC = DateConversion.StringYYYYMMDDToStringDDMMYYYY(registerDate) ;
			    dateOfBirthC = DateConversion.StringYYYYMMDDToStringDDMMYYYY(registerDate) ;
			    confirmDateC = DateConversion.StringYYYYMMDDToStringDDMMYYYY(registerDate) ;
				
				pstmtServerInsert.setString(1, registerDateC); 
				pstmtServerInsert.setString(2, dateOfBirthC); 
				pstmtServerInsert.setString(3, confirmDateC); 
				pstmtServerInsert.setInt(4, memberId ); 
				pstmtServerInsert.executeUpdate();
			    
				System.out.println("memberId  "+ memberId +"  registerDate "+registerDate+"  registerDateC "+registerDateC);
			}
			rst.close();
			stmt.close();
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
}