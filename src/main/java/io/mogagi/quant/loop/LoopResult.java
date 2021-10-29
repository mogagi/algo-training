package io.mogagi.quant.loop;

import lombok.Data;

@Data
public class LoopResult {
    private Boolean pass = true;
    private Object data;

    public static LoopResult pass(Object... data) {
        LoopResult loopResult = new LoopResult();
        if (data.length > 0) {
            loopResult.data = data[0];
        }
        return loopResult;
    }

    public static LoopResult stop() {
        LoopResult loopResult = new LoopResult();
        loopResult.pass = false;
        return loopResult;
    }
}