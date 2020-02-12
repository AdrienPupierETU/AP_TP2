import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalk {
     List<List<Double>> matAdj=new ArrayList<>();
     int numberVertice;
     final int N=1000;
     public Random rand= new Random();
     public RandomWalk(int numberVertice){
         for(int i=0;i<numberVertice;i++){
             matAdj.add(new ArrayList<>());
             for(int y=0;y<numberVertice;y++){
                matAdj.get(i).add(0.0);
             }
         }
         this.numberVertice=numberVertice;
     }
     public void createGrapheKn(){
         for(int i=0;i<numberVertice;i++){
             for(int y=0;y<numberVertice;y++){
                 if(i==y){
                     continue;
                 }
                 matAdj.get(i).set(y,1.0/(numberVertice-1));
             }
         }
     }
     public void createGrapheChemin(){
         for(int i=0;i<numberVertice;i++){
             for(int y=0;y<numberVertice;y++){
                 if(i==y){
                     continue;
                 }
                 if(i==0){
                     matAdj.get(0).set(1,1.0);
                     continue;
                 }
                 if(i==numberVertice-1){
                     matAdj.get(i).set(numberVertice-2,1.0);
                     continue;
                 }
                 if(i==y+1){
                     matAdj.get(i).set(y,0.5);
                 }else if(i==y-1){
                     matAdj.get(i).set(y,0.5);
                 }
             }
         }
     }

     public void createSucette(){
         for(int i=0;i<numberVertice/2;i++){
             for(int y=0;y<numberVertice/2;y++){
                 if(i==y){
                     continue;
                 }
                 if(i==(numberVertice/2)-1){
                     matAdj.get(i).set(y,1.0/(numberVertice/2));
                 }else{
                     matAdj.get(i).set(y,1.0/((numberVertice/2)-1));
                 }

             }
         }
         matAdj.get((numberVertice/2)-1).set((numberVertice/2),1.0/(numberVertice/2));
         matAdj.get((numberVertice/2)).set((numberVertice/2)-1,0.5);
         for(int i=numberVertice/2;i<numberVertice;i++){
             for(int y=numberVertice/2;y<numberVertice;y++){
                 if(i==numberVertice-1){
                     matAdj.get(i).set(numberVertice-2,1.0);
                     break;
                 }
                 if(i==y+1){
                     matAdj.get(i).set(y,0.5);
                 }else if(i==y-1){
                     matAdj.get(i).set(y,0.5);
                 }
             }
         }
        System.out.println("test");

     }

     public double hitingTime(int entry, int target){
         if(entry<0){
             entry=0;
         }
         if(entry>numberVertice){
             entry=numberVertice;
         }
         if(target<0){
             target=0;
         }
         if(target>numberVertice){
             target=numberVertice;
         }
         int courant=entry;
         double sumI=0;
         for(int i=0;i<N;i++){
             courant=entry;
             int nbIte=1;
             while(true){
                 if(courant==target){
                     sumI+=nbIte;
                     break;
                 }
                 int Nbsucessor= getNbSucessor(courant);
                 double next=(rand.nextDouble()*Nbsucessor);
                 courant=getIemeSucessor(courant,(int)next);
                 nbIte++;
             }


         }
         return sumI/N;
     }

    public double coverTime(int entry){
        if(entry<0){
            entry=0;
        }
        if(entry>numberVertice){
            entry=numberVertice;
        }

        int courant;
        double sumI=0;
        for(int i=0;i<N;i++){
            courant=entry;
            List<Integer> couvert= new ArrayList<>();
            couvert.add(entry);
            int nbIte=1;
            while(true){
                if(couvert.size()==numberVertice){
                    sumI+=nbIte;
                    break;
                }
                int Nbsucessor= getNbSucessor(courant);
                double next=(rand.nextDouble()*Nbsucessor);
                courant=getIemeSucessor(courant,(int)next);
                if(!couvert.contains(courant)){
                    couvert.add(courant);
                }
                nbIte++;
            }


        }
        return sumI/N;
    }

     public int getNbSucessor(int entry){
         int nb=0;
         for(int i=0;i<numberVertice;i++){
             if(matAdj.get(entry).get(i)>0.0) nb++;
         }
         return nb;
     }
     public int getIemeSucessor(int entry,int ieme){
         int nb=0;
         int succ=-1;
         for(int i=0;i<numberVertice;i++){
             if(matAdj.get(entry).get(i)>0.0) nb++;
             if(nb==ieme+1){
                 succ=i;
                 break;
             }
         }
         return succ;
     }
}
