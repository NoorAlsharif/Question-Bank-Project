
/*
 * Noor Hashem Al Ghalib Al Sharif 
 * 1725009
 * IBR
 * CPCS 204
 * nalsharif0045@stu.kau.edu.sa
 */

package questionbank;

import java.io.PrintWriter;

/**
 *
 * @author HP z600
 */
public class Answer {

    private char ansCode;
    private String ansDescription;
    private boolean correct;

    public Answer() {
    }

    public void setAnsCode(char c) {
        ansCode = c;
    }

    public char getAnsCode() {
        return ansCode;
    }

    public void setAnsDescription(String desc) {
        ansDescription = desc;
    }

    public String getAnsDescription() {
        return ansDescription;
    }

    public void setCorrect(boolean c) {
        correct = c;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void print_answers(PrintWriter output) {
        //Fill in the method implementation
        
        //USING PRINTF TO ARANGE OUTPUTFILE DATA:
        output.printf("%-15s|%-10s|%-15s|%-40s|%-10s|%-40s|%-10b%n", "", "", "", "", ansCode, ansDescription, correct);
    }
}
