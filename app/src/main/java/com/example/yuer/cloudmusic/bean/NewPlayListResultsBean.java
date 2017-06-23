package com.example.yuer.cloudmusic.bean;

public class NewPlayListResultsBean {

    //播放状态  true 播放   false 未播放
    private boolean playStatus = false;

    private FileUrlBean fileUrl;
    private int albumId;
    private String displayName;
    private String objectId;
    private int duration;
    private String artist;
    private AlbumPicBean albumPic;
    private int size;
    private String title;
    private int id;
    private String album;
    private LrcBean lrc;

    public boolean isPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(boolean playStatus) {
        this.playStatus = playStatus;
    }

    public FileUrlBean getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(FileUrlBean fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public AlbumPicBean getAlbumPic() {
        return albumPic;
    }

    public void setAlbumPic(AlbumPicBean albumPic) {
        this.albumPic = albumPic;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public LrcBean getLrc() {
        return lrc;
    }

    public void setLrc(LrcBean lrc) {
        this.lrc = lrc;
    }


    public static class FileUrlBean {
        /**
         * name : 张学友 - 讲你知.mp3
         * url : http://ac-kCFRDdr9.clouddn.com/hJhoiRmzIBs4Cvo4mJyxiYpyFBoMfntpf4Q5kasO.mp3
         * objectId : 59398dd38d6d81005855ca04
         * __type : File
         * provider : qiniu
         */

        private String name;
        private String url;
        private String objectId;
        private String __type;
        private String provider;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
    }

    public static class AlbumPicBean {
        /**
         * name : 下载.jpg
         * url : http://ac-kCFRDdr9.clouddn.com/af344b23e5d20df528ab.jpg
         * objectId : 59425e0bac502e006b78af3f
         * __type : File
         * provider : qiniu
         */

        private String name;
        private String url;
        private String objectId;
        private String __type;
        private String provider;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
    }

    public static class LrcBean {
        /**
         * name : 讲你知.lrc
         * url : http://ac-kCFRDdr9.clouddn.com/e11ffb6bc99dc5c755f6.lrc
         * objectId : 59494c96128fe1006a5f45ef
         * __type : File
         * provider : qiniu
         */

        private String name;
        private String url;
        private String objectId;
        private String __type;
        private String provider;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String get__type() {
            return __type;
        }

        public void set__type(String __type) {
            this.__type = __type;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
    }
}