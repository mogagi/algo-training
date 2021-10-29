package io.mogagi.quant.loop;

public interface Loop {
    /**
     * submit to a loop's to-do list
     */
    void submit();

    /**
     * submit to a loop's to-do list
     *
     * @param loopName name of loop
     * @param data     data
     */
    void submit(String loopName, Object data);

    /**
     * notify those loops once job done
     *
     * @param loop loop to be noticed
     */
    Loop notify(Loop... loop);
}
