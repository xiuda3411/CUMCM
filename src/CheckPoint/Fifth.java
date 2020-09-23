package CheckPoint;

import Entity.Node;
import Entity.Player;
import Entity.Weather;
import Map.GameMap;
import Util.RoutesUtil;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;

/**
 * @program: Desert
 * @description: 第五关
 * @author: 宋丽
 * @create: 2020-09-13 11:57
 **/
public class Fifth {


    public static void routeAndMoney(int water,int food ,ArrayList<Node[]> routes){
        Weather[] weather = Weather.fifthLevelWeather();
        //System.out.println(routes.size());

        //i表示A使用的路线
        for (int i = 0; i < routes.size();i++){

            int num = 0;//成功的数量
            int sum = 0;//剩余钱的总额
            float max = 0;//成功的最大余额
            //System.out.println(sum);
            //Player a = new Player(water,water,routes.get(i));
            //System.out.println("i = "+i);

            //j表示B使用的路线
            for (int j = 0;j < routes.size();j++){

                //System.out.println("j = "+j);
                Player A = new Player(water,food,routes.get(i));
                Player B = new Player(water,water,routes.get(j));

                //进入A的旅行
                for (int k = 0; k < A.getRoute().length;k++){
                    //System.out.println("k = "+k);
                    if (A.isAlive() && A.getRoute()[k].getNodeId() == 13){
                        A.sum();
                        sum += A.getMoney();
                        num++;
                        if (A.getMoney() > max){
                            max = A.getMoney();
                        }
                        continue;
                    }
                    if (!A.isAlive()){
                        continue;
                    }
                    //System.out.println(A.getLog());
                    //玩家AB同程，消耗为基础消耗的4倍
                    if (k < B.getRoute().length && A.getpNode() != null && B.getpNode() != null && !"高温".equals(weather[A.getDay()].getDiscribe())){
                        if ( A.getRoute()[k] == B.getRoute()[k]){
                            A.getRoute()[k].consume(weather[A.getDay()],A,2);
                            B.getRoute()[k].consume(weather[A.getDay()],B,2);
                            A.setLog(A.getLog()+"AB同程，A位于"+A.getRoute()[k+1].getNodeId()+"\n");
                        }
                        //如果今天高温,AB都不动，仅基础消耗
                    }else if ("高温".equals(weather[A.getDay()].getDiscribe())){
                        A.getRoute()[k].consume(weather[A.getDay()],A,0);
                        B.getRoute()[k].consume(weather[A.getDay()],B,0);
                        k--;
                        A.setLog(A.getLog()+"AB停止前进，A位于"+A.getRoute()[k+1].getNodeId()+"\n");
                    }else {
                        A.getRoute()[k].consume(weather[A.getDay()],A,1);
                        A.setLog(A.getLog()+"A到达"+A.getRoute()[k+1].getNodeId()+"\n");
                        if (k < B.getRoute().length){
                            B.getRoute()[k].consume(weather[A.getDay()],B,1);
                        }
                    }

                    if (A.isAlive()){
                        A.setpNode(A.getRoute()[k]);
                    }else {
                        continue;
                    }

                    if (B.isAlive() && k < B.getRoute().length){
                        B.setpNode(B.getRoute()[k]);
                    }else {
                        B.setpNode(null);
                    }
                }

            }
            System.out.println("route:"+i+" 初始水量:"+water+" 初始食物量:"+food+" 平均余额:"+sum/19+" 成功概率:"+(float)num*100/19+"%"+" 最大余额:"+max);
        }
    }

    public static void main(String[] args) {
         ArrayList<Node[]> routes = RoutesUtil.routes;
        ArrayList<Node> map1 = GameMap.getMapFive();
        RoutesUtil.getPaths(map1.get(0),null,map1.get(0),map1.get(12));

//        for (int water = 49; water < 70;water++){
//            for (int food = 49;food < 90 ; food++){
//                routeAndMoney(water,food,routes);
//            }
//        }
        //routeAndMoney(90,50,routes);
        //routeAndMoney(80,81,routes);
    }

}
