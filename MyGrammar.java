import computation.contextfreegrammar.*;
import java.util.ArrayList; 
import java.util.List;

public class MyGrammar {
	public static ContextFreeGrammar makeGrammar() {
		
    //create the grammar's variables 
    Variable start = new Variable("S0");
    Variable s = new Variable('S');
    Variable t = new Variable('T');
    Variable f = new Variable('F');
    Variable s1 = new Variable("S1");
    Variable t1 = new Variable("T1");
    Variable f1 = new Variable("F1");
    Variable l = new Variable('L');
    Variable r = new Variable('R');
    Variable a = new Variable('A');
    Variable m = new Variable('M');

    //create the grammar's terminals
    Terminal one = new Terminal('1');
    Terminal zero = new Terminal('0');
    Terminal x = new Terminal('x');
    Terminal leftBracket = new Terminal('(');
    Terminal rightBracket = new Terminal(')');
    Terminal plus = new Terminal('+');
    Terminal timesSign = new Terminal('*');

    //create a list with the grammar's rules
    List<Rule> rules = new ArrayList<Rule>();

    //create the rules of the grammar in Chomsky-normal form for start symbol S0
    Rule str1 = new Rule(start,  new Word(s, t1));
    rules.add(str1);
    Rule str2 = new Rule(start, new Word(s, f1));
    rules.add(str2);
    Rule str3 = new Rule(start, new Word(l, s1));
    rules.add(str3);
    Rule str4 = new Rule(start, new Word(one));
    rules.add(str4);
    Rule str5 = new Rule(start, new Word(zero));
    rules.add(str5);
    Rule str6 = new Rule(start, new Word(x));
    rules.add(str6);

    //create the rules of the grammar in Chomsky-normal form for variable S
    Rule sr1 = new Rule(s,  new Word(s, t1));
    rules.add(sr1);
    Rule sr2 = new Rule(s, new Word(s, f1));
    rules.add(sr2);
    Rule sr3 = new Rule(s, new Word(l, s1));
    rules.add(sr3);
    Rule sr4 = new Rule(s, new Word(one));
    rules.add(sr4);
    Rule sr5 = new Rule(s, new Word(zero));
    rules.add(sr5);
    Rule sr6 = new Rule(s, new Word(x));
    rules.add(sr6);

    //create the rules of the grammar in Chomsky-normal form for variable T
    Rule tr1 = new Rule(t, new Word(t, f1));
    rules.add(tr1);
    Rule tr2 = new Rule(t, new Word(l, s1));
    rules.add(tr2);
    Rule tr3 = new Rule(t, new Word(one));
    rules.add(tr3);
    Rule tr4 = new Rule(t, new Word(zero));
    rules.add(tr4);
    Rule tr5 = new Rule(t, new Word(x));
    rules.add(tr5);
    
    //create the rules of the grammar in Chomsky-normal form for variable F
    Rule fr1 = new Rule(f, new Word(l, s1));
    rules.add(fr1);
    Rule fr2 = new Rule(f, new Word(one));
    rules.add(fr2);
    Rule fr3 = new Rule(f, new Word(zero));
    rules.add(fr3);
    Rule fr4 = new Rule(f, new Word(x));
    rules.add(fr4);

    //create the rules of the grammar in Chomsky-normal form for variable T1
    Rule t1r = new Rule(t1, new Word(a, t));
    rules.add(t1r);

    //create the rules of the grammar in Chomsky-normal form for variable F1
    Rule f1r = new Rule(f1, new Word(m, f));
    rules.add(f1r);

    //create the rules of the grammar in Chomsky-normal form for variable S1
    Rule s1r = new Rule(s1, new Word(s, r));
    rules.add(s1r);

    //create the rules of the grammar in Chomsky-normal form for variable L
    Rule lr = new Rule(l, new Word(leftBracket));
    rules.add(lr);

    //create the rules of the grammar in Chomsky-normal form for variable R
    Rule rr = new Rule(r, new Word(rightBracket));
    rules.add(rr);

    //create the rules of the grammar in Chomsky-normal form for variable A
    Rule ar = new Rule(a, new Word(plus));
    rules.add(ar);

    //create the rules of the grammar in Chomsky-normal form for variable M
    Rule mr = new Rule(m, new Word(timesSign));
    rules.add(mr);

    //create the context-free grammar
    ContextFreeGrammar cfg = new ContextFreeGrammar(rules);

		return cfg;
	}
}
