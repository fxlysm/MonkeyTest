package com.fxly.monkeytest.monkey;

import android.content.Context;

/**
 * Created by Lambert Liu on 2016-12-23.
 */

public class MonkeyRunnerThread  extends Thread {

    private String command;
    public MonkeyRunnerThread(String command) {
        CMDUtils.CMD_Result rs= CMDUtils.RunMonkeyCommand(command,true,true);

    }


}