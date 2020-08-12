package co.wishroll.models.repository.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {

        @SerializedName("access")
        @Expose
        private String access;

        @SerializedName("csrf")
        @Expose
        private String csrf;

        @SerializedName("access_expires_at")
        @Expose
        private String accessExpiresAt;

        /**
         * No args constructor for use in serialization
         *
         */
        public AccessToken() {
        }

        /**
         *
         * @param access
         * @param csrf
         * @param accessExpiresAt
         */
        public AccessToken(String access, String csrf, String accessExpiresAt) {
            super();
            this.access = access;
            this.csrf = csrf;
            this.accessExpiresAt = accessExpiresAt;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }

        public String getCsrf() {
            return csrf;
        }

        public void setCsrf(String csrf) {
            this.csrf = csrf;
        }

        public String getAccessExpiresAt() {
            return accessExpiresAt;
        }

        public void setAccessExpiresAt(String accessExpiresAt) {
            this.accessExpiresAt = accessExpiresAt;
        }

    }


