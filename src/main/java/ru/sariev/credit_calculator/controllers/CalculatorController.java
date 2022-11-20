package ru.sariev.credit_calculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sariev.credit_calculator.models.Calculation;
import ru.sariev.credit_calculator.services.CalculatorService;
import javax.validation.Valid;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping()
    public String mainPage(@ModelAttribute("calculation") Calculation calculation) {
        calculation.setCreditAmount(calculatorService.getEnteredCreditAmount());
        calculation.setPercentRate(calculatorService.getEnteredPercentRate());
        calculation.setCreditTerm(calculatorService.getEnteredCreditTerm());
        calculation.setCurrentDate(calculatorService.getEnteredCurrentDate());
        calculation.setRepaymentType(calculatorService.getEnteredRepaymentType());
        return "calculator/main";
    }

    @PostMapping("/calculations")
    public String create(@ModelAttribute("calculation") @Valid Calculation calculation, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "calculator/main";

        model.addAttribute("calculations", calculatorService.getCalculationList(calculation));
        model.addAttribute("overPayment", calculatorService.getOverPayment());
        calculatorService.setOverPayment(0.0);
        return "calculator/calcs";
    }
}
