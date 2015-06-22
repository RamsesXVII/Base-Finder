package baseFinder;

import java.util.List;

import com.google.api.services.genomics.Genomics;
import com.google.api.services.genomics.model.ReadGroupSet;
import com.google.api.services.genomics.model.SearchReadGroupSetsRequest;
import com.google.common.collect.Lists;

public class ReadGroupsSetIDGetter  {

	private Genomics genomics;

	public ReadGroupsSetIDGetter(Genomics genomics) {
		this.genomics=genomics;
	}

	public String getReadGroupSetID(String datasetId, String sample) {
		/*
		 * Specifico il datasetID e sample e voglio che mi sia ritornato il l'id del read group set che lo contiene
		 */
		try{

			SearchReadGroupSetsRequest readsetsReq = new SearchReadGroupSetsRequest()
			.setDatasetIds(Lists.newArrayList(datasetId))
			.setName(sample);
			System.out.println("dataSetIDs e sample in cui voglio cercare le basi"+readsetsReq.toString());
			
			List<ReadGroupSet> readGroupSets = genomics.readgroupsets().search(readsetsReq)
					.setFields("readGroupSets(id)").execute().getReadGroupSets();
			if (readGroupSets == null || readGroupSets.size() != 1) {
				return("Searching for " + sample
						+ " didn't return the right number of read group sets"); }
			
			return readGroupSets.get(0).getId();
		}catch (Throwable t) {
			t.printStackTrace();
		}
		return null;}

}
