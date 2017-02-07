package com.fxly.monkeytest.monkey;

import android.util.Log;

/**
 * Created by Lambert Liu on 2016-12-23.
 */

public  class GetCommand {

    /**
     * 生成命令
     * @param pkgName 包名
     * @param clsName 类名
     * @param mtdName 方法名
     * @return
     */
    public static   String generateCommand(String pkgName, String clsName, String mtdName) {
        String command = "am instrument  --user 0 -w -r   -e debug false -e class "
                + pkgName + "." + clsName + "#" + mtdName + " "
                + pkgName + ".test/android.support.test.runner.AndroidJUnitRunner";
        Log.e("test1: ", command);
        return command;


        //         adb shell am instrument -w -r   -e debug false -e class com.fxly.mytestcase.TestOne#demo com.fxly.mytestcase.test/android.support.test.runner.AndroidJUnitRunner
    }
}
