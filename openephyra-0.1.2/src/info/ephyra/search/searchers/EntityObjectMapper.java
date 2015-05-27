/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ephyra.search.searchers;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author l33yang
 */
public class EntityObjectMapper {

    private d d;

    /**
     * @return the d
     */
    public d getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(d d) {
        this.d = d;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class d {

        private List<Results> results;

        /**
         * @return the results
         */
        public List<Results> getResults() {
            return results;
        }

        /**
         * @param results the results to set
         */
        public void setResults(List<Results> results) {
            this.results = results;
        }

        public static class Results {

            private Metadata __metadata;

            /**
             * @return the __metadata
             */
            public Metadata get_metadata() {
                return __metadata;
            }

            /**
             * @param _metadata the __metadata to set
             */
            public void set_metadata(Metadata _metadata) {
                this.__metadata = _metadata;
            }

            public static class Metadata {

                private String uri;
                private String type;
                private String ID;
                private String Title;
                private String Description;
                private String DisplayUrl;
                private String Url;

                /**
                 * @return the uri
                 */
                public String getUri() {
                    return uri;
                }

                /**
                 * @param uri the uri to set
                 */
                public void setUri(String uri) {
                    this.uri = uri;
                }

                /**
                 * @return the type
                 */
                public String getType() {
                    return type;
                }

                /**
                 * @param type the type to set
                 */
                public void setType(String type) {
                    this.type = type;
                }

                /**
                 * @return the ID
                 */
                public String getID() {
                    return ID;
                }

                /**
                 * @param ID the ID to set
                 */
                public void setID(String ID) {
                    this.ID = ID;
                }

                /**
                 * @return the Title
                 */
                public String getTitle() {
                    return Title;
                }

                /**
                 * @param Title the Title to set
                 */
                public void setTitle(String Title) {
                    this.Title = Title;
                }

                /**
                 * @return the Description
                 */
                public String getDescription() {
                    return Description;
                }

                /**
                 * @param Description the Description to set
                 */
                public void setDescription(String Description) {
                    this.Description = Description;
                }

                /**
                 * @return the DisplayUrl
                 */
                public String getDisplayUrl() {
                    return DisplayUrl;
                }

                /**
                 * @param DisplayUrl the DisplayUrl to set
                 */
                public void setDisplayUrl(String DisplayUrl) {
                    this.DisplayUrl = DisplayUrl;
                }

                /**
                 * @return the Url
                 */
                public String getUrl() {
                    return Url;
                }

                /**
                 * @param Url the Url to set
                 */
                public void setUrl(String Url) {
                    this.Url = Url;
                }
            }
        }
    }
}
