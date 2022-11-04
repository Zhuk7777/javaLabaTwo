import java.io.*;
import java.util.*;

public class TreeFormula {
    private class TreeNode
    {
        String data;
        TreeNode left,right;
    }
    private TreeNode root;
    private int iterator=0;
    private static String[] funcs  = {"abs", "sqrt", "pow", "sin","cos"};

    public boolean isCorrectFunc(String formula)
    {
        for(int i=0;i<5;i++)
        {
            formula = formula.replace(funcs[i],"a");
        }

        String cur="";

        for(int i=0;i<formula.length();i++)
        {
            if (formula.charAt(i) >= 'a' && formula.charAt(i) <= 'z')
                cur += formula.charAt(i);
            else
            if (formula.charAt(i) >= 'A' && formula.charAt(i) <= 'Z')
                cur += formula.charAt(i);
            else
                cur = "";

            if(cur.length()>1)
                return false;
        }
        return true;

    }
    public boolean isCorrectParenthesis(String formula)
    {
        boolean result=true;
        int size=formula.length();
        Stack stack=new Stack();

        while(iterator<size&& result==true)
        {
            if(formula.charAt(iterator)=='(')
                stack.push(formula.charAt(iterator));
            else {
                if (formula.charAt(iterator) == ')') {
                    if (stack.empty() || stack.peek() == ")")
                        result = false;
                    else stack.pop();
                }
            }
            iterator++;
        }

        iterator=0;
        return stack.empty()&&result;
    }

    private String calcOperations(char operation)
    {
        String result="";
        switch (operation)
        {
            case '+':
                result="-";
                break;
            case '-':
                result="+";
                break;
        }

        return result;
    }

    private TreeNode createTreeFormula(String formula)
    {
        TreeNode node=new TreeNode();
        if(iterator>0&&formula.charAt(iterator)=='-'&&(formula.charAt(iterator-1)=='('||formula.charAt(iterator-1)=='/'||formula.charAt(iterator-1)=='*'))
        {
            String cur;
            if(formula.charAt(iterator+1)=='-')
            {
                cur="+";
                iterator+=2;
            }
            else
            {
                cur="-";
                iterator++;
            }

            while((formula.charAt(iterator)>='0'&&formula.charAt(iterator)<='9')||formula.charAt(iterator)=='.')
            {
                cur+=formula.charAt(iterator);
                iterator++;
            }
            node.data=cur;
            node.left=null;
            node.right=null;

        }
        else {

            if (formula.charAt(iterator) >= '0' && formula.charAt(iterator) <= '9') {
                String cur = "";
                while ((formula.charAt(iterator) >= '0' && formula.charAt(iterator) <= '9') || formula.charAt(iterator) == '.') {
                    cur += formula.charAt(iterator);
                    iterator++;
                }
                node.data = cur;
                node.left = null;
                node.right = null;
            } else {
                if (formula.charAt(iterator) == '(')
                    iterator++;
                node.left = createTreeFormula(formula);

                if (formula.charAt(iterator + 1) == '-' && formula.charAt(iterator) != '/' && formula.charAt(iterator) != '*') {
                    node.data = calcOperations(formula.charAt(iterator));
                    iterator++;
                } else
                    node.data = formula.substring(iterator, iterator + 1);

                iterator++;
                node.right = createTreeFormula(formula);

                iterator++;
            }
        }

        return node;
    }

    private double calcFormulaInternal(TreeNode node)  {

        if(node.left==null&&node.right==null)
            return Double.parseDouble(node.data);

        double resultLeft=calcFormulaInternal(node.left);
        double resultRight=calcFormulaInternal(node.right);
        double result=0;
        switch (node.data)
        {
            case "+":
                result=resultLeft+resultRight;
                break;

            case "-":
                result=resultLeft-resultRight;
                break;

            case "*":
                result=resultLeft*resultRight;
                break;

            case "/":
                result=resultLeft/resultRight;
                break;
        }
        return result;
    }

    private boolean isFunc(int i,String formula)
    {
        if(formula.length()-i<6)
            return false;

        if("abs".equals(formula.substring(i,i+3)))
            return true;

        if("pow".equals(formula.substring(i,i+3)))
            return true;

        if("cos".equals(formula.substring(i,i+3)))
            return true;

        if("sin".equals(formula.substring(i,i+3)))
            return true;

        if(formula.length()-i<7)
            return false;
        else
        if("sqrt".equals(formula.substring(i,i+4)))
            return true;

        return false;
    }
    private String func(int i, String formula)
    {
        if(formula.charAt(i)=='a')
            return "abs";
        if(formula.charAt(i)=='p')
            return "pow";
        if(formula.charAt(i)=='c')
            return "cos";
        if(formula.charAt(i)=='s')
        {
            if(formula.charAt(i+1)=='i')
                return "sin";
            else
                return "sqrt";
        }

        return "";
    }

