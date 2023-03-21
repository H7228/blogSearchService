package com.bebs.source.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Constant {

    @Getter
    @AllArgsConstructor
    public enum SortTypeKakao {

        ACCURACY("accuracy", "정확도순"),
        RECENCY("recency", "최신순");

        private final String code;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum SortTypeNaver {

        SIM("sim", "정확도순"),
        DATE("date", "최신순");

        private final String code;
        private final String desc;
    }

}
