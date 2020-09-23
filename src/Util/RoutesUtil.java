package Util;

import Entity.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * @program: Desert
 * @description: 路径工具类
 * @author: 宋丽
 * @create: 2020-09-11 15:08
 **/
public class RoutesUtil {
    //用栈保存临时路径节点
    public static Stack<Node> stack = new Stack<>();
    //保存路径的集合
    public static ArrayList<Node[]> routes = new ArrayList<>();

    //判断节点是否在栈中
    public static boolean isNodeInStack(Node node){
        Iterator<Node> iterator = stack.iterator();
        while (iterator.hasNext()){
            Node node1 = (Node)iterator.next();
            if (node.equals(node1)){
                return true;
            }
        }
        return false;
    }

    //栈中节点组成一条路径，经行转储并打印输出
    public static void showAndSavePath(){
        Node[] nodes = (Node[]) stack.toArray(new Node[0]);
        for (int i = 0; i < nodes.length; i++){
            Node node = (Node)nodes[i];
            if (i<(nodes.length-1)){
                System.out.print(node.getNodeId()+"->");
            }else {
                System.out.print(node.getNodeId());
            }
        }
        System.out.println();
        routes.add(nodes);
    }


    /**
	 * 寻找路径的方法
	 * cNode: 当前的起始节点currentNode
	 * pNode: 当前起始节点的上一节点previousNode
	 * sNode: 最初的起始节点startNode
	 * eNode: 终点endNode
     **/
    public static boolean getPaths(Node cNode, Node pNode, Node sNode, Node eNode){
               Node nNode = null;

        //判断防止出现环路
        if (cNode != null && pNode != null && cNode == pNode){
            return false;
        }
        if (cNode != null){
            int i = 0;
            //起始节点入栈
            stack.push(cNode);
            //如果起始节点即为终点，说明找到一条可行路线，如果此路线不经过矿山，则舍弃
            //第五关，只选择节点数小于等于6的路线
            if (cNode.equals(eNode)){
                if (stack.size()<=6){
                    showAndSavePath();
                }
                //showAndSavePath();
                return true;
            }
            //如果不是继续查找
            else {
                /*从当前起始节点cNode有连接关系的相邻节点中
                按顺序遍历得到一个节点，作为下一次递归查找
                的起始节点*/
                nNode = cNode.getNextNode()[i];
                while (nNode != null){
                    /*如果nNode是起始节点/nNode已在栈中
                    /nNode是cNode的上一节点，说明产生环路，
                    应重新在与当前起始节点的相邻节点中寻找nNode*/
                    if (pNode != null
                            && (nNode == sNode || nNode == pNode || isNodeInStack(nNode))) {
                        i++;
                        if (i >= cNode.getNextNode().length) {
                            nNode = null;
                        } else {
                            nNode = cNode.getNextNode()[i];
                        }
                        continue;
                    }
                    /*以nNode为新的起始节点，
                当前起始节点cNode为上一节点，
                递归调用寻找路径的方法
                 */
                    if (getPaths(nNode,cNode,sNode,eNode)){
                        //如果找到一条路，弹出栈顶节点
                        stack.pop();
                    }
                    //继续在与cNode的邻接节点中测试nNode
                    i++;
                    if (i >= cNode.getNextNode().length){
                        nNode = null;
                    }else {
                        nNode = cNode.getNextNode()[i];
                    }
                }

                stack.pop();
                return false;
            }
            /*当遍历完所有与cNode有连接关系的节点后，
            * 说明在以cNode为起始节点到终点的路径已经全部找到
            */
        }else {
            return false;
        }
    }
}
