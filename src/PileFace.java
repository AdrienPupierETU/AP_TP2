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
        init_mat(transMat, nb_vertices);
        //Il y a deux etats de victoire le 3 et le 6
        //Au 3 le joueur 1 gagne au 6 c'est le joueur 2 qui gagne
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

    public void init_mat(List<List<Double>> M, int NBvertices){
        for(int i=0;i<NBvertices;i++){
            M.add(new ArrayList<>());
            for(int y=0;y<NBvertices;y++){
                M.get(i).add(0.0);
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
    public void prodMat(int n ,List<List<Double>> M) {
        List<List<Double>> prodMat = new ArrayList<>();
        init_mat(prodMat, 7);


        for (int nb_ite = 0; nb_ite < n; nb_ite++){
            for (int i = 0; i < M.size(); i++) {
                for (int j = 0; j < M.size(); j++) {
                    for (int k = 0; k < M.size(); k++) {
                        double proba = M.get(i).get(k) * M.get(k).get(j);
                        prodMat.get(i).set(j, proba);
                    }
                }
            }
        }
    }

    void init_new_chain(){
        init_mat(matChain, 7);
        matChain.get(0).set(1,.5);
        matChain.get(0).set(2,.5);
        matChain.get(1).set(2,.5);
        matChain.get(1).set(4,.5);
        matChain.get(2).set(3,.5);
        matChain.get(2).set(5,.5);
        matChain.get(3).set(0,1.0);
        matChain.get(4).set(2,.5);
        matChain.get(4).set(6,.5);
        matChain.get(5).set(5,.5);
        matChain.get(5).set(1,.5);
        matChain.get(6).set(0,1.0);

        affiche_mat(matChain);
        System.out.println();
        prodMat(N, matChain);

        System.out.println("Matrice apres avoir itéréb");
        affiche_mat(matChain);
    }
}
