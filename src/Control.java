

import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * The type Control.
 *
 * @author Lv and Zhou
 * @Description cat, dog Animal类
 * strength 攻击力度
 * operation 操作(0：表示选择道具的输入操作，1表示攻击力度的输入操作
 * over 比赛是否结束，True结束，False没结束
 * round 回合数
 * roundOwner 当前回合所有者 0：cat，1：dog
 * @date 2020.3.27
 */
public class Control {
    private Animal cat;
    private Animal dog;
    private int strength;
    private int operation;
    private int operationNum;
    private boolean over;
    private int round;
    private int roundOwner;
    /**
     * The constant MAXSTRENGTH.
     * 最大输入力度
     */
    public static final int MAXSTRENGTH = 10;
    /**
     * The constant MINSTRENGTH.
     * 最小输入力度
     */
    public static final int MINSTRENGTH = 0;
    /**
     * The constant MINHITSTRENGTH.
     * 最小击中力度
     */
    public static final int MINHITSTRENGTH = 4;
    /**
     * The constant MAXHITSTRENGTH.
     * 最大击中力度
     */
    public static final int MAXHITSTRENGTH = 6;
    /**
     * The constant TOOLOP.
     * 表示当前回合的选择道具阶段
     */
    public static final int TOOLOP = 1;
    /**
     * The constant ATKOP.
     * 表示当前回合的输入攻击力度阶段
     */
    public static final int ATKOP = 2;
    /**
     * The constant TOOLOPLOWBORDER.
     * 选择道具阶段输入的下界
     */
    public static final int TOOLOPLOWBORDER = 1;
    /**
     * The constant TOOLOPHIGHBORDER.
     * 选择道具阶段输入的上界
     */
    public static final int TOOLOPHIGHBORDER = 2;


    /**
     * Instantiates a new Control.
     */
    public Control(){
        this.cat = new Animal("cat");
        this.dog = new Animal("dog");
        this.strength = 0;
        this.over = false;
        this.round = 1;
        this.operation = TOOLOP;
        this.roundOwner = 0;
    }

    public void setRoundOwner(int roundOwner) {
        this.roundOwner = roundOwner;
    }

    public int getRoundOwner() {
        return roundOwner;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public void setOperationNum(int operationNum) {
        this.operationNum = operationNum;
    }

    public boolean getOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public Animal getCat() {
        return cat;
    }

    public Animal getDog() {
        return dog;
    }

    /**
     * Is numeric boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isNumeric(String str){
        if(!str.isEmpty()){
            for (int i = str.length();--i>=0;){
                if (!Character.isDigit(str.charAt(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Output.
     */
    public void output(){
        System.out.println("Cat                                                 Dog");
        System.out.println("HP:" + this.cat.getHP() +"                                              Dog:" + this.dog.getHP());
        System.out.println("道具数:" + this.cat.getTool().size() +"                                             道具数:" + this.dog.getTool().size());
    }

    /**
     * Play.
     */
    public void play(){
        while (!this.over){
            System.out.println("ROUND  " + this.getRound());
            this.setRound(this.getRound()+1);
            this.output();
            System.out.println("猫猫的回合  go!");
            this.getInput();
            this.useTool();
            this.getInput();
            this.attack();
            this.cat.endUsingTool();
            this.gameOver();
            if(over){ break; }
            this.changeRound();
            System.out.println("狗狗的回合  go!");
            this.getInput();
            this.useTool();
            this.getInput();
            this.attack();
            this.dog.endUsingTool();
            this.gameOver();
            this.changeRound();
            System.out.println("==========================================================");
        }
    }

    /**
     * Attack int.
     *
     * @return the int
     */
    public int attack(){
        if(this.getStrength()>=MINHITSTRENGTH && this.getStrength()<=MAXHITSTRENGTH){
            if(this.roundOwner == 0){
                this.cat.attack(this.dog,cat.getATK());
                return 0;
            }else if(this.roundOwner == 1){
                this.dog.attack(this.cat,dog.getATK());
                return 1;
            }else{
                System.out.println("error");
                return -1;
            }
        }else{
            System.out.println("攻击没有命中");
            return 2;
        }
    }

    /**
     * Game over int.
     *检测游戏是否已经结束
     * @return the int
     */
    public int gameOver(){
        if(this.dog.getHP()<=0){
            this.over = true;
            System.out.println("Cat WIN");
            return 0;
        }
        if(this.cat.getHP()<=0){
            this.over = true;
            System.out.println("Dog WIN");
            return 1;
        }
        return -1;
    }


    /**
     * Get input int.
     * 在一个回合中获取玩家的两次输入。
     *
     * @return the int
     */
    public int getInput(){
        Scanner scanner= new Scanner(System.in);
        if(this.operation == TOOLOP){
            System.out.println("请选择你的操作（输入操作前的数字）");
            System.out.println("1.不使用道具");
            System.out.println("2.使用道具");
//            System.out.println("2.使用道具1");
//            System.out.println("3.使用道具2");
//            System.out.println("4.使用道具3");
//            System.out.println("5.使用道具4");

            String index;
            int ioperationNum;
            while (true){
                index = scanner.nextLine();
                if(isNumeric(index)){
                    ioperationNum = Integer.parseInt(index);
                    if(ioperationNum <= TOOLOPHIGHBORDER && ioperationNum >= TOOLOPLOWBORDER){
                        this.setOperationNum(ioperationNum);
                        this.setOperation(ATKOP);
                        return this.operationNum;
                    }else{
                        System.out.println("1选择道具有误，只能输入1或者2");
                    }
                }else {
                    System.out.println("选择道具有误，只能输入1或者2");
                }
            }

        }
        else if(this.operation == ATKOP) {
            System.out.println("请输入你的攻击力度（0-10的整数）");
            String index;
            int istrength;
            while (true){
                index = scanner.nextLine();
                if(isNumeric(index)){
                    istrength = Integer.parseInt(index);
                    if(istrength <= MAXSTRENGTH && istrength >= MINSTRENGTH){
                        this.setStrength(istrength);
                        this.setOperation(TOOLOP);
                        return this.strength;
                    }else{
                        System.out.println("输入力度不在正确范围内，请重新输入");
                    }
                }else {
                    System.out.println("输入力度不在正确范围内，请重新输入");
                }
            }
        }else{
            return -1;
        }
    }

    /**
     * Use tool int.
     *
     * @return the int
     */
    public int useTool(){
        if(this.operationNum == TOOLOPHIGHBORDER){
            if(this.roundOwner == 0){
                return cat.useTool();
            }else if(this.roundOwner == 1){
                return dog.useTool();
            }else{
                return -1;
            }
        }else{
            return -1;
        }
    }

    /**
     * Change round.
     */
    public void changeRound(){
        this.setRoundOwner((this.roundOwner + 1) % 2);
    }


    /**
     * Set input.
     * 仅是为了测试而写的函数，在程序运行过程中无意义
     *
     * @param input the input
     */
    public void setInput(int input){
        if(this.operation == TOOLOP){
            this.setOperationNum(input);
        }else if(this.operation == ATKOP){
            this.setStrength(input);
        }else{
            return;
        }
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args){
        Control control = new Control();
        control.play();
    }
}
