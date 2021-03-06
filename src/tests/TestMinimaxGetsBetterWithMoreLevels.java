package tests;

import game.BlobStrategy;
import game.Board;
import game.Computer;
import game.Game;
import game.Human;
import game.Movement;
import game.Strategy;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ai.ABPMinimax;
import ai.LMinimax;
import ai.Minimax;
import ai.TBIDABPMinimax;
import ai.TBIDLMinimax;

public class TestMinimaxGetsBetterWithMoreLevels{
	
	private Computer computer;
	private Human human;
	private Strategy strategy;
	
	private Game game;
	private Minimax minimaxI;
	private Minimax minimaxS;

	private Minimax cycle(Minimax m){
		if (m == minimaxI){
			return minimaxS;
		}
		return minimaxI;
	}
	
	@Before
	public void before(){
		game = new Game();
		computer = game.getComputer();
		human = game.getHuman();
		strategy = new BlobStrategy(human, computer);
	}
	
	public boolean testMinimax() {
		Board board = game.getBoard();
		Minimax current = minimaxS;
		while (!game.hasWin(computer) && !game.hasWin(human)){
			Movement move;
			current.setBoard(game.getBoard());
			move = current.getBestMove();
			if (move == null){
				break;
			}
			game.move(move);
			System.out.println(move);
			System.out.println(game.getBoard());
			current = cycle(current);
		}
		System.out.println("Last board:");
		System.out.println(game.getBoard());
		System.out.println("Gradient for computer: " + (board.countTilesForPlayer(computer) - board.countTilesForPlayer(human)));
		return board.countTilesForPlayer(computer) - board.countTilesForPlayer(human) > 0;
	}
	
	@Test
	public void testABPMinimax(){
		Board board = game.getBoard();
		minimaxI = new ABPMinimax(4, strategy, board, computer, human);
		minimaxS = new ABPMinimax(2, strategy, board, human, computer);
		Assert.assertEquals(true, testMinimax());
	}
	
	@Test
	public void testTBIDABPMinimax(){
		Board board = game.getBoard();
		minimaxI = new TBIDABPMinimax(375, strategy, board, computer, human);
		minimaxS = new TBIDABPMinimax(125, strategy, board, human, computer);
		Assert.assertEquals(true, testMinimax());
	}
	
	@Test
	public void testTBIDLMinimax(){
		Board board = game.getBoard();
		minimaxI = new TBIDLMinimax(300, strategy, board, computer, human);
		minimaxS = new TBIDLMinimax(200, strategy, board, human, computer);
		Assert.assertEquals(true, testMinimax());
	}
	
	//@Test
	public void testLMinimax(){
		Board board = game.getBoard();
		minimaxI = new LMinimax(4, strategy, board, computer, human);
		minimaxS = new LMinimax(2, strategy, board, human, computer);
		Assert.assertEquals(true, testMinimax());
	}
	
	
}
