package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validate {

    private static Scanner scanner = new Scanner(System.in);

    public static int getInteger(String mess, String error, int min, int max) {
        while (true) {
            try {
                System.out.print(mess);
                String input = scanner.nextLine();

                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.err.println("Bạn phải nhập trong khoảng " + min + "-" + max);
                }
            } catch (Exception e) {
                System.err.println(error);
            }
        }
    }

    public static double getDouble(String mess, String error, double min, double max) {
        while (true) {
            try {
                System.out.print(mess);
                String input = scanner.nextLine();

                double number = Double.parseDouble(input);
                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.err.println("Bạn phải nhập trong khoảng " + min + "-" + max);
                }

            } catch (Exception e) {
                System.err.println(error);
            }
        }
    }

    public static float getFloat(String mess, String error, float min, float max) {
        while (true) {
            try {
                System.out.print(mess);
                String input = scanner.nextLine();

                float number = Float.parseFloat(input);
                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.err.println("Bạn phải nhập trong khoảng " + min + "-" + max);
                }
            } catch (Exception e) {
                System.err.println(error);
            }

        }
    }

    public static String getString(String mess, String error, String regex) {
        while (true) {
            System.out.print(mess);
            String input = scanner.nextLine().trim();

            if (input.matches(regex)) {
                return input;
            } else {
                System.out.println(error);
             
           
            }
        }
    }

    public static boolean getBoolean(String mess, String error) {
        String input = getString(mess, error, "[ynYN]");
        return input.equalsIgnoreCase("y");
    }

    public static int getInteger(String mess, String error, int min, int max, int defaultValue) {
        while (true) {
            try {
                System.out.print(mess);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    return defaultValue;
                }

                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.err.println("Bạn phải nhập trong khoảng " + min + "-" + max);
                }
            } catch (NumberFormatException e) {
                System.err.println(error);
            }
        }
    }

    public static double getDouble(String mess, String error, double min, double max, double defaultValue) {
        while (true) {
            try {
                System.out.print(mess);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    return defaultValue;
                }

                double number = Double.parseDouble(input);
                if (number >= min && number <= max) {
                    return number;
                } else {
                    System.err.println("Bạn phải nhập trong khoảng " + min + "-" + max);
                }
            } catch (NumberFormatException e) {
                System.err.println(error);
            }
        }
    }
public static LocalDate getDate(String message, String error) {
        // Change the date format to dd-MM-yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                // Try to parse the input string into a LocalDate using the new formatter
                LocalDate date = LocalDate.parse(input, formatter);
                return date; // Return the valid date
            } catch (DateTimeParseException e) {
                // If the input cannot be parsed, show the error message
                System.out.println(error);
            }
        }
    }
}
