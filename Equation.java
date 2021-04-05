/**
 * Equation represents a Bydysawd chemical equation. 
 * An equation can have multiple formulas on each side, 
 * e.g. X3 + Y2Z2 = ZX + Y2X2 + Z. 
 *
 * @author Lyndon While
 * @version 2021
 */
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Equation
{
    // the two sides of the equation 
    // there can be multiple formulas on each side 
    private ArrayList<Formula> lhs, rhs;

    /**
     * Parses s to construct an equation. s will contain a 
     * syntactically legal equation, e.g. X3 + Y2Z = ZX + Y2X4. 
     * s may contain whitespace between formulas and symbols. 
     */
    public Equation(String s)
    {
        String temp = new String(s);
        temp = temp.replaceAll("(\\s+|^\\s+|\\s+$)", "");
        for(int i=0; i< temp.length(); i++)
        {
            if(temp.charAt(i)=='='){
                this.lhs=parseSide(temp.substring(0, i));
                this.rhs=parseSide(temp.substring(i+1));
                break;
            }
        }
    }

    /**
     * Returns the left-hand side of the equation.
     */
    public ArrayList<Formula> getLHS()
    {
        return this.lhs;
    }

    /**
     * Returns the right-hand side of the equation.
     */
    public ArrayList<Formula> getRHS()
    {
        // TODO 12b
        return this.rhs;
    }
    
    /**
     * Returns the indices at which x occurs in s, 
     * e.g. indicesOf("ax34x", 'x') returns <1,4>. 
     */
    public static ArrayList<Integer> indicesOf(String s, char x)
    {
        ArrayList<Integer> main = new ArrayList<>();

        int index = s.indexOf(x);
        while (index >= 0) {
            main.add(index);
            index = s.indexOf(x, index + 1);
        }

        return main;
    }
    
    /**
     * Parses s as one side of an equation. 
     * s will contain a series of formulas separated by pluses, 
     * and it may contain whitespace between formulas and symbols. 
     */
    public static ArrayList<Formula> parseSide(String s)
    {
        ArrayList<Formula> main = new ArrayList<>();
        String temp1 = new String(s);
        
        temp1 = temp1.replaceAll("(\\s+|^\\s+|\\s+$)", "");
        String[] parts = temp1.split("\\+");
        
        for( String p : parts ){
            main.add(new Formula(p));
        }

        return main;
    }

    /**
     * Returns true iff the equation is balanced, i.e. it has the 
     * same number of atoms of each Bydysawd element on each side. 
     */
    public boolean isValid()
    {
        ArrayList<Term> temp1= new ArrayList<>();
        Formula mainLhs = new Formula(temp1);
        ArrayList<Term> temp2= new ArrayList<>();
        Formula mainRhs = new Formula(temp2);
        
        for(int i=0; i<lhs.size(); i++)
        {
            mainLhs.getTerms().addAll(lhs.get(i).getTerms());
        }
        
        for(int i=0; i<rhs.size(); i++)
        {
            mainRhs.getTerms().addAll(rhs.get(i).getTerms());
        }
        
        mainLhs.standardise();
        mainRhs.standardise();
        
        return mainLhs.isIsomer(mainRhs);
    }

    /**
     * Returns the equation as a String.
     */
    public String display(){

        String s = this.getLHS().stream().map(
            e -> e.display()
        ).collect(
            Collectors.joining(" + ")
        );
        
        s += " = ";

        s += this.getRHS().stream().map(
            e -> e.display()
        ).collect(
            Collectors.joining(" + ")
        );

        return s;
    }
}
