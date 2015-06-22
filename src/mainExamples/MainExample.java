package mainExamples;

import baseFinder.BaseFinder;

import com.google.api.services.genomics.Genomics;

import connection.Autorizzazione;

import java.io.IOException;


public class MainExample {

/* TODO
 * Vedere se si trova come invocare comandi (Java su gitHUb)
 */
  public static void main(String[] args) throws IOException {
//      String datasetId = "10473108253681171589"; //  1000 Genomes dataset ID
//      String sample = "NA12872";
//      String referenceName = "22";
//      final Long referencePosition = 51003835L;
      
	  
	      String datasetId = "10473108253681171589"; //  1000 Genomes dataset ID
	      String sample = "NA10847";
	      String referenceName = "22";
	      final Long referencePosition = 25652292L;
	  
	  Autorizzazione autorizzazione= new Autorizzazione();
	  Genomics genomics = autorizzazione.getAuthorization(args);

	  // This example gets the read bases for a sample at specific a position
      BaseFinder bf= new BaseFinder(genomics);
      String bases=bf.getBasesAtPosition(datasetId, sample, referenceName, referencePosition);
 
      
//      //// This example gets the variants for a sample at a specific position TODO ??? codice disponibile su gitHub 
//      VariantFinder vf= new VariantFinder(genomics);
//      String variants=vf.getVariantsAtPosition(datasetId, sample, referenceName, referencePosition);
//      System.out.println(variants);
          
      
  }
}