    private String argFunc(int i, String formula,Map<String,Double>map)
    {
        int j;
        String arg="",value;

        Scanner in = new Scanner(System.in);
        if(func(i,formula)!="sqrt")
            j=i+4;
        else
            j=i+5;
        while(formula.charAt(j)!=')')
        {
            if(((formula.charAt(j)>='a'&&formula.charAt(j)<='z')||(formula.charAt(j)>='A'&&formula.charAt(j)<='Z'))) {
                if (map.get(String.valueOf(formula.charAt(j))) != null)
                    arg += map.get(String.valueOf(formula.charAt(j)));
                else {
                    System.out.println("Enter the value of the variable " + formula.charAt(j));
                    value = in.next();
                    arg += value;
                    map.put(String.valueOf(formula.charAt(j)), Double.parseDouble(value));
                }
            }
            else
                arg+=formula.charAt(j);
            j++;
        }
        iterator=j;

        return arg;
    }
    public double calcFunction(String func, String arg)
    {
        double value=0.0,leftValue,rightValue=0.0;
        int i=0;

        String cur;
        if(arg.charAt(i)=='-')
        {
            if(arg.charAt(i+1)=='-') {
                cur = "+";
                i+=2;
            }
            else
            {
                cur="-";
                i++;
            }
        }
        else
            cur="";

        while(i<arg.length()&&((arg.charAt(i)>='0'&&arg.charAt(i)<='9')||arg.charAt(i)=='.'))
        {
            cur+=arg.charAt(i);
            i++;
        }
        leftValue=Double.parseDouble(cur);

        if(i<arg.length()) {
            int operation=i;
            i++;
            if(arg.charAt(i)=='-')
            {
                if(arg.charAt(i+1)=='-') {
                    cur = "+";
                    i+=2;
                }
                else
                {
                    cur="-";
                    i++;
                }
            }
            else
                cur="";

            while(i<arg.length()&&((arg.charAt(i)>='0'&&arg.charAt(i)<='9')||arg.charAt(i)=='.'))
            {
                cur+=arg.charAt(i);
                i++;
            }
            rightValue = Double.parseDouble(cur);

            switch (arg.charAt(operation)) {
                case '+':
                    value = leftValue + rightValue;
                    break;
                case '-':
                    value = leftValue - rightValue;
                    break;
                case '*':
                    value = leftValue * rightValue;
                    break;
                case '/':
                    value = leftValue / rightValue;
                    break;
                case ',':
                    value= Math.pow(leftValue,rightValue);
            }
        }
        else
            value=leftValue;

        if("abs".equals(func))
            value=Math.abs(value);
        if("cos".equals(func))
            value=Math.cos(value);
        if("sin".equals(func))
            value=Math.sin(value);
        if("sqrt".equals(func)) {
            if(value<0)
                throw new ArithmeticException();
            value = Math.sqrt(value);
        }

        return value;

    }
    public double calcFormula(String formula) throws IOException {

        if(isCorrectParenthesis(formula)==false)
            throw new IOException();
        if(isCorrectFunc(formula)==false)
            throw new IOException();

        Scanner in = new Scanner(System.in);
        String value, newFormula="";
        String f,arg;
        Map<String, Double> map = new HashMap<>();

        for(int i=0;i<formula.length();i++)
        {
            if(((formula.charAt(i)>='a'&&formula.charAt(i)<='z')||(formula.charAt(i)>='A'&&formula.charAt(i)<='Z'))) {
                if(isFunc(i,formula)==true) {
                    f=func(i,formula);
                    arg=argFunc(i,formula,map);
                    newFormula += calcFunction(f,arg);
                    i=iterator;
                }
                else
                if(map.get(String.valueOf(formula.charAt(i)))!=null)
                    newFormula+=map.get(String.valueOf(formula.charAt(i)));
                else {
                    System.out.println("Enter the value of the variable " + formula.charAt(i));
                    value = in.next();
                    newFormula += value;
                    map.put(String.valueOf(formula.charAt(i)), Double.parseDouble(value));
                }
            }
            else
                newFormula+=formula.charAt(i);

        }

        iterator=0;
        root=createTreeFormula(newFormula);
        iterator=0;
        return calcFormulaInternal(root);
    }



}
