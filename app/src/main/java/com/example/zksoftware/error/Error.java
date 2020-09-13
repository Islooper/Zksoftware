package com.example.zksoftware.error;

/**
 * Created by looper on 2020/9/12.
 */
public enum Error implements INumberEnum {


    OK(0) {
        public String getDescription() {
            return "成功";
        }
    },
    UNKOW_Para(100) {
        public String getDescription() {
            return "unkow param";
        }
    };


    private int code;

    Error(int number) {
        this.code = number;
    }

    @Override
    public int getCode() {
        return 0;
    }

    public abstract String getDescription();
}
