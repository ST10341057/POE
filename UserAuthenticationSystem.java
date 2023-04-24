import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class UserAuthenticationSystem {

    public static void main(String[] args){

        Scanner prompt = new Scanner(System.in);

        Line();
        System.out.println("Select an option\n1. Register\n2. Login");
        System.out.println("");
        System.out.print("Enter Option Here:  ");
        int option = prompt.nextInt();
        Line();
        switch (option){
            case 1:
                System.out.println("Hi, Welcome to the registration page");
                registerUser();
                break;

            case 2:
                System.out.println("Hi, Welcome to the Login page");
                while (true){
                    boolean loginBoolean = loginUser();
                    if (loginBoolean)
                        System.out.println(returnLoginStatus(loginBoolean, fullName));
                    break;
                }
                break;
            default:
                System.out.println("Program Ended");
                break;
        }
        Line();

    }

    // This method ensures that any username contains an underscore (_) and is no more than 5 characters
    public static boolean checkUserName(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    // This method ensures that passwords meet the minimum password complexity requirements
    public static boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*")
                && password.matches(".*\\d.*") && password.matches(".*[^a-zA-Z0-9].*");
    }

    public static String registerUser (){

        Scanner prompt = new Scanner(System.in);
        String username;
        String password;
        String firstName;
        String lastName;
        boolean hasUserName;

        //Prompting user to create a username and password
       

            System.out.println("Create a username and Password");
            System.out.println("Your Username must have an underscore (_) and no more than 5 characters long");
            System.out.println("Your Password must be at least 8 characters long");
            Line();
            System.out.print("Username: ");
            username = prompt.nextLine();

            //checking if user already exists
            int[] returnVars = stringInText(username, "usernames.txt", 0);
            if (returnVars[0] == 0)
            return "Username already exists";



            if (!checkUserName(username))
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length .";

            
           
            Line();
            //prompting user to create password 
            System.out.print("Password: ");
            password = prompt.nextLine();

                if (!checkPasswordComplexity(password)) {
                    return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
                }
                
            // Writing to the files 
            try {
                FileWriter usernameWriter = new FileWriter("usernames.txt", true);
                usernameWriter.write(userName + "\n");
                usernameWriter.close();
                FileWriter passwordWriter = new FileWriter("passwords.txt", true);
                passwordWriter.write(password + "\n");
                passwordWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            
            return "Registration successful";
    }

    public static boolean loginUser(){

        Scanner prompt = new Scanner(System.in);
        String username;
        String password;
        
        System.out.print("Username: ");
        username = prompt.nextLine();
        int[] returnVarsUserName = stringInText(username, "usernames.txt", 0);
        int userNameIndex = returnVarsUserName[1];
        if (returnVarsUserName[0] == 1){
            System.out.println("Username not found");
            prompt.close();
            return false;
        }

        System.out.print("Password: ");
        password = prompt.nextLine();
        prompt.close();
        int[] returnVarsPassword = stringInText(password, "passwords.txt", userNameIndex);
        if (returnVarsPassword[0] == 1){
            System.out.println("False");
            return false;
        }
        
        System.out.println("True");      
        return true;
    }
        
    public static int[] stringInText(String text, String filePath, int index){
        String currentLine;
        int position = 0;
        if (index == 0){
            try {
                File file = new File(filePath);
                Scanner readFile = new Scanner(file);
                while (readFile.hasNextLine()) {
                    position++;
                    currentLine = readFile.nextLine();
                    if (text.equals(currentLine)){
                        int[] returnVars = {0, position};
                        return returnVars;
                    }
                }
                readFile.close();
            } 
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        else{
            try {
                File file = new File(filePath);
                Scanner readFile = new Scanner(file);
                for (int i = 0; i < index; i++) {
                    currentLine = readFile.nextLine();
                    if (i == (index - 1)){
                        if (text.equals(currentLine)){
                            int[] returnVars = {0, position};
                            return returnVars;
                        }
                    }
                }
                readFile.close();
            } 
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        int[] returnVars = {1, position};
        return returnVars;
    }

    public static String returnLoginStatus(boolean loginBoolean, String fullName){
        if (loginBoolean)
            return "Welcome " + Fullname + " it is great to see you again.";
        return "Username or password incorrect, please try again";
    }


    public static String getNames(){
        Scanner prompt = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = prompt.nextLine();

        System.out.print("Enter second name: ");
        String secondName = prompt.nextLine();
        return firstName + " " + secondName;
    }
        
        


    

    
    public static String Line(){
        String line = "==========================================";
        return line;
    }
}
