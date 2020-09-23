package Entity;

import Util.ShopUtil;

/**
 * @program: Desert
 * @description: 特殊节点村庄
 * @author: 宋丽
 * @create: 2020-09-11 14:09
 **/
public class VillageNode extends Node {
    public VillageNode(int nodeId) {
        super(nodeId);
    }



    public VillageNode(int nodeId, Node[] nextNode) {
        super(nodeId, nextNode);
    }

    /**
     * 路过村庄做出选择
     * @param weather 天气
     * @param player 玩家
     * @param choice 选择，0停留，1前进
     * @param buy 是否购买物资，0不买，1购买
     * @param water 购买水的数量
     * @param food 购买食物的数量
     */
    public void consume(Weather weather, Player player, int choice,int buy,int water,int food) {
        super.consume(weather,player,choice);
        if (buy == 1){
            ShopUtil.buyFood(player,food);
            //ShopUtil.buyWater(player,water);
        }
    }
}
