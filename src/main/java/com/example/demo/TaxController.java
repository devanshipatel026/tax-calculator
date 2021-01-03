package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Tax;
import com.example.demo.model.Bracket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

//Defining Restful service to get Tax bracket
@RestController
public class TaxController {
    //List of tax brackets and tax rates
    List<Integer> taxBracket = Arrays.asList(48535, 48534,53404,63895,Integer.MAX_VALUE);
    List<Float> taxPercentage = Arrays.asList((float)15,(float)20.5,(float)26,(float)29,(float)33);
    List<String> titles = Arrays.asList("$48,535 or less", "$48,535 to $97,069", "$97,069 to $150,473", "$150,473 to $214,368","More than $214,368");

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse HttpServletResponse){
        HttpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        HttpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
    }

    //Defining URI to access Restful API Endpoints, consumes value and produces JSON object
    @RequestMapping("api/taxCalculation")
    public Tax TaxCalculator(@RequestParam(value = "amount",
            defaultValue = "0") String amount) {
        Tax response = new Tax();
        float taxableAmount;
        try{
            //Check negative value
            taxableAmount = Float.parseFloat(amount);
        }catch (Exception e){
            //Bad Request response in case of invalid amount
            response.setMessage("Invalid Number");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        //Successful response
        response.setMessage("Success");
        response.setStatusCode(HttpStatus.OK.value());
        List<Bracket> bracketList =  processTaxableAmount(taxableAmount);
        response.setBracket(bracketList);
        response.setTotalTax(getTotalTax(bracketList));

        return response;
    }

    private List<Bracket> processTaxableAmount(float taxableAmount) {

        //Calculated amount after tax
        List<Float> taxAmount = new ArrayList<>();
        int i=0;
        while(i<5 && taxableAmount>0){
            float amount = 0;
            if(taxableAmount > taxBracket.get(i)){
                amount = taxBracket.get(i);
            }
            else{
                amount = taxableAmount;
            }
            taxableAmount -= amount;
            taxAmount.add((float) (amount * taxPercentage.get(i) * 0.01));
            i++;
        }
        List<Bracket> bracketList = new ArrayList<>();
        for (int j = 0; j < taxAmount.size() ; j++) {
            Bracket br = new Bracket();
            br.setTaxAmount(taxAmount.get(j));
            br.setPercentage(taxPercentage.get(j));
            br.setTitle(titles.get(j));
            bracketList.add(br);
        }
        return bracketList;
    }

    private float getTotalTax(List<Bracket> bracketList){
        float totalTax = 0;
        for(Bracket bracket : bracketList) {
            totalTax += bracket.getTaxAmount();
        }
        return totalTax;
    }

}
