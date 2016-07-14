package lv.ctco;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static lv.ctco.Consts.OFFER_PATH;

public class HeaderBuilder {
    public static HttpHeaders buildHeader(UriComponentsBuilder b, String path, Object... uriVariableValues) {
        UriComponents uriComponents =
                b.path(path).buildAndExpand(uriVariableValues);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uriComponents.toUri());
        return responseHeaders;
    }
}
