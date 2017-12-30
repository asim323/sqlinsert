package sqlinsert;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
public class insert {
		public String name = "";
		public static int rows = 0;
		public static int cols = 0;
		public static String[] label = null;
		public static String[][] ret = null;
		public static  Connection connect = null;
	    public static Statement statement = null;
	    public static PreparedStatement preparedStatement = null;
		public static void  main( String[] args) throws Exception 
		{
			BufferedReader br = null;

			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(
						"test.csv")));

				String line;

				line = br.readLine();
				setrows(line);

				int count = 0;

				while ((line = br.readLine()) != null) {

					count++;

				}

				cols = count;

				ret = new String[cols][rows];

				br.close();

				br = new BufferedReader(new InputStreamReader(new FileInputStream(
						"test.csv")));

				line = "start";
				count = 0;
				while ((line) != null) {
					line = br.readLine();
					System.out.println(count + ":" + line);
					
					if (line != null) {

						String[] temp = line.split(",");
						try {
							for (int i = 0; i < rows; i++) {
								ret[count][i] = temp[i];
							}
						} catch (Exception e) {
							//e.printStackTrace();
							continue;
						}
					}
					count++;
				}

				System.out.println("load complete");
				 Class.forName("com.mysql.jdbc.Driver");
		           
		            connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"
		                      + "user=root&password="); 
		            preparedStatement = connect.prepareStatement("insert into feedback.comments values (default, ?, ?, ? , ?, ?)");
					for (int i = 0; i < rows; i++) 
					{
						preparedStatement.setString(1, ret[i][1]);
						preparedStatement.setString(2, ret[i][2]);
						preparedStatement.setString(3, ret[i][3]);
						preparedStatement.setString(4, ret[i][4]);
						preparedStatement.setString(5, ret[i][5]);
						preparedStatement.executeUpdate();
				
					}


			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


			
			
		}
		private static void setrows(String firstrow) {
			String[] temp = firstrow.split(",");
			rows = temp.length;

			label = new String[rows];

			for (int i = 0; i < rows; i++) {
				label[i] = temp[i];
			}
		}
	}



