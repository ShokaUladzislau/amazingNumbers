package numbers;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Amazing Numbers!\n" +
                "\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");

        Scanner sc = new Scanner(System.in);
        String[] input;

        while (true) {
            System.out.print("Enter a request: > ");
            String line = sc.nextLine();
            System.out.println();

            if (line.trim().isEmpty()) {
                System.out.println("Nothing entered");
                continue;
            } else {
                input = line.split("\\s+");
            }

            switch (input.length) {
                case 1: {
                    if (isNumeric(input[0])) showFullProperties(input);
                    break;
                }
                case 2: {
                    showShortPropertiesForList(input);
                    break;
                }
                default: {
                    showNumbersWithProperties(input);
                    break;
                }
            }
        }
    }

    public static boolean isNumeric(String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException nfe) {
            System.out.println("The first parameter should be a natural number or zero.\n");
            return false;
        }
        return true;
    }

    private static void showNumbersWithProperties(String[] input) {
        long firstNum = Long.parseLong(input[0]);
        long secondNum = Long.parseLong(input[1]);
        String[] properties = convertArrayToUpperCase(Arrays.copyOfRange(input, 2, input.length));

        if (checkNumber(firstNum, "The first") && checkNumber(secondNum, "The second")) {
            if (checkProperties(properties)) {
                showNumbersList(firstNum, secondNum, properties);
            }
        }
    }

    private static boolean checkProperties(String[] properties) {
        List<String> wrongProperties = new ArrayList<>();

        for (String property : properties) {
            if (!checkProperty(property)) {
                wrongProperties.add(property.toUpperCase(Locale.ROOT));
            }
            if (Arrays.asList(properties).contains("-" + property)) {
                showExclusiveRequestMessage(property, "-" + property);
                return false;
            }
        }


        boolean evenAndOdd = Arrays.asList(properties).contains("EVEN") && Arrays.asList(properties).contains("ODD");
        boolean sunnyAndSquare = Arrays.asList(properties).contains("SUNNY") && Arrays.asList(properties).contains("SQUARE");
        boolean spyAndDuck = Arrays.asList(properties).contains("SPY") && Arrays.asList(properties).contains("DUCK");
        boolean happyAndSad = Arrays.asList(properties).contains("HAPPY") && Arrays.asList(properties).contains("SAD");
        boolean notEvenAndNotOdd = Arrays.asList(properties).contains("-EVEN") && Arrays.asList(properties).contains("-ODD");
        boolean notSunnyAndNotSquare = Arrays.asList(properties).contains("-SUNNY") && Arrays.asList(properties).contains("-SQUARE");
        boolean notSpyAndNotDuck = Arrays.asList(properties).contains("-SPY") && Arrays.asList(properties).contains("-DUCK");
        boolean notHappyAndNotSad = Arrays.asList(properties).contains("-HAPPY") && Arrays.asList(properties).contains("-SAD");

        if (evenAndOdd) {
            showExclusiveRequestMessage("EVEN", "ODD");
        } else if (sunnyAndSquare) {
            showExclusiveRequestMessage("SUNNY", "SQUARE");
        } else if (spyAndDuck) {
            showExclusiveRequestMessage("SPY", "DUCK");
        } else if (happyAndSad) {
            showExclusiveRequestMessage("HAPPY", "SAD");
        } else if (notEvenAndNotOdd) {
            showExclusiveRequestMessage("-EVEN", "-ODD");
        } else if (notSunnyAndNotSquare) {
            showExclusiveRequestMessage("-SUNNY", "-SQUARE");
        } else if (notSpyAndNotDuck) {
            showExclusiveRequestMessage("-SPY", "-DUCK");
        } else if (notHappyAndNotSad) {
            showExclusiveRequestMessage("-HAPPY", "-SAD");
        } else if (wrongProperties.size() >= 2) {
            System.out.println("The properties " + wrongProperties + " are wrong.");
            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]\n");
        } else if (wrongProperties.size() == 1) {
            System.out.println("The property " + wrongProperties + " is wrong.");
            System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]\n");
        } else {
            return true;
        }
        wrongProperties.clear();
        return false;
    }

    private static void showExclusiveRequestMessage(String firstProperty, String secondProperty) {
        System.out.println("The request contains mutually exclusive properties: [" + firstProperty + ", " + secondProperty + "]\n" +
                "There are no numbers with these properties.");
    }

    private static String[] convertArrayToUpperCase(String[] properties) {
        for (int i = 0; i < properties.length; i++) {
            properties[i] = properties[i].toUpperCase();
        }
        return properties;
    }

    private static void showNumbersList(long firstNum, long secondNum, String[] properties) {
        int count = 0;
        List<Long> reminders = new ArrayList<>();

        while (count < secondNum) {

            for (String property : properties) {
                long number = findNumbers(firstNum, property);
                if (number != 0) {
                    reminders.add(number);
                }
            }

            if (reminders.size() == properties.length && reminders.stream().distinct().count() <= 1 ||
                    reminders.size() > properties.length && reminders.stream().distinct().count() <= 1 ||
                    reminders.size() == 1 && properties.length == 1) {
                showShortPropertiesForNumber(reminders.get(0));
                count++;
            }

            reminders.clear();
            firstNum++;
        }
    }

    private static long findNumbers(long firstNum, String property) {
        long reminder = 0;
        switch (property.toUpperCase(Locale.ROOT)) {
            case "EVEN":
            case "-ODD":
                if (isEven(firstNum)) reminder = firstNum;
                break;
            case "ODD":
            case "-EVEN":
                if (!isEven(firstNum)) reminder = firstNum;
                break;
            case "BUZZ":
                if (isBuzz(firstNum)) reminder = firstNum;
                break;
            case "DUCK":
                if (isDuck(String.valueOf(firstNum))) reminder = firstNum;
                break;
            case "PALINDROMIC":
                if (isPalindromic(firstNum)) reminder = firstNum;
                break;
            case "GAPFUL":
                if (isGapful(firstNum)) reminder = firstNum;
                break;
            case "SPY":
                if (isSpy(firstNum)) reminder = firstNum;
                break;
            case "SUNNY":
                if (isSunny(firstNum)) reminder = firstNum;
                break;
            case "SQUARE":
                if (isPerfectSquare(firstNum)) reminder = firstNum;
                break;
            case "JUMPING":
                if (isJumping(firstNum)) reminder = firstNum;
                break;
            case "HAPPY":
            case "-SAD":
                if (isHappy(firstNum)) reminder = firstNum;
                break;
            case "SAD":
            case "-HAPPY":
                if (!isHappy(firstNum)) reminder = firstNum;
                break;
            case "-BUZZ":
                if (!isBuzz(firstNum)) reminder = firstNum;
                break;
            case "-DUCK":
                if (!isDuck(String.valueOf(firstNum))) reminder = firstNum;
                break;
            case "-PALINDROMIC":
                if (!isPalindromic(firstNum)) reminder = firstNum;
                break;
            case "-GAPFUL":
                if (!isGapful(firstNum)) reminder = firstNum;
                break;
            case "-SPY":
                if (!isSpy(firstNum)) reminder = firstNum;
                break;
            case "-SUNNY":
                if (!isSunny(firstNum)) reminder = firstNum;
                break;
            case "-SQUARE":
                if (!isPerfectSquare(firstNum)) reminder = firstNum;
                break;
            case "-JUMPING":
                if (!isJumping(firstNum)) reminder = firstNum;
                break;

        }

        return reminder;
    }

    private static boolean checkProperty(String property) {
        String[] properties = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SQUARE", "SPY", "SUNNY", "JUMPING", "HAPPY", "SAD", "-EVEN", "-ODD", "-BUZZ", "-DUCK", "-PALINDROMIC", "-GAPFUL", "-SQUARE", "-SPY", "-SUNNY", "-JUMPING", "-HAPPY", "-SAD"};
        for (String element : properties) {
            if (Objects.equals(property.toUpperCase(Locale.ROOT), element)) {
                return true;
            }
        }
        return false;
    }

    private static void showShortPropertiesForList(String[] input) {
        long firstNum = Long.parseLong(input[0]);
        long secondNum = Long.parseLong(input[1]);

        if (checkNumber(firstNum, "The first") && checkNumber(secondNum, "The second")) {
            for (long number = firstNum; number < firstNum + secondNum; number++) {
                showShortPropertiesForNumber(number);
            }
            System.out.println();
        }
    }

    private static void showShortPropertiesForNumber(long number) {
        List<String> parameters = new ArrayList<>();

        if (isEven(number)) parameters.add("even");
        if (!isEven(number)) parameters.add("odd");
        if (isBuzz(number)) parameters.add("buzz");
        if (isDuck(String.valueOf(number))) parameters.add("duck");
        if (isPalindromic(number)) parameters.add("palindromic");
        if (isJumping(number)) parameters.add("jumping");
        if (isGapful(number)) parameters.add("gapful");
        if (isSpy(number)) parameters.add("spy");
        if (isPerfectSquare(number)) parameters.add("square");
        if (isSunny(number)) parameters.add("sunny");
        if (isHappy(number)) parameters.add("happy");
        if (!isHappy(number)) parameters.add("sad");

        System.out.println(number + " is " + parameters.toString().replace("[", "").replace("]", ""));
    }

    private static boolean checkNumber(long number, String parameter) {
        if (number == 0) {
            System.out.println("Goodbye!");
            System.exit(0);
            return false;
        } else if (number < 0) {
            System.out.println(parameter + " parameter should be a natural number or zero.\n");
            return false;
        }
        return true;
    }

    private static void showFullProperties(String[] input) {
        long number = Long.parseLong(input[0]);

        if (checkNumber(number, "The first")) {
            System.out.println("Properties of " + number);
            System.out.println("buzz: " + isBuzz(number));
            System.out.println("duck: " + isDuck(String.valueOf(number)));
            System.out.println("palindromic: " + isPalindromic(number));
            System.out.println("gapful: " + isGapful(number));
            System.out.println("spy: " + isSpy(number));
            System.out.println("square: " + isPerfectSquare(number));
            System.out.println("sunny: " + isSunny(number));
            System.out.println("jumping: " + isJumping(number));
            System.out.println("happy: " + isHappy(number));
            System.out.println("sad: " + !isHappy(number));
            System.out.println("even: " + isEven(number));
            System.out.println("odd: " + !isEven(number) + "\n");
        }
    }

    static boolean isHappy(long number) {
        long slow, fast;
        slow = fast = number;
        do {
            slow = numSquareSum(slow);
            fast = numSquareSum(numSquareSum(fast));
        }
        while (slow != fast);
        return (slow == 1);
    }

    static long numSquareSum(long n) {
        int squareSum = 0;
        while (n != 0) {
            squareSum += (n % 10) * (n % 10);
            n /= 10;
        }
        return squareSum;
    }

    static boolean isJumping(long number) {
        boolean flag = true;
        while (number != 0) {
            long digit1 = number % 10;
            number = number / 10;
            if (number != 0) {
                long digit2 = number % 10;
                if (Math.abs(digit1 - digit2) != 1) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    static boolean isPerfectSquare(double x) {
        double sr = Math.sqrt(x);
        return ((sr - Math.floor(sr)) == 0);
    }

    static boolean isSunny(long number) {
        return (isPerfectSquare(number + 1));
    }

    private static boolean isSpy(long number) {
        int digit, sum = 0, product = 1;
        while (number > 0) {
            digit = (int) (number % 10);
            sum += digit;
            product *= digit;
            number = number / 10;
        }
        return sum == product;
    }

    private static boolean isEven(long number) {
        return number % 2 == 0;
    }

    private static boolean isBuzz(long number) {
        return number % 7 == 0 || number % 10 == 7;
    }

    public static boolean isDuck(String inputNumber) {
        int length = inputNumber.length();
        int countZero = 0;
        char ch;

        for (int i = 0; i < length; i++) {
            ch = inputNumber.charAt(i);
            if (ch == '0')
                countZero++;
        }

        char beginWithZero = inputNumber.charAt(0);
        return beginWithZero != '0' && countZero > 0;
    }

    private static boolean isPalindromic(long number) {
        StringBuilder str = new StringBuilder();
        str.append(number);
        return Long.toString(number).equals(str.reverse().toString());
    }

    private static boolean isGapful(long number) {
        if (number <= 99) {
            return false;
        } else {
            long value = firstDigit(number) * 10L + lastDigit(number);
            return number % value == 0;
        }
    }

    public static int firstDigit(long number) {
        int digit = (int) Math.log10(number);
        return (int) (number / Math.pow(10, digit));
    }

    public static int lastDigit(long number) {
        return (int) (number % 10);
    }
}