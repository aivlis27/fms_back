package com.cognizant.fms;

import java.util.Scanner;

import com.cognizant.fms.servicelayer.ServiceLayer;


public class App 
{
    public static void main( String[] args )
    {	
    	@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
    	
        ServiceLayer fmsc = new ServiceLayer();
        
        boolean flag = true;
        while(flag) {
        
	        System.out.println("\n\n[--- WELCOME TO SILVIA'S FLIGHT MANAGEMENT SYSTEM :) ---]\n\n");
	        
	        System.out.println("Choose an option:");
	        System.out.println("1. Login");
	        System.out.println("2. New User");
	        System.out.println("3. Show all flights");
	        System.out.println("4. Search flight");
	        System.out.println("5. Book ticket");
	        System.out.println("6. Show my ticket");
	        System.out.println("7. Cancel my ticket");
	        System.out.println("8. Exit");
	        
	        int choice = sc.nextInt();        
	        switch(choice) {
		        case 1 : {
		        	fmsc.getLoginDisplay();
		        	continue;
		        }
		        case 2:{
		        	fmsc.getNewUserDisplay();
		        	continue;
		        }
		        case 3: {
		        	fmsc.getAllFlightDisplay();
		        	continue;
		        }
		        case 4:{
		        	fmsc.getSearchFlightDisplay();
		        	continue;
		        }
		        case 5:{
		        	fmsc.getBookFlightDisplay();
		        	continue;
		        }
		        case 6:{
		        	fmsc.getTicketsDisplay();
		        	continue;
		        }
		        case 7:{
		        	fmsc.getTicketCancelled();
		        	continue;
		        }
		        case 8:{
		        	flag = false;
		        	break;
		        }
		        default : System.out.println("Invalid Option...Try Again.");
		        
	        }
        }
    }

}
