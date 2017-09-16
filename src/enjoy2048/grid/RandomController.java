package enjoy2048.grid;

import java.util.List;
import java.util.Random;

import enjoy2048.Index;
import enjoy2048.Number;

class RandomController {
	
	RandomController() { }

	private Index dropRandom(GridData gridData, List<Index> candidates) {
		Random random4index = new Random();
		int posToBeFilled = random4index.nextInt(candidates.size());
		Index indexToBeFilled = candidates.get(posToBeFilled);
		
		Random random4Number = new Random();
		int numberSpan = random4Number.nextInt(8);
		Number number;
		if (numberSpan == 0)
			number = Number.toNumber(4);
		else
			number = Number.toNumber(2);
		
		gridData.put(indexToBeFilled, number);

		return indexToBeFilled;
	}

	Index randomFilling(GridData gridData, List<Index> empties) {
			// this method and dropRandom() returns the index filled with the random value
			return dropRandom(gridData, empties);
	}
	
	
}