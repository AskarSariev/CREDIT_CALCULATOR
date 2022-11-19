package ru.sariev.credit_calculator.services;

import org.springframework.stereotype.Service;
import ru.sariev.credit_calculator.models.Calculation;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CalculatorService {

    public List<Calculation> getCalculationList(Calculation calculation) {
        List<Calculation> calculations = null;

        if (calculation.getRepaymentType().equals("Annuity")) {
            calculations = annuityRepaymentType(calculation);
        }

        if (calculation.getRepaymentType().equals("Differentiated")) {
            calculations = differentiatedRepaymentType(calculation);
        }

        return calculations;
    }

    private List<Calculation> annuityRepaymentType(Calculation calculation) {

        List<Calculation> calculations = new ArrayList<>();

        calculation.setRemainder(calculation.getCreditAmount());
        calculations.add(calculation);

        // Остаток долга на момент 1-го платежа
        double balanceOwed = calculation.getCreditAmount();

        // Доля процентной ставки (в месяц)
        double shareOfPercentRate = calculation.getPercentRate() / 100.0 / 12.0;

        // Сумма ежемесячного платежа
        double monthlyPaymentAmount = calculation.getCreditAmount() * (shareOfPercentRate + shareOfPercentRate / (Math.pow( (1 + shareOfPercentRate), calculation.getCreditTerm() ) - 1) );

        // Ежемесячный начисленный процент на момент 1-го платежа
        double monthlyPercent = calculation.getCreditAmount() * shareOfPercentRate;

        // Тело кредита на момент 1-го платежа
        double bodyOfCredit = monthlyPaymentAmount - monthlyPercent;

        for (int i = 1; i <= calculation.getCreditTerm() + 1; i++) {

            // Вычисление даты следующего ежемесячного платежа
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calculation.getCurrentDate());
            calendar.add(Calendar.MONTH, +i);
            Date dateOfNextPayment = calendar.getTime();

            if (balanceOwed < monthlyPaymentAmount) {

                monthlyPercent = balanceOwed * shareOfPercentRate;
                balanceOwed = 0.0;
                monthlyPaymentAmount = monthlyPercent + bodyOfCredit;

            } else {

                monthlyPercent = balanceOwed * shareOfPercentRate;
                balanceOwed = balanceOwed - bodyOfCredit;
                bodyOfCredit = monthlyPaymentAmount - monthlyPercent;
            }

            Calculation newCalculation = new Calculation(dateOfNextPayment, monthlyPaymentAmount, monthlyPercent, bodyOfCredit, balanceOwed);
            calculations.add(newCalculation);
        }

        return calculations;
    }

    private List<Calculation> differentiatedRepaymentType(Calculation calculation) {

        List<Calculation> calculations = new ArrayList<>();

        calculation.setRemainder(calculation.getCreditAmount());
        calculations.add(calculation);

        // Остаток долга на момент 1-го платежа
        double balanceOwed = calculation.getCreditAmount();

        double bodyOfCredit = calculation.getCreditAmount() / calculation.getCreditTerm();

        // Доля процентной ставки (в месяц)
        double shareOfPercentRate = calculation.getPercentRate() / 100.0 / 12.0;

        // Ежемесячный начисленный процент на момент 1-го платежа
        double monthlyPercent = calculation.getCreditAmount() * shareOfPercentRate;

        // Сумма ежемесячного платежа
        double monthlyPaymentAmount = bodyOfCredit + monthlyPercent;

        for (int i = 1; i <= calculation.getCreditTerm(); i++) {
            // Вычисление даты следующего ежемесячного платежа
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calculation.getCurrentDate());
            calendar.add(Calendar.MONTH, +i);
            Date dateOfNextPayment = calendar.getTime();

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

            Calculation newCalculation = new Calculation(dateOfNextPayment, monthlyPaymentAmount, monthlyPercent, bodyOfCredit, balanceOwed);
            calculations.add(newCalculation);
        }

        return calculations;
    }
}
