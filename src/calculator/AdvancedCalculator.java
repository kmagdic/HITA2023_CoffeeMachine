package calculator;

public class AdvancedCalculator extends Calculator {

    public AdvancedCalculator() {
        operationsList.add("pow");
    }
    public double calculate(){
        if (operation.equals("pow")){
            return Math.pow(a,b);
        }
        else if (operation.equals("%")){
            return a % b;
        }
        else {
            return super.calculate();
        }
    }
}
