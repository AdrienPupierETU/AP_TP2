public class app {


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
        System.out.println("Random Walk coveer time Sucette=2*10 en partant de u :"+rwSucette.coverTime((rwSucette.numberVertice/2)-1));
        System.out.println("Random Walk coveer time Sucette=2*10 en partant de v :"+rwSucette.coverTime((rwSucette.numberVertice)-1));
    }
}
