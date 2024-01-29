package edu.ac.practice.array;

import java.io.*;

public class 원재의_메모리_복구하기 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, ans, length;
    static char[] memory;

    public static void main(String args[]) throws Exception {
        T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++) {
            init();

            char c = '0';
            for (int i = 0; i < length; i++) {
                if (c == memory[i]) continue;
                c = memory[i];
                ans++;
            }

            System.out.println("#" + t + " " + ans);
        }
    }

    private static void init() throws IOException {
        // 입력
        memory = br.readLine().toCharArray();
        length = memory.length;
        ans = 0;
    }
}

/**

class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int T, ans, length;
    static Boolean[] input;

    public static void main(String args[]) throws Exception {
        T = Integer.parseInt(br.readLine());

        for(int t = 1; t <= T; t++) {
            init();

            bfs();

            System.out.println("#"+ t + " " + ans);
        }
    }

    private static void bfs() {
        Queue<Try> queue = new LinkedList<>();
        for (int i = 0; i < length; i++) queue.offer(new Try(i));

        while (!queue.isEmpty()) {
            Try cTry = queue.poll();
            if (cTry.isSame()) {
                ans = cTry.cnt;
                return;
            }

            for (int j = 0; j < length; j++) {
                if (cTry.preIdx == j) continue;
                queue.offer(new Try(cTry, j));
            }
        }
    }

    private static void init() throws IOException {
        // 입력
        input = Arrays.stream(br.readLine().split("")).map(n -> n.equals("1") ? true : false).toArray(Boolean[]::new);
        length = input.length;
    }

    static class Try {
        boolean[] switches;
        int cnt, preIdx;

        Try(int idx) {
            this.switches = new boolean[length];
            this.cnt = 0;
            fix(idx);
        }

        Try(Try before, int idx) {
            this.switches = before.switches.clone();
            this.cnt = before.cnt;
            fix(idx);
        }

        void fix(int idx) {
            for (int i = idx; i < length; i++) this.switches[i] = !this.switches[i];
            this.cnt++;
            this.preIdx = idx;
        }

        boolean isSame() {
            for (int i = 0; i < length; i++) {
                if (input[i] != this.switches[i]) return false;
            }

            return true;
        }
    }
}
 */
