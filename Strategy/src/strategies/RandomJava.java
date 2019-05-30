package strategies;

import annotations.difficultyLevel;
import annotations.game;
import supporters.difficultyEnum;

import java.util.Random;
import java.util.Vector;

@game
public class RandomJava {
    public RandomJava() {
        System.out.println("Initialized: " + this.getClass().getName().toString());
    }

    @difficultyLevel(level = difficultyEnum.easy)
    public int totalRandom(Vector<String> stringButtonVectorTmp, int size){

        int result =-1;
        System.out.println("Calling in: " + this.getClass().getName().toString() + " easy method");

        Random generator = new Random();

        int position = generator.nextInt(2);
        for (int i = 0; i < size; i++){
            if(stringButtonVectorTmp.get(position).equals(" ")){
                result = position;
            }
        }
        return result;
    }

    @difficultyLevel(level = difficultyEnum.average)
    public int totalRandomAverage(Vector<String> stringButtonVectorTmp, int size){

        int result =-1;
        System.out.println("Calling in: " + this.getClass().getName().toString() + " average method");

        Random generator = new Random();

        int position = generator.nextInt(size);
        for (int i = 0; i < size; i++){
            if(stringButtonVectorTmp.get(position).equals(" ")){
                result = position;
            }
        }
        if(result==-1){
            do {
                for (int i =0; i < size; i++) {
                    if(stringButtonVectorTmp.get(i).equals(""))
                        result = i;
                }
            }while (result==-1);
        }
        return result;
    }

    @difficultyLevel(level = difficultyEnum.expert)
    public int totalRandomExpert(Vector<String> stringButtonVectorTmp, int size){

        int result =-1;
        System.out.println("Calling in: " + this.getClass().getName().toString() + " expert method");

        Random generator = new Random();

        int position;
        do {

            position = generator.nextInt(size);
            for (int i = 0; i < size; i++) {
                if (stringButtonVectorTmp.get(position).equals(" ")) {
                    result = position;
                }
            }

        }while (result==-1);
        return result;
    }
}
