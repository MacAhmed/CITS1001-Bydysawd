/**
 * Formula represents a Bydysawd chemical formula. 
 * A formula is a sequence of terms, e.g. AX3YM67. 
 *
 * @author Lyndon While
 * @version 2021
 */
import java.util.ArrayList;
import java.util.Collections;

public class Formula
{
    // the constituent terms of the formula
    private ArrayList<Term> terms;

    /**
     * Makes a formula containing a copy of terms.
     */
    public Formula(ArrayList<Term> terms)
    {
        this.terms = (ArrayList<Term>) terms.clone();
    }

    /**
     * Parses s to construct a formula. s will be a legal sequence 
     * of terms with no whitespace, e.g. "AX3YM67" or "Z".  
     * The terms in the field must be in the same order as in s. 
     */
    public Formula(String s)
    {
        this.terms = new ArrayList<>();
        String temp1 = new String(s);
        String temp2 = new String(s);
        
        do {
            temp1 = temp2.substring(lastUC(temp2));
            this.terms.add(new Term(temp1));
            temp2 = temp2.substring(0, lastUC(temp2));
        } while (temp2.length() != 0 );
        
        for (int i =0; i < (this.terms.size() / 2); i++ ) {
            Collections.swap(terms, i, this.terms.size() - i - 1);
        }
    }

    /**
     * Returns the terms of the formula.
     */
    public ArrayList<Term> getTerms()
    {
        return this.terms;
    }
    
    /**
     * Returns the index in s where the rightmost upper-case letter sits, 
     * e.g. lastTerm("AX3YM67") returns 4. 
     * Returns -1 if there are no upper-case letters. 
     */
    public static int lastUC(String s)
    {
        for (int i = s.length() - 1; i >=0 ; i-- ) {
            if(Character.isUpperCase(s.charAt(i))){
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Returns the total number of atoms of element in terms. 
     * e.g. if terms = <<W,2>,<X,1>,<W,5>>, countElement('W') returns 7, 
     * countElement('X') returns 1, and countElement('Q') returns 0.
     */
    public int countElement(char element)
    {
        this.standardise();
        for(Term x : terms){
            if(x.getElement() == element){
                return x.getCount();
            }
        }

        return 0;
    }

    /**
     * Puts terms in standardised form, where each element present is 
     * represented by exactly one term, and terms are in alphabetical order.
     * e.g. <<C,3>,<D,1>,<B,2>,<D,2>,<C,1>> becomes <<B,2>,<C,4>,<D,3>>.
     */
    public void standardise()
    {
        ArrayList<Term> temp = new ArrayList<>();
        int size = this.terms.size();
        for( int i = 0; i < size; i++ ){
            temp.add(i, this.nextElement());
            terms.remove(this.nextElement());
        }

        int a=0;
        for(int i = 0; i < size; i++){
            a += temp.get(i).getCount();
            if( i+1 == size ){
                this.terms.add(new Term(temp.get(i).getElement(), a));
                break;
            }

            if(temp.get(i).getElement() != temp.get(i+1).getElement()) {
                this.terms.add(new Term(temp.get(i).getElement(), a));
                a=0;
            }
        }
    }

    // returns the next Element in terms alphabetically
    // e.g. terms = {Term(H,2), Term(C, 2), Term(H,4),Term(C,1)} returns
    // Term(C,2)
    public Term nextElement(){
        Term min = this.terms.get(0);
        for(int i = 0; i < this.terms.size(); i++ ){
            if(this.terms.get(i).getElement() < min.getElement()){
                min = this.terms.get(i);
            }
        }
        return min;
    }

    /**
     * Returns true iff this formula and other are isomers, 
     * i.e. they contain the same number of every Bydysawd element. 
     */
    public boolean isIsomer(Formula other)
    {
        this.standardise();
        other.standardise();
        return this.terms.equals(other.terms);
    }

    /**
     * Returns the formula as a String. 
     * e.g. if terms = <<B,22>,<E,1>,<D,3>>, it returns "B22ED3". 
     */
    public String display()
    {
        String[] s1 = new String[terms.size()];
        String s2= new String();
        for(int i=0; i<terms.size(); i++){
            s1[i]=terms.get(i).display();
            s2+=s1[i];
        }
        
        return s2;
    }
}
