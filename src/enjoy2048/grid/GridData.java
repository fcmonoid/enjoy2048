package enjoy2048.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import enjoy2048.Index;
import enjoy2048.Number;

class GridData {
	
	private GridPlate gridPlate = null;
	private Map<Index, Number> matrix = new HashMap<>();
	private List<Index> mergeList = new ArrayList<>();
	//private int numOfEmpties = 0;

	
	GridData(GridPlate gridPlate) {
		this.gridPlate = gridPlate; 

		for (Index i : Index.values()) {
			matrix.put(i, Number.Empty);
		}

		matrix.put(Index.I44, Number.OddNumber);
		matrix.put(Index.I_1_1, Number.OddNumber);
	}

	
	// i is for x coordinate (various columns)
	// j is for y coordinate (various rows)
	Number get(Index index) {
		return matrix.get(index);
	}

	Iterable<Index> getAllIndex(){
		return matrix.keySet();
	}
	void put(Index index, Number newNumber) {
		matrix.put(index, newNumber);
	}

	@Override
	public String toString() {
		String s = "";
		for (int y = 0; y < GridPlate.MAX; y++) {
			for (int x = 0; x < GridPlate.MAX; x++)
				s = s + " " + get(Index.getIndex(x, y)).getValue();
			s += "\n";
		}
		return s;
	}

	String getString(Index index) {
		return get(index).toString();
	}


	
	////////////////////////// check ////////////////////////////////
	boolean possibleMovesExist() {
		for (int x=0; x < GridPlate.MAX; x++)
			if (isMergableRowOrCol(Index.colFromBottomToTop(x, GridPlate.MAX)))
				return true;
		
		for (int y=0; y < GridPlate.MAX; y++)
			if (isMergableRowOrCol(Index.rowFromLeftToRight(y, GridPlate.MAX)))
				return true;
				
		return false;
	}

	boolean isMergableRowOrCol(Iterable<Index> decIndex) {
		// initialize border lines
		Index rightNext = Index.I44;
		put(rightNext, Number.OddNumber);

		for (Index i : decIndex) { 	// merge in reverse order! eg. 0222 => 0024(o) 0042(x)
			if (matrix.get(rightNext) == matrix.get(i))
				return true;
			rightNext = i;
		}
		return false;
	}

	/*
	boolean randomFillingAndEvaluation() {
		numOfEmpties = gridPlate.randomFilling(1);

		if (numOfEmpties > 0)
			return false;

		if (noMorePossibleMoves())
			return true;

		return false;
	}
	*/

	boolean isInMergeList(Index index) { // merged number should be
											// highlighted in GUI window
		if (this.mergeList.contains(index))
			return true;
		return false;
	}

	void clearTemporaryData() {
		mergeList.clear();
		gridPlate.clearRandomList();
	}

	private boolean isEmptyRowOrCol(Iterable<Index> index) { // 0000
		// True if all elements in nth row are empty
		for (Index i : index) {
			if (matrix.get(i) != Number.Empty)
				return false;
		}
		return true;
	}

	private boolean isNonemptyBeforeEmpty(Index p, Index b) { // 0200
		return (matrix.get(p) != Number.Empty) && (matrix.get(b) == Number.Empty);
	}

	///////////////////////// control ///////////////////////////
	private void shiftEmptiesToOpposite(Iterable<Index> index, int j) {
		if (isEmptyRowOrCol(index))
			return;

		boolean change = true;
		while (change) {
			change = false;
			Index prev = Index.I_1_1;
			put(prev, Number.Empty); // initialize borderline
			for (Index i : index) {
				if (isNonemptyBeforeEmpty(prev, i)) {// 0200 => 0020
					put(i, matrix.get(prev));
					put(prev, Number.Empty);
					change = true;
				}
				prev = i;
			}
		}
		put(Index.I_1_1, Number.OddNumber); // roll back for I_1_1
	}

	private Number mergeToRightNext(Index i, Index rightNext) {
		// assert get(i) == get(rightNext)

		Number currentNumber = get(i);

		// When both are Empties, do nothing
		if (currentNumber == Number.Empty)
			return Number.Empty;

		// Otherwise, merge them with doubledNumber : 22 => 04
		Number doubledNumber = currentNumber.getNext();
		put(rightNext, doubledNumber);
		put(i, Number.Empty);

		// set score and add this index to mergeList
		gridPlate.increaseScore(doubledNumber.getValue());
		mergeList.add(rightNext);

		return doubledNumber;
	}

	void onSwipeForEachRow(int j, Iterable<Index> incIndex, Iterable<Index> decIndex, Iterable<Index> newIncIndex) {
		if (isEmptyRowOrCol(incIndex)) // eg. 0000
			return;

		shiftEmptiesToOpposite(incIndex, j); // eg. 0200 => 0002

		// initialize border lines
		Index rightNext = Index.I44;
		put(rightNext, Number.OddNumber);

		for (Index i : decIndex) { // merge in reverse order! eg. 0222 =>
									// 0024 (o) 0042(x)
			if (matrix.get(rightNext) == matrix.get(i)) {
				gridPlate.setWin(mergeToRightNext(i, rightNext)); // eg.
																	// 0220
																	// =>
																	// 0040
				shiftEmptiesToOpposite(newIncIndex, j); // eg. 0040 => 0004
			}
			rightNext = i;
		}
	}

	List<Index> collectEmpties() {
		List<Index> candidates = new ArrayList<Index>();
		for (Index index : getAllIndex()) {
			if (index.isActualIndex() && get(index) == Number.Empty) {
				candidates.add(index);
			}
		}
		return candidates;
	}
	/*void setNumOfEmpties(int size) {
		numOfEmpties = size;
	}
	*/

	/*

	public void randomFilling(int n) {
		// TODO Auto-generated method stub
		for (int i=0 ; i < n; i++ )
			gridPlate.randomFilling();
	}
	*/

}