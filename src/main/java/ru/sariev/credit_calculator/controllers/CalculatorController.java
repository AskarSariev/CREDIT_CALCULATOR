package ru.sariev.credit_calculator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sariev.credit_calculator.models.Calculation;
import ru.sariev.credit_calculator.services.CalculatorService;

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
        return "calculator/main";
    }

    @PostMapping("/calculations")
    public String create(@ModelAttribute("calculation") Calculation calculation, Model model) {

        model.addAttribute("calculations", calculatorService.getCalculationList(calculation));
        return "calculator/calcs";
    }
}
