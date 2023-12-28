import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.openai.com/v1/engines/text-davinci-003/completions";  // Use o URL correto para a versão da API que você está usando
        String token = "KEY_API";

        // Exemplo de prompt para a GPT-3.5
        String prompt = "Calcule o IMC e me mostre apenas o resultado, peso=70 altura=1.7";

        // Montar a solicitação
        String jsonBody = "{\"prompt\": \"" + prompt + "\"}";
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonBody, mediaType);

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                //System.out.println("Resposta da API GPT-3.5: " + responseBody);
                JSONObject myObject = new JSONObject(responseBody);
                JSONArray choices = myObject.getJSONArray("choices");
                JSONObject sonObject = choices.getJSONObject(0);
                String sonData = sonObject.getString("text");
                System.out.println(sonData);

            } else {
                System.out.println("Erro na requisição: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
