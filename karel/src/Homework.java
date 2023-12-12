import stanford.karel.SuperKarel;


public class Homework extends SuperKarel {
    public static int karelMovesConter = 0;
    public void run() {
        setBeepersInBag(1000);
        int x = getAxis(), y = getAxis();
        System.out.println(x+" "+y);
        divideMap(x, y);
        System.out.println("Total moves: "+karelMovesConter);
        karelMovesConter = 0;
    }

    public int getAxis(){
        int x = 1;
        while(true){
            if(frontIsBlocked())
                break;
            moveAndcount();
            x++;
        }
        turnLeft();
        return x;
    }

    public void divideMap(int x, int y){
        //divideBy4
        if(x >= 3 && y >= 3){
            //optimizing the number of moves in the even x even case where we will not walk on the same path to minimize moves
            if((x % 2) == 0 && (y % 2) == 0){
                moveHalf(x);
                turnLeft();
                fillBeepersInAxis(y/2 - 1);
                turnLeft();
                fillBeepersInAxis(x);
                turnRight();
                moveAndcount();
                turnRight();
                fillBeepersInAxis(x/2 - 1);
                turnLeft();
                fillBeepersInAxis(y);
                turnRight();
                moveAndcount();
                turnRight();
                fillBeepersInAxis(y/2 - 1);
                turnLeft();
                fillBeepersInAxis(x);
                turnRight();
                moveAndcount();
                turnRight();
                fillBeepersInAxis(x/2 - 1);
                turnLeft();
                fillBeepersInAxis(y);
            }
            else{
                //even x
                if(x % 2 == 0){
                    moveHalf(x);
                    turnLeft();
                    fillBeepersInAxis(y);
                    turnRight();
                    moveAndcount();
                    turnRight();
                    fillBeepersInAxis(y);
                    turnAround();

                }
                //odd x
                else{
                    moveHalf(x);
                    turnLeft();
                    fillBeepersInAxis(y);
                    turnAround();
                }

                //even y
                if(y % 2 == 0){
                    moveHalf(y);
                    turnRight();
                    fillBeepersInAxis(x);
                    turnLeft();
                    moveAndcount();
                    turnLeft();
                    fillBeepersInAxis(x);
                    turnLeft();
                    moveAndcount();
                    turnLeft();
                    fillBeepersInAxis(x/2);
                }
                //odd y
                else{
                    moveHalf(y);
                    turnRight();
                    fillBeepersInAxis(x);
                    turnAround();
                    fillBeepersInAxis(x);

                }
        }
        }

        //special corner-case four: one of the axes is less than 3 and the shape of the map look like a bar
        else if(x > 6 || y > 6){
            if (y > x){//flip map to look at it horizontal
                int t = x;
                x = y;
                y = t;
                turnLeft();
            }

            int extraBeepers = (x - 3) % 4;
            for(int i = 1;i <= extraBeepers;i++){//fill optimization to get four parts
                if(noBeepersPresent()) {
                    putBeeper();
                    if (rightIsClear())
                        putBeeperAbove();
                    else if(leftIsClear())
                        putBeeperBelow();
                }
                moveAndcount();
            }

            fillBeepersBy((x-3)/4);
        }

        //2x2 corner-case
        else if (x == 2 && y == 2) {
            putBeeper();
            moveAndcount();
            turnLeft();
            moveAndcount();
            putBeeper();
        }

        //divideBy3,2,1 when one of the axes' length is more than 2
        else{
            if (y > x){//flip map to look at it horizontal
                int t = x;
                x = y;
                y = t;
                turnLeft();
            }
            fillBeepersBy(1);
            if(x>2) {//for maps 1x2 and 2x1 that cant be divided
                putBeeper();
                if (rightIsClear())
                    putBeeperAbove();
                else if(leftIsClear())
                    putBeeperBelow();
            }
        }
    }

    //fill beepers by "step/s"
    public void fillBeepersBy(int step){
        int flag = 0;
        while(true){
            if(frontIsBlocked())
                break;
            if(flag == step){
                if(noBeepersPresent()){
                    putBeeper();
                    if (rightIsClear())
                        putBeeperAbove();
                    else if(leftIsClear())
                        putBeeperBelow();
                }
                flag = 0;
                moveAndcount();
            }
            moveAndcount();
            flag++;
        }
    }

    //put beeper above
    public void putBeeperAbove() {
        turnRight();
        moveAndcount();
        if (noBeepersPresent())
            putBeeper();
        turnAround();
        moveAndcount();
        turnRight();
    }

    //put beeper below
    public void putBeeperBelow() {
        turnLeft();
        moveAndcount();
        if (noBeepersPresent())
            putBeeper();
        turnAround();
        moveAndcount();
        turnLeft();
    }

    //move to the half
    public void moveHalf(int axis){
        int center;
        if(axis % 2 == 1)
            center = (axis / 2);
        else
            center = (axis / 2) - 1;

        for(int i = 1; i <= center;i++)//move half
            moveAndcount();
    }

    //fill beepers in axis with specific length
    public void fillBeepersInAxis(int length){
        int i = 1;
        while(true){//fill beepers in axis
            if(noBeepersPresent())
                putBeeper();
            if(frontIsBlocked() || i > length)
                break;
            moveAndcount();
            i++;
        }
    }

    //to move and counting them
    public void moveAndcount(){
        move();
        karelMovesConter++;
    }

}

