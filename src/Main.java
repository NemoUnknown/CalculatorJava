import java.util.Arrays;
import java.util.Scanner;
public class Main {
    static char[] Operations = new char[]{'+', '-', '/', '*'};

    static String[] Roman = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"};
    static int[] Arabic = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        try {
            System.out.println(calc(input));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean isArabic(String input, int symbolIndex) {
        boolean isFirstNumberDigit = false;
        String firstNumber = input.substring(0, symbolIndex);

        for (int i = 0; i < firstNumber.length(); i++) {
            isFirstNumberDigit = Character.isDigit(firstNumber.charAt(i));
            if (isFirstNumberDigit == false) return false;
        }

        boolean isSecondNumberDigit = false;
        String secondNumber = input.substring(symbolIndex + 1);

        for (int i = 0; i < secondNumber.length(); i++) {
            isSecondNumberDigit = Character.isDigit(secondNumber.charAt(i));
            if (isSecondNumberDigit == false) return false;
        }
        return true;
    }

    public static int getResult(int firstNumber, int secondNumber, char symbol) {
        int result = Integer.MAX_VALUE;

        switch (symbol) {
            case '+' -> result = firstNumber + secondNumber;
            case '-' -> result = firstNumber - secondNumber;
            case '/' -> result = firstNumber / secondNumber;
            case '*' -> result = firstNumber * secondNumber;
            default -> System.out.println("нет такого арифметического действия");
        }
        return result;
    }

    public static boolean checkTheNumber(int number) {
        return (number > 0 && number <= 10);
    }

    public static int calculateArabicNumbers(String input, int symbolIndex) throws Exception {
        int firstNumber = Integer.parseInt(input.substring(0, symbolIndex));
        if (checkTheNumber(firstNumber) == false) throw new Exception("Число должно быть от 1 до 10");

        int secondNumber = Integer.parseInt(input.substring(symbolIndex + 1));
        if (checkTheNumber(secondNumber) == false) throw new Exception("Число должно быть от 1 до 10");

        char symbol = input.charAt(symbolIndex);

        return getResult(firstNumber, secondNumber, symbol);
    }

    public static int indexAt(String[] array, String symbol){
        for (int i = 0; i < array.length; i++){
            if (array[i].equals(symbol)){
                return i;
            }
        }
        return -1;
    }

    public static int romanToArabic(String romanNumber) throws Exception{
        int arabic = 0;

        for (int i = 0; i < romanNumber.length(); i++){
            if (indexAt(Roman, Character.toString(romanNumber.charAt(i))) < 0) throw new Exception("Введено не римское число");

            int firstSymbol = Arabic[indexAt(Roman, Character.toString(romanNumber.charAt(i)))];

            if (i + 1 < romanNumber.length()) {
                int secondSymbol = Arabic[indexAt(Roman, Character.toString(romanNumber.charAt(i + 1)))];

                if (firstSymbol >= secondSymbol) {
                    arabic += firstSymbol;
                } else {
                    arabic += secondSymbol - firstSymbol;
                    i++;
                }
            }
            else{
                    arabic += firstSymbol;
            }
        }
        if (arabic == 0){
            throw new Exception("Введено не римское число");
        }
        return arabic;
    }

    public static String arabicToRoman(int number){
        String[] reverseRoman = new String[Roman.length];
        for (int i = 0; i < reverseRoman.length; i++){
            reverseRoman[i] = Roman[Roman.length - i - 1];
        }

        int[] reverseArabic = new int[Arabic.length];
        for (int i = 0; i < reverseArabic.length; i++){
            reverseArabic[i] = Arabic[Arabic.length - i - 1];
        }

        StringBuilder romanNumber = new StringBuilder("");
        for (int i = 0; i < reverseArabic.length; i++){
            while (number >= reverseArabic[i]) {
            number -= reverseArabic[i];
            romanNumber.append(reverseRoman[i]);
            }
        }
        return romanNumber.toString();
    }
    public static String calculateRomanNumbers(String input, int symbolIndex) throws Exception {
        String firstRomanNumber = input.substring(0, symbolIndex).toUpperCase();
        String secondRomanNumber = input.substring(symbolIndex + 1).toUpperCase();
        char symbol = input.charAt(symbolIndex);

        int firstArabicNumber = romanToArabic(firstRomanNumber);
        if (checkTheNumber(firstArabicNumber) == false) throw new Exception("Число должно быть от I до X");

        int secondArabicNumber = romanToArabic(secondRomanNumber);
        if (checkTheNumber(secondArabicNumber) == false) throw new Exception("Число должно быть от I до X");

        int result = getResult(firstArabicNumber, secondArabicNumber, symbol);

        if (result <= 0) throw new Exception("Римские цифры не могут быть меньше I");
        return arabicToRoman(result);
    }

    public static String calc(String input) throws Exception {
        input = input.trim();
        input = input.replaceAll("\\s", "");

        int symbolIndex = -1;
        int count = 0;

        for (int i = 0; i < Operations.length; i++) {

            if (input.indexOf(Operations[i]) > 0) {
                symbolIndex = input.indexOf(Operations[i]);
                count++;

                if (input.indexOf(Operations[i]) != input.lastIndexOf(Operations[i])) throw new Exception("Калькультор выполнняет только \"+, -, *, /\"");
            }
        }
        if (count > 1) throw new Exception("Калькультор выполнняет только \"+, -, *, /\"");
        if (symbolIndex < 0) throw new Exception("Калькультор выполнняет только \"+, -, *, /\"");

        if (isArabic(input, symbolIndex))
            return Integer.toString(calculateArabicNumbers(input, symbolIndex));

        return calculateRomanNumbers(input, symbolIndex);
    }
}