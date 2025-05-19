import java.util.Scanner;

public class BankApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		KodnestBank kb = KodnestBank.getInstance();
		
		//BankFactory bankf = new BankFactory();
		//KodnestBank kb = (KodnestBank) bankf.getBank();
		
		while (true) 
		{
			System.out.println("WELCOME TO KODNEST BANK");
			System.out.println("Chose From Below Menu");
			System.out.println("1===========>REGISTARATION");
			System.out.println("2===========>LOGIN");
			System.out.println("3===========>CHECK BALANCE");
			System.out.println("4===========>TRANSFER AMOUNT");
			System.out.println("5===========>UPDATE PASSWORD");
			System.out.println("6===========>UPDATE PROFILE");
			System.out.println("7===========>DELETE ACCOUNT");
			System.out.println("8===========>Check profile");
			System.out.println("9===========>STOP");
			
			int choice  = sc.nextInt();
			
			switch(choice)
			{
				case 1:
					kb.register();
					break;
				case 2:
					kb.login();
					break;
				case 3:
					kb.checkBalance();
					break;
				case 4:
					kb.transferAmount();
					break;
				case 5:
					kb.changePassword();
					break;
				case 6:
					kb.updateProfile();
					break;
				case 7:
					kb.delete();
					break;
				case 8:
					kb.checkProfile();
					break;
				default :
					System.out.println("Thank you for login.....");
					System.exit(0);
						
			}
		}
		

	}

}
