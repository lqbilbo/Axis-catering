package zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

public class Master implements Watcher {

    ZooKeeper zk;
    String hostPort;

    String serverId = Integer.toHexString(new Random().nextInt());
    boolean isLeader = false;

    boolean checkMaster() throws KeeperException,InterruptedException {
        while (true) {
            try {
                Stat stat = new Stat();
                byte data[] = zk.getData("/master", false, stat);
                isLeader = new String(data).equals(serverId);
                return true;
            } catch (KeeperException.NoNodeException e) {
                return false;
            } catch (KeeperException.ConnectionLossException e) {
            }
        }
    }

    Master(String hostPort) {
        this.hostPort = hostPort;
    }

    void runForMaster() throws InterruptedException,KeeperException {
        while(true) {
            try {
                zk.create("/master",
                        serverId.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
                isLeader = true;
                break;
            } catch (KeeperException.NodeExistsException e) {
                isLeader = false;
                break;
            } catch (KeeperException.ConnectionLossException e) {
            }
            if (checkMaster()) break;
        }
    }

    void startZK() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    void stopZK() throws Exception {
        zk.close();
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println(event);
    }

    public static void main(String[] args) throws Exception {
        Master m = new Master(args[0]);

        m.startZK();

        //wait for a bit
        Thread.sleep(60000);

        m.stopZK();
    }
}
