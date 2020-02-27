import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PileFace {
    List<List<Double>> transMat = new ArrayList<>();
    int nb_vertices = 7;
    int nb_games = 500;
    int N = 100;
    Random rand = new Random();

    double nb_j1Win = 0;
    double nb_j2Win = 0;
    double probaJ1 = 0;
    double probaJ2 = 0;

    List<List<Double>> matChain = new ArrayList<>();

    public PileFace(){
        init_mat();
        transMat.get(0).set(1,.5);
        transMat.get(0).set(2,.5);
        transMat.get(1).set(2,.5);
        transMat.get(1).set(4,.5);
        transMat.get(2).set(3,.5);
        transMat.get(2).set(5,.5);
        transMat.get(4).set(2,.5);
        transMat.get(4).set(6,.5);
        transMat.get(5).set(5,.5);
        transMat.get(5).set(1,.5);
    }

    public void affiche_mat(List<List<Double>> M){
        for(int i = 0; i < M.size(); i++) {
            System.out.println(M.get(i));
        }
    }

    public void init_mat(){
        for(int i=0;i<nb_vertices;i++){
            transMat.add(new ArrayList<>());
            for(int y=0;y<nb_vertices;y++){
                transMat.get(i).add(0.0);
            }
        }
    }

    private int getNbSucessor(int entry){
        int nb=0;
        for(int i=0;i<nb_vertices;i++){
            if(transMat.get(entry).get(i)>0.0) nb++;
        }
        return nb;
    }

    private int getIemeSucessor(int entry,int ieme){
        int nb=0;
        int succ=-1;
        for(int i=0;i<nb_vertices;i++){
            if(transMat.get(entry).get(i)>0.0) nb++;
            if(nb==ieme+1){
                succ=i;
                break;
            }
        }
        return succ;
    }

    private void game(){
        int debut = 0;

        int courant;
        double sumI=0;
        for(int i=0;i<N;i++){
            courant=debut;
            List<Integer> couvert= new ArrayList<>();
            couvert.add(debut);
            int nbIte=1;
            while(true){
                if(couvert.size()==nb_vertices){
                    sumI+=nbIte;
                    break;
                }
                int Nbsucessor= getNbSucessor(courant);
                if(Nbsucessor == 0){
                    //System.out.println("L'etat courant est : "+courant);
                    if(courant == 3) nb_j1Win++;
                    else if(courant == 6) nb_j2Win++;
                    return;
                }
                double next=(rand.nextDouble()*Nbsucessor);
                courant=getIemeSucessor(courant,(int)next);
                if(!couvert.contains(courant)){
                    couvert.add(courant);
                }
                nbIte++;
            }
        }
    }

    public void probaWin(){
        for(int i = 0; i<nb_games; i++){
            game();
        }

        probaJ1 = nb_j1Win/500;
        probaJ2 = nb_j2Win/500;

        System.out.println("Le joueur 1 a "+ probaJ1*100 +"% de chance de gagner");
        System.out.println("Le joueur 2 a "+ probaJ2*100 +"% de chance de gagner");
    }

    //Multiplie n fois la matrice M par elle meme
    public List<List<Double>> prodMat(List<List<Double>> M, int n){
        List<List<Double>> prodMat = new ArrayList<>();
        prodMat.add(new ArrayList<>());
        prodMat.add(new ArrayList<>());

        for(int i = 0; i < n; i++){
            prodMat.get(0).add(Math.pow(M.get(0).get(0),2) + M.get(0).get(1)*M.get(1).get(0));
            prodMat.get(0).add((M.get(1).get(0)*M.get(1).get(0)) + (M.get(0).get(1)*M.get(1).get(1)));
            prodMat.get(1).add((M.get(1).get(0)*M.get(0).get(0))+ (M.get(1).get(1)*M.get(1).get(0)));
            prodMat.get(1).add(Math.pow(M.get(1).get(0),2) + Math.pow(M.get(1).get(1),2));
        }
        return prodMat;
    }

    void init_chain(){
        for(int i=0;i<3;i++){
            matChain.add(new ArrayList<>());
            for(int y=0;y<3;y++){
                matChain.get(i).add(0.0);
            }
        }
        matChain.get(0).set(1,probaJ1);
        matChain.get(0).set(2,probaJ2);
        matChain.get(1).set(1,1.0);
        matChain.get(2).set(2,1.0);
        affiche_mat(matChain);
    }
}
