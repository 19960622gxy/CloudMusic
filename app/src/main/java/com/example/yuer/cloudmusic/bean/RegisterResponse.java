package com.example.yuer.cloudmusic.bean;

import java.util.List;

/**
 * Created by Yuer on 2017/6/6.
 */

public class RegisterResponse {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * updatedAt : 2017-06-03T02:40:21.062Z
         * objectId : 58e52eb361ff4b00618570bc
         * username : weidong
         * createdAt : 2017-04-05T17:51:47.973Z
         * emailVerified : false
         * location : {"longitude":0,"latitude":0,"__type":"GeoPoint"}
         * mobilePhoneVerified : false
         * installation : {"__type":"Pointer","className":"_Installation","objectId":"KniSQflr7EYeGfg2P7Ewz04OSFwF5U3q"}
         */

        private String updatedAt;
        private String objectId;
        private String username;
        private String createdAt;
        private boolean emailVerified;
        private LocationBean location;
        private boolean mobilePhoneVerified;
        private InstallationBean installation;

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public boolean isEmailVerified() {
            return emailVerified;
        }

        public void setEmailVerified(boolean emailVerified) {
            this.emailVerified = emailVerified;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public boolean isMobilePhoneVerified() {
            return mobilePhoneVerified;
        }

        public void setMobilePhoneVerified(boolean mobilePhoneVerified) {
            this.mobilePhoneVerified = mobilePhoneVerified;
        }

        public InstallationBean getInstallation() {
            return installation;
        }

        public void setInstallation(InstallationBean installation) {
            this.installation = installation;
        }

        public static class LocationBean {
            /**
             * longitude : 0.0
             * latitude : 0.0
             * __type : GeoPoint
             */

            private double longitude;
            private double latitude;
            private String __type;

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public String get__type() {
                return __type;
            }

            public void set__type(String __type) {
                this.__type = __type;
            }
        }

        public static class InstallationBean {
            /**
             * __type : Pointer
             * className : _Installation
             * objectId : KniSQflr7EYeGfg2P7Ewz04OSFwF5U3q
             */

            private String __type;
            private String className;
            private String objectId;

            public String get__type() {
                return __type;
            }

            public void set__type(String __type) {
                this.__type = __type;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }
        }
    }
}
