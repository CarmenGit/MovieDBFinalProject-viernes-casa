
package es.cice.moviedbfinalproject.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//import es.cice.moviedbfinalproject.R;

public class Images {

    private String base_url;
    private String secure_base_url;
    private List<String> backdrop_sizes = null;
    private List<String> logo_sizes = null;
    private List<String> poster_sizes = null;
    private List<String> profile_sizes = null;
    private List<String> still_sizes = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getBaseUrl() {
        return base_url;
    }

    public void setBaseUrl(String baseUrl) {
        this.base_url = baseUrl;
    }

    public String getSecureBaseUrl() {
        return secure_base_url;
    }

    public void setSecureBaseUrl(String secureBaseUrl) {
        this.secure_base_url = secureBaseUrl;
    }

    public List<String> getBackdropSizes() {
        return backdrop_sizes;
    }

    public void setBackdropSizes(List<String> backdropSizes) {
        this.backdrop_sizes = backdropSizes;
    }

    public List<String> getLogoSizes() {
        return logo_sizes;
    }

    public void setLogoSizes(List<String> logoSizes) {
        this.logo_sizes = logoSizes;
    }

    public List<String> getPosterSizes() {
        return poster_sizes;
    }

    public void setPosterSizes(List<String> posterSizes) {
        this.poster_sizes = posterSizes;
    }

    public List<String> getProfileSizes() {

        return profile_sizes;
    }

    public void setProfileSizes(List<String> profileSizes) {
        this.profile_sizes = profileSizes;
    }

    public List<String> getStillSizes() {
        return still_sizes;
    }

    public void setStillSizes(List<String> stillSizes) {
        this.still_sizes = stillSizes;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
