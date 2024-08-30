package edu.liubomur;

import okhttp3.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

//https://www.voicerss.org/personel/ api key 54484752da284a55a1d1d6ad13088e43
//TODO explore documentation how to generate speech
//https://www.voicerss.org/api/

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://voicerss-text-to-speech.p.rapidapi.com/"))
                .header("x-rapidapi-key", "")
                .header("x-rapidapi-host", "voicerss-text-to-speech.p.rapidapi.com")
                .header("Content-Type", "multipart/form-data; boundary=---011000010111000001101001")
                .method("POST", HttpRequest.BodyPublishers.ofString("-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"src\"\r\n\r\nHello, world!\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"hl\"\r\n\r\nen-us\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"r\"\r\n\r\n0\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"c\"\r\n\r\nmp3\r\n-----011000010111000001101001\r\nContent-Disposition: form-data; name=\"f\"\r\n\r\n8khz_8bit_mono\r\n-----011000010111000001101001--\r\n\r\n"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());

        InputStream inputStream = new ByteArrayInputStream(response.body().getBytes());
        OutputStream outputStream = new FileOutputStream("kirreal.mp3");

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        System.out.println("MP3 file downloaded successfully!");

        /*String requestBody = Files.readString(Path.of("kirreal.json"), Charset.defaultCharset());
        System.out.println(requestBody);

        URL url = new URL("https://api.play.ht/api/v2/tts/stream");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "audio/mpeg");
        connection.setRequestProperty("AUTHORIZATION", "8d787fcd18b5433e8a33a36540bd9009");
        connection.setRequestProperty("X-USER-ID", "2wTBwENGoNYsXkwdMh0AbaVH7iW2");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String jsonInputString = "{\r\n  \"text\": \"kyrylo\",\r\n  \"voice\": \"s3://voice-cloning-zero-shot/d9ff78ba-d016-47f6-b0ef-dd630f59414e/female-cs/manifest.json\",\r\n  \"output_format\": \"mp3\"\r\n}";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Stream the response directly to file
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            OutputStream outputStream = new FileOutputStream("kirreal.mp3");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("MP3 file downloaded successfully!");
        } else {
            System.out.println("Failed to download file. Response code: " + responseCode);
        }*/

        /*OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"text\": \"Кирило\",\r\n  \"voice\": \"s3://voice-cloning-zero-shot/d9ff78ba-d016-47f6-b0ef-dd630f59414e/female-cs/manifest.json\",\r\n  \"output_format\": \"mp3\"\r\n}");
        Request request = new Request.Builder()
                .url("https://api.play.ht/api/v2/tts/stream")
                .method("POST", body)
                .addHeader("Accept", "audio/mpeg")
                .addHeader("AUTHORIZATION", "8d787fcd18b5433e8a33a36540bd9009")
                .addHeader("X-USER-ID", "2wTBwENGoNYsXkwdMh0AbaVH7iW2")
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;

                File targetFile = new File("kirreal.mp3");

                Files.copy(response.body().byteStream(), targetFile.toPath());
            } else {
                System.out.println("Response code: " + response.code());
                System.out.println("Error: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
