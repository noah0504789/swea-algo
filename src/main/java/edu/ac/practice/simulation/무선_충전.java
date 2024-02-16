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
    - 범위: 충전기로부터 C 거리 내 모든 좌표.
        - 중첩 영역: 사용자가 원하는 충전기로 충전 가능.
    - 사용 조건: 사용자는 충전 범위 내에 있어야 함.
    - 충전량: 성능(P)만큼 충전됨. (여러 사용자들이 동접 시, 충전량은 각각 P/n으로 분배됨.)

    [사용자]
    - 인원: 2명
    - 시작점: (1,1), (10,10)
    - 이동: 매 초별 주어진 방향으로 한 칸씩 이동
 * @input TC
    - M: 총 이동시간(20<=M<=100), A: 충전기 개수(1<=A<=8)
    - 초당 사용자 이동 이력(0-STOP, 1-UP, 2-RIGHT, 3-DOWN, 4-LEFT)
    - 충전기 정보: 좌표(X,Y), 충전 범위(C, 1<=C<=4), 성능(P, 10<=P<=500, 짝수)
 * @output
 * @time_complex 30,876 kb / 217 ms
 * @perf
 */
public class 무선_충전 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final Queue<int[]> queue = new LinkedList<>();
    private static final StringBuffer sb = new StringBuffer();
    private static final int[][] DIRS = {{-1,0},{1,0},{0,-1},{0,1}};
    private static final int HEIGHT = 10;
    private static final int WIDTH = 10;
    private static final int UP_CMD = 1;
    private static final int RIGHT_CMD = 2;
    private static final int DOWN_CMD = 3;
    private static final int LEFT_CMD = 4;
    private static final int A_IDX = 0;
    private static final int B_IDX = 1;
    private static final WirelessCharger NON_EXIST = new WirelessCharger(10000, 10000, 10000, 10000);
    private static final WirelessCharger OR_ELSE = new WirelessCharger(10000, 10000, 10000, 0);
    private static StringTokenizer st;
    private static Point[][] map;
    private static Set<WirelessCharger> aCgrs, bCgrs, commonCgrs, unionCgrs;
    private static WirelessCharger maxCgr;
    private static int[][] usersPoint;
    private static int[] cmdsOfA, cmdsOfB;
    private static int TC, M, A, cgrX, cgrY, C, P;
    private static int acr, acc, anr, anc, bcr, bcc, bnr, bnc;
    private static int ans;

    static final class Point {
        Set<WirelessCharger> availableChargers;

        public Set<WirelessCharger> getAvailableChargers() {
            return availableChargers;
        }

        public void addCharger(WirelessCharger charger) {
            if (availableChargers == null) availableChargers = new HashSet<>();

            availableChargers.add(charger);
        }

        public boolean hasCharger(WirelessCharger charger) {
            return hasAnyCharger() && availableChargers.contains(charger);
        }

        public boolean hasAnyCharger() {
            return Objects.nonNull(availableChargers);
        }
    }

    static final class WirelessCharger {
        final int r, c, radius, power;

        public WirelessCharger(int r, int c, int radius, int power) {
            this.r = r;
            this.c = c;
            this.radius = radius;
            this.power = power;
        }

        public int getPower() {
            return power;
        }

        public void markServiceArea() {
            queue.offer(new int[] {r, c, 0});
            map[r][c].addCharger(this);

            while (!queue.isEmpty()) {
                int[] point = queue.poll();
                int cr = point[0], cc = point[1], depth = point[2];

                for (int[] dir : DIRS) {
                    if (depth == radius) {
                        queue.clear();
                        return;
                    }

                    int nr = cr + dir[0], nc = cc + dir[1];
                    if (nr < 0 || nr >= HEIGHT || nc < 0 || nc >= WIDTH) continue;
                    if (map[nr][nc].hasCharger(this)) continue;

                    map[nr][nc].addCharger(this);
                    queue.offer(new int[] {nr, nc, depth+1});
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
        TC = Integer.parseInt(br.readLine());
        for (int t = 1; t <= TC; t++) {
            ans = 0;
            usersPoint = new int[][]{{0, 0}, {HEIGHT-1, WIDTH-1}};

            map = new Point[HEIGHT][WIDTH];
            for (int r = 0; r < HEIGHT; r++) {
                for (int c = 0; c < WIDTH; c++) {
                    map[r][c] = new Point();
                }
            }

            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());

            cmdsOfA = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            cmdsOfB = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());
                cgrX = Integer.parseInt(st.nextToken());
                cgrY = Integer.parseInt(st.nextToken());
                C = Integer.parseInt(st.nextToken());
                P = Integer.parseInt(st.nextToken());

                new WirelessCharger(cgrY -1, cgrX -1, C, P).markServiceArea();
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

        br.close();
    }

    private static void charge() {
        acr = usersPoint[A_IDX][0];
        acc = usersPoint[A_IDX][1];
        bcr = usersPoint[B_IDX][0];
        bcc = usersPoint[B_IDX][1];

        aCgrs = map[acr][acc].getAvailableChargers();
        bCgrs = map[bcr][bcc].getAvailableChargers();

        int max = 0;

        if (Objects.nonNull(aCgrs) && Objects.nonNull(bCgrs)) {
            commonCgrs = new HashSet<>(aCgrs);
            commonCgrs.retainAll(bCgrs);

            int maxA = aCgrs.stream().max(comparingInt(WirelessCharger::getPower)).get().power;
            int maxB = bCgrs.stream().max(comparingInt(WirelessCharger::getPower)).get().power;
            int maxCommon = commonCgrs.stream().max(comparingInt(WirelessCharger::getPower)).orElseGet(() ->OR_ELSE).power;
            if (maxCommon == 0) {
                max = maxA + maxB;
            } else {
                unionCgrs = new HashSet<>(aCgrs);
                unionCgrs.addAll(bCgrs);

                // cond1) a, b 합집합에서, maxCommonPwr보다 큰 원소가 있으면
                if (unionCgrs.stream().anyMatch(c -> c.power > maxCommon)) {
                    max = maxA + maxB;
                } else { // cond2) a, b 각각의 집합에 maxCommon보다 큰 값이 없으면, maxCommonPwr보다 큰 원소가 없으면
                    int lessMaxCommonPwrAndMaxA = aCgrs.stream().filter(c -> c.equals(maxCommon)).max(comparingInt(WirelessCharger::getPower)).orElseGet(() -> OR_ELSE).power;
                    int lessMaxCommonPwrAndMaxB = bCgrs.stream().filter(c -> c.equals(maxCommon)).max(comparingInt(WirelessCharger::getPower)).orElseGet(() -> OR_ELSE).power;
                    max = Math.max(maxCommon, lessMaxCommonPwrAndMaxA+lessMaxCommonPwrAndMaxB);
                }
            }
        } else if (Objects.nonNull(aCgrs)) {
            max = aCgrs.stream().max(comparingInt(WirelessCharger::getPower)).get().power;
        } else if (Objects.nonNull(bCgrs)) {
            max = bCgrs.stream().max(comparingInt(WirelessCharger::getPower)).get().power;
        }

        ans += max;
    }

    private static void moveA(int time) {
        anr = acr;
        anc = acc;

        int aCmd = cmdsOfA[time];
        if (aCmd == UP_CMD) anr -= 1;
        else if (aCmd == DOWN_CMD) anr += 1;
        else if (aCmd == LEFT_CMD) anc -= 1;
        else if (aCmd == RIGHT_CMD) anc += 1;

        usersPoint[A_IDX][0] = anr;
        usersPoint[A_IDX][1] = anc;
    }

    private static void moveB(int time) {
        bnr = bcr;
        bnc = bcc;

        int bCmd = cmdsOfB[time];
        if (bCmd == UP_CMD) bnr -= 1;
        else if (bCmd == DOWN_CMD) bnr += 1;
        else if (bCmd == LEFT_CMD) bnc -= 1;
        else if (bCmd == RIGHT_CMD) bnc += 1;

        usersPoint[B_IDX][0] = bnr;
        usersPoint[B_IDX][1] = bnc;
    }
}

