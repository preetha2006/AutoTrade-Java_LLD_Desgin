package services;

import models.*;
import storage.DataStore;

public class PaymentService {

    private DataStore ds = DataStore.getInstance();

    // ---------------- CASH PAYMENT ----------------

    public Payment processCashPayment(int offerId, int buyerId, int sellerId, double amount){

        Payment p = new Payment(
                ds.nextPaymentId(),
                offerId,
                buyerId,
                sellerId,
                amount,
                "CASH",
                1,
                amount
        );

        ds.addPayment(p);

        System.out.println("\nPayment Method : CASH");
        System.out.println("Amount Paid : ₹" + amount);
        System.out.println("Status : SUCCESS");

        return p;
    }


    // ---------------- LOAN CREATION ----------------

    public Payment createLoan(int offerId, int buyerId, int sellerId, double amount, int months){

        double rate = 0.085 / 12;

        double emi = (amount * rate * Math.pow(1 + rate, months))
                / (Math.pow(1 + rate, months) - 1);

        Payment p = new Payment(
                ds.nextPaymentId(),
                offerId,
                buyerId,
                sellerId,
                amount,
                "LOAN",
                months,
                emi
        );

        ds.addPayment(p);

        System.out.println("\nLoan Approved!");
        System.out.println("Bank : HDFC Bank");
        System.out.println("Loan ID : " + p.getPaymentId());
        System.out.println("Monthly EMI : ₹" + Math.round(emi));

        return p;
    }


    // ---------------- EMI CREATION ----------------

    public Payment createEmi(int offerId, int buyerId, int sellerId, double amount, int months){

        double interestRate = 0.10;

        double total = amount + (amount * interestRate);

        double emi = total / months;

        Payment p = new Payment(
                ds.nextPaymentId(),
                offerId,
                buyerId,
                sellerId,
                total,
                "EMI",
                months,
                emi
        );

        ds.addPayment(p);

        System.out.println("\nEMI Plan Created");
        System.out.println("EMI ID : " + p.getPaymentId());
        System.out.println("Duration : " + months + " months");
        System.out.println("Monthly EMI : ₹" + Math.round(emi));

        return p;
    }


    // ---------------- LOAN EMI PAYMENT ----------------

    public void payLoanMonth(int paymentId, int month){

        for(Payment p : ds.getPayments()){

            if(p.getPaymentId() == paymentId){

                if(month != p.getMonthsPaid() + 1){
                    System.out.println("Cannot pay this month.");
                    System.out.println("Please clear previous dues first.");
                    return;
                }

                p.payNextMonth();

                if(p.getMonthsPaid() == p.getDurationMonths()){
                    System.out.println("Loan Closed Successfully!");
                }

                return;
            }
        }

        System.out.println("Loan ID not found.");
    }


    // ---------------- EMI PAYMENT ----------------

    public void payEmiMonth(int paymentId, int month){

        for(Payment p : ds.getPayments()){

            if(p.getPaymentId() == paymentId){

                if(month != p.getMonthsPaid() + 1){
                    System.out.println("Cannot pay this month.");
                    System.out.println("Please clear previous dues first.");
                    return;
                }

                p.payNextMonth();

                if(p.getMonthsPaid() == p.getDurationMonths()){
                    System.out.println("EMI Plan Completed!");
                }

                return;
            }
        }

        System.out.println("EMI ID not found.");
    }


    // ---------------- VIEW LOAN SCHEDULE ----------------

    public void viewLoanSchedule(int paymentId){

        for(Payment p : ds.getPayments()){

            if(p.getPaymentId() == paymentId){

                System.out.println("\nLoan Schedule");

                p.showSchedule();

                return;
            }
        }

        System.out.println("Loan not found.");
    }


    // ---------------- VIEW EMI SCHEDULE ----------------

    public void viewEmiSchedule(int paymentId){

        for(Payment p : ds.getPayments()){

            if(p.getPaymentId() == paymentId){

                System.out.println("\nEMI Schedule");

                p.showSchedule();

                return;
            }
        }

        System.out.println("EMI plan not found.");
    }


    // ---------------- LOAN OPTIONS ----------------

    public void showLoanOptions(double carPrice){

        System.out.println("\nAvailable Loan Plans");

        int[] tenures = {12,24,36};

        double rate = 0.085/12;

        for(int t : tenures){

            double emi = (carPrice * rate * Math.pow(1+rate,t))
                    /(Math.pow(1+rate,t)-1);

            System.out.println(
                    t + " months EMI : ₹" + Math.round(emi)
            );
        }
    }


    // ---------------- EMI OPTIONS ----------------

    public void showEmiOptions(double price){

        System.out.println("\nAvailable EMI Plans");

        int[] months = {3,6,9};

        double interestRate = 0.10;

        for(int m : months){

            double total = price + (price * interestRate);

            double emi = total / m;

            System.out.println(
                    m + " months EMI : ₹" + Math.round(emi)
            );
        }
    }


    // ---------------- LIST PAYMENTS BY BUYER ----------------

    public void listPaymentsByBuyer(int buyerId){

        for(Payment p : ds.getPayments()){

            if(p.getBuyerId() == buyerId){
                p.displayPayment();
            }
        }
    }


    // ---------------- ADMIN : LIST ALL PAYMENTS ----------------

    public void listAllPayments(){

        if(ds.getPayments().isEmpty()){
            System.out.println("No transactions yet.");
            return;
        }

        System.out.println("\n--- All Transactions ---");

        for(Payment p : ds.getPayments()){
            p.displayPayment();
        }
    }
}