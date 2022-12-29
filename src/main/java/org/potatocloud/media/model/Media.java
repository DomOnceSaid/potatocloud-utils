package org.potatocloud.media.model;

import java.util.Arrays;

public class Media {
    private String thumbnail;
    private String reducedQualityView;
    private String[] original;

    public Media() {}
    public Media(String thumbnail, String reducedQualityView, String[] original) {
        this.thumbnail = thumbnail;
        this.reducedQualityView = reducedQualityView;
        this.original = original;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Media setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getReducedQualityView() {
        return reducedQualityView;
    }

    public Media setReducedQualityView(String reducedQualityView) {
        this.reducedQualityView = reducedQualityView;
        return this;
    }

    public String[] getOriginal() {
        return original;
    }

    public Media setOriginal(String[] original) {
        this.original = original;
        return this;
    }

    @Override
    public String toString() {
        return "Media{" +
                "thumbnail='" + thumbnail + '\'' +
                ", reducedQualityView='" + reducedQualityView + '\'' +
                ", original=" + Arrays.toString(original) +
                '}';
    }
}
