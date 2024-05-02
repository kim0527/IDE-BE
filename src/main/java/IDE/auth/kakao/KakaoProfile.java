package IDE.auth.kakao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class KakaoProfile {

    private Integer id;
    private String nickname;
    private String profileImage;

    public KakaoProfile(String jsonResponseBody){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonResponseBody);

        this.id = element.getAsJsonObject().get("id").getAsInt();

        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        this.nickname = properties.getAsJsonObject().get("nickname").getAsString();
        this.profileImage = properties.getAsJsonObject().get("profile_image").getAsString();
    }

    private LocalDateTime parseConnectedDate(String connectedAt) {

        String result = connectedAt.substring(0, connectedAt.length() - 1);

        return LocalDateTime.parse(result, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }




}