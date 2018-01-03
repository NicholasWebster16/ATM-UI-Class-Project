package atm_application.exception_handling;

import atm_application.Application;
import atm_application.data_model.Account;

import java.util.ArrayList;

/******************************************************
 * Class CheckInput:
 * Usage: This class is uses to check user input against
 * a set of different common input errors.
 * Details: Most of the actual checking methods are
 * private, this is to encourage the programming of
 * sets of checks and avoid one-off checks. By checking
 * in sets it is possible to create a list off errors
 * to alert. This means that the user gets detailed
 * information about all errors with their input. If the
 * user enters -35 for withdraw the error could contain
 * as many as three messages:
 *  - Input can't be negative
 *  - Input not in increment of 20
 *  - Insufficient funds in account.
 *
 *  NOTE:
 *  By including the boolean statement throwErrorOnFind
 *  it is possible to customize the error checks to allow
 *  for unique breaking points. example:
 *  If the input contains letters not numbers it isn't
 *  necessary to run the number checks. But if the number
 *  is negative we still want to check for increments
 *  or sufficient funds.
 ******************************************************/
public abstract class CheckInput {
    // Array of error messages to output upon exception throw.
    private static ArrayList<String> errors = new ArrayList<>();
    // Runs required checks to ensure the input is a user number.
    public static boolean userNumberInputCheck (String input) throws InvalidInputException{
        errors.clear();
        boolean userLogonValid = true;

        emptyInputCheck(input, true);
        numberOnlyCheck(input, true, true);

        int intInput = Integer.parseInt(input);
        negativeNumberCheck(intInput, true);
        existingUserCheck(intInput, true);

        return  userLogonValid;
    }
    // Checks to ensure that the value is valid for a deposit.
    public static boolean depositInputCheck(String input) throws  InvalidInputException{
        errors.clear();
        boolean depositValid = true;

        emptyInputCheck(input, true);
        numberOnlyCheck(input, false, true);
        double doubleInput = Double.parseDouble(input);
        negativeNumberCheck(doubleInput, true);

        return depositValid;
    }
    // Checks to ensure withdraw input is valid
    public static boolean withdrawInputCheck(String input, Account withdrawAccount) throws InvalidInputException{
        errors.clear();

        boolean isValidWithdrawAmount = true;
        emptyInputCheck(input, true);
        numberOnlyCheck(input, true,true);

        int intInput = Integer.parseInt(input);

        boolean isNegative = negativeNumberCheck(intInput, false);
        boolean isDivisibleBy20 = numberIsDivisible(intInput, 20, false);
        boolean sufficientFunds = sufficientFundsCheck(intInput, withdrawAccount, false);

        if(isNegative || !isDivisibleBy20 || !sufficientFunds){
            throw new InvalidInputException(errors);
        }
        return isValidWithdrawAmount;
    }
    // Checks to ensure the transfer user number is valid
    public static boolean transferUserInputCheck (String input) throws InvalidInputException{
        errors.clear();
        boolean userLogonValid = true;

        emptyInputCheck(input, true);
        numberOnlyCheck(input, true, true);

        int intInput = Integer.parseInt(input);
        negativeNumberCheck(intInput, true);
        existingUserCheck(intInput, true);
        sameAsCurrentUser(intInput, true);
        return  userLogonValid;
    }
    // Checks to ensure the transfer amount is valid.
    public static boolean transferAmountInputCheck (String input, Account transferingAccount) throws  InvalidInputException{
        errors.clear();

        boolean transferAmountValid = true;
        emptyInputCheck(input, true);
        numberOnlyCheck(input, true,true);
        double doubleInput = Double.parseDouble(input);
        boolean isNegative = negativeNumberCheck(doubleInput, false);
        boolean sufficientFunds = sufficientFundsCheck(doubleInput, transferingAccount, false);

        return transferAmountValid;
    }



    // Returns TRUE or throws error message if - input is null or an empty string ("")
    private static boolean emptyInputCheck(String input, boolean throwErrorOnFind) throws InvalidInputException {
        if(input == null || input == ""){
            errors.add("Input is empty, Please enter a value before clicking enter");
            if (throwErrorOnFind){
                throw new InvalidInputException(errors);
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }
    // Returns TRUE of throws error message if - input contains any Non-Number Characters
    private static boolean numberOnlyCheck(String input, boolean wholeNumberOnly, boolean throwErrorOnFind) throws InvalidInputException {
        char inputCharArray[] = input.toCharArray();
        boolean foundNonNumberCharacter = false;
        for (char character : inputCharArray) {
            if(!Character.isDigit(character) && !wholeNumberOnly && character != '.' && character != '-') {
                foundNonNumberCharacter = true;
                errors.add("Input must contain only a whole or decimal number");
                if(throwErrorOnFind){ throw new InvalidInputException(errors); }
            }
            else if(!Character.isDigit(character) && wholeNumberOnly && character != '-'){
                foundNonNumberCharacter = true;
                errors.add("Input must contain only a whole number");
                if(throwErrorOnFind){ throw new InvalidInputException(errors); }
            }
        }
        return foundNonNumberCharacter;
    }
    // Returns TRUE or throws error message if - integer input is negative
    private static boolean negativeNumberCheck(double intInput, boolean throwErrorOnFind) throws InvalidInputException{
        boolean isNegative = false;
        if(intInput < 0) {
            isNegative = true;
            errors.add("Number can not be negative");
            if(throwErrorOnFind){ throw new InvalidInputException(errors); }
        }
        return isNegative;
    }
    // Returns FALSE or throws error message if - No user with given User Number is found.
    private static boolean existingUserCheck(int userNumber, boolean throwErrorOnFind) throws InvalidInputException {
        boolean userExists = true;
        if (Application.dataController.findUserByNumber(userNumber) == null) {
            errors.add("No users found with user number: " + userNumber);
            userExists = false;
            if (throwErrorOnFind) { throw new InvalidInputException(errors); }
        }
        return userExists;
    }
    // Returns TRUE or throws error message if - The userNumber entered is the same as the currently selected user.
    private static boolean sameAsCurrentUser(int userNumber, boolean throwErrorOnFind) throws InvalidInputException{
        boolean sameAsCurrentUser = (Application.uiController.getDisplayingUser().getUserNumber() == userNumber);
        if (sameAsCurrentUser){
            errors.add("You can not enter your own user number");
            if (throwErrorOnFind) {throw new InvalidInputException(errors);}
        }
        return sameAsCurrentUser;
    }
    // Returns FALSE or throws error message if - Number is not an increment of the given number
    private static boolean numberIsDivisible(int intInput, int divisbleByValue, boolean throwErrorOnFind) throws InvalidInputException {
        boolean numberIsValid = intInput % divisbleByValue == 0;
        if (!numberIsValid) {
            errors.add("Number must be in increments of: " + divisbleByValue);
            if (throwErrorOnFind) { throw new InvalidInputException(errors); }
        }
        return numberIsValid;
    }
    // Returns FALSE or throws error message if - The given account contains less funds than the given number.
    public static boolean sufficientFundsCheck(double inputAmount, Account accountToCheck, boolean throwErrorOnFind) throws InvalidInputException{
        boolean sufficientFunds = ((accountToCheck.getAccountBalance() - Math.abs(inputAmount)) >= 0);
        if (!sufficientFunds) {
            errors.add("Insufficient funds in this user's " + accountToCheck.getAccountTypeString() + " account");
            if (throwErrorOnFind) { throw new InvalidInputException(errors); }
        }
        return  sufficientFunds;
    }
}
