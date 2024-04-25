package com.cognizant.fms.dao;
import com.cognizant.fms.bean.User;
import com.cognizant.fms.database.DBcon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
	public ResultSet findUserById(long uid) {
		try{
			Connection cn = DBcon.con();
		String query = "select * from users where user_id = '"+uid+"'";
		PreparedStatement ps = cn.prepareStatement(query);
		ResultSet r = ps.executeQuery();
		return r;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	public boolean addUser(User obj) {
		try{
			Connection cn = DBcon.con();
			
			PreparedStatement ps = cn.prepareStatement("insert into users (user_id, first_name, last_name, age, gender) values (?, ?, ?, ?, ?)");
			ps.setLong(1,obj.getUserId());
			ps.setString(2,obj.getFname());
			ps.setString(3,obj.getLname());
			ps.setInt(4,obj.getAge());
			ps.setString(5,obj.getGender());
			ps.executeUpdate();
			
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
}
}
