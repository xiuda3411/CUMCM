package CheckPoint;
import Entity.Node;
import Entity.Player;
import Entity.Weather;
import Map.GameMap;
import Util.ShopUtil;

import java.util.ArrayList;

/**
 * @program: Desert
 * @description: 测试
 * @author: 宋丽
 * @create: 2020-09-12 15:37
 **/
public class FirstTest {
    /**
     *
     * @param food 起始点购买的食物 150-250
     * @param water 起始点购买的水 200-300
     * @param buyFood 在村庄购买食物的分母3-9
     * @param buyWater 在村庄购买水的分母3-9
     * @param dig1 第一次挖矿天数4-8天
     * @param dig2 第二次挖矿天数2-4天
     * @return
     */
    public static Player money(int food,int water,float buyFood,float buyWater,int dig1,int dig2){
        ArrayList<Node> map1 = GameMap.getMapOne();
        //RoutesUtil.getPaths(map1.get(0),null,map1.get(0),map1.get(26));
        //ArrayList<Node[]> routes = RoutesUtil.routes;
        Weather[] weathers = Weather.FirstLevelWeather();
        Node[] route = new Node[]{map1.get(0),map1.get(24),map1.get(23),map1.get(22),map1.get(21),map1.get(8),
                map1.get(14),map1.get(12),map1.get(11),map1.get(12),map1.get(14),map1.get(12),map1.get(11),map1.get(13),
                map1.get(15),map1.get(16),map1.get(20),map1.get(26)};

        float max = 0;

        int mineTimes = 0;
        boolean first = true;
        Player player = new Player(water,food,route);
        for (int j = 0;j < route.length;j++){
            //System.out.println(mineTimes);
            //到达村庄
            if (route[j].getNodeId() == 15){
                //player.setLog(player.getLog()+"第"+player.getDay()+"天，购买食物"+player.getWeight()/buyFood+"购买水"+player.getWeight()/buyWater+"\n");
                ShopUtil.buyWaterAndFood(player, (int) (player.getWeight()*buyWater),(int) (player.getWeight()*buyFood));
                player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
            }else
                //第一次到达矿山
                if (route[j].getNodeId() == 12 && mineTimes<dig1){
                    mineTimes++;
                    //System.out.println(player.leaveOrNot(j));
                    //如果离开
                    if (mineTimes>=dig1 && !"沙暴".equals(weathers[player.getDay()].getDiscribe())){
                        player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()].getDiscribe()+"离开矿山\n");
                        player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                        route[j].consume(weathers[player.getDay()],player,1);
                    }else {
                        //如果继续挖矿
                        player.setLog(player.getLog() + "第" + player.getDay() + "天，" + weathers[player.getDay()].getDiscribe() + "继续挖矿\n");
                        player.setLog(player.getLog() + "剩余资金数" + player.getMoney() + " 剩余水量" + player.getWater() + " 剩余食物量" + player.getFood() + "\n");
                        route[j].consume(weathers[player.getDay()], player, 0);
                        j--;
                    }
                    //第二次
                }else if (route[j].getNodeId() == 12 && mineTimes>=dig1){
                    mineTimes++;
                    if (mineTimes>=dig1+dig2 && !"沙暴".equals(weathers[player.getDay()].getDiscribe())){
                        player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()].getDiscribe()+"离开矿山\n");
                        player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                        route[j].consume(weathers[player.getDay()],player,1);
                    }else {
                        //如果继续挖矿
                        player.setLog(player.getLog() + "第" + player.getDay() + "天，" + weathers[player.getDay()].getDiscribe() + "继续挖矿\n");
                        player.setLog(player.getLog() + "剩余资金数" + player.getMoney() + " 剩余水量" + player.getWater() + " 剩余食物量" + player.getFood() + "\n");
                        route[j].consume(weathers[player.getDay()], player, 0);
                        j--;
                    }
                }
                else {
                    player.setLog(player.getLog()+"第"+player.getDay()+"天，"+",前往"+route[j].getNodeId()+"号地点\n");
                    player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                    if(route[j].getNodeId() == 27){
                        player.sum();
                        break;
                    }else {
                        if ("沙暴".equals(weathers[player.getDay()].getDiscribe())){
                            route[j].consume(weathers[player.getDay()],player,0);
                            j--;
                        }else {
                            route[j].consume(weathers[player.getDay()],player,1);
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
        float buyFood = (float) 2/13;
        float buyWater =(float)3/13 ;
        int times = 0;
        int food = 334;//223+3/325
        int water = 177;//251-2/183
        int dig1 = 7;
        int dig2 = 5;
        Player player=null;
        //for (int food = 220;food <= 240;food++){
            //for (int water = 240;water > 220;water--){
                //for (int buyFood = 3;buyFood <= 9;buyFood++){
                    //for (int buyWater = 3;buyWater <= 9;buyWater++){
//                        for (int dig1 = 4;dig1 <= 8;dig1++){
//                            for (int dig2 = 2;dig2 <= 4;dig2++){
        for (int i = 0;i < 29;i++) {
            player = money(food+3*i, water-2*i, buyFood, buyWater, dig1, dig2);
            if (player.isAlive()) {
                if (player.getMoney() > max) {
                    max = player.getMoney();
                    System.out.println("最大值发生了变化");
                    System.out.println(player.getLog());
                    System.out.println("dig1 = " + dig1 + " dig2 = " + dig2);
                    System.out.println(i);
                }
            }
        }
                                //System.out.println("运行次数"+times++);
//                            }
//                        }
                    //}
                //}
            //}
        //}

    }
}
