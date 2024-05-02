package api.v1.auth.kakao;

import lombok.Data;
@Data
public class KakaoOAuth {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private int refresh_token_expries_in;
}