package com.mp.program2;

public class Bill {
    private double billAmount;
    private double tipPercent;
    private boolean roundBill;
    private boolean roundTip;

    public Bill(double billAmount, double tipPercent){
        this.billAmount = billAmount;
        this.tipPercent = tipPercent;
        //Set to false because default is no rounding
        //and on startup all the radio buttons are unselected.
        this.roundBill = false;
        this.roundTip = false;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public double getTipPercent() {
        return tipPercent;
    }

    public void setTipPercent(double tipPercent) {
        this.tipPercent = tipPercent;
    }

    public boolean isRoundBill(){
        return roundBill;
    }

    //No setter for the booleans because I want them to
    //act like the radio buttons, when one is selected, the
    //rest are deselected.
    public void setRoundBillTrue() {
       this.roundBill = true;
       this.roundTip = false;
    }

    public boolean isRoundTip() {
        return roundTip;
    }

    //No setter for the booleans because I want them to
    //act like the radio buttons, when one is selected, the
    //rest are deselected.
    public void setRoundTipTrue() {
        this.roundTip = true;
        this.roundBill = false;
    }

    public void setNoRounding(){
        this.roundTip = false;
        this.roundBill = false;
    }

    public double getTip(){
        if(roundTip){
            return Math.round(this.billAmount * (this.tipPercent / 100));
        }
            return (this.billAmount * (this.tipPercent / 100));
    }

    public double calculateTotalBill(){
        double totalBill = 0;
        if(roundBill){
            return Math.round(this.billAmount + (this.billAmount * (this.tipPercent / 100)));
        }else if(roundTip){
            double roundedTip = Math.round(this.billAmount * (this.tipPercent / 100));
            return this.billAmount + roundedTip;
        }else{
            //No rounding
            return (this.billAmount + (this.billAmount * (this.tipPercent / 100)));
        }
    }
}
