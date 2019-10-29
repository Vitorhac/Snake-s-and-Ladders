package org.underdogs.gloriagame;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.PasswordInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

public class MenuHandler {
    /*
    public static void main(String[] args) {

        Prompt prompt = new Prompt(System.in, System.out);

        String[] options = {"Login", "Rules", "Play", "Quit"};

        while (true) {

            System.out.println("     /^^    /^^^^        /^^^^       /^^^^          /^^^^^          /^               /^^^^   /^^          /^^^^     /^^^^^^^    /^^      /^       \n" +
                    "     /^^  /^^    /^^   /^    /^^   /^^    /^^       /^^   /^^      /^ ^^           /^    /^^ /^^        /^^    /^^  /^^    /^^  /^^     /^ ^^     \n" +
                    "     /^^/^^        /^^/^^        /^^        /^^     /^^    /^^    /^  /^^         /^^        /^^      /^^        /^^/^^    /^^  /^^    /^  /^^    \n" +
                    "     /^^/^^        /^^/^^        /^^        /^^     /^^    /^^   /^^   /^^        /^^        /^^      /^^        /^^/^ /^^      /^^   /^^   /^^   \n" +
                    "     /^^/^^        /^^/^^   /^^^^/^^        /^^     /^^    /^^  /^^^^^^ /^^       /^^   /^^^^/^^      /^^        /^^/^^  /^^    /^^  /^^^^^^ /^^  \n" +
                    "/^   /^^  /^^     /^^  /^^    /^   /^^     /^^      /^^   /^^  /^^       /^^       /^^    /^ /^^        /^^     /^^ /^^    /^^  /^^ /^^       /^^ \n" +
                    " /^^^^      /^^^^       /^^^^^       /^^^^          /^^^^^    /^^         /^^       /^^^^^   /^^^^^^^^    /^^^^     /^^      /^^/^^/^^         /^^\n" +
                    "                                                                                                                                                  ");
            MenuInputScanner scanner = new MenuInputScanner(options);
            scanner.setMessage("Welcome to the jungle!!");
            int answer = prompt.getUserInput(scanner);
            System.out.println("User wants to " + options[answer - 1]);
            while (true) {
                if (answer == 1) {
                    StringInputScanner question = new StringInputScanner();
                    question.setMessage("What is your name?\n");
                    String name = prompt.getUserInput(question);
                    PasswordInputScanner question2 = new PasswordInputScanner();
                    question2.setMessage("What's your password?\n");
                    String pass = prompt.getUserInput(question2);
                    System.out.println("\nLogin Successful!\n");
                    StringInputScanner welcome = new StringInputScanner();
                    welcome.setMessage(name + " are you ready?");
                    prompt.getUserInput(welcome);
                }
                if (answer == 2) {
                    StringInputScanner question = new StringInputScanner();
                    question.setMessage("Regras do jogo:\n" + "Press r for roll the dice\n" + "With 64 points wins\n");
                    String rules = prompt.getUserInput(question);
                }
               /*if(answer == 3){
                   start();
               }
            }
        }
    }
    */
}


