package Entity;

/**
 * @program: Desert
 * @description: 路线节点
 * @author: 宋丽
 * @create: 2020-09-11 11:45
 **/
public class Node {
    //本节点编号
    private int nodeId;
    //能够前往的下一节点编号
    private Node[] nextNode;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public Node[] getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node[] nextNode) {
        this.nextNode = nextNode;
    }

    public Node(int nodeId) {
        this.nodeId = nodeId;
    }

    public Node(int nodeId, Node[] nextNode) {
        this.nodeId = nodeId;
        this.nextNode = nextNode;
    }

    public void consume(Weather weather,Player player,int choice){
        player.setDay(player.getDay()+1);
        //0表示选择在原地停留
        if (choice == 0 || "沙暴".equals(weather.getDiscribe())){
            //System.out.println("原地停留");
            weather.basic(player);
        }
        //1表示前进
        else if(choice == 1){
            //System.out.println("前进");
            weather.basic(player);
            weather.basic(player);
        //2表示两玩家同程
        }else if(choice == 2){
            weather.basic(player);
            weather.basic(player);
            weather.basic(player);
            weather.basic(player);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//地址相等
        }

        if(obj == null){
            return false;//非空性,为空应该返回false。
        }

        if(obj instanceof Node){
            Node other = (Node) obj;
            //需要比较的字段相等，则这两个对象相等
            if(this.nodeId == other.nodeId){
                return true;
            }
        }

        return false;
    }
}
