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
		/*
		Random rand = new Random();
		int posToBeFilled = rand.nextInt(candidates.size());
		Index indexToBeFilled = candidates.get(posToBeFilled); // 9.13 이걸 좀
																// 미리 찾아놓고
																// merge 하고
																// 나서 put하면
																// 될 것 같다.
		gridData.put(indexToBeFilled, Number.N2);

		return indexToBeFilled;
		*/
	}

	Index randomFilling(GridData gridData, List<Index> empties) {
			Index indexRandomFilled = dropRandom(gridData, empties);
			return indexRandomFilled;
	}
	
	
}