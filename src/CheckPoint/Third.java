package CheckPoint;

import Entity.Node;
import Entity.Player;
import Entity.Weather;
import Map.GameMap;

import java.util.ArrayList;

/**
 * @program: Desert
 * @description: 第三关
 * @author: 宋丽
 * @create: 2020-09-13 02:07
 **/
public class Third {
    public static final ArrayList<Node> MAP1 = GameMap.getMapOne();
    //挖矿的路线
    public static final Node[] ROUTE1 = new Node[]{MAP1.get(0),MAP1.get(3),MAP1.get(2),MAP1.get(8),MAP1.get(10),MAP1.get(12)};
    //不挖矿的路线
    public static final Node[] ROUTE2 = new Node[]{MAP1.get(0),MAP1.get(4),MAP1.get(5),MAP1.get(12)};

    //随机生成每天的天气情况
    public static Weather RandomWeather(){
        float a = (float) Math.random();
        if (a<0.375){
            return new Weather("晴朗");
        }else {
            return new Weather("高温");
        }
    }

    //计算玩家通关与否，返回结束时的玩家
    public static Player Money(int water,int food,Node[] route){
        Player player = new Player(water,food,route);
        //挖矿天数
        int mineDays = 0;
        //如果新建玩家不合格直接返回
        if (!player.isAlive()){
            return player;
        }
        for (int i = 0;i < route.length;i++){
            player.setLog(player.getLog()+"第"+player.getDay()+"天，剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
            //生成每天的随机天气情况
            Weather weather = RandomWeather();
            player.setLog(player.getLog()+"今日天气:"+weather.getDiscribe()+"\n");
            //如果经过矿山
            if (route[i].getNodeId() == 9 && mineDays <5){
                mineDays++;
                route[i].consume(weather,player,0);
                i--;
            }else {
                //不经过矿山则继续前进
                route[i].consume(weather,player,1);
            }
            if (!player.isAlive()){
                break;
            }
            if(route[i].getNodeId() == 13){
                player.sum();
                break;
            }
        }
        return player;
    }

    //1000名玩家携带相同数量的食物与水进入不同路线最终通关的成功率
    public static float win(int waterAndFood,Node[] route){
        //成功通关的人数
        int ok1 = 0;
        float max = 0;
        int sum = 0;
        for (int i = 0; i < 1000; i++){
            //Player p1 = Money(169,179,ROUTE1);
            Player p1 = Money(waterAndFood,waterAndFood,route);
            if (p1.isAlive()){
                ok1++;
                sum += p1.getMoney();
                if (p1.getMoney()>max){
                    max = p1.getMoney();
                }
            }
        }
        System.out.print("平均值"+sum/1000+"  ");
        return (float) ok1/1000;
    }

    public static void main(String[] args) {
        for (int i = 169; i < 238; i++){
            System.out.print(i+"   "+win(i, ROUTE1));
            System.out.println();

        }
    }
}
