package com.example.xianshajin.rosandroidlab;

import android.os.Bundle;

import org.ros.android.MessageCallable;
import org.ros.android.RosActivity;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

public class MainActivity extends RosActivity {
    private RosTextView<nav_msgs.Odometry> rosTextView;

    public MainActivity() {
        super("ROS Android Lab", "ROS Android Lab");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.xianshajin.rosandroidlab.R.layout.activity_main);
        rosTextView = (RosTextView<nav_msgs.Odometry>) findViewById(R.id.text);
        rosTextView.setTopicName("odom");
        rosTextView.setMessageType(nav_msgs.Odometry._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<String, nav_msgs.Odometry>() {
            @Override
            public String call(nav_msgs.Odometry message) {
                return message.getChildFrameId();
            }
        });
    }

    protected void init(NodeMainExecutor nodeMainExecutor) {
        // At this point, the user has already been promopted to either enter the URI
        // of a master to use or to start a master locally.

        // The user can easily use the selected ROS Hostname in the master chooser
        // activity
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
        nodeConfiguration.setMasterUri(getMasterUri());
        //nodeMainExecutor.execute(listener, nodeConfiguration);
        // The RosTextView is also a NodeMain that must be executed in order to
        // start displaying incoming messages.
        nodeMainExecutor.execute(rosTextView, nodeConfiguration);
    }
}
