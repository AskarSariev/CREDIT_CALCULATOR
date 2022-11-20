package ru.sariev.credit_calculator.models;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

public class Calculation {
    @Min(value = 10000, message = "Минимальная сумма 10 000 руб.")
    private int creditAmount; // Сумма кредита

    @Min(value = 1, message = "Минимальная процентная ставка 1%")
    private double percentRate; // Процентная ставка

    @Min(value = 1, message = "Минимальный срок кредитования 1 месяц")
    @Max(value = 360, message = "Максимальный срок кредитования 360 месяцев")
    private int creditTerm; // Срок кредитования

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date currentDate; // Дата выдачи кредита
    private String repaymentType; // Тип погашения
    private double monthlyPaymentAmount;  // Сумма ежемесячного платежа
    private double monthlyPercent; // Ежемесячный начисленный процент
    private double bodyOfCredit; // Тело кредита
    private double remainder; // Остаток долга

    public Calculation() {
    }

    public Calculation(int creditAmount, double percentRate, int creditTerm, Date currentDate, String repaymentType) {
        this.creditAmount = creditAmount;
        this.percentRate = percentRate;
        this.creditTerm = creditTerm;
        this.currentDate = currentDate;
        this.repaymentType = repaymentType;
    }

    public Calculation(Date currentDate, double monthlyPaymentAmount, double monthlyPercent, double bodyOfCredit, double remainder) {
        this.currentDate = currentDate;
        this.monthlyPaymentAmount = monthlyPaymentAmount;
        this.monthlyPercent = monthlyPercent;
        this.bodyOfCredit = bodyOfCredit;
        this.remainder = remainder;
    }

    public int getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(int creditAmount) {
        this.creditAmount = creditAmount;
    }

    public double getPercentRate() {
        return percentRate;
    }

    public void setPercentRate(double percentRate) {
        this.percentRate = percentRate;
    }

    public int getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(int creditTerm) {
        this.creditTerm = creditTerm;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType;
    }

    public double getMonthlyPaymentAmount() {
        return monthlyPaymentAmount;
    }

    public void setMonthlyPaymentAmount(double monthlyPaymentAmount) {
        this.monthlyPaymentAmount = monthlyPaymentAmount;
    }

    public double getMonthlyPercent() {
        return monthlyPercent;
    }

    public void setMonthlyPercent(double monthlyPercent) {
        this.monthlyPercent = monthlyPercent;
    }

    public double getBodyOfCredit() {
        return bodyOfCredit;
    }

    public void setBodyOfCredit(double bodyOfCredit) {
        this.bodyOfCredit = bodyOfCredit;
    }

    public double getRemainder() {
        return remainder;
    }

    public void setRemainder(double remainder) {
        this.remainder = remainder;
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "creditAmount=" + creditAmount +
                ", percentRate=" + percentRate +
                ", creditTerm=" + creditTerm +
                ", currentDate=" + currentDate +
                ", repaymentType=" + repaymentType +
                ", monthlyPaymentAmount=" + monthlyPaymentAmount +
                ", monthlyPercent=" + monthlyPercent +
                ", bodyOfCredit=" + bodyOfCredit +
                ", remainder=" + remainder +
                '}';
    }
}
