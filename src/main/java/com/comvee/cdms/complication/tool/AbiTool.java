package com.comvee.cdms.complication.tool;

public class AbiTool {

    /**
     * 1 下肢动脉中层钙化
     * 2 重度动脉闭塞性病变
     * 3 中度动脉狭窄
     * 4 轻度动脉狭窄
     * 5 临界值
     * 6 正常
     */
    public static final int ABI_LEVEL_1 = 1;
    public static final int ABI_LEVEL_2 = 2;
    public static final int ABI_LEVEL_3 = 3;
    public static final int ABI_LEVEL_4 = 4;
    public static final int ABI_LEVEL_5 = 5;
    public static final int ABI_LEVEL_6 = 6;

    /**
     * 获取abi级别 （病变情况）
     * @param leftAbi
     * @param rightAbi
     * @return
     */
    public static Integer getAbiLevel(Double leftAbi ,Double rightAbi){
        if(leftAbi == null && rightAbi == null){
            return null;
        }
        Integer leftLevel = null;
        Integer rightLevel = null;
        if(leftAbi != null){
            leftLevel = levelHandler(leftAbi);
        }
        if(rightAbi != null){
            rightLevel = levelHandler(rightAbi);
        }
        if(leftLevel != null && rightLevel != null){
            return leftLevel.intValue() < rightLevel.intValue() ? leftLevel : rightLevel;
        }
        if(leftLevel == null){
            return rightLevel;
        }
        return leftLevel;
    }

    private static int levelHandler(double abi){
        int result = ABI_LEVEL_6;
        if(abi > 1.3d){
            result = ABI_LEVEL_1;
        }else if(abi <= 0.4d){
            result = ABI_LEVEL_2;
        }else if(abi > 0.4d && abi <= 0.7d){
            result = ABI_LEVEL_3;
        }else if(abi > 0.7d && abi <= 0.9d){
            result = ABI_LEVEL_4;
        }else if(abi > .9d && abi <= 0.99d){
            result = ABI_LEVEL_5;
        }
        return result;
    }

    public static boolean judgeAbiAbnormal(Float abi){
        if(abi == null){
            return false;
        }
        return abi < 1.0f || abi > 1.3f;
    }

    public static boolean judgePwvAbnormal(Integer pwv){
        if(pwv == null){
            return false;
        }
        return pwv < 1000 || pwv > 2000;
    }
}
