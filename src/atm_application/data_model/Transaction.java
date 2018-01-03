package atm_application.data_model;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    // The transaction type allows the transaction log to state what kind of transaction it was.
    public static enum TransType {WITHDRAW, DEPOSIT, TRANSFER_SENDING, TRANSFER_RECEIVING};

    private double transAmount;
    private String transDescription;
    private Date transDateTime;
    private TransType transType;

    // The constructor simply initializes the values.
    public Transaction(double transAmount, TransType transType, String transDescription, Date transDateTime) {
        this.transAmount = transAmount;
        this.transType = transType;
        this.transDescription = transDescription;
        this.transDateTime = transDateTime;
    }

    // This method returns an array of strings representing the log information about the transaction.
    public String[] genTransactionLog() {
        // Formaters make the output String readable and user friendly.
        NumberFormat dollarFormatter = NumberFormat.getCurrencyInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // Builds the returning array.
        String transactionLog[] = {
            dateFormat.format(this.transDateTime),
            this.transType.toString(),
            this.transDescription,
            dollarFormatter.format(this.transAmount).toString()
        };

        // Returns the array of transaction details.
        return transactionLog;
    }

    // Getter methods:
    public double getTransAmount() {
        return transAmount;
    }
    public TransType getTransType() {
        return transType;
    }
    public String getTransDescription() {
        return transDescription;
    }
    public Date getTransDateTime() {
        return transDateTime;
    }
}
