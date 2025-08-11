package coingame.controller;

import java.util.Scanner;

import coingame.coin.CoinA;
import coingame.coin.CoinB;
import coingame.coin.CoinC;
import coingame.coin.CoinHandler;
import coingame.player.Player;
import coingame.service.CoinChart;

import java.util.ArrayList;
import java.util.List;
import coingame.player.NPCPlayer;
import coingame.player.NPCSetup;


public class Controller {

	private List<NPCPlayer> npcList = new ArrayList<>();
	
	private Player player;
	private CoinChart coinChart;
	private CoinHandler coinHandler;
	private Timer timer;
	
	private int currentRound = 1;
	private final int maxRound = 7;
	private boolean isRunning = true;
	
	private Scanner sc = new Scanner(System.in);
	
	private int startingCash;
	
//    private final String RED = "\u001B[31m";
//    private final String BLUE = "\u001B[34m";
//    private final String RESET = "\u001B[0m";
	
	public Controller(CoinHandler coinHandler) {
		this.coinHandler = coinHandler;
		this.coinChart = new CoinChart();
	}
	
	
	public void startGame() {
		System.out.println("Game Start!!");
		
		System.out.println("Player 이름을 정해주세요: ");
		String name = sc.nextLine();
		
		System.out.println("난이도(거지/평민/부자) 선택");
		String difficulty = sc.nextLine();
	
		switch(difficulty) {
			case("부자"):
				startingCash = 20000000; break;
			case("평민"):
				startingCash = 1000000; break;
			case("거지"):
				startingCash = 50000; break;
			default:
                System.out.println("잘못된 난이도, normal로 설정합니다.");
                startingCash = 100000;
		}
		
		player = new Player(name, startingCash);
		
		 npcList.add(NPCSetup.npc1);
		 npcList.add(NPCSetup.npc2);
		 npcList.add(NPCSetup.npc3);

		
		coinChart.addCoin(new CoinA());
		coinChart.addCoin(new CoinB());
		coinChart.addCoin(new CoinC());
		//coinHandler.simulatePriceChange(3);
		while(isRunning && currentRound <= maxRound) {
			System.out.println(currentRound + "일차");
//			System.out.println("[시간: 30초 남음]");
			
			coinHandler.simulatePriceChange(1);
			
			coinChart.showCoinList();
			
			timer = new Timer(120);
            timer.start();

            playerAction();
            
            if (player.getWallet().getTotalCoinsPrice() <= 0) {
            	System.out.println("=== GAME OVER ===");
                System.out.println("파산했습니다! " + player.getName() + "은 코인하지 마셈 ㄷㄷ");
                break;
            }
            for (NPCPlayer npc : npcList) {
                npc.takeAction(coinChart);
            }
            printRoundResult(currentRound);
            currentRound++;
		}
		
		printFinalResult();
		
	}
	
    // 중간 결과 (거래 종료 시점)
    private void printRoundResult(int round) {
        System.out.println("=== " + round + "일차 거래 종료 ===");
        player.showStatus();
        for (NPCPlayer npc : npcList) {
            npc.showStatus();
        }
        System.out.println();
    }

    // 최종 결과 (게임 종료 시)
    private void printFinalResult() {
        System.out.println("=== 최종 결과 ===");
        // 수익률 계산하는 로직
        int finalAsset = player.getCash() + player.getWallet().getTotalCoinsPrice();
        int profit = finalAsset - startingCash;
        double profitRate = (double) profit / startingCash * 100;
        
        System.out.println("수익률: " + profitRate + "%");
    }
	
    private int playerAction() {
    	
        while (!timer.isTimeOver()) {
            System.out.println("1. 매도 2. 매수 3. SKIP 4. 나가기");
            System.out.print("선택: ");

            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine(); // 개행 처리

                switch (choice) {
                    case 1:
                        
                        player.getWallet().showMyCoins();
                        System.out.println("판매할 코인의 이름과 개수을 입력해주세요.");
                        System.out.print("코인 이름: ");
                        String coinToSell = sc.nextLine();
                        System.out.print("개수: ");
                        int amountToSell = sc.nextInt();
                        sc.nextLine();

                        player.sellCoin(coinToSell, amountToSell);
                        break;
                    case 2:
                        coinChart.showCoinList();
                        System.out.println("구매할 코인의 번호와 개수을 입력해주세요.");
                        System.out.print("코인 번호: ");
                        int index = sc.nextInt();
                        sc.nextLine();
                        System.out.print("개수: ");
                        int amountToBuy = sc.nextInt();
                        sc.nextLine();

                        player.buyCoin(coinChart, index, amountToBuy);
                        break;
                    case 3:
                        System.out.println("하루를 건너뜁니다.");
                        timer.stopTimer();
                        return 1;
                    case 4:
                        System.out.println("게임을 종료합니다.");
                        isRunning = false;
                        timer.stopTimer();
                        return -1;
                    default:
                        System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                }
            }
        }
        System.out.println(currentRound + "일차가 지나갑니다.");
        timer.stopTimer();
        
        return 1;
    }

}