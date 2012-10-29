package tests;

import game.BlobStrategy;
import game.Board;
import game.Computer;
import game.Human;
import game.Movement;
import game.Player;
import game.Strategy;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import structures.Pair;
import structures.Point;

public class TestGenerateBoards {
	
	private Computer computer;
	private Human human;
	private Strategy strategy;
	
	@Before
	public void before(){
		this.human = new Human();
		this.computer = new Computer();
		this.strategy = new BlobStrategy(human, computer);
	}
	
	@Test
	public void upperLeftCorner() {
		Board board = starterBoard(); 
		Point source = Point.getInstance(0, 0);
		
		List<Pair<Board, Movement>> expect = new LinkedList<Pair<Board, Movement>>();
		
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(1, 0)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(0, 1)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(1, 1)));
		
		List<Pair<Board, Movement>> got = new LinkedList<Pair<Board, Movement>>();
		for (Pair<Board, Movement> move : strategy.boardsForMove(board, source)){
			got.add(move);
		}
		
		assert(got.containsAll(expect));
	}
	
	@Test
	public void twoSteps() {
		Board board = starterBoard(); 
		Point source = Point.getInstance(0, 0);
		
		List<Pair<Board, Movement>> expect = new LinkedList<Pair<Board, Movement>>();
		
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(2, 0)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(0, 2)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(1, 2)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(2, 1)));
		expect.add(mockAdyacentMove(human, board, source, Point.getInstance(2, 2)));
		
		List<Pair<Board, Movement>> got = new LinkedList<Pair<Board, Movement>>();
		for (Pair<Board, Movement> move : strategy.boardsForMove(board, source)){
			got.add(move);
		}
		
		assert(got.containsAll(expect));
	}
	
	
	@Test
	public void upperAdyacentEnemy() {
		Point source = Point.getInstance(4, 4);
		
		Board board = new Board(strategy, human, computer).
				putBlob(human, Point.getInstance(4, 4)).
				putBlob(computer, Point.getInstance(4, 5));
		
		Board expect = new Board(strategy, human, computer).
				putBlob(human, Point.getInstance(4, 4)).
				putBlob(human, Point.getInstance(4, 4)).
				putBlob(human, Point.getInstance(5, 5));
		
		List<Pair<Board, Movement>> got = new LinkedList<Pair<Board, Movement>>();
		for (Pair<Board, Movement> move : strategy.boardsForMove(board, source)){
			got.add(move);
		}
		
		assert(got.contains(expect));
	}
	
	private Pair<Board, Movement> mockAdyacentMove(Player p, Board board, Point source, Point target){
		return 	new Pair<Board, Movement>(
				((Board) board.clone()).putBlob(p, target), new Movement(source, target));
	}

	private Board starterBoard(){
		Board baseBoard = new Board(strategy, human, computer);
		baseBoard = baseBoard.
				putBlob(human, Point.getInstance(0, 0)).
				putBlob(human, Point.getInstance(0, 7)).
				putBlob(computer, Point.getInstance(7, 0)).
				putBlob(computer, Point.getInstance(7, 7));
		
		return baseBoard;
	}
	
}
