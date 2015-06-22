package baseFinder;

import com.google.api.services.genomics.Genomics;


public class BaseFinder {
	private Genomics genomics;

	public BaseFinder(Genomics genomics){
		this.genomics=genomics;
	}

	public String getBasesAtPosition(String datasetId, String sample, String referenceName, Long referencePosition){

			// 1. Cerco il read group set ID di sample
			ReadGroupsSetIDGetter rg= new ReadGroupsSetIDGetter(genomics);
			String readGroupSetId= rg.getReadGroupSetID(datasetId,sample);


			// 2. Una volta ottenuto il read group set ID,
			// cerco le reads alla posizione in cui sono interessato
			BasesGetter r= new BasesGetter(genomics);
			String bases=r.getBases(readGroupSetId,referenceName,referencePosition,sample);
			
			return bases;
	}
}
