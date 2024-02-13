package edu.ac.practice.np;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024-02-13
 * @link https://swexpertacademy.com/main/talk/solvingClub/problemView.do?solveclubId=AY0LFFoqrIIDFAXz&contestProbId=AWgv9va6HnkDFAW0&probBoxId=AY2gBgM6OSIDFAXh&type=PROBLEM&problemBoxTitle=0212%EC%A3%BC&problemBoxCnt=1
 * @requirements 규영이의 라운드별 제출한 카드가 주어질 때, 이기는 경우, 지는 경우를 출력하라.
 * @key_words
    [카드]
    - 갯수: 18
    - 구성: 1~18

    [게임]
    - 플레이어 수: 2명
    - 카드 분배: 9장씩
    - 라운드 수: 9
    - 방식: 최종 점수가 높은 플레이어가 승자 (무승부 존재)
        1. 라운드 별 플레이어가 낸 카드를 비교
        2. 높은 수를 낸 사람이 승리

    - 점수부여
        - 승자: 두 카드에 적힌 수의 합만큼 점수를 얻음
        - 패자: 점수 X
 * @input TC
    - 규영이의 라운드 별 제출한 카드
 * @output
 * @time_complex O(N/2!)
 * @perf 23,672 kb / 786 ms
 */
public class 규영이와_인영이의_카드게임 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final StringBuffer sb = new StringBuffer();
    private static StringTokenizer st;
    private static final int N = 18;
    private static int[] cards1, cards2;
    private static boolean[] isIncludedCard1;
    private static int T, winCnt, loseCnt;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            winCnt = 0;
            loseCnt = 0;

            isIncludedCard1 = new boolean[N];
            cards1 = new int[N/2];
            cards2 = new int[N/2];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N/2; i++) {
                cards1[i] = Integer.parseInt(st.nextToken());
                isIncludedCard1[cards1[i]-1] = true;
            }

            int idx = 0;
            for (int i = 0; i < N; i++) {
                if (!isIncludedCard1[i]) cards2[idx++] = i+1;
            }

            Arrays.sort(cards2);

            do {
                judge();
            } while(np());

            sb.append("#" + t + " ").append(winCnt + " ").append(loseCnt).append("\n");
        }

        System.out.println(sb);
    }

    private static void judge() {
        int winP = 0, loseP = 0;

        for (int i = 0; i < N/2; i++) {
            int sumP = cards1[i] + cards2[i];

            if (cards1[i] > cards2[i]) winP += sumP;
            else if (cards1[i] < cards2[i]) loseP += sumP;
        }

        if (winP > loseP) winCnt++;
        else if (winP < loseP) loseCnt++;
    }

    private static boolean np() {
        int infIdx = N/2-1;

        while (infIdx > 0 && cards2[infIdx-1] > cards2[infIdx]) infIdx--;

        if (infIdx == 0) return false;

        int lastIdx = N/2-1, targetIdx = infIdx-1;
        while (cards2[targetIdx] > cards2[lastIdx]) lastIdx--;

        swap(cards2, targetIdx, lastIdx);

        reverse(cards2, infIdx, N/2-1);

        return true;
    }

    private static void swap(int[] arr, int srcIdx, int destIdx) {
        int tmp = arr[srcIdx];
        arr[srcIdx] = arr[destIdx];
        arr[destIdx] = tmp;
    }

    private static void reverse(int[] arr, int left, int right) {
        while (left < right) {
            swap(arr, left++, right--);
        }
    }
}
