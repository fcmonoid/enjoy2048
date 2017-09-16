package enjoy2048.grid;

import java.util.ArrayList;
import java.util.List;

import enjoy2048.Index;
import enjoy2048.Number;

public class GridPlate {
	static final int MAX = 4;
	private static GridPlate theGridPlate = null;
	
	private boolean win = false;
	private long score = 0;
	
	private GridData gridData;
	private RandomController randomController;
	private List<Index> randomList = new ArrayList<>();
	
	public static GridPlate getPlate() {
		theGridPlate = new GridPlate();
		theGridPlate.initialize();
		return theGridPlate;
	}

	GridPlate() {
		gridData = new GridData(this);
		randomController = new RandomController();
	}

	////////////////////////// auxiliary ////////////////////////////////////
	private void initialize() {
		randomFilling(2); 
	}
	
	public String getString(Index index) {
		return gridData.getString(index);
	}

	void increaseScore(int value) {
		score += value;
	}
	
	public String getScoreStr() {
		return Long.toString(score);
	}
	
	void setWin(Number doubledNumber) {
		if (doubledNumber.getValue() > Number.N1024.getValue())
		// if (doubledNumber.getValue() > Number.N16.getValue())
			win = true;
		else
			win = false;
	}

	void clearWin() {
		win = false;
	}

	public boolean isWin() { // win/finish logic should be organized... 9.13
		return win;
	}

	public void addRandomList(Index index){
		randomList.add(index);
	}
	
	public void clearRandomList() {
		randomList.clear();
	}

	public boolean isNewRandom(Index index){
		if (randomList.contains(index))
			return true;
		return false;
	}
	
	public boolean isNewMerge(Index index) { // merged number should be
		// highlighted in GUI window
		return gridData.isInMergeList(index);
	}
	
	public void clearDataforNext(){
		gridData.clearTemporaryData();
	}
	
	public boolean isFailbak() {
		
		List<Index> empties = gridData.collectEmpties();
		int numOfEmpties = empties.size();
		
		randomFilling(1);

		if (numOfEmpties > 0)
			return false;

		//if (gridData.noMorePossibleMoves())
		if (gridData.possibleMovesExist())
			return false;

		// return false;
		return true;
	}
	
	public boolean isFail() {
		List<Index> empties = gridData.collectEmpties();
		int numOfEmpties = empties.size();
		
		if (numOfEmpties > 0)
			return false;

		if (gridData.possibleMovesExist())
			return false;

		return true;
		
	}


	////////////////////////// control ///////////////////////////////

	public void onSwipeToRight() {
		for (int j = 0; j < MAX; j++) {
			Iterable<Index> incIndex = Index.rowFromLeftToRight(j, MAX);
			Iterable<Index> decIndex = Index.rowFromRightToLeft(j, MAX);
			Iterable<Index> newIncIndex = Index.rowFromLeftToRight(j, MAX);

			gridData.onSwipeForEachRow(j, incIndex, decIndex, newIncIndex);
		}
	}

	public void onSwipeToLeft() {
		for (int j = 0; j < MAX; j++) {
			Iterable<Index> incIndex = Index.rowFromRightToLeft(j, MAX);
			Iterable<Index> decIndex = Index.rowFromLeftToRight(j, MAX);
			Iterable<Index> newIncIndex = Index.rowFromRightToLeft(j, MAX);

			gridData.onSwipeForEachRow(j, incIndex, decIndex, newIncIndex);
		}
	}

	public void onSwipeToTop() {
		for (int j = 0; j < MAX; j++) {
			Iterable<Index> incIndex = Index.colFromBottomToTop(j, MAX);
			Iterable<Index> decIndex = Index.colFromTopToBottom(j, MAX);
			Iterable<Index> newIncIndex = Index.colFromBottomToTop(j, MAX);

			gridData.onSwipeForEachRow(j, incIndex, decIndex, newIncIndex);
		}
	}

	public void onSwipeToBottom() {
		for (int j = 0; j < MAX; j++) {
			Iterable<Index> incIndex = Index.colFromTopToBottom(j, MAX);
			Iterable<Index> decIndex = Index.colFromBottomToTop(j, MAX);
			Iterable<Index> newIncIndex = Index.colFromTopToBottom(j, MAX);

			gridData.onSwipeForEachRow(j, incIndex, decIndex, newIncIndex);
		}
	}

	public int randomFilling(int nTimes) {
		List<Index> empties = gridData.collectEmpties();
		int numOfEmpties = empties.size();
		if (numOfEmpties <= 0 || nTimes <= 0) 
			return 0;
		
		int numOfRandoms = (nTimes > numOfEmpties ? numOfEmpties : nTimes);  
		
		for(int i = numOfRandoms; i > 0; i--) {
			addRandomList(randomController.randomFilling(gridData, empties));
			empties = gridData.collectEmpties();
		}
		
		return numOfRandoms;
	}

	public int getValue(Index index) {
		
		return gridData.get(index).getValue();
	}
}
