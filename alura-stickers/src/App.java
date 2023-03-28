import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexao HTTP e buscar os tops 250 filmes

        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        // System.out.println(body);

        // extrair só os dados que interessam(títulos, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados

        for (int i = 0; i < listaDeFilmes.size(); i++) {
            Map<String, String> filme = listaDeFilmes.get(i);
            System.out.println("\033[40m" + "\033[1;31m" + "\u001b[1mTítulo: \u001b[m" + filme.get("title") + "\033[0m");
            System.out.println("\033[40m" + "\033[1;31m" + "\u001b[1mImagem: \u001b[m" + filme.get("image") + "\033[0m");
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelinhas = (int) classificacao;
            System.out.print("\033[40m" + "\033[1;31m" + "\u001b[1mClassificação: \u001b[m" + filme.get("imDbRating")+ " " + "\033[0m");
            for (int n = 1; n <= numeroEstrelinhas; n++) {
               
                if (numeroEstrelinhas < 9) {
                    System.out.print("👎");
                } else {
                    System.out.print("⭐");
                    
                }
            }
            System.out.println("\n");
        }
    }

}
