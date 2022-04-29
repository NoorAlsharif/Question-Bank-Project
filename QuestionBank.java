
/*
 * Noor Hashem Al Ghalib Al Sharif 
 * 1725009
 * IBR
 * CPCS 204
 * nalsharif0045@stu.kau.edu.sa
 */
package questionbank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author HP z600
 */
public class QuestionBank {

    /**
     * @param args the command line arguments
     */
    //BST of questions
    private static CourseQBank questionsBank = new CourseQBank("CPCS204");
    //array of linkedLists of Quizzes
    private static Quiz[] quizzes;
    private static Question question;
    private static Quiz quiz;

    public static void main(String[] args) throws FileNotFoundException {

        File inputFile = new File("QuestionBank.in");

        if (!inputFile.exists()) {
            System.out.println("input file does not exist.");
        } else {

            Scanner input = new Scanner(inputFile);

            PrintWriter output = new PrintWriter("QuestionBank.out");

            int num = input.nextInt();

            //create array and fill with values
            quizzes = new Quiz[input.nextInt()];

            /// read text of command to call method to execute
            String commandText = "";
            do {

                commandText = input.next();

                if (commandText.equals("ADD")) {
                    output.print("ADD Command:");
                    QuestionAdd(output, input);
                    output.println("");
                } else if (commandText.equals("BUILDQUIZ")) {
                    output.println("BUILDQUIZ Command:");
                    QuizAdd(input, output);
                    output.println("");
                } else if (commandText.equals("DELETE")) {
                    output.print("DELETE Command:");
                    QuestionDelete(output, input);
                    output.println("");

                } else if (commandText.equals("PRINTQUIZ")) {
                    output.println("PRINTQUIZ Command:");
                    printQuiz(output, input);
                    output.println("");

                } else if (commandText.equals("PRINTBANKQUESTIONS")) {
                    output.println("PRINTBANKQUESTIONS Command:");
                    printBST_Questions(output, input);
                    output.println("");
                }

            } while (input.hasNext());
            output.close();

        }

    }

    private static void QuestionAdd(PrintWriter output, Scanner input) {

        int num = input.nextInt();
        int topicNum = input.nextInt();
        int difficultNum = input.nextInt();
        int marks = input.nextInt();
        int answersNum = input.nextInt();
        String description = input.nextLine();

        QBSTNode QuestionNode = questionsBank.find_question_by_id(num);
        //this num is found in the Tree it can't be added again
        if (QuestionNode != null) {
            output.println("Question ID: " + num + ", [" + description + "]  - is already part of the question bank.");

        } else {

            //create question object and set data 
            question = new Question();
            question.setQID(num);
            question.setTopicNo(topicNum);
            question.setDifficultyLevel(difficultNum);
            question.setAssignedMarks(marks);
            question.setNoAnswers(answersNum);
            question.setDescription(description);

            for (int i = 0; i < question.getNoAnswers(); i++) {

                Answer anser = new Answer();
                anser.setAnsCode(input.next().charAt(0));
                anser.setCorrect(input.nextBoolean());
                anser.setAnsDescription(input.nextLine());

                question.addAnswer(anser, i);
            }

            //add question object to the BST 
            QBSTNode QuestionNode1 = new QBSTNode(question);
            questionsBank.insert2(QuestionNode1);
            output.println("Question ID: " + question.getQID() + " has been added to " + questionsBank.getCourseCode() + " bank successfully.");

        }

    }

    private static void QuestionDelete(PrintWriter output, Scanner input) {
        int id = input.nextInt();

        QBSTNode QuestionNode1 = questionsBank.find_question_by_id(id);
        if (QuestionNode1 == null) {// this id is not found in any node of BST
            output.println("\tCannot Perform DELETE Command: " + id + " was not found in course question bank.");

        } else {
            output.println("	" + id + " has been removed from course question bank.");
            //delete QuestionNode object from the BST
            questionsBank.delete(QuestionNode1);

            //find quiz index of deleteing id 
            for (int j = 0; j < quizzes.length; j++) {

                quiz = quizzes[j];
                //delete question object from the LL
                quiz.delete_question_id(id);
            }

        }

    }

    private static void QuizAdd(Scanner input, PrintWriter output) {

        quiz = new Quiz();
        quiz.setQuizID(input.nextInt());

        int num = input.nextInt();

        for (int i = 0; i < num; i++) {
            int id = input.nextInt();
            QBSTNode result = questionsBank.find_question_by_id(id);
            if (result != null) {
                question = result.getQuestion();
                QLLNode newNode = new QLLNode();
                newNode.setQuestion(question);

                quiz.addNode(newNode);

                output.println("	question id " + id + " is added to quiz");
            }

        }

        int quizIndex = -1;
        //find quiz index of adding in the first empty index in the array
        for (int i = 0; i < quizzes.length; i++) {
            if (quizzes[i] == null) {
                quizIndex = i;
                break;
            }
        }

        quizzes[quizIndex] = quiz;

    }

    private static void printQuiz(PrintWriter output, Scanner input) {
        quiz = null;
        int id = input.nextInt();

        //find quiz index of printing id 
        for (int i = 0; i < quizzes.length; i++) {
            if (quizzes[i].getQuizID() == id) {
                quiz = quizzes[i];
                break;

            }

        }

        if (quiz != null) {
            output.println("Quiz no.: " + id);
            // print details data of LL
            quiz.printData(output);
            output.println();
        }

    }

    private static void printBST_Questions(PrintWriter output, Scanner input) {
        String name = input.next();
        if (questionsBank.getCourseCode().equals(name)) {

            int mode = input.nextInt();
            int criterionTopic = input.nextInt();

            // print details data of BST
            questionsBank.printDataBST(mode, criterionTopic, output);
        }

    }
}
