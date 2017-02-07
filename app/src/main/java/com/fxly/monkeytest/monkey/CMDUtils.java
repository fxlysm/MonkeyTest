package com.fxly.monkeytest.monkey;

/**
 * Created by Administrator on 2017/1/23 0023.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 执行命令
 */
public class CMDUtils {

    private static final String TAG = "CMDUtils";

    public static class CMD_Result {
        public int resultCode;
        public String error;
        public String success;

        public CMD_Result(int resultCode, String error, String success) {
            this.resultCode = resultCode;
            this.error = error;
            this.success = success;
        }

    }

    /**
     * 执行命令
     *
     * @param command         命令
     * @param isShowCommand   是否显示执行的命令
     * @param isNeedResultMsg 是否反馈执行的结果
     * @retrun CMD_Result
     */
    public static CMD_Result runCMD(String command, boolean isShowCommand,
                                    boolean isNeedResultMsg , Context context) {
        if (isShowCommand)
            Log.i(TAG, "runCMD:" + command);
        CMD_Result cmdRsult = null;
        int result;
        try {
            Process process = Runtime.getRuntime().exec(command);
            result = process.waitFor();
            if (isNeedResultMsg) {
                StringBuilder successMsg = new StringBuilder();
                StringBuilder errorMsg = new StringBuilder();
                BufferedReader successResult = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                BufferedReader errorResult = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }
                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
                cmdRsult = new CMD_Result(result, errorMsg.toString(),
                        successMsg.toString());
            }
            Toast.makeText(context,"Command:"+command+"\nTestResult:Successed",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "run CMD:" + command + " failed");
            Toast.makeText(context,"Command:"+command+"\nTestResult:Failed",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return cmdRsult;
    }



    public static CMD_Result  RunMonkeyCommand(String command , boolean isNeedResultMsg, boolean isShowCommand){
        CMD_Result cmdRsult = null;

        if(isShowCommand){
            int result;
            try {
                Process process = Runtime.getRuntime().exec(command);
                result = process.waitFor();
                if (isNeedResultMsg) {
                    StringBuilder successMsg = new StringBuilder();
                    StringBuilder errorMsg = new StringBuilder();
                    BufferedReader successResult = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    BufferedReader errorResult = new BufferedReader(
                            new InputStreamReader(process.getErrorStream()));
                    String s;
                    while ((s = successResult.readLine()) != null) {
                        successMsg.append(s);
                    }
                    while ((s = errorResult.readLine()) != null) {
                        errorMsg.append(s);
                    }
                    cmdRsult = new CMD_Result(result, errorMsg.toString(),
                            successMsg.toString());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return cmdRsult;
    }
}



