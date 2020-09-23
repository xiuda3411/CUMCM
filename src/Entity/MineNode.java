package Entity;

/**
 * @program: Desert
 * @description: 特殊节点矿山
 * @author: 宋丽
 * @create: 2020-09-11 12:11
 **/
public class MineNode extends Node {

    //public static final int PROFIT = 1000;
    public static final int PROFIT = 200;

    public MineNode(int nodeId) {
        super(nodeId);
    }

    public MineNode(int nodeId, Node[] nextNode) {
        super(nodeId, nextNode);
    }

    @Override
    public void consume(Weather weather, Player player, int choice) {
        player.setDay(player.getDay()+1);
        //0表示选择在原地停留，因为此处为矿山，故原地停留视为挖矿
        if (choice == 0 || "沙暴".equals(weather.getDiscribe())){
            weather.basic(player);
            weather.basic(player);
            weather.basic(player);
            player.setMoney(player.getMoney()+PROFIT);
        }else if (choice == 1){
            weather.basic(player);
            weather.basic(player);
        }
        //选择挖矿
//        if (choice == 0 && !"沙暴".equals(weather.getDiscribe())){
//            player.setLog(player.getLog()+"第"+player.getDay()+"天，"+Weather.FirstLevelWeather()[player.getDay()].getDiscribe()+"挖矿\n");
//            //System.out.println("挖矿");
//            weather.basic(player);
//            weather.basic(player);
//            weather.basic(player);
//            player.setMoney(player.getMoney()+PROFIT);
//        //沙暴天气不挖矿
//        } else if ("沙暴".equals(weather.getDiscribe())){
//            //System.out.println("沙暴天不挖矿");
//            player.setLog(player.getLog()+"第"+player.getDay()+"天，"+Weather.FirstLevelWeather()[player.getDay()].getDiscribe()+"沙暴天不挖矿\n");
//            weather.basic(player);
//        } else if (choice == 1){
//            //System.out.println("前进");
//            weather.basic(player);
//            weather.basic(player);
//        }
    }
}
