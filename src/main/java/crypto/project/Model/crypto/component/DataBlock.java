//
//------------------------------------------------------------
//      DESX Cryptographic Algorithm Implementation Project
//  Damian Biskupski
//  Mateusz Dangreaux
//                          2022
//------------------------------------------------------------
//
package crypto.project.Model.crypto.component;

import crypto.project.Model.functional.PermutationFunction;
import crypto.project.Model.patterns.Tables;

public class DataBlock {

    private final byte[] block;
    private final byte[] initialPermutation;
    private final byte[] left = new byte[32];
    private final byte[] right = new byte[32];

    public DataBlock(byte[] binaryBlock) {
        block = binaryBlock;
        //Initial Permutation
        initialPermutation = PermutationFunction.permutation(Tables.IP, block, 64);
        //division into left and right sides of data
        sharePermutedBlock(initialPermutation);
    }

    private void sharePermutedBlock(byte[] permuted) {
        System.arraycopy(permuted, 0, left, 0, 32);
        System.arraycopy(permuted, 32, right, 0, 32);
    }

    public byte[] getLeft() {
        return left;
    }

    public byte[] getRight() {
        return right;
    }
}
