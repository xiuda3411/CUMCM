package Entity;

/**
 * @program: Desert
 * @description: 天气
 * @author: 宋丽
 * @create: 2020-09-11 11:28
 **/
public class Weather {

    //天气，分为晴朗、高温、沙暴天气
    private String discribe;

    public Weather(String discribe) {
        this.discribe = discribe;
    }

    public String getDiscribe() {
        return discribe;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }

    //不同天气的基础消耗
    public void basic(Player player){
        if ("晴朗".equals(discribe)){
            player.setFood(player.getFood()-7);
            player.setWater(player.getWater()-5);
            player.setWeight(player.getWeight()+3*5+2*7);
            player.setLog(player.getLog()+"第"+(player.getDay())+"天，天气"+discribe+"消耗食物7，水5\n");
        }else if ("高温".equals(discribe)){
            player.setFood(player.getFood()-6);
            player.setWater(player.getWater()-8);
            player.setWeight(player.getWeight()+3*8+2*6);
            player.setLog(player.getLog()+"第"+(player.getDay())+"天，天气"+discribe+"消耗食物6，水8\n");
        }else if ("沙暴".equals(discribe)){
            player.setFood(player.getFood()-10);
            player.setWater(player.getWater()-10);
            player.setWeight(player.getWeight()+3*10+2*10);
            player.setLog(player.getLog()+"第"+(player.getDay())+"天，天气"+discribe+"消耗食物10，水10\n");
        }
    }

    public static int foodConsum(Weather weather){
        if ("晴朗".equals(weather.getDiscribe())){
            return 7;
        }else if ("高温".equals(weather.getDiscribe())){
            return 6;
        }else if ("沙暴".equals(weather.getDiscribe())){
            return 10;
        }
        return 0;
    }
    public static int waterConsum(Weather weather){
        if ("晴朗".equals(weather.getDiscribe())){
            return 5;
        }else if ("高温".equals(weather.getDiscribe())){
            return 8;
        }else if ("沙暴".equals(weather.getDiscribe())){
            return 10;
        }
        return 0;
    }

    public static Weather[] FirstLevelWeather(){
        return new Weather[]
                {new Weather("高温"),new Weather("高温"),new Weather("晴朗"),new Weather("沙暴"),
                        new Weather("晴朗"),new Weather("高温"),new Weather("沙暴"),
                        new Weather("晴朗"),new Weather("高温"),new Weather("高温"),new Weather("沙暴"),
                        new Weather("高温"),new Weather("晴朗"),new Weather("高温"),new Weather("高温"),
                        new Weather("高温"),new Weather("沙暴"),new Weather("沙暴"),new Weather("高温"),
                        new Weather("高温"),new Weather("晴朗"),new Weather("晴朗"),new Weather("高温"),
                        new Weather("晴朗"),new Weather("沙暴"),new Weather("高温"),new Weather("晴朗"),
                        new Weather("晴朗"),new Weather("高温"),new Weather("高温")};
    }

    public static Weather[] fifthLevelWeather(){
        return new Weather[]
                {new Weather("晴朗"),new Weather("高温"),new Weather("晴朗"),new Weather("晴朗")
                        ,new Weather("晴朗"),new Weather("晴朗"),new Weather("高温"),
                        new Weather("高温"),new Weather("高温"),new Weather("高温")};
    }
}
