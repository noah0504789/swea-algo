package edu.ac.practice.linkedlist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author noah kim
 * @date 2024/02/11
 * @link https://swexpertacademy.com/main/code/codeBattle/problemDetail.do?contestProbId=AV14zIwqAHwCFAYD&categoryId=AY1INdsqPvADFAWX&categoryType=BATTLE&battleMainPageIndex=1
 * @requirement 주어진 암호문을 명령어로 수정하고, 최종결과를 출력하라.
 * @summary
    [암호문]
    - 구성: N개의 숫자뭉치
    - 숫자뭉치: 0~999999

    [명령문]
    - 삽입: I x, y, s (x번째 암호문 다음부터 y개의 s 숫자뭉치 삽입)
    - 삭제: D x, y (x번째 암호문 다음부터 y개 삭제)
    - 추가: A y, s (맨 뒤에 y개의 s 숫자뭉치를 추가)
 * @input TC: 10
    - N: 숫자뭉치 갯수 (2_000<=N<=4_000)
    - 암호문
    - M: 명령어 개수 (250<=M<=500)
    - 명령어
 * @output
 * @time_complex
 * @perf
 */
public class 암호문_3 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final LinkedList<Integer> linkedList = new LinkedList<>();
    private static final StringBuffer sb = new StringBuffer();
    private static final int T = 10;
    private static StringTokenizer st;
    private static int N, M;

    public static void main(String[] args) throws IOException {
        for (int t = 1; t <= T; t++) {
            linkedList.clear();

            N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            for (int i = 0; i < N; i++) {
                linkedList.addLast(Integer.parseInt(st.nextToken()));
            }

            M = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                String operator = st.nextToken();
                if (operator.equals("I")) {
                    int idxToAdd = Integer.parseInt(st.nextToken());
                    int cntToAdd = Integer.parseInt(st.nextToken());
                    for (int j = 0; j < cntToAdd; j++) {
                        int numToAdd = Integer.parseInt(st.nextToken());
                        linkedList.add(idxToAdd++, numToAdd);
                    }
                }
                else if (operator.equals("D")) {
                    int idxToDel = Integer.parseInt(st.nextToken());
                    int cntToDel = Integer.parseInt(st.nextToken());

                    for (int j = 0; j < cntToDel; j++) linkedList.remove(idxToDel);
                }
                else if (operator.equals("A")) {
                    int cntToAdd = Integer.parseInt(st.nextToken());
                    for (int j = 0; j < cntToAdd; j++) {
                        int numToAdd = Integer.parseInt(st.nextToken());
                        linkedList.addLast(numToAdd);
                    }
                }
            }

            sb.append("#" + t + " ");

            for (int i = 0; i < 10; i++) {
                sb.append(linkedList.poll() + " ");
            }

            sb.append("\n");
        }

        System.out.println(sb);

        br.close();
    }
}
