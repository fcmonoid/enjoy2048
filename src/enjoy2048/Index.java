package enjoy2048;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public enum Index {
	I_1_1(-1,-1), 	// Assigned with OddNumber, but Assigned with Empty if needed
	I44(4,4), 		// Assigned with OddNumber
	
	I00(0,0), I10(1,0),I20(2,0),I30(3,0),
	I01(0,1), I11(1,1),I21(2,1),I31(3,1),
	I02(0,2), I12(1,2),I22(2,2),I32(3,2),
	I03(0,3), I13(1,3),I23(2,3),I33(3,3);
	
	private int xCoordinate; // --
	private int yCoordinate; // |
	
	private Index(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
	}
	
	int x() { return xCoordinate;}
	int y() { return yCoordinate;}
	
	@Override
	public
	String toString(){
		return xCoordinate+ "," + yCoordinate;
	}

	static Map<Integer,Map<Integer,Index>> indexMap = new HashMap<>();
	static final int MAX = 4;
	
	static {
		for (int x = -1; x <= MAX ; x++){
			// including -1, 4 for I_1_1 and I44
			indexMap.put(x, new HashMap<>());
		}
		
		for(Index index: Index.values()){
			int x = index.xCoordinate;
			int y = index.yCoordinate;
			Map<Integer,Index> mapForX = indexMap.get(x);
			mapForX.put(y,index);
		}		
	}
	
	public static Index getIndex(int x, int y) {
		return indexMap.get(x).get(y);
	}

	// these don't include dummy indices (like I44, I_1_1)
	public static Iterable<Index> rowFromLeftToRight(int y, int max) {
		return new Iterable<Index>(){
			@Override
			public Iterator<Index> iterator() {
				return new Iterator<Index>(){
					int x = 0;
					@Override
					public boolean hasNext() {
						if (x < max) 
							return true;
						return false;
					}

					@Override
					public Index next() {
						//System.out.println("inside next()"+x+":"+Y);
						Index index = Index.getIndex(x, y);
						//System.out.println("right after next()" + index.xCoordinate +":"+index.yCoordinate);
						x++;
						return index;
					}
				};
			}
		};
	}
	
	public static Iterable<Index> rowFromRightToLeft(int y, int max) {
		return new Iterable<Index>(){
			@Override
			public Iterator<Index> iterator() {
				return new Iterator<Index>(){
					int x = max - 1;
					@Override
					public boolean hasNext() {
						if (x >= 0) 
							return true;
						return false;
					}
		
					@Override
					public Index next() {
						Index index = Index.getIndex(x, y);
						x--;
						return index;
					}
				};
			}
		};
	}
	
	public static Iterable<Index> colFromTopToBottom(int x, int max) {
		return new Iterable<Index>(){
			@Override
			public Iterator<Index> iterator() {
				return new Iterator<Index>(){
					int y = 0;
					@Override
					public boolean hasNext() {
						if (y < max) 
							return true;
						return false;
					}
		
					@Override
					public Index next() {
						Index index = Index.getIndex(x, y);
						y++;
						return index;
					}
				};
			}
		};
	}
	
	public static Iterable<Index> colFromBottomToTop(int x, int max) {
		return new Iterable<Index>(){
			@Override
			public Iterator<Index> iterator() {
				return new Iterator<Index>(){
					int y = max - 1;
					@Override
					public boolean hasNext() {
						if (y >= 0) 
							return true;
						return false;
					}
		
					@Override
					public Index next() {
						Index index = Index.getIndex(x, y);
						y--;
						return index;
					}
				};
			}
		};
	}
	
	public static Iterable<Index> allIndex (int max) {
		return new Iterable<Index>(){
			@Override
			public Iterator<Index> iterator() {
				return new Iterator<Index>(){
					int x = 0, y = 0;
					@Override
					public boolean hasNext() {
						if (x < max && y < max) 
							return true;
						return false;
					}
		
					@Override
					public Index next() {
						Index index = Index.getIndex(x, y);
						x++;
						if (x >= max) {
							x = 0; 
							y++;
						}
						return index;
					}
				};
			}
		};
	}

	public boolean isActualIndex() {
		if (this == I_1_1 && this == I44 ) 
				return false;
		return true;
	}
}
