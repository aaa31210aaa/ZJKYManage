package com.zhks.safetyproduction.utils;

import java.util.List;

public class StringUtils {
    /**
     * 用逗号分割
     */
    public static String setTextStrComma(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                builder.append(list.get(i) + ",");
            } else {
                builder.append(list.get(i));
            }
        }
        return builder.toString();
    }
}
