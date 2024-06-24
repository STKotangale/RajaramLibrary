package com.raja.lib.tools;

import java.sql.* ;

public class DataConnectionTest 
{
	// Password == bene#123
	public static void main(String argu[])
	{
		try
		{
			System.out.println("Sk1");
			String driver = "com.mysql.cj.jdbc.Driver";
			// com.mysql.cj.jdbc.Driver
			System.out.println("Sk2");
			Class.forName(driver).newInstance();
			System.out.println("Sk3");
			String url="jdbc:mysql://localhost:3306/rajalib?user=root&password=root";
			//String url="jdbc:mysql://localhost:3306/beneficiary?user=root&password=root";
			System.out.println("Sk4");
			Connection con = DriverManager.getConnection(url);;
			System.out.println("Sk5");
			Statement stmt = con.createStatement();
			System.out.println("Sk6");
			String query = "select * from auth_users";
			System.out.println(query);
			// select * from ad_admin as ad, ad_branch as br, ad_session ses  where ad.adminUsername ='sandesh' AND ad.adminPassword ='dxefDkPE4u0=' AND  ses.sessionId='2011'  AND ad.branchIdF=br.branchId AND ad.isBlock='N'"
			ResultSet rst = stmt.executeQuery(query);
			System.out.println("Sk7");
			rst.next();
			System.out.println("Sk8");
			System.out.println("userId  "+ rst.getString("userId"));
			while(rst.next())
			{
				System.out.println("userId  "+ rst.getString("userId"));
				System.out.println("username  "+ rst.getString("username"));
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