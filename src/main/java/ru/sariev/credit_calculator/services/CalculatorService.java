package ru.sariev.credit_calculator.services;

import org.springframework.stereotype.Service;
import ru.sariev.credit_calculator.models.Calculation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CalculatorService {

    private int enteredCreditAmount; // Введенная сумма кредита в поле ввода
    private double enteredPercentRate; // Введенная процентная ставка в поле ввода
    private int enteredCreditTerm; // Введенный срок кредита в поле ввода
    private Date enteredCurrentDate; // Введенная дата выдачи кредита в поле ввода
    private String enteredRepaymentType; // Введенный тип погащения в поле ввода

    private double overPayment; // Суммарная переплата по кредиту

    public List<Calculation> getCalculationList(Calculation calculation) {

        // Запоминание введенных в форму значений
        initEnteredData(calculation);

        List<Calculation> calculations = null;

        // Расчет графика платежей в зависимости от типа погашения
        if (calculation.getRepaymentType().equals("Annuity")) {
            calculations = annuityRepaymentType(calculation);
        }

        if (calculation.getRepaymentType().equals("Differentiated")) {
            calculations = differentiatedRepaymentType(calculation);
        }

        return calculations;
    }

    // Расчет графика платежей при Аннуитетном типе погашения
    private List<Calculation> annuityRepaymentType(Calculation calculation) {

        // Запоминание введенных в форму значений
        initEnteredData(calculation);

        List<Calculation> calculations = new ArrayList<>();

        getFirstLineOfPayment(calculation, calculations);

        // Остаток долга на момент 1-го платежа
        double balanceOwed = calculation.getCreditAmount();

        // Доля процентной ставки (в месяц)
        double shareOfPercentRate = calculation.getPercentRate() / 100.0 / 12.0;

        // Сумма ежемесячного платежа. Значение постоянное
        double monthlyPaymentAmount = calculation.getCreditAmount() * (shareOfPercentRate + shareOfPercentRate /
                (Math.pow( (1 + shareOfPercentRate), calculation.getCreditTerm() ) - 1) );

        // Ежемесячный начисленный процент на момент 1-го платежа
        double monthlyPercent = calculation.getCreditAmount() * shareOfPercentRate;
//        System.out.println(monthlyPercent);

        // Тело кредита на момент 1-го платежа
        double bodyOfCredit = monthlyPaymentAmount - monthlyPercent;

        for (int i = 1; i <= calculation.getCreditTerm() + 1; i++) {

            if (balanceOwed < monthlyPaymentAmount) {

                monthlyPercent = balanceOwed * shareOfPercentRate;
                balanceOwed = 0.0;
                monthlyPaymentAmount = monthlyPercent + bodyOfCredit;

            } else {

                monthlyPercent = balanceOwed * shareOfPercentRate;
                balanceOwed = balanceOwed - bodyOfCredit;
                bodyOfCredit = monthlyPaymentAmount - monthlyPercent;
            }

            overPayment += monthlyPercent;

            Calculation newCalculation = new Calculation(getDateofNextPayment(calculation, i),
                    monthlyPaymentAmount, monthlyPercent, bodyOfCredit, balanceOwed);

            calculations.add(newCalculation);
        }

        return calculations;
    }

    // Запоминание введенных в форму значений
    private void initEnteredData(Calculation calculation) {
        enteredCreditAmount = calculation.getCreditAmount();
        enteredPercentRate = calculation.getPercentRate();
        enteredCreditTerm = calculation.getCreditTerm();
        enteredCurrentDate = calculation.getCurrentDate();
        enteredRepaymentType = calculation.getRepaymentType();
    }

    // Расчет графика платежей при Дифференцированном типе погашения
    private List<Calculation> differentiatedRepaymentType(Calculation calculation) {

        List<Calculation> calculations = new ArrayList<>();

        getFirstLineOfPayment(calculation, calculations);

        // Остаток долга на момент 1-го платежа
        double balanceOwed = calculation.getCreditAmount();

        // Тело кредита. Значение постоянное
        double bodyOfCredit = calculation.getCreditAmount() / calculation.getCreditTerm();

        // Доля процентной ставки (в месяц)
        double shareOfPercentRate = calculation.getPercentRate() / 100.0 / 12.0;

        // Ежемесячный начисленный процент на момент 1-го платежа
        double monthlyPercent = calculation.getCreditAmount() * shareOfPercentRate;

        // Сумма ежемесячного платежа
        double monthlyPaymentAmount = bodyOfCredit + monthlyPercent;

        for (int i = 1; i <= calculation.getCreditTerm(); i++) {

            if (balanceOwed < monthlyPaymentAmount) {

                monthlyPercent = balanceOwed * shareOfPercentRate;
                bodyOfCredit = balanceOwed;
                balanceOwed = 0.0;
                monthlyPaymentAmount = bodyOfCredit + monthlyPercent;

            } else {

                balanceOwed = balanceOwed - bodyOfCredit;
                monthlyPercent = balanceOwed * shareOfPercentRate;
                monthlyPaymentAmount = bodyOfCredit + monthlyPercent;
            }

            overPayment += monthlyPercent;

            Calculation newCalculation = new Calculation(getDateofNextPayment(calculation, i),
                    monthlyPaymentAmount, monthlyPercent, bodyOfCredit, balanceOwed);
            calculations.add(newCalculation);
        }

        return calculations;
    }

    // Фиксация остатка долга в первой строке графика платежей
    private void getFirstLineOfPayment(Calculation calculation, List<Calculation> calculations) {

        calculation.setRemainder(calculation.getCreditAmount());
        calculations.add(calculation);
    }

    // Вычисление даты следующего ежемесячного платежа
    private Date getDateofNextPayment(Calculation calculation, int count) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calculation.getCurrentDate());
        calendar.add(Calendar.MONTH, +count);

        return calendar.getTime();
    }

    public int getEnteredCreditAmount() {
        return enteredCreditAmount;
    }

    public double getEnteredPercentRate() {
        return enteredPercentRate;
    }

    public int getEnteredCreditTerm() {
        return enteredCreditTerm;
    }

    public Date getEnteredCurrentDate() {
        return enteredCurrentDate;
    }

    public String getEnteredRepaymentType() {
        return enteredRepaymentType;
    }

    public double getOverPayment() {
        return overPayment;
    }

    public void setOverPayment(double overPayment) {
        this.overPayment = overPayment;
    }
}
