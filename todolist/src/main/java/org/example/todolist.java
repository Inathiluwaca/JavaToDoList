package org.example;
import java.util.Scanner;
import  java.util.ArrayList;

public class todolist {

    static Scanner input= new Scanner(System.in);

    static void Welcome(){
        System.out.println("Welcome to  MyToDoList!\n");
    }
    static void Menu(){
        System.out.println("Menu:\n1.View MyToDoList\n2.Add item to MyToDoList\n3.Complete Task\n4.Remove task\n5.Exit\n");
    }


    static String command(){
        while (true){
            System.out.println("Please enter the number corresponding to your chosen option:");
            String user_number = input.nextLine();
            if (validate_command(user_number)){
                System.out.println("You have chosen option "+user_number);
                return user_number;
            }else{
                System.out.println("Invalid option");
            }
        }
    }

    static boolean validate_command(String user_number) {
        String[] choices = {"1","2","3","4","5"};
        for (int i = 0; i< choices.length; i++){
            if (choices[i].equals(user_number)){
                return true;
            }
        }return false;
    }

    static void user_remove(ArrayList<String> todo){
        while (true){
            System.out.println("Insert the number of the item you want to remove. Insert quit to stop.");
            String user_remove = input.nextLine();
            boolean num= false;
            if (user_remove.equals("quit")){
                System.out.println("Back to Main menu");
                break;
            }else{
//                this is a rege ex that checks of user input is  a number
                try {
                    if (user_remove.matches("\\d+")) {
//                   this changes the user input which is a string to a number
                        int userstring = Integer.parseInt(user_remove);

//                    array lists use remove in indexes
                        int removedindex = userstring - 1;
                        todo.remove(removedindex);
                        System.out.println("Item is removed");
                        list_ToDo(todo);

                    } else {
                        System.out.println("Invalid input.");
                    }
                } catch (IndexOutOfBoundsException e){
                    System.out.println("Invalid input.");
                }
            }


        }

    }
    static void completed(ArrayList<String> todo){
        while (true) {
            System.out.println("Insert number of item you have completed.Insert quit to stop.");
            String user_done = input.nextLine();

            if (user_done.equals("quit")) {
                System.out.println("Back to Main menu");
                break;

            }
            else {
                try {
                    if (user_done.matches("\\d+")) {
                        int userstring = Integer.parseInt(user_done);
                        int array_index= userstring-1;
                        if ((array_index >= 0 && array_index < todo.size())){
                            String completeString= todo.get(array_index)+ " (complete)";
                            todo.set(array_index,completeString);
                            System.out.println("Item marked as complete.");
                            list_ToDo(todo);}
                    }else {
                        System.out.println("Invalid input.");
                    }
                }catch (IndexOutOfBoundsException e){
                    System.out.println("Invalid input.");
          }

            }
        }
    }


    static void user_add(ArrayList<String> todo){
        while (true) {
            System.out.println("What do you want to add on MyToDoList?Insert quit to stop.");
            String user_add = input.nextLine();
            if(user_add.equals("quit")){
                System.out.println("Goodbye");
                break;
            }else{
                todo.add(user_add);
                System.out.println("Item added!");
                list_ToDo(todo);
            }
        }

    }
    static  void list_ToDo ( ArrayList<String> todo){
        System.out.println("TO DO LIST:");
        for (int i = 0; i< todo.size() ;i++ ){
            System.out.println(i+1+"."+ todo.get(i));
        }
    }

    public static void main(String[]args){
        ArrayList<String> todo = new ArrayList<>();
        todo.add("30 minute workout");
        todo.add("Go buy milk.");
        Welcome();
        while (true) {

            Menu();
            String user_num = command();
            if (user_num.equals("1")) {
                list_ToDo(todo);
                System.out.println("\nBack to main menu\n");
            }
            if (user_num.equals("5")) {
                System.out.println("Goodbye");
                break;

            } else if (user_num.equals("2")) {
                user_add(todo);
            }else if (user_num.equals("3")){
                completed(todo);
            }
            else  if (user_num.equals("4")){
                user_remove(todo);
            }

        }
    }

}
