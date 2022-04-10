package crypto.project.Model.crypto.component;

import crypto.project.Model.functional.PermutationFunction;
import crypto.project.Model.patterns.Tables;

public class KeyBlock {

    private byte[] left;
    private byte[] right;
    private byte[] connectedBlock = new byte[56];
    private byte[] key = new byte[48];

    private byte[] leftPattern = {
            57, 49, 41, 33, 25, 17,  9,
            1, 58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36
    };
    private byte[] rightPattern = {
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13,  5, 28, 20, 12,  4
    };

    public KeyBlock(byte[] block) {
        //first permutation PC1
        left = PermutationFunction.permutation(leftPattern, block, 28);
        right = PermutationFunction.permutation(rightPattern, block, 28);
    }

    public void roundEncrypt(int round) {
        leftShift(Tables.shiftBits[round]);
        create48bitKey();
        secondPermutation();
    }

    public void roundDecrypt(int round) {
        rightShift(Tables.shiftBits[round]);
        create48bitKey();
        secondPermutation();
    }

    private void leftShift(byte times) {
        byte tmpL = 0;
        byte tmpR = 0;

        for (int j = 0; j < times; j++) {
            tmpL = left[0];
            tmpR = right[0];

            for (int i = 0; i < 27; i++) {
                left[i] = left[i + 1];
                right[i] = right[i + 1];
            }
            left[27] = tmpL;
            right[27] = tmpR;
        }
    }

    private void rightShift(byte times) {
        byte tmpL;
        byte tmpR;

        for (int j = 0; j < times; j++) {
            tmpL = left[27];
            tmpR = right[27];
            for (int i = 27; i > 0; i--) {
                left[i] = left[i - 1];
                right[i] = right[i - 1];
            }
            left[0] = tmpL;
            right[0] = tmpR;
        }
    }

    private void create48bitKey() {
        System.arraycopy(left, 0, connectedBlock, 0, 28);
        System.arraycopy(right, 0, connectedBlock, 28, 28);
    }

    private void secondPermutation() {
        key = PermutationFunction.permutation(Tables.PC2, connectedBlock, 48);
    }

    public byte[] getKeys() {
        return key;
    }
}
