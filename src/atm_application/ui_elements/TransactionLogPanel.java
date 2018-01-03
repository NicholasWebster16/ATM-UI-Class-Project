package atm_application.ui_elements;

import atm_application.data_model.Account;
import atm_application.data_model.Transaction;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class TransactionLogPanel  extends JPanel {

    private JTable logTable;
    private JScrollPane logTablePane;

    public TransactionLogPanel(Account accountToLog) {
        super(new BorderLayout());
        ArrayList<Transaction> transactionList = accountToLog.getTransactions();

        String transactionLogList[][] = new String[transactionList.size()][4];
        String columnLabel[] = {"Date", "Trans Type", "Trans Description", "Amount"};

        for (int i = 0; i < transactionList.size(); i++) {
            Transaction transaction = transactionList.get(i);
            String[] transactionLog = transaction.genTransactionLog();
            // I seem to be unable to add the transactionLog string array as whole to transactionLogList[i].
            // Attempt:
            //      transactionLogList[i][] = {transactionLog};
            // Only way I found to work was adding one entry at a time.
            // Works:
            transactionLogList[i][0] = transactionLog[0];
            transactionLogList[i][1] = transactionLog[1];
            transactionLogList[i][2] = transactionLog[2];
            transactionLogList[i][3] = transactionLog[3];
        }

        this.setPreferredSize(new Dimension(500,200));

        logTable = new JTable(transactionLogList, columnLabel);
        logTablePane = new JScrollPane(logTable);

        this.add(logTablePane);
        this.setBorder(BorderFactory.createTitledBorder(null,  accountToLog.getAccountTypeString() +" Account - Transaction Log", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, CustomFonts.labelSmall, Color.black));
    }
}
