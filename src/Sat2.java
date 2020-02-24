import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sat2 {
    List<Integer> listeValuationVariable =new ArrayList<>();
    int nbVariable=0;
    List<List<Integer>> listeClause= new ArrayList<>();
    private Random rand= new Random();
    int H;
    Sat2(String formule, int H,int nbVariable){
        this.H=H;
        this.nbVariable=nbVariable;
        formule=formule.replace("("," ");
        formule=formule.replace(")"," ");
        String[] clauses = formule.split("&");
        for(String clause : clauses){
            List<Integer> locClause= new ArrayList<>();
            String[] vars = clause.split("\\|");
            for(String var : vars){
                int variable=Integer.parseInt(var.trim());
                locClause.add(variable);
            }
            listeClause.add(locClause);
        }
        for(int i=0;i<nbVariable;i++){
            listeValuationVariable.add(-1); //Valuation falsifié pour toutes les variables
        }
    }

    public Pair<Boolean,List<Integer>> solveRandomWalk(){
        Pair<Boolean, List<Integer>> result;

        for(int i=0;i<H;i++){
            Boolean isValid=true;
            for(List<Integer> clause: listeClause){
                if(!isClauseVerified(clause)){
                    changeOneValuation();
                    isValid=false;
                    break; // Un changement a eu lieu, on doit augmenter H.
                }
            }
            if(isValid){
                return new Pair<>(true, listeValuationVariable);
            }
        }
        result=new Pair<>(false, listeValuationVariable);
        return result;
    }
    private Boolean isClauseVerified(List<Integer> clause){
        for(Integer var : clause){
            if(var >0){ //posifif
                if(listeValuationVariable.get(var-1)==1)return true;
            }else{ //negatif
                if(listeValuationVariable.get(Math.abs(var)-1)==-1)return true;
            }
        }
        return false;
    }
    private void changeOneValuation(){
        int nbClause=listeClause.size();
        int randomClause=(int)(rand.nextDouble()*nbClause);
        while(true){
            if(isClauseVerified(listeClause.get(randomClause))){
                randomClause=(randomClause+1)%nbClause;
            }else{
                break;
            }
        }
        int randomVar=(int)(rand.nextDouble()*2);
        int var=listeClause.get(randomClause).get(randomVar);
        listeValuationVariable.set(Math.abs(var)-1,listeValuationVariable.get(Math.abs(var)-1)*(-1));
    }
}
