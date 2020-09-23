package CheckPoint;

import Entity.Node;

import Entity.Player;
import Entity.Weather;
import Map.GameMap;
import Util.RoutesUtil;
import Util.ShopUtil;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

/**
 * @program: Desert
 * @description: 第一关
 * @author: 宋丽
 * @create: 2020-09-11 14:32
 **/
public class FirstLevel {
    public static void main(String[] args) {

        ArrayList<Node> map1 = GameMap.getMapOne();
        RoutesUtil.getPaths(map1.get(0),null,map1.get(0),map1.get(26));
        //ArrayList<Node[]> routes = RoutesUtil.routes;
        Weather[] weathers = Weather.FirstLevelWeather();

        Node[] route = new Node[]{map1.get(0),map1.get(24),map1.get(23),map1.get(22),map1.get(21),map1.get(8),
                map1.get(14),map1.get(12),map1.get(11),map1.get(12),map1.get(14),map1.get(12),map1.get(11),map1.get(13),
                map1.get(15),map1.get(16),map1.get(20),map1.get(26)};

        float max = 0;

        //路线编号
        //for (int i = 0;i < routes.size();i++){
            //Node[] route = routes.get(i);
            //if (route.length>15 ){
                //System.out.println(2);
             //   continue;
            //}
            for (int w = 20;w < 280;w++){
                for (int f = 20;f < 450;f++){
                    Player player = new Player(w,f,route);

                    if (!player.isAlive()){
                        //System.out.println(3);
                        continue;
                    }
                    //j为步数
                    for (int j = 0;j < route.length;j++){
                        //到达村庄
                        //System.out.print("j="+j+" ");
                        //System.out.println(route[j].getNodeId());
                        //System.out.println(player.getLog());
                        if (route[j].getNodeId() == 15){
                            /*player.setLog(player.getLog()+"第"+player.getDay()+"天，购买食物"+player.getWeight()/3+"购买水"+player.getWeight()/9+"\n");
                            ShopUtil.buyFood(player,player.getWeight()/3);
                            ShopUtil.buyWater(player,player.getWeight()/9);
                            player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                        */
                            player.buyOrNot(j);
                        }else
                        //到达矿山
                        if (route[j].getNodeId() == 12 ){
                            //System.out.println(player.leaveOrNot(j));
                            //如果离开
                            if (player.leaveOrNot(j)){
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
                        }else {
                            player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()].getDiscribe()+",在"+route[j].getNodeId()+"号地点\n");
                            player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                            if(route[j].getNodeId() == 27){
                                break;
                            }else {
                                if ("沙暴".equals(weathers[player.getDay()].getDiscribe())){
                                    j--;
                                }
                                route[j].consume(weathers[player.getDay()],player,1);
                            }

                        }
                        if (!player.isAlive()){
                            break;
                        }
                    }
                    float sum = player.sum();
                    if (sum > max && player.isAlive()){
                        max = sum;
                        System.out.println(max);
                        Node[] nodes = player.getRoute();
                        for (int k = 0; k < nodes.length; k++){
                            Node node = (Node)nodes[k];
                            if (k<(nodes.length-1)){
                                System.out.print(node.getNodeId()+"->");
                            }else {
                                System.out.print(node.getNodeId());
                            }
                        }
                        System.out.println();
                        System.out.println(player.getLog());
                        //System.out.println("路线编号为"+i);
                    }
                }

            }

        //System.out.println(routes.size());
        System.out.println("over");
    }
}
