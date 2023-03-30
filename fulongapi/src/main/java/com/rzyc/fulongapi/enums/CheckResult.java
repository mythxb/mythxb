package com.rzyc.fulongapi.enums;

/**
 * 1.合格 2.不合格 3：不涉及 4：未选择
 */
public enum CheckResult {

    QUALIFIED(1),
    UNQUALIFIED(2),
    NOT_INVOLVED(3),
    NOT_SELECTED(4);

    private Integer result;

    CheckResult(Integer result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
