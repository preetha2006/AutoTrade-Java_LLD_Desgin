package models;
import java.time.LocalDateTime;

public class Payment {

    private int paymentId;
    private int offerId;
    private int buyerId;
    private int sellerId;

    private double totalAmount;
    private double monthlyAmount;

    private int durationMonths;
    private int monthsPaid;

    private String method; // CASH, LOAN, EMI
    private String status; // ACTIVE, COMPLETED

    private LocalDateTime paidAt;

    public Payment(int paymentId, int offerId, int buyerId, int sellerId,
                   double totalAmount, String method,
                   int durationMonths, double monthlyAmount) {

        this.paymentId = paymentId;
        this.offerId = offerId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.totalAmount = totalAmount;
        this.method = method;

        this.durationMonths = durationMonths;
        this.monthlyAmount = monthlyAmount;

        this.monthsPaid = 0;
        this.status = "ACTIVE";
        this.paidAt = LocalDateTime.now();
    }

    public int getPaymentId(){ return paymentId; }
    public int getBuyerId(){ return buyerId; }

    public int getMonthsPaid(){ return monthsPaid; }
    public int getDurationMonths(){ return durationMonths; }

    public double getMonthlyAmount(){ return monthlyAmount; }

    public void payNextMonth(){

        if(monthsPaid >= durationMonths){
            System.out.println("Loan already completed.");
            return;
        }

        monthsPaid++;

        System.out.println("Month " + monthsPaid + " payment of ₹" + monthlyAmount + " successful.");

        if(monthsPaid == durationMonths){
            status = "COMPLETED";
            System.out.println("Loan fully completed!");
        }
    }

    public void showSchedule(){

        System.out.println("\nLoan ID : " + paymentId);
        System.out.println("Total Amount : ₹" + totalAmount);
        System.out.println("Duration : " + durationMonths + " months");
        System.out.println("Monthly EMI : ₹" + monthlyAmount);

        System.out.println("\nMonth  Status");

        for(int i=1;i<=durationMonths;i++){

            if(i<=monthsPaid)
                System.out.println(i + "      PAID");
            else
                System.out.println(i + "      PENDING");
        }
    }

    public void displayPayment(){

        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║          LOAN SUMMARY              ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println(" Payment ID : " + paymentId);
        System.out.println(" Method     : " + method);
        System.out.println(" Total Amt  : ₹" + totalAmount);
        System.out.println(" Duration   : " + durationMonths + " months");
        System.out.println(" Paid       : " + monthsPaid + " months");
        System.out.println(" Status     : " + status);
        System.out.println("╚════════════════════════════════════╝");
    }
}