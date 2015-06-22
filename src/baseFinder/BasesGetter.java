package baseFinder;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.beust.jcommander.internal.Maps;
import com.google.api.services.genomics.Genomics;
import com.google.api.services.genomics.model.Read;
import com.google.api.services.genomics.model.SearchReadsRequest;
import com.google.common.collect.Lists;

public class BasesGetter {

	private Genomics genomics;

	public BasesGetter(Genomics genomics) {
		this.genomics=genomics;
	}


	public String getBases(String readGroupSetId, String referenceName,
			Long referencePosition, String sample) {
	try{ 
		/*/Una volta ottenuto l'id effettuo la ricerca nella posizione a cui sono interessato
		 * 
		 */
			
			SearchReadsRequest readsReq = new SearchReadsRequest()
			.setReadGroupSetIds(Lists.newArrayList(readGroupSetId))
			.setReferenceName(referenceName)
			.setStart(Long.valueOf(referencePosition)) //BigInt non valitdo
			.setEnd(Long.valueOf(referencePosition + 1)) //prendo più basi TODO
			.setPageSize(1024);
			
			//readgroupset
			System.out.println("Il formato della richiesta delle reads: \n"
					+ "NB: il readGroupSetID corrisponde a tutte le reads presenti in sample \n"
					+ "viene specificato in start dove cercare le basi"+readsReq.toString());

			System.out.println("ecco l'elenco delle reads sovrapposte nella posizione specificata");
			List<Read> reads = genomics.reads().search(readsReq)
					.setFields("alignments(alignment,alignedSequence)").execute().getAlignments();
			
			//elementi readGroupset
			for(Read r:reads){
				System.out.println(r.toString());
			}
			Map<Character, Integer> baseCounts = Maps.newHashMap();
			for (Read read : reads) {
				//TODO rincontrollare sottrazione
				int index = BigInteger.valueOf(referencePosition).subtract(BigInteger.valueOf(
						read.getAlignment().getPosition().getPosition())).intValue();

				Character base = read.getAlignedSequence().charAt(index);
				
				System.out.println("index: "+index+" base "+base);
				if (!baseCounts.containsKey(base)) {
					baseCounts.put(base, 0);
				}
				baseCounts.put(base, baseCounts.get(base) + 1);
			}

			StringBuilder s=new StringBuilder();
			s.append(sample + " bases on " + referenceName + " at "
					+ referencePosition + " are \n");
			for (Map.Entry<Character, Integer> entry : baseCounts.entrySet()) {
				s.append(entry.getKey() + ": " + entry.getValue()+ "\n" );
			}
			System.out.println(s.toString());

		} catch (IllegalStateException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

}
