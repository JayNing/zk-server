package com.ning.zookeeper.lock;


import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;

/**
 * @Author JAY
 * @Date 2019/8/23 10:28
 * @Description TODO
 **/
public class LockDemo {

    private static String CONNECTION_STR="192.168.226.155:2181,192.168.226.156:2181,192.168.226.157:2181";

    public static void main(String[] args) throws Exception{
        ZkClient client = new ZkClient(CONNECTION_STR);
        client.create("/lock","Jay",CreateMode.PERSISTENT);

        client.subscribeDataChanges("/lock", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据发生了变化，s = " + s + ", o = " + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("数据发生了删除，s = " + s );
            }
        });

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.writeData("/lock","Ning");

        client.unsubscribeAll();

        client.subscribeDataChanges("/lock", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据发生了变化2，s = " + s + ", o = " + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("数据发生了删除2，s = " + s );
            }
        });

        try {
            Thread.sleep(5000L);
            client.writeData("/lock","lilei");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
