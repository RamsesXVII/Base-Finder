package connection;

import com.beust.jcommander.Parameter;

public class Arguments {
    @Parameter(names = "--client_secrets_filename",
        description = "Path to client_secrets.json") 
    public String clientSecretsFilename = "/Users/mattiaiodice/Desktop/lm/client_secrets.json";

    @Parameter(names = "--noauth_local_webserver",
        description = "If your browser is on a different machine then run this" + 
            "application with this command-line parameter")
    public boolean noLocalServer = false;
  }
