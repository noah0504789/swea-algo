package edu.ac.practice.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Comparator.comparingInt;

/**
 * @author noah kim
 * @date 2024-02-15
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AWXRDL1aeugDFAUo&probBoxId=AY2gBgM6OSIDFAXh&type=PROBLEM&problemBoxTitle=0212%EC%A3%BC&problemBoxCnt=5
 * @requirement 사용자들이 충전한 양의 합이 최대가 되는 값을 출력하라.
 * @keyword
    [지도]
    - 크기: 10*10

    [좌표] (X: col, Y: row)
    - 거리계산: |X1-X2| + |Y1-Y2|

    [무선충전기]
    - 제공 범위: 충전기로부터 C 거리 내 모든 좌표.
        - 중첩 영역: 사용자가 원하는 충전기 선택 가능.
    - 충전량: 성능(P)만큼 충전됨. (여러 사용자들이 동시사용 시, 충전량은 각각 P/n으로 분배됨.)

    [사용자]
    - 인원: 2명
    - 시작점: (1,1), (10,10)
    - 이동: 매 초별 주어진 방향으로 한 칸씩 이동
 * @input TC
    - M: 총 이동시간(20<=M<=100), A: 충전기 개수(1<=A<=8)
    - 초당 사용자 이동명령 이력(0-STOP, 1-UP, 2-RIGHT, 3-DOWN, 4-LEFT)
    - 충전기 정보: 좌표(X,Y), 충전 범위(C, 1<=C<=4), 성능(P, 10<=P<=500, 짝수)
 * @output
 * @time_complex 33,020 kb / 259 ms
 * @perf
 */
public class 무선_충전 {
    private static final BufferedReader brr = new BufferedReader(new InputStreamReader(System.in));
    private static final Queue<int[]> queue = new LinkedList<>();
    private static final StringBuffer sb = new StringBuffer();
    private static final int[][] DIRS = {{-1,0},{1,0},{0,-1},{0,1}};
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;
    private static final int UP_CMD = 1;
    private static final int RIGHT_CMD = 2;
    private static final int DOWN_CMD = 3;
    private static final int LEFT_CMD = 4;
    private static final WirelessCharger NO_CHARGING_AREA = new WirelessCharger(-1, -1, -1, 0);
    private static StringTokenizer st;
    private static Point[][] map;
    private static PriorityQueue<WirelessCharger> aCgrs, bCgrs;
    private static Set<WirelessCharger> commonCgrs = new HashSet<>(), unionCgrs = new HashSet<>();
    private static WirelessCharger maxCommonCgr;
    private static int[] cmdsOfA, cmdsOfB;
    private static int TC, M, A, cgrX, cgrY, C, P, maxCommonPwr;
    private static int ar, ac, br, bc;
    private static int ans;

    static final class Point {
        PriorityQueue<WirelessCharger> availableChargers;

        public PriorityQueue<WirelessCharger> getAvailableChargers() {
            return availableChargers;
        }

        public void addCharger(WirelessCharger charger) {
            if (availableChargers == null) availableChargers = new PriorityQueue<>(Comparator.comparingInt(WirelessCharger::getOutput).reversed());

            availableChargers.offer(charger);
        }

        public boolean hasCharger(WirelessCharger charger) {
            return hasAnyCharger() && availableChargers.contains(charger);
        }

        public boolean hasAnyCharger() {
            return Objects.nonNull(availableChargers);
        }
    }

    static final class WirelessCharger {
        final int r, c, radius, output;

        public WirelessCharger(int r, int c, int radius, int output) {
            this.r = r;
            this.c = c;
            this.radius = radius;
            this.output = output;
        }

        public int getOutput() {
            return output;
        }

        public boolean isOutputHigher(int output) {
            return this.output > output;
        }

