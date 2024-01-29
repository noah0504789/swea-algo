package edu.ac.practice.string;

import java.io.*;

public class 원재의_메모리_복구 {
    private static int T;
    private static final char[] bitArr = {'0', '1'};
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            T = Integer.parseInt(br.readLine());

            for (int t = 1; t <= T; t++) {
                System.out.printf("#%d %d \n", t, getFixCnt(br.readLine()));
            }
        }
    }

    private static int getFixCnt(String originalBits) {
        int cnt = 0;
        int startIdx = 1;
        for (char c : originalBits.toCharArray()) {
            if (c == bitArr[startIdx]) {
                cnt++;
                startIdx = (startIdx+1) % bitArr.length;
            }
        }

        return cnt;
    }
}
