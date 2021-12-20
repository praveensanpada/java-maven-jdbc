import java.sql.*;
import java.io.*;

class DBConnection{

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    DBConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo","root","root");
            System.out.println("\n                Application Started....\n");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void insertRecord(String firstName, String lastName, int rollNumber, String address, String className){
        try{ 
            ps = con.prepareStatement("select * from students where rollNumber = ?");
            ps.setInt(1,rollNumber);
            rs = ps.executeQuery();
            int rollCountInsert=0;
            
            while(rs.next()){
                rollCountInsert++;
            }

            if(rollCountInsert>0){
                System.out.println("\n                RECORD UISNG THIS ROLL ALREADY FOUND.....\n");
            }else{
                String query="INSERT INTO students(firstName,lastName,rollNumber,address,className) VALUES (?,?,?,?,?)";
                ps = con.prepareStatement(query);
                ps.setString(1,firstName);
                ps.setString(2,lastName);
                ps.setInt(3,rollNumber);
                ps.setString(4,address);
                ps.setString(5,className);
                ps.executeUpdate();
                System.out.println("\n                Data Inserted.....\n");
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void displayRecords(){
        try{
            ps = con.prepareStatement("select * from students");
            rs = ps.executeQuery();
     
            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-5s| %-15s| %-15s| %-10s| %-20s| %-15s| %-10s |\n","ID","FIRST NAME","LAST NAME","ROLLN","ADDRESS","CLASS NAME","DATE");
            System.out.println("----------------------------------------------------------------------------------------------------------");

            while(rs.next()){
                System.out.printf("| %-5s| %-15s| %-15s| %-10s| %-20s| %-15s| %-10s |\n",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }
        
            System.out.println("----------------------------------------------------------------------------------------------------------");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void searchByFname(String firstName){
        try{
            String firstNameSql = "select * from students where firstName like '%"+firstName+"%'";
            System.out.println(firstNameSql);
            ps = con.prepareStatement(firstNameSql);
            rs = ps.executeQuery();

            System.out.println("----------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-5s| %-15s| %-15s| %-10s| %-20s| %-15s| %-10s |\n","ID","FIRST NAME","LAST NAME","ROLLN","ADDRESS","CLASS NAME","DATE");
            System.out.println("----------------------------------------------------------------------------------------------------------");
      
            while(rs.next()){
                System.out.printf("| %-5s| %-15s| %-15s| %-10s| %-20s| %-15s| %-10s |\n",rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getString(7));
            }

            System.out.println("----------------------------------------------------------------------------------------------------------");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void deleteRecord(int rollNumber){
        try{
            ps = con.prepareStatement("DELETE FROM students WHERE rollNumber = ?");
            ps.setInt(1,rollNumber);
            ps.executeUpdate();
            System.out.println("\n                DELETED.......\n");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void  updateRecord(String firstName,String lastName, String address, String className, int rollNumber){
        try{
            String queryUpdate="update students set firstName = ?, lastName = ?, address = ?, className = ? where rollNumber = ?";
            ps = con.prepareStatement(queryUpdate);
            ps.setString(1,firstName);
            ps.setString(2,lastName);
            ps.setString(3,address);
            ps.setString(4,className);
            ps.setInt(5,rollNumber);
            ps.executeUpdate();
            System.out.println("\n                UPDATED.......\n");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void close(){
        try{
            con.close();
            System.out.println("\n                Application Close....\n");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        int choice = -1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DBConnection con = new DBConnection();

        try{
            do{
                System.out.println();
                System.out.println("                WELCOME TO JDBC Application :)");
                System.out.println("                1) Insert The Data");
                System.out.println("                2) Display The Data");
                System.out.println("                3) Search By First Name");
                System.out.println("                4) Update The Data");
                System.out.println("                5) Delete The Data");
                System.out.println("                6) EXIT");
                System.out.print("                Select Your Choice:- ");
                choice = Integer.parseInt(br.readLine());
                System.out.println();
                switch(choice){

                    case 1:
                        System.out.print("                Enter The First Name:- ");
                        String firstName = br.readLine();
                        System.out.print("                Enter The Last Name:- ");
                        String lastName = br.readLine();
                        System.out.print("                Enter The Roll Number:- ");
                        int rollNumber =Integer.parseInt(br.readLine());
                        System.out.print("                Enter The Address:- ");
                        String address = br.readLine();
                        System.out.print("                Enter The Class Name:- ");
                        String className = br.readLine();
                        con.insertRecord(firstName,lastName,rollNumber,address,className);
                    break;


                    case 2:
                        con.displayRecords();
                    break;

                    case 3:
                        System.out.print("                Enter The First Name:- ");
                        String firstNameSearch = br.readLine();
                        con.searchByFname(firstNameSearch);
                    break;

                    case 4:
                        System.out.print("                Enter The First Name:- ");
                        String firstNameUpdate = br.readLine();
                        System.out.print("                Enter The Last Name:- ");
                        String lastNameUpdate = br.readLine();
                        System.out.print("                Enter The Roll Number:- ");
                        int rollNumberUpdate =Integer.parseInt(br.readLine());
                        System.out.print("                Enter The Address:- ");
                        String addressUpdate = br.readLine();
                        System.out.print("                Enter The Class Name:- ");
                        String classNameUpdate = br.readLine();
                        con.updateRecord(firstNameUpdate,lastNameUpdate,addressUpdate,classNameUpdate,rollNumberUpdate);
                    break;

                    case 5:
                        System.out.print("                Enter The Roll Number:- ");
                        int rollNumberDelete =Integer.parseInt(br.readLine());
                        con.deleteRecord(rollNumberDelete);
                    break;

                    case 6:
                        System.out.println("                Thanks For Using JDBC Application :)");
                    break;

                    default:
                        System.out.println("                Invalid Input. Try Again.....");

                }
            }while(choice!=6);
        }catch(Exception e){
            System.out.println(e);
        }finally{
            con.close();
        }
    }
}