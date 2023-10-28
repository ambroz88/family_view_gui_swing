package cz.ambrogenea.familyvision.gui.swing.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.ambrogenea.familyvision.gui.swing.constant.Endpoints;
import cz.ambrogenea.familyvision.gui.swing.dto.*;
import cz.ambrogenea.familyvision.gui.swing.service.JsonParser;
import cz.ambrogenea.familyvision.gui.swing.utils.FileIO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class Connections {

    public static FamilyTree[] getTrees() throws IOException {
        return getResponse(new HttpGet(Endpoints.FAMILY_TREES), FamilyTree[].class);
    }

    public static FamilyTree createTree(FamilyTreeRequest request) throws IOException {
        return getResponse(generateJsonPost(Endpoints.FAMILY_TREES, request), FamilyTree.class);
    }

    public static VisualConfiguration getVisualConfiguration() throws IOException {
        return getResponse(new HttpGet(Endpoints.VISUAL), VisualConfiguration.class);
    }

    public static void updateVisualConfiguration(VisualConfiguration visualConfiguration) throws IOException {
        sendRequest(generateJsonPost(Endpoints.VISUAL, visualConfiguration));
    }

    public static TreeShapeConfiguration getThreeShapeConfiguration() throws IOException {
        return getResponse(new HttpGet(Endpoints.TREE_SHAPE), TreeShapeConfiguration.class);
    }

    public static void updateTreeShapeConfiguration(TreeShapeConfiguration visualConfiguration) throws IOException {
        sendRequest(generateJsonPost(Endpoints.TREE_SHAPE, visualConfiguration));
    }

    public static void uploadData(File file, Long treeId) throws IOException {
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("gedcomFile", file, ContentType.DEFAULT_BINARY, file.getName())
                .addTextBody("familyTreeId", treeId.toString(), ContentType.DEFAULT_TEXT)
                .build();
        HttpPost httpPost = new HttpPost(Endpoints.DATA);
//        httpPost.setHeader("Content-type", "multipart/form-data");
        httpPost.setEntity(entity);

        sendRequest(httpPost);
    }

    public static List<Person> getPersonsInTree(Long treeId) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet(Endpoints.PEOPLE_SIMPLE);
        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter("treeId", treeId.toString())
                .build();
        httpGet.setURI(uri);
        return Arrays.stream(getResponse(httpGet, Person[].class)).toList();
    }

    public static TreeModel generateTree(Long personId) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet(Endpoints.TREES);
        URI uri = new URIBuilder(httpGet.getURI())
                .addParameter("personId", personId.toString())
                .build();
        httpGet.setURI(uri);
        return getResponse(httpGet, TreeModel.class);
    }

    public static TreeModel updateTree() throws IOException {
        HttpPost httpPost = new HttpPost(Endpoints.TREES);
        return getResponse(httpPost, TreeModel.class);
    }

    public static TreeModel[] generateTreeModels(Long personId, String format) throws URISyntaxException, IOException {
        HttpPost httpPost = new HttpPost(Endpoints.DOC_GEN);
        URI uri = new URIBuilder(httpPost.getURI())
                .addParameter("personId", personId.toString())
                .addParameter("format", format)
                .build();
        httpPost.setURI(uri);
        return getResponse(httpPost, TreeModel[].class);
    }

    public static void uploadImageToDoc(InputStream imageStream, String familyName, Dimension size) throws IOException {
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("image", FileIO.copyInputStreamToFile(imageStream, familyName), ContentType.DEFAULT_BINARY, familyName)
                .addTextBody("familyName", familyName, ContentType.DEFAULT_TEXT)
                .addTextBody("imageWidth", String.valueOf(size.width), ContentType.DEFAULT_TEXT)
                .addTextBody("imageHeight", String.valueOf(size.height), ContentType.DEFAULT_TEXT)
                .build();
        HttpPut httpPut = new HttpPut(Endpoints.DOC_GEN);
        httpPut.setEntity(entity);

        sendRequest(httpPut);
    }

    public static void saveDocument(String fileName) throws IOException, URISyntaxException {
        HttpPost httpPost = new HttpPost(Endpoints.DOC_GEN + "/save-file");
        URI uri = new URIBuilder(httpPost.getURI())
                .addParameter("fileName", fileName)
                .build();
        httpPost.setURI(uri);
        sendRequest(httpPost);
    }


    private static HttpPost generateJsonPost(String url, Object requestBody) throws UnsupportedEncodingException, JsonProcessingException {
        HttpPost httpPost = new HttpPost(url);
        final StringEntity entity = new StringEntity(JsonParser.get().writeValueAsString(requestBody));
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        return httpPost;
    }

    private static void sendRequest(HttpRequestBase httpRequest) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        client.execute(httpRequest);
    }

    private static <T> T getResponse(HttpRequestBase httpRequest, Class<T> responseClass) throws IOException {
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(httpRequest)
        ) {
            final String jsonResponse = getStringFromInputStream(response.getEntity().getContent());
            return JsonParser.get().readValue(jsonResponse, responseClass);
        }
    }

    private static String getStringFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}
