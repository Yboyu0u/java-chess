package chess.view;

import java.util.Scanner;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static void announceStart() {
        System.out.println("> 체스 게임을 시작합니다.\n" +
                "> 게임 시작 : start\n" +
                "> 게임 종료 : end\n" +
                "> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public static String command() {
        return scanner.nextLine();
    }
}
