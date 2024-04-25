package com.cognizant.fms.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cognizant.fms.bean.Booking;
import com.cognizant.fms.database.DBcon;

public class BookingDao {

	public boolean bookTicket(Booking obj) {
		
		try {
			Connection c = DBcon.con();
			String query = "Insert into ticket(ticket_id, traveling_date, flight_class, price, no_of_seats, flight_id, user_id) values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = c.prepareStatement(query);
			ps.setString(1, obj.getTicketId());
			ps.setString(3, obj.getFlightClass());
			ps.setDouble(4, obj.getPrice());
			ps.setInt(5, obj.getSeat());
			ps.setInt(6, obj.getFlightId());
			ps.setLong(7, obj.getUserId());
			ps.setString(2,obj.getTravelDate() );
			ps.executeUpdate();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet showTicket(long uid) {
		try {
			Connection c = DBcon.con();
			String query = "select u.first_name,t.ticket_id, f.origin,f.destination , t.traveling_date,t.no_of_seats,t.flight_class,u.age ,u.gender from ticket t inner join flights f ON f.flight_id = t.flight_id inner join users u  ON u.user_id = t.user_id where u.user_id = "+uid;
			PreparedStatement ps = c.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			return rs;
		}catch(SQLException e) {
			e.getMessage();
			return null;
		}
	}
	
	public boolean cancelTicket(String tid,long uid) {
		try {
			Connection c = DBcon.con();
			String query = "delete from ticket where ticket_id = '"+tid+"' and user_id = "+uid;
			
			PreparedStatement ps = c.prepareStatement(query);
			int row = ps.executeUpdate();
			return row >0;
		}catch(SQLException e) {
			e.getMessage();
			return false;
		}
	}

	public ResultSet findByTicketId(String tid) {
		try {
			Connection c = DBcon.con();
			String query = "SELECT * FROM ticket WHERE ticket_id = '"+tid+"'";
			PreparedStatement ps = c.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			return rs;
		}catch(SQLException e) {
			e.getMessage();
			return null;
		}
	
	}
}
