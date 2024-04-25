package com.cognizant.fms.servicelayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.cognizant.fms.bean.Booking;
import com.cognizant.fms.bean.User;
import com.cognizant.fms.dao.BookingDao;
import com.cognizant.fms.dao.FlightDao;
import com.cognizant.fms.dao.UserDao;

public class ServiceLayer {
	//GLOBAL VARIABLE
	public long user_ID = 0;
	
	//Scanner Object
	Scanner sc = new Scanner(System.in);
	
	//DAO Objects
	FlightDao fdao = new FlightDao();
	UserDao udao = new UserDao();
	BookingDao bdao = new BookingDao();
	
	//Models Object
	User userobj = new User();
	Booking bookingobj = new Booking();
	
	
	//Utility Methods
	public void getBookFlightDisplay() {
		if(user_ID==0) {
			System.out.println("Please Login First....\n\n");
			return;
		}
		
		getSearchFlightDisplay();
		
		System.out.println("Enter Flight Id : ");
		int fid = sc.nextInt();
		try {
			ResultSet res = fdao.checkFlight(fid);
			
			
			if(res.next()) {
				
				System.out.println("Enter Your Class : ");
				String cls = sc.next();
				System.out.println("Enter Number of Seats : ");
				int seat = sc.nextInt();
				
				bookingobj.setFlightId(fid);
				bookingobj.setFlightClass(cls);
				bookingobj.setSeat(seat);
				bookingobj.setUserId(user_ID);
				
				//Travel Date
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
				String dateOfTravelling =formatter.format(res.getDate("departure_time")); ;
				bookingobj.setTravelDate(dateOfTravelling);
				
				//Price 
				if(cls.equalsIgnoreCase("economic")) {
					double p = seat * res.getInt("eco_price");
					bookingobj.setPrice(p);
				}else if(cls.equalsIgnoreCase("business")) {
					double p = seat * res.getInt("bus_price");
					bookingobj.setPrice(p);
				}
				//Ticket Generator
				String tid = ""+user_ID+res.getString("flight_name")+res.getDate("departure_time")+"";
				bookingobj.setTicketId(tid);
				
				boolean response = bdao.bookTicket(bookingobj);
				
				if(response) {
					System.out.println("Thanks, "+user_ID+". Your Flight Booked Successfully");
					fdao.updateSeat(fid, seat, cls, "-");
				}else {
					System.out.println("Not Booked....Something is wrong!...Try Again");
				}
				
			}else {
				System.out.println("No Such Flight Available");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getSearchFlightDisplay() {
		
		System.out.println("Enter your source : ");
		String src = sc.next();
		
		System.out.println("Enter your Destination : ");
		String dest = sc.next();
		
		try {
			
			ResultSet res = fdao.searchFlight(src,dest);
			
			int size = 0;
			while(res.next()) {
				size++;
			}
			
			
			if(size>0) {
					ResultSet result = fdao.searchFlight(src,dest);
					System.out.println("Flight ID\t\tFlight Name\t\tDeparture\t\tArrival\t\tEconomic Seats\t\tEconomic Price\t\tBusiness Seats\t\tBusiness Price");
					while(result.next()) {
						System.out.println(result.getInt("flight_id")+"\t\t\t"+result.getString("flight_name")+"\t\t"+result.getDate("departure_time")+"\t\t"+result.getDate("arrival_time")+"\t\t"+result.getInt("available_eco_seats")+"\t\t\t"+result.getDouble("eco_price")+"\t\t\t"+result.getInt("available_bus_seats")+"\t\t\t"+result.getDouble("bus_price"));
					}
			}else {
				System.out.println("Sorry!....No Flights Availabale From "+src+" To "+dest);
			}


		} catch (SQLException e) {
			e.getMessage();
		}
	}

		
	
	//DISPLAYS METHODS
	
	public void getLoginDisplay() {
		System.out.println("Enter your mobile number: ");
		long mob = sc.nextLong();
		
		try{
			ResultSet rs = udao.findUserById(mob);
		
			if(rs.next()) {
				System.out.println("Logged in as "+rs.getString("first_name")+"\n\n\n");
				user_ID = rs.getLong("user_id");
			}else {
				System.out.println("User does not exist.....Please Create Your Account");
			}
		}catch(SQLException e) {
			e.getMessage();
		}
	}
	

	public void getNewUserDisplay() {
		if(user_ID > 0) {
			System.out.println("User already logged in. Log out for creating new account.");
			return;
		}
		try {
			System.out.println("Enter your First Name : ");
			String fn = sc.next();
			System.out.println("Enter your Last Name : ");
			String ln = sc.next();
			System.out.println("Enter your Mobile No.(Mandatory)");
			long num = sc.nextLong();
			String numString = ""+num+"";
			
			if(!Pattern.matches("[789][0-9]{9}", numString)){
				System.out.println("Invalid Input");
				return;
			}
			System.out.println("Enter your Age : ");
			int a = sc.nextInt();
			
				if(a<=0 || a>100) {
					System.out.println("You are not allowed to board our flight.");	
					return;
				}
			System.out.println("Enter your Gender : ");
			String gen = sc.next();
						
			userobj.setFname(fn);
			userobj.setLname(ln);
			userobj.setUserId(num);
			userobj.setAge(a);
			userobj.setGender(gen);
			
			boolean result = udao.addUser(userobj);
			if(result == true) {
				System.out.println(fn+", Your Account created successfully");
			}else {
				System.out.println("Something Went Wrong");
			}
		}catch(Exception e){
			System.out.println("Invalid Input");
		}
		
	}

	public void getAllFlightDisplay() {
		try {
			ResultSet rs = fdao.showFlight();
			
			int size = 0;
			while(rs.next()) {
				size++;
			}
			
			
			if(size>0) {
					ResultSet result = fdao.showFlight();
					System.out.println("Flight ID\t\tFlight Name\t\tDeparture\t\t\tArrival\t\t\t\tEconomic Seats\t\tEconomic Price\t\tBusiness Seats\t\tBusiness Price");
					while(result.next()) {
						System.out.println(result.getInt("flight_id")+"\t\t\t"+result.getString("flight_name")+"\t\t\t"+result.getDate("departure_time")+"\t\t\t"+result.getDate("arrival_time")+"\t\t\t"+result.getInt("available_eco_seats")+"\t\t\t"+result.getDouble("eco_price")+"\t\t\t"+result.getInt("available_bus_seats")+"\t\t\t"+result.getDouble("bus_price"));
					}
			}else {
				System.out.println("Sorry!....No Flights Availabale");
			}
			
			
			
		}catch(Exception e) {
			e.getMessage();
		}
		
	}
    
	public void getTicketsDisplay() {
		try {
			ResultSet rs = bdao.showTicket(user_ID);
			while(rs.next()) {
				System.out.println("Ticket ID : "+rs.getString("ticket_id"));
				System.out.println("Name : "+rs.getString("first_name"));
				System.out.println("From "+rs.getString("origin")+" To "+rs.getString("destination"));
				System.out.println(rs.getString("traveling_date").toUpperCase());
				System.out.println("Age : "+rs.getInt("age"));
				System.out.println("Gender : "+rs.getString("gender"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void getTicketCancelled() {
		System.out.println("Enter Ticket ID : ");
		String tid = sc.next();
		try {
			ResultSet ticketDetail = bdao.findByTicketId(tid);
			boolean result = bdao.cancelTicket(tid,user_ID);
			if(result == true){
				System.out.println("Deleted Successfully!");
				if(ticketDetail.next()) {
					fdao.updateSeat(ticketDetail.getInt("flight_id"), ticketDetail.getInt("no_of_seats"), ticketDetail.getString("flight_class"), "+");	
				}
			}else {
				System.out.println("You are trying to cancel wrong ticket.");
			}
		}catch(Exception e) {
			e.getMessage();
		}
	}
}