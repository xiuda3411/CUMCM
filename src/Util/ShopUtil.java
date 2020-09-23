package Util;
import Entity.Player;
import Entity.Weather;

/**
 * @program: Desert
 * @description: 商店工具类
 * @author: 宋丽
 * @create: 2020-09-11 14:20
 **/
public class ShopUtil {
    /**
     * 购买水,和食物
     * @param player
     * @param water
     */
    public static boolean buyWaterAndFood(Player player,int water,int food){
        if (player.getMoney()>water*10 && player.getWeight()>water*3
                && player.getMoney()>food*20 && player.getWeight()>food*2){
            player.setWater(player.getWater()+water);
            player.setMoney(player.getMoney()-water*10);
            player.setFood(player.getFood()+food);
            player.setMoney(player.getMoney()-food*20);
            player.setWeight(player.getWeight()-water*3);
            player.setWeight(player.getWeight()-food*2);
            player.setLog(player.getLog()+"第"+player.getDay()+"天，购买食物"+food+"份\n");
            player.setLog(player.getLog()+"第"+player.getDay()+"天，购买水"+water+"份\n");

            return true;
        }
        return false;

    }

    /**
     * 购买食物
     * @param player
     * @param food
     */
    public static boolean buyFood(Player player,int food){
        if (player.getMoney()>food*20 && player.getWeight()>food*2){
            player.setFood(player.getFood()+food);
            player.setMoney(player.getMoney()-food*20);
            player.setWeight(player.getWeight()-food*2);
            player.setLog(player.getLog()+"第"+player.getDay()+"天，购买食物"+food+"份\n");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int food = 0;
        int water = 0;
        int day = 0;
        int weight = 0;
        Weather[] weather = Weather.FirstLevelWeather();
        for (int i = 0;i < 30;i++){
            if ("晴朗".equals(weather[i].getDiscribe())){
                day++;
                food+=7*2;
                water+=5*2;
                weight+=3*5*2+2*2*7;
            }else if ("高温".equals(weather[i].getDiscribe())){
                day++;
                food+=6*2;
                water+=8*2;
                weight+=3*8*2+2*6*2;
            }else if ("沙暴".equals(weather[i].getDiscribe())){
                System.out.println("停留一天");
                food+=10;
                water+=10;
                weight+=3*10+2*10;
            }
            if (day>30||weight>1200){
                System.out.println("food = "+food);
                System.out.println("water = "+water);
                System.out.println("day = "+day);
                System.out.println("weight = "+weight);
                break;
            }
        }

    }
}
