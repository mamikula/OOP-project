package agh.ics.oop;
import java.util.Arrays;
import java.util.Random;

public class Genes {
    private int[] genes;
    private int size;
    private int genesNum;

    public int[] getGenes() {
        return genes;
    }

    public Genes(int genesNum, int size) {
        genes = new int[32];
        this.size = size;
        this.genesNum = genesNum;
        fillRandom();
//        makeProprielyGen();
    }


    public Genes(Animal father, Animal mother) {
        this(8, 32);
        int ena = (int) (father.getEnergy() + mother.getEnergy());
        int partOfGenes = (int) (32*(father.getEnergy() / ena));
        //0 -> left
        //1 -> right
        float side = (float) Math.random();
        if(side < 0.5){
            for(int i = 0; i < partOfGenes; i++){
                genes[i] = father.getGenes().getGenes()[i];
                }
            for(int i = partOfGenes; i < 32; i++){
                genes[i] = mother.getGenes().getGenes()[i];
            }
        }
        else{
            for(int i = partOfGenes; i < 32; i++){
                this.genes[i] = father.getGenes().getGenes()[i];
            }
            for(int i = 0; i < partOfGenes; i++){
                this.genes[i] = mother.getGenes().getGenes()[i];
            }
        }
        makeProprielyGen();
    }


    private void fillRandom() {
        for (int i = 0; i < 32; i++) {
            genes[i] = (int) (Math.random() * 8);
        }
        Arrays.sort(this.genes);
    }

    private void makeProprielyGen() {
        int[] valid = new int[8];
        boolean flag = true;
        while(flag) {
            flag = false;
            for (int i = 0; i < 8; i++) valid[i] = 0;
            for (int i = 0; i < 32; i++) {
                valid[this.genes[i]] += 1;
            }
            for (int i = 0; i < 8; i++) {
                if (valid[i] == 0) flag = true;
            }

            if (flag) {
                for (int i = 0; i < 8; i++) {
                    if (valid[i] == 0) {
                        this.genes[(int) (Math.random() * 32)] = i;
                    }
                }
            }
        }
        Arrays.sort(this.genes);
    }

    public int returnRandomGen() {
        int rand = (int) (Math.random() * (size));
        return this.genes[rand];
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < size; i++) {
            result = result + " " + Integer.toString(genes[i]);
        }
        return result;
    }
}
    





