package io.mogagi.quant;

import org.junit.jupiter.api.Test;

/**
 * @author mogagi
 * @since 2021/12/07 11:15:27
 */
class Solution {

    @Test
    public void test() {
        String haystack = "ABCABXYABCABATDM", needle = "ABCABA";
        assert strStr(haystack, needle) == 7;
    }

    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {
            return 0;
        }
        int i = 0, j = 0, next[] = getNext(needle);
        while (i < haystack.length() && j < needle.length()) {
            if (j == -1 || haystack.charAt(i) == needle.charAt(j)) {
                i += 1;
                j += 1;
            } else {
                j = next[j];
            }
            if (j == needle.length()) {
                return i - j;
            }
        }
        return -1;
    }

    private int[] getNext(String pattern) {
        int i = 0, j = -1, next[] = new int[pattern.length()];
        next[0] = -1;
        while (i < next.length - 1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                i += 1;
                j += 1;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }
}
