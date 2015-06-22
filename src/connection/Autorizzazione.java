package connection;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.extensions.java6.auth.oauth2.GooglePromptReceiver;
import com.google.api.services.genomics.Genomics;
import com.google.api.services.genomics.GenomicsScopes;
import com.google.cloud.genomics.utils.GenomicsFactory;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;

import java.io.File;

import connection.Arguments;

public class Autorizzazione {

  public Genomics getAuthorization(String[] args) {
    Arguments arguments = new Arguments();
    JCommander parser = new JCommander(arguments);

    try {
      parser.parse(args);

      // Autorizzazione
      VerificationCodeReceiver receiver = arguments.noLocalServer ?
          new GooglePromptReceiver() : new LocalServerReceiver();
      GenomicsFactory genomicsFactory = GenomicsFactory.builder("getting_started_java")
          .setScopes(Lists.newArrayList(GenomicsScopes.GENOMICS))
          .setVerificationCodeReceiver(Suppliers.ofInstance(receiver))
          .build();

      File clientSecrets = new File(arguments.clientSecretsFilename);
      if (!clientSecrets.exists()) {
        System.err.println(
            "Il file Client secrets " + arguments.clientSecretsFilename + " non esiste."
            + " Visita https://cloud.google.com/genomics/install-genomics-tools#authenticate per sapere come"
            + " ottenere e gestire un file client_secrets.json.  Se possiedi un client_secrets.json"
            + " in un determinato path, usa --client_secrets_filename <path>/client_secrets.json.");
      }
      return genomicsFactory.fromClientSecretsFile(clientSecrets);

    } catch (ParameterException e) {
      System.err.append(e.getMessage()).append("\n");
      parser.usage();
    } catch (Throwable t) {
      t.printStackTrace();
    }
	return null;
  }
}
