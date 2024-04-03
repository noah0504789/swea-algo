package edu.ac.practice.simulation;

import java.io.*;
import java.util.*;

public class 벽돌깨기_김태희 {
    static Stack<Integer> stack = new Stack<>();
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};
    static int N,H,W,min;
    static class Point {
        int r, c, cnt;

        public Point(int r, int c, int cnt) {
            this.r = r;
            this.c = c;
            this.cnt = cnt;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(in.readLine());

        for (int tc = 1; tc <= TC; tc++) {
            StringTokenizer st = new StringTokenizer(in.readLine(), " ");
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            int[][] map = new int[H][W];
            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(in.readLine(), " ");
                for (int j = 0; j < W; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            min = Integer.MAX_VALUE;
            go(0, map);

            System.out.println("#"+tc+" "+min);
        }
    }

    // 구슬 던지기: 던져서 모두 깨졌으면 true, 아니면 false 리턴
    private static boolean go(int count, int[][] map) {
        int remainCnt = getRemain(map);
        if (remainCnt == 0) {
            min = 0;
            return true; // 다 깨진 상황에서 true 리턴
        }

        if (count == N) {
            min = Math.min(min, remainCnt);
            return false;
        }

        // 모든 열에 던지기 시도
        int[][] newMap = new int[H][W];
        for (int c = 0; c < W; c++) {
            // 구슬에 맞는 가장 윗벽돌 찾기
            int r = 0;
            while(r<H && map[r][c]==0) ++r;

            if (r==H) continue;

            // 벽돌 깨뜨리기 전에 벽돌 복사
            copy(map, newMap);

            // 해당 벽돌 깨뜨리기
            int brick = newMap[r][c];

            // 연쇄 벽돌 처리
            boom(r, c, newMap);

            if (brick > 1) {
                // 중력 작용 처리
                down(newMap);
            }

            // 다음구슬 던지러 가기
            if (go(count+1, newMap)) return true;
        }

        return false;
    }

    private static void down(int[][] map) {
        for (int c = 0; c < W; c++) {
            for (int r = 0; r < H; r++) {
                if (map[r][c]==0) continue;
                stack.push(map[r][c]);
                map[r][c] = 0;
            }

            int r = H-1;
            while (stack.isEmpty()) {
                map[r--][c] = stack.pop();
            }
        }
    }

    private static void boom(int r, int c, int[][] map) {
        Queue<Point> queue = new ArrayDeque<>();
        // 방문처리하고 큐에 넣기
        if (map[r][c]>1) queue.offer(new Point(r, c, map[r][c]));
        map[r][c] = 0; // 방문처리 (벽돌 깨뜨리기)

        while (!queue.isEmpty()) {
            Point cur = queue.poll();

            // 현재 벽돌의 쓰인 수 -1만큼 4방 탐색
            for (int d = 0; d < 4; d++) {
                int nr = cur.r;
                int nc = cur.c;
                for (int k = 1; k < cur.cnt; k++) {
                    nr += dr[d];
                    nc += dc[d];
                    if (nr>=0 && nr<H && nc>=0 && nc<W && map[nr][nc]>0) {
                        if (map[nr][nc]>1) queue.offer(new Point(nr, nc, map[nr][nc]));
                        map[nr][nc] = 0; // 방문처리 (벽돌 깨뜨리기)
                    }
                }
            }
        }
    }

    private static int getRemain(int[][] map) {
        int cnt = 0;
        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                if (map[r][c]>0) ++cnt;
            }
        }
        return cnt;
    }

    private static void copy(int[][] map, int[][] newMap) {
        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                newMap[r][c] = map[r][c];
            }
        }
    }
}
