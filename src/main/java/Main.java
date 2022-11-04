import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String str="(((((x+pow(2,-z))*-3)/-y)/-abs(z))+t)";
        TreeFormula formula= new TreeFormula();
        double result = formula.calcFormula(str);
        System.out.println(result);
    }

}