        public void markServiceArea() {
            queue.offer(new int[] {r, c, 0});
            map[r][c].addCharger(this);

            while (!queue.isEmpty()) {
                int[] point = queue.poll();
                int cr = point[0], cc = point[1], level = point[2];
                if (level == radius+1) {
                    queue.clear();
                    return;
                }

                map[cr][cc].addCharger(this);

                for (int[] dir : DIRS) {
                    int nr = cr + dir[0], nc = cc + dir[1];
                    if (nr < 0 || nr >= HEIGHT || nc < 0 || nc >= WIDTH) continue;
                    if (map[nr][nc].hasCharger(this)) continue;

                    queue.offer(new int[] {nr, nc, level+1});
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WirelessCharger that = (WirelessCharger) o;
            return r == that.r && c == that.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    public static void main(String[] args) throws IOException {
        TC = Integer.parseInt(brr.readLine());

        for (int t = 1; t <= TC; t++) {
            ans = 0;

            ar = 0;
            ac = 0;
            br = HEIGHT-1;
            bc = WIDTH-1;

            map = new Point[HEIGHT][WIDTH];
            for (int r = 0; r < HEIGHT; r++) {
                for (int c = 0; c < WIDTH; c++) {
                    map[r][c] = new Point();
                }
            }

            st = new StringTokenizer(brr.readLine());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());

            cmdsOfA = Arrays.stream(brr.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            cmdsOfB = Arrays.stream(brr.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(brr.readLine());
                cgrX = Integer.parseInt(st.nextToken());
                cgrY = Integer.parseInt(st.nextToken());
                C = Integer.parseInt(st.nextToken());
                P = Integer.parseInt(st.nextToken());

                new WirelessCharger(cgrY-1, cgrX-1, C, P).markServiceArea();
            }

            charge();

            for (int time = 0; time < M; time++) {
                moveA(time);
                moveB(time);

                charge();
            }

            sb.append("#"+t+" ").append(ans).append("\n");
        }

        System.out.println(sb);

        brr.close();
    }

    private static void charge() {
        aCgrs = map[ar][ac].getAvailableChargers();
        bCgrs = map[br][bc].getAvailableChargers();

        int max = 0;

        int maxA = Optional.ofNullable(aCgrs).map(PriorityQueue::peek).orElseGet(() -> NO_CHARGING_AREA).getOutput();
        int maxB = Optional.ofNullable(bCgrs).map(PriorityQueue::peek).orElseGet(() -> NO_CHARGING_AREA).getOutput();

        if (Objects.nonNull(aCgrs) && Objects.nonNull(bCgrs)) {
            commonCgrs.addAll(aCgrs);
            commonCgrs.retainAll(bCgrs);

            if (commonCgrs.isEmpty()) {
                max = maxA + maxB;
            } else {
                maxCommonCgr = commonCgrs.stream().max(comparingInt(WirelessCharger::getOutput)).get();
                maxCommonPwr = maxCommonCgr.output;

                unionCgrs.addAll(aCgrs);
                unionCgrs.addAll(bCgrs);

                if (unionCgrs.stream().anyMatch(c -> c.isOutputHigher(maxCommonPwr))) {
                    max = maxA + maxB;
                } else {
                    int maxAExclCommon = aCgrs.stream().filter(c -> !c.equals(maxCommonCgr)).max(comparingInt(WirelessCharger::getOutput)).orElseGet(() -> NO_CHARGING_AREA).getOutput();
                    int maxBExclCommon = bCgrs.stream().filter(c -> !c.equals(maxCommonCgr)).max(comparingInt(WirelessCharger::getOutput)).orElseGet(() -> NO_CHARGING_AREA).getOutput();

                    max = Math.max(maxCommonPwr + maxAExclCommon, maxCommonPwr + maxBExclCommon);
                }

                unionCgrs.clear();
            }

            commonCgrs.clear();
        } else {
            max = maxA + maxB;
        }

        ans += max;
    }

    private static void moveA(int time) {
        int aCmd = cmdsOfA[time];
        if (aCmd == UP_CMD) ar--;
        else if (aCmd == DOWN_CMD) ar++;
        else if (aCmd == LEFT_CMD) ac--;
        else if (aCmd == RIGHT_CMD) ac++;
    }

    private static void moveB(int time) {
        int bCmd = cmdsOfB[time];
        if (bCmd == UP_CMD) br--;
        else if (bCmd == DOWN_CMD) br++;
        else if (bCmd == LEFT_CMD) bc--;
        else if (bCmd == RIGHT_CMD) bc++;
    }
}
