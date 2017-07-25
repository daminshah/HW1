package com.example.daminshah.newsapp.Database;

import android.provider.BaseColumns;

/**
 * Created by daminshah on 7/25/17.
 */

public class Contract {

    public static class TABLE_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME="articles";
        public static final String COLUMN_NAME_AUTHOR="author";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_DESCRIPTION="description";
        public static final String COLUMN_NAME_NEWS_URL="newsurl";
        public static final String COLUMN_NAME_IMAGE_URL="imgurl";
        public static final String COLUMN_NAME_PUBLISHED="published";
    }
}
