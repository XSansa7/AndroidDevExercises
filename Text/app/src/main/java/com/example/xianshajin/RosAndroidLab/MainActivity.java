package com.example.xianshajin.RosAndroidLab;

import android.os.Bundle;
import android.util.Log;

import org.ros.android.MessageCallable;
import org.ros.android.view.RosTextView;
import org.ros.node.NodeConfiguration;
import org.ros.android.RosActivity;
import org.ros.node.NodeMainExecutor;


public class MainActivity extends RosActivity {
    private RosTextView<nav_msgs.Odometry> rosTextView;
    //private Listener listener;



    public MainActivity() {
        // The RosActivity constructor configures the notification title and ticker messages
        super("Pubsub Tutorial", "Pubsub Tutorial");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.xianshajin.text.R.layout.activity_main);
        rosTextView = (RosTextView<nav_msgs.Odometry>) findViewById(com.example.xianshajin.text.R.id.text);
        rosTextView.setTopicName("odom");
        rosTextView.setMessageType(nav_msgs.Odometry._TYPE);
        rosTextView.setMessageToStringCallable(new MessageCallable<java.lang.String, nav_msgs.Odometry>() {
            @Override
            public java.lang.String call(nav_msgs.Odometry message) {
                Log.d("FRAMEID", message.getChildFrameId());
                return message.getChildFrameId();
            }
        });
    }

    protected void init(NodeMainExecutor nodeMainExecutor) {
        //listener = new Listener();

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
