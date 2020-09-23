package CheckPoint;

import Entity.Node;
import Entity.Player;
import Entity.Weather;
import Map.GameMap;
import Util.ShopUtil;

import java.util.ArrayList;

/**
 * @program: Desert
 * @description: 第四关
 * @author: 宋丽
 * @create: 2020-09-13 06:06
 **/
public class Forth {
    public static final ArrayList<Node> MAP1 = GameMap.getMapOne();
    //挖矿的路线
    public static final Node[] ROUTE1 = new Node[]{MAP1.get(0),MAP1.get(1),MAP1.get(2),MAP1.get(3),MAP1.get(8),MAP1.get(13),
            MAP1.get(12),MAP1.get(17),MAP1.get(18),MAP1.get(13),MAP1.get(12),MAP1.get(17),
            MAP1.get(18),MAP1.get(19),MAP1.get(24)};

    public static Weather RandomWeather(){
        float a = (float) Math.random();
        if (a<0.3){
            return new Weather("晴朗");
        }else if (a>0.4){
            return new Weather("高温");
        }else {
            return new Weather("沙暴");
        }
    }

    public static Player money(int dig1,int dig2,Node[] route){
        Player player = new Player(240,240,route);
        int mineTimes = 0;
        if (!player.isAlive()){
            return player;
        }
        for (int i = 0;i < route.length;i++){
            player.setLog(player.getLog()+"第"+player.getDay()+"天，剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
            //生成每天的随机天气情况
            Weather weather = RandomWeather();
            player.setLog(player.getLog()+"今日天气:"+weather.getDiscribe()+"\n");
            //第一次经过矿山
            if (route[i].getNodeId() == 18 && mineTimes<dig1){
                mineTimes++;
                if (mineTimes>=dig1){
                    route[i].consume(weather,player,1);
                    player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weather.getDiscribe()+"离开矿山\n");
                    player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                }else {
                    //如果继续挖矿
                    route[i].consume(weather, player, 0);
                    player.setLog(player.getLog() + "剩余资金数" + player.getMoney() + " 剩余水量" + player.getWater() + " 剩余食物量" + player.getFood() + "\n");
                    i--;
                }
                //第二次经过矿山
            }else if (route[i].getNodeId() == 18 && mineTimes>=dig1){
                mineTimes++;
                if (mineTimes>=dig1+dig2){
                    player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weather.getDiscribe()+"离开矿山\n");
                    route[i].consume(weather,player,1);
                    player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                }else {
                    //如果继续挖矿
                    route[i].consume(weather, player, 0);
                    player.setLog(player.getLog() + "剩余资金数" + player.getMoney() + " 剩余水量" + player.getWater() + " 剩余食物量" + player.getFood() + "\n");
                    i--;
                }
        }else if(route[i].getNodeId() == 14){
                ShopUtil.buyWaterAndFood(player,  (player.getWeight() /5),(player.getWeight() /5));
                player.setLog(player.getLog()+"到达村庄14，剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
            }else if (route[i].getNodeId() == 25){
                player.sum();
                break;
            }else {
                if ("沙暴".equals(weather.getDiscribe())){
                    route[i].consume(weather,player,0);
                    i--;
                }else {
                    route[i].consume(weather,player,1);
                    //player.setLog(player.getLog()+"第"+player.getDay()+"天，"+weathers[player.getDay()].getDiscribe()+",前往"+route[j+1].getNodeId()+"号地点\n");
                    player.setLog(player.getLog()+"剩余资金数"+player.getMoney()+" 剩余水量"+player.getWater()+" 剩余食物量"+player.getFood()+"\n");
                }
            }
            if (!player.isAlive()){
                break;
            }
        }
        return player;
    }

    public static void main(String[] args) {
        float max = 0;
        Player player = null;
        for (int dig1 = 1;dig1 <= 14;dig1++){
            for (int dig2 = 1;dig2+dig1 <= 15;dig2++){
                int ok = 0;
                int sum = 0;
                for (int i = 0;i < 1000;i++){
                    player = money(dig1,dig2,ROUTE1);
                    if (player.isAlive()){
                        ok++;
                        sum+=player.getMoney();
                        if (player.getMoney()>max){
                            max = player.getMoney();
                            //System.out.println("最大值发生了变化");
                            //System.out.println(player.getLog());
                            //System.out.println("dig1 = " + dig1 + " dig2 = " + dig2+" max = "+max );
                            //System.out.println();
                        }
                    }
                }
                System.out.println("dig1 = " + dig1 + " dig2 = " + dig2+" max = "+max +" 均值 = "+sum/1000+" 通过率 = "+(float)ok/1000+"%");
            }
        }
    }

}
