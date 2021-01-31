import computation.contextfreegrammar.*;
import computation.parser.*;
import computation.parsetree.*;
import jdk.nashorn.internal.ir.Terminal;
import sun.net.spi.nameservice.dns.DNSNameService;
import computation.derivation.*;
import java.util.*;

public class Parser implements IParser {

  public Derivation parseTreeDerivation; //the derivation generated in isInLanguage method which needs to be used in the generateParseTree method
  public int derivationSteps; //# of steps w's derivation has

  public boolean isInLanguage(ContextFreeGrammar cfg, Word w){
    
    int step=0; //counts the number of steps performed in a derivation
    List<Word> rulesRHS = new ArrayList();
    List<Derivation> activeDerivations = new ArrayList();
    List<Rule> grammarRules = cfg.getRules();
    Variable startVariable = cfg.getStartVariable();
    Word startWord = variableToWord(startVariable);
    Derivation startVariableDerivation = new Derivation(startWord);
    activeDerivations.add(startVariableDerivation);

    if(w.length()>=1){
      derivationSteps = 2 * w.length() - 1; 
    }
    else{
      derivationSteps=1;
    } 

    while(step<derivationSteps){

      List<Derivation> newActiveList = new ArrayList();
      List<Derivation> newDerivations = new ArrayList();

      for(Derivation d: activeDerivations){
        Word currentWord = d.getLatestWord();
        newDerivations = generateDerivationList(grammarRules, d, step);
        for(Derivation der: newDerivations){
          newActiveList.add(der);
        }   
        newDerivations = new ArrayList();
      } 
    activeDerivations=newActiveList;
    step++;
    }

    //check if the word was created by any of the resulted derivations
    //check first if the result word is formed only of terminals 
    boolean result = false;
    for(Derivation der: activeDerivations){
      Word word=der.getLatestWord();
      int valid=0;
      for(Symbol s: word){
        if(s.isTerminal()){
          valid++;
        }
      }
      if(valid==word.length() && result==false){
        if(word.equals(w)){
          result = true;
          parseTreeDerivation = der;

        }
      }
    } 
    return result;
  }

  //this method generates all possible 1-step derivations for a word
  private List generateDerivationList(List<Rule> rules, Derivation deriv, int step){
    Word lastWord = deriv.getLatestWord();
    List<Derivation> newDerivationList = new ArrayList();
    Derivation initialDerivation = new Derivation(deriv);
    int index=0;
    for(Symbol s: lastWord){
      if(s.isTerminal()){
        index++;
        continue;
      }
      else{
        for(Rule r: rules){
        initialDerivation = new Derivation(deriv);
        Word ruleWord = variableToWord(r.getVariable());
        if(ruleWord.equals(symbolToWord(s))){
          Word derivationWord = lastWord.replace(index, r.getExpansion());
          initialDerivation.addStep(derivationWord, r, step);
          Derivation newDerivation = initialDerivation;
          newDerivationList.add(newDerivation);
        }
        else{
          continue;
        }
      } 
    }
      break;
    }
    return newDerivationList;
  }

  //this method transforms variable to word
  private Word variableToWord(Variable var){
    Word w = new Word(var);
    return w;
  }

  //this method transforms symbol to word
  private Word symbolToWord(Symbol symbol){
    Word w = new Word(symbol);
    return w;
  }

  //this method prints each step in a derivation
  public void printDerivation(Derivation d) {
    for(Step s : d) {
      System.out.println(s);
    }
  }

  public ParseTreeNode generateParseTree(ContextFreeGrammar cfg, Word w) {
    
    ParseTreeNode treeToPrint;

    if(isInLanguage(cfg, w) == false){
      treeToPrint = null;
    }
    else{
      List<ParseTreeNode> finalNodes = new ArrayList();
      ParseTreeNode nodeToAdd;
      for(Step s: parseTreeDerivation){ 
        Rule stepRule = s.getRule();
        if (stepRule != null){
          Word root = variableToWord(stepRule.getVariable());
          Word leaf = stepRule.getExpansion();
          if(leaf.isTerminal()){
            ParseTreeNode leafNode = new ParseTreeNode(leaf.get(0));
            ParseTreeNode treeToAdd = new ParseTreeNode(root.get(0), leafNode);
            finalNodes.add(treeToAdd);
          }
          else{
              List<ParseTreeNode> children = new ArrayList();
              for(Symbol child: leaf){
                  for(ParseTreeNode childTree: finalNodes){
                    if(childTree.getSymbol()==child){
                      children.add(childTree);
                    }
                  }
                }
                finalNodes.add(new ParseTreeNode(root.get(0), children.get(0), children.get(1)));
          }
        }
        else{
          break;
        }
    }
      treeToPrint = finalNodes.get(finalNodes.size()-1);
    }
  return treeToPrint;
  }
}