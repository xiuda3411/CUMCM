package CheckPoint;

import Entity.Node;
import Entity.Player;
import Entity.Weather;
import Map.GameMap;
import Util.RoutesUtil;
import Util.ShopUtil;

import java.util.ArrayList;
import java.util.Map;

/**
 * @program: Desert
 * @description: 第二关
 * @author: 宋丽
 * @create: 2020-09-12 20:19
 **/
public class Second {

    public static final ArrayList<Node> MAP1 = GameMap.getMapOne();
    public static final Node[] ROUTE1 = new Node[]{MAP1.get(0),MAP1.get(1),MAP1.get(2),MAP1.get(3),MAP1.get(4),MAP1.get(12),
            MAP1.get(21),MAP1.get(29),MAP1.get(38),MAP1.get(29),MAP1.get(38),MAP1.get(46), MAP1.get(54),MAP1.get(55),MAP1.get(63)};

    public static final Node[] ROUTE2 = new Node[]{MAP1.get(0),MAP1.get(1),MAP1.get(2),MAP1.get(3),MAP1.get(4),MAP1.get(12),
            MAP1.get(21),MAP1.get(29),MAP1.get(38),MAP1.get(45),MAP1.get(54),MAP1.get(61), MAP1.get(54),MAP1.get(55),MAP1.get(63)};

    public static Player money(int food, int water, float buyFood, float buyWater, int dig1, int dig2,int dig3,Node[] route){
        ArrayList<Node> map1 = GameMap.getMapOne();
        //RoutesUtil.getPaths(map1.get(0),null,map1.get(0),map1.get(26));
        //ArrayList<Node[]> routes = RoutesUtil.routes;
        Weather[] weathers = Weather.FirstLevelWeather();

        float max = 0;

        int mineTimes = 0;
        boolean first = true;
        Player player = new Player(water,food,route);
        for (int j = 0;j < route.length;j++){
            if (!player.isAlive()){
                break;
            }
            if (player.getDay()>0){
                player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()-1].getDiscribe()+",位于"+route[j].getNodeId()+"号地点\n");

            }
            player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
            //System.out.println(mineTimes);
            //到达村庄
            if (route[j].getNodeId() == 39){
                //player.setLog(player.getLog()+"第"+player.getDay()+"天，购买食物"+player.getWeight()/buyFood+"购买水"+player.getWeight()/buyWater+"\n");
                ShopUtil.buyWaterAndFood(player, (int) (player.getWeight()*buyWater),(int) (player.getWeight()*buyFood));
                player.setLog(player.getLog()+"到达村庄39，剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
            }else
                //第一次到达矿山
                if (route[j].getNodeId() == 30 && mineTimes<dig1){
                    mineTimes++;
                    //System.out.println(player.leaveOrNot(j));
                    //如果离开
                    if (mineTimes>=dig1){
                        route[j].consume(weathers[player.getDay()],player,1);
                        player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()].getDiscribe()+"离开矿山\n");
                        player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                    }else {
                        //如果继续挖矿
                         route[j].consume(weathers[player.getDay()], player, 0);
                         player.setLog(player.getLog() + "剩余资金数" + player.getMoney() + " 剩余水量" + player.getWater() + " 剩余食物量" + player.getFood() + "\n");
                        j--;
                    }
                    //第二次
                }else if (route[j].getNodeId() == 30 && mineTimes>=dig1){
                    mineTimes++;
                    if (mineTimes>=dig1+dig2){
                        player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()].getDiscribe()+"离开矿山\n");
                        route[j].consume(weathers[player.getDay()],player,1);
                        player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                    }else {
                        //如果继续挖矿
                        route[j].consume(weathers[player.getDay()], player, 0);
                        player.setLog(player.getLog() + "剩余资金数" + player.getMoney() + " 剩余水量" + player.getWater() + " 剩余食物量" + player.getFood() + "\n");
                        j--;
                    }
                    //第三次挖矿
                }else if (route[j].getNodeId() == 55){
                    mineTimes++;
                    if (mineTimes>=dig1+dig2+dig3){
                        player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()].getDiscribe()+"离开矿山\n");
                        route[j].consume(weathers[player.getDay()],player,1);
                        player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                    }else {
                        //如果继续挖矿
                        player.setLog(player.getLog() + "第" + player.getDay() + "天，" + weathers[player.getDay()].getDiscribe() + "继续挖矿\n");
                        route[j].consume(weathers[player.getDay()], player, 0);
                        player.setLog(player.getLog() + "剩余资金数" + player.getMoney() + " 剩余水量" + player.getWater() + " 剩余食物量" + player.getFood() + "\n");
                        j--;
                    }
                }
                else {
                    if(route[j].getNodeId() == 64){
                        player.sum();
                        break;
                    }else {
                        if ("沙暴".equals(weathers[player.getDay()].getDiscribe())){
                            route[j].consume(weathers[player.getDay()],player,0);
                            j--;
                        }else {
                            route[j].consume(weathers[player.getDay()],player,1);
                            //player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()].getDiscribe()+",前往"+route[j+1].getNodeId()+"号地点\n");
                            player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                        }
                    }

                }
            if (!player.isAlive()){
                //
                //System.out.println(player.getLog());
                break;
            }
        }
//        float sum = player.sum();
//        if (sum > max && player.isAlive()){
//            max = sum;
//            System.out.println(max);
//            Node[] nodes = player.getRoute();
//            for (int k = 0; k < nodes.length; k++){
//                Node node = (Node)nodes[k];
//                if (k<(nodes.length-1)){
//                    System.out.print(node.getNodeId()+"->");
//                }else {
//                    System.out.print(node.getNodeId());
//                }
//            }
//            System.out.println();
//            System.out.println(player.getLog());
//            //System.out.println("路线编号为"+i);
//        }
        return player;
    }
    public static void main(String[] args) {
        float max = 0;

        int times = 0;
        int food = 263;//245/263+3
        int water = 224;//236/224-2
        int dig1 = 5;
        int dig2 = 8;
        int dig3 = 6;
        float buyFood = (float)2/13;
        float buyWater =(float)3/13;
        Player player=null;
        //for (int food = 220;food <= 240;food++){
        //for (int water = 240;water > 220;water--){
        //for (int buyFood = 3;buyFood <= 9;buyFood++){
        //for (int buyWater = 3;buyWater <= 9;buyWater++){
//        for (int dig1 = 4;dig1 <= 5;dig1++){
//            for (int dig2 = 3;dig2 <= 5;dig2++){
//                for (int dig3 = 3;dig3<= 5;dig3++){
        //for (int i = 0;i < 20;i++) {

            player = money(food, water, buyFood, buyWater, dig1, dig2, dig3,ROUTE1);
            if (player.isAlive()) {
                if (player.getMoney() > max) {
                    max = player.getMoney();
                    System.out.println("最大值发生了变化");
                    System.out.println(player.getLog());
                    System.out.println("dig1 = " + dig1 + " dig2 = " + dig2 + " dig3 = " + dig3);
                    //System.out.println("此时i为"+i);
                }
            }
            System.out.println("运行次数" + times++);
       //}
//                }
//            }
//        }
        //}
//        }
//        }
//        }

    }

}
