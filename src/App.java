
//import javafx.util.Pair;

import java.lang.Math;
import java.util.List;
import java.util.Random;

public class App {
    private static Random rand= new Random();
    public static void main(String[] argv){
        RandomWalk rw= new RandomWalk(10);
        rw.createGrapheKn();
        System.out.println("Random Walk hitting time kn=10 :"+rw.hitingTime(0,8));
        System.out.println("Random Walk cover time kn=10 :"+rw.coverTime(0));
        RandomWalk rwChemin = new RandomWalk(10);
        rwChemin.createGrapheChemin();
        System.out.println("Random Walk Whole path time chemin=10 :"+rwChemin.hitingTime(0,rwChemin.numberVertice-1));
        RandomWalk rwSucette = new RandomWalk(2*10);
        rwSucette.createSucette();
        System.out.println("Random Walk cover time Sucette=2*10 en partant de u :"+rwSucette.coverTime((rwSucette.numberVertice/2)-1));
        System.out.println("Random Walk cover time Sucette=2*10 en partant de v :"+rwSucette.coverTime((rwSucette.numberVertice)-1));
        RandomWalk rwGrid= new RandomWalk(4*4); // 4*4 pour une grille de taille 4*4
        rwGrid.createGrid();
        System.out.println("Random walk hitting time grid :"+rwGrid.hitingTime(0,15));
        System.out.println("Distance vole oiseau : "+Math.sqrt(Math.pow(4,2)+Math.pow(4,2)));

        /*--------------------------2SAT-------------------------------------*/
        /*bSat2 sat = new Sat2("(1 | 2)&(2 |-3)&(-1|4)&(-4|-2)",20,4);
        Pair<Boolean, List<Integer>> result =sat.solveRandomWalk();
        if(result.getKey()){
            System.out.println("Formule satisfaisable Valuation  : "+result.getValue().toString());
        }else{
            System.out.println("Formule non satisfaisable" );
        }
        double nbTrue=0.0;
        for(int i=0;i<1000;i++){
            String formule=generateSat2String(20,10);
            Sat2 sat2=new Sat2(formule,150,10);
            Pair<Boolean, List<Integer>> result2 =sat2.solveRandomWalk();
            if(result2.getKey()){
                nbTrue++;
            }
        }
        System.out.println("Sur 1000 formule avec 10 variable et 20 clause géneré aleatoirement "+nbTrue+ " sont valide ce qui represente : "+(nbTrue/1000)*100+"%");
        */
        /*--------------------------Pile/Face-------------------------------------*/
        PileFace pileFace = new PileFace();
        pileFace.probaWin();
        pileFace.init_new_chain();
    }
    private static String generateSat2String(int nbClause,int nbVariable){
        StringBuilder formule= new StringBuilder();
        for(int i=0;i<nbClause;i++){
            StringBuilder clause= new StringBuilder("(");
            for(int y=0;y<2;y++){
                int var=(int) (rand.nextDouble()*nbVariable)+1;
                int sign=(int) (rand.nextDouble()*2);
                if(sign==1){ // negatif
                    var*=-1;
                }
                clause.append(var);
                if(y==0) clause.append("|");
                if(y==1) clause.append(")");
            }
            formule.append(clause);
            if(i!=nbClause-1){
                formule.append("&");
            }
        }
        return formule.toString();
    }
}
