import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TreeFormulaTest {
    TreeFormula formula=new TreeFormula();

    @org.junit.jupiter.api.Test
    void isCorrectFunc() {
        String func1="abs";
        String func2="ab";

        assertEquals(true,formula.isCorrectFunc(func1));
        assertEquals(false,formula.isCorrectFunc(func2));
    }

    @org.junit.jupiter.api.Test
    void isCorrectParenthesis() {
        String test1="((3+2)*abs(x))";
        String test2="((x-2/sqrt(4))";
        String test3="(y-sin(z)";

        assertEquals(true,formula.isCorrectParenthesis(test1));
        assertEquals(false,formula.isCorrectParenthesis(test2));
        assertEquals(false,formula.isCorrectParenthesis(test3));

    }

    @org.junit.jupiter.api.Test
    void calcFunction() {
        String func1="abs";
        String func2="sqrt";
        String func3="pow";
        String func4="sin";
        String func5="cos";

        String arg1="-3.5+1";
        String arg2="3+1";
        String arg3="2,3";
        String arg4="0";

        assertEquals(2.5,formula.calcFunction(func1,arg1));
        assertEquals(2,formula.calcFunction(func2,arg2));
        assertEquals(8,formula.calcFunction(func3,arg3));
        assertEquals(0,formula.calcFunction(func4,arg4));
        assertEquals(1,formula.calcFunction(func5,arg4));
    }

    @org.junit.jupiter.api.Test
    void calcFormula() throws IOException {
        String test1="((3+5)/-2)";
        String test2="((pow(2,-1)*3)-sqrt(4))";
        String test3="((5*-2)/abs(-10))";

        assertEquals(-4,formula.calcFormula(test1));
        assertEquals(-0.5,formula.calcFormula(test2));
        assertEquals(-1,formula.calcFormula(test3));

    }
}