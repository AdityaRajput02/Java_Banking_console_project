import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public  class KodnestBank{
	
	//private static final String PrepreadStatement1 ps  = null;
	private Connection con = null;
	private Scanner scan = new Scanner(System.in);
	public static KodnestBank ref = null;
	
	public static KodnestBank getInstance()
	{
		if(ref == null)
		{
			ref = new KodnestBank();
		}
		
		return ref;
	}
	
		private KodnestBank()
		{
			//String path = 
			//String url = ;
			//String user = ;
			//String password = ;
			
			try
			{
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","MataRani@1234");
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

		//@CodeWriter(developer = "Omkar", task="This perfoms refistration")
		public void register()
		{
			try
			{
				System.out.println("Enter Account");
				String accno = scan.next();
				System.out.println("Enter password");
				String pwd = scan.next();
				System.out.println("Enter confirm password");
				String cpwd = scan.next();
				System.out.println("Enter name");
				String name = scan.next();
				System.out.println("Enter amount");
				String amt = scan.next();
				System.out.println("Enter age");
				int age = scan.nextInt();
				System.out.println("Enter email");
				String email = scan.next();
				System.out.println("Enter phone number");
				String phone = scan.next();
				
				if(accno.length() != 10 || pwd.equals(cpwd)==false || name.length()<3 || amt.length()<=0 || age<18 || email.length()<10 || phone.length() !=10)
				{
					System.out.println("registration unsuccessful..... please retry");
					System.out.println();

				}
				else
				{
					String sql = "insert into KodnestBank.Kodnesttable values (?,?,?,?,?,?,?)";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1,accno);
					ps.setString(2,pwd);
					ps.setString(3,name);
					ps.setString(4,amt);
					ps.setInt(5,age);
					ps.setString(6,email);
					ps.setString(7,phone);
					
					int nora = ps.executeUpdate();
					if(nora == 1)
					{
						System.out.println("Registration successful...");
						System.out.println();
					}
					else
					{
						System.out.println("Some Issue Occured please contact nearest branch..");
						System.out.println();

					}
				}
				
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void login()
		{
			
			try
			{
				String sql = "select * from KodnestBank.Kodnesttable where accno=? AND password=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				System.out.println("enter the account number..");
				String acc = scan.next();
				System.out.println("enter the password..");
				String pass = scan.next();
				
				ps.setString(1, acc);
				ps.setString(2,pass);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next())
				{
					System.out.println("Login success ful....");
					System.out.println("Your Balance is "+rs.getInt("amount"));
					System.out.println();
				}
				else
				{
					System.out.println("Invalid User or Password Please Try Again...");
					System.out.println();

				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		public void changePassword()
		{
			String sql = "select * from KodnestBank.Kodnesttable where accno=? AND password=?";
			try
			{
				PreparedStatement ps = con.prepareStatement(sql);
				System.out.println("enter the account number...");
				String acc = scan.next();
				System.out.println("enter the password....");
				String pass = scan.next();
				
				ps.setString(1,acc);
				ps.setString(2, pass);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next())
				{
					String sql2 = "update KodnestBank.Kodnesttable set password=? where accno=? and password=?";
					PreparedStatement ps1 = con.prepareStatement(sql2);
					
					System.out.println("enter the new password...");
					String newpass = scan.next();
					System.out.println("enter the confirm password...");
					String cpass = scan.next();
					
					if(newpass.equals(cpass))
					{
						ps1.setString(1, newpass);
						ps1.setString(2, acc);
						ps1.setString(3,pass);
					
						int nora = ps1.executeUpdate();
					
						if(nora == 1)
						{
							System.out.println("password updated...");
							System.out.println();

						}
						else
						{
							System.out.println("some problem Occured please try again...");
							System.out.println();

						}	
					
					}
					else
					{
						System.out.println("new password and confirm password is not match..");
						System.out.println();

					}
					
				}
				else
				{
					System.out.println("Old account number and password is Invalid.....");
					System.out.println();

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void transferAmount()
		{
			
			try
			{
				String sql1 = "update KodnestBank.Kodnesttable set amount=amount-? where accno=? and password=?";
				String sql2= "update KodnestBank.Kodnesttable set amount=amount+? where accno=?";
				
				con.setAutoCommit(false);
				
				PreparedStatement ps1 = con.prepareStatement(sql1);
				PreparedStatement ps2 = con.prepareStatement(sql2);
				
				
				System.out.println("enter the accno ");
				String sendersAccno = scan.next();
				
				System.out.println("enter the passord");
				String sendersPass = scan.next();
				
				System.out.println("enter the recivers account number...");
				String reciversAccno = scan.next();
				
				System.out.println("entr the amount that you want to transfer...");
				String amt = scan.next();
				
				ps1.setString(1,amt);
				ps1.setString(2,sendersAccno);
				ps1.setString(3,sendersPass);
				
				int nora1 = ps1.executeUpdate();
				
				ps2.setString(1,amt);
				ps2.setString(2,reciversAccno);
				
				int nora2 = ps2.executeUpdate();
				
				if(nora1 == 1 &&  nora2 == 1)
				{
					con.commit();
					System.out.println("Transaction is successful Thankyou for using kodnestBank....");
					System.out.println();
				}
				else
				{
					con.rollback();
				}
			}
			catch(Exception e)
			{
				try
				{
					con.rollback();
					System.out.println("some problem occured... don't worry your money is safe....");
					System.out.println();

				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
			
		}
		
		public void checkBalance()
		{
			try
			{
				String sql = "select * from KodnestBank.Kodnesttable where accno=? AND password=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				System.out.println("enter the account number..");
				String acc = scan.next();
				System.out.println("enter the password..");
				String pass = scan.next();
				
				ps.setString(1, acc);
				ps.setString(2,pass);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next())
				{
					
					System.out.println("Your Balance is "+rs.getInt("amount"));
					System.out.println();

				}
				else
				{
					System.out.println("Invalid User or Password Please Try Again...");
					System.out.println();

				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
		public void updateProfile()
		{
			try
			{
				String sql = "select * from kodnestbank.kodnesttable where accno=? and password=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				System.out.println("enter the account number...");
				String accno = scan.next();
				
				System.out.println("enter the passsword....");
				String pass = scan.next();
				
				ps.setString(1, accno);
				ps.setString(2, pass);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next())
				{
					String sql2 = "update kodnestbank.kodnesttable set email=?, phone=?, age=? where accno=?";
					PreparedStatement ps2 = con.prepareStatement(sql2);
					
					System.out.println("enter the new email... if you want to update or keep it same ...");
					String email = scan.next();
					
					System.out.println("enter the new phone number if you want to update or keep it same...");
					String phone = scan.next();
					
					System.out.println("enter the new age if you want to update or keet it same...");
					int age = scan.nextInt();
				
					
					ps2.setString(1, email);
					ps2.setString(2,phone);
					ps2.setInt(3, age);
					ps2.setString(4, accno);
					
					
					int nora = ps2.executeUpdate();
					
					if(nora == 1)
					{
						System.out.println("updation is done...");
						System.out.println();
					}
					else
					{
						System.out.println("some problem occured....");
					}
					
					
				}
				else
				{
					System.out.println("worng account number or password please try again...");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void delete()
		{
			try 
			{
				String sql = "delete from KodnestBank.Kodnesttable where accno=?";
				PreparedStatement ps = con.prepareStatement(sql);
				System.out.println("enter the account number....");
				String accno = scan.next();
				ps.setString(1, accno);
				
				int nora = ps.executeUpdate();
				
				if(nora == 1)
				{
					System.out.println("Your account is deleted....");
					
				}
				else
				{
					System.out.println("some problem occured... please try again...");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

		public void checkProfile()
		{

			
			try
			{
				String sql = "select * from KodnestBank.Kodnesttable where accno=? and password=?";
				PreparedStatement ps = con.prepareStatement(sql);
				
				System.out.println("enter the account number");
				String accno = scan.next();
				
				System.out.println("enter the password...");
				String pas = scan.next();
				
				ps.setString(1,accno);
				ps.setString(2, pas);
				
				ResultSet rs = ps.executeQuery();
				
				while(rs.next())
				{
					System.out.println("Account Number = "+rs.getString(1));
					System.out.println("password = "+rs.getString(2));
					System.out.println("Name = "+rs.getString(3));
					System.out.println("Amount = "+rs.getString(4));
					System.out.println("Age = "+rs.getString(5));
					System.out.println("Email = "+rs.getString(6));
					System.out.println("Phone = "+rs.getString(7));

				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
}

