package com.georgecolgrove.test.testroscore;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

public class TwistNode implements NodeMain {
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("test_app/talker");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<geometry_msgs.Twist> publisher =
                connectedNode.newPublisher("/turtle1/cmd_vel", geometry_msgs.Twist._TYPE);
        // This CancellableLoop will be canceled automatically when the node shuts
        // down.
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private int sequenceNumber;

            @Override
            protected void setup() {
                sequenceNumber = 0;
            }

            @Override
            protected void loop() throws InterruptedException {
                geometry_msgs.Twist twist = publisher.newMessage();
                sequenceNumber++;
                twist.getLinear().setX(2);
                publisher.publish(twist);
                Thread.sleep(1000);
            }
        });

    }

    @Override
    public void onShutdown(Node node) {

    }

    @Override
    public void onShutdownComplete(Node node) {

    }

    @Override
    public void onError(Node node, Throwable throwable) {

    }
}
