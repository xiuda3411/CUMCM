package Entity;
import Map.GameMap;
import Util.ShopUtil;

/**
 * @program: Desert
 * @description: 玩家
 * @author: 宋丽
 * @create: 2020-09-11 11:25
 **/
public class Player {
    public static final Weather[] WEATHER = Weather.FirstLevelWeather();
    //玩家可承重
    private int weight = 1200;
    //玩家初始资金
    private float money = 10000;
    //水
    private int water = 0;
    //食物
    private int food = 0;
    //玩家路线
    private Node[] route = null;
    //玩家游戏时间
    private int day = 0;
    //玩家操作记录
    private String log ="";
    //玩家上一步选择
    private Node pNode = null;

    //玩家每天的选择，0为停留，1为前进
    private int choice = 0;

    public Node getpNode() {
        return pNode;
    }

    public void setpNode(Node pNode) {
        this.pNode = pNode;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Node[] getRoute() {
        return route;
    }

    public void setRoute(Node[] route) {
        this.route = route;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    /**
     * 创建初始玩家
     * @param water 初始水的数量
     * @param food 初始食物的数量
     * @param route 路线
     */
    public Player( int water, int food, Node[] route) {
        this.water = water;
        this.food = food;
        this.money = this.getMoney()-water*5-food*10;
        this.weight = this.getWeight()-water*3-food*2;
        this.route = route;
        this.day = getDay();
    }

    /**
     * 玩家是否依然存活
     */
    public boolean isAlive(){
        if (food >= 0 && water >= 0 && day <= 30 && weight>=0){

            return true;
        }else {
            //System.out.println("food = "+food+" water = "+water+" day = "+day+" weight = "+weight);
            return false;
        }
    }
    //路线中是否包含村庄
    public boolean hasVillage(){
        for (Node n:route
        ) {
            if (n.getNodeId()==15){
                return true;
            }
        }
        return false;
    }
    //获取第一次村庄的路线下标
    public int getVillage(){
        int i = 0;
        for (Node n:route
        ) {
            if (n.getNodeId()==15){
                break;
            }
            i++;
        }
        return i;
    }
    //获取第一次经过矿山的路线下标
    public int getMine(){
        int i = 0;
        for (Node n:route
        ) {

            if (n.getNodeId()==12){
                break;
            }
            i++;
        }
        return i;
    }

    //从第n天到第m天前进所需要的最小水量
    public int minWater(int n,int m){
        int minWater = 0;
        try {
            for (int k = n-1; k < m; k++) {
                if ("晴朗".equals(WEATHER[k].getDiscribe())) {
                    minWater += 5 * 2;
                } else if ("高温".equals(WEATHER[k].getDiscribe())) {

                    minWater += 8 * 2;

                } else if ("沙暴".equals(WEATHER[k].getDiscribe())) {
                    //System.out.println("停留一天");
                    minWater += 10;
                    m++;
                }
            }
            return minWater;
        }catch (Exception e){
            return 0;
        }
    }
    //从第n天到第m天前进所需要的最小食物量
    public int minFood(int n,int m){
        int minFood = 0;
        try {
        //System.out.println(m+"  "+n);
            for (int k = n-1; k < m; k++) {
                if ("晴朗".equals(WEATHER[k].getDiscribe())) {
                    minFood += 7 * 2;
                } else if ("高温".equals(WEATHER[k].getDiscribe())) {
                    minFood += 6 * 2;
                } else if ("沙暴".equals(WEATHER[k].getDiscribe())) {
                    minFood += 10;
                    m++;
                }
            }
            return minFood;
        }catch (Exception e){
            return 0;
        }
    }

    //在n点。计算从n点到m点前进所用天数，m、n为下标
    public int runDays(int n,int m){
        int days = 0;
        int now = day;
        for (int i = 0;i < m-n; i++ ){
            if (now+i>=30){
                break;
            }
            if ("沙暴".equals(WEATHER[now + i].getDiscribe())){
                now++;
                i--;
            }
            days++;
        }
        return days;
    }
    /**
     * 玩家策略，在保证存活的情况下尽可能的获得资金,在矿山必定会停留
     */
    //判断是否购买补给,至少保证走到终点
    public void buyOrNot(int j){
        //第一次经过村庄，补满背包

        if (j == getVillage()){
            ShopUtil.buyWaterAndFood(this,weight/5,weight/5);
        }else{
            //第二次经过村庄，补充能支撑到终点和挖矿时间最长的物资
            //从矿山到终点用时
            int needDays = runDays(j+2,route.length-1);
            //从村庄回到矿山用时
            int costDays = runDays(j,j+2);

            int minFood = minFood(30-needDays-1,30)+minFood(day,day+costDays)+minFood(day+costDays,30-needDays)*3/2;
            int minWater = minWater(30-needDays-1,30)+minWater(day,day+costDays)+minWater(day+costDays,30-needDays)*3/2;
            if (food<minFood){
                log+="第"+day+"天，经过村庄购买必要食物"+(minFood-food)+"份\n";
                ShopUtil.buyFood(this,minFood-food);
            }
            if (water<minWater){
                log+="第"+day+"天，经过村庄购买必要水"+(minWater-water)+"份\n";
                //ShopUtil.buyWater(this,minWater-water);
            }
        }

//        Weather[] weather = Weather.FirstLevelWeather();
//        //路线是否经过村庄
//        boolean flag = this.hasVillage();
//        //到达村庄为第i天
//        int i = this.getVillage();
//        //到达矿山为第j天
//        int j = this.getMine();
//        //如果经过村庄
//        if (flag) {
//            //从村庄一路前进到终点的物资消耗
//            int minFood = minFood(i+1,i+1+runDays(i,route.length-1));
//            int minWater = minWater(i+1,i+1+runDays(i,route.length-1));
//            //后经过村庄只进行必要物资补充
//            if (food<minFood){
//                log+="第"+day+"天，经过村庄购买必要食物"+(minFood-food)+"份\n";
//                ShopUtil.buyFood(this,minFood-food);
//
//            }
//            if (water<minWater){
//                log+="第"+day+"天，经过村庄购买必要水"+(minWater-water)+"份\n";
//                ShopUtil.buyWater(this,minWater-water);
//
//            }
//            //先经过村庄进行足量物资补充，原则上买尽量多的物资，使得在矿山待更长的时间
//            if (i<j){
//                //log+="第"+day+"天，经过村庄购买必要水"+(minWater-water)+"份\n";
//                //补充最少物资后按照1：1补充尽量多的食物和水来确保在矿山待的时间最长
//                ShopUtil.buyWater(this,weight/5);
//                ShopUtil.buyFood(this,weight/5);
//
//            }
//        }
    }
    //是否选择主动去一趟村庄，耗时四天
    public boolean goShoppingOrNot(){
        //现在的天数
        int now = day;
        //矿山的下标
        int mine = getMine();
        //从矿山到终点的消耗
        int needDays = runDays(mine,route.length-1);
        int needWater = minWater(now,now+needDays);
        int needFood = minFood(now,now+needDays);
        //如果从矿山前去村庄
        int costDays = runDays(mine,mine+2);
        int costWater = minWater(now,now+costDays);
        int costFood = minFood(now,now+costDays);
        /*if (needDays<=30-now-5 && food>costFood && water>costWater){
            return true;
        }else */
        if (needDays<=30-now-5 && (food<needFood || water<needWater)){
            return true;
        }
        return false;
    }
    //到达矿山后第二天是否该离开,j为此时玩家走到路线的第j步
    public boolean leaveOrNot(int j) {
        //Weather[] weather = Weather.FirstLevelWeather();
        //第一次到达矿山
        //System.out.println(1);
        //System.out.println("j = "+j+" getMine = "+getMine());
        if ("沙暴".equals(WEATHER[day].getDiscribe())){
            return false;
        }
        if (j == getMine()){
            //System.out.println(2);
            //System.out.println(minFood(day, day + 2));
            if((food>minFood(day,day+2) && food<minFood(day,day+2)+100)||(water>minWater(day,day+2) && water<minWater(day,day+2)+100)){
                //System.out.println(2);
                return true;
            }else {
                //System.out.println(3);
                return false;
            }
            //第二次到达矿山
        }else {
            //System.out.println(4);
            //System.out.println("第二次minFood ="+minFood(day, 30 - day - 1));
            //System.out.println("第二次minWater ="+minWater(day, 30 - day - 1));
            if((food>minFood(day,30-day-1) && food<minFood(day,30-day-1)+100)||(water>minWater(day,30-day-1) && water<minWater(day,30-day-1)+100)){
                //System.out.println(5);
                return true;
            }else {
                //System.out.println(6);
                return false;
            }
        }


        //        //到达村庄为第i天
//        int i = this.getVillage();
//        //到达矿山为第j天
//        int j = this.getMine();
        //矿山在村庄前，选择去村庄购买剩余物资，计算从矿山一路前进到商店的最小物资消耗
//        if (i>j){
//            int minFood = 0;
//            int minWater = 0;
//            int minWeight = 0;
//            int m = j;
//            for (int k = 0; k < i - m; k++) {
//                if ("晴朗".equals(weather[this.day + k+1].getDiscribe())) {
//                    minFood += 7 * 2;
//                    minWater += 5 * 2;
//                    minWeight += 3 * 5 * 2 + 2 * 2 * 7;
//                } else if ("高温".equals(weather[this.day + k+1].getDiscribe())) {
//                    minFood += 6 * 2;
//                    minWater += 8 * 2;
//                    minWeight += 3 * 8 * 2 + 2 * 6 * 2;
//                } else if ("沙暴".equals(weather[this.day + k+1].getDiscribe())) {
//                    //System.out.println("停留一天");
//                    minFood += 10;
//                    minWater += 10;
//                    minWeight += 3 * 10 + 2 * 10;
//                    m--;
//                }
//            }
//            if (minFood<food && minWater<water && (minFood>food-Weather.foodConsum(weather[day])*3
//                    || minWater>water-Weather.waterConsum(weather[day])*3)){
//                return true;
//            }else {
//                return false;
//            }
//
//        }else {
//            //矿山在村庄后，计算从矿山一路前进到终点的最少物资
//            int minFood = minFood(j+1,j+1+runDays(j,route.length-1));
//            int minWater = minWater(j+1,j+1+runDays(j,route.length-1));
//            if (minFood<food && minWater<water && (minFood>food-Weather.foodConsum(weather[day])*3
//                    || minWater>water-Weather.waterConsum(weather[day])*3)){
//                return true;
//            }else {
//                return false;
//            }
//        }

    }
    //在矿山时是否要主动前往村庄购买物资，前往村庄需要花费四天
    //最后结算
    public float sum(){
        log+="第"+day+"天到达终点，剩余食物"+food+"份，剩余水"+water+"份，余额"+money+"\n";
        this.setMoney(this.getMoney()+(float) water*5/2+food*5);
        log+="最终结算剩余"+money;
        return this.getMoney();
    }
}
