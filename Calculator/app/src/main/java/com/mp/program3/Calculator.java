package com.mp.program3;

public class Calculator {
    private double firstOperator;
    private double secondOperator;
    private String operation;

    public Calculator(){
        this.firstOperator = Double.NaN;
        this.secondOperator = Double.NaN;
    }

    public double getFirstOperator() {
        return firstOperator;
    }

    public void setFirstOperator(double firstOperator) {
        this.firstOperator = firstOperator;
    }

    public double getSecondOperator() {
        return secondOperator;
    }

    public void setSecondOperator(double secondOperator) {
        this.secondOperator = secondOperator;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double calculateResult(){
        switch(this.operation){
            case("+"):
                return this.firstOperator + this.secondOperator;
            case("-"):
                return this.firstOperator - this.secondOperator;
            case("*"):
                return this.firstOperator * this.secondOperator;
            case("/"):
                if(this.secondOperator == 0){
                    throw new IllegalArgumentException("Divide by zero error");
                }else{
                    return this.firstOperator / this.secondOperator;
                }
            case("^"):
                return Math.pow(this.firstOperator, this.secondOperator);
        }
        return 0;
    }

    public void clear(){
        this.firstOperator = Double.NaN;
        this.secondOperator = Double.NaN;
    }
}
