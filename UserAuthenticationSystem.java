import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class UserAuthenticationSystem {

    public static String namesPath = "names.txt";
    public static String usernamesPath = "usernames.txt";
    public static String passwordsPath = "passwords.txt";
    public static int usernameIndex;

    public static void main(String[] args){
        //Creating text file to store Usenames, Passwords and Fullnames
        createFile(namesPath);
        createFile(passwordsPath);
        createFile(usernamesPath);

        //creating a scanner to prompt user for input
        Scanner prompt = new Scanner(System.in);

        //Prompting user to login or register for a new account
        Line();
        System.out.println("Select an option\n1. Register\n2. Login");
        System.out.println("");
        System.out.print("Enter Option Here: ");
        int option = prompt.nextInt();
        Line();
        switch (option){
            case 1:
                Line();
                System.out.println("Hi, Welcome to the registration page ^___^");
                Line();
                String FullName = getFullName();
                while (true){
                    String registrationStatus = registerUser();
                    System.out.println("\n" + registrationStatus);
                    if (registrationStatus.equals("Registration successful ^_~"))
                        break;
                }
                writeToFile(FullName, namesPath);
                break;

            case 2:
                Line();
                System.out.println("Hi, Welcome to the Login page ^___^");
                Line();
                while (true){
                    boolean loginBoolean = loginUser();
                    System.out.println(returnLoginStatus(loginBoolean));
                    
                    if (loginBoolean)
                        
                    break;
                }
                break;
            default:
                System.out.println("Program Ended");
                break;
        }
        Line();

    }

    
    

    public static void writeToFile(String text, String filePath){
        try {
            FileWriter textWriter = new FileWriter(filePath, true);
            textWriter.write(text + "\n");
            textWriter.close();
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void createFile(String filePath){
        try {
            File myObj = new File(filePath);
            myObj.createNewFile();
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
       
            Line();
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
            return "╯︿╰ Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length .";

        
            //prompting user to create password 
            System.out.print("Password: ");
            password = prompt.nextLine();

                if (!checkPasswordComplexity(password)) {
                    return "ಥ_ಥ Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
                }
                
            // Writing to the files 
            try {
                FileWriter usernameWriter = new FileWriter("usernames.txt", true);
                usernameWriter.write(username + "\n");
                usernameWriter.close();
                FileWriter passwordWriter = new FileWriter("passwords.txt", true);
                passwordWriter.write(password + "\n");
                passwordWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            
            return "Registration successful ^_~";
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
            
            System.out.println("Username not found :(");
            prompt.close();
            return false;
        }

        System.out.print("Password: ");
        password = prompt.nextLine();
        prompt.close();
        int[] returnVarsPassword = stringInText(password, "passwords.txt", userNameIndex);
        if (returnVarsPassword[0] == 1){
        Line();
            return false;
        }
        
        Line();      
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

    public static String returnLoginStatus(boolean loginBoolean){
        if (!loginBoolean)
        return "Username or password incorrect, please try again :(\n";

    // Finding the names that the username is linked to
    String fullName = "";
    try {
        File file = new File(namesPath);
        Scanner readFile = new Scanner(file);
        
        for (int i = 0; i < usernameIndex; i++) 
            fullName = readFile.nextLine();
        readFile.close();
    } 
    catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    
    return "Welcome back," + fullName + " it is great to see you again(^__^)";
        
        
        
        
    }

    


    public static String getFullName(){
        Scanner prompt = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = prompt.nextLine();

        System.out.print("Enter second name: ");
        String secondName = prompt.nextLine();
        return firstName + " " + secondName;
    }
        
    
    public static void Line(){
        String line = "===============================================================";
        System.out.println(line);
    }
}
