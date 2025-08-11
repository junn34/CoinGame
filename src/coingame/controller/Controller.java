package coingame.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import coingame.coin.Coin;
import coingame.coin.CoinA;
import coingame.coin.CoinB;
import coingame.coin.CoinC;
import coingame.coin.CoinHandler;
import coingame.player.NPCPlayer;
import coingame.player.NPCSetup;
import coingame.player.Player;
import coingame.service.CoinChart;

/**
 * 게임의 메인 흐름을 제어하는 컨트롤러.
 * 핵심 포인트: Coin 인스턴스를 한 번만 만들고
 * CoinHandler / CoinChart / Player(지갑)가 같은 인스턴스를 공유한다.
 */
public class Controller {

    // --- 게임 구성 요소 ---
    private final List<NPCPlayer> npcList = new ArrayList<>();
    private Player player;
    private CoinChart coinChart;       // 코인 목록/시세를 보여주는 뷰/서비스
    private CoinHandler coinHandler;   // 시세 변동을 적용
    private Timer timer;               // 라운드 제한 시간

    // --- 게임 상태 ---
    private int currentRound = 1;
    private final int maxRound = 7;
    private boolean isRunning = true;

    private final Scanner sc = new Scanner(System.in);
    private int startingCash;

    // 시장에 존재하는 코인(공유 대상)
    private List<Coin> marketCoins;

    // ===== 생성자 =====
    public Controller() {
        // 코인은 여기서 한 번만 생성하고 끝까지 공유
        marketCoins = new ArrayList<>(List.of(
            new CoinA(),
            new CoinB(),
            new CoinC()
        ));

        // 동일 리스트를 공유해서 전달
        this.coinHandler = new CoinHandler(marketCoins);
        this.coinChart   = new CoinChart(marketCoins);
    }

    // 필요하면 DI(테스트용) 생성자도 제공 가능
    public Controller(List<Coin> coins) {
        this.marketCoins = coins;
        this.coinHandler = new CoinHandler(marketCoins);
        this.coinChart   = new CoinChart(marketCoins);
    }

    // ===== 게임 시작 =====
    public void startGame() {
        System.out.println("Game Start!!");

        System.out.print("Player 이름을 정해주세요: ");
        String name = sc.nextLine().trim();

        System.out.print("난이도(거지/평민/부자) 선택: ");
        String difficulty = sc.nextLine().trim();

        switch (difficulty) {
            case "부자":
                startingCash = 20_000_000; break;
            case "평민":
                startingCash = 1_000_000;  break;
            case "거지":
                startingCash = 50_000;     break;
            default:
                System.out.println("잘못된 난이도, normal로 설정합니다.");
                startingCash = 100_000;
        }

        player = new Player(name, startingCash);

        // NPC 등록
        npcList.add(NPCSetup.npc1);
        npcList.add(NPCSetup.npc2);
        npcList.add(NPCSetup.npc3);


        // 라운드 진행
        while (isRunning && currentRound <= maxRound) {
            System.out.println("===== " + currentRound + "일차 =====");

            // 1) 오늘 변동 적용만
            coinHandler.simulateToday();

            
            coinChart.showCoinList();

            // 2) 행동(플레이어/NPC) 진행
            timer = new Timer(120);
            timer.start();
            int result = playerAction();
            if (result == -1) break;
            for (NPCPlayer npc : npcList) npc.takeAction(coinChart);

           
            coinHandler.closeDay();

            printRoundResult(currentRound);
            currentRound++;
        }


        // 최종 결과
        printFinalResult();
    }

    // ===== 플레이어 행동 =====
    private int playerAction() {
        while (!timer.isTimeOver()) {
            System.out.println();
            System.out.println("1. 매도  2. 매수  3. SKIP  4. 나가기");
            System.out.print("선택: ");

            if (!sc.hasNextInt()) {
                sc.nextLine(); // 잘못된 입력 버림
                System.out.println("숫자를 입력해 주세요.");
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine(); // 개행 처리

            switch (choice) {
                case 1: // 매도
                    System.out.println("판매할 코인의 이름과 개수를 입력해주세요. (예: CoinA 3)");
                    player.getWallet().showMyCoins();
                    String sellLine = sc.nextLine().trim();
                    if (sellLine.isEmpty()) {
                        System.out.println("입력이 비어 있습니다.");
                        break;
                    }
                    String[] sellTokens = sellLine.split("\\s+");
                    if (sellTokens.length < 2) {
                        System.out.println("형식이 올바르지 않습니다. (예: CoinA 3)");
                        break;
                    }
                    String coinToSell = sellTokens[0];
                    int amountToSell;
                    try {
                        amountToSell = Integer.parseInt(sellTokens[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("개수는 숫자로 입력하세요.");
                        break;
                    }
                    player.sellCoin(coinToSell, amountToSell);
                    break;

                case 2: // 매수
                    System.out.println("구매할 코인의 [번호]와 [개수]를 입력해주세요. (예: 0 5)");
                    coinChart.showCoinList();

                    if (!sc.hasNextInt()) { sc.nextLine(); System.out.println("번호는 숫자."); break; }
                    int index = sc.nextInt();
                    if (!sc.hasNextInt()) { sc.nextLine(); System.out.println("개수는 숫자."); break; }
                    int amountToBuy = sc.nextInt();
                    sc.nextLine();

                    player.buyCoin(coinChart, index, amountToBuy);
                    break;

                case 3: // SKIP
                    System.out.println("하루를 건너뜁니다.");
                    timer.stopTimer();
                    return 1;

                case 4: // 나가기
                    System.out.println("게임을 종료합니다.");
                    isRunning = false;
                    timer.stopTimer();
                    return -1;

                default:
                    System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }

        System.out.println(currentRound + "일차가 지나갑니다.");
        timer.stopTimer();
        return 1;
    }

    // ===== 라운드 결과 =====
    private void printRoundResult(int round) {
        System.out.println();
        System.out.println("=== " + round + "일차 거래 종료 ===");
        player.showStatus();
        for (NPCPlayer npc : npcList) {
            npc.showStatus();
        }
        System.out.println();
    }

    // ===== 최종 결과 =====
    private void printFinalResult() {
        System.out.println();
        System.out.println("=== 최종 결과 ===");
        int finalAsset = player.getCash() + player.getWallet().getTotalCoinsPrice();
        int profit = finalAsset - startingCash;
        double profitRate = (double) profit / startingCash * 100.0;
        System.out.println("수익률: " + profitRate + "%");
    }
}
