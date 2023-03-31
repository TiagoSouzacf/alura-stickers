import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexao HTTP e buscar os tops 250 filmes

       API api = API.IMDB_TOP_SERIES;
       //String url = api.getUrl();
       //ExtratorDeConteudo extrator = new ExtratorDeConteudoDaNasa();
       //ExtratorDeConteudo extrator = api.getExtrator();

       String url = "http://localhost:8080/linguagens";
       ExtratorDeConteudo extrator = new ExtratorDeConteudoDoIMDB();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        var diretorio = new File("saida/");
        diretorio.mkdir();

        // exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();

        for (int i = 0; i < conteudos.size(); i++) {
            Conteudo conteudo = conteudos.get(i);

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo = "saida/" + conteudo.getTitulo() + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println(i + ": " + conteudo.getTitulo());
            System.out.println();
        }
    }
}
