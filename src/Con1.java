
public class Con1 {
	
		public static void check_age(int umar)
		{
			
			if(umar >= 18)
			{
				throw new ArithmeticException("you are eligible");
				
			}
			
				System.out.println("not eligible");
		}
	
		public static void main(String [] arg) throws Exception
		{
			//Con1 con= new Con1();
			//con.check_age();
			try
			{
				check_age(15);

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
}
